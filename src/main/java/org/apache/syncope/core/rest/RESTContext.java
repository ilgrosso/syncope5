package org.apache.syncope.core.rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.syncope.common.lib.SyncopeConstants;
import org.apache.syncope.common.lib.to.ErrorTO;
import org.apache.syncope.common.lib.types.ClientExceptionType;
import org.apache.syncope.common.rest.api.RESTHeaders;
import org.apache.syncope.core.logic.UserLogic;
import org.apache.syncope.core.rest.service.UserService;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.view.HttpMessageWriterView;

@OpenAPIDefinition(info =
        @Info(title = "Apache Syncope", version = "4.0.0", contact =
                @Contact(name = "The Apache Syncope community",
                        email = "dev@syncope.apache.org",
                        url = "https://syncope.apache.org")),
        security = {
            @SecurityRequirement(name = "BasicAuthentication"),
            @SecurityRequirement(name = "Bearer") })
@SecuritySchemes({
    @SecurityScheme(name = "BasicAuthentication", type = SecuritySchemeType.HTTP, scheme = "basic"),
    @SecurityScheme(name = "Bearer", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
})
@AutoConfigureBefore(WebFluxAutoConfiguration.class)
@Configuration
public class RESTContext implements WebFluxConfigurer {

    @Override
    public void configureViewResolvers(final ViewResolverRegistry registry) {
        registry.defaultViews(new HttpMessageWriterView(new Jackson2JsonEncoder()));
    }

    private OpenApiCustomizer openApiCustomizer() {
        return openAPI -> openAPI.getPaths().values().stream().
                flatMap(pathItem -> pathItem.readOperations().stream()).
                forEach(operation -> {
                    if (operation.getParameters() != null) {
                        if (operation.getParameters().stream().noneMatch(
                                p -> p instanceof HeaderParameter && RESTHeaders.DOMAIN.equals(p.getName()))) {

                            HeaderParameter parameter = new HeaderParameter();
                            parameter.setName(RESTHeaders.DOMAIN);
                            parameter.setRequired(true);

                            ExternalDocumentation extDoc = new ExternalDocumentation();
                            extDoc.setDescription("Apache Syncope Reference Guide");
                            extDoc.setUrl("https://syncope.apache.org/docs/3.0/reference-guide.html#domains");

                            Schema<String> schema = new Schema<>();
                            schema.setDescription("Domains are built to facilitate multitenancy.");
                            schema.setExternalDocs(extDoc);
                            schema.setEnum(List.of(SyncopeConstants.MASTER_DOMAIN));
                            schema.setDefault(SyncopeConstants.MASTER_DOMAIN);
                            parameter.setSchema(schema);

                            operation.addParametersItem(parameter);
                        }

                        if (operation.getParameters().stream().noneMatch(
                                p -> p instanceof HeaderParameter && RESTHeaders.DELEGATED_BY.equals(p.getName()))) {

                            HeaderParameter parameter = new HeaderParameter();
                            parameter.setName(RESTHeaders.DELEGATED_BY);
                            parameter.setRequired(false);

                            ExternalDocumentation extDoc = new ExternalDocumentation();
                            extDoc.setDescription("Apache Syncope Reference Guide");
                            extDoc.setUrl("https://syncope.apache.org/docs/3.0/reference-guide.html#delegation");

                            Schema<Object> schema = new Schema<>();
                            schema.setDescription("Acton behalf of someone else");
                            schema.setExternalDocs(extDoc);
                            parameter.setSchema(schema);

                            operation.addParametersItem(parameter);
                        }
                    }

                    ApiResponses responses = operation.getResponses();
                    if (responses == null) {
                        responses = new ApiResponses();
                        operation.setResponses(responses);
                    }

                    ApiResponse defaultResponse = responses.getDefault();
                    if (defaultResponse != null) {
                        responses.remove(ApiResponses.DEFAULT);
                        responses.addApiResponse("200", defaultResponse);
                    }

                    Map<String, Header> headers = new LinkedHashMap<>();
                    headers.put(
                            RESTHeaders.ERROR_CODE,
                            new Header().schema(new Schema<>().type("string")).description("Error code"));
                    headers.put(
                            RESTHeaders.ERROR_INFO,
                            new Header().schema(new Schema<>().type("string")).description("Error message(s)"));

                    ErrorTO sampleError = new ErrorTO();
                    sampleError.setStatus(HttpStatus.BAD_REQUEST.value());
                    sampleError.setType(ClientExceptionType.InvalidEntity);
                    sampleError.getElements().add("error message");

                    Schema<ErrorTO> errorSchema = new Schema<>();
                    errorSchema.example(sampleError).
                            addProperty("status", new IntegerSchema().description("HTTP status code")).
                            addProperty("type", new StringSchema().
                                    _enum(Stream.of(ClientExceptionType.values()).
                                            map(Enum::name).collect(Collectors.toList())).
                                    description("Error code")).
                            addProperty("elements", new ArraySchema().type("string").description("Error message(s)"));

                    Content content = new Content();
                    content.addMediaType(
                            org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                            new MediaType().schema(errorSchema));

                    responses.addApiResponse("400", new ApiResponse().
                            description("An error occurred; HTTP status code can vary depending on the actual error: "
                                    + "400, 403, 404, 409, 412").
                            headers(headers).
                            content(content));
                });
    }

    @Bean
    public GroupedOpenApi openApi() {
        return GroupedOpenApi.builder().
                group("Users").
                pathsToMatch("/**").
                addOpenApiCustomizer(openApiCustomizer()).
                build();
    }

    @Bean
    public UserService userService(final UserLogic userLogic) {
        return new UserService(userLogic);
    }
}
