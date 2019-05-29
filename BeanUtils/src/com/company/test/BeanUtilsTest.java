package com.company.test;

import com.company.BeanUtils;
import com.company.FromObject;
import com.company.ToObject;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class BeanUtilsTest {
    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        FromObject fromObject = new FromObject(123, "str");
        ToObject toObject = new ToObject();
        BeanUtils.assign(toObject, fromObject);
        assertEquals(fromObject.getNum(), toObject.getNum());
        assertEquals(fromObject.getStr(), toObject.getStr());
    }
}