package ge.tbc.itacademy.tests;

import ge.tbc.itacademy.data.BookingCaseData;
import ge.tbc.itacademy.models.BookingCase;
import ge.tbc.itacademy.runners.BaseTest;
import ge.tbc.itacademy.steps.HomeSteps;
import io.qameta.allure.*;
import org.testng.annotations.*;

import static ge.tbc.itacademy.data.Constants.*;

@Epic("Booking Tests")
@Feature("Database-Backed Input Testing")
@Link(name = "Booking", url = "https://booking.com/")
public class DataDrivenTests extends BaseTest {
    HomeSteps homeSteps;

    @BeforeClass
    public void stepsInit() {
        homeSteps = new HomeSteps(page);
    }

    @BeforeMethod
    public void homePage() {
        page.navigate(BOOKING);
    }

    @Test(dataProvider = "bookingCasesProvider", dataProviderClass = BookingCaseData.class)
    @Story("Input Searches with Booking Cases from SQL")
    @Severity(SeverityLevel.CRITICAL)
    public void dataDriverSearch(BookingCase bookingCase) {
        homeSteps
                .openCalendar()
                .chooseDates(bookingCase.getCheckIn(), bookingCase.getCheckOut())
                .openGuests()
                .chooseGuests(bookingCase.getGuests())
                .searchSth(bookingCase.getDestination());
    }
}
