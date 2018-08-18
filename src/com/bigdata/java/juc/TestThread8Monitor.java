package com.bigdata.java.juc;
/**
 * 题目：判断打印的"one" or "two"
 * 
 * 1.两个普通同步方法，两个线程，标准打印，打印  one  two
 * 2.新增Thread.sleep(3000) 方法给getOne(),打印？  // one  two
 * 3.新增普通方法getThree()，打印?     // three   one two
 * 4.两个普通的同步方法，两个Number对象，打印     //two one
 * 5.修改getOne()为静态同步方法，一个Number对象，打印     // two  one
 * 6.修改getOne()、getTwo()方法均为静态同步方法，一个Number对象，打印？    //one  two
 * 7.getOne()为静态同步方法，getTwo()方法为非静态同步方法
 */
public class TestThread8Monitor {
	public static void main(String[] args) {
		Number number=new Number();
		Number number2=new Number();
		new Thread() {
			@SuppressWarnings("static-access")
			public void run() {
				number.getOne();
			}
		}.start();
		
		new Thread() {
			public void run() {
				//number.getTwo();
				number2.getTwo();
			}
		}.start();
		
		/*new Thread() {
			public void run() {
				number.getThree();
			}
		}.start();*/
	}
}
class Number{
	public static synchronized void getOne() {
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("one");
	}
	
	public synchronized void getTwo() {
		System.out.println("two");
	}
	
	public void getThree() {
		System.out.println("three");
	}
}