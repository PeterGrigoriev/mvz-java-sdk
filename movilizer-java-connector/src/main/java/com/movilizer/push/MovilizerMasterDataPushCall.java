package com.movilizer.push;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.IMovilizerRequestSender;
import com.movilizer.connector.IProxyInfo;
import com.movilizer.document.IMovilizerDocumentUploader;
import com.movilizer.util.template.ITemplateRepository;

import javax.annotation.Nullable;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerMasterDataPushCall extends MovilizerPushCall implements IMovilizerMasterDataPushCall {
    @Inject
    public MovilizerMasterDataPushCall(@Named("MasterData") IMovilizerCloudSystem system, @Nullable IProxyInfo proxyInfo, IMovilizerRequestSender requestSender, @Nullable IMovilizerDocumentUploader documentUploader, @Nullable ITemplateRepository templateRepository) {
        super(system, proxyInfo, requestSender, documentUploader, templateRepository);
    }
}
