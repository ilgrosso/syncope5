package org.apache.syncope.core.persistence.jpa.entity.user;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Cacheable;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.apache.syncope.common.lib.types.CipherAlgorithm;
import org.apache.syncope.core.persistence.api.entity.user.User;
import org.apache.syncope.core.persistence.jpa.entity.AbstractAny;
import org.apache.syncope.core.spring.security.Encryptor;
import org.apache.syncope.core.spring.security.SecureRandomUtils;

@Entity
@Table(name = JPAUser.TABLE)
@Cacheable
public class JPAUser extends AbstractAny implements User {

    private static final long serialVersionUID = -3905046855521446823L;

    public static final String TABLE = "SyncopeUser";

    private static final Encryptor ENCRYPTOR = Encryptor.getInstance();

    @Column(nullable = true)
    private String password;

    @Transient
    private String clearPassword;

    @Column(nullable = true)
    private String status;

    @Lob
    private String token;

    private OffsetDateTime tokenExpireTime;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private CipherAlgorithm cipherAlgorithm;

    @ElementCollection
    @Column(name = "passwordHistoryValue")
    @CollectionTable(name = "SyncopeUser_passwordHistory", joinColumns =
            @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<String> passwordHistory = new ArrayList<>();

    /**
     * Subsequent failed logins.
     */
    @Column(nullable = true)
    private Integer failedLogins;

    /**
     * Username/Login.
     */
    @Column(unique = true)
    @NotNull(message = "Blank username")
    private String username;

    /**
     * Last successful login date.
     */
    private OffsetDateTime lastLoginDate;

    /**
     * Change password date.
     */
    private OffsetDateTime changePwdDate;

    private Boolean suspended = false;

    private Boolean mustChangePassword = false;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getClearPassword() {
        return clearPassword;
    }

    public void setClearPassword(final String clearPassword) {
        this.clearPassword = clearPassword;
    }

    @Override
    public void removeClearPassword() {
        setClearPassword(null);
    }

    @Override
    public void setEncodedPassword(final String password, final CipherAlgorithm cipherAlgorithm) {
        this.clearPassword = null;

        this.password = password;
        this.cipherAlgorithm = cipherAlgorithm;
        setMustChangePassword(false);
    }

    @Override
    public void setPassword(final String password) {
        this.clearPassword = password;

        try {
            this.password = ENCRYPTOR.encode(
                    password, Optional.ofNullable(cipherAlgorithm).orElse(CipherAlgorithm.AES));
            setMustChangePassword(false);
        } catch (Exception e) {
            LOG.error("Could not encode password", e);
            this.password = null;
        }
    }

    @Override
    public CipherAlgorithm getCipherAlgorithm() {
        return cipherAlgorithm;
    }

    @Override
    public void setCipherAlgorithm(final CipherAlgorithm cipherAlgorithm) {
        if (this.cipherAlgorithm == null || cipherAlgorithm == null) {
            this.cipherAlgorithm = cipherAlgorithm;
        } else {
            throw new IllegalArgumentException("Cannot override existing cipher algorithm");
        }
    }

    @Override
    public boolean canDecodeSecrets() {
        return this.cipherAlgorithm != null && this.cipherAlgorithm.isInvertible();
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(final String status) {
        this.status = status;
    }

    @Override
    public void generateToken(final int tokenLength, final int tokenExpireTime) {
        this.token = SecureRandomUtils.generateRandomPassword(tokenLength);
        this.tokenExpireTime = OffsetDateTime.now().plusMinutes(tokenExpireTime);
    }

    @Override
    public void removeToken() {
        this.token = null;
        this.tokenExpireTime = null;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public OffsetDateTime getTokenExpireTime() {
        return tokenExpireTime;
    }

    @Override
    public boolean checkToken(final String token) {
        return Optional.ofNullable(this.token).
                map(s -> s.equals(token) && !hasTokenExpired()).
                orElseGet(() -> token == null);
    }

    @Override
    public boolean hasTokenExpired() {
        return Optional.ofNullable(tokenExpireTime).
                filter(expireTime -> expireTime.isBefore(OffsetDateTime.now())).
                isPresent();
    }

    @Override
    public List<String> getPasswordHistory() {
        return passwordHistory;
    }

    @Override
    public OffsetDateTime getChangePwdDate() {
        return changePwdDate;
    }

    @Override
    public void setChangePwdDate(final OffsetDateTime changePwdDate) {
        this.changePwdDate = changePwdDate;
    }

    @Override
    public Integer getFailedLogins() {
        return failedLogins == null ? 0 : failedLogins;
    }

    @Override
    public void setFailedLogins(final Integer failedLogins) {
        this.failedLogins = failedLogins;
    }

    @Override
    public OffsetDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    @Override
    public void setLastLoginDate(final OffsetDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public void setSuspended(final Boolean suspended) {
        this.suspended = suspended;
    }

    @Override
    public Boolean isSuspended() {
        return suspended;
    }

    @Override
    public void setMustChangePassword(final boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }

    @Override
    public boolean isMustChangePassword() {
        return mustChangePassword;
    }
}
