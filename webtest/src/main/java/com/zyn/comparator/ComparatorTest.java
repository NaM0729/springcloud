package com.zyn.comparator;

import java.text.Collator;
import java.util.*;


/**
 * 汉字 排序问题
 */
public class ComparatorTest {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("中", "好", "心", "啊");
        strings.sort(Comparator.comparing(String::toString));
        strings.stream().forEach(System.out::print);
        System.out.println();
        Collator collator = Collator.getInstance(Locale.CHINA);
        strings.sort((source, target) -> collator.compare(source, target));
        strings.stream().forEach(System.out::print);
    }
}
