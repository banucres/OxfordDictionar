package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonUtility {
    public static boolean isJsonValid(String jsonStr) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonStr);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
