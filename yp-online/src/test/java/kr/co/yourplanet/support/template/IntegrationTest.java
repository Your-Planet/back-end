package kr.co.yourplanet.support.template;

import static kr.co.yourplanet.support.container.RedisTestContainerConfig.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.yourplanet.support.container.RedisTestContainerConfig;
import okhttp3.mockwebserver.MockWebServer;

@SpringBootTest
@Sql(scripts = "classpath:db/setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@Import(RedisTestContainerConfig.class)
@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_OUT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class IntegrationTest {

    private static final int MOCK_SERVER_PORT = 8090;

    protected MockWebServer mockWebServer;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeAll
    void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(MOCK_SERVER_PORT);
    }

    @AfterAll
    void teardown() throws IOException {
        mockWebServer.shutdown();
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("payments.toss.uri",
                () -> "http://localhost:" + MOCK_SERVER_PORT + "/v1/payments/confirm");
    }


    /**
     * 유틸 메서드
     */
    protected String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
