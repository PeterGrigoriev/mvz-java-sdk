package com.movilizer.masterdata.operation;

import com.movilitas.movilizer.v15.MovilizerMasterdataPoolUpdate;
import com.movilitas.movilizer.v15.MovilizerMasterdataReference;
import com.movilitas.movilizer.v15.MovilizerMasterdataUpdate;
import com.movilizer.masterdata.IMasterdataXmlSetting;
import com.movilizer.util.functional.Operation2;

import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class AddGroup implements Operation2<MovilizerMasterdataPoolUpdate, IMasterdataXmlSetting> {
    private final String group;


    public AddGroup(String group) {
        this.group = group;
    }

    @Override
    public void apply(MovilizerMasterdataPoolUpdate poolUpdate, IMasterdataXmlSetting setting) {
        List<MovilizerMasterdataReference> references = poolUpdate.getReference();
        List<MovilizerMasterdataUpdate> updates = poolUpdate.getUpdate();

        for (MovilizerMasterdataUpdate masterdataUpdate : updates) {
            MovilizerMasterdataReference reference = new MovilizerMasterdataReference();
            reference.setGroup(group);
            String objectKey = masterdataUpdate.getKey();
            reference.setKey(objectKey);
            reference.setMasterdataAckKey(createAcknowledgementKey(reference));
            references.add(reference);
        }
    }

    /**
     * Default behaviour: acknowledgements will be ignored. Override if you want to change it.
     * @param reference - reference to create acknowledgement key for
     * @return acknowledgement key
     */
    protected String createAcknowledgementKey(MovilizerMasterdataReference reference) {
        return "IGNORE_" + reference.getKey() + "_All";
    }

}
