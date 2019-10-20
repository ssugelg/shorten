package com.shorten.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 콘솔에 단순 프로그래스바 표시
 *  - 입출력 예를 단적으로 보여주기 위해 강제적으로 4000 ms sleep 시킴
 */
@Slf4j
public class ProgressBar {

    public static enum Type {
        GET, SET;
    }

    public static void show(Type type) {
        log.info("Refresh Resource Data");

        char[] animationChars = new char[] { '|', '/', '-', '\\' };

        System.out.println("");

        for (int i = 0; i <= 100; ++i) {
            System.out.print("Processing: " + type.name() + " " + i + "% " + animationChars[i % animationChars.length] + " [" + (new String(new char[i / 2]).replace('\0', '#')) + (new String(new char[50 - i / 2]).replace('\0', '-')) + "]\r");

            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Processing: " + type.name() + " Done! [" + (new String(new char[50]).replace('\0', '#')) + "]\n");
    }
}
