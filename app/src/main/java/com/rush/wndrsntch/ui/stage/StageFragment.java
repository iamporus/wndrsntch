package com.rush.wndrsntch.ui.stage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.prush.bndrsntchoicelayout.BndrsntChoiceLayout;
import com.prush.typedtextview.TypedTextView;
import com.rush.wndrsntch.AppConstants;
import com.rush.wndrsntch.R;
import com.rush.wndrsntch.TextAnimator;
import com.rush.wndrsntch.base.BaseFragment;
import com.rush.wndrsntch.data.network.model.Choice;
import com.rush.wndrsntch.data.network.model.Stage;
import com.rush.wndrsntch.di.ApiInjectorFactory;
import com.rush.wndrsntch.di.PreferenceFactory;
import com.rush.wndrsntch.ui.MainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StageFragment extends BaseFragment implements IStageView, View.OnClickListener
{
    private static final String TAG = "StageFragment";
    private TypedTextView mStageTextView;
    private StagePresenter< IStageView > mPresenter;
    private Stage mStage;
    private boolean mbTypingAnimationEnded;
    private LottieAnimationView mClickHereView;
    private BndrsntChoiceLayout mChoiceLayout;

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
        mPresenter = new StagePresenter<>( ApiInjectorFactory.getInstance(getResources()),
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
        TypedTextView.Builder builder = new TypedTextView.Builder( mStageTextView );
        builder.showCursor( true )
                .setTypingSpeed( 50 )
                .setSentencePause( 500 )
                .randomizeTypingSpeed( true );
        mStageTextView = builder.build();

        mStageTextView.showCursor( true );
        mClickHereView = view.findViewById( R.id.animation_view );

        mChoiceLayout = view.findViewById( R.id.bndrSntchoiceLayout );
        mChoiceLayout.setNumberOfChoices( 2 );
        mChoiceLayout.setBRevealMode( true );
        mChoiceLayout.setBRandomizeChoice( true );

        getLifecycle().addObserver( mChoiceLayout.getLifeCycleObserver() );

        mChoiceLayout.setOnChoiceSelectedListener( new BndrsntChoiceLayout.OnChoiceSelectedListener()
        {
            @Override
            public void onChoiceSelected( final int i, final String s )
            {
                mChoiceLayout.reset();
                TextAnimator.hide( mChoiceLayout, 1000 );
                TextAnimator.hide( mStageTextView, 1000 ).addListener( new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd( Animator animation )
                    {
                        super.onAnimationEnd( animation );

                        switch( i )
                        {
                            case R.id.choiceOneTextView:
                                mPresenter.onChoiceSelected( ( Choice ) mChoiceLayout.getChoiceOneTag() );
                                break;
                            case R.id.choiceTwoTextView:
                                mPresenter.onChoiceSelected( ( Choice ) mChoiceLayout.getChoiceTwoTag() );
                                break;
                        }

                    }
                } );
            }
        } );
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

            mChoiceLayout.setChoiceOneTag( mStage.getChoices().get( 0 ) );
            mChoiceLayout.setChoiceTwoTag( mStage.getChoices().get( 1 ) );

            bHasChoices = true;
            mStageTextView.setOnClickListener( null );
        }
        else
        {
            bHasChoices = false;
            TextAnimator.hide( mChoiceLayout, 500 );
            mStageTextView.setOnClickListener( this );

        }

        TextAnimator.reveal( mStageTextView, 500 ).addListener( new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd( Animator animation )
            {
                super.onAnimationEnd( animation );
                TextAnimator.addBlur( mStageTextView, 3, BlurMaskFilter.Blur.NORMAL );
                mStageTextView.setTypedText( mStage.getValue() );
                mStageTextView.setOnCharacterTypedListener( new TypedTextView.OnCharacterTypedListener()
                {
                    @Override
                    public void onCharacterTyped( char character, int index )
                    {
                        Log.d( TAG, "onCharacterTyped: " + index + " - " + character + " length - " + mStage.getValue().length() );
                        if( index == mStage.getValue().length() - 1 )
                        {
                            TextAnimator.shake( mStageTextView, 200 ).addListener( new AnimatorListenerAdapter()
                            {
                                @Override
                                public void onAnimationEnd( Animator animation )
                                {
                                    super.onAnimationEnd( animation );
                                    TextAnimator.removeBlur( mStageTextView );
                                    TextAnimator.addBlur( mStageTextView, 3, BlurMaskFilter.Blur.NORMAL );
                                    if( !bHasChoices )
                                    {
                                        TextAnimator.reveal( mClickHereView, 1000, 0.2f );
                                    }
                                    mbTypingAnimationEnded = true;
                                }
                            } );

                            if( bHasChoices )
                            {
                                TextAnimator.reveal( mChoiceLayout, 1000 );
                                mChoiceLayout.startTimer( 10000, new BndrsntChoiceLayout.OnTimerElapsedListener()
                                {
                                    @Override
                                    public void onTimerElapsed()
                                    {

                                    }
                                } );

                                mChoiceLayout.setChoiceOneText( mStage.getChoices().get( 0 ).getChoice() );
                                mChoiceLayout.setChoiceTwoText( mStage.getChoices().get( 1 ).getChoice() );

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
            case R.id.animation_view:
            {
                if( mbTypingAnimationEnded )
                {
                    TextAnimator.hide( mClickHereView, 1000 );
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
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mPresenter.onDetach();
    }
}
