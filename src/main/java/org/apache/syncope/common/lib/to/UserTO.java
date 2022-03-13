package org.apache.syncope.common.lib.to;

import java.io.Serializable;

public class UserTO implements Serializable {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}
