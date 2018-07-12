package com.example.songbinwang.myapplication.download.db;

import com.example.songbinwang.myapplication.download.entry.ThreadInfo;

import java.util.List;


/**
 * ����������Ľӿ��
 *
 */
public interface ThreadDAO {
	// ����Q��
	public void insertThread(ThreadInfo info);
	// �h���Q��
	public void deleteThread(String url);
	// ���¾Q��
	public void updateThread(String url, int thread_id, int finished);
	// ��ԃ�Q��
	public List<ThreadInfo> queryThreads(String url);
	// �Д�Q���Ƿ����
	public boolean isExists(String url, int threadId);
}
