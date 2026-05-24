package com.cars24.automation.framework.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.cars24.automation.framework.methods.GenericMethods;

public class PropertyGuruHomePage {

    private final Page page;

    public PropertyGuruHomePage(Page page) {
        this.page = page;
    }

    private Locator filtersButton() {
        return page.locator("button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Filters"))
                .first();

    }

    private Locator filtersDialog() {
        return page.locator("[role='dialog']");
    }

    private Locator priceMinimumHeader() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Minimum"))
                .first();
    }

    private Locator priceMaximumHeader() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Maximum"))
                .first();
    }

    // =====================================================
    // Buy / Rent Toggle
    // =====================================================

    private Locator buyToggle() {
        return filtersDialog()
                .getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
                        new Locator.GetByRoleOptions().setName("Buy"));
    }

    private Locator rentToggle() {
        return filtersDialog()
                .getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
                        new Locator.GetByRoleOptions().setName("Rent"));
    }

    // =====================================================
    // Filter Sections
    // =====================================================

    private Locator propertyTypeOption() {
        return filtersDialog()
                .getByText("Property Type");
    }

    private Locator listingCountTitle() {
        return page.locator("h1")
                .first();
    }

    private Locator allPropertyTypeOption() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("All"))
                .first();
    }

    private Locator priceOption() {
        return filtersDialog()
                .getByText("Price");
    }

    private Locator bedroomOption() {
        return filtersDialog()
                .getByText("Bedroom");
    }

    // =====================================================
    // Property Type Options
    // =====================================================

    private Locator hdbOption() {
        return filtersDialog().getByText("HDB");
    }

    private Locator condoApartmentOption() {
        return filtersDialog().getByText("Condo");
    }

    private Locator landedHouseOption() {
        return filtersDialog().getByText("Landed");
    }

    // =====================================================
    // Additional Buy Filter Options
    // =====================================================

    private Locator verifiedListingsOption() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Verified Listings"))
                .first();
    }

    private Locator floorSizeOption() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Floor Size"))
                .first();
    }

    private Locator distanceToMrtOption() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Distance to MRT"))
                .first();
    }

    private Locator psfOption() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("PSF"))
                .first();
    }

    private Locator bathroomOption() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Bathroom"))
                .first();
    }

    private Locator tenureOption() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Tenure"))
                .first();
    }

    private Locator buildYearOption() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Build Year"))
                .first();
    }

    private Locator floorLevelOption() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Floor Level"))
                .first();
    }

    private Locator unitFeatureOption() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Unit Feature"))
                .first();
    }

    private Locator facilitiesOption() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Facilities"))
                .first();
    }

    private Locator furnishingOption() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Furnishing"))
                .first();
    }

    private Locator keywordOption() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Keyword"))
                .first();
    }

    private Locator listingOnOption() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Listed On"))
                .first();
    }

    private Locator listingFeatureOption() {
        return filtersDialog()
                .locator("div, label, button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Listing Feature"))
                .first();
    }

    private Locator clearButton() {
        return filtersDialog()
                .locator("button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Clear"))
                .first();
    }

    private Locator applyButton() {
        return filtersDialog()
                .locator("button")
                .filter(new Locator.FilterOptions()
                        .setHasText("Apply"))
                .first();
    }

    // =====================================================
    // Actions
    // =====================================================

    public void clickFilters() {
        filtersButton().click();
    }

    public void clickRentToggle() {
        rentToggle().click();
    }

    // =====================================================
    // Validations
    // =====================================================

    public boolean isFilterPopupVisible() {
        return filtersDialog().isVisible();
    }

    public boolean isBuyToggleVisible() {
        return buyToggle().isVisible();
    }

    public boolean isRentToggleVisible() {
        return rentToggle().isVisible();
    }

    public boolean isPropertyTypeVisible() {
        return propertyTypeOption().isVisible();
    }

    public boolean isPriceVisible() {
        return priceOption().isVisible();
    }

    public boolean isBedroomVisible() {
        return bedroomOption().isVisible();
    }

    public boolean isHdbVisible() {
        return hdbOption().isVisible();
    }

    public boolean isCondoApartmentVisible() {
        return condoApartmentOption().isVisible();
    }

    public boolean isLandedHouseVisible() {
        return landedHouseOption().isVisible();
    }

    public boolean isPriceMinHeaderVisible() {
        return priceMinimumHeader().isVisible();
    }

    public boolean isPriceMaxHeaderVisible() {
        return priceMaximumHeader().isVisible();
    }

    public boolean isVerifiedListingsVisible() {
        return verifiedListingsOption().isVisible();
    }

    public boolean isFloorSizeVisible() {
        return floorSizeOption().isVisible();
    }

    public boolean isDistanceToMrtVisible() {
        return distanceToMrtOption().isVisible();
    }

    public boolean isPsfVisible() {
        return psfOption().isVisible();
    }

    public boolean isBathroomVisible() {
        return bathroomOption().isVisible();
    }

    public boolean isTenureVisible() {
        return tenureOption().isVisible();
    }

    public boolean isBuildYearVisible() {
        return buildYearOption().isVisible();
    }

    public boolean isFloorLevelVisible() {
        return floorLevelOption().isVisible();
    }

    public boolean isUnitFeatureVisible() {
        return unitFeatureOption().isVisible();
    }

    public boolean isFacilitiesVisible() {
        return facilitiesOption().isVisible();
    }

    public boolean isFurnishingVisible() {
        return furnishingOption().isVisible();
    }

    public boolean isKeywordVisible() {
        return keywordOption().isVisible();
    }

    public boolean isListingOnVisible() {
        return listingOnOption().isVisible();
    }

    public boolean isListingFeatureVisible() {
        return listingFeatureOption().isVisible();
    }

    public boolean isClearButtonVisible() {
        return clearButton().isVisible();
    }

    public boolean isApplyButtonVisible() {
        return applyButton().isVisible();
    }

    public void clickAllPropertyType() {
        allPropertyTypeOption().click();
    }

    public void clickApply() {
        applyButton().click();
    }

    public int getListingCountFromTitle() {
        return GenericMethods
                .getListingCountFromTitle(
                        listingCountTitle()
                );
    }

    public void clickClear() {
        clearButton().click();
    }

    public void clickCondoPropertyType() {
        condoApartmentOption().click();
    }

    //    public String getListingTitle() {
//        return listingCountTitle()
//                .textContent()
//                .trim();
//    }
    public String getListingTitle() {
        page.locator("h1")
                .first()
                .waitFor();
        return listingCountTitle()
                .textContent()
                .trim();
    }

    public void clickLandedPropertyType() {
        landedHouseOption().click();
    }

    public void clickHdbPropertyType() {
        hdbOption().click();
    }

    public void waitForListingTitleToChange(
            String previousTitle
    ) {
        page.waitForLoadState();
        page.waitForCondition(
                () -> !getListingTitle()
                        .equalsIgnoreCase(previousTitle)
        );
    }
}