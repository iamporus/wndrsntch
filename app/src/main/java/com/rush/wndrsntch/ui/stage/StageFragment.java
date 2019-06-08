package com.rush.wndrsntch.ui.stage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rush.wndrsntch.AppConstants;
import com.rush.wndrsntch.R;
import com.rush.wndrsntch.TextAnimator;
import com.rush.wndrsntch.base.BaseFragment;
import com.rush.wndrsntch.data.network.model.Choice;
import com.rush.wndrsntch.data.network.model.Stage;
import com.rush.wndrsntch.di.ApiInjectorFactory;
import com.rush.wndrsntch.di.PreferenceFactory;
import com.rush.wndrsntch.ui.MainActivity;
import com.rush.wndrsntch.ui.views.TypedTextView;

public class StageFragment extends BaseFragment implements IStageView, View.OnClickListener
{
    private static final String TAG = "StageFragment";
    private TypedTextView mStageTextView;
    private TypedTextView mLeftChoice;
    private TypedTextView mRightChoice;
    private StagePresenter< IStageView > mPresenter;
    private Stage mStage;
    private boolean mbTypingAnimationEnded;

    public static StageFragment newInstance( Stage stage )
    {
        StageFragment stageFragment = new StageFragment();
        Bundle args = new Bundle();
        args.putSerializable( MainActivity.STAGE, stage );
        stageFragment.setArguments( args );

        return stageFragment;
    }

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        //TODO: Is this correct? Can Factory pattern complement DI?
        mPresenter = new StagePresenter<>( ApiInjectorFactory.getInstance(),
                                           PreferenceFactory.getInstance( getContext(),
                                                                          AppConstants.PREFS_FILE ) );
        if( getArguments() != null )
        {
            mStage = ( Stage ) getArguments().getSerializable( MainActivity.STAGE );
        }
        mPresenter.onAttach( this );
    }

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState )
    {
        View view = inflater.inflate( R.layout.fragment_stage, container, false );

        mStageTextView = view.findViewById( R.id.textView );
        mStageTextView.splitSentences( true );
        mStageTextView.showCursor( false );
        mStageTextView.emitSound( true, R.raw.typing );

        mLeftChoice = view.findViewById( R.id.button );
        mRightChoice = view.findViewById( R.id.button2 );
        mRightChoice.emitSound( true, R.raw.typing );
        mLeftChoice.emitSound( true, R.raw.typing );
        return view;
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState )
    {
        super.onViewCreated( view, savedInstanceState );

        setupStage( mStage );
    }

    @Override
    public void setupStage( Stage stage )
    {
        mStage = stage;
        final boolean bHasChoices;

        if( mStage.getChoices() != null && mStage.getChoices().size() > 0 )
        {
            //setup choices

            mLeftChoice.setTag( mStage.getChoices().get( 0 ) );
            mRightChoice.setTag( mStage.getChoices().get( 1 ) );

            mLeftChoice.setOnClickListener( this );
            mRightChoice.setOnClickListener( this );

            bHasChoices = true;
            mStageTextView.setOnClickListener( null );
        }
        else
        {
            bHasChoices = false;
            TextAnimator.hide( mLeftChoice, 500 );
            TextAnimator.hide( mRightChoice, 500 );
            mStageTextView.setOnClickListener( this );
        }

        TextAnimator.reveal( mStageTextView, 500 ).addListener( new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd( Animator animation )
            {
                super.onAnimationEnd( animation );
                TextAnimator.addBlur( mStageTextView, 3, BlurMaskFilter.Blur.NORMAL );
                mStageTextView.setTypedText( mStage.getValue(), new TypedTextView.TypingUpdateListener()
                {
                    @Override
                    public void onTypingUpdate( int index )
                    {
                        if( index == mStage.getValue().length() )
                        {
                            TextAnimator.shake( mStageTextView, 200 ).addListener( new AnimatorListenerAdapter()
                            {
                                @Override
                                public void onAnimationEnd( Animator animation )
                                {
                                    super.onAnimationEnd( animation );
                                    TextAnimator.removeBlur( mStageTextView );
                                    TextAnimator.addBlur( mStageTextView, 3, BlurMaskFilter.Blur.NORMAL );
                                    mbTypingAnimationEnded = true;
                                }
                            } );

                            if( bHasChoices )
                            {
                                TextAnimator.reveal( mLeftChoice, 1000 );

                                final String choiceText = mStage.getChoices().get( 0 ).getChoice();
                                mLeftChoice.setTypedText( choiceText, new TypedTextView.TypingUpdateListener()
                                {
                                    @Override
                                    public void onTypingUpdate( int index )
                                    {
                                        if( index == choiceText.length() )
                                        {
                                            TextAnimator.reveal( mRightChoice, 1000 ).addListener( new AnimatorListenerAdapter()
                                            {
                                                @Override
                                                public void onAnimationEnd( Animator animation )
                                                {
                                                    super.onAnimationEnd( animation );
                                                    mRightChoice.setTypedText( mStage.getChoices().get( 1 ).getChoice(), null );
                                                }
                                            } );
                                        }
                                    }
                                } );
                            }
                        }
                    }
                } );
            }
        } );
    }

    @Override
    public void onClick( final View view )
    {
        switch( view.getId() )
        {
            case R.id.textView:
            {
                if( mbTypingAnimationEnded )
                {
                    TextAnimator.hide( mStageTextView, 1000 ).addListener( new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationEnd( Animator animation )
                        {
                            mPresenter.gotoStage( mStage.getNextStageId() );
                            mbTypingAnimationEnded = false;
                        }
                    } );
                }


                break;
            }
            case R.id.button:
            case R.id.button2:
            {
                TextAnimator.hide( mStageTextView, 1000 ).addListener( new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd( Animator animation )
                    {
                        super.onAnimationEnd( animation );
                        mPresenter.onChoiceSelected( ( Choice ) view.getTag() );
                    }
                } );
                break;
            }
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mPresenter.onDetach();
    }
}
