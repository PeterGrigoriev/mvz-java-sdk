package com.movilizer.masterdata.excel;

import com.movilitas.movilizer.v14.*;
import com.movilizer.masterdata.MasterdataFieldNames;
import com.movilizer.masterdata.MasterdataXmlSettings;
import com.movilizer.masterdata.operation.AddGroup;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class AddGroupTest {

    public static final String GROUP = "MyNewGroup";
    private AddGroup addGroup;

    @BeforeMethod
    public void setUp() throws Exception {
        addGroup = new AddGroup(GROUP) {
            @Override
            protected String createAcknowledgementKey(MovilizerMasterdataReference reference) {
                return "All_" + reference.getKey();
            }
        };

    }

    @Test
    public void testApply() throws Exception {
        MovilizerMasterdataPoolUpdate poolUpdate = new MovilizerMasterdataPoolUpdate();
        MovilizerMasterdataUpdate masterdataUpdate = new MovilizerMasterdataUpdate();
        masterdataUpdate.setKey("123");
        masterdataUpdate.setMasterdataAckKey("ACK_123");
        MovilizerGenericDataContainer container = new MovilizerGenericDataContainer();
        MovilizerGenericDataContainerEntry entry = new MovilizerGenericDataContainerEntry();
        entry.setName("fieldOne");
        entry.setValstr("Value One");
        container.getEntry().add(entry);
        masterdataUpdate.setData(container);
        poolUpdate.getUpdate().add(masterdataUpdate);

        addGroup.apply(poolUpdate, new MasterdataXmlSettings("test", "pool2", 2, 100, new MasterdataFieldNames("a", "b", "c")));
        List<MovilizerMasterdataReference> references = poolUpdate.getReference();
        assertEquals(1, references.size());
        MovilizerMasterdataReference reference = references.get(0);

        assertEquals(reference.getMasterdataAckKey(), "All_" + masterdataUpdate.getKey());
        assertEquals(reference.getKey(), masterdataUpdate.getKey());
        assertEquals(reference.getGroup(), GROUP);
    }
}