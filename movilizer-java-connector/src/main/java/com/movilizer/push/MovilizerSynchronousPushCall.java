package com.movilizer.push;

import com.google.common.base.Function;
import com.movilitas.movilizer.v11.MovilizerRequest;
import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.IMovilizerRequestSender;
import com.movilizer.connector.IProxyInfo;
import com.movilizer.connector.MovilizerCallResult;
import com.movilizer.document.IMovilizerDocumentUploader;
import com.movilizer.util.template.ITemplateRepository;

import javax.annotation.Nullable;

/**
 * @author Peter.Grigoriev@movilizer.com.
 */
public class MovilizerSynchronousPushCall extends MovilizerPushCall {
    public MovilizerSynchronousPushCall(IMovilizerCloudSystem system, @Nullable IProxyInfo proxyInfo, IMovilizerRequestSender requestSender, @Nullable IMovilizerDocumentUploader documentUploader, @Nullable ITemplateRepository templateRepository) {
        super(system, proxyInfo, requestSender, documentUploader, templateRepository);
    }

    public MovilizerCallResult synchronousSend() {
        doWithRequest(new Function<MovilizerRequest, Void>() {
            @Nullable
            @Override
            public Void apply(MovilizerRequest movilizerRequest) {
                movilizerRequest.setSynchronousResponse(Boolean.TRUE);
                return null;
            }
        });
        return doCallMovilizerCloud();
    }
}
