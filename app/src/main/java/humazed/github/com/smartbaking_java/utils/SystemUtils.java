package humazed.github.com.smartbaking_java.utils;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * User: YourPc
 * Date: 8/3/2017
 */

public final class SystemUtils {
    public static void hideSystemUI(AppCompatActivity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }
}