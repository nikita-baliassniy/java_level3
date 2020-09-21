package testClasses;

import annotations.AfterSuite;
import annotations.BeforeSuite;

public class TestClassWithoutTests extends BasicTestClass {

    @BeforeSuite
    public void before() {
        System.out.println("Before");
    }

    @AfterSuite
    public void after() {
        System.out.println("After");
    }

}
