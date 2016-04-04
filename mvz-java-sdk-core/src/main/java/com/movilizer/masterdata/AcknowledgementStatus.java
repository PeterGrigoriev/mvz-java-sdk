package com.movilizer.masterdata;

import com.movilizer.push.EventAcknowledgementStatus;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public enum AcknowledgementStatus {
    SENT,
    ERROR,
    RESEND,
    OK;

    public EventAcknowledgementStatus toEventAcknowledgementStatus() {
        switch (this) {
            case ERROR:
                return EventAcknowledgementStatus.ERROR;
            case OK:
                return EventAcknowledgementStatus.OK;
            case RESEND:
                return EventAcknowledgementStatus.RESEND;
            case SENT:
                return EventAcknowledgementStatus.SENT;
        }
        throw new IllegalStateException("Unknown AcknowledgementStatus " + this);
    }
}