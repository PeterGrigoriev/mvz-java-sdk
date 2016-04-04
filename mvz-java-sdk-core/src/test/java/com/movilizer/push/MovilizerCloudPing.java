package com.movilizer.push;

import com.google.inject.Guice;
import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.MovilizerCloudSystem;
import com.movilizer.connector.MovilizerRequestSender;
import com.movilizer.connector.MovilizerWebServiceProvider;
import com.movilizer.module.MovilizerModule;
import com.movilizer.util.proxy.ProxyInfo;

import java.security.NoSuchAlgorithmException;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerCloudPing {

    public static final MovilizerCloudSystem EMOVE_PRODUCTIVE = new MovilizerCloudSystem(
            1256, "FI4LnOhfSm2zFPxEiGbjHKAhm1yeIi2", "https://movilizer.com/MovilizerDistributionService/WebService/", 18000
    );
    public static final ProxyInfo COFELY_PROXY = new ProxyInfo("proxy.cofely-fr.gdfsuez.net", 8080);
    public static final MovilizerRequestSender REQUEST_SENDER = new MovilizerRequestSender(new MovilizerWebServiceProvider());

    public MovilizerCloudPing() {
        cloudSystem = EMOVE_PRODUCTIVE;
    }


    public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException {
        MovilizerModule movilizerModule = new MovilizerModule() {
            @Override
            protected void setUp() {

            }
        };
        Guice.createInjector(movilizerModule).getInstance(MovilizerCloudPing.class).run();
    }


    private final IMovilizerCloudSystem cloudSystem;

    public void run() throws InterruptedException, NoSuchAlgorithmException {
        prepareSSL();

        int numberOfSuccessfulCalls = 0;
        int numberOfFailedCalls = 0;

        for (int i = 0; i < 100; i++) {
            try {
                IMovilizerPushCall pushCall = new MovilizerPushCall(cloudSystem, COFELY_PROXY, REQUEST_SENDER, null, null);
                boolean success = pushCall.send();
                System.out.println("Call successful: " +  success);
                if(success) {
                    numberOfSuccessfulCalls++;
                }
                else  {
                    numberOfFailedCalls++;
                }

            } catch (Exception e) {
                e.printStackTrace(System.out);
                numberOfFailedCalls++;
            }
            System.out.println("SUCCESSFUL CALLS: " + numberOfSuccessfulCalls);
            System.out.println("FAILED CALLS: " + numberOfFailedCalls);
            Thread.sleep(1000);
        }

    }

    private void prepareSSL() throws NoSuchAlgorithmException {
//        SSLParameters sslParameters = SSLContext.getDefault().getSupportedSSLParameters();
//        sslParameters.setProtocols(new String[]{""});
//        java.security.Security.setProperty("jdk.tls.disabledAlgorithms","DH, DHE, ECDHE");
    }
}
