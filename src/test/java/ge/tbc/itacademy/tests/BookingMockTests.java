package ge.tbc.itacademy.tests;

import ge.tbc.itacademy.runners.BaseTest;
import ge.tbc.itacademy.steps.HomeSteps;
import ge.tbc.itacademy.steps.ResultsSteps;
import io.qameta.allure.*;
import org.testng.annotations.*;

import static ge.tbc.itacademy.data.Constants.*;
import static ge.tbc.itacademy.util.MockUtil.*;

@Epic("Booking Tests")
@Feature("Network Interception and Mock Scenarios")
@Link(name = "Booking", url = "https://booking.com/")
public class BookingMockTests extends BaseTest {
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
    @Story("Empty Search Results Scenario")
    @Severity(SeverityLevel.CRITICAL)
    public void noResultsMockTest() {
        mockEmptySearchResults(page);

        homeSteps.searchSth(SEARCH1);
        resultsSteps
                .selectFreeBreakfast()
                .checkResultHeading(NO_RESULTS)
                .visibleSearchBar()
                .siteNoCrash();
    }

    @Test
    @Story("Simulate Slow Backend Response")
    @Severity(SeverityLevel.NORMAL)
    public void slowResponseMockTest() {
        mockSlowResponse(page);

        homeSteps
                .searchSth(SEARCH1)
                .loaderVisible();
    }

    @Test
    @Story("Handle Malformed JSON Response")
    @Severity(SeverityLevel.CRITICAL)
    public void invalidJsonMockTest() {
        mockInvalidJson(page);

        homeSteps.searchSth(SEARCH1);
        resultsSteps
                .errorMsgVisible()
                .consoleError();
    }

    @Test
    @Story("Override Search Response by Destination")
    @Severity(SeverityLevel.MINOR)
    public void searchSpecificResultsMockTest() {
        mockSearchSpecificResults(page);

        homeSteps.searchSth(SEARCH2);
        resultsSteps
                .resultsExist()
                .goBack();

        homeSteps.searchSth(SEARCH1);
        resultsSteps
                .resultsNotExist();
    }

    @Test
    @Story("Handle 500 Internal Server Error")
    @Severity(SeverityLevel.BLOCKER)
    public void internalServerErrorMockTest() {
        mockIntervalServerError(page);

        homeSteps
                .searchSth(SEARCH1)
                .alertVisible()
                .timeoutReload();
    }

    @Test
    @Story("Handle UI with Long or Missing Hotel Data")
    @Severity(SeverityLevel.NORMAL)
    public void hotelCardOverrideMockTest() {
        mockHotelCardOverride(page);

        homeSteps.searchSth(SEARCH1);
        resultsSteps
                .switchToGrid()
                .validateGrid(true)
                .selectPriceRange(FROM, TO)
                .validatePrice(FROM, TO);
    }

    @Test
    @Story("Validate Timeout Behavior on Infinite Loading")
    @Severity(SeverityLevel.CRITICAL)
    public void infiniteTimeoutMockTest() {
        mockInfiniteTimeout(page);

        homeSteps.searchSth(SEARCH1);
        resultsSteps.timeoutReload();
    }
}
