package hotelapp;

import java.util.HashMap;
import java.util.Map;

public class HotelHandler {
    private Map<String, Hotel> hotelMap = new HashMap<>();
    public HotelHandler(Hotel[] hotels){
        this.setHotels(hotels);
    }

    public void setHotels(Hotel[] hotels){

        for(Hotel hotel: hotels){
            this.hotelMap.put(hotel.getId(), hotel);
        }
    }

    public String findHotelId(String hotelId){
        Hotel hotel = this.hotelMap.get(hotelId);
        String result = "";

        if(hotel == null){
            result = ("No Hotel found with id : "+hotelId)+ System.lineSeparator();

        }else{
            result+=("The hotel details are:"+System.lineSeparator());
            result+=hotel.getName() + System.lineSeparator();
            result+=hotel+System.lineSeparator();
            result = hotel.toString();
        }
        return result;
    }
}
