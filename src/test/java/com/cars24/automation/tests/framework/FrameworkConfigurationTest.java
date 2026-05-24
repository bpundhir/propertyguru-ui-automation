package com.cars24.automation.tests.framework;

import com.cars24.automation.framework.config.FrameworkConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class FrameworkConfigurationTest {
    @Test(groups = "framework")
    public void loadsExternalConfigurationProfile() {
        FrameworkConfig config = FrameworkConfig.load();

        Assert.assertFalse(config.baseUrl().toString().isBlank(), "A base URL must be configured");
        Assert.assertTrue(config.saleListingsPath().startsWith("/"), "Listing path must be relative to the base URL");
        Assert.assertTrue(config.actionTimeoutMilliseconds() > 0, "Action timeout must be positive");
    }
}
