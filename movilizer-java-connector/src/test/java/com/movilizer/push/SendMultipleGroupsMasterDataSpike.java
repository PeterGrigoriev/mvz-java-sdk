package com.movilizer.push;

import com.movilizer.connector.*;
import com.movilizer.jaxb.MovilizerJaxbMarshaller;
import com.movilizer.masterdata.MasterdataFieldNames;
import com.movilizer.masterdata.MasterdataJsonReader;
import com.movilizer.masterdata.MasterdataXmlSettings;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class SendMultipleGroupsMasterDataSpike {
    private static final String ENDPOINT = "https://demo.movilizer.com/MovilizerDistributionService/WebService/";
    private static final int SYSTEM_ID = 502810010;
    private static final String PASSWORD  = "UM0BjIpbTQ7odkYySKC7l28u19aO4";

    private static final IProxyInfo PROXY_INFO = null;// new ProxyInfo("proxy.cofely-fr.gdfsuez.net", 8080);
    private final MovilizerCloudSystem cloudSystem;
    private final MasterdataJsonReader masterdataJsonReader;
    private final MasterdataXmlSettings settings;
    private MovilizerJaxbMarshaller marshaller;

    public static void main(String[] args) throws Exception {
        new SendMultipleGroupsMasterDataSpike().run();
    }


    public SendMultipleGroupsMasterDataSpike() {
        this.cloudSystem = new MovilizerCloudSystem(SYSTEM_ID, PASSWORD, ENDPOINT, 180000);
        MasterdataFieldNames fieldNames = new MasterdataFieldNames("group", "id", "description");
        fieldNames.setEventType("eventType");
        fieldNames.setEventId("eventId");
        settings = new MasterdataXmlSettings("spike", "poolOne", 10, 1, fieldNames, null);

        masterdataJsonReader = new MasterdataJsonReader();
        marshaller = MovilizerJaxbMarshaller.getInstance();
    }

    private void run() throws Exception {

//        IMasterdataReaderResult events = masterdataJsonReader.read(new StringReader("[\n" +
//                "    {\"eventId\":1, \"eventType\":\"Create\", id:\"1\", \"fieldOne\":12, \"fieldTwo\":\"ABC\", \"group\": \"groupOne\", \"description\":\"firstRecord\"},\n" +
//                "    {\"eventId\":2, \"eventType\":\"Create\", id:\"2\", \"fieldOne\":21, \"fieldTwo\":\"CBA\", \"group\": \"groupOne\", \"description\":\"secondRecord\"}\n" +
//                "]"), settings);

        MovilizerSynchronousPushCall call = newCloudCall();
//        call.addMasterdataPoolUpdate(events.getMasterdataPoolUpdate());

        call.addMasterdataGroupReference("1", settings.getPool(), "groupTwo", "ackOne");
        call.addMasterdataGroupReference("2", settings.getPool(), "groupThree", "ackTwo");

        sendAndDumpResponse(call);
    }

    private MovilizerSynchronousPushCall newCloudCall() {
        IMovilizerRequestSender requestSender = new MovilizerRequestSender(new MovilizerWebServiceProvider());
        return new MovilizerSynchronousPushCall(cloudSystem, PROXY_INFO, requestSender, null, null);
    }

    private void sendAndDumpResponse(MovilizerSynchronousPushCall call) {
        System.out.println("Request:");
        System.out.println(marshaller.marshall(call.accessRequest()));
        MovilizerCallResult movilizerCallResult = call.synchronousSend();
        System.out.println("Response:");

        System.out.println(marshaller.marshall(movilizerCallResult.getResponse()));
    }

}
