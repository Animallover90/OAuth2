package OAuth2.oauth2;

import OAuth2.oauth2.google.GoogleLoginDTO;
import OAuth2.oauth2.google.GoogleOAuth2Service;
import OAuth2.oauth2.kakao.KakaoLoginDTO;
import OAuth2.oauth2.kakao.KakaoLoginService;
import OAuth2.oauth2.naver.NaverLoginDTO;
import OAuth2.oauth2.naver.NaverLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OAuth2Controller {

    private final GoogleOAuth2Service googleOAuth2Service;
    private final KakaoLoginService kakaoLoginService;
    private final NaverLoginService naverLoginService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/login/google/redirect")
    public String googleRedirect(HttpServletRequest request, HttpServletResponse response) {
        try {
            GoogleLoginDTO googleUserInfo = googleOAuth2Service.getUserInfo(request);
            log.info("GOOGLE={}", googleUserInfo);

            loginProcess(response, googleUserInfo.getId(), LoginProvider.GOOGLE);

            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "error/500";
        }
    }

    @GetMapping("/login/kakao/redirect")
    public String kakaoRedirect(HttpServletResponse response, String code) {
        try {
            KakaoLoginDTO kakaoUserInfo = kakaoLoginService.getUserInfo(code);
            log.info("KAKAO={}", kakaoUserInfo);

            loginProcess(response, kakaoUserInfo.getId(), LoginProvider.KAKAO);

            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "error/500";
        }
    }

    @GetMapping("/login/naver/redirect")
    public String naverRedirect(HttpServletResponse response, String code) {
        try {
            NaverLoginDTO naverUserInfo = naverLoginService.getUserInfo(code);
            log.info("NAVER={}", naverUserInfo);

            loginProcess(response, naverUserInfo.getId(), LoginProvider.NAVER);

            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "error/500";
        }
    }

    @GetMapping("/member/logout")
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("쿠키, 세션 삭제");
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "error/500";
        }
    }

    private void loginProcess(HttpServletResponse response, String id, LoginProvider provider) {
        log.info("로그인 또는 신규 가입 프로세스와 세션 저장 또는 쿠키 전송");
    }
}
