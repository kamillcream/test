<template>
  <div class="container py-5 mt-3">
    <!-- 🔽 필터/검색 UI -->
    <div class="row align-items-center mt-3 mb-2">
      <div class="col-md-12 d-flex justify-content-end gap-2">
        <select v-model="searchType" class="form-select form-select-sm w-auto" style="font-size: 14px; padding: 4px;">
          <option value="all">전체</option>
          <option value="name">이름</option>
          <option value="skill">사용 기술</option>
        </select>
        <input v-model="searchText" type="text" class="form-control form-control-sm w-auto" placeholder="검색어 입력" style="font-size: 14px; padding: 4px;">
        <button class="btn btn-primary btn-sm" style="font-size: 14px; padding: 4px;" @click="search">검색</button>
      </div>
    </div>

    <div class="row">
      <div class="col pt-2 mt-1">
        <hr class="my-4">
      </div>
    </div>

    <div class="row">
      <div class="col">
        <ul class="simple-post-list m-0 position-relative" style="padding: 0;">
          <li v-for="member in members" :key="member.id" style="border-bottom: 1px rgb(230, 230, 230) solid; padding: 8px 10px; font-size: 14px;" class="d-flex flex-column gap-2">
            <!-- 상단: 이름/소개 + 퇴사처리 버튼 -->
            <div class="d-flex justify-content-between align-items-center">
              <div class="d-flex gap-2 align-items-center">
                <a href="#" class="text-5 m-0" style="font-size: 14px;">{{ member.name }} /</a>
                <a href="#" class="text-4 m-0" style="font-size: 14px;">{{ member.intro }}</a>
              </div>
              <span v-if="member.status === '재직'" class="btn btn-primary btn-outline btn-lg" style="font-size: 14px; padding: 8px 12px;" @click="fireMember(member.id)">퇴사 처리</span>
              <span v-else class="btn btn-light btn-lg" style="font-size: 14px; padding: 8px 12px;">퇴사</span>
            </div>

            <div class="d-flex justify-content-between align-items-center" style="font-size: 14px;">
              <!-- 좌측: 경력 / 사용 기술 -->
              <div class="d-flex align-items-center gap-2">
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold">경력</span> | {{ member.career }}
                </div>
                <div class="d-flex align-items-center gap-2 ms-3">
                  <span class="text-dark text-uppercase font-weight-semibold">사용 기술</span> |
                  <div v-for="skill in member.skills" :key="skill.name" class="btn d-flex align-items-center gap-2 border-0" style="padding: 2px 6px;">
                    <img :src="skill.icon" width="16">
                    {{ skill.name }}
                  </div>
                </div>
              </div>
              <!-- 우측: 입사일자/퇴사일자 -->
              <div class="text-muted" style="white-space: nowrap;">
                <span class="text-dark text-uppercase font-weight-semibold">
                  {{ member.status === '재직' ? '입사일자' : '퇴사일자' }}
                </span> | {{ member.status === '재직' ? member.joinDate : member.leaveDate }}
              </div>
            </div>
          </li>
        </ul>

        <!-- 페이징 -->
        <div class="mt-5">
          <ul class="pagination float-end">
            <li class="page-item"><a class="page-link" href="#" @click.prevent="changePage(currentPage - 1)"><i class="fas fa-angle-left"></i></a></li>
            <li v-for="page in totalPages" :key="page" class="page-item" :class="{ active: currentPage === page }">
              <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page }}</a>
            </li>
            <li class="page-item"><a class="page-link" href="#" @click.prevent="changePage(currentPage + 1)"><i class="fas fa-angle-right"></i></a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const searchType =ref('all')
const searchText =ref('')
const currentPage =ref('1')
const totalPages =ref('3')
const members =ref([
  {
    id: 1,
          name: '홍길동',
          intro: '안녕하세요. Java 개발자입니다.',
          career: '0년차',
          skills: [
            { name: 'Java', icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg' },
            { name: 'Python', icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/python/python-original.svg' },
            { name: 'Spring Boot', icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg' }
          ],
          status: '재직',
          joinDate: '2023-06-01',
          leaveDate: ''
        },
        {
          id: 2,
          name: '홍길동',
          intro: '대표이력서 미설정',
          career: '0년차',
          skills: [
            { name: 'Java', icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg' },
            { name: 'Python', icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/python/python-original.svg' },
            { name: 'Spring Boot', icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg' }
          ],
          status: '재직',
          joinDate: '2023-06-01',
          leaveDate: ''
        },
        {
          id: 3,
          name: '홍길동',
          intro: '안녕하세요. Java 개발자입니다.',
          career: '0년차',
          skills: [
            { name: 'Java', icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg' },
            { name: 'Python', icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/python/python-original.svg' },
            { name: 'Spring Boot', icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg' }
          ],
          status: '퇴사',
          joinDate: '2023-06-01',
          leaveDate: '2024-12-01'
        }
  ])

  function search() {
    //검색 로직 구현
    console.log('검색:', searchType.value, searchText.value)
  }

  function fireMember(id) {
    //퇴사 처리 로직 구현
    console.log('퇴사 처리:', id)
  }

  function changePage(page) {
    if (page < 1 || page > totalPages.value) return
    currentPage.value = page
    //페이지 변경 로직 구현
  }


</script>

<style scoped>
.simple-post-list {
  list-style: none;
  padding: 0;
}
</style> 