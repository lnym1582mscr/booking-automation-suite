package ge.tbc.itacademy.factory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserFactory {
    public static Browser getBrowser(Playwright playwright, String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                return playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            case "webkit":
                return playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
    }
}
