package com.knowledgesharing.ms.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.knowledgesharing.ms.Application;
import com.knowledgesharing.ms.datatransfer.KnowledgeSharingDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class KnowSharingControllerApiTest {

    @LocalServerPort
    private int randomServerPort;

    private WireMockServer wireMockServer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private RequestSpecification request;
    private Response response;

    @BeforeEach
    void beforeEach() {
        startWireMockServer();
    }

    @AfterEach
    void tearDown() {
        stopWireMockServer();
    }

    private void stopWireMockServer() {
        wireMockServer.stop();
    }

    private void startWireMockServer() {
        wireMockServer = new WireMockServer(
                wireMockConfig().port(8888));
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
    }

    @Nested
    @TestInstance(PER_CLASS)
    class FetchDetails {
        @Test
        void shouldFetchDetailsAPI() throws Exception {
            String author = "Climate action needs new frontline leadership";
            String title = "Ozawa Bineshi Albert";
            String url = "http://localhost:" + randomServerPort + "/v1/knowledge-sharing";
            url += "?author=" + author;
            url += "&title=" + title;
            url += "&likes=12000";
            url += "&views=404000";
            response = given().when().get(url);
            response.then().statusCode(200);
        }
    }

    @Nested
    @TestInstance(PER_CLASS)
    class InsertDetails {
        @Test
        void shouldInsertRecords() throws Exception {
            String url = "http://localhost:" + randomServerPort + "/v1/knowledge-sharing";
            KnowledgeSharingDto knowledgeSharingDto = KnowledgeSharingDto
                    .builder()
                    .title("some-title")
                    .author("some-author")
                    .views(100L)
                    .likes(100L)
                    .date(LocalDate.now())
                    .link("some-link")
                    .build();

            String requestBody = objectMapper.writeValueAsString(knowledgeSharingDto);
            request = given().contentType(ContentType.JSON);
            response = request.when().body(requestBody).post(url);
            response.then().statusCode(201);

        }
    }

    @Nested
    @TestInstance(PER_CLASS)
    class modifyDetails {
        @Test
        void shouldModifyRecords() throws Exception {
            String url = "http://localhost:" + randomServerPort + "/v1/knowledge-sharing/2";
            KnowledgeSharingDto knowledgeSharingDto = KnowledgeSharingDto
                    .builder()
                    .title("some-title")
                    .author("some-author")
                    .views(100L)
                    .likes(100L)
                    .link("some-link")
                    .build();

            String requestBody = objectMapper.writeValueAsString(knowledgeSharingDto);
            request = given().contentType(ContentType.JSON);
            response = request.when().body(requestBody).put(url);
            response.then().statusCode(200);

        }
    }

    @Nested
    @TestInstance(PER_CLASS)
    class deleteDetails {
        @Test
        void shouldModifyRecords() throws Exception {
            String url = "http://localhost:" + randomServerPort + "/v1/knowledge-sharing/3";
            response = given().when().delete(url);
            response.then().statusCode(204);
        }
    }

}
