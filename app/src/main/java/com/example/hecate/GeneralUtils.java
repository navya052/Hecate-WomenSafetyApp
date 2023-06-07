package com.example.hecate;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

public class GeneralUtils {
    public static void changeAppIconDynamically(Context context, boolean isNewIcon) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        if (isNewIcon) {
            pm.setComponentEnabledSetting(
                    new ComponentName(context,
                            "com.example.hecate.SplashActivityAlias1"), //com.example.dummy will be your package
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);

            pm.setComponentEnabledSetting(
                    new ComponentName(context,
                            "com.example.hecate.SplashActivityAlias"),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
        } else {
            pm.setComponentEnabledSetting(
                    new ComponentName(context,
                            "com.example.hecate.SplashActivityAlias1"),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

            pm.setComponentEnabledSetting(
                    new ComponentName(context,
                            "com.example.hecate.SplashActivityAlias"),
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        }
    }
}