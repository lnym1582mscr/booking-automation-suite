package ge.tbc.itacademy.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ResultsPage extends BasePage{
    Page page;
    public Locator breadcrumbs,
            resultHeading,
            filters,
            priceFrom,
            priceTo,
            results,
            status,
            sortDropdown,
            gridRadio,
            sticky;
    public String
            price = "price-and-discounted-price",
            rating = "[data-testid='rating-stars'] span, [data-testid='rating-squares'] span";

    public ResultsPage(Page page) {
        super(page);
        this.page = page;
        this.results = page.getByTestId("property-card");
        this.breadcrumbs = page.getByTestId("breadcrumbs");
        this.resultHeading = page.locator("h1[aria-live='assertive']");
        this.filters = page.getByTestId("filters-group-label-content");
        this.priceFrom = page.locator("[aria-label='Min.']").filter(new Locator.FilterOptions().setVisible(true));
        this.priceTo = page.locator("[aria-label='Max.']").filter(new Locator.FilterOptions().setVisible(true));
        this.status = page.getByRole(AriaRole.STATUS).filter(new Locator.FilterOptions().setVisible(true));
        this.sortDropdown = page.getByTestId("sorters-dropdown-trigger");
        this.gridRadio = page.getByText("Grid");
        this.sticky = page.getByTestId("sticky-container");
    }

    public Locator getSortOption(String label) {
        return page.getByTestId("sorters-dropdown").getByLabel(label);
    }

    public Locator getPageTitle(Page popup) {
        return popup.locator("#hp_hotel_name h2");
    }

    public Locator getPageType(Page popup) {
        return popup.locator(".hprt-roomtype-name").first();
    }

    public Locator getPageReviewCount(Page popup) {
        return popup.locator("#reviews-tab-trigger");
    }

    public Locator getPagePrice(Page popup) {
        return popup.locator(".prco-valign-middle-helper").first();
    }
}