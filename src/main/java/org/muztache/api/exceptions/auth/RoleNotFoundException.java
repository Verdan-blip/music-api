package org.muztache.api.exceptions.auth;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String roleName) {
        super(String.format("Role with name %s not found", roleName));
    }
}
