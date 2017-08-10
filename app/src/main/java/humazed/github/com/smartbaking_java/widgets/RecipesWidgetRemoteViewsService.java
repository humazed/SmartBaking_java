package humazed.github.com.smartbaking_java.widgets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import humazed.github.com.smartbaking_java.R;
import humazed.github.com.smartbaking_java.model.Ingredient;
import humazed.github.com.smartbaking_java.model.Recipe;
import humazed.github.com.smartbaking_java.utils.auto_gson.GsonAutoValue;

/**
 * User: huma
 * Date: 04-Sep-16
 */
public class RecipesWidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private final String TAG = RecipesWidgetRemoteViewsService.class.getSimpleName();

            private Recipe mRecipe;

            @Override
            public void onCreate() {
                SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_recipe), Context.MODE_PRIVATE);

                mRecipe = GsonAutoValue.getInstance()
                        .fromJson(prefs.getString(getString(R.string.pref_recipe_gson), ""), Recipe.class);
            }

            @Override
            public void onDataSetChanged() {
                SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_recipe), Context.MODE_PRIVATE);

                mRecipe = GsonAutoValue.getInstance()
                        .fromJson(prefs.getString(getString(R.string.pref_recipe_gson), ""), Recipe.class);
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION || mRecipe == null) return null;


                Ingredient ingredient = mRecipe.ingredients().get(position);

                Log.d(TAG, "ingredient = " + ingredient);

                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_row_step);

                views.setTextViewText(R.id.nameTextView, ingredient.toString());

                // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually
//                Intent intent = new Intent();
//                intent.putExtra(KEY_STEPS, new ArrayList<>(mRecipe.steps()));
//                intent.putExtra(KEY_POSITION, position);
//
//                views.setOnClickFillInIntent(R.id.row_steps_container, intent);

                return views;
            }

            @Override
            public int getViewTypeCount() { return 1; }

            @Override
            public int getCount() { return mRecipe == null ? 0 : mRecipe.steps().size(); }

            @Override
            public void onDestroy() { }

            @Override
            public RemoteViews getLoadingView() { return null; }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}