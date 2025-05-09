<template>
  <div class="row mb-3">
    <div class="col">
      <h2 class="font-weight-normal text-7 mb-0 text-start">
        {{ props.text }}
      </h2>
      <hr class="mt-2 mb-0" />
    </div>
  </div>
</template>
<script setup>
import { defineProps } from 'vue'

const props = defineProps({ text: String })
</script>
<style></style>
