package by.bseu.pp.lab06;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
public final class DateParser {
    private DateParser() { }
    
    public static LocalDate parseDate(String text) {
        
        if(text==null){
            throw new DataFormatException("date: NULL");
        }

        DateTimeFormatter europeanFormatter=DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);

            

        try{

            if(text.matches("\\d{4}-\\d{2}-\\d{2}")){
                return LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
            }

            if(text.matches("\\d{2}\\.\\d{2}\\.\\d{4}")){
                return  LocalDate.parse(text, europeanFormatter);
            }

            throw new DataFormatException("invalid date "+text);

        }
        catch(DateTimeParseException e){
            
            throw new DataFormatException("invalid date "+ text, e);

        }

    }
}
