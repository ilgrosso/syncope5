package org.apache.syncope.common.lib.to;

import java.io.Serializable;

public class UserCR implements Serializable {

    private static final long serialVersionUID = 2965220990115086067L;

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
