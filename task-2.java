import java.lang.reflect.*;
import java.util.*;

public class ObjectInspector {

    public static ArrayList<Method> inspectObject(Object obj) {
        Class<?> objClass = obj.getClass();
        System.out.println("Real Type: " + objClass.getName());
        System.out.println("State:");
        inspectFields(obj);
        System.out.println("Methods:");
        ArrayList<Method> available = inspectMethods(objClass);
        return available;
    }

    private static void inspectFields(Object obj) {
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                System.out.printf("- %s %s %s\n",
                    Modifier.toString(field.getModifiers()),
                    field.getName(),
                    field.get(obj));
            } catch (IllegalAccessException e) {
                System.out.printf("- %s %s %s\n",
                    Modifier.toString(field.getModifiers()),
                    field.getName(),
                    "<inaccessible>");
            }
        }
    }

    private static ArrayList<Method> inspectMethods(Class<?> objClass) {
        Method[] methods = objClass.getDeclaredMethods();
        ArrayList<Method> available = new ArrayList<Method>();

        for (Method method: methods) {
            if (Modifier.isPublic(method.getModifiers()) && method.getParameterCount() == 0) {
                available.add(method);
            }
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
        return available;
    }

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
        Object obj = new MyClass();
        ArrayList<Method> available = inspectObject(obj);
        System.out.println("Available methods:");
        int counter = 0;
        for (Method method: available) {
            System.out.printf("%d. %s %s %s()\n",
                    ++counter,
                    Modifier.toString(method.getModifiers()),
                    method.getReturnType().getSimpleName(),
                    method.getName());
        }
        System.out.println("Select method to execute by number in list:");
        Scanner input = new Scanner(System.in);
        int number = input.nextInt();
        if (number > 0 && number <= counter) {
            Method selected = available.get(number - 1);
            selected.setAccessible(true);
            selected.invoke(obj);
        } else {
            System.out.println("Invalid method number.");
        }
    }
}

class MyClass {
    protected int pass = 2580;
    private int number = 10;
    public String str = "Hello";

    public void method1() {
        System.out.println("Method 1 called");
    }

    public void method2() {
        System.out.println("Method 2 called");
    }
}
