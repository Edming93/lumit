package com.lumit.shop.common.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.repository.UserRepository;
import com.lumit.shop.common.security.CustomAuthenticationProvider;
import com.lumit.shop.common.security.UserAuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoLoginServiceImpl implements KakaoLoginService {
    @Value("${kakao.api_key}")
    private String kakaoApiKey;
    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    private UserDetailsService uds;

    @Autowired
    private UserRepository userRepository;

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;

    @Autowired
    public KakaoLoginServiceImpl(CustomAuthenticationProvider customAuthenticationProvider, UserAuthenticationSuccessHandler userAuthenticationSuccessHandler) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.userAuthenticationSuccessHandler = userAuthenticationSuccessHandler;
    }


    public User selectUserByKakaoId(Map<String, Object> kakaoUserInfo) {
        User user = userRepository.selectUserByKakaoId((String) kakaoUserInfo.get("kakao_id"));

        return user;
    }

    // 1. 인가 코드 받기 (@RequestParam String code)
    public String getKaKaoCheck(String code, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 2. 토큰 받기
        String accessToken = this.getAccessToken(code);

        // 3. 사용자 정보 받기
        Map<String, Object> kakaoUserInfo = this.getUserInfo(accessToken);
        if (kakaoUserInfo != null) {
            User user = this.selectUserByKakaoId(kakaoUserInfo);

            if (user == null) {
                session.setAttribute("social_id", "KAKAO_" + (String) kakaoUserInfo.get("kakao_id"));
                return "redirect:/member/createUser"; // 회원가입 페이지로 리디렉션
            } else {

                Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());
                System.out.println("요기를 탑니다.");
                try {
                    // 인증 객체 생성
                    Authentication authenticated = customAuthenticationProvider.kakaoAuthenticate(authentication);

                    // SecurityContext 생성 및 인증 객체 설정
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(authenticated);

                    // SecurityContext를 세션에 저장
                    HttpSessionSecurityContextRepository secRepo = new HttpSessionSecurityContextRepository();
                    secRepo.saveContext(context, request, response);

                    // 성공시 핸들러 호출
                    userAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authenticated);
                    return null;
                } catch (AuthenticationException e) {
                    // 인증 실패 시 처리 로직
                    throw new RuntimeException("Authentication failed", e);
                } catch (ServletException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            System.out.println("카카오 토큰 정보를 받아오지 못했습니다.");
        }
        return "/login";
    }

    // 토큰 받기
    public String getAccessToken(String code) {
        String accessToken = "";
        String refreshToken = "";
        String reqUrl = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //필수 헤더 세팅
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setDoOutput(true); //OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();

            //필수 쿼리 파라미터 세팅
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=").append(kakaoApiKey);
            sb.append("&redirect_uri=").append(kakaoRedirectUri);
            sb.append("&code=").append(code);

            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            // log.info("[KakaoApi.getAccessToken] responseCode = {}", responseCode);
            System.out.println(String.format("[KakaoApi.getAccessToken] responseCode = %s", responseCode));
            BufferedReader br;
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line = "";
            StringBuilder responseSb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseSb.append(line);
            }
            String result = responseSb.toString();
            // log.info("responseBody = {}", result);
            System.out.println(String.format("responseBody = %s", result));

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    // 사용자 정보 요청
    public HashMap<String, Object> getUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqUrl = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            int responseCode = conn.getResponseCode();
            // log.info("[KakaoApi.getUserInfo] responseCode : {}",  responseCode);

            BufferedReader br;
            if (responseCode >= 200 && responseCode <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line = "";
            StringBuilder responseSb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseSb.append(line);
            }
            String result = responseSb.toString();
            // log.info("responseBody = {}", result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String kakaoId = element.getAsJsonObject().get("id").getAsString();
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakaoAccount.has("email") ? kakaoAccount.get("email").getAsString() : "No email";

            userInfo.put("kakao_id", kakaoId);
            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    // 로그아웃
    public void kakaoLogout(String accessToken) {
        String reqUrl = "https://kapi.kakao.com/v1/user/logout";

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            // log.info("[KakaoApi.kakaoLogout] responseCode : {}",  responseCode);

            BufferedReader br;
            if (responseCode >= 200 && responseCode <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line = "";
            StringBuilder responseSb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseSb.append(line);
            }
            String result = responseSb.toString();
            // log.info("kakao logout - responseBody = {}", result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

