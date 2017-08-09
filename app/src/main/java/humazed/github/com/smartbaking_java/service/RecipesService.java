package humazed.github.com.smartbaking_java.service;

import android.content.Context;

import humazed.github.com.smartbaking_java.utils.NetworkUtil;
import humazed.github.com.smartbaking_java.utils.auto_gson.GsonAutoValue;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * User: YourPc
 * Date: 8/6/2017
 */

public class RecipesService {

    public static RecipesApi create(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(NetworkUtil.getCaCheOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create(GsonAutoValue.getInstance()))
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .build();

        return retrofit.create(RecipesApi.class);
    }

}
