package com.xunj.util;

import javax.servlet.ServletContext;



public class AutoThread extends Thread {

	int wait_time;
	ServletContext application;
	public AutoThread(int waittime,ServletContext application) {
		this.wait_time=waittime*1000;
		this.application = application;
	}
	
	public void run() {
		try {
			Thread.sleep(wait_time);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {

			while (true) {
				RunWorkThread r=new RunWorkThread(application);
				r.start();
				Thread.sleep(wait_time);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
