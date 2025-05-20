import { createApp } from 'vue'
import App from './App.vue'
import { createPinia } from 'pinia'
import { useAuthStore } from './fo/stores/authStore'
import router from './fo/router'

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
app.use(router)

const authStore = useAuthStore()
authStore.initializeFromStorage()

app.mount('#app')
