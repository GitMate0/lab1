import java.lang.reflect.*;

public class MethodCaller {
    public static void callMethod(Object object, String methodName, Object... parameters) throws FunctionNotFoundException {
        Method[] methods = object.getClass().getDeclaredMethods();
        System.out.println("Class name: " + object.getClass().getName());
        if (methods.length > 0) {
            System.out.println("Methods:");
            for (Method method : methods) {
                System.out.printf("- %s %s %s(",
                        Modifier.toString(method.getModifiers()),
                        method.getReturnType().getSimpleName(),
                        method.getName());
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (i > 0) {
                        System.out.print(", ");
                    }
                    System.out.print(parameterTypes[i].getSimpleName());
                }
                System.out.println(")");
            }
        }
        Class<?>[] gettedParameterTypes = new Class<?>[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            gettedParameterTypes[i] = parameters[i].getClass();
            System.out.println(parameters[i].getClass().getName());
        }
        try {
            Method method = object.getClass().getMethod(methodName, gettedParameterTypes);
            System.out.println("Method parameters:");
            for (Object parameter : parameters) {
                System.out.println(parameter);
            }
            try {
                Object result = method.invoke(object, parameters);
                System.out.println("Method result: " + result);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            throw new FunctionNotFoundException("Method " + methodName + " not found");
        }
    }

    public static void main(String[] args) {
        try {
            callMethod(new MyClass(), "myMethod", 10, "Hello");
        } catch (FunctionNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class MyClass {
    public void myMethod(Integer number, String text) {
        System.out.println("Number: " + number + ", Text: " + text);
    }
}

class FunctionNotFoundException extends Exception {
    public FunctionNotFoundException(String message) {
        super(message);
    }
}
