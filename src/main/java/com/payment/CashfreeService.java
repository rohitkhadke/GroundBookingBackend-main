package com.payment;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import tools.jackson.databind.ObjectMapper;

@Service
public class CashfreeService {

    private final String APP_ID =
            System.getenv("CASHFREE_APP_ID");

    private final String SECRET_KEY =
            System.getenv("CASHFREE_SECRET_KEY");

    public Map<String, Object> createOrder(
            double amount) {

        try {
        
            String orderId =
                    "ORDER_" + UUID.randomUUID();

            Map<String, Object> order =
                    new HashMap<>();

            order.put("order_id", orderId);

            order.put("order_amount", amount);

            order.put("order_currency", "INR");

            Map<String, String> customer =
                    new HashMap<>();

            customer.put(
                    "customer_id",
                    "cust_001");

            customer.put(
                    "customer_name",
                    "Pratik");

            customer.put(
                    "customer_email",
                    "test@test.com");

            customer.put(
                    "customer_phone",
                    "9999999999");

            order.put(
                    "customer_details",
                    customer);

            ObjectMapper mapper =
                    new ObjectMapper();

            String requestBody =
                    mapper.writeValueAsString(order);

            HttpRequest request =
                    HttpRequest.newBuilder()

                    .uri(
                      URI.create(
                       "https://sandbox.cashfree.com/pg/orders"
                      )
                    )

                    .header(
                       "Content-Type",
                       "application/json"
                    )

                    .header(
                       "x-client-id",
                       APP_ID
                    )

                    .header(
                       "x-client-secret",
                       SECRET_KEY
                    )

                    .header(
                       "x-api-version",
                       "2023-08-01"
                    )

                    .POST(
                      HttpRequest.BodyPublishers
                      .ofString(requestBody)
                    )

                    .build();

            HttpClient client =
                    HttpClient.newHttpClient();

            HttpResponse<String> response =
                    client.send(
                      request,
                      HttpResponse.BodyHandlers.ofString()
                    );

            return mapper.readValue(
                    response.body(),
                    Map.class);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }
}