package diex.eav.com.base;

import android.content.Context;

/**
 * 公共View接口
 *
 * @author Ht
 */
public interface IBaseView {




    void showProgress(String message);


    void showProgress();


    void cancelProgress();


    void showTheToast(int resId);


    void showTheToast(String msg);


    Context getContext();

     void onServerFail(String msg);
}
