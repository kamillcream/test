import router from './index'

export function navigateByUserTypeAndProjectSq(userType, projectSq) {
  console.log(userType)
  if (userType === 'PERSONAL') {
    router.push(`/project/spec/user/${projectSq}`)
  } else if (userType === 'COMPANY') {
    router.push(`/project/spec/company/${projectSq}`)
  } else {
    router.push('/login')
  }
}

export function navigateCompanyPageWithProjectSq(userType, projectSq) {
  if (userType === 'PERSONAL') {
    router.push('/')
  } else if (userType === 'COMPANY') {
    router.push(`/project/spec/company/${projectSq}`)
  } else {
    router.push('/login')
  }
}

export default router
