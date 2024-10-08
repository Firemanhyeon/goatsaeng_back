package com.example.gotsaeng_back.domain.auth.oauth2.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class OAuthAttributes {
    private Map<String , Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String provider;


    public static OAuthAttributes of(String registrationId , String userNameAttributeName , Map<String , Object> attributes){
        if("naver".equals(registrationId)){
            return ofNaver("id",attributes);
        }else if("kakao".equals(registrationId)){
            return ofKakao("id",attributes);
        }
        return ofGoogle(userNameAttributeName,attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String , Object> attributes){
        return OAuthAttributes.builder()
                .name(UUID.randomUUID().toString())
                .email((String)attributes.get("email"))
                .provider("Google")
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName , Map<String , Object> attributes){
        Map<String,Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name(UUID.randomUUID().toString())
                .email((String)response.get("email"))
                .provider("Naver")
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName ,Map<String , Object> attributes ){

        Map<String,Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String,Object> account = (Map<String, Object>) response.get("profile");

        return OAuthAttributes.builder()
                .name(UUID.randomUUID().toString())
                .email((String) response.get("email"))
                .provider("Kakao")
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

}