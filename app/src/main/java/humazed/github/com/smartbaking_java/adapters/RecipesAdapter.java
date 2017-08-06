package humazed.github.com.smartbaking_java.adapters;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import humazed.github.com.smartbaking_java.R;
import humazed.github.com.smartbaking_java.model.Recipe;


/**
 * User: YourPc
 * Date: 7/22/2017
 */
public final class RecipesAdapter extends BaseQuickAdapter<Recipe, BaseViewHolder> {

    @BindView(R.id.recipeImageView) ImageView mRecipeImageView;
    @BindView(R.id.recipeNameTextView) TextView mRecipeNameTextView;

    public RecipesAdapter(List<Recipe> data) {
        super(R.layout.row_recipe, data);
    }

    @Override
    public void convert(BaseViewHolder helper, Recipe result) {
        ButterKnife.bind(this, helper.itemView);

        mRecipeNameTextView.setText(result.name());

        Glide.with(mContext)
                .load(result.image())
                .apply(new RequestOptions().placeholder(R.drawable.ic_cake_white_24dp))
                .into(mRecipeImageView);
    }
}

