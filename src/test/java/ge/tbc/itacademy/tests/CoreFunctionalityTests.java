package ge.tbc.itacademy.tests;

import ge.tbc.itacademy.runners.BaseTest;
import ge.tbc.itacademy.steps.*;
import ge.tbc.itacademy.util.RetryAnalyzer;
import ge.tbc.itacademy.util.RetryCount;
import io.qameta.allure.*;
import org.testng.annotations.*;

import static ge.tbc.itacademy.data.Constants.*;

@Epic("Booking Tests")
@Feature("Core Booking Flow Validation")
@Link(name = "Booking", url = "https://booking.com/")
public class CoreFunctionalityTests extends BaseTest {
    ResultsSteps resultsSteps;
    HomeSteps homeSteps;

    @BeforeClass
    public void stepsInit() {
        resultsSteps = new ResultsSteps(page);
        homeSteps = new HomeSteps(page);
    }

    @BeforeMethod
    public void homePage() {
        page.navigate(BOOKING);
    }

    @Test
    @Story("Validate Destination Search with Results")
    @Severity(SeverityLevel.CRITICAL)
    public void searchTest() {
        homeSteps
                .searchSth(SEARCH1)
                .closeCalendarIfOpen();
        resultsSteps
                .checkBreadcrumbs(SEARCH1)
                .checkResultHeading(SEARCH1)
                .resultsExist();
    }

    @Test
    @Story("Date Selection and Display Confirmation")
    @Severity(SeverityLevel.CRITICAL)
    public void dateSelectionTest() {
        homeSteps
                .openCalendar()
                .chooseDates(DAYS)
                .searchSth(SEARCH1)
                .closeCalendarIfOpen();
        resultsSteps.confirmDate(DAYS);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @RetryCount(count = 3)
    @Story("Filter by Options and Validate Output")
    @Severity(SeverityLevel.NORMAL)
    public void filterApplicationTest() {
        homeSteps
                .openCalendar()
                .chooseDates(DAYS)
                .searchSth(SEARCH1)
                .closeCalendarIfOpen();
        resultsSteps
                .selectFiveStars()
                .selectFreeBreakfast()
                .resultsExist()
                .selectPriceRange(FROM, TO)
                .validateBreakfast()
                .validateStars()
                .validatePrice(FROM, TO);
    }

    @Test
    @Story("Sort Results by Review Score")
    @Severity(SeverityLevel.MINOR)
    public void sortByReviewScoreTest() {
        homeSteps
                .openCalendar()
                .chooseDates(DAYS)
                .searchSth(SEARCH1)
                .closeCalendarIfOpen();
        resultsSteps
                .openSortDropdown()
                .chooseSortOption(RATING_SORT)
                .validateRatingSort();
    }

    @Test
    @Story("Validate Property Details Consistency")
    @Severity(SeverityLevel.NORMAL)
    public void propertyDetailsConsistencyTest() {
        homeSteps
                .openCalendar()
                .chooseDates(DAYS)
                .searchSth(SEARCH1)
                .closeCalendarIfOpen();
        resultsSteps
                .compareOffers();
    }
}
