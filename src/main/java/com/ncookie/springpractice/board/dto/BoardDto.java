package com.ncookie.springpractice.board.dto;

import com.ncookie.springpractice.board.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;


/*
 * dto는 Controller <-> Service <-> Repository 간에 필요한 데이터를 캡슐화한 데이터 전달 객체
 * Service에서 Repository 메서드를 호출할 때, Entity를 전달한 이유는 JpaRepository에 정의된 함수들은 미리 정의되어 있기 때문
 * 그래서 Entity를 전달할 수 밖에 없었는데, 요점은 각 계층에서 필요한 객체전달은 Entity 객체가 아닌 dto 객체를 통해 주고받는 것이 좋다는 것
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class BoardDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    // dto에서 필요한 부분을 빌더패턴을 통해 entity로 만듦
    public BoardEntity toEntity() {
        return BoardEntity.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .build();
    }

    public static BoardDto from(BoardEntity boardEntity) {
        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }
//    @Builder
//    public BoardDto(Long id, String writer, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
//        this.id = id;
//        this.writer = writer;
//        this.title = title;
//        this.content = content;
//        this.createdDate = createdDate;
//        this.modifiedDate = modifiedDate;
//    }
}
