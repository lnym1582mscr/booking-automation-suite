package ge.tbc.itacademy.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class BasePage {
    public Search search = new Search();
    public Locator nav,
            navLinks,
            hamburger,
            loader,
            errorMsg,
            reload,
            alert;

    public BasePage(Page page) {
        this.search.searchBar = page.getByRole(AriaRole.COMBOBOX);
        this.search.calendar = page.getByTestId("datepicker-tabs");
        this.search.calendarBtn = page.getByTestId("searchbox-dates-container");
        this.search.guests = page.getByTestId("occupancy-config");
        this.search.adults = page.locator("#group_adults");
        this.nav = page.getByTestId("header-xpb");
        this.navLinks = nav.locator("a");
        this.hamburger = page.getByTestId("header-mobile-menu-button");
        this.loader = page.locator(".loader");
        this.errorMsg = page.getByText("error");
        this.reload = page.getByText("Reload");
        this.alert = page.locator(".toast, .alert").first();
    }
}
