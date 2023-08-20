package nz.ac.aucklanduni.se306project1.models;

public interface SearchFilterable {

    /**
     * Determines whether this class matches the specified lowercase search query.
     *
     * @param query The search query
     * @return Whether this class matches the query
     */
    boolean matches(final String query);
}
