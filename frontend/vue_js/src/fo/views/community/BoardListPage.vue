<template>
  <section>
    <CommonPageHeader
      title=""
      strongText="일반 게시판"
      :breadcrumbs="[{ text: 'Home', link: '/' }, { text: '커뮤니티' }]"
    />
    <div class="container py-5 mt-3">
      <!-- 검색창 및 필터 영역 -->
      <div
        class="row align-items-center justify-content-between py-3 border-bottom mb-3"
      >
        <div class="col-md-6">
          <form class="d-flex">
            <select class="form-select w-auto me-2">
              <option selected>전체</option>
              <option>제목</option>
              <option>내용</option>
            </select>
            <input
              class="form-control w-auto me-2"
              type="search"
              placeholder="검색어 입력"
            />
            <button class="btn btn-primary px-3" type="submit">검색</button>
          </form>
        </div>
        <div class="col-md-6 text-end">
          <select class="form-select w-auto d-inline-block">
            <option selected>최신순</option>
            <option>오래된순</option>
            <option>조회순</option>
            <option>추천순</option>
          </select>
        </div>
      </div>
      <!-- 게시판 리스트 -->
      <div class="row">
        <div class="col">
          <BoardTable
            :boardList="
              boardList.slice(
                (currentPage - 1) * viewBoxCnt,
                currentPage * viewBoxCnt,
              )
            "
            :isQna="false"
          />
          <!-- 등록 버튼 -->
          <!-- [추가] 클릭 시 Qna 게시글 작성 오픈 -->
          <div class="d-flex justify-content-end mb-3">
            <a href="/board/register" class="btn btn-primary px-4">등록</a>
          </div>
          <!-- 페이지네이션: 우측 하단 정렬 -->
          <CommonPagination
            :currentPage="currentPage"
            :totalPages="totalPages"
            @update:currentPage="currentPage = $event"
          />
        </div>
      </div>
    </div>
  </section>
</template>
<script setup>
import BoardTable from '@/fo/components/community/BoardTable.vue'
import CommonPagination from '@/fo/components/common/CommonPagination.vue'
import { ref } from 'vue'
import CommonPageHeader from '@/fo/components/common/CommonPageHeader.vue'

