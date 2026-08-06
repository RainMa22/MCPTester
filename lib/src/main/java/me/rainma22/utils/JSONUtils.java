package me.rainma22.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {
    public static boolean isValidJSON(String s){
        try{
            new JSONObject(s);
            return true;
        } catch(JSONException je){
            return false;
        }
    }
    
}
