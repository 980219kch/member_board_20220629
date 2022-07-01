package com.its.memberboard.entity;

import com.its.memberboard.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "comment_table")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;


    @Column
    private String commentWriter;

    @Column
    private String commentContents;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime commentCreatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private BoardEntity boardEntity;


    public static CommentEntity toSaveEntity(CommentDTO commentDTO, MemberEntity memberEntity, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(memberEntity.getMemberEmail());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setMemberEntity(memberEntity);
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }
}
