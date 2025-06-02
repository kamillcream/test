<template>
  <div class="modal-layer">
    <div class="modal-content">
      <div class="modal-header">
        <button class="close-btn" @click="modalStore.closeModal()">×</button>
      </div>
      <div id="daum-postcode"></div>
    </div>
  </div>
</template>

<script setup>
/* global daum */ //주소 전역 변수 선언

import { onMounted, defineProps } from 'vue'
import { useModalStore } from '@/fo/stores/modalStore'

const props = defineProps({
  onComplete: Function,
})

const modalStore = useModalStore()

onMounted(() => {
  new daum.Postcode({
    onComplete: function (data) {
      let addr = ''
      if (data.userSelectedType === 'R') {
        addr = data.roadAddress
      } else {
        addr = data.jibunAddress
      }

      // 시도 + 시군구 분리
      const parts = addr.split(' ')
      const sido = parts[0] || ''
      const sigungu = parts[1] || ''
      // 정확한 sigungu가 추출되었는지 다시 검증
      if (!sido || !sigungu) {
        alert(
          '주소에서 시군구 정보를 추출할 수 없습니다. 다른 주소를 선택해주세요.',
        )
        return
      }

      props.onComplete?.({
        zonecode: data.zonecode,
        address: addr,
        sido,
        sigungu,
        latitude: data.latitude ? Number(data.latitude) : null,
        longitude: data.longitude ? Number(data.longitude) : null,
      })

      console.log('[주소 선택 완료]', { sido, sigungu, addr })
      modalStore.closeModal()
    },
  }).embed(document.getElementById('daum-postcode'))
})
</script>
<style scoped>
.modal-layer {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 9999;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
}
.modal-content {
  position: relative;
  width: 540px;
  max-width: 95vw;
  background: #fff;
  padding: 20px;
  overflow-x: hidden;
  overflow-y: auto;
  box-sizing: border-box;
}
.modal-header {
  height: 40px; /* 닫기 버튼 높이만큼 */
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.close-btn {
  position: absolute;
  top: 16px;
  right: 16px;
  background: transparent;
  border: none;
  font-size: 2rem;
  cursor: pointer;
  z-index: 10;
}
#daum-postcode {
  min-width: 0;
  width: 100% !important;
}
</style>
