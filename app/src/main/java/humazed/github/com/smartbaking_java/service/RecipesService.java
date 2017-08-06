package humazed.github.com.smartbaking_java.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import humazed.github.com.smartbaking_java.utils.AutoValueAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * User: YourPc
 * Date: 8/6/2017
 */

public class RecipesService {
    public static RecipesApi create() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new AutoValueAdapterFactory())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .build();

        return retrofit.create(RecipesApi.class);
    }
}
