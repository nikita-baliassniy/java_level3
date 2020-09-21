package testClasses;

import annotations.AfterSuite;
import annotations.BeforeSuite;
import annotations.Test;

public class TestClassWithExceptionAfter extends BasicTestClass {

    @Test("1")
    public void foo1() {
        System.out.println("First priority");
    }

    @Test("2")
    public void foo2() {
        System.out.println("Second priority");
    }

    @Test()
    public void foo3() {
        System.out.println("Default priority");
    }

    @BeforeSuite
    public void before() {
        System.out.println("Before");
    }

    @AfterSuite
    public void after1() {
        System.out.println("After1");
    }

    @AfterSuite
    public void after2() {
        System.out.println("After2");
    }

}
