package com.movilizer.util.velocity;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.LineNumberReader;
import java.io.Reader;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class DefaultMoveletTemplateTextLoaderTest {

    private final DefaultMoveletTemplateTextLoader loader;

    public DefaultMoveletTemplateTextLoaderTest() {
        loader = new DefaultMoveletTemplateTextLoader();
    }

    @Test
    public void testGetTemplateText() throws Exception {
        Reader templateText = loader.getTemplateText("WelcomeMovelet.vm");
        assertNotNull(templateText);
        LineNumberReader lineNumberReader = new LineNumberReader(templateText);
        String line = lineNumberReader.readLine();
        assertTrue(line.contains("<?xml version=\"1.0\"?>"));
    }
}
