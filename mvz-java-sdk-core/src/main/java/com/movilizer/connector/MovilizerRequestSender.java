package com.movilizer.connector;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.movilitas.movilizer.v15.MovilizerRequest;
import com.movilitas.movilizer.v15.MovilizerResponse;
import com.movilitas.movilizer.v15.MovilizerWebServiceV15;
import com.movilizer.util.config.IKeyStoreInfo;
import com.movilizer.util.config.MovilizerConfig;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import javax.xml.ws.BindingProvider;
import java.util.Map;

import static com.movilizer.util.proxy.ProxyUtil.applyProxy;
import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@Singleton
public class MovilizerRequestSender implements IMovilizerRequestSender {

    private static final ILogger logger = ComponentLogger.getInstance("MovilizerRequestSender");

    private final IMovilizerWebServiceProvider webServiceProvider;

    @Inject
    public MovilizerRequestSender(IMovilizerWebServiceProvider webServiceProvider) {
        this.webServiceProvider = webServiceProvider;
    }

    @Override
    public MovilizerCallResult sendRequest(MovilizerRequest request, IMovilizerCloudSystem system, IProxyInfo proxyInfo) {
        setUpSsl();
        if (proxyInfo != null) {
            logger.debug("Using proxy " + proxyInfo.getProxyHost() + ":" + proxyInfo.getProxyPort());
            applyProxy(proxyInfo);
        }
        try {
            MovilizerWebServiceV15 port = webServiceProvider.get();
            BindingProvider bindingProvider = (BindingProvider) port;
            Map<String, Object> requestContext = bindingProvider.getRequestContext();

            addEndpoint(requestContext, system);
            addTimeout(requestContext, system);

            logger.debug("Calling Movilizer Cloud. System id is [" + system.getSystemId() + "].");
            MovilizerResponse response = port.movilizer(request);
            logger.debug("Done calling Movilizer Cloud");
            return new MovilizerCallResult(response);
        } catch (Throwable throwable) {
            logger.error(throwable);
            return new MovilizerCallResult("Request failed");
        }
    }

    private void setUpSsl() {
        // TODO: keystore info to be injected with Guice
        IKeyStoreInfo keyStoreInfo = MovilizerConfig.getInstance().getKeyStoreInfo();
        if(null == keyStoreInfo) {
            System.setProperty("https.protocols", "TLSv1");
        }
        else  {
            logger.debug("Setting up SSL keystore [" + keyStoreInfo.getLocation() + "]");
            System.setProperty("javax.net.ssl.keyStore", keyStoreInfo.getLocation());
            System.setProperty("javax.net.ssl.keyStorePassword", keyStoreInfo.getPassword());
            System.setProperty("javax.net.ssl.trustStore", keyStoreInfo.getLocation());
            System.setProperty("javax.net.ssl.trustStorePassword", keyStoreInfo.getPassword());
        }
    }


    private void addEndpoint(Map<String, Object> requestContext, IMovilizerCloudSystem system) {
        String oldEndpoint = (String) requestContext.get(ENDPOINT_ADDRESS_PROPERTY);
        String newEndpoint = system.getEndpoint();

        logger.debug("Preparing to call Movilizer Cloud");
        logger.debug("Endpoint specified in WSDL is [" + oldEndpoint + "]");
        logger.debug("Using endpoint from configuration [" + newEndpoint + "]");

        requestContext.put(ENDPOINT_ADDRESS_PROPERTY, newEndpoint);
    }

    private void addTimeout(Map<String, Object> requestContext, IMovilizerCloudSystem system) {
        int timeout = system.getTimeout();
        requestContext.put("com.sun.xml.internal.ws.connect.timeout", timeout);
        requestContext.put("com.sun.xml.ws.connect.timeout", timeout);

        requestContext.put("com.sun.xml.internal.ws.request.timeout", timeout);
        requestContext.put("com.sun.xml.ws.request.timeout", timeout);

        logger.debug("Set REQUEST/CONNECT.TIMEOUT to:" + timeout);
    }
}
