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

class DateParserTest {

    @ParameterizedTest @CsvSource({"2026-09-01,2026-09-01", "01.09.2026,2026-09-01",
            "29.02.2024,2024-02-29", "2024-02-29,2024-02-29"})
    void valid(String text, String expected) {
        assertEquals(LocalDate.parse(expected), DateParser.parseDate(text));
    }
    @ParameterizedTest @ValueSource(strings = {"", " ", "2026-02-29", "31.02.2026", "31.04.2026",
            "2026-13-01", "1.09.2026", "2026-9-01", " 2026-09-01", "2026-09-01T10:00", "2026/09/01"})
    void rejectsInvalid(String text) {
        DataFormatException ex = assertThrows(DataFormatException.class, () -> DateParser.parseDate(text));
        assertTrue(ex.getMessage().contains("date"));
    }
    @Test void rejectsNull() { assertThrows(DataFormatException.class, () -> DateParser.parseDate(null)); }

}
