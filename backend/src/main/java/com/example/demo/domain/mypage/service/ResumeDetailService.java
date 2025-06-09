package com.example.demo.domain.mypage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.demo.domain.mypage.dto.response.ResumeDetailResponseDTO;
import com.example.demo.domain.mypage.repository.ResumeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeDetailService {

    private final ResumeDetailRepository repository;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public ResumeDetailResponseDTO getResumeDetail(Long resumeSq) {
        ResumeDetailResponseDTO resume = repository.getResumeBasic(resumeSq);
        if (resume == null) {
            return null;
        }

        // 주소
        resume.setAddress(repository.getAddress(resumeSq));

        // 학력
        List<ResumeDetailResponseDTO.EducationDTO> educationList = repository.getEducationList(resumeSq);
        for (ResumeDetailResponseDTO.EducationDTO edu : educationList) {
            String codeNm = repository.findCommonCodeName((long) edu.getEducationStatusCd());
            edu.setEducationStatusNm(codeNm);
        }
        resume.setEducationList(educationList);

        // 경력
        resume.setCareerList(repository.getCareerList(resumeSq));

        // 프로젝트 + 기술태그
        List<ResumeDetailResponseDTO.ProjectDTO> projects = repository.getProjectList(resumeSq);
        for (ResumeDetailResponseDTO.ProjectDTO project : projects) {
            List<String> skillTags = repository.getSkillTagNamesByProjectHistorySq(project.getProjectHistorySq());
            project.setSkillTags(skillTags);
            project.setProjectHistoryTypeNm(repository.findCommonCodeName((long) project.getProjectHistoryTypeCd()));
            project.setProjectHistoryJobPositionTypeNm(
                    repository.findCommonCodeName((long) project.getProjectHistoryJobPositionTypeCd()));
        }
        resume.setProjectList(projects);

        // 전체 기술 태그 리스트
        List<String> skillTagList = repository.getSkillTagNamesByResumeSq(resumeSq);
        resume.setSkillTagList(skillTagList);

        // 자격증
        resume.setCertificationList(repository.getCertificationList(resumeSq));

        // 교육 이력
        resume.setTrainingList(repository.getTrainingList(resumeSq));

        // 첨부파일 리스트 + S3 URL 생성
        List<java.util.Map<String, Object>> attachments = repository.getAttachmentList(resumeSq);

        List<ResumeDetailResponseDTO.AttachmentDTO> attachmentDTOs = attachments.stream()
                .map(map -> {
                    ResumeDetailResponseDTO.AttachmentDTO dto = new ResumeDetailResponseDTO.AttachmentDTO();
                    dto.setAttachmentOriginFileNm((String) map.get("file_original_nm"));

                    String fileSaveName = (String) map.get("file_save_nm");
                    // amazonS3.getUrl(bucket, key)로 URL 생성
                    String s3Url = amazonS3.getUrl(bucket, fileSaveName).toString();
                    dto.setAttachmentFileUrl(s3Url);

                    return dto;
                }).collect(Collectors.toList());

        resume.setAttachmentList(attachmentDTOs);

        return resume;
    }
}
