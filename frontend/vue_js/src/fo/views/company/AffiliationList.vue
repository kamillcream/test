<template>
  <section>
    <CommonPageHeader
      title=""
      strongText="소속 모집 공고"
      :breadcrumbs="[{ text: 'Home', link: '/' }, { text: '소속' }]"
    />
    <div class="container py-4">
      <div class="row">
        <div class="col">
          <div class="blog-posts">
            <div class="row">
              <!-- 카드 -->
              <div
                v-for="afltn in afltnList.slice(
                  (currentPage - 1) * viewBoxCnt,
                  currentPage * viewBoxCnt,
                )"
                :key="afltn"
                class="col-md-4 col-lg-3"
              >
                <article
                  class="post post-medium border-0 pb-0 mb-5 shadow-sm rounded overflow-hidden bg-white"
                >
                  <!-- 이미지 영역 -->
                  <div
                    class="post-image position-relative"
                    style="height: 180px"
                  >
                    <a href="#" class="d-block h-100 w-100 position-relative">
                      <img
                        :src="`${afltn.company_profile_image_url}`"
                        class="img-fluid img-thumbnail img-thumbnail-no-borders rounded-0 h-100 w-100"
                        style="object-fit: cover"
                        alt="기업 이미지"
                      />
                      <!-- 조회수 뱃지 -->
                      <div
                        class="position-absolute top-0 end-0 m-2 px-2 py-1 text-white rounded d-flex align-items-center gap-1"
                        style="
                          background-color: rgba(0, 0, 0, 0.5);
                          font-size: 0.85rem;
                        "
                      >
                        <i class="bi bi-eye"></i>
                        <span>{{ formatNum(afltn.view_cnt) }}</span>
                      </div>
                    </a>
                  </div>
                  <!-- 내용 영역 -->
                  <div class="post-content p-3 bg-white">
                    <div
                      class="d-flex justify-content-between align-items-center mb-2"
                    >
                      <h2
                        class="font-weight-semibold text-5 line-height-6 mb-0"
                        style="font-size: 1.1rem"
                      >
                        <a href="#" class="text-primary text-decoration-none">{{
                          afltn.company_nm
                        }}</a>
                      </h2>
                      <a
                        href="#"
                        class="text-muted"
                        style="text-decoration: none"
                      >
                        <i class="bi bi-heart-fill" style="color: pink"></i>
                      </a>
                    </div>
                    <!-- 키워드 태그 뱃지 -->
                    <div class="d-flex flex-wrap gap-2 mb-3">
                      <span
                        v-for="tag in afltn.tags"
                        :key="tag"
                        class="badge bg-light text-grey border"
                        >{{ tag }}</span
                      >
                    </div>
                    <!-- 설명 영역 -->
                    <div
                      class="description p-3 mb-3 rounded"
                      style="background-color: #f5f5f5"
                    >
                      <p
                        class="small mb-0 text-dark"
                        style="font-size: 0.85rem"
                      >
                        {{ afltn.company_greeting_txt }}
                      </p>
                    </div>
                    <div class="d-grid" @click="clickApplication(afltn)">
                      <a
                        href="#"
                        class="btn btn-light btn-sm text-primary"
                        style="font-size: 0.85rem"
                        >소속 신청하기</a
                      >
                    </div>
                  </div>
                </article>
              </div>
            </div>
            <CommonPagination
              :currentPage="currentPage"
              :totalPages="totalPages"
              @update:currentPage="currentPage = $event"
            />
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
<script setup>
import CommonPageHeader from '@/fo/components/common/CommonPageHeader.vue'
import CommonPagination from '@/fo/components/common/CommonPagination.vue'
import AffiliationRecuit from '@/fo/components/company/AffiliationRecruit.vue'
import { useModalStore } from '@/fo/stores/modalStore'
import { ref } from 'vue'

const modalStore = useModalStore()

const afltnList = [
  {
    company_sq: 1,
    company_nm: 'EST Soft1',
    user_nm: '홍길동',
    company_adress: '서울시 강남구ff',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 2,
    company_nm: 'EST Soft2',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 3,
    company_nm: 'EST Soft3',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 4,
    company_nm: 'EST Soft4',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 5,
    company_nm: 'EST Soft5',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 6,
    company_nm: 'EST Soft6',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 7,
    company_nm: 'EST Soft7',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 8,
    company_nm: 'EST Soft8',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 9,
    company_nm: 'EST Soft9',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 10,
    company_nm: 'EST Soft10',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 11,
    company_nm: 'EST Soft11',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 12,
    company_nm: 'EST Soft12',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 13,
    company_nm: 'EST Soft13',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 14,
    company_nm: 'EST Soft14',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 15,
    company_nm: 'EST Soft15',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 16,
    company_nm: 'EST Soft16',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 17,
    company_nm: 'EST Soft17',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 18,
    company_nm: 'EST Soft18',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 19,
    company_nm: 'EST Soft19',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 20,
    company_nm: 'EST Soft20',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 21,
    company_nm: 'EST Soft21',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 22,
    company_nm: 'EST Soft22',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 23,
    company_nm: 'EST Soft23',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 24,
    company_nm: 'EST Soft24',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 25,
    company_nm: 'EST Soft25',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 26,
    company_nm: 'EST Soft26',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 27,
    company_nm: 'EST Soft27',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 28,
    company_nm: 'EST Soft28',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 29,
    company_nm: 'EST Soft29',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 30,
    company_nm: 'EST Soft30',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 31,
    company_nm: 'EST Soft31',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 32,
    company_nm: 'EST Soft32',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 33,
    company_nm: 'EST Soft33',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 34,
    company_nm: 'EST Soft34',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 35,
    company_nm: 'EST Soft35',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 36,
    company_nm: 'EST Soft36',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 37,
    company_nm: 'EST Soft37',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 38,
    company_nm: 'EST Soft38',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
  {
    company_sq: 39,
    company_nm: 'EST Soft39',
    user_nm: '홍길동',
    company_adress: '서울시 강남구',
    company_profile_image_url: 'img/blog/medium/blog-8.jpg',
    company_greeting_txt:
      '신입/경력 Java 백엔드 개발자 모집. Spring Boot 기반 API 서버 개발 업무를 담당합니다.',
    company_open_dt: '2025-04-17',
    view_cnt: 1200,
    tags: ['서울', '신입', '학력무관', 'JAVA'],
  },
]

const formatNum = (num) => {
  if (num < 1000) {
    return num.toString()
  } else if (num < 1000000) {
    return (num / 1000).toFixed(1).replace(/\.0$/, '') + 'K'
  } else {
    return (num / 1000000).toFixed(1).replace(/\.0$/, '') + 'M'
  }
}

// 한 화면에 보일 박스 숫자 설정
const viewBoxCnt = 12

const currentPage = ref(1)

// [수정] 추후 데이터에 맞게 수정
const totalPages = Math.ceil(afltnList.length / viewBoxCnt)

// 소속 신청하기 모달
const clickApplication = (afltnInfo) => {
  modalStore.openModal(AffiliationRecuit, {
    afltnInfo: afltnInfo,
  })
}
</script>
<style></style>
