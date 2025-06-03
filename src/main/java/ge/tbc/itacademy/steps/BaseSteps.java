package ge.tbc.itacademy.steps;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;
import ge.tbc.itacademy.page.BasePage;
import io.qameta.allure.Step;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.*;

public class BaseSteps {
    Page page;
    BasePage basePage;

    public BaseSteps(Page page) {
        this.basePage = new BasePage(page);
        this.page = page;
    }

    @Step("Searches for {query}")
    public BaseSteps searchSth(String query) {
        basePage.search.searchBar.fill(query);
        basePage.search.searchBar.press("Enter");
        return this;
    }

    public void checkStickiness(Locator locator) {
        double initialY = locator.boundingBox().y;

        page.evaluate("window.scrollTo(0, 1000);");
        double afterScrollY = locator.boundingBox().y;

        assertEquals(initialY, afterScrollY);
    }

    @Step("Checks if the calendar is open and closes it if it is")
    public BaseSteps closeCalendarIfOpen() {
         if (basePage.search.calendar.isVisible()) {
             basePage.search.calendarBtn.click();
         }
        return this;
    }

    @Step("Opens the calendar")
    public BaseSteps openCalendar() {
        basePage.search.calendarBtn.click();
        return this;
    }

    @Step("Opens the guest count tab")
    public BaseSteps openGuests() {
        basePage.search.guests.click();
        return this;
    }

    @Step("Chooses dates from {0} to {1}")
    public BaseSteps chooseDates(LocalDate checkIn, LocalDate checkOut) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String fromDateStr = checkIn.format(formatter);
        String toDateStr = checkOut.format(formatter);

        page.locator("[data-date='" + fromDateStr + "']").click();
        page.locator("[data-date='" + toDateStr + "']").click();
        return this;
    }

    @Step("Chooses a date {0} days ahead from today")
    public BaseSteps chooseDates(int daysAhead) {
        LocalDate today = LocalDate.now();
        LocalDate toDate = today.plusDays(daysAhead);
        this.chooseDates(today, toDate);
        return this;
    }

    @Step("Chooses {0} guest amount")
    public BaseSteps chooseGuests(int guests) {
        basePage.search.adults.fill(String.valueOf(guests));
        return this;
    }

    @Step("Validates that the navigation bar links are all fully visible")
    public BaseSteps validateFullNav() {
        BoundingBox navBox = basePage.nav.boundingBox();
        List<ElementHandle> links = basePage.navLinks.elementHandles();

        for (ElementHandle link : links) {
            BoundingBox linkBox = link.boundingBox();

            boolean isFullyVisible = linkBox.x >= navBox.x &&
                    linkBox.x + linkBox.width <= navBox.x + navBox.width;

            assertTrue(isFullyVisible);
        }
        return this;
    }

    @Step("Checks if a burger menu is visible or not")
    public BaseSteps burgir(Boolean yesNo) {
        if (yesNo) {
            assertThat(basePage.hamburger).isVisible();
            return this;
        }
        assertThat(basePage.hamburger).not().isVisible();
        return this;
    }

    @Step("Checks if the search bar is visible")
    public BaseSteps visibleSearchBar() {
        assertThat(basePage.search.searchBar).isVisible();
        return this;
    }

    @Step("Checks that the site hasn't crashed")
    public BaseSteps siteNoCrash() {
        assertFalse(page.content().isEmpty());
        return this;
    }

    @Step("Checks that a loader is visible")
    public BaseSteps loaderVisible() {
        assertThat(basePage.loader).isVisible();
        return this;
    }

    @Step("Checks that an error message is displayed")
    public BaseSteps errorMsgVisible() {
        assertThat(basePage.errorMsg).isVisible();
        return this;
    }

    @Step("Checks for an error in the console")
    public BaseSteps consoleError() {
        page.onConsoleMessage(msg -> {
            assertTrue(msg.text().contains("error"));
        });
        return this;
    }

    @Step("Waits for the site to initiate a reload and checks for it")
    public BaseSteps timeoutReload() {
        page.waitForTimeout(11000);
        assertThat(basePage.reload).isVisible();
        return this;
    }

    @Step("Navigates back")
    public BaseSteps goBack() {
        page.goBack();
        return this;
    }

    @Step("Validates that an alert is visible")
    public BaseSteps alertVisible() {
        assertThat(basePage.alert).isVisible();
        return this;
    }
}
