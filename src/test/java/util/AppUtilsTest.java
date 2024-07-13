package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static util.AppUtils.*;
import static util.AppUtils.getFileName;

@DisplayName("App Utils Tests")
class AppUtilsTest {

    @Test
    @DisplayName("Test isEmpty with null CharSequence")
    void testIsEmpty_NullCharSequence() {
        assertTrue(isEmpty((CharSequence) null));
    }

    @Test
    @DisplayName("Test isEmpty with empty CharSequence")
    void testIsEmpty_EmptyCharSequence() {
        assertTrue(isEmpty(""));
    }

    @Test
    @DisplayName("Test isEmpty with non-empty CharSequence")
    void testIsEmpty_NonEmptyCharSequence() {
        assertFalse(isEmpty("test"));
    }

    @Test
    @DisplayName("Test isEmpty with null String")
    void testIsEmpty_NullString() {
        assertTrue(isEmpty((String) null));
    }

    @Test
    @DisplayName("Test isEmpty with empty String")
    void testIsEmpty_EmptyString() {
        assertTrue(isEmpty(""));
    }

    @Test
    @DisplayName("Test isEmpty with non-empty String")
    void testIsEmpty_NonEmptyString() {
        assertFalse(isEmpty("test"));
    }

    @Test
    @DisplayName("Test isEmpty with null Map")
    void testIsEmpty_NullMap() {
        assertTrue(isEmpty((Map<?, ?>) null));
    }

    @Test
    @DisplayName("Test isEmpty with empty Map")
    void testIsEmpty_EmptyMap() {
        assertTrue(isEmpty(new HashMap<>()));
    }

    @Test
    @DisplayName("Test isEmpty with non-empty Map")
    void testIsEmpty_NonEmptyMap() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertFalse(isEmpty(map));
    }

    @Test
    @DisplayName("Test isEmpty with null Collection")
    void testIsEmpty_NullCollection() {
        assertTrue(isEmpty((Collection) null));
    }

    @Test
    @DisplayName("Test isEmpty with empty Collection")
    void testIsEmpty_EmptyCollection() {
        assertTrue(isEmpty(new ArrayList<>()));
    }

    @Test
    @DisplayName("Test isEmpty with non-empty Collection")
    void testIsEmpty_NonEmptyCollection() {
        List<String> list = new ArrayList<>();
        list.add("item");
        assertFalse(isEmpty(list));
    }

    @Test
    @DisplayName("Test isNull with null Object")
    void testIsNull_NullObject() {
        assertTrue(isNull(null));
    }

    @Test
    @DisplayName("Test isNull with non-null Object")
    void testIsNull_NonNullObject() {
        assertFalse(isNull(new Object()));
    }



    @Test
    @DisplayName("Test getFileName with valid .txt file in arguments")
    void testGetFileName_ValidTxtFile() {
        String[] args = {"arg1", "arg2.txt", "arg3"};
        assertEquals("arg2.txt", getFileName(args));
    }

    @Test
    @DisplayName("Test getFileName with multiple .txt files in arguments")
    void testGetFileName_MultipleTxtFiles() {
        String[] args = {"arg1.txt", "arg2.txt", "arg3"};
        assertEquals("arg1.txt", getFileName(args));
    }

    @Test
    @DisplayName("Test getFileName with no .txt file in arguments")
    void testGetFileName_NoTxtFile() {
        String[] args = {"arg1", "arg2", "arg3"};
        assertNull(getFileName(args));
    }

    @Test
    @DisplayName("Test getFileName with empty arguments")
    void testGetFileName_EmptyArgs() {
        String[] args = {};
        assertNull(getFileName(args));
    }

}