package com.movilizer.connector;

import com.movilitas.movilizer.v15.MovilizerResponse;
import com.movilitas.movilizer.v15.MovilizerStatusMessage;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerCallResultTest {

    @Test
    public void testIsSuccess() throws Exception {
        MovilizerCallResult result = new MovilizerCallResult(new MovilizerResponse());
        assertTrue(result.isSuccess());

        result = new MovilizerCallResult("Bad thing happened");
        assertFalse(result.isSuccess());
    }

    @Test
    public void testGetResponse() throws Exception {
        MovilizerResponse response = new MovilizerResponse();
        MovilizerCallResult result = new MovilizerCallResult(response);
        assertSame(result.getResponse(), response);
    }

    @Test
    public void testGetResponseWithError() throws Exception {
        MovilizerResponse response = new MovilizerResponse();
        response.getStatusMessage().add(getErrorMessage());
        MovilizerCallResult result = new MovilizerCallResult(response);
        assertFalse(result.isSuccess());
    }

    private static MovilizerStatusMessage getErrorMessage() {
        MovilizerStatusMessage statusMessage = new MovilizerStatusMessage();
        statusMessage.setType((short)403);
        statusMessage.setMessage("Error!!!");
        return statusMessage;
    }

    @Test
    public void testGetError() throws Exception {
        String error = "Bad thing happened";
        MovilizerCallResult result = new MovilizerCallResult(error);
        assertEquals(result.getError(), error);
    }
}
