package com.movilizer.masterdata;

import com.movilitas.movilizer.v11.MovilizerMasterdataAck;
import com.movilitas.movilizer.v11.MovilizerMasterdataDeleted;
import com.movilitas.movilizer.v11.MovilizerMasterdataError;
import com.movilizer.pull.CannotProcessMasterdataAcknowledgementException;
import com.movilizer.pull.CannotProcessMasterdataDeletionException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.google.common.collect.Sets.newHashSet;
import static org.testng.Assert.assertEquals;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MasterdataAcknowledgementProcessorTest {


    private MasterdataAcknowledgementProcessor processor;
    private MockMasterdataAcknowledger masterdataAcknowledger;

    @BeforeMethod
    public void setUp() throws Exception {
        IMasterdataXmlSetting settingOne = new MasterdataXmlSettings(
                "test", "poolOne", 10, 3, new MasterdataFieldNames(
                "groupOne", "id", "some description"
        ));
        IMasterdataXmlSetting settingTwo = new MasterdataXmlSettings(
                "test", "poolTwo", 100, 1, new MasterdataFieldNames(
                "groupTwo", "id", "some description"
        ));

        masterdataAcknowledger = new MockMasterdataAcknowledger();
        processor = new MasterdataAcknowledgementProcessor(newHashSet(settingOne, settingTwo), masterdataAcknowledger);
    }

    @Test
    public void testGetMyPools() throws Exception {
        assertEquals(processor.getTargetPools(), newHashSet("poolOne", "poolTwo"));
    }

    @Test(expectedExceptions = CannotProcessMasterdataAcknowledgementException.class)
    public void testProcessMasterdataAcknowledgementForForeignPool() throws Exception {
        MovilizerMasterdataAck masterdataAck = new MovilizerMasterdataAck();
        masterdataAck.setPool("notHisPool");
        masterdataAck.setKey("123");
        masterdataAck.setMasterdataAckKey("345");
        masterdataAck.setGroup("someGroup");
        processor.processMasterdataAcknowledgement(masterdataAck);
    }

    @Test(expectedExceptions = CannotProcessMasterdataAcknowledgementException.class)
    public void testProcessMasterdataAcknowledgementForNullPool() throws Exception {
        MovilizerMasterdataAck masterdataAck = new MovilizerMasterdataAck();
        masterdataAck.setKey("123");
        masterdataAck.setMasterdataAckKey("345");
        masterdataAck.setGroup("someGroup");
        processor.processMasterdataAcknowledgement(masterdataAck);
    }

    @Test
    public void testProcessMasterdataAcknowledgement() throws Exception {
        MovilizerMasterdataAck masterdataAck = new MovilizerMasterdataAck();
        masterdataAck.setPool("poolOne");
        masterdataAck.setKey("123");
        masterdataAck.setMasterdataAckKey("345");
        masterdataAck.setGroup("someGroup");
        processor.processMasterdataAcknowledgement(masterdataAck);
        processor.submit();
        assertEquals(masterdataAcknowledger.getCalls().size(), 1);
    }

    @Test(expectedExceptions = CannotProcessMasterdataAcknowledgementException.class)
    public void testProcessMasterdataErrorThrowsException() throws CannotProcessMasterdataAcknowledgementException {
        MovilizerMasterdataError masterdataError = new MovilizerMasterdataError();

        processor.processMasterdataError(masterdataError);
    }

    @Test
    public void testSubmitDoesNotCallAcknowledgerForNoAcknowledgements() throws Exception {
        processor.submit();
        assertEquals(masterdataAcknowledger.getCalls().size(), 0);
    }

    @Test(expectedExceptions = CannotProcessMasterdataAcknowledgementException.class)
    public void testProcessMasterdataAcknowledgementThrowsOnNullPull() throws Exception {
        MovilizerMasterdataAck masterdataAck = new MovilizerMasterdataAck();
        masterdataAck.setPool(null);
        masterdataAck.setKey("123");
        masterdataAck.setMasterdataAckKey("345");
        masterdataAck.setGroup("someGroup");
        processor.processMasterdataAcknowledgement(masterdataAck);
    }

    @Test(expectedExceptions = CannotProcessMasterdataAcknowledgementException.class)
    public void testProcessMasterdataAcknowledgementThrowsOnNullAckKey() throws Exception {
        MovilizerMasterdataAck masterdataAck = new MovilizerMasterdataAck();
        masterdataAck.setPool("somePool");
        masterdataAck.setKey(null);
        masterdataAck.setMasterdataAckKey("345");
        masterdataAck.setGroup("someGroup");
        processor.processMasterdataAcknowledgement(masterdataAck);
    }

    @Test(expectedExceptions = CannotProcessMasterdataAcknowledgementException.class)
    public void testProcessMasterdataAcknowledgementThrowsOnNonIntAckKey() throws Exception {
        MovilizerMasterdataAck masterdataAck = new MovilizerMasterdataAck();
        masterdataAck.setPool("somePool");
        masterdataAck.setKey("askjh212121");
        masterdataAck.setMasterdataAckKey("345");
        masterdataAck.setGroup("someGroup");
        processor.processMasterdataAcknowledgement(masterdataAck);
    }

    @Test(expectedExceptions = CannotProcessMasterdataDeletionException.class)
    public void testProcessMasterdataDeletionThrowsOnNullPull() throws Exception {
        MovilizerMasterdataDeleted masterdataDeleted = new MovilizerMasterdataDeleted();
        masterdataDeleted.setPool(null);
        masterdataDeleted.setKey("123");
        masterdataDeleted.setMasterdataAckKey("345");
        masterdataDeleted.setGroup("someGroup");
        processor.processMasterdataDeletion(masterdataDeleted);
    }

    @Test(expectedExceptions = CannotProcessMasterdataDeletionException.class)
    public void testProcessMasterdataDeletionThrowsOnNonIntegerAckKey() throws Exception {
        MovilizerMasterdataDeleted masterdataDeleted = new MovilizerMasterdataDeleted();
        masterdataDeleted.setPool("somePool");
        masterdataDeleted.setKey("abc");
        masterdataDeleted.setMasterdataAckKey("abc");
        masterdataDeleted.setGroup("someGroup");
        processor.processMasterdataDeletion(masterdataDeleted);
    }

}
