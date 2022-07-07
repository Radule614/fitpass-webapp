package model.facility;

public final class Location {
    private double lat;
    private double lng;
    private String country;
    private String state;
    private String city;
    private String town;
    private String road;
    private String number;
    private String postcode;
    public Location(){}

    public double getLat() {
        return lat;
    }
    public double getLng() {
        return lng;
    }
    public String getCountry() {
        return country;
    }
    public String getState() {
        return state;
    }
    public String getCity() {
        return city;
    }
    public String getTown() {
        return town;
    }
    public String getRoad() {
        return road;
    }
    public String getNumber() {
        return number;
    }
    public String getPostcode() {
        return postcode;
    }
}
