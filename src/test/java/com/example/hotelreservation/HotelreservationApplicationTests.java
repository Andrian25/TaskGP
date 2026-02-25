package com.example.hotelreservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HotelreservationApplicationTests
{
    private final ObjectMapper objectMapper = new ObjectMapper();
    @LocalServerPort
    private int port;

	@Test
	void contextLoads()
    {
	}

    @Test
    void shouldCreateAndReturnHotel() throws Exception
    {
        final String payload = """
                {
                  "name":"DoubleTree by Hilton Minsk",
                  "description":"The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms.",
                  "brand":"Hilton",
                  "address":{
                    "houseNumber":9,
                    "street":"Pobediteley Avenue",
                    "city":"Minsk",
                    "country":"Belarus",
                    "postCode":"220004"
                  },
                  "contacts":{
                    "phone":"+375 17 309-80-00",
                    "email":"doubletreeminsk.info@hilton.com"
                  },
                  "arrivalTime":{
                    "checkIn":"14:00",
                    "checkOut":"12:00"
                  }
                }
                """;

        final HttpResponse<String> response = post("/property-view/hotels", payload);
        assertThat(response.statusCode()).isEqualTo(201);
        final Map<String, Object> responseBody = objectMapper.readValue(response.body(), Map.class);
        assertThat(responseBody.get("id")).isNotNull();
        assertThat(responseBody.get("name")).isEqualTo("DoubleTree by Hilton Minsk");
        assertThat(responseBody.get("phone")).isEqualTo("+375 17 309-80-00");
    }

    @Test
    void shouldSupportSearchAndHistogram() throws Exception
    {
        final String payload = """
                {
                  "name":"DoubleTree by Hilton Minsk",
                  "description":"The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms.",
                  "brand":"Hilton",
                  "address":{
                    "houseNumber":9,
                    "street":"Pobediteley Avenue",
                    "city":"Minsk",
                    "country":"Belarus",
                    "postCode":"220004"
                  },
                  "contacts":{
                    "phone":"+375 17 309-80-00",
                    "email":"doubletreeminsk.info@hilton.com"
                  },
                  "arrivalTime":{
                    "checkIn":"14:00",
                    "checkOut":"12:00"
                  }
                }
                """;

        final HttpResponse<String> createResponse = post("/property-view/hotels", payload);
        assertThat(createResponse.statusCode()).isEqualTo(201);
        final String createResult = createResponse.body();
        assertThat(createResult).isNotBlank();

        final Long id = objectMapper.readTree(createResult).get("id").asLong();

        final HttpResponse<String> amenityResponse = post("/property-view/hotels/" + id + "/amenities", "[\"Free WiFi\",\"Fitness center\"]");
        assertThat(amenityResponse.statusCode()).isEqualTo(200);

        final HttpResponse<String> searchResponse = get("/property-view/search?city=Minsk&amenities=Free%20WiFi");
        assertThat(searchResponse.statusCode()).isEqualTo(200);
        assertThat(searchResponse.body()).contains("\"id\":" + id);

        final HttpResponse<String> detailsResponse = get("/property-view/hotels/" + id);
        assertThat(detailsResponse.statusCode()).isEqualTo(200);
        assertThat(detailsResponse.body()).contains("\"id\":" + id);
        assertThat(detailsResponse.body()).contains("\"city\":\"Minsk\"");

        final HttpResponse<String> histogramResponse = get("/property-view/histogram/city");
        assertThat(histogramResponse.statusCode()).isEqualTo(200);
        assertThat(histogramResponse.body()).contains("Minsk");
	}

    private HttpResponse<String> post(final String path, final String payload) throws IOException, InterruptedException
    {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url(path)))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> get(final String path) throws IOException, InterruptedException
    {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url(path)))
                .GET()
                .build();
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String url(final String path)
    {
        return "http://localhost:" + port + path;
    }
}
