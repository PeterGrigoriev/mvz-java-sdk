package com.movilizer.ssl;

import com.google.inject.ImplementedBy;
import com.movilizer.util.config.IKeyStoreInfo;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(KeyStoreManager.class)
public interface IKeyStoreManager {
    void createKeyStore(IKeyStoreInfo keyStoreInfo, ICertificateInfo... certificates) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException;
}
