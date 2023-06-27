package portfolio.backend.api.auth.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import portfolio.backend.api.auth.config.auth.PrincipalDetails;
import portfolio.backend.api.auth.config.oauth.provider.*;
import portfolio.backend.api.auth.constant.UserType;
import portfolio.backend.api.auth.entity.SiteUser;
import portfolio.backend.api.auth.repository.UserRepository;

import java.util.Map;

@Service
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    // 소셜 로그인 이후 userRequest 후처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
//        System.out.println("userRequest" + userRequest.getClientRegistration());
//        System.out.println("userRequest" + userRequest.getAccessToken());
//        System.out.println("userRequest" + super.loadUser(userRequest).getAttributes());

        OAuth2User oauth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            oAuth2UserInfo = new KakaoUserInfo(oauth2User.getAttributes());
        }else{
            System.out.println("지원하지 않는 소셜 로그인 플랫");
        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String userId = provider+"_"+providerId;
        String password = passwordEncoder.encode("encrypt");
        String username = oAuth2UserInfo.getEmail();
        UserType userType = UserType.USER;


        SiteUser userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            userEntity = new SiteUser();
            userEntity.setUsername(username);
            userEntity.setPassword(password);
            userEntity.setUserId(userId);
            userEntity.setProvider(provider);
            userEntity.setProviderId(providerId);
            userEntity.setUserType(userType);

            userRepository.save(userEntity);
        }
        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
    }

}
