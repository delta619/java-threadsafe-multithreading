package hotelapp;

import java.util.*;

public class HotelHandler {
    private Map<String, Hotel> hotelMap = new TreeMap<>(String::compareTo);

    public HotelHandler(){
    }

    public void insertHotels(Hotel[] hotels){

        for(Hotel hotel: hotels){
            this.hotelMap.put(hotel.getId(), hotel);
        }
    }

    public Hotel findHotelId(String hotelId){

        Hotel hotel = this.hotelMap.get(hotelId);

        String result = "";

        if(hotel == null){
            result = "No Hotel found with id : "+hotelId+ System.lineSeparator();
        }else{
            return hotel;
        }
        return null;
    }
    public void writeOutput( ReviewHandler reviewHandler, String outputFileName){
        for(String hotelId: this.hotelMap.keySet()){
            Helper.writeFile(outputFileName, this.hotelMap.get(hotelId).toString());
                for(Review review: reviewHandler.findReviewsByHotelId(hotelId, true)){
                    Helper.writeFile(outputFileName, review.toString());
                }
                    Helper.writeFile(outputFileName, System.lineSeparator());
        }
    }
}
