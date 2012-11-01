package csci498.strupper.munchlist;

public class Restaurant {

  private String name = "";
  private String address = "";
  private String type = "";
  private String feed = "";
  private Double lat = 0d;
  private Double lon = 0d;

  public Restaurant() { }

  public Restaurant(String name, String address, String type, String feed) {
    super();
    this.name = name;
    this.address = address;
    this.type = type;
    this.feed = feed;
  }

  public Restaurant(String name,
                    String address,
                    String type,
                    String feed,
                    Double lat,
                    Double lon) {
    super();
    this.name = name;
    this.address = address;
    this.type = type;
    this.feed = feed;
    this.lat = lat;
    this.lon = lon;
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

  public Double getLat() {
    return lat;
  }

  public void setLat(Double lat) {
    this.lat = lat;
  }

  public Double getLon() {
    return lon;
  }

  public void setLon(Double lon) {
    this.lon = lon;
  }
}
