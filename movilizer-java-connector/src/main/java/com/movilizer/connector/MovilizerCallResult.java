package com.movilizer.connector;


import com.movilitas.movilizer.v12.MovilizerResponse;
import com.movilitas.movilizer.v12.MovilizerStatusMessage;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import java.util.List;

import static com.movilizer.util.string.StringUtils.isNullOrEmpty;
import static java.util.Arrays.asList;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerCallResult {

    private MovilizerCallResult(MovilizerResponse response, String error) {
        this.error = error;
        this.response = response;
    }

    private final MovilizerResponse response;
    private String error;

    public MovilizerCallResult(MovilizerResponse response) {
        this(response, checkForErrors(response));
    }

    private static String checkForErrors(MovilizerResponse response) {
        List<MovilizerStatusMessage> statusMessages = response.getStatusMessage();
        if(null == statusMessages) {
            return null;
        }

        for (MovilizerStatusMessage statusMessage : statusMessages) {
            if(isError(statusMessage.getType())) {
                logMessage(statusMessage);
                return statusMessage.getMessage();
            }
        }
        return null;
    }

    private static final ILogger logger = ComponentLogger.getInstance(MovilizerCallResult.class.getName());

    private static void logMessage(MovilizerStatusMessage statusMessage) {
        logger.error(statusMessage.getMessage());
        String additionalMessage = statusMessage.getAdditional();
        if(additionalMessage != null) {
            logger.error(additionalMessage);
        }
    }


    private static final List<Short> ERRORS = asList((short)402, (short)403);

    private static boolean isError(short type) {
        return ERRORS.contains(type);
    }

    public MovilizerCallResult(String error) {
        this(null, error);
    }

    public boolean isSuccess() {
        return isNullOrEmpty(error);
    }

    public MovilizerResponse getResponse() {
        return response;
    }

    public String getError() {
        return error;
    }
}
