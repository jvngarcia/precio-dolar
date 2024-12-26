package com.example.precio_dolar.infrastructure.repositories;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.example.precio_dolar.domain.dtos.CurrencyDTO;
import com.example.precio_dolar.domain.repositories.WebScrapping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Repository
public class BcvScrapping implements WebScrapping {

    @Value("${bcv.url}")
    String URL;

    @Override
    public List<CurrencyDTO> execute() {

        List<CurrencyDTO> currencies = new ArrayList<>();

        try {
            disableSSLValidation();
            Document document = Jsoup.connect(URL).get();

            Element dolar = document.select("div#dolar > div > div > div.centrado > strong").first();
            Element euro = document.select("div#euro > div > div > div.centrado > strong").first();

            if (dolar != null && euro != null) {
                CurrencyDTO dollar = new CurrencyDTO("Dolar", dolar.text());
                CurrencyDTO eur = new CurrencyDTO("Euro", euro.text());

                currencies.add(dollar);
                currencies.add(eur);

                return currencies;
            }

        } catch (Exception e) {
            System.err.println("Error al obtener la tasa de cambio del BCV");
            e.printStackTrace();
        }

        return null;
    }

    private static void disableSSLValidation() throws Exception {
        TrustManager[] trustAllCertificates = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCertificates, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Deshabilitar hostname verification
        HostnameVerifier allHostsValid = (hostname, session) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
}
