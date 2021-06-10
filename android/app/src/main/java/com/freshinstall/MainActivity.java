package com.freshinstall;

 import android.os.Bundle;

 import com.datatheorem.android.trustkit.TrustKit;

 import com.datatheorem.android.trustkit.pinning.OkHttp3Helper;
 import com.facebook.react.ReactActivity;
 import com.facebook.react.modules.network.OkHttpClientProvider;

 import java.io.IOException;
 import java.net.MalformedURLException;
 import java.net.URL;

 import javax.net.ssl.HttpsURLConnection;

 import okhttp3.OkHttpClient;

public class MainActivity extends ReactActivity {

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */

  private OkHttpClient.Builder currentClient;
  private String hostname = "https://api.fingerinc.xyz";

  @Override
  protected String getMainComponentName() {
    return "freshinstall";
  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TrustKit.initializeWithNetworkSecurityConfiguration(this);

        URL url = null;
        try {
            url = new URL(hostname);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String serverHostname = url.getHost();

        // HttpsUrlConnection
        HttpsURLConnection connection = null;

        try {
            connection = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.setSSLSocketFactory(TrustKit.getInstance().getSSLSocketFactory(serverHostname));

        // OkHttp 3.0.x, 3.1.x and 3.2.x
        currentClient = new OkHttpClient.Builder()
                        .sslSocketFactory(OkHttp3Helper.getSSLSocketFactory())
                        .addInterceptor(OkHttp3Helper.getPinningInterceptor())
                        .followRedirects(false)
                        .followSslRedirects(false);
    }
}
