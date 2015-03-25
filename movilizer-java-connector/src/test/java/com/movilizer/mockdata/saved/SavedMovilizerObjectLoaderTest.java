package com.movilizer.mockdata.saved;

import com.movilitas.movilizer.v12.MovilizerReplyMovelet;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class SavedMovilizerObjectLoaderTest {

    private final SavedMovilizerObjectLoader loader;

    public SavedMovilizerObjectLoaderTest() {
        loader = SavedMovilizerObjectLoader.getInstance();
    }

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(SavedMovilizerObjectLoader.getInstance());
    }

    @Test
    public void testLoadXml() throws Exception {
        String xml = loader.loadXml("ReplyMovelet.xml");
        assertNotNull(xml);
        assertFalse(xml.isEmpty());
    }

    @Test
    public void testLoad() throws Exception {
        MovilizerReplyMovelet replyMovelet = loader.load(MovilizerReplyMovelet.class, "ReplyMovelet.xml");
        assertNotNull(replyMovelet);
        assertFalse(replyMovelet.getReplyQuestion().isEmpty());
    }
}
