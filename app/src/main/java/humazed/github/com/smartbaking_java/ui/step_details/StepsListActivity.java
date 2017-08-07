package humazed.github.com.smartbaking_java.ui.step_details;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import humazed.github.com.smartbaking_java.R;
import humazed.github.com.smartbaking_java.adapters.StepsAdapter;
import humazed.github.com.smartbaking_java.model.Ingredient;
import humazed.github.com.smartbaking_java.model.Recipe;
import humazed.github.com.smartbaking_java.model.Step;
import java8.util.stream.StreamSupport;

import static android.support.v4.app.NavUtils.navigateUpFromSameTask;
import static humazed.github.com.smartbaking_java.ui.MainActivity.KEY_RECIPE;


/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [StepDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepsListActivity extends AppCompatActivity {
    private static final String TAG = StepsListActivity.class.getSimpleName();

    public static final String KEY_STEPS = "StepsListActivity:mStep";
    public static final String KEY_POSITION = "StepsListActivity:mPosition";

    @Nullable
    @BindView(R.id.recipe_detail_container)
    FrameLayout mRecipeDetailContainer;
    @BindView(R.id.stepsRecyclerView) RecyclerView mStepsRecyclerView;
    @BindView(R.id.ingredientsTextView) TextView mIngredientsTextView;
    @BindView(R.id.cardView) CardView mCardView;
    @BindView(R.id.frameLayout) FrameLayout mFrameLayout;
    @BindBool(R.bool.isTablet) boolean isTablet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_list);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Recipe recipe = getIntent().getParcelableExtra(KEY_RECIPE);

        if (getSupportActionBar() != null) getSupportActionBar().setTitle(recipe.name() + " Steps");
        setupRecyclerView(new ArrayList<>(recipe.steps()));

        mIngredientsTextView.setText(StreamSupport.stream(recipe.ingredients())
                .map(Ingredient::toString)
                .reduce((s, s2) -> s + s2).get());
    }

    private void setupRecyclerView(ArrayList<Step> steps) {
        StepsAdapter adapter = new StepsAdapter(steps);
        adapter.setOnItemClickListener((adapter1, view, position) -> {

            if (isTablet) {
                StepDetailFragment.start(this, steps, position);
            } else {
                Intent intent = new Intent(this, StepDetailActivity.class);
                intent.putExtra(KEY_STEPS, steps);
                intent.putExtra(KEY_POSITION, position);
                startActivity(intent);
            }
        });
        mStepsRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}