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

class JsonReadTest {

    @TempDir Path dir;
    Path file(String text) throws IOException {return Files.writeString(dir.resolve("input.json"),text,StandardCharsets.UTF_8);}
    String valid() {return "{\"id\":\"A\",\"date\":\"01.09.2026\",\"customer\":\"Клиент\",\"category\":\"Офис\",\"amount\":12.5}";}
    @Test void readsObjectsAndDuplicates() throws IOException {
        List<Sale> sales=JsonSalesIO.read(file("["+valid()+","+valid()+"]"));
        assertEquals(2,sales.size());assertEquals("Клиент",sales.get(0).getCustomer());
        assertEquals(LocalDate.of(2026,9,1),sales.get(0).getDate());assertEquals(12.5,sales.get(1).getAmount());
    }
    @Test void emptyArray() throws IOException {assertTrue(JsonSalesIO.read(file("[]")).isEmpty());}
    @ParameterizedTest @ValueSource(strings={"", "null", "{}", "[", "[null]", "[1]", "[{}]", "[] trailing", "[//comment\n]", "[{'id':'A'}]"})
    void rejectsInvalidDocument(String text) throws IOException {Path path=file(text);assertThrows(DataFormatException.class,()->JsonSalesIO.read(path));}
    @ParameterizedTest @ValueSource(strings={"\"12.5\"", "null", "true", "-1", "1e999"})
    void rejectsAmount(String amount) throws IOException {
        Path path=file("["+valid().replace("12.5",amount)+"]");
        DataFormatException ex=assertThrows(DataFormatException.class,()->JsonSalesIO.read(path));
        assertTrue(ex.getMessage().contains("1"));assertTrue(ex.getMessage().contains("amount"));
    }
    @Test void rejectsWrongDateAndIdentifiesSecondRecord() throws IOException {
        Path path=file("["+valid()+","+valid().replace("01.09.2026","31.02.2026")+"]");
        DataFormatException ex=assertThrows(DataFormatException.class,()->JsonSalesIO.read(path));
        assertTrue(ex.getMessage().contains("2"));assertTrue(ex.getMessage().contains("date"));
    }
    @Test void ignoresExtraProperties() throws IOException {
        Path path=file("["+valid().replace("12.5}","12.5,\"extra\":true}")+"]");
        assertEquals(1,JsonSalesIO.read(path).size());
    }
    @Test void missingFile() {assertThrows(IOException.class,()->JsonSalesIO.read(dir.resolve("missing")));}

}
