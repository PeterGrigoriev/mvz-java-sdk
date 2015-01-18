package com.movilizer.util.string;

import org.testng.annotations.Test;

import static com.movilizer.util.string.StringUtils.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class StringUtilsTest {
    @Test
    public void testPhoneToDeviceAddress() throws Exception {
        assertEquals(phoneToDeviceAddress("003361234567"), "+3361234567");
        assertEquals(phoneToDeviceAddress("+3361234567"), "+3361234567");
        // TODO: proceed here
        // assertEquals(phoneToDeviceAddress("+33 6 123 456 7"), "+3361234567");
    }

    @Test
    public void testEmailToDeviceAddress() throws Exception {

        assertEquals(emailToDeviceAddress("Peter.Grigoriev@movilizer.com"), "@Peter.Grigoriev@movilizer.com");
        assertEquals(emailToDeviceAddress("@Peter.Grigoriev@movilizer.com"), "@Peter.Grigoriev@movilizer.com");
    }

    @Test
    public void testJoinOmitEmpties() {
        assertEquals(joinOmitEmpties("-", "a", "b"), "a-b");
        assertEquals(joinOmitEmpties("-", "a", null, "b", ""), "a-b");
    }

    @Test
    public void testRemoveSuffix() throws Exception {
        assertEquals(removeSuffix("AB", "B"), "A");
        assertEquals(removeSuffix("ABC", "C"), "AB");
        assertEquals(removeSuffix("ABC", "B"), "ABC");
        assertEquals(removeSuffix("ABC", ""), "ABC");
        assertEquals(removeSuffix("", ""), "");
        assertEquals(removeSuffix("ABC", null), "ABC");
        assertEquals(removeSuffix(null, "ABC"), null);
    }

    @Test
    public void testRemovePrefix() throws Exception {
        assertEquals(removePrefix("AB", "A"), "B");
        assertEquals(removePrefix("ABC", "AB"), "C");
        assertEquals(removePrefix("ABC", "B"), "ABC");
        assertEquals(removePrefix("ABC", ""), "ABC");
        assertEquals(removePrefix("", ""), "");
        assertEquals(removePrefix("ABC", null), "ABC");
        assertEquals(removePrefix(null, "ABC"), null);
    }


    @Test
    public void testRemoveSuffixes() throws Exception {
        assertEquals(removeSuffixes("ABC"), "ABC");
        assertEquals(removeSuffixes("ABC", "B", "C"), "AB");
        assertEquals(removeSuffixes("ABC", "C", "B"), "AB");
        assertEquals(removeSuffixes("ABC", "A", "Z"), "ABC");
    }

    @Test
    public void testIsNullOrEmpty() throws Exception {
        assertTrue(isNullOrEmpty(null));
        assertTrue(isNullOrEmpty(""));
        assertFalse(isNullOrEmpty("ABC"));


        @SuppressWarnings({"StringBufferReplaceableByString", "StringBufferMayBeStringBuilder", "MismatchedQueryAndUpdateOfStringBuilder"})
        StringBuffer stringBuffer = new StringBuffer();
        assertTrue(isNullOrEmpty(stringBuffer.toString()));

    }

    @Test
    public void testReplaceXMLPredefinedEntitiesTest() throws Exception {
        assertTrue(replaceXMLPredefinedEntities("A<B").contains("A&lt;B"));
        assertTrue(replaceXMLPredefinedEntities("A>B").contains("A&gt;B"));
        assertTrue(replaceXMLPredefinedEntities("A&B").contains("A&amp;B"));
        assertTrue(replaceXMLPredefinedEntities("A'B").contains("A&apos;B"));
        assertTrue(replaceXMLPredefinedEntities("A\"B").contains("A&quot;B"));
    }

    @Test
    public void testFormatNoLocale() {
        String result = formatNoLocale("Hello {0}!", "David");
        assertEquals(result, "Hello David!");
    }

    @Test
    public void testCanParseInt() {
        assertTrue(canParseInt("0"));
        assertTrue(canParseInt("129876"));
        assertFalse(canParseInt("12,9876"));
        assertFalse(canParseInt("aslkj"));
        assertFalse(canParseInt(null));
    }

}
