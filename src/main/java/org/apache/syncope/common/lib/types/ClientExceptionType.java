package org.apache.syncope.common.lib.types;

import org.springframework.http.HttpStatus;

public enum ClientExceptionType {

    AssociatedAnys(HttpStatus.BAD_REQUEST),
    AssociatedResources(HttpStatus.BAD_REQUEST),
    Composite(HttpStatus.BAD_REQUEST),
    ConcurrentModification(HttpStatus.PRECONDITION_FAILED),
    ConnectorException(HttpStatus.BAD_REQUEST),
    DataIntegrityViolation(HttpStatus.CONFLICT),
    EntityExists(HttpStatus.CONFLICT),
    GenericPersistence(HttpStatus.BAD_REQUEST),
    HasChildren(HttpStatus.BAD_REQUEST),
    InvalidAccessToken(HttpStatus.INTERNAL_SERVER_ERROR),
    InvalidPrivilege(HttpStatus.BAD_REQUEST),
    InvalidImplementation(HttpStatus.BAD_REQUEST),
    InvalidImplementationType(HttpStatus.NOT_FOUND),
    InvalidSecurityAnswer(HttpStatus.BAD_REQUEST),
    InvalidEntity(HttpStatus.BAD_REQUEST),
    InvalidLogger(HttpStatus.BAD_REQUEST),
    InvalidConnInstance(HttpStatus.BAD_REQUEST),
    InvalidConnIdConf(HttpStatus.BAD_REQUEST),
    InvalidPolicy(HttpStatus.BAD_REQUEST),
    InvalidConf(HttpStatus.BAD_REQUEST),
    InvalidPath(HttpStatus.BAD_REQUEST),
    InvalidProvision(HttpStatus.BAD_REQUEST),
    InvalidOrgUnit(HttpStatus.BAD_REQUEST),
    InvalidReport(HttpStatus.BAD_REQUEST),
    InvalidReportExec(HttpStatus.BAD_REQUEST),
    InvalidRelationship(HttpStatus.BAD_REQUEST),
    InvalidRelationshipType(HttpStatus.BAD_REQUEST),
    InvalidAnyType(HttpStatus.BAD_REQUEST),
    InvalidAnyObject(HttpStatus.BAD_REQUEST),
    InvalidGroup(HttpStatus.BAD_REQUEST),
    InvalidSchemaDefinition(HttpStatus.BAD_REQUEST),
    InvalidSearchParameters(HttpStatus.BAD_REQUEST),
    InvalidPageOrSize(HttpStatus.BAD_REQUEST),
    InvalidPropagationTaskExecReport(HttpStatus.BAD_REQUEST),
    InvalidPlainSchema(HttpStatus.BAD_REQUEST),
    InvalidDerSchema(HttpStatus.BAD_REQUEST),
    InvalidVirSchema(HttpStatus.BAD_REQUEST),
    InvalidMapping(HttpStatus.BAD_REQUEST),
    InvalidMembership(HttpStatus.BAD_REQUEST),
    InvalidRealm(HttpStatus.BAD_REQUEST),
    InvalidDynRealm(HttpStatus.BAD_REQUEST),
    InvalidRole(HttpStatus.BAD_REQUEST),
    InvalidUser(HttpStatus.BAD_REQUEST),
    InvalidExternalResource(HttpStatus.BAD_REQUEST),
    InvalidPullTask(HttpStatus.BAD_REQUEST),
    InvalidRequest(HttpStatus.BAD_REQUEST),
    InvalidValues(HttpStatus.BAD_REQUEST),
    NotFound(HttpStatus.NOT_FOUND),
    RequiredValuesMissing(HttpStatus.BAD_REQUEST),
    RESTValidation(HttpStatus.BAD_REQUEST),
    GroupOwnership(HttpStatus.BAD_REQUEST),
    InUse(HttpStatus.BAD_REQUEST),
    Scheduling(HttpStatus.BAD_REQUEST),
    DelegatedAdministration(HttpStatus.FORBIDDEN),
    Reconciliation(HttpStatus.BAD_REQUEST),
    Unknown(HttpStatus.BAD_REQUEST),
    Workflow(HttpStatus.BAD_REQUEST);

    private final HttpStatus responseStatus;

    ClientExceptionType(final HttpStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public static ClientExceptionType fromHeaderValue(final String exceptionTypeHeaderValue) {
        ClientExceptionType result = null;
        for (ClientExceptionType type : values()) {
            if (exceptionTypeHeaderValue.equals(type.name())) {
                result = type;
            }
        }

        if (result == null) {
            throw new IllegalArgumentException("Unexpected header value: " + exceptionTypeHeaderValue);
        }

        return result;
    }

    public String getInfoHeaderValue(final String value) {
        // HTTP header values cannot contain CR / LF
        return (name() + ':' + value).replaceAll("(\\r|\\n)", " ");
    }

    public HttpStatus getResponseStatus() {
        return responseStatus;
    }
}
