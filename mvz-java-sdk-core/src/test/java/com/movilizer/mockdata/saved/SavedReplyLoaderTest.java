package com.movilizer.mockdata.saved;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class SavedReplyLoaderTest {

    private final SavedMovilizerObjectLoader loader;

    public SavedReplyLoaderTest() {
        loader = SavedMovilizerObjectLoader.getInstance();
    }

    @Test
    public void testLoad() throws Exception {
        String loadedXml = loader.loadXml("ReplyMovelet.xml");

        Assert.assertNotNull(loadedXml);


    }
}
