package ir.imorate.yom.configuration.properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "app.name=You Owe Me",
        "app.description=An accounting application among friends",
        "app.version=0.0.1"
})
@ActiveProfiles("test")
@DisplayName("Application properties test")
class ApplicationPropertiesTest {

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    Environment environment;

    @Value("${app.name}")
    private String name;

    @Value("${app.description}")
    private String description;

    @Value("${app.version}")
    private String version;

    @Test
    @DisplayName("Application properties and Environment values")
    void shouldEnvBeanAndAppPropertiesReturnSameValues() {
        String name = environment.getProperty("app.name");
        String description = environment.getProperty("app.description");
        String version = environment.getProperty("app.version");

        assertThat(applicationProperties.getName()).isEqualTo(name);
        assertThat(applicationProperties.getDescription()).isEqualTo(description);
        assertThat(applicationProperties.getVersion()).isEqualTo(version);

        assertThat(this.name).isEqualTo(name);
        assertThat(this.description).isEqualTo(description);
        assertThat(this.version).isEqualTo(version);
    }

}