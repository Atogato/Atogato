package portfolio.backend.authentication.api.controller.auth;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.authentication.api.entity.auth.AuthReqModel;
import portfolio.backend.authentication.api.entity.user.UserRefreshToken;
import portfolio.backend.authentication.api.repository.user.UserRefreshTokenRepository;
import portfolio.backend.authentication.common.ApiResponse;
import portfolio.backend.authentication.config.properties.AppProperties;
import portfolio.backend.authentication.oauth.entity.RoleType;
import portfolio.backend.authentication.oauth.entity.UserPrincipal;
import portfolio.backend.authentication.oauth.exception.TokenExpiredException;
import portfolio.backend.authentication.oauth.token.AuthToken;
import portfolio.backend.authentication.oauth.token.AuthTokenProvider;
import portfolio.backend.authentication.utils.CookieUtil;
import portfolio.backend.authentication.utils.HeaderUtil;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Api(tags = {"User Refresh Token"})
public class AuthController {

    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";

    @ApiIgnore
    @PostMapping("/login")
    public ApiResponse login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody AuthReqModel authReqModel
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authReqModel.getId(),
                        authReqModel.getPassword()
                )
        );
        System.out.println("어도" + authentication);


        String userId = authReqModel.getId();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                userId,
                ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );
        System.out.println("날짜" + accessToken);

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        System.out.println("토큰만료" + refreshTokenExpiry);

        // userId refresh token 으로 DB 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);
        if (userRefreshToken == null) {
            // 없는 경우 새로 등록
            userRefreshToken = new UserRefreshToken(userId, refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        } else {
            // DB에 refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(refreshToken.getToken());
            userRefreshTokenRepository.save(userRefreshToken);
        }

        System.out.println("업데이트" + userRefreshToken);

        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return ApiResponse.success("token", accessToken.getToken());
    }

    @GetMapping("/refresh")
    public ApiResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // access token 확인
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        try {
            if (!authToken.validate()) {
                return ApiResponse.invalidAccessToken();
            }
        } catch (TokenExpiredException e) {
            Claims claims = e.getClaims();
            if (claims == null) {
                return ApiResponse.notExpiredTokenYet();
            }
            String userId = claims.getSubject();
            RoleType roleType = RoleType.of(claims.get("role", String.class));

            Date now = new Date();
            AuthToken newAccessToken = tokenProvider.createAuthToken(
                    userId,
                    roleType.getCode(),
                    new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
            );

            return ApiResponse.success("token", newAccessToken.getToken());
        }
        return ApiResponse.notExpiredTokenYet();
    }
//    @GetMapping("/refresh")
//    public ApiResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
//        // access token 확인
//        String accessToken = HeaderUtil.getAccessToken(request);
//        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
//        try {
//            if (!authToken.validate()) {
//                return ApiResponse.invalidAccessToken();
//            }
//        } catch (TokenExpiredException e) {
//            Claims claims = e.getClaims();
//            if (claims == null) {
//                return ApiResponse.notExpiredTokenYet();
//            }
//            String userId = claims.getSubject();
//            RoleType roleType = RoleType.of(claims.get("role", String.class));
//
//            String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
//                    .map(Cookie::getValue)
//                    .orElse(null);
//
//            AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);
//
//            System.out.println("리프레쉬" + refreshToken);
//            System.out.println("유저" + userId);
//            System.out.println("토큰" + authRefreshToken);
//
//            if (!authRefreshToken.validate()) {
//                return ApiResponse.invalidRefreshToken();
//            }
//
//            UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken);
//            System.out.println("유저 리프레쉬" + userRefreshToken);
//
//            if (userRefreshToken == null) {
//                return ApiResponse.invalidRefreshToken();
//            }
//
//            Date now = new Date();
//            AuthToken newAccessToken = tokenProvider.createAuthToken(
//                    userId,
//                    roleType.getCode(),
//                    new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
//            );
//
//            long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();
////
//            if (validTime <= THREE_DAYS_MSEC) {
//                long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
//
//                authRefreshToken = tokenProvider.createAuthToken(
//                        appProperties.getAuth().getTokenSecret(),
//                        new Date(now.getTime() + refreshTokenExpiry)
//                );
//
//                userRefreshToken.setRefreshToken(authRefreshToken.getToken());
//
//                int cookieMaxAge = (int) refreshTokenExpiry / 60;
//                CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
//                CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
//            }
//
//            return ApiResponse.success("token", newAccessToken.getToken());
//        }
//        return ApiResponse.notExpiredTokenYet();
//    }
}

