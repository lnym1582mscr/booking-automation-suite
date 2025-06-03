package ge.tbc.itacademy.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import ge.tbc.itacademy.enums.Filter;
import ge.tbc.itacademy.models.Offer;
import ge.tbc.itacademy.page.ResultsPage;
import io.qameta.allure.Step;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.itacademy.data.Constants.*;
import static org.testng.Assert.*;

public class ResultsSteps extends BaseSteps{
    Page page;
    ResultsPage resultsPage;

    public ResultsSteps(Page page) {
        super(page);
        this.page = page;
        this.resultsPage = new ResultsPage(page);
    }

    private void checkKeywordInSth(String keyword, Locator locator) {
        assertThat(locator).isVisible();
        assertThat(locator).containsText(keyword);
    }

    private void selectFilter(Filter filter) {
        resultsPage.filters.getByText(filter.getLabel()).first().click();
    }

    private Offer captureCardOffer(Locator offer) {
        return new Offer(
                offer,
                offer.getByTestId("title").innerText().strip(),
                offer.getByTestId("recommended-units").locator("h4").innerText().strip(),
                offer.locator("xpath=//div[contains(text(), 'reviews')]").innerText().replaceAll("[^0-9]", ""),
                offer.getByTestId(resultsPage.price).innerText().strip()
        );
    }

    private Offer captureSiteOffer(Page popup) {
        return new Offer(
                null,
                resultsPage.getPageTitle(popup).innerText().strip(),
                resultsPage.getPageType(popup).innerText().strip(),
                resultsPage.getPageReviewCount(popup).innerText().replaceAll("[^0-9]", ""),
                resultsPage.getPagePrice(popup).innerText().strip()
        );
    }

    private int cardsInRow() {
        List<Locator> cards = resultsPage.results.all();
        double firstCardY = cards.get(0).boundingBox().y;

        int sameRowCount = 0;
        for (Locator card : cards) {
            if (card.boundingBox().y == firstCardY) {
                sameRowCount++;
            }
        }
        return sameRowCount;
    }

    @Step("Checks for the keyword {0} in the 'breadcrumbs'")
    public ResultsSteps checkBreadcrumbs(String keyword) {
        this.checkKeywordInSth(keyword, resultsPage.breadcrumbs);
        return this;
    }

    @Step("Checks for the keyword {0} in the heading")
    public ResultsSteps checkResultHeading(String keyword) {
        this.checkKeywordInSth(keyword, resultsPage.resultHeading);
        return this;
    }

    @Step("Validates that the results exist")
    public ResultsSteps resultsExist() {
        assertFalse(resultsPage.results.count() < 0);
        return this;
    }

    @Step("Validates that the results do not exist")
    public ResultsSteps resultsNotExist() {
        assertThat(resultsPage.results).isEmpty();
        return this;
    }

    @Step("Confirms that the offers show bookings for {0} days")
    public ResultsSteps confirmDate(int daysAhead) {
        assertThat(resultsPage.results.first()).isVisible();
        for (var result : resultsPage.results.all()) {
            result.scrollIntoViewIfNeeded();
            Locator daysGuests = result.getByTestId("price-for-x-nights");
            if (daysAhead % 7 == 0) {
                assertThat(daysGuests).containsText(daysAhead / 7 + " week");
            } else {
                assertThat(daysGuests).containsText(daysAhead + " night");
            }
        }
        return this;
    }

    @Step("Selects a price range from {0} to {1}")
    public ResultsSteps selectPriceRange(String from, String to) {
        resultsPage.priceFrom.fill(from);
        resultsPage.priceTo.fill(to);
        return this;
    }

    @Step("Selects the 'free breakfast' filter")
    public ResultsSteps selectFreeBreakfast() {
        this.selectFilter(Filter.BREAKFAST);
        return this;
    }

    @Step("Filters offers by five stars")
    public ResultsSteps selectFiveStars() {
        this.selectFilter(Filter.STAR5);
        return this;
    }

    @Step("Validates that the bookings offer free breakfast")
    public ResultsSteps validateBreakfast() {
        for (var result : resultsPage.results.all()) {
            assertThat(result).containsText(BREAKFAST);
        }
        return this;
    }

    @Step("Validates that the offers have five stars")
    public ResultsSteps validateStars() {
        for (var result : resultsPage.results.all()) {
            assertEquals(result.locator(resultsPage.rating).count(), 5);
        }
        return this;
    }

    @Step("Validates that the offers are priced from {0} to {1}")
    public ResultsSteps validatePrice(String from, String to) {
        for (var result: resultsPage.results.all()) {
            String price = result.getByTestId(resultsPage.price).innerText();
            int number = Integer.parseInt(price.replaceAll("[^0-9]", ""));
            assertTrue(Integer.parseInt(from) < number && Integer.parseInt(to) > number);
        }
        return this;
    }

    @Step("Opens the sort dropdown")
    public ResultsSteps openSortDropdown() {
        resultsPage.sortDropdown.click();
        return this;
    }

    @Step("Chooses a sort option")
    public ResultsSteps chooseSortOption(String option) {
        resultsPage.getSortOption(option).click();
        return this;
    }

    @Step("Validates that the offers are sorted via rating")
    public ResultsSteps validateRatingSort() {
        List<Locator> offers = resultsPage.results.all();

        for (int i = 0; i < offers.size() - 1; i++) {
            offers.get(i).scrollIntoViewIfNeeded();
            assertTrue(
                    offers.get(i).locator(resultsPage.rating).count()
                            >=
                            offers.get(i + 1).locator(resultsPage.rating).count()
            );
        }
        return this;
    }

    @Step("Matches an offer card to its page information")
    public ResultsSteps compareOffers() {
        assertThat(resultsPage.results.first()).isVisible();
        for (var result : resultsPage.results.all()) {
            result.scrollIntoViewIfNeeded();
            Offer cardOffer = this.captureCardOffer(result);

            Page popup = page.waitForPopup(result::click);
            popup.waitForLoadState(LoadState.DOMCONTENTLOADED);

            Offer pageOffer = this.captureSiteOffer(popup);
            assertEquals(cardOffer, pageOffer, "Offer mismatch between card and detail page");
            popup.close();
        }
        return this;
    }

    @Step("Switches to grid layout")
    public ResultsSteps switchToGrid() {
        resultsPage.gridRadio.click();
        return this;
    }

    @Step("Validates that the offers are either in a grid layout or not")
    public ResultsSteps validateGrid(boolean yesNo) {
        assertThat(resultsPage.results.first()).isVisible();
        if (yesNo){
            assertTrue(this.cardsInRow() > 0);
            return this;
        }
        assertEquals(this.cardsInRow(), 1);
        return this;
    }

    @Step("Validates that the site header is sticky")
    public ResultsSteps stickyHeader() {
        this.checkStickiness(resultsPage.sticky);
        return this;
    }
}
