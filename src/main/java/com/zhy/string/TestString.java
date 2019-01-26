package com.zhy.string;

import java.util.Arrays;

public class TestString {

    //字符串反转 首尾对调
    public static void swap(char[] chars, int front, int end) {
        char tmp;
        while(front < end) {
            tmp = chars[end];
            chars[end] = chars[front];
            chars[front] = tmp;
            front++;
            end--;
        }
    }

    //判断两个字符串组成是否相同 排序
    public static boolean compareStr(String s1, String s2) {
        byte[] b1 = s1.getBytes(), b2 = s2.getBytes();
        Arrays.sort(b1);
        Arrays.sort(b2);
        s1 = new String(b1);
        s2 = new String(b2);
        return s1.equals(s2);
    }

    //判断两个字符串组成是否相同 转成asc ii 用数组记录出现次数
    public static boolean compareStr2(String s1, String s2) {
        byte[] b1 = s1.getBytes(), b2 = s2.getBytes();
        int count[] = new int[256];
        for (int i = 0; i < 256; i++) {
            count[i] = 0;
        }
        for (byte aB1 : b1) {
            count[aB1 - '0']++;
        }
        for (byte aB2 : b2) {
            count[aB2 - '0']--;
        }
        for (int i : count) {
            if (i != 0 ) return false;
        }
        return true;
    }

    //删除字符串中重复的字符
    public static String removeDuplicate(String s) {
        char[] arr = s.toCharArray();
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            if (arr[i] == '\0') {
                continue;
            }
            for (int j = i + 1; j < len; j++) {
                if (arr[j] == '\0') continue;;
                if (arr[i] == arr[j]) arr[j] = '\0';
            }
        }
        int length = 0;
        for (int i = 0; i < len; i++) {
            if (arr[i] != '\0') arr[length++] = arr[i];
        }

        return new String(arr, 0 , length);
    }

    //字符串转数字
    public static void string2Int(String str) {
        double intData = 10;
        double floatData = 0.1;
        boolean flag = true;    //记录符号
        int count = 0;           //记录小数点个数
        int index = 0;           //记录小数点下标位置
        float leftRes = 0;       //小数点左边数
        float rightRes = 0;    //小数点右边数
        float result = 0;

        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '.') {
                count++;
                index = i;
            }
        }

        if (count > 1) {
            // 非法
            return;
        }

        if (count == 0 && index == 0) {
            //不含小数
            index = c.length;
        }
        //处理整数部分
        for (int i = 0; i < index; i++) {
            char temp = c[i];
            if (temp == '-') {
                flag = false;
                continue;
            }
            if (temp == '+') {
                flag = true;
            }

            double tempInt = temp - '0';
            if (tempInt >= 0 && tempInt <= 9) {
                leftRes = (float) (leftRes * intData + tempInt);
            }
        }

        //小数部分
        for (int j = c.length - 1; j > index; j--) {
            char temp = c[j];
            double tempDecimal = temp - '0';
            if (tempDecimal >= 0 && tempDecimal <= 9) {
                rightRes = (float) (rightRes * floatData + tempDecimal * floatData);
            }
        }

        result = leftRes + rightRes;

        if (!flag) result = (-1) * result;
    }

    //统计一行字符里有多少单词
    public static int getWordCounts(String string) {
        int count = 0;
        int word = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
                word = 0;
            } else if (word == 0) {
                word = 1;
                count++;
            }
        }
        return count;
    }

    private void testEquals() {

        String s = new String("abc");
        char[] c = {'a', 'b', 'c'};
        System.out.println( s.equals(c));

        String s1 = "abc";
        System.out.println(s1.equals(c));

        System.out.println(s1.equals(s));
    }
}
