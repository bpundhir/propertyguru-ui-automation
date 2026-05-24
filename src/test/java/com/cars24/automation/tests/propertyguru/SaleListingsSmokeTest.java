package com.cars24.automation.tests.propertyguru;

import com.cars24.automation.framework.assertions.AssertionUtil;
import com.cars24.automation.framework.base.BaseClass;
import com.cars24.automation.framework.reporting.ExtentReportManager;
import org.testng.annotations.Test;

public final class SaleListingsSmokeTest extends BaseClass {

    @Test(description = "Verify clicking Filters button opens filters popup")
    public void verifyFiltersPopupOpens() {
        try {
            propertyGuruHomePage().clickFilters();

            AssertionUtil.assertTrue(propertyGuruHomePage().isFilterPopupVisible(), "Filters popup should be visible");
        } catch (Exception exception) {
            AssertionUtil.fail(exception.getMessage());
        }
        AssertionUtil.assertAll();
    }

    @Test(description = "Verify all Buy filter options are visible")
    public void verifyFilterOptionsForBuy() {
        try {
            propertyGuruHomePage()
                    .clickFilters();
            propertyGuruValidation()
                    .validateCommonFilterOptions();
        } catch (Exception exception) {
            AssertionUtil.fail(
                    exception.getMessage()
            );
        }
        AssertionUtil.assertAll();
    }

    @Test(description = "Verify all filter options are visible when Rent is selected")
    public void verifyFilterOptionsForRent() {
        try {
            propertyGuruHomePage()
                    .clickFilters();
            propertyGuruHomePage()
                    .clickRentToggle();
            propertyGuruValidation()
                    .validateCommonFilterOptions();
        } catch (Exception exception) {
            AssertionUtil.fail(
                    exception.getMessage()
            );
        }
        AssertionUtil.assertAll();
    }

    @Test(description = "Verify Property Type filter apply functionality")
    public void verifyPropertyTypeFilterApplyFeature() {
        try {
            propertyGuruHomePage()
                    .clickFilters();
            int initialListingCount =
                    propertyGuruHomePage()
                            .getListingCountFromTitle();
            propertyGuruHomePage()
                    .clickAllPropertyType();

            propertyGuruHomePage()
                    .clickApply();

            int finalListingCount =
                    propertyGuruHomePage()
                            .getListingCountFromTitle();

            AssertionUtil.assertEquals(
                    finalListingCount,
                    initialListingCount,
                    "Listing count should remain same after applying All filter"
            );

            propertyGuruValidation()
                    .validatePropertyTypeFilter(
                            "Condo",
                            () -> propertyGuruHomePage()
                                    .clickCondoPropertyType(),
                            "Condo"
                    );

            propertyGuruValidation()
                    .validatePropertyTypeFilter(
                            "Landed",
                            () -> propertyGuruHomePage()
                                    .clickLandedPropertyType(),
                            "Landed"
                    );

            propertyGuruValidation()
                    .validatePropertyTypeFilter(
                            "HDB",
                            () -> propertyGuruHomePage()
                                    .clickHdbPropertyType(),
                            "HDB"
                    );

        } catch (Exception exception) {

            AssertionUtil.fail(
                    exception.getMessage()
            );
        }

        AssertionUtil.assertAll();
    }
}