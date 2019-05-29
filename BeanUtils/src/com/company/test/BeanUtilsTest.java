package com.company.test;

import com.company.BeanUtils;
import com.company.FromObject;
import com.company.ToObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeanUtilsTest {
    @Test
    public void test() {
        FromObject fromObject = new FromObject(11, "hello");
        ToObject toObject = new ToObject();
        BeanUtils.assign(toObject, fromObject);
        assertEquals(11, toObject.checkNum());
        assertEquals("hello", toObject.checkStr());
    }
}