package portfolio.backend.api.auth.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import portfolio.backend.api.auth.googleUtils.*;

import java.net.URI;
import java.net.URISyntaxException;


@Log4j2
@RequiredArgsConstructor
@Controller
public class ApiController {

    private final GoogleUtils configUtils;

    @Value("${mygoogle.clientid}")
    private String CLIENT_ID;

    @Value("${mygoogle.clientsecret}")
    private String CLIENT_SECRETS;


    @GetMapping(value = "/oauth/login")
    public ResponseEntity<Object> moveGoogleInitUrl() {
        String authUrl = configUtils.googleInitUrl();
        URI redirectUri = null;

        System.out.println("요청 url : " + authUrl);

        try {
            redirectUri = new URI(authUrl);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirectUri);
            log.info("check login");
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/oauth/login/redirect")
    public String redirectGoogleLogin(
            @RequestParam(value = "code") String authCode,
            Model model
    ) {
        // HTTP 통신을 위해 RestTemplate 활용
        RestTemplate restTemplate = new RestTemplate();
        GoogleLoginRequest requestParams = GoogleLoginRequest.builder()
                .clientId(configUtils.getClientid())
                .clientSecret(configUtils.getClientsecret())
                .code(authCode)
                .redirectUri(configUtils.getClientredirect())
                .grantType("authorization_code")
                .build();

        log.info("login try....");
        log.info(requestParams.getCode());

        try {
            // Http Header 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<GoogleLoginRequest> httpRequestEntity = new HttpEntity<>(requestParams, headers);

            System.out.println(httpRequestEntity.getBody());
            System.out.println(httpRequestEntity.getHeaders());
            System.out.println(configUtils.getAuthurl() + "/token");
            ResponseEntity<String> apiResponseJson = restTemplate.postForEntity(configUtils.getAuthurl() + "/token", httpRequestEntity, String.class);


            System.out.println("크리덴셜 확인");
            System.out.println(apiResponseJson.getBody());


            // ObjectMapper를 통해 String to Object로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
            GoogleLoginResponse googleLoginResponse = objectMapper.readValue(apiResponseJson.getBody(), new TypeReference<GoogleLoginResponse>() {
            });

            // 사용자의 정보는 JWT Token으로 저장되어 있고, Id_Token에 값을 저장한다.
            String jwtToken = googleLoginResponse.getIdToken();

            String accessToken = googleLoginResponse.getAccessToken();

            // JWT Token을 전달해 JWT 저장된 사용자 정보 확인
            String requestUrl = UriComponentsBuilder.fromHttpUrl(configUtils.getAuthurl() + "/tokeninfo").queryParam("id_token", jwtToken).toUriString();

            String resultJson = restTemplate.getForObject(requestUrl, String.class);

            if (resultJson != null) {
                GoogleLoginDto userInfoDto = objectMapper.readValue(resultJson, new TypeReference<GoogleLoginDto>() {
                });

                // 구글 로그인 DTO 반환
                log.info(userInfoDto.toString());

                model.addAttribute("result", userInfoDto);

                return "users/ReturnPage";
            } else {
                throw new Exception("Google OAuth failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "home";
    }


}