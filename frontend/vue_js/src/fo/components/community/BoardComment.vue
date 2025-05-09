<template>
  <div>
    <div class="post-comments mt-5 post-comments">
      <h4 class="mb-3" style="font-size: 1.5rem">
        댓글 ({{ comments.length }})
      </h4>
      <ul class="comments">
        <li v-for="comment in comments" :key="comment">
          <div class="comment">
            <div
              class="img-thumbnail img-thumbnail-no-borders d-none d-sm-block"
            >
              <img
                class="avatar"
                alt=""
                :src="`${comment.user_profile_img_url}`"
              />
            </div>
            <div class="comment-block" style="font-size: 1.2rem">
              <div class="comment-arrow"></div>
              <!-- 이름 + 신고/하트 -->
              <div
                class="d-flex justify-content-between align-items-center"
                style="margin-bottom: 10px"
              >
                <span class="comment-by text-primary" style="font-size: 1.3rem">
                  <strong>{{ comment.user_nm }}</strong>
                </span>
                <!-- 작성자 본인일 경우 -->
                <!-- [추가] 본인 인증 로직 -->
                <span v-if="comment.user_sq == 3" class="comment-icons d-flex">
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
                <!-- 작성자 아닌 경우 -->
                <span v-else class="comment-icons d-flex">
                  <a
                    href="#"
                    class="text-danger"
                    style="font-size: 1rem; margin-right: 15px"
                  >
                    <span class="ms-2 text-primary"
                      >추천 {{ comment.recommend_cnt }}</span
                    >
                  </a>
                  <a href="#" class="text-danger" style="font-size: 1rem">
                    <span class="ms-2 text-primary">신고</span>
                  </a>
                </span>
              </div>
              <!-- 내용 -->
              <p style="font-size: 1.2rem">
                {{ comment.comment_description_edt }}
              </p>
              <!-- 날짜 + 수정/삭제 (홍길동과 동일하게 float-end 사용) -->
              <span class="date float-end" style="font-size: 1.1rem">
                {{ comment.created_at }}
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
</template>
<script>
export default {
  name: 'BoardComment',
  props: { comments: Array },
}
</script>
<style></style>
