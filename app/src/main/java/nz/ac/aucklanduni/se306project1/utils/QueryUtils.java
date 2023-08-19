package nz.ac.aucklanduni.se306project1.utils;

import androidx.appcompat.widget.SearchView;

import java.util.function.Consumer;

public class QueryUtils {

    public static SearchView.OnQueryTextListener createQueryChangeListener(
            final Consumer<String> listener
    ) {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                listener.accept(newText);
                return true;
            }
        };
    }
}
