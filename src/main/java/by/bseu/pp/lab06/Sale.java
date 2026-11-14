package by.bseu.pp.lab06;

import java.time.LocalDate;
/** Готовая неизменяемая модель. Все суммы в учебной работе имеют тип double. */
public final class Sale {
    private final String id;
    private final LocalDate date;
    private final String customer;
    private final String category;
    private final double amount;
    public Sale(String id, LocalDate date, String customer, String category, double amount) {
        this.id = validateText(id, "id");
        if (date == null) throw new DataFormatException("date: значение не задано");
        if (date.getYear() < 0 || date.getYear() > 9999) throw new DataFormatException("date: нужен четырёхзначный год 0000–9999");
        this.date = date;
        this.customer = validateText(customer, "customer");
        this.category = validateText(category, "category");
        if (!Double.isFinite(amount) || amount < 0)
            throw new DataFormatException("amount: требуется конечное число >= 0, получено " + amount);
        this.amount = amount == 0 ? 0 : amount;
    }
    private static String validateText(String value, String field) {
        if (value == null || value.isBlank()) throw new DataFormatException(field + ": требуется непустая строка");
        if (value.contains(";") || value.contains("\n") || value.contains("\r") || value.contains("\""))
            throw new DataFormatException(field + ": символы ;, кавычки и переносы строк запрещены учебным форматом");
        return value;
    }
    public String getId() { return id; }
    public LocalDate getDate() { return date; }
    public String getCustomer() { return customer; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
}
