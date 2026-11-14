package by.bseu.pp.lab06;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
public final class CsvSalesIO {
    public static final String HEADER = "id;date;customer;category;amount";
    private CsvSalesIO() { }
    /** TODO 2. Ровно пять полей. split с -1 сохраняет пустое последнее поле. */
    public static Sale parseLine(String line, int lineNumber) {
        // TODO 2: реализуйте по условию лабораторной работы.
        throw new UnsupportedOperationException("TODO 2");
    }
    /** TODO 3. UTF-8, обязательный заголовок, BOM допустим только перед заголовком.
     * Пустые строки пропускаются. При ошибке импорт целиком отклоняется. */
    public static List<Sale> read(Path path) throws IOException {
        Checks.path(path);
        // TODO 3: реализуйте по условию лабораторной работы.
        throw new UnsupportedOperationException("TODO 3");
    }
    /** TODO 4. Проверки до открытия файла. Создать папки, перезаписать файл в UTF-8.
     * Дата ISO, сумма через Double.toString, порядок и повторы сохраняются. */
    public static void write(Path path, List<Sale> sales) throws IOException {
        Checks.path(path);
        Checks.sales(sales);
        // TODO 4: реализуйте по условию лабораторной работы.
        throw new UnsupportedOperationException("TODO 4");
    }
}
