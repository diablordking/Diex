package diex.eav.com.viewimpl.other;

import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import diex.eav.com.R;
import diex.eav.com.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import diex.eav.com.utils.ThemeUtils;


public class News extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
  //  @BindView(R.id.iv_topic)
//    ImageView ivTopic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

      ButterKnife.bind(this);
  //     applyKitKatTranslucency(getResources().getColor(R.color.black));
        initView();
    }

    private void initView() {

        toolbar.setBackgroundColor(ThemeUtils.getToolBarColor());
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backThActivity();
            }
        });

        toolbar.setTitle("Diet News");

    }
    @Override
    public String setActName() {
        return null;
    }
}
