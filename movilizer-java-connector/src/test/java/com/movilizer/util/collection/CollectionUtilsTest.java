package com.movilizer.util.collection;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static com.google.common.collect.Sets.newHashSet;
import static com.movilizer.util.collection.CollectionUtils.newLinkedSet;
import static com.movilizer.util.collection.CollectionUtils.newProperties;
import static com.movilizer.util.collection.CollectionUtils.take;
import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class CollectionUtilsTest {
    @Test
    public void testTake() throws Exception {
        List<String> list = asList("a", "b", "c");
        List<String> firstTwo = take(list, 2);
        assertEquals(firstTwo.size(), 2);
        assertEquals("a", firstTwo.get(0));
        assertEquals("b", firstTwo.get(1));

        list = asList("a", "b");
        firstTwo = take(list, 200);
        assertEquals(firstTwo.size(), 2);
        assertEquals("a", firstTwo.get(0));
        assertEquals("b", firstTwo.get(1));
    }

    @Test
    public void testNewProperties() {
        assertNull(newProperties((String[]) null));
        assertEquals(newProperties(), new Properties());
        Properties properties = newProperties("a", "1", "b", "2");
        Properties expected = new Properties();
        expected.setProperty("a", "1");
        expected.setProperty("b", "2");
        assertEquals(properties, expected);

        // last "unpaired" arg will be ignored
        properties = newProperties("a", "1", "b", "2", "c");
        assertEquals(properties, expected);
    }

    @Test
    public void testNewLinkedSet() {
        Set<String> set = newLinkedSet("1", "2", "3", "4");
        List<String> copy = new ArrayList<String>(set);
        Assert.assertEquals(copy, asList("1", "2", "3", "4"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testToStringSet() {
        List list = new ArrayList();
        list.add("a");
        list.add("b");

        Set<String> set = newHashSet("a", "b");

        assertEquals(CollectionUtils.toStringSet(list), set);
    }
}
