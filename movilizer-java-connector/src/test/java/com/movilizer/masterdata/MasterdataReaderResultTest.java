package com.movilizer.masterdata;

import com.movilitas.movilizer.v12.MovilizerMasterdataPoolUpdate;
import com.movilitas.movilizer.v12.MovilizerMasterdataReference;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

public class MasterdataReaderResultTest {

    @Test
    public void testGetErrorEventIds() throws Exception {
        List<String> errorEventIds = newArrayList("2", "3");
        MasterdataReaderResult result = new MasterdataReaderResult("myPool", new ArrayList<IMasterdataChange>(), errorEventIds);
        assertEquals(errorEventIds, result.getErrorEventIds());
    }

    @Test
    public void testGetMasterdataPoolUpdate() {
        MovilizerMasterdataReference reference = new MovilizerMasterdataReference();

        reference.setGroup("myGroup");
        reference.setMasterdataAckKey("myAckKey");
        reference.setKey("myObjectId");
        IMasterdataChange change = new MasterdataChange(reference);
        List<IMasterdataChange> changes = newArrayList(change);
        MasterdataReaderResult result = new MasterdataReaderResult("myPool", changes, new ArrayList<String>());
        MovilizerMasterdataPoolUpdate poolUpdate = result.getMasterdataPoolUpdate();
        assertEquals(poolUpdate.getPool(), "myPool");
        List<MovilizerMasterdataReference> references = poolUpdate.getReference();
        assertEquals(references.size(), 1);
        assertSame(references.get(0), reference);
    }
}