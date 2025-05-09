<template>
  <div>
    <div class="modal-header">
      <h4 class="modal-title" id="defaultModalLabel">신고 하기</h4>
      <button
        type="button"
        class="btn-close"
        data-bs-dismiss="modal"
        aria-hidden="true"
        @click="closeModal"
      ></button>
    </div>

    <div class="modal-body">
      <p class="mb-3">신고 사유를 선택해주세요:</p>
      <form>
        <div class="form-check mb-2">
          <input
            class="form-check-input"
            type="checkbox"
            value="욕설/비방"
            id="reason1"
          />
          <label class="form-check-label" for="reason1">욕설/비방</label>
        </div>
        <div class="form-check mb-2">
          <input
            class="form-check-input"
            type="checkbox"
            value="스팸/홍보"
            id="reason2"
          />
          <label class="form-check-label" for="reason2">스팸/홍보</label>
        </div>
        <div class="form-check mb-2">
          <input
            class="form-check-input"
            type="checkbox"
            value="개인정보 노출"
            id="reason3"
          />
          <label class="form-check-label" for="reason3">개인정보 노출</label>
        </div>
        <div class="form-check mb-2">
          <input
            class="form-check-input"
            type="checkbox"
            value="기타"
            id="reasonOther"
          />
          <label class="form-check-label" for="reasonOther">기타</label>
        </div>

        <!-- 직접입력칸 -->
        <div class="mt-3" id="customReasonContainer" style="display: block">
          <label for="customReason" class="form-label">기타 사유 입력</label>
          <textarea
            class="form-control"
            id="customReason"
            rows="3"
            placeholder="자세한 신고 사유를 입력해주세요."
          ></textarea>
        </div>
      </form>
    </div>

    <div class="modal-footer">
      <button type="button" class="btn btn-primary">신고 제출</button>
      <button
        type="button"
        class="btn btn-light"
        data-bs-dismiss="modal"
        @click="closeModal"
      >
        닫기
      </button>
    </div>
  </div>
</template>
<script setup>
import { useModalStore } from '@/fo/stores/modalStore'

const modalStore = useModalStore()

const closeModal = () => {
  modalStore.closeModal()
}
</script>
<style></style>
