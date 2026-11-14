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

class JsonWriteTest {

    @TempDir Path dir;
    @Test void roundTrip() throws IOException {
        List<Sale> sales=List.of(TestData.sale("A","2026-09-01",0),TestData.sale("A","2026-09-01",12.5));
        Path path=dir.resolve("nested/out.json");JsonSalesIO.write(path,sales);TestData.same(sales,JsonSalesIO.read(path));
        var array=com.google.gson.JsonParser.parseString(Files.readString(path)).getAsJsonArray();
        assertTrue(array.get(1).getAsJsonObject().get("amount").getAsJsonPrimitive().isNumber());
        assertEquals("2026-09-01",array.get(0).getAsJsonObject().get("date").getAsString());
    }
    @Test void emptyOverwritesOldFile() throws IOException {
        Path path=dir.resolve("out.json");Files.writeString(path,"OLD");JsonSalesIO.write(path,List.of());
        assertEquals("[]",Files.readString(path).strip());
    }
    @Test void validatesBeforeTruncation() throws IOException {
        Path path=dir.resolve("out.json");Files.writeString(path,"KEEP");
        assertThrows(IllegalArgumentException.class,()->JsonSalesIO.write(path,Arrays.asList((Sale)null)));
        assertEquals("KEEP",Files.readString(path));
    }
    @Test void nullList() {assertThrows(IllegalArgumentException.class,()->JsonSalesIO.write(dir.resolve("x"),null));}
    @Test void nullPath() {assertThrows(IllegalArgumentException.class,()->JsonSalesIO.write(null,List.of()));}
    @Test void directoryAsFile() {assertThrows(IOException.class,()->JsonSalesIO.write(dir,List.of()));}

}
