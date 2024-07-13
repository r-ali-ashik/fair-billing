package service;

import model.UsageSummary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Console Output Service Tests")
class ConsoleOutputServiceTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Should return empty map when input is empty")
    void testPrintUsageSummary() {
        ConsoleOutputService consoleOutputService = new ConsoleOutputService();

        Map<String, UsageSummary> usageSummaryMap = new HashMap<>();
        UsageSummary usageSummary = new UsageSummary();
        usageSummary.setTotalSession(5);
        usageSummary.setTotalTime(300);
        usageSummaryMap.put("user1", usageSummary);

        consoleOutputService.printUsageSummary(usageSummaryMap);
        assertEquals("user1 5 300\n", outputStreamCaptor.toString());
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }
}