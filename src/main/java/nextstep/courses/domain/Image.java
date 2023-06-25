package nextstep.courses.domain;

public class Image {
    private String url;

    public Image(String url) {
        this.url = url;
    }

    public Image() {
        this("");
    }

    public void updateUrl(String imgUrl) {
        url = imgUrl;
    }

    public boolean isSameImage(String imgUrl) {
        return url == imgUrl;
    }

    public String getUrl() {
        return url;
    }
}
