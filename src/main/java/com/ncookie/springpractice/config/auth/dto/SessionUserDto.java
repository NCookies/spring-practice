package com.ncookie.springpractice.config.auth.dto;

import com.ncookie.springpractice.user.entity.UserEntity;
import lombok.Getter;

/*
 * 세션에 저장하기 위해 UserEntity 클래스를 세션에 저장하려고 하면 UserEntity 클래스에 직렬화를 구현하지 않았다는 에러가 발생함
 * Entity 클래스는 직렬화 코드를 넣지 않는게 좋음. 엔티티 클래스에는 언제 다른 엔티티와 관계가 형성될지 모르기 때문
 * @OneToMany, @ManyToMany 등 자식 엔티티를 갖고 있다면 직렬화 대상에 자식들까지 포함되니 성능 이슈, 부수 효과가 발생할 확률이 높음
 * 그래서 직렬화 기능을 가진 세션 Dto를 하나 추가로 만든 것이 더 좋은 방법이다.
 */
@Getter
public class SessionUserDto {
    private final String name;
    private final String email;
    private final String picture;

    public SessionUserDto(UserEntity user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
