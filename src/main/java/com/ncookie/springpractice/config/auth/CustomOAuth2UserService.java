package com.ncookie.springpractice.config.auth;

import com.ncookie.springpractice.config.auth.dto.OAuthAttributesDto;
import com.ncookie.springpractice.config.auth.dto.SessionUserDto;
import com.ncookie.springpractice.user.entity.UserEntity;
import com.ncookie.springpractice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

// 구글 로그인 이후 가져온 사용자의 정보(email, name, picture 등) 들을 기반으로 가입 및 정보수정, 세션 저장 등의 기능을 지원
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /*
         * 현재 로그인 진행 중인 서비스를 구분하는 코드
         * 지금은 구글만 사용하는 값이지만, 이후 네이버 로그인 연동시에 네이버 로그인인지, 구글 로그인인지 구분하기 위해 사용
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        /*
         * OAuth2 로그인 진행 시 키가 되는 필드값을 이야기함 Primary Key와 같은 의미
         * 구글의 경우 기본적으로 코드를 지원하지만, 네이버 카카오 등은 기본 지원하지 않음. 구글의 기본 코드는 "sub"임
         */
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributesDto attributes = OAuthAttributesDto
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        UserEntity user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUserDto(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    // 이미 가입되있던 회원이라면 저장 대신 업데이트를 해줌
    private UserEntity saveOrUpdate(OAuthAttributesDto attributes) {
        UserEntity user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
