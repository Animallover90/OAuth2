package OAuth2.oauth2.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class KakaoResponseDTO {

    String tokenType;
    String nickName;
    String expiresIn;
    String refreshTokenExpiresIn;
    String scope;
    String accessToken;
    String refreshToken;

}

        // var result = {
        // "access_token": "_CXadHl7yPA_1o1wQaIxewOcMMZSrQLi1kUF0Aopb9UAAAF_2IMaYA",
        //     "token_type": "bearer",
        //     "refresh_token": "ntb8pRkM5zHvCTTg0YHCaljcd2Ppo9JYIl98lgopb9UAAAF_2IMaXw",
        //     "expires_in": 21599,
        //     "scope": "account_email talk_message profile_nickname",
        //     "refresh_token_expires_in": 5183999
        // }

// {
//     "id": 2178170672,
//     "connected_at": "2022-03-30T07:05:06Z",
//     "properties": {
//     "nickname": "Lee"
// },
//     "kakao_account": {
//     "profile_nickname_needs_agreement": false,
//         "profile": {
//         "nickname": "Lee"
//     },
//     "has_email": true,
//         "email_needs_agreement": false,
//         "is_email_valid": true,
//         "is_email_verified": true,
//         "email": "flskwl123@naver.com"
// }
// }