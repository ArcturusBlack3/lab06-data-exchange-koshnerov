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

class CsvReadTest {

    @TempDir Path dir;
    Path file(String text) throws IOException { return Files.writeString(dir.resolve("input.csv"), text, StandardCharsets.UTF_8); }
    @Test void readsBomBlanksAndDuplicates() throws IOException {
        String line="S01;2026-09-01;Клиент;Офис;10";
        List<Sale> sales=CsvSalesIO.read(file("\uFEFF"+CsvSalesIO.HEADER+"\n\n"+line+"\n \n"+line+"\n"));
        assertEquals(2,sales.size()); assertEquals("Клиент", sales.get(0).getCustomer());
        assertEquals("S01",sales.get(1).getId());
    }
    @Test void headerOnlyIsEmpty() throws IOException { assertTrue(CsvSalesIO.read(file(CsvSalesIO.HEADER)).isEmpty()); }
    @ParameterizedTest @ValueSource(strings={"", "wrong", "date;id;customer;category;amount", " id;date;customer;category;amount"})
    void rejectsHeader(String text) throws IOException {
        Path path=file(text);assertThrows(DataFormatException.class,()->CsvSalesIO.read(path));
    }
    @Test void physicalLineAndNoSourceMutation() throws IOException {
        String text=CsvSalesIO.HEADER+"\n\nA;31.02.2026;C;Cat;10\n";Path path=file(text);
        DataFormatException ex=assertThrows(DataFormatException.class,()->CsvSalesIO.read(path));
        assertTrue(ex.getMessage().contains("3"));assertEquals(text,Files.readString(path));
    }
    @Test void missingFile() { assertThrows(IOException.class,()->CsvSalesIO.read(dir.resolve("missing"))); }
    @Test void nullPath() { assertThrows(IllegalArgumentException.class,()->CsvSalesIO.read(null)); }

}
