package portfolio.backend.api.auth.googleUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@Log4j2
@Component
public class GoogleUtils {

    @Value("${mygoogle.authurl}")
    private String authurl;

    @Value("${mygoogle.loginurl}")
    private String loginurl;

    @Value("${mygoogle.clientid}")
    private String clientid;

    @Value("${mygoogle.clientsecret}")
    private String clientsecret;

    @Value("${mygoogle.clientredirect}")
    private String clientredirect;

    @Value("${mygoogle.scope}")
    private String scope;


    public String googleInitUrl() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", clientid);
        params.put("redirect_uri", clientredirect);
        params.put("response_type", "code");
        params.put("scope", getScopeUrl());

        String paramStr = params.entrySet().stream()
                .map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));

        log.info("만들려는 url : "+ loginurl
                + "/o/oauth2/v2/auth"
                + "?"
                + paramStr);

        return loginurl
                + "/o/oauth2/v2/auth"
                + "?"
                + paramStr;
    }


    public String getScopeUrl() {
        return scope.replaceAll(",", "%20");
    }

}