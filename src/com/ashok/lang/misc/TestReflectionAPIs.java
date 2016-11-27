package com.ashok.lang.misc;

import com.ashok.lang.geometry.Line;
import com.ashok.lang.geometry.Point;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.math.Matrix;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem Name: Reflection API's
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class TestReflectionAPIs {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static Map<String, Class> primitiveWrapperMap = new HashMap<String, Class>();

    static {
        primitiveWrapperMap.put("int", Integer.class);
        primitiveWrapperMap.put("long", Long.class);
        primitiveWrapperMap.put("double", Double.class);
        primitiveWrapperMap.put("float", Float.class);
        primitiveWrapperMap.put("boolean", Boolean.class);
        primitiveWrapperMap.put("char", Character.class);
        primitiveWrapperMap.put("byte", Byte.class);
        primitiveWrapperMap.put("void", Void.class);
        primitiveWrapperMap.put("short", Short.class);
    }

    public static void main(String[] args) throws IOException,
            IllegalAccessException, InvocationTargetException,
            InstantiationException {

        try {
            reflections();
        } finally {
            out.close();
        }
    }

    private static void reflections() throws IOException,
            IllegalAccessException, InstantiationException,
            InvocationTargetException {
        Point a = new Point(10, 20), b = new Point(13, 23);
        Line line = new Line(a, b);
        long[][] ar = new long[][]{{1, 2}, {2, 3}};
        Matrix matrix = new Matrix(ar);

        int value = 10;
        Object vo = value;
        out.println(vo.getClass().getSimpleName());
        out.println(vo.getClass().getTypeName());
        out.println(a.getClass().getTypeName());
        out.flush();

        Object[] objects = {matrix, a, line};

        for (Object object : objects) {
            out.println(object.getClass().getSimpleName());
            Class c = object.getClass();

            // Object copy = instantiate(object.getClass());
            // out.println("Object created: " + copy.getClass().getSimpleName()
            // + ": " + copy);

            Method[] methods = object.getClass().getDeclaredMethods();
            // out.println(object.getClass().getMethods());
            // out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            // out.flush();
            out.println("Which method you want to execute?");
            int i = 0;
            for (Method method : methods) {
                out.println((i++) + "\t" + method);
            }
            out.flush();
            Method method = methods[in.readInt()];
            method.setAccessible(true);
            out.println(method.getName()
                    + " executed and result is: "
                    + method.invoke(object,
                    getParameters(method.getParameterTypes())));
            out.flush();

            Field[] fields = object.getClass().getDeclaredFields();
            out.println("Which field value you want to know?");
            i = 0;

            for (Field field : fields) {
                out.println((i++) + ":\t" + field.getType().getSimpleName()
                        + " " + field.getName());
            }

            out.flush();
            Field field = fields[in.readInt()];
            field.setAccessible(true);
            out.println("Value for field: " + field.getName() + " is: "
                    + field.get(object));

            out.println("You want to update the value? 0 for no and other values for yes");
            out.flush();
            int update = in.readInt();

            if (update != 0) {
                field.setAccessible(true);
                field.set(object, instantiate(field.getType()));
                out.println("New value for " + field.getName() + " is "
                        + field.get(object));
                out.flush();
            }
        }
    }

    private static Object invokeMethod(Method method)
            throws InvocationTargetException, IllegalAccessException,
            InstantiationException, IOException {
        Class[] types = method.getParameterTypes();
        if (types.length == 0)
            return method.invoke(null, new Object[0]);

        return method.invoke(getParameters(types));
    }

    private static Object instantiate(Class clazz)
            throws IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        String className = clazz.getSimpleName(), classType = clazz
                .getTypeName();
        if (primitiveWrapperMap.containsKey(clazz.getSimpleName()))
            return getPrimitiveData(className);

        Constructor[] constructors = clazz.getConstructors();
        if (constructors.length == 0)
            throw new InstantiationException("No Constructor found for class "
                    + clazz);

        // we will pick the first constructor only.
        Constructor constructor = constructors[0];
        Class[] types = constructor.getParameterTypes();
        Object[] parameters = new Object[types.length];
        int index = 0;

        for (Class type : types) {
            parameters[index++] = instantiate(type);
        }

        return constructor.newInstance(parameters);
    }

    private static Object[] getParameters(Class[] types)
            throws IllegalAccessException, InstantiationException,
            InvocationTargetException, IOException {
        if (types.length == 0)
            return new Object[0];

        Object[] parameters = new Object[types.length];
        int index = 0;

        for (Class type : types) {
            parameters[index++] = instantiate(type);
        }

        return parameters;
    }

    private static Object instantiate(Type type) throws IllegalAccessException,
            InstantiationException, InvocationTargetException, IOException {
        String typeName = type.getTypeName(), typeClass = type.getClass()
                .getSimpleName();
        if (!typeClass.contains("Class"))
            return instantiate(type.getClass());

        return getPrimitiveData(type.getTypeName());
    }

    private static Object getPrimitiveData(String primitiveClass)
            throws IOException {
        out.println("Enter value for: " + primitiveClass);
        out.flush();
        String value = in.read();
        switch (primitiveClass) {
            case "double":
                return Double.valueOf(value);
            case "int":
                return Integer.valueOf(value);
            case "long":
                return Long.valueOf(value);
            case "boolean":
                return Boolean.valueOf(value);
            case "char":
                return value.charAt(0);
            case "float":
                return Float.valueOf(value);
            case "short":
                return Short.valueOf(value);
            default:
                return 0;
        }
    }
}
