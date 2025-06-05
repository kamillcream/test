import { defineStore } from 'pinia'

export const useAffiliationStore = defineStore('Affiliation', {
  state: () => ({
    viewerSq: null,
    greeting: '',
  }),
  actions: {
    resetGreeting() {
      this.greeting = ''
    },
  },
})
