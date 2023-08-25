package nz.ac.aucklanduni.se306project1.utils;

import android.content.res.Resources;

import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

public class StringUtils {
    public static String getQuantity(
            final Resources res,
            final @PluralsRes int pluralId,
            final @StringRes int zeroId,
            final int quantity
    ) {
        if (quantity == 0) {
            return res.getString(zeroId);
        }
        return res.getQuantityString(pluralId, quantity, quantity);
    }
}
