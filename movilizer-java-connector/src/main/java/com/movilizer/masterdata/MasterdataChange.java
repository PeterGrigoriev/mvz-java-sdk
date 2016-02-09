package com.movilizer.masterdata;


import com.movilitas.movilizer.v14.MovilizerMasterdataDelete;
import com.movilitas.movilizer.v14.MovilizerMasterdataReference;
import com.movilitas.movilizer.v14.MovilizerMasterdataUpdate;

import static com.movilizer.masterdata.MasterdataEventType.DELETE;
import static com.movilizer.masterdata.MasterdataEventType.UPDATE;
import static com.movilizer.util.string.StringUtils.isNullOrEmpty;
import static java.lang.Integer.parseInt;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MasterdataChange implements IMasterdataChange {
    private final MovilizerMasterdataUpdate update;
    private final MovilizerMasterdataDelete delete;
    private final MasterdataEventType type;
    private final MovilizerMasterdataReference reference;

    public MasterdataChange(MovilizerMasterdataUpdate update) {
        this.update = update;
        this.delete = null;
        this.reference = null;
        type = UPDATE;
    }

    /**
     * For a de-reference, set group
     * @param delete - masterdata delete or de-reference
     */
    public MasterdataChange(MovilizerMasterdataDelete delete) {
        this.delete = delete;
        this.update = null;
        this.reference = null;
        type = DELETE;
    }

    public MasterdataChange(MovilizerMasterdataReference movilizerMasterdataReference) {
        this.delete = null;
        this.update = null;
        this.reference = movilizerMasterdataReference;
        this.type = UPDATE;
    }

    @Override
    public String getObjectKey() {
        if(isDelete()) {
            return delete.getKey();
        }

        if(isUpdate()) {
            return isReference() ? reference.getKey() : update.getKey();
        }
        return null;
    }

    @Override
    public boolean isReference() {
        return this.reference != null || (isDelete() && !isNullOrEmpty(delete.getGroup()));
    }

    @Override
    public boolean isUpdate() {
        return update != null || reference != null;
    }

    @Override
    public boolean isDelete() {
        return delete != null;
    }

    @Override
    public MovilizerMasterdataUpdate getUpdate() {
        return update;
    }

    @Override
    public MovilizerMasterdataDelete getDelete() {
        return delete;
    }

    @Override
    public MovilizerMasterdataReference getReference() {
        return reference;
    }

    @Override
    public MasterdataEventType getType() {
        return type;
    }

    @Override
    public int getEventId() {
        return parseInt(getAckKey());
    }

    private String getAckKey() {
        if(update != null) {
            return update.getMasterdataAckKey();
        }
        if(delete != null) {
            return delete.getMasterdataAckKey();
        }
        if(reference != null) {
            return reference.getMasterdataAckKey();
        }
        return null;
    }
}
