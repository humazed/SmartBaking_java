package humazed.github.com.smartbaking_java.utils.auto_gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * User: YourPc
 * Date: 8/8/2017
 */

public final class GsonAutoValue {
    private static final Gson ourInstance = new GsonBuilder()
            .registerTypeAdapterFactory(new AutoValueAdapterFactory())
            .create();

    public static Gson getInstance() {
        return ourInstance;
    }

    private GsonAutoValue() { }
}
