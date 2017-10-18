package com.sunny.mvpzhihu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

public final class ViewUtil {

    public static float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }

    public static int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    public static void setBottomNavigationShiftMode(BottomNavigationView bottomNavigationView, boolean enableShiftMode, boolean enableItemShiftMode) {
        try {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
            if (menuView == null) return;

            Field shiftMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftMode.setAccessible(true);
            shiftMode.setBoolean(menuView, enableShiftMode);
            shiftMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                if (item == null) continue;
                item.setShiftingMode(enableItemShiftMode);
                item.setChecked(item.getItemData().isChecked());
            }

            menuView.updateMenuView();
        } catch (Exception e) {
            LogUtil.e(e, "Unable to set shift mode for BottomNavigationView");
        }
    }

}
