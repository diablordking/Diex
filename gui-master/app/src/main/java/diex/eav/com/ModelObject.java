package diex.eav.com;

import diex.eav.com.R;
/**
 * Created by ALP on 18.12.2016.
 */


public enum ModelObject {

    RED(R.string.email, R.layout.homepage),
    BLUE(R.string.email, R.layout.homepage),
    GREEN(R.string.email, R.layout.homepage);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
