package ir.imorate.yom.configuration.properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
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
    @DisplayName("Application properties")
    void testApplicationProperties() {
        ApplicationProperties applicationProperties = new ApplicationProperties();

        assertThat(applicationProperties.getName()).isNull();
        assertThat(applicationProperties.getDescription()).isNull();
        assertThat(applicationProperties.getVersion()).isNull();

        applicationProperties.setName("Test app");
        applicationProperties.setDescription("Test app description");
        applicationProperties.setVersion("2.3.5");

        assertThat(applicationProperties.getName()).isEqualTo("Test app");
        assertThat(applicationProperties.getDescription()).isEqualTo("Test app description");
        assertThat(applicationProperties.getVersion()).isEqualTo("2.3.5");
    }

    @Test
    @DisplayName("Application properties bean")
    void testApplicationPropertiesBean() {
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