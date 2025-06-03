package ge.tbc.itacademy.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;
import ge.tbc.itacademy.page.HomePage;
import io.qameta.allure.Step;

import static org.testng.Assert.assertTrue;

public class HomeSteps extends BaseSteps{
    Page page;
    HomePage homePage;

    public HomeSteps(Page page) {
        super(page);
        this.page = page;
        this.homePage = new HomePage(page);
    }

    @Step("Verifies that the footers are stacked vertically")
    public HomeSteps stackedFooters() {
        int count = homePage.footerGroups.count();

        double previousBottomY = -1;

        for (int i = 0; i < count; i++) {
            homePage.footerGroups.nth(i).scrollIntoViewIfNeeded();
            BoundingBox box = homePage.footerGroups.nth(i).boundingBox();

            double currentTopY = box.y;

            if (previousBottomY != -1) {
                System.out.println("Previous bottom Y: " + previousBottomY);
                System.out.println("Current top Y: " + currentTopY);
                assertTrue(currentTopY >= previousBottomY);
            }
            previousBottomY = box.y + box.height;
        }
        return this;
    }
}
