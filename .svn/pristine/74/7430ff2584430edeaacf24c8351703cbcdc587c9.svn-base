package com.kentec.milkbox.homedine.network;

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SSLX509 implements X509TrustManager {
	X509TrustManager myJSSEX509TrustManager;

	public SSLX509() throws Exception {
		KeyStore ks = KeyStore.getInstance("BKS");

		TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
		tmf.init(ks);
		TrustManager tms[] = tmf.getTrustManagers();
		for (int i = 0; i < tms.length; i++) {
			if (tms[i] instanceof X509TrustManager) {
				myJSSEX509TrustManager = (X509TrustManager) tms[i];
				return;
			}
		}
	}

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

}