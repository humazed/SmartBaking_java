package humazed.github.com.smartbaking_java.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import humazed.github.com.smartbaking_java.R;
import humazed.github.com.smartbaking_java.adapters.RecipesAdapter;
import humazed.github.com.smartbaking_java.model.Recipe;
import humazed.github.com.smartbaking_java.service.RecipesService;
import humazed.github.com.smartbaking_java.ui.step_details.StepsListActivity;
import humazed.github.com.smartbaking_java.utils.RecyclerViewUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String KEY_RECIPE = "MainActivity:recipe";

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RecipesService.create().getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                setupRecyclerView(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });


    }

    private void setupRecyclerView(List<Recipe> recipes) {
        RecipesAdapter adapter = new RecipesAdapter(recipes);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(this, StepsListActivity.class);
            intent.putExtra(KEY_RECIPE, recipes.get(position));
            startActivity(intent);
        });

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, RecyclerViewUtils.calculateSpanCount(this)));
    }
}