package com.zyn.tail;

/**
 * 线程定时开启 和 关闭
 * zyn 2020-12-02
 */
public class ThreadTest {

    public static void main(String[] args) throws InterruptedException {
        String str = "sdfsfsdf";
        if (str.lastIndexOf("/") == str.length() - 1) {
            System.out.println("1");
        } else {
            str += "/";
            System.out.println(str);
        }
    }
}
