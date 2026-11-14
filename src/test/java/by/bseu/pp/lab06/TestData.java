package by.bseu.pp.lab06;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
final class TestData {
    static Sale sale(String id, String date, double amount) {
        return new Sale(id, LocalDate.parse(date), "Клиент А", "Офис", amount);
    }
    static void same(List<Sale> expected, List<Sale> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Sale a = expected.get(i), b = actual.get(i);
            assertEquals(a.getId(), b.getId());
            assertEquals(a.getDate(), b.getDate());
            assertEquals(a.getCustomer(), b.getCustomer());
            assertEquals(a.getCategory(), b.getCategory());
            assertEquals(a.getAmount(), b.getAmount(), 0.0);
        }
    }
}
