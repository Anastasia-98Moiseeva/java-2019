package com.company;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils {

    /* Scans object "from" for all getters. If object "to"

     * contains correspondent setter, it will invoke it
     *
     * to set property value for "to" which equals to the property
     *
     * of "from".
     *
     * <p/>
     *
     * The type in setter should be compatible to the value returned
     *
     * by getter (if not, no invocation performed).
     *
     * Compatible means that parameter type in setter should
     *
     * be the same or be superclass of the return type of the getter.
     *
     * <p/>
     *
     * The method takes care only about public methods.
     *
     * @param to Object which properties will be set.
     *
     * @param from Object which properties will be used to get values.*/

    public static void assign(Object to, Object from) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if((to == null)||(from == null)) return;
        List<Method> getters = getMethods(from,"get");
        List<Method> setters = getMethods(to,"set");

        for (Method getter : getters) {

            String getterName = getter.getName().substring(3);
            boolean getType = getter.getName().contains("get");

            for (Method setter : setters) {

                String setterName = setter.getName().substring(3);
                boolean setType = setter.getName().contains("set");

                if ((setterName.equals(getterName))&&

                        (!getter.getReturnType().equals(void.class))&&
                        (setter.getParameterTypes().length == 1) &&(setType & getType)) {

                    try {
                        setter.invoke(to,getter.invoke(from));

                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private static List<Method> getMethods(Object object,String pattern) {

        Method[] methods = object.getClass().getMethods();
        List<Method> ListOfMethods = new ArrayList<>();

        for (Method method : methods) {

            if (method.getName().toLowerCase().startsWith(pattern.toLowerCase())) {
                ListOfMethods.add(method);
            }
        }
        return ListOfMethods;
    }
}
