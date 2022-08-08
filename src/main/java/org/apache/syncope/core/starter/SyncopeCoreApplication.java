package org.apache.syncope.core.starter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info =
        @Info(title = "Apache Syncope", version = "4.0.0", contact =
                @Contact(name = "The Apache Syncope community",
                        email = "dev@syncope.apache.org",
                        url = "https://syncope.apache.org")),
        security = {
            @SecurityRequirement(name = "BasicAuthentication"),
            @SecurityRequirement(name = "Bearer") })
@SpringBootApplication
public class SyncopeCoreApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SyncopeCoreApplication.class, args);
    }
}
