package com.movilizer.ssl;

import com.movilizer.util.config.KeyStoreInfo;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class CreateKeyStoreSample {
    public static void main(String[] args) throws Exception
    {
        KeyStoreInfo keyStoreInfo = new KeyStoreInfo("d:\\Trash\\Keystores\\movilizer.keystore", "myPassword");

        new KeyStoreManager().createKeyStore(keyStoreInfo,
                new CertificateInfo("d:\\Trash\\Keystores\\Certificates\\thawte-base64.cer", "thawte"),
                new CertificateInfo("d:\\Trash\\Keystores\\Certificates\\movilizer-base64.cer", "movilizer"));
    }
}
