package ge.tbc.itacademy.util;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MockUtil {
    private static final Logger logger = LoggerFactory.getLogger(MockUtil.class);

    public static void mockEmptySearchResults(Page page) {
        page.route("**/searchresults.html*", route -> route.fulfill(new Route.FulfillOptions()
                .setStatus(200)
                .setBody("{\"hotels\": []}")
                .setContentType("application/json")));
    }

    public static void mockSlowResponse(Page page) {
        page.route("**/searchresults.html*", route -> {
            try { Thread.sleep(5000); } catch (InterruptedException ignored) { }
            route.resume();
        });
    }

    public static void mockInvalidJson(Page page) {
        page.route("**/searchresults.html*", route -> {
            route.fulfill(new Route.FulfillOptions()
                    .setStatus(200)
                    .setContentType("application/json")
                    .setBody("{ thisIs: notValidJson }"));
        });
    }

    public static void mockSearchSpecificResults(Page page) {
        page.route("**/searchresults.html*", route -> {
            try {
                String postData = route.request().postData();
                if (postData != null && postData.contains("Tbilisi")) {
                    route.fulfill(new Route.FulfillOptions()
                            .setStatus(200)
                            .setContentType("application/json")
                            .setBody(Files.readString(Paths.get("mock-data/tbilisiHotels.json"))));
                } else {
                    route.fulfill(new Route.FulfillOptions()
                            .setStatus(200)
                            .setContentType("application/json")
                            .setBody("{\"hotels\": []}"));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void mockIntervalServerError(Page page) {
        page.route("**/searchresults.html*", route -> {
            if (Math.random() < 0.5) {
                logger.warn("Simulated 500 Internal Server Error for URL: {}", route.request().url());

                route.fulfill(new Route.FulfillOptions()
                        .setStatus(500)
                        .setBody("Internal Server Error"));
            } else {
                logger.info("Request allowed: {}", route.request().url());
                route.resume();
            }
        });
    }

    public static void mockHotelCardOverride(Page page) {
        page.route("**/searchresults.html*", route -> {
            route.fulfill(new Route.FulfillOptions()
                    .setStatus(200)
                    .setContentType("application/json")
                    .setBody("""
                {
                  "hotels": [
                    {
                      "name": "This is a very very very very very very very very long hotel name that should wrap or break the UI",
                      "price": null
                    }
                  ]
                }
            """));
        });
    }

    public static void mockInfiniteTimeout(Page page) {
        page.route("**/searchresults.html*", route -> {
            // Never calls `route.fulfill` or `route.resume`
        });
    }
}