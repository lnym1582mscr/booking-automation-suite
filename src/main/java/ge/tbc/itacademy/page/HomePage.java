package ge.tbc.itacademy.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage extends BasePage{
    public Locator footerGroups;
    public HomePage(Page page) {
        super(page);
        this.footerGroups = page.locator("[data-testid^='footer-group_']");
    }
}
