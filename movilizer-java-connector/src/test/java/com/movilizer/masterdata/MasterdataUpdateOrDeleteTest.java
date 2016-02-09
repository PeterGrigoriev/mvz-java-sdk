package com.movilizer.masterdata;

import com.movilitas.movilizer.v14.MovilizerMasterdataDelete;
import com.movilitas.movilizer.v14.MovilizerMasterdataUpdate;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MasterdataUpdateOrDeleteTest {

    @Test
    public void testGetEventId() throws Exception {
        MovilizerMasterdataUpdate update = new MovilizerMasterdataUpdate();
        update.setMasterdataAckKey("123");
        assertEquals(MasterdataEventType.UPDATE, new MasterdataChange(update).getType());
        assertEquals(123, new MasterdataChange(update).getEventId());

        MovilizerMasterdataDelete delete = new MovilizerMasterdataDelete();
        delete.setMasterdataAckKey("234");
        assertEquals(new MasterdataChange(delete).getType(), MasterdataEventType.DELETE);
        assertEquals(new MasterdataChange(delete).getEventId(), 234);
    }
}