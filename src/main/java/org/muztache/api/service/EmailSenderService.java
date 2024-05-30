package org.muztache.api.service;

import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailSenderService {

    private static final String API_KEY = System.getenv("EMAIL_SENDER_API_KEY");

    private static final Headers HEADERS = Headers.of(
            "Content-Type", "application/json",
            "X-Requested-With", "XMLHttpRequest",
            "Authorization", "Bearer %s".formatted(API_KEY)
    );

    private static final String APPLICATION_JSON = "application/json";
    private static final String API_SERVICE_URL = "https://api.mailersend.com/v1/email";
    private static final String SENDER = "iMS_xn5QV6@trial-3yxj6ljv6q0ldo2r.mlsender.net";

    private final OkHttpClient client = new OkHttpClient.Builder()
            .build();

    public void send(String receiverEmail, String subject, String text) {

        JSONObject from = new JSONObject();
        from.put("email", SENDER);

        JSONArray toArray = new JSONArray();
        JSONObject to = new JSONObject();
        to.put("email", receiverEmail);
        toArray.add(to);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("from", from);
        jsonObject.put("to", toArray);
        jsonObject.put("text", text);
        jsonObject.put("subject", subject);

        RequestBody requestBody = RequestBody.create(
                jsonObject.toJSONString(), MediaType.parse(APPLICATION_JSON)
        );

        Request request = new Request.Builder()
                .url(API_SERVICE_URL)
                .headers(HEADERS)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);

        try(Response response = call.execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException(response.body().string());
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
