package com.movilizer.rest;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@SuppressWarnings("deprecation")
@Singleton
public class AllowAllHttpClientProvider implements Provider<HttpClient> {

    private static ILogger logger = ComponentLogger.getInstance(AllowAllHttpClientProvider.class);
    private final String userName;
    private final String password;

    public AllowAllHttpClientProvider(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    @Override
    public HttpClient get() {
        TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] certificate, String authType) throws CertificateException {
                return true;
            }
        };
        SSLSocketFactory socketFactory;
        try {
            socketFactory = new SSLSocketFactory(acceptingTrustStrategy, ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            logger.fatal(e);
            return null;
        }
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("https", 443, socketFactory));
        ClientConnectionManager connectionManager = new PoolingClientConnectionManager(registry);


        DefaultHttpClient httpClient = new DefaultHttpClient(connectionManager);

        httpClient.setRedirectStrategy(new LaxRedirectStrategy());

        httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(userName, password));
        HttpParams httpParameter = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameter, 3000);
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setSoTimeout(httpParameters, 5000);

        return httpClient;
    }
}
