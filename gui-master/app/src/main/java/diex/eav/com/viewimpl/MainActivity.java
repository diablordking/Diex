package diex.eav.com.viewimpl;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import diex.eav.com.R;
import diex.eav.com.adapter.ThemeColorAdapter;
import diex.eav.com.base.ActivityCollector;
import diex.eav.com.base.BaseActivity;
import diex.eav.com.base.EasyRecyclerViewAdapter;
import diex.eav.com.home.ThemeColor;
import diex.eav.com.utils.ThemeUtils;
import diex.eav.com.viewimpl.other.AboutActivity;
import diex.eav.com.viewimpl.other.News;
import diex.eav.com.viewimpl.other.RecommedActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
import diex.eav.com.adapter.CustomPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;



public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbarlayout)
    AppBarLayout appbarlayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.coordinatorlayout)
    CoordinatorLayout coordinatorlayout;
    @BindView(R.id.id_navigationview)
    NavigationView idNavigationview;
    @BindView(R.id.drawerlayout_home)
    DrawerLayout drawerlayoutHome;
    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;
    CircleImageView abc;
    private List<Fragment> listFragment;
    private int currentFragment;
    private TextView txtName;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       // applyKitKatTranslucency();
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this,drawerlayoutHome, ThemeUtils.getThemeColor());
       // StatusBarUtil.setColor(MainActivity.this, ThemeUtils.getThemeColor());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        CustomPagerAdapter cus=new CustomPagerAdapter(this);
        viewPager.setAdapter(cus);

        initView();


        initChangeTheme();

    }



    private  void initView(){

        toolbar.setBackgroundColor(ThemeUtils.getToolBarColor());


        ActionBarDrawerToggle mActionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerlayoutHome, toolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        drawerlayoutHome.setDrawerListener(mActionBarDrawerToggle);

        idNavigationview.inflateHeaderView(R.layout.header_nav);

        View headerView = idNavigationview.getHeaderView(0);
        txtName = (TextView) headerView.findViewById(R.id.tv_name);
//        GoogleSignInAccount a = resultt.getSignInAccount();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            String displayName = user.getDisplayName();
            Uri profileUri = user.getPhotoUrl();

            for (UserInfo userInfo : user.getProviderData()) {
                if (displayName == null && userInfo.getDisplayName() != null) {
                    displayName = userInfo.getDisplayName();
                }
                if (profileUri == null && userInfo.getPhotoUrl() != null) {
                    profileUri = userInfo.getPhotoUrl();
                }
            }
                abc = (CircleImageView) headerView.findViewById(R.id.sdv_avatar);
            txtName.setText(displayName);
            // emailTextView.setText(user.getEmail());
             if (profileUri != null) {
             Glide.with(this)
             .load(profileUri)
             //.placeholder(R.drawable.ic_avtar)
             .into(abc);
             }

        }

        idNavigationview.inflateMenu(R.menu.menu_nav);
        idNavigationview.setItemIconTintList(ThemeUtils.getNaviItemIconTinkList());

        onNavgationViewMenuItemSelected(idNavigationview);


    }


    /**

     *
     * @param mNav
     */
    private void onNavgationViewMenuItemSelected(NavigationView mNav) {
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_menu_home:
                        startThActivity(News.class);
                        break;
                    case R.id.nav_menu_categories:
                        startThActivity(AboutActivity.class);

                        break;

                    case R.id.nav_menu_recommend:
                        startThActivity(RecommedActivity.class);
                        break;
                    case R.id.nav_menu_feedback:

                        break;
                    case R.id.nav_menu_setting:

                       // startThActivityByIntent(new Intent(MainActivity.this, SettingActivity.class));
                        break;

                    case R.id.nav_menu_theme:

                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_theme_color, null, false);
                        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.theme_recycler_view);
                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                        recyclerView.setAdapter(themeColorAdapter);
                        android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Choose The Color")
                                .setView(view)
                                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ThemeUtils.setThemeColor( getResources().getColor(themeColorList.get(themeColorAdapter.getPosition()).getColor()));// 不要变换位置
                                        ThemeUtils.setThemePosition(themeColorAdapter.getPosition());
                                        // finish();
                                        new Handler().postDelayed(new Runnable() {
                                            public void run() {
                                                ActivityCollector.getInstance().refreshAllActivity();
                                                // closeHandler.sendEmptyMessageDelayed(MSG_CLOSE_ACTIVITY, 300);
                                            }
                                        }, 100);
                                    }
                                })
                                .show();

                        break;
                }


                menuItem.setChecked(true);

                return true;
            }
        });
    }

    private ArrayList<ThemeColor> themeColorList = new ArrayList<>();
    private ThemeColorAdapter themeColorAdapter = new ThemeColorAdapter();

    private void initChangeTheme(){
        themeColorAdapter = new ThemeColorAdapter();
        themeColorList.add(new ThemeColor(R.color.theme_red_base));
        themeColorList.add(new ThemeColor(R.color.theme_blue));
        themeColorList.add(new ThemeColor(R.color.theme_blue_light));
        themeColorList.add(new ThemeColor(R.color.theme_balck));
        themeColorList.add(new ThemeColor(R.color.theme_teal));
        themeColorList.add(new ThemeColor(R.color.theme_brown));
        themeColorList.add(new ThemeColor(R.color.theme_green));
        themeColorList.add(new ThemeColor(R.color.theme_red));
        themeColorAdapter.setDatas(themeColorList);
        themeColorAdapter.setOnItemClickListener(new EasyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position, Object data) {
                for (ThemeColor themeColor : themeColorList) {
                    themeColor.setChosen(false);
                }
                themeColorList.get(position).setChosen(true);
                themeColorAdapter.notifyDataSetChanged();

            }
        });
    }






    @Override
    public String setActName() {
        return null;
    }





    public void getDataFail() {

    }


}
