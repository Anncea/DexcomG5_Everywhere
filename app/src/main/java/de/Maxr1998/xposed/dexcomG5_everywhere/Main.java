package de.Maxr1998.xposed.dexcomG5_everywhere;

import android.os.Build;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.setStaticObjectField;

public class Main implements IXposedHookZygoteInit, IXposedHookLoadPackage {

    public static final String PKG_NAME_R1_MGDL = "com.dexcom.cgm.region1.mgdl";
    public static final String PKG_NAME_R1_MMOL = "com.dexcom.cgm.region1.mmol";
    public static final String PKG_NAME_R2_MGDL = "com.dexcom.cgm.region2.mgdl";
    public static final String PKG_NAME_R2_MMOL = "com.dexcom.cgm.region2.mmol";

    public static final String BASE_NAME = "com.dexcom.cgm";


    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {

    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lPParam) throws Throwable {
        if (lPParam.packageName.equals(PKG_NAME_R1_MGDL) || lPParam.packageName.equals(PKG_NAME_R1_MMOL) || lPParam.packageName.equals(PKG_NAME_R2_MGDL) || lPParam.packageName.equals(PKG_NAME_R2_MMOL)) {
            try {
                setStaticObjectField(Build.class, "MANUFACTURER", "motorola");
                setStaticObjectField(Build.class, "MODEL", "Nexus 6");

                Class activity = findClass(BASE_NAME + ".activities.AppCompatabilityActivity", lPParam.classLoader);
                Class loginActivity = findClass(BASE_NAME + ".activities.LoginActivity", lPParam.classLoader);


                findAndHookMethod(activity, "isRooted", new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        return false;
                    }
                });

                findAndHookMethod(loginActivity, "isUsernameValid", String.class, new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        return true;
                    }
                });

                findAndHookMethod(loginActivity, "isPasswordValid", String.class, new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        return true;
                    }
                });
            } catch (Throwable t) {
                log(t);
            }
        }
    }
}