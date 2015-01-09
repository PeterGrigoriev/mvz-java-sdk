package com.movilizer.connector;

import com.movilitas.movilizer.v11.MovilizerResponse;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerCallResultTest {

    @Test
    public void testIsSuccess() throws Exception {
        MovilizerCallResult result = new MovilizerCallResult(new MovilizerResponse(), null);
        assertTrue(result.isSuccess());

        result = new MovilizerCallResult(null, "Bad thing happened");
        assertFalse(result.isSuccess());
    }

    @Test
    public void testGetResponse() throws Exception {
        MovilizerResponse response = new MovilizerResponse();
        MovilizerCallResult result = new MovilizerCallResult(response, null);
        assertSame(result.getResponse(), response);
    }

    @Test
    public void testGetError() throws Exception {
        String error = "Bad thing happened";
        MovilizerCallResult result = new MovilizerCallResult(error);
        assertEquals(result.getError(), error);
    }
}
