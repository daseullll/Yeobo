package com.jagi.yeobo.service;

import com.jagi.yeobo.domain.User;
import com.jagi.yeobo.domain.repository.UserRepository2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>  {

    private final UserRepository2 userRepository;

    private final UserService userService;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // DefaultOAuth2UserService 객체를 성공 정보를 바탕으로 만듦
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        // 생성된 Service 객체로 부터 User 정보를 받음
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        // 받은 User 정보로 부터 user 정보를 받는다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        System.out.println("-------------loadUser---------" + registrationId);
        System.out.println("-------------loadUser---------" + userNameAttributeName);

        // Success Handler가 사용할 수 있도록 등록
        OAuth2Attribute oAuth2Attribute =
                OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        //db 저장
        saveOrUpdate(oAuth2Attribute);

        var memberAttribute = oAuth2Attribute.covertToMap();
        // DefaultOAuth2User 객체를 성공 정보를 바탕으로 만듦
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("USER")),
                memberAttribute, "email");
    }

    private void saveOrUpdate(OAuth2Attribute attribute) {
        if(!userRepository.existsByEmail(attribute.getEmail())){
            UserDto2 user = UserDto2.builder().email(attribute.getEmail()).name(attribute.getName()).build();
            System.out.println("input db");
            userService.joinSocial(user);
        }
    }
}
