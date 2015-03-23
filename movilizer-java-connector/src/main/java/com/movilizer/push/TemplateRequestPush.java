package com.movilizer.push;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.movilitas.movilizer.v12.MovilizerRequest;
import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.jaxb.MovilizerJaxbUnmarshaller;
import com.movilizer.util.template.ITemplateRepository;
import com.movilizer.util.template.IXmlTemplate;

import javax.inject.Named;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@Singleton
public class TemplateRequestPush implements ITemplateRequestPush {

    private final ITemplateRepository repository;
    private final IMovilizerPushCall pushCall;
    private final IMovilizerCloudSystem movilizerCloudSystem;

    @Inject
    public TemplateRequestPush(@Named("Requests") ITemplateRepository repository,
                               IMovilizerPushCall pushCall,
                               IMovilizerCloudSystem movilizerCloudSystem) {
        this.repository = repository;
        this.pushCall = pushCall;
        this.movilizerCloudSystem = movilizerCloudSystem;
    }


    @Override
    public void push(String templateName, Object context) throws Exception {
        MovilizerRequest request = createRequest(templateName, context);
        pushCall.setRequest(request);
        pushCall.send();
    }

    @Override
    public MovilizerRequest createRequest(String templateName, Object context) throws Exception {
        IXmlTemplate template = repository.getTemplate(templateName);
        String xml = template.produceXml(context);
        MovilizerRequest movilizerRequest = MovilizerJaxbUnmarshaller.getInstance().unmarshall(MovilizerRequest.class, xml);
        movilizerRequest.setNumResponses(0);
        movilizerRequest.setSystemId(movilizerCloudSystem.getSystemId());
        movilizerRequest.setSystemPassword(movilizerCloudSystem.getPassword());
        movilizerRequest.setUseAutoAcknowledge(false);
        return movilizerRequest;
    }
}
