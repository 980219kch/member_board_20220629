package com.its.memberboard.service;

import com.its.memberboard.dto.BoardDTO;
import com.its.memberboard.dto.CommentDTO;
import com.its.memberboard.entity.BoardEntity;
import com.its.memberboard.entity.CommentEntity;
import com.its.memberboard.entity.MemberEntity;
import com.its.memberboard.repository.BoardRepository;
import com.its.memberboard.repository.CommentRepository;
import com.its.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    public void save(CommentDTO commentDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(commentDTO.getCommentWriter());
        MemberEntity memberEntity = optionalMemberEntity.get();
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        BoardEntity boardEntity = optionalBoardEntity.get();
        commentRepository.save(CommentEntity.toSaveEntity(commentDTO, memberEntity, boardEntity));

    }

    public List<CommentDTO> findAll(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        BoardEntity boardEntity = optionalBoardEntity.get();
        List<CommentEntity> commentEntityList = boardEntity.getCommentEntityList();
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(CommentEntity commentEntity: commentEntityList) {
            commentDTOList.add(CommentDTO.toCommentDTO(commentEntity));
        }
        return commentDTOList;
    }
}
