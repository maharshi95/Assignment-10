package com.mars.findbugs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maharshigor on 22/07/16.
 */
public class ThreadTest {
	public static void main(String[] args) {
		Class c = Thread.class;
		while (c != null) {
			System.out.println (c);
			c = c.getSuperclass ();
		}
		Thread t;
		Runnable runnable = new Runnable ( ) {
			public void run() {

			}
		};
		System.out.println (runnable.getClass ());
	}
	public void test() {
		MyRunnable runnable = new MyRunnable ();
		ControlThread controlThread = new ControlThread ("My Thread",runnable);
		Thread thread = new Thread (runnable);
		Sample s = new Sample ();

		controlThread.run ();
		s.run ();
		(new Thread ()).run ();
		thread.run ();
		runnable.run ();
	}
}

class ControlThread extends Thread {
	private String name;
	public ControlThread(String name,Runnable runnable) {
		super(runnable);
		this.name = name;
	}

	@Override
	public void run() {
		Runnable r = new Runnable() {
			public void run() {
				System.out.println ("Msg inside runnable");
			}
		};
		r.run ();
	}

	public String getThreadName() {
		return name;
	}

	public void setThreadName(String name) {
		this.name = name;
	}
}

class Sample {
	private int value;
	public void run() {
		value = 12;
	}
}

class MyRunnable implements Runnable {
	public void run() {
		System.out.println ("hey there, i am running");
	}
}
