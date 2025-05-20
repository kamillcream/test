import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    userNm: localStorage.getItem('userNm') || '',
    userType: localStorage.getItem('userType') || '',
    isLoggedIn: !!localStorage.getItem('userNm'), // userNm이 있으면 로그인 상태로 판단
  }),
  actions: {
    setUser({ userNm, userTypeCd }) {
      const userType =
        userTypeCd === 301 ? 'PERSONAL' : userTypeCd === 302 ? 'COMPANY' : ''

      this.userNm = userNm
      this.userType = userType
      this.isLoggedIn = true

      // 로컬스토리지에 저장
      localStorage.setItem('userNm', userNm)
      localStorage.setItem('userType', userType)
    },
    clearUser() {
      this.userNm = ''
      this.userType = ''
      this.isLoggedIn = false

      // 로컬스토리지 초기화
      localStorage.removeItem('userNm')
      localStorage.removeItem('userType')
    },
  },
})
