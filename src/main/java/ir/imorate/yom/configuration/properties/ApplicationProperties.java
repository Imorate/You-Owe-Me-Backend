package ir.imorate.yom.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class ApplicationProperties {

    /**
     * Name of the application.
     */
    private String name = "You Owe Me";

    /**
     * Description of the application.
     */
    private String description;

    /**
     * Version of the application.
     */
    private String version = "1.0.0";

}
