package diex.eav.com.adapter;

/**
 * Created by ALP on 18.12.2016.
 */
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import diex.eav.com.R;
import diex.eav.com.ModelObject;
import diex.eav.com.viewimpl.HomeActivity;
import diex.eav.com.viewimpl.MainActivity;
import diex.eav.com.viewimpl.MainActivity.*;

import static android.content.ContentValues.TAG;

public class CustomPagerAdapter extends PagerAdapter {

    Context getmContext() {
        return mContext;
    }

    private Context mContext;

    public CustomPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        ModelObject modelObject = ModelObject.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);

       View v=layout.findViewById(R.id.deneme);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "fdsfdfsd ");

            }
        });
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }


    @Override
    public int getCount() {
        return ModelObject.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        ModelObject customPagerEnum = ModelObject.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }

    public Context getItem() {
        return mContext;
    }
}