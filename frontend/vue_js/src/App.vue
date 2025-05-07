<template>
  <div>
    <HeaderBeforeLogin />
    <HelloWorld msg="임시 메인 페이지" />
    <div class="background-container">
      <img alt="Vue logo" src="./assets/logo.png" />
    </div>
    <HelloWorld msg="Freelancer_servcice" />
  </div>
</template>

<script>
import HeaderBeforeLogin from './fo/components/common/HeaderBeforeLogin.vue'
import HelloWorld from './fo/components/common/HelloWorld.vue'

export default {
  name: 'App',
  components: {
    HeaderBeforeLogin,
    HelloWorld,
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
}

/* 회색 배경 */
.background-container {
  background-color: #d3d3d3; /* 회색 배경 */
  padding: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.background-container img {
  max-width: 100%;
  height: auto;
}
</style>
