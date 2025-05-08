<template>
  <section class="page-header page-header-modern bg-color-grey page-header-sm">
    <div class="container">
      <div class="row">
        <div class="col-md-8 order-2 order-md-1 align-self-center p-static">
          <h1 class="text-dark">
            {{ title }}
            <strong>{{ strongText }}</strong>
          </h1>
        </div>
        <div class="col-md-4 order-1 order-md-2 align-self-center">
          <ul class="breadcrumb d-block text-md-end">
            <li v-for="(crumb, index) in breadcrumbs" :key="index">
              <template v-if="crumb.link">
                <a :href="crumb.link">{{ crumb.text }}</a>
              </template>
              <template v-else>
                <span class="active">{{ crumb.text }}</span>
              </template>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { defineProps } from 'vue'

defineProps({
  title: {
    type: String,
    default: 'Modern',
  },
  strongText: {
    type: String,
    default: 'Small',
  },
  breadcrumbs: {
    type: Array,
    default: () => [{ text: 'Home', link: '#' }, { text: 'Features' }],
  },
})
</script>

<style scoped>
.page-header {
  width: 100%;
}
</style>
