package humazed.github.com.smartbaking_java.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    public static final String DATA_LOADER = "DATA_LOADER";

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    CountingIdlingResource mCountingIdlingResource = new CountingIdlingResource(DATA_LOADER);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mCountingIdlingResource.increment();

        RecipesService.create(this).getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                mProgressBar.setVisibility(View.GONE);
                Log.d(TAG, "onResponse() returned: " + response.body());
                setupRecyclerView(response.body());
                mCountingIdlingResource.decrement();
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(MainActivity.this, "unexpected error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                mCountingIdlingResource.decrement();
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