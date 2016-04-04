package com.movilizer.masterdata;


import com.movilitas.movilizer.v14.MovilizerMasterdataDelete;
import com.movilitas.movilizer.v14.MovilizerMasterdataReference;
import com.movilitas.movilizer.v14.MovilizerMasterdataUpdate;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMasterdataChange {
    String getObjectKey();

    boolean isReference();

    boolean isUpdate();

    boolean isDelete();

    MovilizerMasterdataUpdate getUpdate();

    MovilizerMasterdataDelete getDelete();

    MovilizerMasterdataReference getReference();

    MasterdataEventType getType();

    int getEventId();
}
