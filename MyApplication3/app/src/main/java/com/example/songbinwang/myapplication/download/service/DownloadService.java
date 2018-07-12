package com.example.songbinwang.myapplication.download.service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;


import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.songbinwang.myapplication.download.entry.FileInfo;

/**
 * ���d�������춈������d�΄գ����Ҍ����d�M�Ȃ��f��Activity��
 */
public class DownloadService extends Service {

	public static final String ACTION_START = "ACTION_START";
	public static final String ACTION_STOP = "ACTION_STOP";
	public static final String ACTION_UPDATE = "ACTION_UPDATE";
	public static final String ACTION_FINISHED = "ACTION_FINISHED";
	// �ļ��ı���·��
	public static final String DownloadPath = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/download/";
	public static final int MSG_INIT = 0;

	private Map<Integer, DownloadTask> mTasks = new LinkedHashMap<Integer, DownloadTask>();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// ���Activity�����Ĳ���
		if (ACTION_START.equals(intent.getAction())) {
			FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
			Log.i("test", "START" + fileInfo.toString());
			InitThread initThread = new InitThread(fileInfo);
			DownloadTask.sExecutorService.execute(initThread);			
		} else if (ACTION_STOP.equals(intent.getAction())) {
			FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
			DownloadTask task = mTasks.get(fileInfo.getId());
			if (task != null) {
				// ֹͣ��������
				task.mIsPause = true;
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	// ��InitThread�Q���Ы@ȡFileInfo��Ϣ��Ȼ���_ʼ���d�΄�
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_INIT:
				FileInfo fileInfo = (FileInfo) msg.obj;
				Log.i("test", "INIT:" + fileInfo.toString());
				// �@ȡFileInfo�����_ʼ���d�΄�
				DownloadTask task = new DownloadTask(DownloadService.this, fileInfo, 3);
				task.download();
				// ������������ӵ�������
				mTasks.put(fileInfo.getId(), task);
				// �����������ص�֪ͨ
				Intent intent = new Intent(ACTION_START);
				intent.putExtra("fileInfo", fileInfo);
				sendBroadcast(intent);
				break;
			}
		};
	};

	// ��ʼ�����d�Q�̣��@�����d�ļ�����Ϣ
	class InitThread extends Thread {
		private FileInfo mFileInfo = null;

		public InitThread(FileInfo mFileInfo) {
			super();
			this.mFileInfo = mFileInfo;
		}

		@Override
		public void run() {
			HttpURLConnection conn = null;
			RandomAccessFile raf = null;
			try {
				URL url = new URL(mFileInfo.getUrl());
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5 * 1000);
				conn.setRequestMethod("GET");
				int code = conn.getResponseCode();
				int length = -1;
				if (code == HttpURLConnection.HTTP_OK) {
					length = conn.getContentLength();
				}
				//����ļ�����ΪС��0����ʾ��ȡ�ļ�ʧ�ܣ�ֱ�ӷ���
				if (length <= 0) {
					return;
				}
				// �Д��ļ�·���Ƿ���ڣ��������@����
				File dir = new File(DownloadPath);
				if (!dir.exists()) {
					dir.mkdir();
				}
				// ���������ļ�
				File file = new File(dir, mFileInfo.getFileName());
				raf = new RandomAccessFile(file, "rwd");
				raf.setLength(length);
				// �O���ļ��L��
				mFileInfo.setLength(length);
				// ��FileInfo������f�oHandler
				Message msg = Message.obtain();
				msg.obj = mFileInfo;
				msg.what = MSG_INIT;
				mHandler.sendMessage(msg);
//				msg.setTarget(mHandler);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
					conn.disconnect();
				}
				try {
					if (raf != null) {
						raf.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			super.run();
		}
	}

}