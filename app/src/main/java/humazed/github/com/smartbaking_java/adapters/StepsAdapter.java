package humazed.github.com.smartbaking_java.adapters;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import humazed.github.com.smartbaking_java.R;
import humazed.github.com.smartbaking_java.model.Step;

/**
 * User: YourPc
 * Date: 7/22/2017
 */
public final class StepsAdapter extends BaseQuickAdapter<Step, BaseViewHolder> {
    @BindView(R.id.nameTextView) TextView mNameTextView;
    @BindView(R.id.stepImageView) ImageView mStepImageView;

    public StepsAdapter(List<Step> data) {
        super(R.layout.row_step, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Step result) {
        ButterKnife.bind(this, helper.itemView);

        mNameTextView.setText(result.id() + 1 + "- " + result.shortDescription());

        if (TextUtils.isEmpty(result.videoURL())) mStepImageView.setVisibility(View.INVISIBLE);

    }
}

