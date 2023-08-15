package nz.ac.aucklanduni.se306project1.models;

import java.util.Objects;

public class ImageInfo {
    private String url;
    private String description;

    public ImageInfo() {
    }

    public ImageInfo(final String url, final String description) {
        this.url = url;
        this.description = description;
    }

    public String getUrl() {
        return this.url;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final ImageInfo imageInfo = (ImageInfo) o;
        return Objects.equals(this.url, imageInfo.url) &&
                Objects.equals(this.description, imageInfo.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.url, this.description);
    }
}
