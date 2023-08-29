package OAuth2.oauth2.naver;

import OAuth2.oauth2.utils.OAuth2Util;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class NaverLoginService {

    private final NaverConfig naverConfig;

    public NaverLoginDTO getUserInfo(String code) throws JsonProcessingException {
        // 인가코드로 AccessCode 받아오기
        String accessToken = getAccessToken(code);

        // AccessCode로 유저 정보 받아오기
        return getUserInfoByToken(accessToken);
    }

    public String getAccessToken(String code) throws JsonProcessingException {
        // HttpHeaders class를 통해 header에 파라미터 추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

        // MultiVauleMap으로 body부분에 파라미터 추가
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
        bodyParams.add("grant_type", "authorization_code");
        bodyParams.add("client_id", naverConfig.getClientId());
        bodyParams.add("client_secret", naverConfig.getClientSecret());
        bodyParams.add("redirect_uri", naverConfig.getRedirectUri());
        bodyParams.add("code", code);

        ResponseEntity<String> response = OAuth2Util.httpGetPost(HttpMethod.POST, httpHeaders, bodyParams,
                naverConfig.getNaverOauth2AuthUrl(), String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)

        try {
            return objectMapper.readValue(response.getBody(), new TypeReference<NaverResponseDTO>() {
            }).getAccessToken();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public NaverLoginDTO getUserInfoByToken(String token) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        ResponseEntity<String> response = OAuth2Util.httpGetPost(HttpMethod.GET, httpHeaders, new LinkedMultiValueMap<>(),
                naverConfig.getNaverAuthUrl(), String.class);

        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(response.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)

        try {
            return objectMapper.readValue(jsonObject.get("response").toString(), new TypeReference<NaverLoginDTO>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
