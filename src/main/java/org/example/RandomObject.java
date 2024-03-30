package org.example;

import java.lang.reflect.Field;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;

public class RandomObject<T> {
    private static long seed = System.nanoTime();
    private static long Next() {
        seed ^= (seed << 21);
        seed ^= (seed >>> 35);
        seed ^= (seed << 4);
        return seed;
    }
    public static <T> T Generate(Class<T> tClass) throws Exception {
        if (tClass.isArray())
            return tClass.cast(RandomArray(tClass.getComponentType()));
        T obj;
        try {
            obj = tClass.getDeclaredConstructor().newInstance();
        }
        catch (Exception ex) {
            obj = switch (tClass.getName()) {
                case "java.lang.Integer" -> obj = tClass.getDeclaredConstructor(int.class).newInstance((int) Next());
                case "java.lang.Boolean" -> obj = tClass.getDeclaredConstructor(boolean.class).newInstance(Next() % 2 == 0);
                case "java.lang.Double" -> obj = tClass.getDeclaredConstructor(double.class).newInstance((double) Next() / (double) Next());
                case "java.lang.Long" -> obj = tClass.getDeclaredConstructor(long.class).newInstance(Next());
                case "java.lang.Short" -> obj = tClass.getDeclaredConstructor(short.class).newInstance((short) Next());
                case "java.lang.Char" -> obj = tClass.getDeclaredConstructor(char.class).newInstance((char) Next());
                case "java.lang.Byte" -> obj = tClass.getDeclaredConstructor(byte.class).newInstance((byte) Next());
                case "java.lang.Float" -> obj = tClass.getDeclaredConstructor(float.class).newInstance((float) Next() / (float) Next());
                default -> throw new Exception("Object or component must have constructor without parameters\n" + ex.getMessage());
            };
            return obj;
        }
        Field[] fields = tClass.getDeclaredFields();

        for (Field field : fields)
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                if (field.getType().isPrimitive()) {
                    switch (field.getType().getName()) {
                        case "int" -> field.set(obj, (int) Next());
                        case "boolean" -> field.set(obj, Next() % 2 == 0);
                        case "double" -> field.set(obj, (double) Next() / (double) Next());
                        case "short" -> field.set(obj, (short) Next());
                        case "byte" -> field.set(obj, (byte) Next());
                        case "float" -> field.set(obj, (float) Next() / (float) Next());
                        case "char" -> field.set(obj, (char) Next());
                        default -> field.set(obj, Next());
                    }
                } else field.set(obj, Generate(field.getType()));
            }
        return obj;
    }
    private static <T> T[] RandomArray(Class<T> tClass) throws Exception {
        T[] arr = (T[])Array.newInstance(tClass, Math.abs((short)Next()));
        for (int i = 0; i < arr.length; ++i)
            arr[i] = Generate(tClass);
        return arr;
    }
    public static void SetSeed(long newSeed) {
        seed = newSeed;
    }
    public static long GetSeed() {
        return seed;
    }
}