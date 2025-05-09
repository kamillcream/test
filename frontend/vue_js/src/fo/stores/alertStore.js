import { defineStore } from 'pinia'

export const useAlertStore = defineStore('alert', {
  state: () => ({
    visible: false,
    message: '',
    type: '', // success, danger
    scrollTop: 0, // 스크롤 위치 상태 추가
  }),
  actions: {
    show(message, type) {
      // 알림 창의 위치를 현재 스크롤 위치에 맞춰 설정
      this.scrollTop = window.scrollY
      this.message = message
      this.type = type
      this.visible = true

      setTimeout(() => {
        this.visible = false
      }, 1000)
    },
    hide() {
      this.visible = false
    },
  },
})
