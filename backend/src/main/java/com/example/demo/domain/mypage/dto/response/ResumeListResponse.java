package com.example.demo.domain.mypage.dto.response;

public class ResumeListResponse {
    private Long resumeId;          // 이력서 ID
    private String title;           // 이력서 제목
    private String registerDate;    // 생성일자 (yyyy.MM.dd로 포맷된 문자열)
    private boolean isRepresentative; // 대표 여부 (0 또는 1을 boolean으로 변환)

    public ResumeListResponse() {}

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public boolean isRepresentative() {
        return isRepresentative;
    }

    public void setRepresentative(boolean representative) {
        isRepresentative = representative;
    }
}
