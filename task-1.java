import java.lang.reflect.*;
import java.util.Scanner;

public class ClassDescription {

    public static String getClassDescription(String className) throws ClassNotFoundException {
        Class<?> cls = Class.forName(className);
        return getClassDescription(cls);
    }

    public static String getClassDescription(Class<?> cls) {
        String description = "";

        // Package name
        description += String.format("Package: %s\n", cls.getPackageName());

        // Modifiers and class name
        int modifiers = cls.getModifiers();
        description += String.format("Modifiers: %s\n", Modifier.toString(modifiers));
        description += String.format("Class Name: %s\n", cls.getSimpleName());

        // Superclass
        Class<?> superclass = cls.getSuperclass();
        if (superclass != null) {
            description += String.format("Superclass: %s\n", superclass.getName());
        }

        // Implemented interfaces
        Class<?>[] interfaces = cls.getInterfaces();
        if (interfaces.length > 0) {
            description += "Implemented Interfaces:\n";
            for (Class<?> intf : interfaces) {
                description += String.format("- %s\n", intf.getName());
            }
        }

        // Fields
        Field[] fields = cls.getDeclaredFields();
        if (fields.length > 0) {
            description += "Fields:\n";
            for (Field field : fields) {
                description += String.format("- %s %s %s\n",
                        Modifier.toString(field.getModifiers()),
                        field.getType().getSimpleName(),
                        field.getName());
            }
        }

        // Constructors
        Constructor<?>[] constructors = cls.getDeclaredConstructors();
        if (constructors.length > 0) {
            description += "Constructors:\n";
            for (Constructor<?> constructor : constructors) {
                description += String.format("- %s %s(",
                        Modifier.toString(constructor.getModifiers()),
                        cls.getSimpleName());
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (i > 0) {
                        description += ", ";
                    }
                    description += parameterTypes[i].getSimpleName();
                }
                description += ")\n";
            }
        }

        // Methods
        Method[] methods = cls.getDeclaredMethods();
        if (methods.length > 0) {
            description += "Methods:\n";
            for (Method method : methods) {
                description += String.format("- %s %s %s(",
                        Modifier.toString(method.getModifiers()),
                        method.getReturnType().getSimpleName(),
                        method.getName());
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (i > 0) {
                        description += ", ";
                    }
                    description += parameterTypes[i].getSimpleName();
                }
                description += ")\n";
            }
        }

        return description;
    }

    public static void main(String[] args) {
        try {
            System.out.print("Enter class name: ");
            Scanner input = new Scanner(System.in);
            String className = input.nextLine();
            String description = getClassDescription(className);
            System.out.println(description);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
