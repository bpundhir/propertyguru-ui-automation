package com.cars24.automation.framework.methods;

import com.microsoft.playwright.Locator;

public class GenericMethods {

    private GenericMethods() {
    }

    public static int getListingCountFromTitle(
            Locator titleLocator
    )
    {
        String headingText =
                titleLocator.textContent();
        if (headingText == null || headingText.isBlank()) {
            throw new IllegalStateException(
                    "Listing title text is empty"
            );
        }

        String count =
                headingText.replaceAll("[^0-9]", "");
        return Integer.parseInt(count);
    }
}
