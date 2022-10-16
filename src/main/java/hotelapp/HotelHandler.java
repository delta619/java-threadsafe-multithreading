package hotelapp;

import java.util.*;

public class HotelHandler {
    private Map<String, Hotel> hotelMap = new TreeMap<>(String::compareTo);

    public HotelHandler(Hotel[] hotels){
        this.setHotels(hotels);
    }

    public void setHotels(Hotel[] hotels){

        for(Hotel hotel: hotels){
            this.hotelMap.put(hotel.getId(), hotel);
        }
    }

    public String findHotelId(String hotelId, boolean printFormat){

        Hotel hotel = this.hotelMap.get(hotelId);
        if(printFormat){
            return hotel.toString();
        }
        String result = "";

        if(hotel == null){
            result = "No Hotel found with id : "+hotelId+ System.lineSeparator();
        }else{
            result += ("The hotel details are: "+System.lineSeparator());
            result += hotel.getName() + System.lineSeparator();
            result += hotel+System.lineSeparator();
        }
        return result;
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
