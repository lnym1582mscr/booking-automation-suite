package ge.tbc.itacademy.enums;

public enum Filter {
    KITCHEN("Kitchen facilities", FilterCategory.MEAL),
    BREAKFAST("Breakfast included", FilterCategory.MEAL),
    ALL("All meals included", FilterCategory.MEAL),
    BREAKFAST_DINNER("Breakfast & dinner included", FilterCategory.MEAL),
    STAR1("1 star", FilterCategory.STARS),
    STAR2("2 stars", FilterCategory.STARS),
    STAR3("3 stars", FilterCategory.STARS),
    STAR4("4 stars", FilterCategory.STARS),
    STAR5("5 stars", FilterCategory.STARS);

    private final String label;
    private final FilterCategory category;

    Filter(String label, FilterCategory category) {
        this.label = label;
        this.category = category;
    }

    public String getLabel() {
        return label;
    }

    public FilterCategory getCategory() {
        return category;
    }

    public enum FilterCategory {
        MEAL, STARS
    }
}
