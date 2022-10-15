package hotelapp;

public class Helper {

    public static int countWords(String line, String checkWord){
        line = line.replaceAll("\\p{Punct}", " ");
        int cnt = 0;
        for(String word: line.split(" ")){
            if(word.equalsIgnoreCase(checkWord)){
                cnt++;
            }
        }
        return cnt;
    };
}


