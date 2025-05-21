import axios from 'axios'

const baseUrl = 'http://localhost:8080/api'

// axios 인스턴스 생성
const apiInstance = axios.create({
  baseURL: baseUrl,
  withCredentials: true, // 쿠키 전송을 위해 필요
})

// 토큰 재발급 시도 중 여부 플래그
let isRefreshing = false
let failedQueue = []

const processQueue = (error, token = null) => {
  failedQueue.forEach((prom) => {
    if (error) {
      prom.reject(error)
    } else {
      prom.resolve(token)
    }
  })

  failedQueue = []
}

// 응답 인터셉터 설정
apiInstance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config

    if (error.response?.status === 401 && !originalRequest._retry) {
      if (isRefreshing) {
        return new Promise(function (resolve, reject) {
          failedQueue.push({ resolve, reject })
        })
          .then(() => apiInstance(originalRequest))
          .catch((err) => Promise.reject(err))
      }

      originalRequest._retry = true
      isRefreshing = true

      try {
        await apiInstance.post('/refresh-token') // refresh-token은 쿠키로 전송됨
        processQueue(null)
        return apiInstance(originalRequest) // 원래 요청 재시도
      } catch (err) {
        processQueue(err)
        return Promise.reject(err)
      } finally {
        isRefreshing = false
      }
    }

    return Promise.reject(error)
  },
)

// 공통 HTTP 메서드 정의
const api = {
  async $get(url, params) {
    try {
      const response = await apiInstance.get(url, params)
      return response.data
    } catch (err) {
      console.error(err)
      throw err
    }
  },
  async $post(url, data, config = undefined) {
    try {
      const response = await apiInstance.post(url, data, config)
      return response.data
    } catch (err) {
      console.error(err)
      throw err
    }
  },
  async $put(url, data) {
    try {
      const response = await apiInstance.put(url, data)
      return response.data
    } catch (err) {
      console.error(err)
      throw err
    }
  },
  async $patch(url, data) {
    try {
      const response = await apiInstance.patch(url, data)
      return response.data
    } catch (err) {
      console.error(err)
      throw err
    }
  },
  async $delete(url) {
    try {
      const response = await apiInstance.delete(url)
      return response.data
    } catch (err) {
      console.error(err)
      throw err
    }
  },
}

export { api, baseUrl }
