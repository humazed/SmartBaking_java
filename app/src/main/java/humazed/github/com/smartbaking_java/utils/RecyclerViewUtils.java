package humazed.github.com.smartbaking_java.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * User: YourPc
 * Date: 8/2/2017
 */

public class RecyclerViewUtils {
    public static int calculateSpanCount(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = (float) displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 130);
    }
}

