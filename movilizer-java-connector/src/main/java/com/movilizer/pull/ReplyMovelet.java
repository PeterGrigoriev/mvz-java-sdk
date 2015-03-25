package com.movilizer.pull;

import com.movilitas.movilizer.v12.MovilizerReplyMovelet;
import com.movilitas.movilizer.v12.MovilizerReplyQuestion;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

/**
 * Created by philippe.guillamet@altran.com on 30/10/2014.
 */
// TODO: we need to get rid of this decorator
public class ReplyMovelet extends MovilizerReplyMovelet {
    private final MovilizerReplyMovelet movilizerReplyMovelet;
    private boolean lastReplyMovelet = false;

    public ReplyMovelet(MovilizerReplyMovelet movilizerReplyMovelet) {
        this.movilizerReplyMovelet = movilizerReplyMovelet;
    }

    public MovilizerReplyMovelet getMovilizerReplyMovelet() {
        return movilizerReplyMovelet;
    }

    public List<MovilizerReplyQuestion> getReplyQuestion() {
        return movilizerReplyMovelet.getReplyQuestion();
    }

    public String getEncryptionIV() {
        return movilizerReplyMovelet.getEncryptionIV();
    }

    public void setEncryptionIV(String value) {
        movilizerReplyMovelet.setEncryptionIV(value);
    }

    public String getEncryptionHMAC() {
        return movilizerReplyMovelet.getEncryptionHMAC();
    }

    public void setEncryptionHMAC(String value) {
        movilizerReplyMovelet.setEncryptionHMAC(value);
    }

    public String getMoveletKey() {
        return movilizerReplyMovelet.getMoveletKey();
    }

    public void setMoveletKey(String value) {
        movilizerReplyMovelet.setMoveletKey(value);
    }

    public String getMoveletKeyExtension() {
        return movilizerReplyMovelet.getMoveletKeyExtension();
    }

    public void setMoveletKeyExtension(String value) {
        movilizerReplyMovelet.setMoveletKeyExtension(value);
    }

    public long getMoveletVersion() {
        return movilizerReplyMovelet.getMoveletVersion();
    }

    public void setMoveletVersion(Long value) {
        movilizerReplyMovelet.setMoveletVersion(value);
    }

    public int getCount() {
        return movilizerReplyMovelet.getCount();
    }

    public void setCount(int value) {
        movilizerReplyMovelet.setCount(value);
    }

    public String getParentMoveletKey() {
        return movilizerReplyMovelet.getParentMoveletKey();
    }

    public void setParentMoveletKey(String value) {
        movilizerReplyMovelet.setParentMoveletKey(value);
    }

    public String getParentMoveletKeyExtension() {
        return movilizerReplyMovelet.getParentMoveletKeyExtension();
    }

    public void setParentMoveletKeyExtension(String value) {
        movilizerReplyMovelet.setParentMoveletKeyExtension(value);
    }

    public Integer getParentReplySetCount() {
        return movilizerReplyMovelet.getParentReplySetCount();
    }

    public void setParentReplySetCount(Integer value) {
        movilizerReplyMovelet.setParentReplySetCount(value);
    }

    public String getEncryptionAlgorithm() {
        return movilizerReplyMovelet.getEncryptionAlgorithm();
    }

    public void setEncryptionAlgorithm(String value) {
        movilizerReplyMovelet.setEncryptionAlgorithm(value);
    }

    public String getParticipantKey() {
        return movilizerReplyMovelet.getParticipantKey();
    }

    public void setParticipantKey(String value) {
        movilizerReplyMovelet.setParticipantKey(value);
    }

    public String getDeviceAddress() {
        return movilizerReplyMovelet.getDeviceAddress();
    }

    public void setDeviceAddress(String value) {
        movilizerReplyMovelet.setDeviceAddress(value);
    }

    public XMLGregorianCalendar getCreationTimestamp() {
        return movilizerReplyMovelet.getCreationTimestamp();
    }

    public void setCreationTimestamp(XMLGregorianCalendar value) {
        movilizerReplyMovelet.setCreationTimestamp(value);
    }

    public XMLGregorianCalendar getSyncTimestamp() {
        return movilizerReplyMovelet.getSyncTimestamp();
    }

    public void setSyncTimestamp(XMLGregorianCalendar value) {
        movilizerReplyMovelet.setSyncTimestamp(value);
    }

    public Integer getTimedif() {
        return movilizerReplyMovelet.getTimedif();
    }

    public void setTimedif(Integer value) {
        movilizerReplyMovelet.setTimedif(value);
    }

    public short getReplyUploadPriority() {
        return movilizerReplyMovelet.getReplyUploadPriority();
    }

    public void setReplyUploadPriority(Short value) {
        movilizerReplyMovelet.setReplyUploadPriority(value);
    }

    public boolean isLastReplyMovelet() {
        return lastReplyMovelet;
    }

    public void setLastReplyMovelet(boolean lastReplyMovelet) {
        this.lastReplyMovelet = lastReplyMovelet;
    }
}
