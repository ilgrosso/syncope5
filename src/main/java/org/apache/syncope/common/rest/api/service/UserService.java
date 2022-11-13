package org.apache.syncope.common.rest.api.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.syncope.common.lib.to.PagedResult;
import org.apache.syncope.common.lib.to.ProvisioningResult;
import org.apache.syncope.common.lib.to.UserCR;
import org.apache.syncope.common.lib.to.UserTO;
import org.apache.syncope.common.lib.to.UserUR;
import org.apache.syncope.common.rest.api.RESTHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PatchExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

@HttpExchange("/rest/users")
@RequestMapping("/rest/users")
public interface UserService {

    @Operation(summary = "Creates a new user.")
    @Parameter(name = RESTHeaders.PREFER, in = ParameterIn.HEADER, description =
            "Allows client to specify a preference for the result to be returned from the server", allowEmptyValue =
            true, schema =
            @Schema(defaultValue = "return-content", allowableValues = { "return-content", "return-no-content" }))
    @Parameter(name = RESTHeaders.NULL_PRIORITY_ASYNC, in = ParameterIn.HEADER, description =
            "If 'true', instructs the propagation process not to wait for completion when communicating"
            + " with External Resources with no priority set",
            allowEmptyValue = true, schema =
            @Schema(type = "boolean", defaultValue = "false"))
    @ApiResponses(value =
            @ApiResponse(responseCode = "201", description =
                    "User successfully created enriched with propagation status information, as Entity,"
                    + " or empty if 'Prefer: return-no-content' was specified",
                    content =
                    @Content(schema =
                            @Schema(implementation = ProvisioningResult.class)), headers = {
                @Header(name = RESTHeaders.RESOURCE_KEY, schema =
                        @Schema(type = "string"), description =
                        "UUID generated for the user created"),
                @Header(name = HttpHeaders.LOCATION, schema =
                        @Schema(type = "string"), description =
                        "URL of the user created"),
                @Header(name = RESTHeaders.PREFERENCE_APPLIED, schema =
                        @Schema(type = "string"), description =
                        "Allows the server to inform the "
                        + "client about the fact that a specified preference was applied") }))
    @PostExchange(accept = MediaType.APPLICATION_JSON_VALUE, contentType = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<UserTO>> create(@RequestBody UserCR createReq);

    @Operation(summary = "Deletes user matching provided key.")
    @Parameter(name = RESTHeaders.PREFER, in = ParameterIn.HEADER, description =
            "Allows client to specify a preference for the result to be returned from the server", allowEmptyValue =
            true, schema =
            @Schema(defaultValue = "return-content", allowableValues = { "return-content", "return-no-content" }))
    @Parameter(name = HttpHeaders.IF_MATCH, in = ParameterIn.HEADER, description =
            "When the provided ETag value does not match the latest modification date of the entity, "
            + "an error is reported and the requested operation is not performed.",
            allowEmptyValue = true, schema =
            @Schema(type = "string"))
    @Parameter(name = RESTHeaders.NULL_PRIORITY_ASYNC, in = ParameterIn.HEADER, description =
            "If 'true', instructs the propagation process not to wait for completion when communicating"
            + " with External Resources with no priority set",
            allowEmptyValue = true, schema =
            @Schema(type = "boolean", defaultValue = "false"))
    @Parameter(name = "key", description = "User's key", in = ParameterIn.PATH, schema =
            @Schema(type = "string"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description =
                "User, Group or Any Object successfully deleted enriched with propagation status "
                + "information, as Entity",
                content =
                @Content(schema =
                        @Schema(implementation = ProvisioningResult.class))),
        @ApiResponse(responseCode = "204", description =
                "No content if 'Prefer: return-no-content' was specified", headers =
                @Header(name = RESTHeaders.PREFERENCE_APPLIED, schema =
                        @Schema(type = "string"), description =
                        "Allows the server to inform the "
                        + "client about the fact that a specified preference was applied")),
        @ApiResponse(responseCode =
                "412", description =
                "The ETag value provided via the 'If-Match' header does not match the latest modification"
                + " date of the entity") })
    @DeleteExchange(value = "/{key}", contentType = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(value = "/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<UserTO>> delete(@PathVariable String key);

    @Operation(summary = "Reads the user matching the provided key.")
    @Parameter(name = "key", description = "User's key", in = ParameterIn.PATH, schema =
            @Schema(type = "string"))
    @ApiResponse(responseCode = "200", description = "user with matching key")
    @GetExchange(value = "/{key}")
    @GetMapping(value = "/{key}")
    Mono<UserTO> read(@PathVariable String key);

    @Operation(summary = "Search for users.")
    @ApiResponse(responseCode = "200", description = "Paged list of any objects matching the given query")
    @GetExchange(value = "/")
    @GetMapping(value = "/")
    Mono<PagedResult<UserTO>> search();

    @Operation(summary = "Updates user matching the provided key.")
    @Parameter(name = RESTHeaders.PREFER, in = ParameterIn.HEADER, description =
            "Allows client to specify a preference for the result to be returned from the server", allowEmptyValue =
            true, schema =
            @Schema(defaultValue = "return-content", allowableValues = { "return-content", "return-no-content" }))
    @Parameter(name = HttpHeaders.IF_MATCH, in = ParameterIn.HEADER, description =
            "When the provided ETag value does not match the latest modification date of the entity, "
            + "an error is reported and the requested operation is not performed.",
            allowEmptyValue = true, schema =
            @Schema(type = "string"))
    @Parameter(name = RESTHeaders.NULL_PRIORITY_ASYNC, in = ParameterIn.HEADER, description =
            "If 'true', instructs the propagation process not to wait for completion when communicating"
            + " with External Resources with no priority set",
            allowEmptyValue = true, schema =
            @Schema(type = "boolean", defaultValue = "false"))
    @Parameter(name = "key", description = "User's key", in = ParameterIn.PATH, schema =
            @Schema(type = "string"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description =
                "User successfully updated enriched with propagation status information, as Entity", content =
                @Content(schema =
                        @Schema(implementation = ProvisioningResult.class))),
        @ApiResponse(responseCode = "204", description =
                "No content if 'Prefer: return-no-content' was specified", headers =
                @Header(name = RESTHeaders.PREFERENCE_APPLIED, schema =
                        @Schema(type = "string"), description =
                        "Allows the server to inform the "
                        + "client about the fact that a specified preference was applied")),
        @ApiResponse(responseCode =
                "412", description =
                "The ETag value provided via the 'If-Match' header does not match the latest modification"
                + " date of the entity") })
    @PatchExchange(value = "/{key}",
            accept = MediaType.APPLICATION_JSON_VALUE, contentType = MediaType.APPLICATION_JSON_VALUE)
    @PatchMapping(value = "/{key}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<UserTO>> update(@PathVariable String key, @RequestBody UserUR updateReq);
}
