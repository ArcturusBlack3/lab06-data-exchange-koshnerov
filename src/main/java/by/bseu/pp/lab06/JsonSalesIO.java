package by.bseu.pp.lab06;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
public final class JsonSalesIO {
    private JsonSalesIO() { }
    
    
    public static List<Sale> read(Path path) throws IOException {
        Checks.path(path);
        
         List<Sale> sales = new ArrayList<>();

        try (BufferedReader reader =
                     Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

            JsonArray array = JsonSupport.parseArray(reader);

            for (int i = 0; i < array.size(); i++) {
                JsonElement element = array.get(i);

                Sale sale = JsonSupport.toSale(element, i + 1);

                sales.add(sale);
            }
        }

        return sales;

    }
    
    public static void write(Path path, List<Sale> sales) throws IOException {
        Checks.path(path);
        Checks.sales(sales);
        
        Path parent = path.toAbsolutePath().getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        JsonArray array = new JsonArray();

        for (Sale sale : sales) {
            array.add(JsonSupport.toJson(sale));
        }

        try (BufferedWriter writer =
                     Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

            JsonSupport.GSON.toJson(array, writer);
        }
    }
}
