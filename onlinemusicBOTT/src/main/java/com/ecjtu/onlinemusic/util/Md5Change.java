package com.ecjtu.onlinemusic.util;

import org.apache.commons.codec.digest.DigestUtils;

/*
 * @description: md5基础上的加密
 * @author: 何江涛
 * @create: 2022-07-01
 * @version: 1.0
 */
public class Md5Change {
    static public String encryption(String originalString) {
        String md5String = DigestUtils.md5Hex(originalString);
        //  cxfdh0cd98f00b204e9800998ecf842d41d87e
        // 截取的字符的下标
        String letter = "c";
        // 截取的最后下标
        int last = 3;

        // 追加字符串数组
        String[] arr = {"45nkjbre", "ffsad", "8kkaw54sfdf", "df9", "cxfdh0", "76jhj", "w4", "p9xs"};
        int first;

        // 第一个截取的下标
        first = md5String.indexOf(letter);
        // 最后一个截取的下标
        last = md5String.length() - last;

        // 第一个下标小于零（没找到字符）
        if (first < 0) {
            first = 7;
        }
        // 最后下标超过md5长度
        if (last > md5String.length()) {
            last = md5String.length() - 5;
        }
        // 第一个下标大于最后下标
        if (first > last) {
            int transfer = first;
            first = last;
            last = transfer;
        }

        // 追加的字符串 数组下标
        int append = md5String.codePointAt(5) % arr.length;

        //转换位置 追加字符串    最终的加密字符串
        String tranString = arr[append] + md5String.substring(first, last) + md5String.substring(0, first) + md5String.substring(last);
        System.err.println("加密前: " + originalString);
        System.err.println("加密md5: " + md5String);
        System.err.println("加密md5Change: " + tranString);

        return tranString;


    }
}
