package csci498.strupper.munchlist;

public class Restaurant {

  private String name = "";
  private String address = "";
  private String type = "";
  private String feed = "";

  public Restaurant() { }

  public Restaurant(String name, String address, String type, String feed) {
    super();
    this.name = name;
    this.address = address;
    this.type = type;
    this.feed = feed;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return getName();
  }

  public String getFeed() {
    return feed;
  }

  public void setFeed(String feed) {
    this.feed = feed;
  }
}
