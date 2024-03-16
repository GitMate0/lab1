import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public interface ExampleService {
    String performOperation(String input);
}

public class ExampleServiceImpl implements ExampleService {
    @Override
    public String performOperation(String input) {
        // Simulating some operation
        return "Result of " + input;
    }
}

public class ProfilingInvocationHandler implements InvocationHandler {
    private final Object target;

    public ProfilingInvocationHandler(Object target) {
        this.target = target;
    }

    public static Object newInstance(Object target) {
        return Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            target.getClass().getInterfaces(),
            new ProfilingInvocationHandler(target)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.nanoTime();
        Object result = method.invoke(target, args);
        long elapsedTime = System.nanoTime() - start;

        System.out.println("Executing " + method.getName());
        if (args != null) {
            System.out.print(" with args: ");
            for (Object arg : args) {
                System.out.print(arg + " ");
            }
        }
        System.out.println("\nMethod execution time: " + elapsedTime + " nanoseconds");

        System.out.println("Method returned: " + result);

        return result;
    }
}

public class ProxyDemo {
    public static void main(String[] args) {
        ExampleServiceImpl realService = new ExampleServiceImpl(); // create instance of real service
        ExampleService proxyService = (ExampleService) ProfilingInvocationHandler.newInstance(realService); // wrap it with the profiling proxy

        // Call method on the proxy instance
        String result = proxyService.performOperation("test operation");
        System.out.println("Proxy returned: " + result);
    }
}