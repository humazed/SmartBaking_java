package humazed.github.com.smartbaking_java;

import android.app.Application;

import com.blankj.utilcode.util.Utils;


/**
 * User: YourPc
 * Date: 8/8/2017
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(getApplicationContext());

    }
}
