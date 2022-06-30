package com.its.memberboard.service;

import com.its.memberboard.dto.MemberDTO;
import com.its.memberboard.entity.MemberEntity;
import com.its.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public Long save(MemberDTO memberDTO) throws IOException {
        MultipartFile memberProfile = memberDTO.getMemberProfile();
        String memberProfileName = memberProfile.getOriginalFilename();
        memberProfileName = System.currentTimeMillis() + "-" + memberProfileName;
        String savePath = "C:\\springboot_memberImg\\" + memberProfileName;
        if(!memberProfile.isEmpty()) {
            memberProfile.transferTo(new File(savePath));
        }
        memberDTO.setMemberProfileName(memberProfileName);
        Long savedId = memberRepository.save(MemberEntity.toSaveEntity(memberDTO)).getId();
        return savedId;
    }

    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if(optionalMemberEntity.isEmpty()) {
            return "ok";
        } else {
            return "no";
        }
    }

    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(optionalMemberEntity.isPresent()) {
            MemberEntity loginEntity = optionalMemberEntity.get();
            if(loginEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                return MemberDTO.toMemberDTO(loginEntity);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public MemberDTO findByMemberEmail(String email) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(email);
        MemberEntity memberEntity = optionalMemberEntity.get();
        return MemberDTO.toMemberDTO(memberEntity);
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateEntity(memberDTO));
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for(MemberEntity memberEntity: memberEntityList) {
            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }
}
