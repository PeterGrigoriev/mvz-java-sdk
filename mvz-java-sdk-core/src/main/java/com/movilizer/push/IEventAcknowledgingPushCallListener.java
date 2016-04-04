package com.movilizer.push;


/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IEventAcknowledgingPushCallListener extends IMovilizerPushCallListener {
    void addId(int id);
}