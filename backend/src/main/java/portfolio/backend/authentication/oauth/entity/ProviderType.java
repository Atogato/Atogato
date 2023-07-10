package portfolio.backend.authentication.oauth.entity;

import lombok.Getter;

@Getter
public enum ProviderType {
    GOOGLE,
    FACEBOOK,
    NAVER,
    KAKAO,
    LOCAL;
}
