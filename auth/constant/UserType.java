package portfolio.backend.api.auth.constant;

import lombok.Getter;

@Getter
public enum UserType {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserType(String value) {
        this.value = value;
    }

    private String value;

}
