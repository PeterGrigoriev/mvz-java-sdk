package com.movilizer.masterdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MockMasterdataAcknowledger implements IMasterdataAcknowledger {
    List<MockMasterdataAcknowledgerCall> calls = new ArrayList<MockMasterdataAcknowledgerCall>();

    @Override
    public void acknowledge(IMasterdataXmlSetting setting, Collection<Integer> eventIds, AcknowledgementStatus status) throws SQLException {
        calls.add(new MockMasterdataAcknowledgerCall(setting, eventIds, status));
    }

    public List<MockMasterdataAcknowledgerCall> getCalls() {
        return calls;
    }
}
