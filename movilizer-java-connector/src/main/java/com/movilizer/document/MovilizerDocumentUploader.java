package com.movilizer.document;

import com.google.common.io.Files;
import com.google.inject.Singleton;
import com.movilitas.movilizer.v11.MovilizerDocumentDelete;
import com.movilitas.movilizer.v11.MovilizerDocumentPoolUpdate;
import com.movilitas.movilizer.v11.MovilizerDocumentUpdate;
import com.movilitas.movilizer.v11.MovilizerRequest;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@Singleton
public class MovilizerDocumentUploader implements IMovilizerDocumentUploader {
    private final static ILogger logger = ComponentLogger.getInstance("MovilizerDocumentUploader");

    @Override
    public void addDocument(MovilizerRequest request, String pool, String key, File file) throws IOException {
        InputStream fileInputStream = new FileInputStream(file);
        addDocument(request, pool, key, Files.getFileExtension(file.getName()), fileInputStream);
    }


    @Override
    public void addDocument(MovilizerRequest request, String pool, String key, String extension, InputStream fileInputStream) throws IOException {
        logger.debug("Adding document: pool = [" + pool + "]" + " key = " + "[" + key + "] extension = [" + extension + "]");
        byte[] byteArray = IOUtils.toByteArray(fileInputStream);


        MovilizerDocumentPoolUpdate update = new MovilizerDocumentPoolUpdate();
        update.setPool(pool);

        MovilizerDocumentUpdate documentUpdate = new MovilizerDocumentUpdate();
        documentUpdate.setDocumentAckKey("DOC:" + pool + ":" + key);
        documentUpdate.setKey(key);
        documentUpdate.setFilesuffix(extension);
        documentUpdate.setLanguage("");

        documentUpdate.setData(byteArray);
        update.getUpdate().add(documentUpdate);
        request.getDocumentPoolUpdate().add(update);
        logger.debug("Added document: bytes read = [" + byteArray.length + "] pool = [" + pool + "]" + " key = " + "[" + key + "] extension = [" + extension + "]");
    }

    @Override
    public void deleteDocument(MovilizerRequest request, String pool, String key) {
        logger.debug("Adding document deletion: pool = [" + pool + "]" + " key = " + "[" + key + "]" );


        MovilizerDocumentPoolUpdate update = new MovilizerDocumentPoolUpdate();
        update.setPool(pool);

        MovilizerDocumentDelete documentDelete = new MovilizerDocumentDelete();
        documentDelete.setDocumentAckKey("DOC:" + pool + ":" + key);
        documentDelete.setKey(key);

        update.getDelete().add(documentDelete);
        request.getDocumentPoolUpdate().add(update);
        logger.debug("Added document deletion: pool = [" + pool + "]" + " key = " + "[" + key + "]" );
    }
}
