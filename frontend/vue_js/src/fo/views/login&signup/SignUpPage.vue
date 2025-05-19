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
import { api } from '@/axios'
import { useAlertStore } from '@/fo/stores/alertStore'
import { useCompanyProfileStore } from '@/fo/stores/companyProfileStore'

const route = useRoute()
const router = useRouter()
const alertStore = useAlertStore()
const signUpType = ref('')
const companyProfileStore = useCompanyProfileStore()

onMounted(() => {
  const type = route.query.loginType
  if (type === 'PERSONAL' || type === 'COMPANY') {
    signUpType.value = type

    router.replace({ path: route.path })
  }
  console.log('signUpType.value', signUpType.value)
})

async function handleSubmit(rawFormData) {
  // 백엔드 DTO에 맞게 키를 변환
  const formData = {
    userId: rawFormData.id,
    userPw: rawFormData.password,
    userNm: rawFormData.name,
    userGenderCd: Number(rawFormData.gender),
    userPhoneNum: rawFormData.phone,
    userEmail: `${rawFormData.emailId}@${rawFormData.emailDomain}`,
    userAgreedPrivacyPolicyYn: rawFormData.terms ? 'Y' : 'N',
    userBirthDt: rawFormData.dob,
    userTypeCd: Number(rawFormData.typeCode),
    userSignupTypeCd: Number(rawFormData.signupTypeCode),
    zonecode: rawFormData.postcode,
    address: rawFormData.address,
    detailAddress: rawFormData.detailAddress || rawFormData.addressDetail,
    sigungu: rawFormData.sigungu,
    latitude: Number(rawFormData.latitude),
    longitude: Number(rawFormData.longitude),

    // 기업 관련 추가
    companyNm: rawFormData.companyName,
    companyCeoNm: rawFormData.companyCeoName,
    companyOpenDt: rawFormData.companyOpenDate,
    companyBizNum: rawFormData.companyBizNumber,
  }
  console.log('formData', formData)
  try {
    await api.$post('/signup', formData)
    alertStore.show('회원가입이 완료되었습니다!', 'info')
    companyProfileStore.resetProfile() // ✅ 스토어 초기화
    router.push('/login')
  } catch (err) {
    // 서버에서 온 에러 메시지
    const errorMessage =
      err.response?.data?.message || '회원가입에 실패하였습니다'
    alertStore.show(errorMessage, 'danger')
  }
}
</script>
