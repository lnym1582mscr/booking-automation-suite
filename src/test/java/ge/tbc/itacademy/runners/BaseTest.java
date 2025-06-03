package ge.tbc.itacademy.runners;

import com.microsoft.playwright.*;
import ge.tbc.itacademy.factory.BrowserFactory;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.nio.file.Paths;
import java.util.Map;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @Parameters("browser")
    @BeforeClass
    public void setUp(@Optional("chromium") String browserName) {
        playwright = Playwright.create();
        browser = BrowserFactory.getBrowser(playwright, browserName);
        context = browser.newContext(new Browser.NewContextOptions()
                .setLocale("en-US")
                .setExtraHTTPHeaders(Map.of("Accept-Language", "en-US")));

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page = context.newPage();
    }

    @AfterMethod
    public void captureOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            takeScreenshot(page);

            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("traces/" + result.getName() + "-trace.zip")));
        }
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] takeScreenshot(Page page) {
        return page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
    }

    @AfterClass
    public void tearDown() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}