<template lang="">
    <HeaderBeforeLogin/>
    <div class="container py-5">
        <div class="row pt-4 mt-2 mb-5">
            <!-- 좌측: 지원 자격 (스크롤 없애기) -->
            <div class="col-md-8 mb-4 mb-md-0" style="max-height: auto;">
                <h2 class="text-color-dark font-weight-normal text-5 mb-2">지원 자격 / 근무 조건</h2>
                <ul class="list list-icons list-primary list-borders text-2">
                    <li><i class="fas fa-caret-right left-10"></i><strong class="text-color-primary">경력 / 학력 :</strong> {{ project.career }} /
                        {{ project.education }} </li>
                    <li v-for="job in project.jobs" :key="job.id">
                        <i class="fas fa-caret-right left-10"></i>
                        <strong class="text-color-primary">모집 직군 :</strong> {{ job }}
                    </li>

                    <!-- 필수 기술 -->
                    <li>
                        <i class="fas fa-caret-right left-10"></i><strong class="text-color-primary">필수 기술 </strong>
                        <ul class="ps-4 mb-2">
                            <li>
                                <strong class="text-dark">언어</strong>
                                <div 
                                v-for="language in project.languages" :key="language.id"
                                class="d-flex align-items-center gap-2 mt-1">
                                    <div class="d-flex align-items-center me-3">
                                        <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" alt="Java" width="24" height="24" class="me-1">
                                        <span>{{ language }}</span>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <strong class="text-dark">Framework</strong>
                                <div class="d-flex align-items-center gap-2 mt-1">
                                    <div class="d-flex align-items-center me-3">
                                        <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" alt="Spring Boot" width="24" height="24" class="me-1">
                                        <span>Spring Boot</span>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <strong class="text-dark">Tool</strong>
                                <div class="d-flex align-items-center gap-2 mt-1">
                                    <div class="d-flex align-items-center me-3">
                                        <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg" alt="Git" width="24" height="24" class="me-1">
                                        <span>Git</span>
                                    </div>
                                    <div class="d-flex align-items-center me-3">
                                        <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/jenkins/jenkins-original.svg" alt="Jenkins" width="24" height="24" class="me-1">
                                        <span>Jenkins</span>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </li>

                    <!-- 우대 기술 -->
                    <li>
                        <i class="fas fa-caret-right left-10"></i><strong class="text-color-primary">우대 기술 </strong>
                        <ul class="ps-4 mb-2">
                            <li>
                                <strong class="text-dark">언어</strong>
                                <div class="d-flex align-items-center gap-2 mt-1">
                                    <div class="d-flex align-items-center me-3">
                                        <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" alt="Java" width="24" height="24" class="me-1">
                                        <span>Java</span>
                                    </div>
                                    <div class="d-flex align-items-center me-3">
                                        <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/python/python-original.svg" alt="Python" width="24" height="24" class="me-1">
                                        <span>Python</span>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <strong class="text-dark">Framework</strong>
                                <div class="d-flex align-items-center gap-2 mt-1">
                                    <div class="d-flex align-items-center me-3">
                                        <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" alt="Spring Boot" width="24" height="24" class="me-1">
                                        <span>Spring Boot</span>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <strong class="text-dark">Tool</strong>
                                <div class="d-flex align-items-center gap-2 mt-1">
                                    <div class="d-flex align-items-center me-3">
                                        <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/intellij/intellij-original.svg" alt="IntelliJ" width="24" height="24" class="me-1">
                                        <span>IntelliJ</span>
                                    </div>
                                    <div class="d-flex align-items-center me-3">
                                        <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg" alt="Docker" width="24" height="24" class="me-1">
                                        <span>Docker</span>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </li>


                    <!-- 우대 사항 -->
                    <li><i class="fas fa-caret-right left-10"></i><strong class="text-color-primary">우대 사항 :</strong> 금융권
                        프로젝트 경험자</li>

                    <!-- 근무 조건 -->
                    <li><i class="fas fa-caret-right left-10"></i><strong class="text-color-primary">근무 형태 :</strong> 프리랜서 /
                        계약직 / 정규직</li>
                    <li><i class="fas fa-caret-right left-10"></i><strong class="text-color-primary">근무 지역 :</strong> 서울
                        영등포구</li>
                    <li><i class="fas fa-caret-right left-10"></i><strong class="text-color-primary">단가 :</strong> 협의 후 결정
                    </li>
                </ul>


            </div>

            <!-- 우측: 회사 정보 (고정된 카드) -->
            <div class="col-md-4" style="position: sticky; top: 100px;">
                <div class="card">
                    <div class="card-body">
                        <div class="d-flex align-items-center mb-3">
                            <div class="me-4 flex-shrink-0">
                                <img src="img/blog/medium/blog-2.jpg" alt="프로젝트 이미지" class="rounded-circle" style="width: 70px; height: 70px; object-fit: cover;">
                            </div>
                            <div>
                                <h2 class="text-color-dark font-weight-normal text-5 mb-0">프로젝트 Title</h2>
                                <p class="text-muted mb-0">EST Soft</p>
                            </div>
                        </div>

                        <p>㈜이에스티소프트는 2021년에 설립된 회사로 자본금 1,000만원, 매출액 20억 5,769만원, 사원수 50명 규모의 고용노동부에서 선정한 강소기업입니다...</p>

                        <div class="card-footer bg-white border-top-0 pt-4">
                            <div class="text-start text-2">
                                <p class="mb-1 text-color-primary"><i class="fas fa-caret-right me-2"></i><strong class="text-color-primary">모집 기간 :</strong> 2025/04/01 ~ 2025/04/10
                                </p>
                                <p class="mb-1 text-color-primary"><i class="fas fa-caret-right me-2"></i><strong class="text-color-primary">인터뷰 기간 :</strong> 2025/04/11 ~ 2025/04/30
                                </p>
                                <p class="mb-0 text-color-primary"><i class="fas fa-caret-right me-2"></i><strong class="text-color-primary">수행 기간 :</strong> 2025/05/01 ~ 2025/12/31
                                </p>
                            </div>
                        </div>

                        <hr class="solid my-4">

                        <!-- 버튼 영역 (중앙 정렬, 크기 증가) -->
                        <div class="d-flex justify-content-center align-items-center gap-3">
                            <a href="#" class="btn btn-lg btn-rounded btn-primary btn-lg">지원하기</a>
                            <!-- 스크랩 버튼 -->
                            <a href="#" class="btn btn-lg btn-light btn-rounded d-flex align-items-center btn-lg">
                                <i class="bi bi-heart fs-5" style="line-height: 1; color: lightpink;"></i>
                                <span class="me-2 ms-2">스크랩</span><span>45</span>
                            </a>
                        </div>
                    </div>

                    <!-- 조회수 텍스트 (우측 하단으로 배치) -->
                    <div class="position-absolute top-0 end-0 p-2">
                        <span class="text-grey" style="font-size: 0.8rem;">조회수: 113</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import HeaderBeforeLogin from '../../components/common/HeaderBeforeLogin.vue'

export default {
  name: 'UserProjectSpecPage',
  components: {
    HeaderBeforeLogin
  },
  props: {
    project: {
      type: Object,
      required: true
    }
  }
}

</script>
<style lang="">
    
</style>