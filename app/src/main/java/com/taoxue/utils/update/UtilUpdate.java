package com.taoxue.utils.update;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.taoxue.app.AppData;

import java.io.File;

public class UtilUpdate {
	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉 sd卡中创建一个目标文件
	 * @param name
	 * @return Author: 14052012 zyn Date: 2014年11月7日 下午3:10:35
	 */
	public static String createSDCardDir(String name) {
		File sdcardDir = Environment.getExternalStorageDirectory();
		String path = sdcardDir.getPath() + "/MUDOWN";
		File file = null;
		try {
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {

				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				file = new File(dir + File.separator + name);
				if (file.exists()) {
					file.delete();
				}
				file.createNewFile();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return file.getPath();
	}

	public static void installApk(String urlPath, Context context) {
		Intent apkIntent = new Intent();
		apkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		apkIntent.setAction(Intent.ACTION_VIEW);
		File apkFile = new File(urlPath);
		Log.i("jone", "apk length " + apkFile.length() + "");
		Uri uri = Uri.fromFile(apkFile);
		apkIntent.setDataAndType(uri, "application/vnd.android.package-archive");
		context.startActivity(apkIntent);
	};

	/**
	 * 判断是否更新,是提示更新,不是就提示已经是最新版本
	 * @param context
     */
	public static void judgeUpdate( Context context) {
		IsUpdateBean bean = new IsUpdateBean();
		IsUpdateBean.ResultBean resultBean= new IsUpdateBean.ResultBean();
		resultBean.setIsUpdate("1");
		resultBean.setUrl("http://60.167.138.16/imtt.dd.qq.com/16891/C24F75D84D04B4C77A475CBD022FD918.apk?mkey=59083df3d5ef9229&f=b601&c=0&fsname=com.qiyi.video_8.3_80850.apk&csr=1bbd&p=.apk");
		resultBean.setContent("更新日志:\n1.测试升级\n2.测试升级\n3.测试升级\n" +
				"4.测试升级\n" +
				"5.测试升级\n" +
				"6.测试升级");
		bean.setResult(resultBean);

		AppData.apkURL = bean.getResult().getUrl();
		UpdateManager manager = new UpdateManager(context, bean);
		manager.checkUpdate(Integer.valueOf(bean.getResult().getIsUpdate()));// 检查软件更新
	}

}
