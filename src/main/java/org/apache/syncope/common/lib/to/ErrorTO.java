package org.apache.syncope.common.lib.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.syncope.common.lib.types.ClientExceptionType;

public class ErrorTO implements Serializable {

    private static final long serialVersionUID = 2435764161719225927L;

    private int status;

    private ClientExceptionType type;

    private final List<String> elements = new ArrayList<>();

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public ClientExceptionType getType() {
        return type;
    }

    public void setType(final ClientExceptionType type) {
        this.type = type;
    }

    public List<String> getElements() {
        return elements;
    }
}
