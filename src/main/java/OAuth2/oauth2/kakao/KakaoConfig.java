package OAuth2.oauth2.kakao;

// @Value를 사용하기 위해 @Component 추가
// 해당 클래스를 Bean으로 생성해둠
// 추후 사용할 일이 있을 때, Bean 인스턴스를 꺼내 활용

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KakaoConfig {

    @Value("${kakao_client_id}")
    private String clientId;

    @Value("${kakao_redirect_uri}")
    private String redirectUri;

    @Value("${kakao.oauth2.client.authUri}")
    private String kakaoOauth2AuthUrl;

    @Value("${kakao.client.authUri}")
    private String kakaoAuthUrl;

}
