package com.movilizer.connector;

import com.movilitas.movilizer.v14.MovilizerMoveletDelete;
import com.movilitas.movilizer.v14.MovilizerRequest;
import org.testng.annotations.Test;

import static com.movilizer.connector.MoveletDelete.getMoveletDelete;
import static com.movilizer.connector.MoveletKeyWithExtension.keyWithExtension;
import static org.testng.Assert.assertEquals;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class DeleteMoveletTest {
    private final MoveletDelete moveletDelete;

    public DeleteMoveletTest() {
        moveletDelete = new MoveletDelete(keyWithExtension("someMoveletKey", "someMoveletKeyExtension"));
    }

    @Test
    public void testGetMoveletDelete() throws Exception {
        MovilizerMoveletDelete movilizerMoveletDelete = getMoveletDelete(keyWithExtension("someMoveletKey", "someMoveletKeyExtension"));
        assertEquals(movilizerMoveletDelete.getMoveletKey(), "someMoveletKey");
        assertEquals(movilizerMoveletDelete.getMoveletKeyExtension(), "someMoveletKeyExtension");

    }

    @Test
    public void testApply() throws Exception {
        MovilizerRequest request = new MovilizerRequest();
        moveletDelete.apply(request);
        assertEquals(1, request.getMoveletDelete().size());
    }
}
