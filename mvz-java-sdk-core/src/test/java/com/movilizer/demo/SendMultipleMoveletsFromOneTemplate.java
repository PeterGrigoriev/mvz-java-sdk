package com.movilizer.demo;

import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.MovilizerRequestSender;
import com.movilizer.connector.MovilizerWebServiceProvider;
import com.movilizer.push.MovilizerPushCall;
import com.movilizer.util.config.IMovilizerConfig;
import com.movilizer.util.config.MovilizerConfig;
import com.movilizer.util.template.ResourceXmlTemplateRepository;

/**
 * @author Peter.Grigoriev@gmail.com
 */
public class SendMultipleMoveletsFromOneTemplate {

    private IMovilizerConfig config;

    public static void main(String[] args) throws Exception {
        new SendMultipleMoveletsFromOneTemplate().run();
    }

    public SendMultipleMoveletsFromOneTemplate() {
        this.config = MovilizerConfig.getInstance(SendMultipleMoveletsFromOneTemplate.class);
    }

    private void run() throws Exception {

        IMovilizerCloudSystem movilizerSystem = config.getMovilizerSystem();
        System.out.println("movilizerSystem.getSystemId() = " + movilizerSystem.getSystemId());

        MovilizerRequestSender requestSender = new MovilizerRequestSender(new MovilizerWebServiceProvider());
        MovilizerPushCall pushCall = new MovilizerPushCall(movilizerSystem, null, requestSender, null, new ResourceXmlTemplateRepository("TestProject", SendMultipleMoveletsFromOneTemplate.class));


        pushCall.addMovelets(new SampleDataProvider("Sam Khebanoff"), "/com/movilizer/templaterepository/TestUserMovelet.vm");

        pushCall.send();
    }
}
