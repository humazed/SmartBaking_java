package humazed.github.com.smartbaking_java.ui.step_details;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

import humazed.github.com.smartbaking_java.R;
import humazed.github.com.smartbaking_java.model.Step;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [StepsListActivity].
 */
public class StepDetailActivity extends AppCompatActivity {
    private static final String TAG = StepDetailActivity.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        if (savedInstanceState == null) {
            int position = getIntent().getIntExtra(StepsListActivity.KEY_POSITION, 0);
            ArrayList<Step> steps = getIntent().getParcelableArrayListExtra(StepsListActivity.KEY_STEPS);

            StepDetailFragment.start(this, steps, position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}