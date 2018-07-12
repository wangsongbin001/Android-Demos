package com.example.songbinwang.myapplication.download.util;

import java.util.HashMap;
import java.util.Map;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.songbinwang.myapplication.R;
import com.example.songbinwang.myapplication.download.MainActivity;
import com.example.songbinwang.myapplication.download.entry.FileInfo;
import com.example.songbinwang.myapplication.download.service.DownloadService;

public class NotificationUtil {

	private Context mContext;
	private NotificationManager mNotificationManager = null;
	private Map<Integer, Notification> mNotifications = null; 
	
	public NotificationUtil(Context context) {
		this.mContext = context;
		// ���ϵͳ֪ͨ������
		mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// ����֪ͨ�ļ���
		mNotifications = new HashMap<Integer, Notification>();
	}
	/**
	 * ��ʾ֪ͨ��
	 * @param fileInfo
	 */
	public void showNotification(FileInfo fileInfo) {
		// �ж�֪ͨ�Ƿ��Ѿ���ʾ
		if(!mNotifications.containsKey(fileInfo.getId())){
			Notification notification = new Notification();
			notification.tickerText = fileInfo.getFileName() + "��ʼ����";
			notification.when = System.currentTimeMillis();
			notification.icon = R.mipmap.ic_launcher;
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			
			// ���֪֮ͨ�����ͼ
			Intent intent = new Intent(mContext, MainActivity.class);
			PendingIntent pd = PendingIntent.getActivity(mContext, 0, intent, 0);
			notification.contentIntent = pd;
			
			// ����Զ����ͼRemoteViews����
			RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.d_notification);
			// ����Զ����ͼ�����ÿ�ʼ����¼�
			Intent intentStart = new Intent(mContext, DownloadService.class);
			intentStart.setAction(DownloadService.ACTION_START);
			intentStart.putExtra("fileInfo", fileInfo);
			PendingIntent piStart = PendingIntent.getService(mContext, 0, intentStart, 0);
			remoteViews.setOnClickPendingIntent(R.id.start_button, piStart);
			
			// ����Զ����ͼ�����ý�������¼�
			Intent intentStop = new Intent(mContext, DownloadService.class);
			intentStop.setAction(DownloadService.ACTION_STOP);
			intentStop.putExtra("fileInfo", fileInfo);
			PendingIntent piStop = PendingIntent.getService(mContext, 0, intentStop, 0);
			remoteViews.setOnClickPendingIntent(R.id.stop_button, piStop);
			// ����TextView���ļ�������
			remoteViews.setTextViewText(R.id.file_textview, fileInfo.getFileName());
			// ����Notification����ͼ
			notification.contentView = remoteViews;
			// ����Notification֪ͨ
			mNotificationManager.notify(fileInfo.getId(), notification);
			// ��Notification��ӵ�������
			mNotifications.put(fileInfo.getId(), notification);
		}
	}
	/**
	 * ȡ��֪ͨ��֪ͨ
	 */
	public void cancelNotification(int id) {
		mNotificationManager.cancel(id);
		mNotifications.remove(id);
	}
	/**
	 * ����֪ͨ��������
	 * @param id ��ȡNotification��id
	 * @param progress ��ȡ�Ľ���
	 */
	public void updataNotification(int id, int progress) {
		Notification notification = mNotifications.get(id);
		if (notification != null) {
			// �޸Ľ���������
			notification.contentView.setProgressBar(R.id.progressBar2, 100, progress, false);
			mNotificationManager.notify(id, notification);
		}
	}
}
