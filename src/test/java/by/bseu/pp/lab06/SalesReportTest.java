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

class SalesReportTest {

    final LocalDate from=LocalDate.of(2026,9,1), to=LocalDate.of(2026,9,5);
    @Test void boundariesOrderDuplicatesAndIndependentContainer() {
        Sale a=TestData.sale("A","2026-09-01",10), b=TestData.sale("B","2026-09-05",20);
        List<Sale> input=new ArrayList<>(List.of(TestData.sale("X","2026-08-31",999),b,a,a,TestData.sale("Y","2026-09-06",999)));
        List<Sale> output=SalesReport.filterByPeriod(input,from,to);
        assertEquals(List.of(b,a,a),output);assertEquals(5,input.size());input.clear();assertEquals(3,output.size());
    }
    @Test void oneDay() {assertEquals(1,SalesReport.filterByPeriod(List.of(TestData.sale("A","2026-09-01",10)),from,from).size());}
    @Test void emptyAndNoMatches() {assertTrue(SalesReport.filterByPeriod(List.of(),from,to).isEmpty());assertTrue(SalesReport.filterByPeriod(List.of(TestData.sale("A","2026-08-01",10)),from,to).isEmpty());}
    @Test void invalidPeriod() {assertThrows(IllegalArgumentException.class,()->SalesReport.filterByPeriod(List.of(),to,from));}
    @Test void nullArguments() {
        assertThrows(IllegalArgumentException.class,()->SalesReport.filterByPeriod(null,from,to));
        assertThrows(IllegalArgumentException.class,()->SalesReport.filterByPeriod(List.of(),null,to));
        assertThrows(IllegalArgumentException.class,()->SalesReport.filterByPeriod(Arrays.asList((Sale)null),from,to));
    }
    @Test void sumsWithoutRounding() {assertEquals(.246,SalesReport.totalAmount(List.of(TestData.sale("A","2026-09-01",.123),TestData.sale("A","2026-09-01",.123))),1e-12);}
    @Test void emptyTotal() {assertEquals(0,SalesReport.totalAmount(List.of()));}
    @Test void nullTotal() {assertThrows(IllegalArgumentException.class,()->SalesReport.totalAmount(null));}

}
