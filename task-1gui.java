import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;

import java.lang.reflect.*;

public class ClassDescriptionGUI extends Frame implements ActionListener {

    private TextField classNameField;
    private TextArea descriptionArea;

    public ClassDescriptionGUI() {
        setTitle("Аналізатор класу");
        setSize(600, 400);

        Panel panel = new Panel();
        panel.setLayout(new GridLayout(2, 1));

        Panel inputPanel = new Panel();
        Label classNameLabel = new Label("Введіть повне ім'я класу:");
        classNameField = new TextField(20);
        Button describeButton = new Button("Аналіз");
        describeButton.addActionListener(this);
        inputPanel.add(classNameLabel);
        inputPanel.add(classNameField);
        inputPanel.add(describeButton);

        Panel outputPanel = new Panel();
        descriptionArea = new TextArea();
        descriptionArea.setEditable(false);
        outputPanel.add(descriptionArea);

        panel.add(inputPanel);
        panel.add(outputPanel);

        add(panel);
        setVisible(true);
        addWindowListener (new WindowAdapter() {    
            public void windowClosing (WindowEvent e) {    
                dispose();    
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Аналіз")) {
            String className = classNameField.getText();
            if (!className.isEmpty()) {
                try {
                    String description = getClassDescription(className);
                    descriptionArea.setText(description);
                } catch (ClassNotFoundException ex) {
                    descriptionArea.setText("Клас не знайдено: " + className);
                }
            } else {
                descriptionArea.setText("Будь ласка введіть ім'я класу.");
            }
        }
    }

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
        new ClassDescriptionGUI();
    }
}
