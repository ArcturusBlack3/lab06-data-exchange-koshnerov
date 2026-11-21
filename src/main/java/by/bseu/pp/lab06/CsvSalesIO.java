package by.bseu.pp.lab06;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public final class CsvSalesIO {
    public static final String HEADER = "id;date;customer;category;amount";
    private CsvSalesIO() { }

    public static Sale parseLine(String line, int lineNumber) {
        
        try{
            if(line==null){
                throw new DataFormatException("null");
            }

            String[] fields =line.split(";",-1);

            if(fields.length!=5){
                throw new DataFormatException("YOU NEED 5 FIELDS. THIS LENGHT IS "+ fields.length);
            }

            LocalDate date =DateParser.parseDate(fields[1]);
            
            String amountText=fields[4];

            if(!amountText.matches("[0-9]+(?:\\.[0-9]+)?(?:[eE][+-]?[0-9]+)?")){
                throw new  DataFormatException("amount is not correct ");
            }
            
            double amount=Double.parseDouble(amountText);

            if(!Double.isFinite(amount)||amount<0){
                throw new DataFormatException("amount is not correct");
            }

            return new Sale(fields[0], date, fields[2], fields[3], amount);

        }
        catch(DataFormatException|NumberFormatException e){
            throw new DataFormatException("CSV Line "+lineNumber+ ": "+e.getMessage(), e);
        }

    }
    
    
    public static List<Sale> read(Path path) throws IOException {
        Checks.path(path);
        List <Sale> sales=new ArrayList<>();

        try(BufferedReader reader=Files.newBufferedReader(path, StandardCharsets.UTF_8)){
            String header = reader.readLine();
            if(header==null){
                throw new DataFormatException("CSV file is empty");
            }

            if(header.startsWith("\uFEFF")){
                header=header.substring(1);
            }

            if(!HEADER.equals(header)){
                throw new DataFormatException("invalid CSV header: "+header);
            }

            String line;
            int lineNumber=1;

            while((line=reader.readLine())!=null){
                lineNumber++;

                if(line.isBlank()){
                    continue;
                }

                sales.add(parseLine(line, lineNumber));
            }
        }
        return  sales;
    }
    
    public static void write(Path path, List<Sale> sales) throws IOException {
        Checks.path(path);
        Checks.sales(sales);
        
         Path parent = path.toAbsolutePath().getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (BufferedWriter writer =
                     Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

            writer.write(HEADER);
            writer.newLine();

            for (Sale sale : sales) {
                writer.write(sale.getId() + ";" +sale.getDate() + ";" +sale.getCustomer() + ";" +sale.getCategory() + ";" +Double.toString(sale.getAmount()));

                writer.newLine();
            }
        }
    }
}