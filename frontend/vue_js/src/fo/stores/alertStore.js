import { defineStore } from 'pinia'

export const useAlertStore = defineStore('alert', {
  state: () => ({
    visible: false,
    message: '',
    type: '', // success, danger
  }),
  actions: {
    show(message, type) {
      this.message = message
      this.type = type
      this.visible = true
      setTimeout(() => (this.visible = false), 1000)
    },
    hide() {
      this.visible = false
    },
  },
})
