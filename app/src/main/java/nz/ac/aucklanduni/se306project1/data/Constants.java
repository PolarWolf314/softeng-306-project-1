package nz.ac.aucklanduni.se306project1.data;

public class Constants {
    public static class IntentKeys {
        public static final String CATEGORY_ID = "CategoryId";
        public static final String ITEM_ID = "ItemId";
    }

    public static class FilterKeys {
        public static final String CATEGORY_FILTERING = "CategoryFiltering";
        public static final String SEARCH_QUERY = "SearchQuery";

    }

    public static class PreferencesKeys {
        public static final String USER_SETTINGS_FILE_NAME = "User settings";
        public static final String FIRST_TIME_PREFERENCE_KEY = "firstTime";
    }

    public static class ToastMessages {
        public static final String INCORRECT_USERNAME_OR_PASSWORD = "Username and/or password is incorrect";
        public static final String EMAIL_ALREADY_IN_USE = "Email address already in use";
        public static final String WEAK_PASSWORD = "Password must be at least 6 characters in length";
        public static final String INVALID_EMAIL = "Invalid email address";
        public static final String CONFIRMED_PASSWORD_MISMATCH = "Confirmed password must match password";
        public static final String USER_DATA_CLEARED = "Cleared shopping cart and watchlist";
        public static final String ITEM_ADDED_TO_CART = "Item added to cart";
        public static final String CHECKOUT_MESSAGE = "Order placed";
    }

    public static class Colours {
        public static final double WCAG_AA_TEXT_CONTRAST_RATIO = 4.5;
    }
}
