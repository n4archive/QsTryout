package org.eu.droid_ng.systemui.qs;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;

import androidx.annotation.ColorInt;

import java.text.NumberFormat;

/* ng: stripped down com.android.settingslib.Utils */
public class Utils {
    /** Formats a double from 0.0..100.0 with an option to round **/
    public static String formatPercentage(double percentage, boolean round) {
        final int localPercentage = round ? Math.round((float) percentage) : (int) percentage;
        return formatPercentage(localPercentage);
    }

    /** Formats the ratio of amount/total as a percentage. */
    public static String formatPercentage(long amount, long total) {
        return formatPercentage(((double) amount) / total);
    }

    /** Formats an integer from 0..100 as a percentage. */
    public static String formatPercentage(int percentage) {
        return formatPercentage(((double) percentage) / 100.0);
    }

    /** Formats a double from 0.0..1.0 as a percentage. */
    public static String formatPercentage(double percentage) {
        return NumberFormat.getPercentInstance().format(percentage);
    }

    public static int getBatteryLevel(Intent batteryChangedIntent) {
        int level = batteryChangedIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        int scale = batteryChangedIntent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
        return (level * 100) / scale;
    }

    public static ColorStateList getColorAccent(Context context) {
        return getColorAttr(context, android.R.attr.colorAccent);
    }

    public static ColorStateList getColorError(Context context) {
        return getColorAttr(context, android.R.attr.colorError);
    }

    @ColorInt
    public static int getColorAccentDefaultColor(Context context) {
        return getColorAttrDefaultColor(context, android.R.attr.colorAccent);
    }

    @ColorInt
    public static int getColorErrorDefaultColor(Context context) {
        return getColorAttrDefaultColor(context, android.R.attr.colorError);
    }

    @ColorInt
    public static int getColorStateListDefaultColor(Context context, int resId) {
        final ColorStateList list =
                context.getResources().getColorStateList(resId, context.getTheme());
        return list.getDefaultColor();
    }

    /**
     * This method computes disabled color from normal color
     *
     * @param context the context
     * @param inputColor normal color.
     * @return disabled color.
     */
    @ColorInt
    public static int getDisabled(Context context, int inputColor) {
        return applyAlphaAttr(context, android.R.attr.disabledAlpha, inputColor);
    }

    @ColorInt
    public static int applyAlphaAttr(Context context, int attr, int inputColor) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        float alpha = ta.getFloat(0, 0);
        ta.recycle();
        return applyAlpha(alpha, inputColor);
    }

    @ColorInt
    public static int applyAlpha(float alpha, int inputColor) {
        alpha *= Color.alpha(inputColor);
        return Color.argb((int) (alpha), Color.red(inputColor), Color.green(inputColor),
                Color.blue(inputColor));
    }

    @ColorInt
    public static int getColorAttrDefaultColor(Context context, int attr) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        @ColorInt int colorAccent = ta.getColor(0, 0);
        ta.recycle();
        return colorAccent;
    }

    public static ColorStateList getColorAttr(Context context, int attr) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        ColorStateList stateList = null;
        try {
            stateList = ta.getColorStateList(0);
        } finally {
            ta.recycle();
        }
        return stateList;
    }

    public static int getThemeAttr(Context context, int attr) {
        return getThemeAttr(context, attr, 0);
    }

    public static int getThemeAttr(Context context, int attr, int defaultValue) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        int theme = ta.getResourceId(0, defaultValue);
        ta.recycle();
        return theme;
    }

    public static Drawable getDrawable(Context context, int attr) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        Drawable drawable = ta.getDrawable(0);
        ta.recycle();
        return drawable;
    }

    /**
    * Create a color matrix suitable for a ColorMatrixColorFilter that modifies only the color but
    * preserves the alpha for a given drawable
    * @param color
    * @return a color matrix that uses the source alpha and given color
    */
    public static ColorMatrix getAlphaInvariantColorMatrixForColor(@ColorInt int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        ColorMatrix cm = new ColorMatrix(new float[] {
                0, 0, 0, 0, r,
                0, 0, 0, 0, g,
                0, 0, 0, 0, b,
                0, 0, 0, 1, 0 });

        return cm;
    }

    /**
     * Create a ColorMatrixColorFilter to tint a drawable but retain its alpha characteristics
     *
     * @return a ColorMatrixColorFilter which changes the color of the output but is invariant on
     * the source alpha
     */
    public static ColorFilter getAlphaInvariantColorFilterForColor(@ColorInt int color) {
        return new ColorMatrixColorFilter(getAlphaInvariantColorMatrixForColor(color));
    }
}
