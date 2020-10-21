package org.example;

public class ParseUtil {

    public static double CutSpecialSymbols(String str)
    {
        String temp = str.replaceAll("[^0-9\\s+.]", "");
        temp.trim();
        return Double.parseDouble(temp);
    }

    public static double CutCharacters(String str)
    {
        String temp = str.replaceAll("[a-zA-Z\\s°+]", "");
        temp.trim();
        return Double.parseDouble(temp);
    }

    public ParseUtil()
    {

    }
}