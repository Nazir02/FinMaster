package com.encom.finmaster.modules.main.home.utils;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.encom.finmaster.R;


public class ShadowTransformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    private ViewPager pager;
    private ICardAdapter mAdapter;
    private float mLastOffset;
    private boolean mScalingEnabled = true;
    private static final float scale_size = 1.2f;

    public ShadowTransformer(ViewPager viewPager, ICardAdapter adapter) {
        //mScalingEnabled=true;
        pager = viewPager;
        viewPager.addOnPageChangeListener(this);
        mAdapter = adapter;
    }

    @Override
    public void transformPage(View page, float position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int realCurrentPosition;
        int nextPosition;
        float realOffset;
        boolean goingLeft = mLastOffset > positionOffset;

        // If we're going backwards, onPageScrolled receives the last position
        // instead of the current one
        if (goingLeft) {
            realCurrentPosition = position + 1;
            nextPosition = position;
            realOffset = 1 - positionOffset;
        } else {
            nextPosition = position + 1;
            realCurrentPosition = position;
            realOffset = positionOffset;
        }

        // Avoid crash on overscroll
        if (nextPosition > mAdapter.getCardsCount() - 1
                || realCurrentPosition > mAdapter.getCardsCount() - 1) {
            return;
        }
        View currentView = null;
        if (mAdapter.getViewAt(realCurrentPosition) != null) {
            currentView = (View) mAdapter.getViewAt(realCurrentPosition).findViewById(R.id.cardFrame);
        }


        // This might be null if a fragment is being used
        // and the views weren't created yet
        if (currentView != null) {
            if (mScalingEnabled) {
                currentView.setScaleX((float) (1 + 0.2 * (1 - realOffset)));
                currentView.setScaleY((float) (1 + 0.2 * (1 - realOffset)));
            }
            //currentView.setCardElevation((baseElevation + baseElevation  * (ICardAdapter.MAX_ELEVATION_FACTOR - 1) * (1 - realOffset)));
            //currentView.setCardElevation(BaseFunctions.dpToPx(8));
        }

        //CardView nextCard = mAdapter.getViewAt(nextPosition);
        View nextView = null;
        if (mAdapter.getViewAt(nextPosition) != null) {
            nextView = (View) mAdapter.getViewAt(nextPosition).findViewById(R.id.cardFrame);
        }


        //CardView nextView= mAdapter.getViewAt(nextPosition);
        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (nextView != null) {
            if (mScalingEnabled) {
                nextView.setScaleX((float) (1 + 0.2 * (realOffset)));
                nextView.setScaleY((float) (1 + 0.2 * (realOffset)));
            }
            //nextView.setCardElevation((baseElevation + baseElevation  * (ICardAdapter.MAX_ELEVATION_FACTOR - 1) * (realOffset)));
            //nextView.setCardElevation(BaseFunctions.dpToPx(4));
        }

        mLastOffset = positionOffset;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
