package com.zyn.redis.DataStructure;

import com.zyn.WebTestRunTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zyn
 * @Description
 * @date 2020-07-29 9:31
 * <p>
 * 不是看到希望才会去坚持，而是坚持了才会看到希望
 */
public class ZsetTestTest extends WebTestRunTests {

    @Autowired
    ZsetTest zsetTest;

    @Test
    public void testAdd() {
//        zsetTest.add("1.2.2.4");
    }

    @Test
    public void addAndExpire() {
        zsetTest.addAndExpire();
    }
    @Test
    public void getValue() {
        zsetTest.getValue();
    }
}