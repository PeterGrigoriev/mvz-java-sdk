package com.movilizer.document;

import com.movilitas.movilizer.v12.MovilizerDocumentPoolUpdate;
import com.movilitas.movilizer.v12.MovilizerDocumentUpdate;
import com.movilitas.movilizer.v12.MovilizerRequest;
import org.apache.tools.ant.util.ReaderInputStream;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author Peter.Grigoriev@movilizer.com
 */

public class MovilizerDocumentUploaderTest {

    @Test(expectedExceptions = IOException.class)
    public void testAddDocumentWithNoFile() throws Exception {
        MovilizerDocumentUploader uploader = new MovilizerDocumentUploader();
        MovilizerRequest request = new MovilizerRequest();
        uploader.addDocument(request, "somePool", "someKey", new File("c:\\SomeFileWhichDoesNotExist.zip"));
    }

    @Test
    public void testAddDocumentFromStream() throws Exception {
        MovilizerDocumentUploader uploader = new MovilizerDocumentUploader();
        MovilizerRequest request = new MovilizerRequest();
        String content = "Test Document Content";
        int lengthInBytes = content.getBytes().length;
        InputStream stream = new ReaderInputStream(new StringReader(content));
        uploader.addDocument(request, "somePool", "someKey", "txt", stream);
        assertEquals(request.getDocumentPoolUpdate().size(), 1);
        MovilizerDocumentPoolUpdate poolUpdate = request.getDocumentPoolUpdate().get(0);
        assertEquals("somePool", poolUpdate.getPool());
        List<MovilizerDocumentUpdate> documentUpdates = poolUpdate.getUpdate();
        assertEquals(documentUpdates.size(), 1);
        MovilizerDocumentUpdate documentUpdate = documentUpdates.get(0);
        String key = documentUpdate.getKey();
        assertEquals("someKey", key);
        assertEquals("txt", documentUpdate.getFilesuffix());
        assertEquals(lengthInBytes, documentUpdate.getData().length);
    }


    @Test
    public void testDeleteDocument() throws Exception {
        MovilizerDocumentUploader uploader = new MovilizerDocumentUploader();
        MovilizerRequest request = new MovilizerRequest();
        uploader.deleteDocument(request, "poolOne", "docOne");
        assertEquals(request.getDocumentPoolUpdate().get(0).getDelete().size(), 1);

    }
}
