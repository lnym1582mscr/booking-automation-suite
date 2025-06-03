package ge.tbc.itacademy.tests;

import ge.tbc.itacademy.runners.BaseTest;
import ge.tbc.itacademy.steps.HomeSteps;
import ge.tbc.itacademy.steps.ResultsSteps;
import io.qameta.allure.*;
import org.testng.annotations.*;

import static ge.tbc.itacademy.data.Constants.*;

@Epic("Booking Tests")
@Feature("Cross-Device Viewport Testing")
@Link(name = "Booking", url = "https://booking.com/")
public class UIResponsivenessTests extends BaseTest {
    HomeSteps homeSteps;
    ResultsSteps resultsSteps;

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
    @Story("Verify Grid Layout and Navbar on Desktop")
    @Severity(SeverityLevel.NORMAL)
    public void desktopResponsiveTest() {
        page.setViewportSize(1920, 1080);
        homeSteps
                //1
                .validateFullNav()
                //3
                .burgir(false)
                .openCalendar()
                .chooseDates(DAYS)
                .searchSth(SEARCH1);
        resultsSteps
                //2
                .switchToGrid()
                .validateGrid(true);
    }

    @Test
    @Story("Ensure Layout Adjusts on Tablet")
    @Severity(SeverityLevel.TRIVIAL)
    public void tabletResponsiveTest() {
        page.setViewportSize(768, 1024);
        homeSteps
                //2
                .stackedFooters()
                //3
                .visibleSearchBar()
                .openCalendar()
                .chooseDates(DAYS)
                .searchSth(SEARCH1);
        resultsSteps
                //1
                .validateGrid(false);
    }

    @Test
    @Story("Validate Mobile Layout, Sticky Headers and Navigation")
    @Severity(SeverityLevel.CRITICAL)
    public void mobileResponsiveTest() {
        page.setViewportSize(375, 667);
        homeSteps
                //1
                .burgir(true)
                .openCalendar()
                .chooseDates(DAYS)
                .searchSth(SEARCH1);
        resultsSteps
                //2
                .validateGrid(false)
                //3
                .stickyHeader();
    }
}
