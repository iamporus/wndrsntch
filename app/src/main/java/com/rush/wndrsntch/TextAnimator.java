package com.rush.wndrsntch;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.BlurMaskFilter;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.google.common.base.Preconditions;

public class TextAnimator
{
    public static void addBlur( @NonNull TextView textView, float radius, BlurMaskFilter.Blur blurType )
    {
        Preconditions.checkNotNull( textView );
        Preconditions.checkArgument( radius != 0 );
        BlurMaskFilter blurMaskFilter = new BlurMaskFilter( radius, blurType );
        textView.getPaint().setMaskFilter( blurMaskFilter );
    }

    public static void removeBlur( @NonNull TextView textView )
    {
        Preconditions.checkNotNull( textView );
        textView.setLayerType( View.LAYER_TYPE_SOFTWARE, null );
        textView.getPaint().setMaskFilter( null );
    }

    public static ObjectAnimator reveal( @NonNull final TextView textView, int duration )
    {
        Preconditions.checkNotNull( textView );
        ObjectAnimator animation = ObjectAnimator.ofFloat( textView, "alpha", 0f, 1f );
        animation.setDuration( duration );
        animation.start();

        animation.addListener( new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd( Animator animation )
            {
                super.onAnimationEnd( animation );
                textView.setVisibility( View.VISIBLE );
            }
        } );

        return animation;
    }

    public static ObjectAnimator hide( @NonNull final TextView textView, int duration )
    {
        Preconditions.checkNotNull( textView );
        ObjectAnimator animation = ObjectAnimator.ofFloat( textView, "alpha", 1f, 0f );
        animation.setDuration( duration );
        animation.start();

        animation.addListener( new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd( Animator animation )
            {
                super.onAnimationEnd( animation );
                textView.setVisibility( View.INVISIBLE );
            }
        } );

        return animation;
    }

    public static ObjectAnimator shake( @NonNull TextView textView, int duration )
    {
        ObjectAnimator rotate = ObjectAnimator.ofFloat( textView, "translationX", 0f, 5f, 0f, 5f, 0f ); // rotate o degree then 20 degree and so on for one loop of rotation.
        rotate.setDuration( duration );
        rotate.start();

        return rotate;
    }

    public static ObjectAnimator breathe( @NonNull TextView textView, int duration )
    {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat( textView, "scaleX", 1f, 0.9f, 1f, 1.1f, 1f ); // rotate o degree then 20 degree and so on for one loop of rotation.
        scaleX.setDuration( duration );

        ObjectAnimator scaleY = ObjectAnimator.ofFloat( textView, "scaleY", 1f, 0.9f, 1f, 1.1f, 1f ); // rotate o degree then 20 degree and so on for one loop of rotation.
        scaleY.setDuration( duration );

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play( scaleX ).with( scaleY );
        animatorSet.start();

        return scaleX;
    }
}
