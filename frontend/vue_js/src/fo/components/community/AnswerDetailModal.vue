<template>
  <div>
    <div class="modal-header">
      <h4 class="modal-title" id="largeModalLabel">QnA 답변</h4>
      <button
        type="button"
        class="btn-close"
        data-bs-dismiss="modal"
        aria-hidden="true"
      >
        ×
      </button>
    </div>
    <div class="modal-body">
      <div class="post-content ms-0">
        <div class="post-header">
          <h1 class="font-weight-semi-bold mb-0 position-relative">
            <!-- 뱃지들을 아이콘보다 위쪽에 배치 -->
            <div class="d-flex flex-wrap gap-2 mb-2">
              <span class="badge bg-primary badge-xs" style="font-size: large"
                >채택 답변</span
              >
            </div>

            <!-- 제목 + 오른쪽 아이콘들 -->
            <div class="d-flex justify-content-between align-items-start">
              <a href="blog-post.html"
                >React.js와 Vue.js의 차이점은 무엇인가요?</a
              >

              <!-- 아이콘 버튼 -->
              <span class="post-icons d-flex">
                <!-- 조회수 버튼 추가 -->
                <a
                  href="#"
                  class="btn btn-light btn-rounded text-grey d-flex align-items-center me-2"
                  style="font-size: 1rem"
                >
                  <i class="fa-solid fa-eye" style="font-size: 1.2rem"></i>
                  <span class="me-2 ms-2 text-grey">조회수</span>
                  <span>123</span>
                </a>

                <!-- 추천 버튼 -->
                <a
                  href="#"
                  class="btn btn-light btn-rounded text-grey d-flex align-items-center me-2"
                  style="font-size: 1rem"
                >
                  <i
                    class="fa-regular fa-thumbs-up"
                    style="font-size: 1.2rem"
                  ></i>
                  <span class="me-2 ms-2 text-grey">추천</span> <span>45</span>
                </a>

                <!-- 신고 버튼 -->
                <a
                  href="#"
                  class="btn btn-light btn-rounded text-grey d-flex align-items-center me-2"
                  style="font-size: 1rem"
                >
                  <i
                    class="fa-solid fa-land-mine-on"
                    style="font-size: 1.2rem"
                  ></i>
                  <span class="me-2 ms-2 text-grey">신고</span>
                </a>
              </span>
            </div>
          </h1>

          <!-- 작성자 및 날짜 -->
          <div class="post-meta mt-2">
            <span><i class="far fa-user"></i> By <a href="#">홍길동</a> </span>
            <span
              ><i class="far fa-calendar-alt"></i>
              <a href="#">2025-04-17</a></span
            >
          </div>
        </div>

        <p class="post-description mt-5 mb-5 text-5">
          React.js와 Vue.js는 모두 인기 있는 프론트엔드 라이브러리인데, 각
          기술의 주요 차이점은 무엇인가요? 두 기술 중 어떤 상황에서 어떤 것을
          선택하는 것이 좋을지 궁금합니다.
        </p>
        <!-- 첨부파일 섹션 -->
        <div class="post-attachments mt-3 mb-4">
          <h5 class="font-weight-bold text-grey">첨부파일</h5>
          <ul class="list-unstyled">
            <li>
              <a href="files/프로젝트소개.pdf" download>
                React_vs_Vue_개발비교.pdf</a
              >
            </li>
            <li>
              <a href="files/기획안.pptx" download> React_기초_가이드.pdf</a>
            </li>
          </ul>
        </div>
        <!-- "태그" 제목 추가 -->
        <div class="post-tags mt-4">
          <h5 class="font-weight-bold text-grey">태그</h5>
          <!-- 태그 제목 추가 -->
          <a href="#" class="btn btn-rounded btn-primary">React.js</a>
          <a href="#" class="btn btn-rounded btn-primary">Vue.js</a>
          <a href="#" class="btn btn-rounded btn-light">프론트엔드</a>
          <a href="#" class="btn btn-rounded btn-light">사이드 프로젝트</a>
        </div>
        <div class="post-admin mt-4 text-end">
          <a href="#" class="btn btn-primary">답변 채택하기</a>
          <a href="#" class="btn btn-primary">수정</a>
          <a href="#" class="btn btn-primary">삭제</a>
        </div>

        <div class="post-comments mt-5 post-comments">
          <h4 class="mb-3" style="font-size: 1.5rem">댓글 (2)</h4>

          <ul class="comments">
            <!-- <li>
                                <div class="comment">
                                    <div class="img-thumbnail img-thumbnail-no-borders d-none d-sm-block">
                                        <img class="avatar" alt="" src="img/avatars/avatar-2.jpg">
                                    </div>
                                    <div class="comment-block" style="font-size: 1.2rem;">
                                        <div class="comment-arrow"></div>
                                        <span class="comment-by" style="font-size: 1.3rem;">
                                            <strong>홍길동</strong>
                                        </span>
                                        <p style="font-size: 1.2rem;">이 프로젝트 정말 흥미롭네요. 참여하고 싶습니다!</p>
                
                                        <span class="date float-end" style="font-size: 1.1rem;">
                                            <a href="#" class="text-primary" style="font-size: 1.1rem;">수정</a>
                                            <a href="#" class="text-primary" style="font-size: 1.1rem;">삭제 </a>2025년 1월 12일 1:38 PM
                                        </span>
                                    </div>
                                </div>
                            </li> -->
            <li>
              <div class="comment">
                <div
                  class="img-thumbnail img-thumbnail-no-borders d-none d-sm-block"
                >
                  <img class="avatar" alt="" src="img/avatars/avatar.jpg" />
                </div>
                <div class="comment-block" style="font-size: 1.2rem">
                  <div class="comment-arrow"></div>

                  <!-- 이름 + 신고/하트 -->
                  <div
                    class="d-flex justify-content-between align-items-center"
                    style="margin-bottom: 10px"
                  >
                    <span
                      class="comment-by text-primary"
                      style="font-size: 1.3rem"
                    >
                      <strong>홍길동</strong>
                    </span>
                    <span class="comment-icons d-flex">
                      <a
                        href="#"
                        class="text-danger"
                        style="font-size: 1rem; margin-right: 15px"
                      >
                        <span class="ms-2 text-primary">수정</span>
                      </a>
                      <a href="#" class="text-danger" style="font-size: 1rem">
                        <span class="ms-2 text-primary">삭제</span>
                      </a>
                    </span>
                  </div>

                  <!-- 내용 -->
                  <p style="font-size: 1.2rem">
                    React.js는 대규모 애플리케이션에서 많이 사용되는 것 같아요.
                    Vue.js는 더 직관적이고 빠르게 시작할 수 있어서 소규모
                    프로젝트에서 유리할 수 있을 것 같네요
                  </p>

                  <!-- 날짜 + 수정/삭제 (홍길동과 동일하게 float-end 사용) -->
                  <span class="date float-end" style="font-size: 1.1rem">
                    2025년 1월 12일 1:38 PM
                  </span>
                </div>
              </div>
            </li>
            <li>
              <div class="comment">
                <div
                  class="img-thumbnail img-thumbnail-no-borders d-none d-sm-block"
                >
                  <img class="avatar" alt="" src="img/avatars/avatar.jpg" />
                </div>
                <div class="comment-block" style="font-size: 1.2rem">
                  <div class="comment-arrow"></div>

                  <!-- 이름 + 신고/하트 -->
                  <div
                    class="d-flex justify-content-between align-items-center"
                    style="margin-bottom: 10px"
                  >
                    <span
                      class="comment-by text-primary"
                      style="font-size: 1.3rem"
                    >
                      <strong>박영수</strong>
                    </span>
                    <span class="comment-icons d-flex">
                      <a
                        href="#"
                        class="text-danger"
                        style="font-size: 1rem; margin-right: 15px"
                      >
                        <span class="ms-2 text-grey">추천 6</span>
                        <!-- <i class="fa-solid fa-land-mine-on" style="font-size: 1.2rem;"></i> -->
                      </a>
                      <a href="#" class="text-pink" style="font-size: 1rem">
                        <span class="ms-2 text-grey">신고</span>
                        <!-- <i class="fa-solid fa-heart text-pink" style="font-size: 1.2rem; color:pink"></i> -->
                      </a>
                    </span>
                  </div>

                  <!-- 내용 -->
                  <p style="font-size: 1.2rem">
                    Vue.js는 더 간단하고 빠르게 구현할 수 있는 것 같아요.
                    React는 다양한 기능을 확장할 수 있어서 유연하게 사용할 수
                    있죠.
                  </p>

                  <!-- 날짜 + 수정/삭제 (홍길동과 동일하게 float-end 사용) -->
                  <span class="date float-end" style="font-size: 1.1rem">
                    2025년 1월 12일 1:45 PM
                  </span>
                </div>
              </div>
            </li>
          </ul>
        </div>
        <div class="post-block mt-5 post-leave-comment">
          <form
            class="contact-form p-4 rounded bg-color-grey"
            action="php/contact-form.php"
            method="POST"
          >
            <div class="p-2">
              <div class="row">
                <label class="form-label font-weight-bold text-dark text-5 mb-3"
                  >댓글 남기기</label
                >

                <div class="form-group col-11">
                  <input
                    type="text"
                    maxlength="5000"
                    data-msg-required="댓글을 입력해주세요."
                    class="form-control"
                    name="message"
                    required=""
                  />
                </div>
                <div class="form-group col-1">
                  <input
                    type="submit"
                    value="댓글 작성"
                    class="btn btn-primary text-3"
                    data-loading-text="로딩 중..."
                  />
                </div>
              </div>
              <div class="row"></div>
            </div>
          </form>
        </div>
      </div>
    </div>
    <div class="modal-footer">
      <a href="resume-list.html" class="btn btn-outline-secondary">닫기</a>
    </div>
  </div>
</template>
<script></script>
<style></style>
