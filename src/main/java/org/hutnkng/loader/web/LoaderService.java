package org.hutnkng.loader.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
public class LoaderService {

    public String load(URL url) {
        log.info("start loading {}", url);
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode <= 299) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                reader.close();
                connection.disconnect();
                log.info("end loading {}", url);
                return buffer.toString();
            }
            log.error("bad response code ({}) on {}", responseCode, url);
            return null;
        } catch (IOException e) {
            log.error("io exception in {}", url);
            return null;
        }
    }
}
