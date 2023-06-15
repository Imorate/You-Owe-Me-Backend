package ir.imorate.yom.security.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@DisplayName("API Response test")
class ApiResponseTest {

    private final LocalDateTime localDateTime = LocalDateTime.of(2023, 6, 5, 12, 38, 58, 55);
    @Autowired
    private JacksonTester<ApiResponse> json;
    private ApiResponse response;

    @BeforeEach
    void setUp() {
        response = ApiResponse.builder()
                .timestamp(localDateTime)
                .reason("Test reason")
                .message("Test message")
                .developerMessage("Hello from developer")
                .data("Sample data")
                .build();
    }

    @Test
    @DisplayName("Serialization")
    void testSerialization() throws IOException {
        JsonContent<ApiResponse> content = json.write(response);
        assertThat(content).hasJsonPath("$.timestamp", response.getTimestamp());
        assertThat(content).hasJsonPath("$.reason", response.getReason());
        assertThat(content).hasJsonPath("$.message", response.getMessage());
        assertThat(content).hasJsonPath("$.developerMessage", response.getDeveloperMessage());
        assertThat(content).hasJsonPath("$.data", response.getData());
    }

    @Test
    @DisplayName("Deserialization")
    void testDeserialization() throws IOException {
        String json = "{" +
                "\"timestamp\":\"" + localDateTime + "\"," +
                "\"reason\":\"Test reason\"," +
                "\"message\":\"Test message\"," +
                "\"developerMessage\":\"Hello from developer\"," +
                "\"data\":\"Sample data\"" +
                "}";
        assertThat(this.json.parse(json))
                .usingRecursiveComparison()
                .isEqualTo(response);
    }

}