package com.sicau.platform;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        int[][] arr = {{5,2},{2,1},{3,0}};
        Arrays.sort(arr, (o1, o2)->{
          return o1[0] - o2[0];
        });
        System.out.println(arr);
    }
}
