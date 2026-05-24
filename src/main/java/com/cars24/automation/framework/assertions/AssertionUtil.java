package com.cars24.automation.framework.assertions;

import org.testng.asserts.SoftAssert;

import java.util.Objects;

public final class AssertionUtil {

    private static final ThreadLocal<SoftAssert> SOFT_ASSERT =
            ThreadLocal.withInitial(SoftAssert::new);

    private AssertionUtil() {
    }

    public static void assertAll() {

        try {
            SOFT_ASSERT.get().assertAll();
        } catch (AssertionError error) {
            throw error;
        } finally {
            SOFT_ASSERT.remove();
        }
    }

    public static void assertEquals(
            Object actual,
            Object expected,
            String message
    ) {

        boolean condition =
                Objects.equals(actual, expected);

        if (!condition) {
            SOFT_ASSERT.get()
                    .assertEquals(
                            actual,
                            expected,
                            message
                    );
        }
    }

    public static void assertTrue(
            boolean condition,
            String message
    ) {

        try {

            SOFT_ASSERT.get()
                    .assertTrue(
                            condition,
                            message
                    );

        } catch (AssertionError ignored) {
        }
    }

    public static void assertFalse(
            boolean condition,
            String message
    ) {

        try {

            SOFT_ASSERT.get()
                    .assertFalse(
                            condition,
                            message
                    );

        } catch (AssertionError ignored) {
        }
    }

    public static void fail(String message) {

        SOFT_ASSERT.get()
                .fail(message);
    }
}