<template>
  <section>
    <div>
      <CommonPageHeader
        :title="signUpType === 'PERSONAL' ? '개인' : '기업'"
        strongText="회원가입"
        :breadcrumbs="[{ text: 'Home', link: '/' }, { text: 'SignUp' }]"
      />

      <div class="row justify-content-md-center">
        <div class="col-md-7">
          <div class="featured-box featured-box-primary text-start mt-0">
            <div class="box-content">
              <h4
                class="color-primary font-weight-semibold text-7 text-uppercase mb-3"
              >
                {{
                  signUpType === 'PERSONAL' ? '개인 회원가입' : '기업 회원가입'
                }}
              </h4>
              <component
                :is="
                  signUpType === 'PERSONAL'
                    ? PersonalSignUpForm
                    : CompanySignUpForm
                "
                @submit="handleSubmit"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { ref, onMounted } from 'vue'
import CommonPageHeader from '@/fo/components/common/CommonPageHeader.vue'
import CompanySignUpForm from '@/fo/components/login&signup/CompanySignUpForm.vue'
import PersonalSignUpForm from '@/fo/components/login&signup/PersonalSignUpForm.vue'

const route = useRoute()
const router = useRouter()
const signUpType = ref('')

onMounted(() => {
  const type = route.query.loginType
  if (type === 'PERSONAL' || type === 'COMPANY') {
    signUpType.value = type

    router.replace({ path: route.path })
  }
  console.log('signUpType.value', signUpType.value)
})

function handleSubmit(formData) {
  // 유효성 검사 후 전송
  console.log('회원가입 제출 데이터:', formData)
  // formData로 API 호출 처리
}
</script>

<style scoped>
select.form-control,
select.form-control-lg {
  line-height: 1.6;
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
  height: auto;
}
</style>
