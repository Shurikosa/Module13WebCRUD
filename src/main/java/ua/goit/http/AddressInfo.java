package ua.goit.http;

public class AddressInfo {

    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private GeoInfo geo;

    public AddressInfo(String street, String suite, String city, String zipcode, GeoInfo geo){
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geo = geo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public GeoInfo getGeo() {
        return geo;
    }

    public void setGeo(GeoInfo geo) {
        this.geo = geo;
    }

    @Override
    public String toString(){
        return  "AddressInfo{" +
                "street='" + street + '\'' +
                ", suite='" + suite + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", geo=" + geo +
                '}';
    }
}
