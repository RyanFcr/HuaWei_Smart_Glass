package com.huawei.audiodevicekit.mvp.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Build;

import com.huawei.audiodevicekit.mvp.impl.ABaseUi;


/**
 * Created by Felix on 2017/8/16.
 * 接口回调->判断Ui是否销毁的工具类
 */

public class LifeCycleUtil {


    public static boolean isActivityDestroyed(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return true;
        }
        boolean isDestroyed = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed();
        return isDestroyed;
    }

    public static boolean isUiDestroyed(ABaseUi ui) {
        return ui == null || ui.getContext() == null;
    }


    public static boolean isContextDestroyed(Context context) {
        if (context != null) {
            if (context instanceof Activity) {
                return isActivityDestroyed((Activity) context);
            } else {
                return !(context instanceof Service);
            }
        }
        return true;
    }

}
