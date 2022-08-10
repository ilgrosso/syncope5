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
import org.apache.syncope.core.persistence.jpa.entity.AbstractAny;
import org.apache.syncope.core.spring.security.Encryptor;
import org.apache.syncope.core.spring.security.SecureRandomUtils;

@Entity
@Table(name = JPAUser.TABLE)
@Cacheable
public class JPAUser extends AbstractAny {

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

    public String getPassword() {
        return password;
    }

    public String getClearPassword() {
        return clearPassword;
    }

    public void setClearPassword(final String clearPassword) {
        this.clearPassword = clearPassword;
    }

    public void removeClearPassword() {
        setClearPassword(null);
    }

    public void setEncodedPassword(final String password, final CipherAlgorithm cipherAlgorithm) {
        this.clearPassword = null;

        this.password = password;
        this.cipherAlgorithm = cipherAlgorithm;
        setMustChangePassword(false);
    }

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

    public CipherAlgorithm getCipherAlgorithm() {
        return cipherAlgorithm;
    }

    public void setCipherAlgorithm(final CipherAlgorithm cipherAlgorithm) {
        if (this.cipherAlgorithm == null || cipherAlgorithm == null) {
            this.cipherAlgorithm = cipherAlgorithm;
        } else {
            throw new IllegalArgumentException("Cannot override existing cipher algorithm");
        }
    }

    public boolean canDecodeSecrets() {
        return this.cipherAlgorithm != null && this.cipherAlgorithm.isInvertible();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void generateToken(final int tokenLength, final int tokenExpireTime) {
        this.token = SecureRandomUtils.generateRandomPassword(tokenLength);
        this.tokenExpireTime = OffsetDateTime.now().plusMinutes(tokenExpireTime);
    }

    public void removeToken() {
        this.token = null;
        this.tokenExpireTime = null;
    }

    public String getToken() {
        return token;
    }

    public OffsetDateTime getTokenExpireTime() {
        return tokenExpireTime;
    }

    public boolean checkToken(final String token) {
        return Optional.ofNullable(this.token).
                map(s -> s.equals(token) && !hasTokenExpired()).
                orElseGet(() -> token == null);
    }

    public boolean hasTokenExpired() {
        return Optional.ofNullable(tokenExpireTime).
                filter(expireTime -> expireTime.isBefore(OffsetDateTime.now())).
                isPresent();
    }

    public List<String> getPasswordHistory() {
        return passwordHistory;
    }

    public OffsetDateTime getChangePwdDate() {
        return changePwdDate;
    }

    public void setChangePwdDate(final OffsetDateTime changePwdDate) {
        this.changePwdDate = changePwdDate;
    }

    public Integer getFailedLogins() {
        return Optional.ofNullable(failedLogins).orElse(0);
    }

    public void setFailedLogins(final Integer failedLogins) {
        this.failedLogins = failedLogins;
    }

    public OffsetDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(final OffsetDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setSuspended(final Boolean suspended) {
        this.suspended = suspended;
    }

    public Boolean isSuspended() {
        return suspended;
    }

    public void setMustChangePassword(final boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }

    public boolean isMustChangePassword() {
        return mustChangePassword;
    }
}
