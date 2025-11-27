package thesis.domain;

public class Link {
  private final String url;
  private final String text;

  public Link(String url, String text) {
    this.url = url;
    this.text = text;
  }

  public String getUrl() {
    return url;
  }

  public String getText() {
    return text;
  }

}
