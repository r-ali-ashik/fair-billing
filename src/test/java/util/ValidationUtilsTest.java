package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.ValidationUtils.*;

@DisplayName("Validation Utils Tests")
class ValidationUtilsTest {

    @Test
    @DisplayName("Test isValidSession with valid session")
    void testIsValidSession_ValidSession() {
        assertTrue(isValidSession("00:01:00 user1 Start"));
    }

    @Test
    @DisplayName("Test isValidSession with invalid session")
    void testIsValidSession_InvalidSession() {
        assertFalse(isValidSession("00:01:00 user1"));
    }

    @Test
    @DisplayName("Test isValidTimestamp with valid timestamp")
    void testIsValidTimestamp_ValidTimestamp() {
        assertTrue(isValidTimestamp("00:01:00"));
    }

    @Test
    @DisplayName("Test isValidTimestamp with invalid timestamp")
    void testIsValidTimestamp_InvalidTimestamp() {
        assertFalse(isValidTimestamp("25:01:00"));
    }

    @Test
    @DisplayName("Test isValidUsername with valid username")
    void testIsValidUsername_ValidUsername() {
        assertTrue(isValidUsername("user1"));
    }

    @Test
    @DisplayName("Test isValidUsername with invalid username")
    void testIsValidUsername_InvalidUsername() {
        assertFalse(isValidUsername("user!"));
    }

    @Test
    @DisplayName("Test isValidIndicator with valid indicator")
    void testIsValidIndicator_ValidIndicator() {
        assertTrue(isValidIndicator("Start"));
        assertTrue(isValidIndicator("End"));
    }

    @Test
    @DisplayName("Test isValidIndicator with invalid indicator")
    void testIsValidIndicator_InvalidIndicator() {
        assertFalse(isValidIndicator("begin"));
    }
}