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

class CsvWriteTest {

    @TempDir Path dir;
    @Test void roundTripAndCreatesParents() throws IOException {
        List<Sale> input=List.of(TestData.sale("A","2026-09-01",12.5),TestData.sale("A","2026-09-01",1e20));
        Path file=dir.resolve("nested/out.csv");CsvSalesIO.write(file,input);
        TestData.same(input,CsvSalesIO.read(file));
        String text=Files.readString(file);assertTrue(text.contains("2026-09-01"));assertTrue(text.contains("Клиент А"));
    }
    @Test void overwritesNotAppends() throws IOException {
        Path file=dir.resolve("out.csv");Files.writeString(file,"OLD OLD OLD");
        CsvSalesIO.write(file,List.of());assertEquals(CsvSalesIO.HEADER,Files.readString(file).strip());
    }
    @Test void validatesBeforeTruncation() throws IOException {
        Path file=dir.resolve("out.csv");Files.writeString(file,"KEEP");
        assertThrows(IllegalArgumentException.class,()->CsvSalesIO.write(file,Arrays.asList((Sale)null)));
        assertEquals("KEEP",Files.readString(file));
    }
    @Test void nullList() { assertThrows(IllegalArgumentException.class,()->CsvSalesIO.write(dir.resolve("x"),null)); }
    @Test void directoryAsFile() { assertThrows(IOException.class,()->CsvSalesIO.write(dir,List.of())); }

}
