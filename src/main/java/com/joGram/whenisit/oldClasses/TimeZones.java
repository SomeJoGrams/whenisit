package com.joGram.whenisit.oldClasses;

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeZones {

    private HashSet<String> timeZoneSymbols;
    private HashSet<String> timeZoneCities;
    public TimeZones(){
        //Collections.synchronizedSet()
        timeZoneCities = new HashSet<>();
        timeZoneSymbols = new HashSet<>();
    }

    public void addCities(Collection<String> collection){
        timeZoneCities.addAll(collection);
    }

    public void addSymbols(Collection<String> collection){
        timeZoneSymbols.addAll(collection);
    }

    // expected String result "City, Timezone" TODO maybe wrap in object
    public String timeZoneCityPair(String cityOrSymbol){
        StringBuilder sB = new StringBuilder();
        if (this.isSymbol(cityOrSymbol)){
            sB.append(ZoneId.of(cityOrSymbol).getDisplayName(TextStyle.NARROW, Locale.GERMAN));
            sB.append(", ");
            sB.append(cityOrSymbol);
        }
        else if (this.isCity(cityOrSymbol)){
            String result = ZoneId.SHORT_IDS.get(cityOrSymbol);
            if (result == null){
                sB.append(cityOrSymbol);
            }
            else{
                sB.append(result);
                sB.append(", ");
                sB.append(cityOrSymbol);
            }
        }
        return sB.toString();
    }

    private boolean isSymbol(String symbol){
        return timeZoneSymbols.contains(symbol);
    }
    private boolean isCity(String city){
        return timeZoneCities.contains(city);
    }

    public String[] getTimeZones(){
        return Stream.concat(timeZoneCities.stream(), timeZoneSymbols.stream()).toArray(String[]::new);
    }

}
