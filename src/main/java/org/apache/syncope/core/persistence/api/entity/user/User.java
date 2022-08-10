package org.apache.syncope.core.persistence.api.entity.user;

import java.time.OffsetDateTime;
import java.util.List;
import org.apache.syncope.common.lib.types.CipherAlgorithm;
import org.apache.syncope.core.persistence.api.entity.Any;

public interface User extends Any {

    String getUsername();

    void setUsername(String username);

    CipherAlgorithm getCipherAlgorithm();

    boolean canDecodeSecrets();

    String getPassword();

    void setEncodedPassword(String password, CipherAlgorithm cipherAlgoritm);

    void setPassword(String password);

    void setCipherAlgorithm(CipherAlgorithm cipherAlgorithm);

    Boolean isSuspended();

    void setSuspended(Boolean suspended);

    String getToken();

    OffsetDateTime getTokenExpireTime();

    void generateToken(int tokenLength, int tokenExpireTime);

    void removeToken();

    boolean checkToken(String token);

    boolean hasTokenExpired();

    String getClearPassword();

    void removeClearPassword();

    OffsetDateTime getChangePwdDate();

    void setChangePwdDate(OffsetDateTime changePwdDate);

    List<String> getPasswordHistory();

    Integer getFailedLogins();

    void setFailedLogins(Integer failedLogins);

    OffsetDateTime getLastLoginDate();

    void setLastLoginDate(OffsetDateTime lastLoginDate);

    boolean isMustChangePassword();

    void setMustChangePassword(boolean mustChangePassword);
}
