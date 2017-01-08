package diex.eav.com.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import diex.eav.com.R;
import diex.eav.com.utils.ThemeUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;



/**
 * Created by b508a on 2016/1/25.
 */
public   abstract class BaseActivity extends  BaseFragmentActivity  implements IBaseView{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    /**

     * @param pClass
     */
    protected void startThActivity(Class<?> pClass) {
        Intent _Intent = new Intent();
        _Intent.setClass(this, pClass);
        startActivity(_Intent);

    }

    protected void startThActivityByIntent(Intent pIntent){
        startActivity(pIntent);

    }


    @Override
    public void showProgress(String message) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void cancelProgress() {

    }

    @Override
    public void showTheToast(int resId) {

    }

    @Override
    public void showTheToast(String msg) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onServerFail(String msg) {

    }

    public void backThActivity() {
        finish();

    }

    protected void applyKitKatTranslucency() {

        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);

           // mTintManager.setStatusBarTintResource(R.color.red_base);
            mTintManager.setStatusBarTintColor(ThemeUtils.getThemeColor());
        }

    }

    protected void applyKitKatTranslucency(int res) {

        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);

            // mTintManager.setStatusBarTintResource(R.color.red_base);
            mTintManager.setStatusBarTintColor(res);
        }

    }


    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}
