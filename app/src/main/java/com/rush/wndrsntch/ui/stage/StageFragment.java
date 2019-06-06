package com.rush.wndrsntch.ui.stage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rush.wndrsntch.AppConstants;
import com.rush.wndrsntch.R;
import com.rush.wndrsntch.base.BaseFragment;
import com.rush.wndrsntch.data.network.model.Choice;
import com.rush.wndrsntch.data.network.model.Stage;
import com.rush.wndrsntch.di.ApiInjectorFactory;
import com.rush.wndrsntch.di.PreferenceFactory;
import com.rush.wndrsntch.ui.MainActivity;

public class StageFragment extends BaseFragment implements IStageView, View.OnClickListener
{
    private static final String TAG = "StageFragment";
    private TextView mStageTextView;
    private Button mLeftChoice;
    private Button mRightChoice;
    private StagePresenter< IStageView > mPresenter;
    private Stage mStage;

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
        mLeftChoice = view.findViewById( R.id.button );
        mRightChoice = view.findViewById( R.id.button2 );
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
        //TODO: Animate UI updates
        mStage = stage;

        if( mStage.getChoices() != null && mStage.getChoices().size() > 0 )
        {
            //setup choices
            mLeftChoice.setText( mStage.getChoices().get( 0 ).getChoice() );
            mRightChoice.setText( mStage.getChoices().get( 1 ).getChoice() );

            mLeftChoice.setVisibility( View.VISIBLE );
            mRightChoice.setVisibility( View.VISIBLE );

            mLeftChoice.setTag( mStage.getChoices().get( 0 ) );
            mRightChoice.setTag( mStage.getChoices().get( 1 ) );
            mLeftChoice.setOnClickListener( this );
            mRightChoice.setOnClickListener( this );
        }
        else
        {
            mLeftChoice.setVisibility( View.INVISIBLE );
            mRightChoice.setVisibility( View.INVISIBLE );
        }

        mStageTextView.setText( mStage.getValue() );
        mStageTextView.setOnClickListener( this );


        startAnimation();
    }

    private void startAnimation()
    {

        BlurMaskFilter blurMaskFilter = new BlurMaskFilter( 4, BlurMaskFilter.Blur.NORMAL );
        mStageTextView.getPaint().setMaskFilter( blurMaskFilter );

        ObjectAnimator animation = ObjectAnimator.ofFloat( mStageTextView, "alpha", 0f, 0.9f );
        animation.setDuration( 500 );
        animation.start();

        animation.addUpdateListener( new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate( ValueAnimator valueAnimator )
            {
                float animatedValue = ( float ) valueAnimator.getAnimatedValue();
                Log.d( TAG, "onAnimationUpdate: " + animatedValue );

            }
        } );
        animation.addListener( new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd( Animator animation )
            {
                super.onAnimationEnd( animation );
                mStageTextView.setLayerType( View.LAYER_TYPE_SOFTWARE, null );
                mStageTextView.getPaint().setMaskFilter( null );
            }
        } );
    }


    @Override
    public void onClick( View view )
    {
        switch( view.getId() )
        {
            case R.id.textView:
            {
                ObjectAnimator animation = ObjectAnimator.ofFloat( mStageTextView, "alpha", 1f, 0f );
                animation.setDuration( 500 );
                animation.start();
                animation.addListener( new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd( Animator animation )
                    {
                        super.onAnimationEnd( animation );
                        mPresenter.gotoStage( mStage.getNextStageId() );
                    }
                } );

                break;
            }
            case R.id.button:
            case R.id.button2:
            {
                mPresenter.onChoiceSelected( ( Choice ) view.getTag() );
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
