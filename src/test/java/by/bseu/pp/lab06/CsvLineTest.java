package by.bseu.pp.lab06;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class CsvLineTest {

    @Test void readsAllFields() {
        Sale sale = CsvSalesIO.parseLine("S01;01.09.2026;Клиент;Офис;12.50", 2);
        assertEquals("S01", sale.getId()); assertEquals("Клиент", sale.getCustomer());
        assertEquals("Офис", sale.getCategory()); assertEquals(LocalDate.of(2026,9,1), sale.getDate());
        assertEquals(12.5, sale.getAmount());
    }
    @Test void acceptsZero() { assertEquals(0, CsvSalesIO.parseLine("A;2026-09-01;C;Cat;0", 2).getAmount()); }
    @Test void acceptsExportExponent() { assertEquals(1e20, CsvSalesIO.parseLine("A;2026-09-01;C;Cat;1.0E20", 2).getAmount()); }
    @ParameterizedTest @ValueSource(strings={
        "A;2026-09-01;C;Cat", "A;2026-09-01;C;Cat;1;extra", "A;2026-09-01;C;Cat;",
        "A;2026-09-01;C;Cat;-1", "A;2026-09-01;C;Cat;NaN", "A;2026-09-01;C;Cat;Infinity",
        "A;2026-09-01;C;Cat;1,25", "A;2026-09-01;C;Cat;1e999", "A;31.02.2026;C;Cat;10",
        ";2026-09-01;C;Cat;10", "A;2026-09-01; ;Cat;10", "A;2026-09-01;C;Cat;0x1.0p2"})
    void errorIncludesPhysicalLine(String line) {
        DataFormatException ex = assertThrows(DataFormatException.class, () -> CsvSalesIO.parseLine(line, 7));
        assertTrue(ex.getMessage().contains("7"));
    }

}
