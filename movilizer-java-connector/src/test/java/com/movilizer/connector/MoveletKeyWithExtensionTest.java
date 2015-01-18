package com.movilizer.connector;

import com.movilitas.movilizer.v11.MovilizerMoveletDelete;
import org.testng.annotations.Test;

import java.util.List;

import static com.movilizer.connector.MoveletKeyWithExtension.keyWithExtension;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MoveletKeyWithExtensionTest {
    @Test
    public void testKeysWithExtension() throws Exception {
        List<IMoveletKeyWithExtension> keysWithExtension = MoveletKeyWithExtension.keysWithExtension("Ext", "SomeKey1", "SomeKey2");
        assertEquals(keysWithExtension.size(), 2);
        assertEquals(keysWithExtension.get(0).getMoveletExtension(), "Ext");
        assertEquals(keysWithExtension.get(1).getMoveletExtension(), "Ext");
        assertEquals(keysWithExtension.get(0).getMoveletKey(), "SomeKey1");
        assertEquals(keysWithExtension.get(1).getMoveletKey(), "SomeKey2");
    }

    @Test
    public void testKeysWithExtensionForMoveletDelete() throws Exception {
        MovilizerMoveletDelete moveletDelete = new MovilizerMoveletDelete();
        String key = "key";
        String extension = "extension";

        moveletDelete.setMoveletKey(key);
        moveletDelete.setMoveletKeyExtension(extension);

        IMoveletKeyWithExtension keyWithExtension = keyWithExtension(moveletDelete);

        assertEquals(keyWithExtension.getMoveletKey(), key);
        assertEquals(keyWithExtension.getMoveletExtension(), extension);

    }

    @Test
    public void testToString() {
        IMoveletKeyWithExtension keyWithExtension = keyWithExtension("key", "extension");
        String string = keyWithExtension.toString();
        assertFalse(string.isEmpty());
        assertTrue(string.contains("key"));
        assertTrue(string.contains("extension"));
    }
}
