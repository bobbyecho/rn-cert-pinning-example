package com.freshinstall;

import com.datatheorem.android.trustkit.TrustKit;
import com.datatheorem.android.trustkit.config.PublicKeyPin;

import javax.inject.Inject;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;

public class OkHttpCertPin {
    private final TrustKit mTrustKit;
    @Inject
    public OkHttpCertPin(TrustKit trustKit)
    {
        mTrustKit = trustKit;
    }

    public OkHttpClient extend(OkHttpClient currentClient, String hostName) {
        CertificatePinner.Builder certificatePinnerBuilder = new CertificatePinner.Builder();
        for (PublicKeyPin key : mTrustKit.getConfiguration().getPolicyForHostname(hostName).getPublicKeyPins()) {
            certificatePinnerBuilder.add(hostName, "sha256/" + key.toString());
        }
        return currentClient.newBuilder().certificatePinner(certificatePinnerBuilder.build()).build();
    }
}