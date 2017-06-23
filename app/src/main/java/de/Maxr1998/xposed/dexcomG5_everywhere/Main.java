package de.Maxr1998.xposed.dexcomG5_everywhere;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

public class Main implements IXposedHookZygoteInit, IXposedHookLoadPackage {

    public static final String PACKAGE_NAME = "com.dexcom.cgm.region1.mgdl";
    public static final String BASE_NAME = "com.dexcom.cgm";


    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {

    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lPParam) throws Throwable {
        if (lPParam.packageName.equals(PACKAGE_NAME)) {
            try {
                Class activity = findClass(BASE_NAME + ".activities.AppCompatabilityActivity", lPParam.classLoader);

                findAndHookMethod(activity, "performAppValidity", new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        callMethod(param.thisObject, "goDirectlyToNextActivity");
                        return null;
                    }
                });

                findAndHookMethod(activity, "isRooted", new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        return false;
                    }
                });
            } catch (Throwable t) {
                log(t);
            }
        }
    }
}