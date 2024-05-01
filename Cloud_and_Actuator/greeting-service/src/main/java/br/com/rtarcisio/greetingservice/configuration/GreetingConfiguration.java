package br.com.rtarcisio.greetingservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("greeting-service")
@Data
public class GreetingConfiguration {
    private String greeting;
    private String defaultValue;

    public GreetingConfiguration() {

    }


}
