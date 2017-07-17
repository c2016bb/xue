package com.taoxue.utils.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.taoxue.R;

import java.lang.reflect.Method;


/**
 * 权限工具类
 */
public class UtilPermission {


    public static void needPermission(Fragment context, int reqCode, String... permissions) {
        checkPermission(context, reqCode, permissions);
    }


    public static void needPermission(Context context, int reqCode, String... permissions) {
        checkPermission(context, reqCode, permissions);
    }

    /**
     * 判断是否有权限
     *
     * @param context
     * @param permissionType
     * 以下是用法
    PermissionUtil.needPermission(mActivity, code, Manifest.permission.CAMERA);
    private final int code = 3;
     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
     PermissionUtil.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
     }

     if (!PermissionUtil.judgePermisson(this,"ACCESS_FINE_LOCATION")) return;
     */

    public static boolean judgePermisson(final Context context, String permissionType) {
        String str = null;
        PackageManager packageManager = context.getPackageManager();
        int permission = packageManager.checkPermission( permissionType, context.getPackageName());
        if (PackageManager.PERMISSION_GRANTED == permission) {
            return true; //有这个权限
        } else {
            if (permissionType.contains("READ_PHONE_STATE")) {
                str = "访问电话状态";
            }
            if (permissionType.contains("WRITE_EXTERNAL_STORAGE")) {
                str = "允许程序写入外部存储";
            }
            if (permissionType.contains("CAMERA")) {
                str = "相机权限";
            }
            if (permissionType.contains("ACCESS_FINE_LOCATION")) {
                str = "定位";
            }
            dialog(context, str);
            return false; //没有这个权限
        }
    }

    /**
     * 弹出设置权限dialog
     *
     * @param context
     * @param message
     */
    public static void dialog(final Context context, String message) {

        LayoutInflater inflaterDl = LayoutInflater.from(context);
        View view = inflaterDl.inflate(R.layout.item_dialog_permission_notice, null);
        final AlertDialog dialog = new AlertDialog.Builder(context, R.style.dialog_custom_style).create();
        dialog.show();
        dialog.getWindow().setContentView(view);

        View btnCancel = view.findViewById(R.id.btn_cancel);
        View btnCommit = view.findViewById(R.id.btn_ok);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
        tvMessage.setText("\"" + message + "权限\"未获得,请在系统设置或者安全中心-->\"权限设置\"中添加相应权限");
        tvOk.setText("设置");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName())); // 根据包名打开对应的设置界面
                context.startActivity(intent);

            }
        });
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.setCancelable(false);

    }


    @TargetApi(Build.VERSION_CODES.M)
    private static void checkPermission(Object context, int reqCode, String... permissions) {

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            //6.0以下
//            executeSuccessResult(context, reqCode);
//            return;
//        }
        boolean granted = hasPermission(context, permissions);//检查权限
        if (granted) {
            executeSuccessResult(context, reqCode);
        } else {
            if (context instanceof Fragment) {
                ((Fragment) context).requestPermissions(permissions, reqCode);
            } else {
                ((Activity) context).requestPermissions(permissions, reqCode);
            }
        }
    }

    private static void executeSuccessResult(Object context, int reqCode) {
        Method successMethod = getTargetMethod(context, reqCode, PermissionSuccess.class);
        try {
            successMethod.invoke(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeFailResult(Object context, int reqCode) {
        Method successMethod = getTargetMethod(context, reqCode, PermissionFail.class);
        try {
            successMethod.invoke(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Method getTargetMethod(Object context, int reqCode, Class annotation) {
        Method[] declaredMethods = context.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (!method.isAccessible()) {
                method.setAccessible(true); //私有的方法必须强制
            }
            //判断方法上是否使用了目标注解
            boolean annotationPresent = method.isAnnotationPresent(annotation);
            if (annotationPresent) {
                if (isTargetMethod(method, reqCode, annotation)) { //比较requestCode是否相等
                    return method;
                }
            }
        }
        return null;
    }

    private static boolean isTargetMethod(Method method, int reqCode, Class cls) {
        if (cls.equals(PermissionSuccess.class)) {
            return reqCode == method.getAnnotation(PermissionSuccess.class).requestCode();
        } else if (cls.equals(PermissionFail.class)) {
            return reqCode == method.getAnnotation(PermissionFail.class).requestCode();
        }
        return false;
    }


    private static boolean hasPermission(Object context, String... permissions) {
        Activity activity = null;
        if (context instanceof Fragment) {
            activity = ((Fragment) context).getActivity();
        } else {
            activity = (Activity) context;
        }
        for (String permission : permissions) {
            int granted = ContextCompat.checkSelfPermission(activity, permission);
            if (granted == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    public static void onRequestPermissionsResult(Fragment context, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        handlePermissionsResult(context, requestCode, permissions, grantResults);
    }


    public static boolean onRequestPermissionsResult(Activity context, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       return handlePermissionsResult(context, requestCode, permissions, grantResults);
    }

    public static boolean handlePermissionsResult(Object context, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean permissionGranted = true;
        for (int grant : grantResults) {
            if (grant == PackageManager.PERMISSION_DENIED) {
                permissionGranted = false;
                break;
            }
        }
        if (permissionGranted) {
            //获得权限
            executeSuccessResult(context, requestCode);
            return true;
        } else {
            //权限被用户拒绝
            executeFailResult(context, requestCode);
            return false;
        }
    }


}
