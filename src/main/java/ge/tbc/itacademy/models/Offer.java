package ge.tbc.itacademy.models;

import com.microsoft.playwright.Locator;

import java.util.Objects;

public class Offer {
    private final Locator locator;
    private final String
            title,
            type,
            reviewCount,
            price;

    public Offer(Locator locator, String title, String type, String reviewCount, String price) {
        this.locator = locator;
        this.title = title;
        this.type = type;
        this.reviewCount = reviewCount;
        this.price = price;
    }

    public Locator getLocator() {
        return locator;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(title, offer.title) &&
                Objects.equals(price, offer.price) &&
                Objects.equals(reviewCount, offer.reviewCount) &&
                Objects.equals(type, offer.type);
    }
}
