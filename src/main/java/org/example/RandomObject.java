package org.example;

import java.lang.reflect.Field;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

public class RandomObject<T> {
    static long seed = System.nanoTime();
    private static long Next() {
        seed ^= (seed << 21);
        seed ^= (seed >>> 35);
        seed ^= (seed << 4);
        return seed;
    }
    public static <T> T Generate(Class<T> tClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (tClass.isPrimitive() || tClass.isEnum())
            return tClass.cast(Next());
        else if (tClass.isArray())
            return tClass.cast(RandomArray(tClass.getComponentType()));
        T obj = tClass.getDeclaredConstructor().newInstance();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields)
            if (field.get(obj) == null)
                field.set(obj, Generate(field.getType()));
        return obj;
    }
//    private static <P> P Random(Class<P> pClass) {
//        return pClass.cast(Next());
//    }
    private static <T> T[] RandomArray(Class<T> tClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        T[] arr = (T[])Array.newInstance(tClass, Math.abs((short)Next()));
        for (int i = 0; i < arr.length; ++i)
            arr[i] = Generate(tClass);
        return arr;
    }
}