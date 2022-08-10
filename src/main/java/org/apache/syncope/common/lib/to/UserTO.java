package org.apache.syncope.common.lib.to;

import java.io.Serializable;

public class UserTO implements Serializable {

    private static final long serialVersionUID = -179978495291993493L;

    private String key;

    private String username;

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}
