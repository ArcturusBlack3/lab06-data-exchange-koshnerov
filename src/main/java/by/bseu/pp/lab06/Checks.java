package by.bseu.pp.lab06;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
/** Готовые проверки аргументов. */
public final class Checks {
    private Checks() { }
    public static void path(Path path) {
        if (path == null) throw new IllegalArgumentException("path: не задан");
    }
    public static void sales(List<Sale> sales) {
        if (sales == null || sales.stream().anyMatch(s -> s == null))
            throw new IllegalArgumentException("sales: список и элементы не должны быть null");
    }
    public static void period(LocalDate from, LocalDate to) {
        if (from == null || to == null || from.isAfter(to))
            throw new IllegalArgumentException("period: нужны даты from <= to");
    }
}
