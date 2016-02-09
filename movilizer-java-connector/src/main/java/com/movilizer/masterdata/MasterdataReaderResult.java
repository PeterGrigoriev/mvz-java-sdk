package com.movilizer.masterdata;

import com.movilitas.movilizer.v14.MovilizerMasterdataDelete;
import com.movilitas.movilizer.v14.MovilizerMasterdataPoolUpdate;
import com.movilitas.movilizer.v14.MovilizerMasterdataReference;
import com.movilitas.movilizer.v14.MovilizerMasterdataUpdate;

import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MasterdataReaderResult implements IMasterdataReaderResult {
    private final List<String> errorEventIds;
    private final MovilizerMasterdataPoolUpdate masterdataPoolUpdate;

    public MasterdataReaderResult(String poolName, List<IMasterdataChange> changes, List<String> errorEventIds) {
        this.masterdataPoolUpdate = toMasterdataPoolUpdate(poolName, changes);
        this.errorEventIds = errorEventIds;
    }

    public static MovilizerMasterdataPoolUpdate toMasterdataPoolUpdate(String poolName, List<IMasterdataChange> changes) {
        MovilizerMasterdataPoolUpdate result = new MovilizerMasterdataPoolUpdate();
        result.setPool(poolName);
        List<MovilizerMasterdataDelete> deletes = result.getDelete();
        List<MovilizerMasterdataUpdate> updates = result.getUpdate();
        List<MovilizerMasterdataReference> references = result.getReference();

        for (IMasterdataChange change : changes) {
            if (change.isDelete()) {
                deletes.add(change.getDelete());
            } else {
                if(change.isReference()) {
                    references.add(change.getReference());
                } else  {
                    updates.add(change.getUpdate());
                }
            }
        }

        return result;
    }


    @Override
    public MovilizerMasterdataPoolUpdate getMasterdataPoolUpdate() {
        return masterdataPoolUpdate;
    }

    public List<String> getErrorEventIds() {
        return errorEventIds;
    }
}
