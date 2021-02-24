package com.zyn.comparator;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class StringComparator implements Comparator<String> {

    //当前环境设置成 CHINA 中文。  在前面实例化对象
    private Collator collator = Collator.getInstance(Locale.CHINA);

    @Override
    public int compare(String o1, String o2) {

        return collator.compare(o1, o2);
    }
}
