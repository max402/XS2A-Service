package de.casestudy.xs2a;

import org.junit.Test;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UrlEncodeUtil {
    @Test
    public void urlEncCert() {
        try {
            String cert = new String(Files.readAllBytes(Paths.get("nginx/certs/nginx.crt")), StandardCharsets.UTF_8);
            String urlEncCert = URLEncoder.encode(cert, "UTF-8");
            System.out.println(cert);
            System.out.println(cert.length());
            System.out.println(urlEncCert);
            System.out.println(urlEncCert.length());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
