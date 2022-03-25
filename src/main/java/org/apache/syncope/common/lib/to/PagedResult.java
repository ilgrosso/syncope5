package org.apache.syncope.common.lib.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PagedResult<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<T> result = new ArrayList<>();

    public List<T> getResult() {
        return result;
    }
}
