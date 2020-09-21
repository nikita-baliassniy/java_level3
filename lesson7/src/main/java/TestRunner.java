import annotations.AfterSuite;
import annotations.BeforeSuite;
import annotations.Test;
import testClasses.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class TestRunner {

    public void start() throws IllegalAccessException, InvocationTargetException {

        BasicTestClass[] testClasses = new BasicTestClass[]{
                new TestClass(), new TestClassWithExceptionAfter(),
                new TestClassWithExceptionBefore(), new TestClassWithoutAfter(),
                new TestClassWithoutBefore(), new TestClassWithoutTests()};

        for (BasicTestClass currentClass : testClasses) {
            try {
                Class aClass = currentClass.getClass();
                Method[] methods = aClass.getDeclaredMethods();
                Map<Method, Integer> priorityTests = new HashMap<>();
                Method beforeMethod = null;
                Method afterMethod = null;
                System.out.println("TESTING START " + currentClass.getClass());
                for (Method method : methods) {
                    if (method.getAnnotation(BeforeSuite.class) != null) {
                        if (beforeMethod == null) {
                            beforeMethod = method;
                        } else {
                            throw new RuntimeException("ERROR! TWO BEFORE METHODS!");
                        }
                    } else if (method.getAnnotation(AfterSuite.class) != null) {
                        if (afterMethod == null) {
                            afterMethod = method;
                        } else {
                            throw new RuntimeException("ERROR! TWO AFTER METHODS!");
                        }
                    } else if (method.getAnnotation(Test.class) != null) {
                        Test annotation = method.getAnnotation(Test.class);
                        priorityTests.put(method, Integer.valueOf(annotation.value()));
                    }
                }
                if (beforeMethod != null) {
                    beforeMethod.invoke(currentClass);
                }
                priorityTests.entrySet().stream().sorted(Map.Entry.comparingByValue())
                        .forEach(k -> {
                            try {
                                k.getKey().invoke(currentClass);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        });
                if (afterMethod != null) {
                    afterMethod.invoke(currentClass);
                }
            } catch (RuntimeException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                System.out.println("END OF TESTS");
                System.out.println("______________");
            }
        }
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        new TestRunner().start();
    }

}
