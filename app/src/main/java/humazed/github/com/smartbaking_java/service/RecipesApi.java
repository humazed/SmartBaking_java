package humazed.github.com.smartbaking_java.service;

import java.util.List;

import humazed.github.com.smartbaking_java.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesApi {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();

}

