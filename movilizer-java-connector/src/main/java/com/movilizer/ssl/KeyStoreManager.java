package com.movilizer.ssl;

import com.google.inject.Singleton;
import com.movilizer.util.config.IKeyStoreInfo;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Collection;

/**
 * @author Peter.Grigoriev@movilizer.com
 */

@Singleton
public class KeyStoreManager implements IKeyStoreManager {

    @Override
    public void createKeyStore(IKeyStoreInfo keyStoreInfo, ICertificateInfo... certificates) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, keyStoreInfo.getPassword().toCharArray());

        for (ICertificateInfo certificate : certificates) {
            addCertificate(keyStore, certificate);
        }

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(keyStoreInfo.getLocation());
            keyStore.store(outputStream, keyStoreInfo.getPassword().toCharArray());
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }


    public static void addCertificate(KeyStore keyStore,
                                       ICertificateInfo certificateInfo)
            throws KeyStoreException, IOException,  NoSuchAlgorithmException, CertificateException
    {
        final CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        File file = new File(certificateInfo.getLocation());
        ByteArrayInputStream inStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));

        Collection<? extends Certificate> certificates = certFactory.generateCertificates(inStream);

        for (Certificate certificate : certificates) {
            keyStore.setCertificateEntry(certificateInfo.getAlias(), certificate);
        }
    }
}
