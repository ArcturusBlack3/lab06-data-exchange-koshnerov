package by.bseu.pp.lab06;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public final class SalesReport {
    private SalesReport() { }
    
    public static List<Sale> filterByPeriod(List<Sale> sales, LocalDate from, LocalDate to) {
        Checks.sales(sales);
        Checks.period(from, to);
        
        List<Sale> filtered = new ArrayList<>();

        for (Sale sale : sales) {
            LocalDate date = sale.getDate();

            if (!date.isBefore(from) && !date.isAfter(to)) {
                filtered.add(sale);
            }
        }

        return filtered;
        
    }
    
    public static double totalAmount(List<Sale> sales) {
        Checks.sales(sales);
        
        double total = 0;

        for (Sale sale : sales) {
            total += sale.getAmount();
        }

        return total;
    }

}
