package hotelapp;

import com.google.gson.annotations.SerializedName;

public class Hotel {

    @SerializedName("f")
    private String name;
    private String id;
    @SerializedName("ad")
    private String address;

    private LL ll;
    static class LL {
        public String lat;
        public String lng;
    }

    public Hotel(String name, String id, String address){
        this.name = name;
        this.id = id;
        this.address = address;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public LL getLL(){
        return this.ll;
    }
    public String getLatitude(){
        return this.ll.lat;
    }
    public String getLongitude(){
        return this.ll.lng;
    }
    public String getAddress(){
        return this.address;
    }

    @Override
    public String toString() {
        return ("\tID - "+ this.getId() + "\n\tAddress - "+ this.getAddress() + "\n\tLatitude - "+ this.getLatitude()+ "\n\tLongitude - "+this.getLongitude());
    }
}
