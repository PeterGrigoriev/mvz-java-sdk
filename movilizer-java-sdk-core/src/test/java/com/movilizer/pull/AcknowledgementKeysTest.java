package com.movilizer.pull;

import com.movilizer.masterdata.AcknowledgementKeys;
import com.movilizer.masterdata.MasterdataFieldNames;
import com.movilizer.masterdata.MasterdataXmlSettings;
import org.testng.annotations.Test;

import java.util.Collection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class AcknowledgementKeysTest {

    private MasterdataXmlSettings setting = new MasterdataXmlSettings("testSubscriber", "testPool", 10, 2,
            new MasterdataFieldNames("testGroup", "OBJ_ID", "DESCRIPTION"));


    @Test
    public void testAdd() throws Exception {
        AcknowledgementKeys acknowledgementKeys = new AcknowledgementKeys(setting);
        acknowledgementKeys.add(123);
        Collection<Integer> keysCollection = acknowledgementKeys.getKeys();
        Integer[] keys = keysCollection.toArray(new Integer[keysCollection.size()]);


        assertEquals(keys.length, 1);
        assertEquals(keys[0].intValue(), 123);
    }

    @Test
    public void testGetSetting() {
        AcknowledgementKeys acknowledgementKeys = new AcknowledgementKeys(setting);

        assertSame(acknowledgementKeys.getSetting(), setting);
    }
}
