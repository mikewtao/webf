package com.github.mikewtao.webf.mvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
/**
 * 导入模块 
 *   1.启动的时候    2.定时扫描
 *   Module - controller --handler
 *    --URI ---URIMapping
 *   
 *
 */
public class ModuleManger implements Manager{

	public static List<Module> modulelist = new ArrayList<>();

	// 定时任务每隔30秒，扫描classpath
	public static void loadModule() {

		final TimerTask timer = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub

			}
		};
		new Timer() {
			@Override
			public void scheduleAtFixedRate(TimerTask task, Date firstTime, long period) {
			}

		}.schedule(timer, new Date());;
	}

	public static Module InitModule() {
		return null;
	}

	@Override
	public void add(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void find(String key) {
		// TODO Auto-generated method stub
		
	}
}
