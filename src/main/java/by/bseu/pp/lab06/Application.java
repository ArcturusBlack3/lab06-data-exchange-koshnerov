package by.bseu.pp.lab06;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
/** Готовая демонстрация. Рабочая папка запуска — корень проекта. */
public final class Application {
    private Application() { }
    public static void main(String[] args) {
        try {
            List<Sale> csv = CsvSalesIO.read(Path.of("data", "sales.csv"));
            List<Sale> json = JsonSalesIO.read(Path.of("data", "sales.json"));
            LocalDate from = DateParser.parseDate("01.09.2026");
            LocalDate to = DateParser.parseDate("2026-09-05");
            List<Sale> selected = SalesReport.filterByPeriod(csv, from, to);
            System.out.println("CSV: " + csv.size() + "; JSON: " + json.size());
            System.out.println("За период: " + selected.size());
            System.out.printf(Locale.ROOT, "Сумма: %.2f%n", SalesReport.totalAmount(selected));
            CsvSalesIO.write(Path.of("output", "period-sales.csv"), selected);
            JsonSalesIO.write(Path.of("output", "period-sales.json"), selected);
            System.out.println("Повторный импорт CSV: " + CsvSalesIO.read(Path.of("output", "period-sales.csv")).size());
            System.out.println("Повторный импорт JSON: " + JsonSalesIO.read(Path.of("output", "period-sales.json")).size());
            System.out.println("Файлы результата: output/period-sales.csv и output/period-sales.json");
        } catch (DataFormatException ex) {
            System.out.println("Ошибка данных: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Ошибка файла: " + ex.getMessage());
        }
        try {
            CsvSalesIO.read(Path.of("data", "sales-invalid.csv"));
        } catch (DataFormatException ex) {
            System.out.println("Ожидаемый отказ: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Ошибка файла: " + ex.getMessage());
        }
    }
}
