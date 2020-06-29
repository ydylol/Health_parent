package com.rendiyu.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Maptest {


    public static void main(String[] args) {
        Map map =new HashMap<>();
        map.put("1",1);
        map.put("2",2);
        map.put("3",3);
        map.put("4",4);
        map.put("5",5);
        map.put("6",6);
        System.out.println(map);


        Set set1 = map.keySet();
        System.out.println(set1);

        Set set = map.entrySet();
        for (Object o : set) {
            System.out.println(o);
        }

    }
}
