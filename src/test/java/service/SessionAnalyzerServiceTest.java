package service;

import model.UsageSummary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Session Analyzer Service Tests")
class SessionAnalyzerServiceTest {

    @Test
    @DisplayName("Analyze session with empty input")
    void testAnalyzeSession_EmptyInput() {
        String input = "";

        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        SessionAnalyzerService sessionAnalyzerService = new SessionAnalyzerService();
        Map<String, UsageSummary> usageSummaryMap = sessionAnalyzerService.analyzeSession(bufferedReader);

        assertTrue(usageSummaryMap.isEmpty());
    }

    @Test
    @DisplayName("Analyze session with one session")
    void testAnalyzeSession_oneSession() {
        String input = "14:00:01 user1 Start\n" +
                "14:00:03 user1 End\n";

        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        SessionAnalyzerService sessionAnalyzerService = new SessionAnalyzerService();
        Map<String, UsageSummary> usageSummaryMap = sessionAnalyzerService.analyzeSession(bufferedReader);

        assertEquals(1, usageSummaryMap.size());

        UsageSummary usageSummaryUser = usageSummaryMap.get("user1");
        assertEquals(1, usageSummaryUser.getTotalSession());
        assertEquals(2, usageSummaryUser.getTotalTime());
    }

    @Test
    @DisplayName("Analyze session with multiple sessions for a single user")
    void testAnalyzeSession_MultipleSessions() {
        String input = "00:00:01 user1 Start\n" +
                "00:00:03 user1 End\n" +
                "12:00:05 user1 Start\n" +
                "12:00:07 user1 End\n";

        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        SessionAnalyzerService sessionAnalyzerService = new SessionAnalyzerService();
        Map<String, UsageSummary> usageSummaryMap = sessionAnalyzerService.analyzeSession(bufferedReader);

        assertEquals(1, usageSummaryMap.size());

        UsageSummary usageSummaryUser = usageSummaryMap.get("user1");
        assertEquals(2, usageSummaryUser.getTotalSession());
        assertEquals(4, usageSummaryUser.getTotalTime());
    }


    @Test
    @DisplayName("Analyze session with invalid session indicator")
    void testAnalyzeSession_InvalidSessionIndicator() {
        String input = "00:01:00 user1 begin\n";

        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        SessionAnalyzerService sessionAnalyzerService = new SessionAnalyzerService();
        Map<String, UsageSummary> usageSummaryMap = sessionAnalyzerService.analyzeSession(bufferedReader);
        assertTrue(usageSummaryMap.isEmpty());
    }


    @Test
    @DisplayName("Analyze session with invalid time format")
    void testAnalyzeSession_InvalidTimeFormat() {
        String input = "25:00:00 user1 Start\n";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));
        SessionAnalyzerService sessionAnalyzerService = new SessionAnalyzerService();
        Map<String, UsageSummary> usageSummaryMap = sessionAnalyzerService.analyzeSession(bufferedReader);
        assertTrue(usageSummaryMap.isEmpty());
    }


    @Test
    @DisplayName("Analyze session with invalid username")
    void testAnalyzeSession_InvalidUsername() {
        String input = "00:01:00 user! start\n";

        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        SessionAnalyzerService sessionAnalyzerService = new SessionAnalyzerService();
        Map<String, UsageSummary> usageSummaryMap = sessionAnalyzerService.analyzeSession(bufferedReader);

        assertTrue(usageSummaryMap.isEmpty());
    }

    @Test
    @DisplayName("Analyze session with multiple users")
    void testAnalyzeSession_MultipleUsers() {
        String input = "00:00:01 user1 Start\n" +
                "00:00:03 user1 End\n" +
                "23:00:05 user2 Start\n" +
                "23:00:07 user2 End\n";

        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        SessionAnalyzerService sessionAnalyzerService = new SessionAnalyzerService();
        Map<String, UsageSummary> usageSummaryMap = sessionAnalyzerService.analyzeSession(bufferedReader);

        assertEquals(2, usageSummaryMap.size());

        UsageSummary usageSummaryUser1 = usageSummaryMap.get("user1");
        assertEquals(1, usageSummaryUser1.getTotalSession());
        assertEquals(2, usageSummaryUser1.getTotalTime());

        UsageSummary usageSummaryUser2 = usageSummaryMap.get("user2");
        assertEquals(1, usageSummaryUser2.getTotalSession());
        assertEquals(2, usageSummaryUser2.getTotalTime());
    }

    @Test
    @DisplayName("Analyze session with one session, session which doesn't have a matching end End indicator")
    void testAnalyzeSession_UnfinishedSession() {
        String input = "00:00:01 user1 Start\n";

        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        SessionAnalyzerService sessionAnalyzerService = new SessionAnalyzerService();
        Map<String, UsageSummary> usageSummaryMap = sessionAnalyzerService.analyzeSession(bufferedReader);

        assertEquals(1, usageSummaryMap.size());

        UsageSummary usageSummaryUser = usageSummaryMap.get("user1");
        assertEquals(1, usageSummaryUser.getTotalSession());
        assertEquals(0, usageSummaryUser.getTotalTime());
    }

    @Test
    @DisplayName("Analyze session with one session, session which doesn't have a matching end Start indicator")
    void testAnalyzeSession_ContinuedSession() {
        String input = "00:00:01 user1 End\n";

        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        SessionAnalyzerService sessionAnalyzerService = new SessionAnalyzerService();
        Map<String, UsageSummary> usageSummaryMap = sessionAnalyzerService.analyzeSession(bufferedReader);

        assertEquals(1, usageSummaryMap.size());

        UsageSummary usageSummaryUser = usageSummaryMap.get("user1");
        assertEquals(1, usageSummaryUser.getTotalSession());
        assertEquals(0, usageSummaryUser.getTotalTime());
    }

    @Test
    @DisplayName("Analyze session with two sessions with one Session without matching Start)")
    void testAnalyzeSession_SessionWithoutMatchingStart() {
        String input = "14:00:01 user1 Start\n" +
                "14:00:10 user1 End\n" +
        "14:00:11 user2 End\n" ;

        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        SessionAnalyzerService sessionAnalyzerService = new SessionAnalyzerService();
        Map<String, UsageSummary> usageSummaryMap = sessionAnalyzerService.analyzeSession(bufferedReader);

        assertEquals(2, usageSummaryMap.size());

        UsageSummary usageSummaryUser = usageSummaryMap.get("user2");
        assertEquals(1, usageSummaryUser.getTotalSession());
        assertEquals(10, usageSummaryUser.getTotalTime());
    }

    @Test
    @DisplayName("Analyze session with two sessions with one Session without matching End)")
    void testAnalyzeSession_SessionWithoutMatchingEnd() {
        String input = "14:00:01 user1 Start\n" +
                "14:00:10 user2 End\n" +
                "14:00:21 user2 End\n" ;

        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        SessionAnalyzerService sessionAnalyzerService = new SessionAnalyzerService();
        Map<String, UsageSummary> usageSummaryMap = sessionAnalyzerService.analyzeSession(bufferedReader);

        assertEquals(2, usageSummaryMap.size());

        UsageSummary usageSummaryUser = usageSummaryMap.get("user1");
        assertEquals(1, usageSummaryUser.getTotalSession());
        assertEquals(20, usageSummaryUser.getTotalTime());
    }
}
