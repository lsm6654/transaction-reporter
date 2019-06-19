package com.kb.jess.profiler.util;

import spark.utils.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;


public class HttpTestUtils {
    public static TestResponse request(String method, String path, String requestBody) {
        try {
            URL url = new URL("http://localhost:8080" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if ("POST".equals(method)) {
                connection.setDoOutput(true);
                connection.getOutputStream().write(requestBody.getBytes());
            }

            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();

            String body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (Exception e) {
            return new TestResponse(500, "");
        }
    }

    public static class TestResponse {
        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }
    }
}
