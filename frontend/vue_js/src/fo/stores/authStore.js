import { defineStore } from 'pinia'

function parseJwt(token) {
  try {
    return JSON.parse(atob(token.split('.')[1])) // base64 디코딩 후 파싱
  } catch (e) {
    return null
  }
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || null,
    userType: null, // 'PERSONAL' | 'COMPANY'  | null
  }),
  getters: {
    isLoggedIn: (state) => !!state.token,
    isPersonal: (state) => state.userType === 'PERSONAL',
    isCompany: (state) => state.userType === 'COMPANY',
  },
  actions: {
    setToken(token) {
      this.token = token
      localStorage.setItem('token', token)

      const payload = parseJwt(token)
      this.userType = payload?.userType || null
    },
    logout() {
      this.token = null
      this.userType = null
      localStorage.removeItem('token')
    },
    initializeFromStorage() {
      const storedToken = localStorage.getItem('token')
      if (storedToken) {
        this.setToken(storedToken)
      }
    },
  },
})
