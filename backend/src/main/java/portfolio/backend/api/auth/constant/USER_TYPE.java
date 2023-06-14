package portfolio.backend.api.auth.constant;

import lombok.Getter;

@Getter
public enum USER_TYPE {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    USER_TYPE(String value) {
        this.value = value;
    }

    private String value;

}
