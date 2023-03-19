package sn.sastrans.backofficev2.trace.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TraceUtil {

    public static  String formatDurationToString(String heureDebut,String heureFin ){
        if(heureDebut!=null && !heureDebut.isEmpty() && heureFin!=null && !heureFin.isEmpty()){
            LocalTime debut = LocalTime.parse(heureDebut);
            LocalTime fin = LocalTime.parse(heureFin);
            Duration tempsEcoule = Duration.between(debut, fin);
            Long hours= tempsEcoule.toHours();
            Long minutes= tempsEcoule.toMinutes() % 60;
            return String.format("%02d:%02d",hours,minutes);
        }else return "NON";



    }
    public static  String distanceBetweenPka(String pkaDebut,String pkaFin ){
        if(pkaDebut!=null && !pkaDebut.isEmpty() && pkaFin!=null && !pkaFin.isEmpty()){
            String[] pkaDebutStr = pkaDebut.split("\\+");
            String[] pkaFinStr = pkaFin.split("\\+");
            log.info("pkadebut1 "+pkaDebutStr[0]);
            log.info("pka2debut2 "+pkaDebutStr[1]);
            Integer pkaDebutmetre= Integer.parseInt(pkaDebutStr[0]) *1000+ Integer.parseInt(pkaDebutStr[1]);
            Integer pkaFinmetre= Integer.parseInt(pkaFinStr[0]) *1000+ Integer.parseInt(pkaFinStr[1]);
            String distance =String.valueOf(Math.abs(pkaFinmetre - pkaDebutmetre));

            return distance;
        }else return "NON";



    }

    public static  String formatLocalDateTimeToString(LocalDateTime date) {

        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String formattedDate = date.format(formatter);
            return formattedDate;
        } else {
            return "NON";
        }

    }

}
