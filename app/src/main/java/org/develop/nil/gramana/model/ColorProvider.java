package org.develop.nil.gramana.model;

import android.content.Context;
import android.graphics.Color;
import org.develop.nil.gramana.R;

import java.util.Locale;

/**
 * Created by n0ne on 13/10/16.
 */
public class ColorProvider {

    private int mDefaultColor;
    private int[] mColorsList;
    private int mCurrColor;

    public ColorProvider (Context c, int defaultColor, int maxColors) {
        mDefaultColor = defaultColor;
        mColorsList = c.getResources().getIntArray(R.array.color_syllables);

        if(mColorsList.length < maxColors) {
            throw new IllegalStateException(
                    String.format(
                            Locale.getDefault(),
                            "There are not enough colors to supply: %d requested, %d given.\n",
                            maxColors,
                            mColorsList.length
                    )
            );
        }

        mCurrColor = 0;
    }

    public int getNextColor () {
        if (mCurrColor < mColorsList.length) {
            return mColorsList[mCurrColor++];
        }
        return mDefaultColor;
    }

}
