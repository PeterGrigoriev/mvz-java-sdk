package com.movilizer.document;

import com.google.inject.ImplementedBy;
import com.movilitas.movilizer.v14.MovilizerRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MovilizerDocumentUploader.class)
public interface IMovilizerDocumentUploader {
    void addDocument(MovilizerRequest request, String pool, String key, File file) throws IOException;

    void addDocument(MovilizerRequest request, String pool, String key, String extension, InputStream fileInputStream) throws IOException;

    void deleteDocument(MovilizerRequest request, String pool, String key);
}
