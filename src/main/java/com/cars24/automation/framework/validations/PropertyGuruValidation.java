package com.cars24.automation.framework.validations;

import com.cars24.automation.framework.assertions.AssertionUtil;
import com.cars24.automation.framework.pages.PropertyGuruHomePage;
import com.cars24.automation.framework.reporting.ExtentReportManager;

public class PropertyGuruValidation {

    private final PropertyGuruHomePage propertyGuruHomePage;

    public PropertyGuruValidation(
            PropertyGuruHomePage propertyGuruHomePage
    ) {
        this.propertyGuruHomePage =
                propertyGuruHomePage;
    }

    public void validateCommonFilterOptions() {

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isPropertyTypeVisible(),
                "Property Type should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isPriceVisible(),
                "Price should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isPriceMinHeaderVisible(),
                "Minimum price header should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isPriceMaxHeaderVisible(),
                "Maximum price header should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isBedroomVisible(),
                "Bedroom should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isVerifiedListingsVisible(),
                "Verified Listings should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isFloorSizeVisible(),
                "Floor Size should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isDistanceToMrtVisible(),
                "Distance to MRT should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isPsfVisible(),
                "PSF should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isBathroomVisible(),
                "Bathroom should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isBuildYearVisible(),
                "Build Year should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isFloorLevelVisible(),
                "Floor Level should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isUnitFeatureVisible(),
                "Unit Feature should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isFacilitiesVisible(),
                "Facilities should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isFurnishingVisible(),
                "Furnishing should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isKeywordVisible(),
                "Keyword should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isListingOnVisible(),
                "Listing On should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isListingFeatureVisible(),
                "Listing Feature should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isClearButtonVisible(),
                "Clear button should be visible"
        );

        AssertionUtil.assertTrue(
                propertyGuruHomePage.isApplyButtonVisible(),
                "Apply button should be visible"
        );
    }

    public void validatePropertyTypeFilter(String propertyType, Runnable action, String expectedTitle) {

        propertyGuruHomePage.clickFilters();
        propertyGuruHomePage.clickClear();
        action.run();
        ExtentReportManager.info(
                "Expected Property Type: "
                        + propertyType
        );

        String previousTitle =
                propertyGuruHomePage
                        .getListingTitle();
        propertyGuruHomePage
                .clickApply();
        propertyGuruHomePage
                .waitForListingTitleToChange(previousTitle);
        String actualTitle =
                propertyGuruHomePage
                        .getListingTitle();

        ExtentReportManager.info(
                "Title after "
                        + propertyType
                        + " selection: "
                        + actualTitle
        );
        ExtentReportManager.info(
                "Actual Title: "
                        + actualTitle
        );

        boolean isTitleMatched =
                switch (propertyType.toLowerCase()) {
                    case "condo" ->
                            actualTitle.contains("Condo")
                                    || actualTitle.contains("Condos")
                                    || actualTitle.contains("Apartment")
                                    || actualTitle.contains("Apartments");
                    case "landed" ->
                            actualTitle.contains("Landed");
                    case "hdb" ->
                            actualTitle.contains("HDB");
                    default ->
                            actualTitle.contains(expectedTitle);
                };
        AssertionUtil.assertTrue(
                isTitleMatched,
                "Title should contain "
                        + propertyType
        );
    }
}