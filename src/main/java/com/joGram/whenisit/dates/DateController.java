package com.joGram.whenisit.dates;


import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.spi.DateFormatProvider;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.spi.TimeZoneNameProvider;


@RestController
public class DateController {

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/date")
    public ResponseDate calculatedDate(@Valid @ModelAttribute("TransferDate") TransferDate transferDate, BindingResult result){
        if (result.hasErrors()) {
            System.out.println("found errors in date");
//            result.getAllErrors().forEach(errorObj -> {
//                System.out.println(errorObj.toString());
//            });
            return null;
        }
        else{

            // parse the date with the from-time zone afterward calculate the translated time from the to Time zone
            LocalDateTime selectedClientDate = null;


            // idea translate the date from the default js format utc to the from time zone to get the input date
            // then calculate the to time zone and return it
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_INSTANT;
            dateTimeFormatter = dateTimeFormatter.withZone(ZoneOffset.UTC);
            try {
                selectedClientDate = LocalDateTime.parse(transferDate.getDate(),dateTimeFormatter);
                selectedClientDate = selectedClientDate.minusMinutes(transferDate.getFromOffset());
            }
            catch (DateTimeParseException e){
                System.err.println(e);
            }

            ResponseDate responseDate  = null;

            if (selectedClientDate != null) {
//                ZoneId zoneId = ZoneId.of(transferDate.getFromTimeZone());
//                ZoneId zoneId1 = ZoneId.of(transferDate.getTimeZone());
//                System.out.println(zoneId.getId());
//                System.out.println(zoneId1.getId());
//                System.out.printf("same time zones? %1$b", ZoneId.of(transferDate.getFromTimeZone()).getId().equals(ZoneId.of(transferDate.getTimeZone()).getId()));
                System.out.println(selectedClientDate.getHour());
                ZonedDateTime wantedFromTime = selectedClientDate.atZone(ZoneId.of(transferDate.getFromTimeZone()));
                System.out.println(wantedFromTime.getHour());
                ZonedDateTime wantedToTime = wantedFromTime.withZoneSameInstant(ZoneId.of(transferDate.getTimeZone()));

                DateTimeFormatter outDateTimeFormatter = DateTimeFormatter.ISO_INSTANT;
                outDateTimeFormatter.withZone(ZoneId.of(transferDate.getTimeZone()));
                System.out.println(wantedToTime);
                String resultTime = wantedToTime.format(outDateTimeFormatter);
                System.out.println(resultTime);
                responseDate = new ResponseDate(resultTime, wantedToTime.getZone().toString());
                //TODO problem conversion from gmt+1 to gmt and with other offsets, which format should i choose as gmt+1 = utc?
            }



//            ZoneId utcZone = ZoneId.of("UTC");
//            ZonedDateTime.parse()
//            ZonedDateTime utcTime = ZonedDateTime.ofInstant(,utcZone)

            //old version for parsing
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//(int)DateFormat.LONG);//,DateCalculator.resolveLocale(date.getTimeZone()));
//            TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
//            utcTimeZone.setRawOffset(transferDate.getFromOffset());
//
//            TimeZone fromTimeZone = TimeZone.getTimeZone(transferDate.getFromTimeZone());//TimeZone.getTimeZone(zoneId);
//            TimeZone toTimeZone = TimeZone.getTimeZone(transferDate.getTimeZone());
//
//            // parse date as utc date
//            TimeZone.setDefault(utcTimeZone);
//            Calendar calendar = Calendar.getInstance(utcTimeZone);// default utc instance
//            dateFormat.setCalendar(calendar);
//
//
//            try{
//                inputDate = dateFormat.parse(transferDate.getDate());
//            }
//            catch (ParseException e){
//                System.out.println("couldnt parse the date");
//                return null;
//            }
//            // determine wanted start time with the "from" time zon
//            // the from time zone should only! change the timezone, but not the time!
////            TimeZone.setDefault(fromTimeZone);
//            calendar.setTimeZone(fromTimeZone);// default utc instance
//
//            dateFormat.setCalendar(calendar);
//
//            calendar.setTimeInMillis(inputDate.getTime());
//
//            // change to the wanted time zone
//            TimeZone.setDefault(toTimeZone);
//            calendar.setTimeZone(toTimeZone);
//            dateFormat.setCalendar(calendar);
//
//            Date changedDate = calendar.getTime();
//            String displayName = toTimeZone.toZoneId().getDisplayName(TextStyle.FULL,Locale.getDefault());
//
//            String resultDateString = dateFormat.format(changedDate);
//            System.out.println(displayName);
//            System.out.println(resultDateString);
//            ResponseDate responseDate = new ResponseDate(resultDateString,calendar.getTimeZone());


//            System.out.println(displayName);
//            System.out.println(resultDateString);
//            ResponseDate responseDate = new ResponseDate(resultDateString,calendar.getTimeZone());

            return responseDate;
        }

    }

}
