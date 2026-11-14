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

class ExchangeIntegrationTest {

    @TempDir Path dir;
    @Test void fullExchangeAndSourceUnchanged() throws IOException {
        Path csv=Path.of("data/sales.csv"),json=Path.of("data/sales.json");
        String before=Files.readString(csv);
        List<Sale> all=CsvSalesIO.read(csv);TestData.same(all,JsonSalesIO.read(json));
        assertEquals(10,all.size());
        List<Sale> filtered=SalesReport.filterByPeriod(all,LocalDate.of(2026,9,1),LocalDate.of(2026,9,5));
        assertEquals(8,filtered.size());assertEquals(1240,SalesReport.totalAmount(filtered),1e-9);
        Path c=dir.resolve("report.csv"),j=dir.resolve("report.json");
        CsvSalesIO.write(c,filtered);JsonSalesIO.write(j,filtered);
        TestData.same(filtered,CsvSalesIO.read(c));TestData.same(filtered,JsonSalesIO.read(j));
        assertEquals(before,Files.readString(csv));
    }
}

