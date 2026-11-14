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
    /** TODO 5. Читать UTF-8 через JsonSupport.parseArray и toSale.
     * Нумерация записей с 1; порядок и повторы сохраняются. */
    public static List<Sale> read(Path path) throws IOException {
        Checks.path(path);
        // TODO 5: реализуйте по условию лабораторной работы.
        throw new UnsupportedOperationException("TODO 5");
    }
    /** TODO 6. Проверить аргументы до открытия. Собрать JsonArray через toJson,
     * создать папки и записать JSON через GSON.toJson(array, writer). */
    public static void write(Path path, List<Sale> sales) throws IOException {
        Checks.path(path);
        Checks.sales(sales);
        // TODO 6: реализуйте по условию лабораторной работы.
        throw new UnsupportedOperationException("TODO 6");
    }
}
