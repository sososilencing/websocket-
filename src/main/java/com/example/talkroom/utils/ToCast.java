package com.example.talkroom.utils;

import javax.validation.constraints.NotNull;
import java.text.NumberFormat;

/**
 * @author Roxi酱
 */
public class ToCast {
    public static int intCast(@NotNull String aim){
        try{
            return Integer.valueOf(aim);
        }catch (NumberFormatException e) {
            return -1;
        }
    }
    public static String stringCast(@NotNull int aim){
        try{
            return String.valueOf(aim);
        }catch (NumberFormatException e){
           return null;
        }
    }
}