const boardList = [
  {
    board_sq: 1,
    board_ttl: '이력서 피드백 부탁드립니다! (프론트엔드 신입)',
    normal_tags: [
      'HTML',
      '이력서',
      '신입',
      'HTML',
      '이력서',
      '신입',
      'HTML',
      '이력서',
      '신입',
      'HTML',
      '이력서',
      '신입',
      'HTML',
      '이력서',
      '신입',
      'HTML',
      '이력서',
      '신입',
      'HTML',
      '이력서',
      '신입',
    ],
    user_nm: 'jun_dev',
    created_at: '2025-04-17',
    view_cnt: 88,
    comment_cnt: 5,
    recommend_cnt: 14,
  },
  {
    board_sq: 2,
    board_ttl: 'React 사이드 프로젝트 팀원 모집합니다 (기획 완료)',
    normal_tags: ['React', '팀모집', '사이드'],
    user_nm: 'codingmate',
    created_at: '2025-04-16',
    view_cnt: 142,
    comment_cnt: 7,
    recommend_cnt: 11,
  },
  {
    board_sq: 3,
    board_ttl: '스터디 같이 하실 분! (백엔드 Java 중심)',
    normal_tags: ['Java', '스터디', '백엔드'],
    user_nm: 'sunny_backend',
    created_at: '2025-04-14',
    view_cnt: 76,
    comment_cnt: 2,
    recommend_cnt: 5,
  },
  {
    board_sq: 4,
    board_ttl: '포트폴리오용 프로젝트 피드백 부탁드려요!',
    normal_tags: ['포트폴리오', 'UI/UX', '신입'],
    user_nm: 'soyoung',
    created_at: '2025-04-13',
    view_cnt: 103,
    comment_cnt: 3,
    recommend_cnt: 9,
  },
  {
    board_sq: 5,
    board_ttl: 'CS 기반 면접 스터디 멤버 구합니다 (온라인)',
    normal_tags: ['CS', '스터디', '온라인'],
    user_nm: 'study_cs',
    created_at: '2025-04-12',
    view_cnt: 192,
    comment_cnt: 6,
    recommend_cnt: 17,
  },
  {
    board_sq: 1,
    board_ttl: '이력서 피드백 부탁드립니다! (프론트엔드 신입)',
    normal_tags: ['HTML', '이력서', '신입'],
    user_nm: 'jun_dev',
    created_at: '2025-04-17',
    view_cnt: 88,
    comment_cnt: 5,
    recommend_cnt: 14,
  },
  {
    board_sq: 2,
    board_ttl: 'React 사이드 프로젝트 팀원 모집합니다 (기획 완료)',
    normal_tags: ['React', '팀모집', '사이드'],
    user_nm: 'codingmate',
    created_at: '2025-04-16',
    view_cnt: 142,
    comment_cnt: 7,
    recommend_cnt: 11,
  },
  {
    board_sq: 3,
    board_ttl: '스터디 같이 하실 분! (백엔드 Java 중심)',
    normal_tags: ['Java', '스터디', '백엔드'],
    user_nm: 'sunny_backend',
    created_at: '2025-04-14',
    view_cnt: 76,
    comment_cnt: 2,
    recommend_cnt: 5,
  },
  {
    board_sq: 4,
    board_ttl: '포트폴리오용 프로젝트 피드백 부탁드려요!',
    normal_tags: ['포트폴리오', 'UI/UX', '신입'],
    user_nm: 'soyoung',
    created_at: '2025-04-13',
    view_cnt: 103,
    comment_cnt: 3,
    recommend_cnt: 9,
  },
  {
    board_sq: 5,
    board_ttl: 'CS 기반 면접 스터디 멤버 구합니다 (온라인)',
    normal_tags: ['CS', '스터디', '온라인'],
    user_nm: 'study_cs',
    created_at: '2025-04-12',
    view_cnt: 192,
    comment_cnt: 6,
    recommend_cnt: 17,
  },
  {
    board_sq: 1,
    board_ttl: '이력서 피드백 부탁드립니다! (프론트엔드 신입)',
    normal_tags: ['HTML', '이력서', '신입'],
    user_nm: 'jun_dev',
    created_at: '2025-04-17',
    view_cnt: 88,
    comment_cnt: 5,
    recommend_cnt: 14,
  },
  {
    board_sq: 2,
    board_ttl: 'React 사이드 프로젝트 팀원 모집합니다 (기획 완료)',
    normal_tags: ['React', '팀모집', '사이드'],
    user_nm: 'codingmate',
    created_at: '2025-04-16',
    view_cnt: 142,
    comment_cnt: 7,
    recommend_cnt: 11,
  },
  {
    board_sq: 3,
    board_ttl: '스터디 같이 하실 분! (백엔드 Java 중심)',
    normal_tags: ['Java', '스터디', '백엔드'],
    user_nm: 'sunny_backend',
    created_at: '2025-04-14',
    view_cnt: 76,
    comment_cnt: 2,
    recommend_cnt: 5,
  },
  {
    board_sq: 4,
    board_ttl: '포트폴리오용 프로젝트 피드백 부탁드려요!',
    normal_tags: ['포트폴리오', 'UI/UX', '신입'],
    user_nm: 'soyoung',
    created_at: '2025-04-13',
    view_cnt: 103,
    comment_cnt: 3,
    recommend_cnt: 9,
  },
  {
    board_sq: 5,
    board_ttl: 'CS 기반 면접 스터디 멤버 구합니다 (온라인)',
    normal_tags: ['CS', '스터디', '온라인'],
    user_nm: 'study_cs',
    created_at: '2025-04-12',
    view_cnt: 192,
    comment_cnt: 6,
    recommend_cnt: 17,
  },
  {
    board_sq: 1,
    board_ttl: '이력서 피드백 부탁드립니다! (프론트엔드 신입)',
    normal_tags: ['HTML', '이력서', '신입'],
    user_nm: 'jun_dev',
    created_at: '2025-04-17',
    view_cnt: 88,
    comment_cnt: 5,
    recommend_cnt: 14,
  },
  {
    board_sq: 2,
    board_ttl: 'React 사이드 프로젝트 팀원 모집합니다 (기획 완료)',
    normal_tags: ['React', '팀모집', '사이드'],
    user_nm: 'codingmate',
    created_at: '2025-04-16',
    view_cnt: 142,
    comment_cnt: 7,
    recommend_cnt: 11,
  },
  {
    board_sq: 3,
    board_ttl: '스터디 같이 하실 분! (백엔드 Java 중심)',
    normal_tags: ['Java', '스터디', '백엔드'],
    user_nm: 'sunny_backend',
    created_at: '2025-04-14',
    view_cnt: 76,
    comment_cnt: 2,
    recommend_cnt: 5,
  },
  {
    board_sq: 4,
    board_ttl: '포트폴리오용 프로젝트 피드백 부탁드려요!',
    normal_tags: ['포트폴리오', 'UI/UX', '신입'],
    user_nm: 'soyoung',
    created_at: '2025-04-13',
    view_cnt: 103,
    comment_cnt: 3,
    recommend_cnt: 9,
  },
  {
    board_sq: 5,
    board_ttl: 'CS 기반 면접 스터디 멤버 구합니다 (온라인)',
    normal_tags: ['CS', '스터디', '온라인'],
    user_nm: 'study_cs',
    created_at: '2025-04-12',
    view_cnt: 192,
    comment_cnt: 6,
    recommend_cnt: 17,
  },
  {
    board_sq: 1,
    board_ttl: '이력서 피드백 부탁드립니다! (프론트엔드 신입)',
    normal_tags: ['HTML', '이력서', '신입'],
    user_nm: 'jun_dev',
    created_at: '2025-04-17',
    view_cnt: 88,
    comment_cnt: 5,
    recommend_cnt: 14,
  },
  {
    board_sq: 2,
    board_ttl: 'React 사이드 프로젝트 팀원 모집합니다 (기획 완료)',
    normal_tags: ['React', '팀모집', '사이드'],
    user_nm: 'codingmate',
    created_at: '2025-04-16',
    view_cnt: 142,
    comment_cnt: 7,
    recommend_cnt: 11,
  },
  {
    board_sq: 3,
    board_ttl: '스터디 같이 하실 분! (백엔드 Java 중심)',
    normal_tags: ['Java', '스터디', '백엔드'],
    user_nm: 'sunny_backend',
    created_at: '2025-04-14',
    view_cnt: 76,
    comment_cnt: 2,
    recommend_cnt: 5,
  },
  {
    board_sq: 4,
    board_ttl: '포트폴리오용 프로젝트 피드백 부탁드려요!',
    normal_tags: ['포트폴리오', 'UI/UX', '신입'],
    user_nm: 'soyoung',
    created_at: '2025-04-13',
    view_cnt: 103,
    comment_cnt: 3,
    recommend_cnt: 9,
  },
  {
    board_sq: 5,
    board_ttl: 'CS 기반 면접 스터디 멤버 구합니다 (온라인)',
    normal_tags: ['CS', '스터디', '온라인'],
    user_nm: 'study_cs',
    created_at: '2025-04-12',
    view_cnt: 192,
    comment_cnt: 6,
    recommend_cnt: 17,
  },
]

// 한 화면에 보일 박스 숫자 설정
const viewBoxCnt = 12

const currentPage = ref(1)

// [수정] 추후 데이터에 맞게 수정
const totalPages = Math.ceil(boardList.length / viewBoxCnt)
</script>
<style></style>
