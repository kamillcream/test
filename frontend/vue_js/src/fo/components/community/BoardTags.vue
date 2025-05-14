<template>
  <div
    ref="containerRef"
    class="mt-2 d-flex justify-content-start flex-wrap gap-1"
  >
    <!-- 태그 -->
    <span
      v-for="tagInfo in visibleTags"
      :key="tagInfo.tag_nm"
      ref="tagRefs"
      class="btn btn-primary btn-rounded btn-3d py-0 px-2 tag-btn"
      :class="tagInfo.type == 'skill' ? ' btn-primary' : 'btn-light'"
      >{{ tagInfo.tag_nm }}</span
    >
    <span
      v-if="hiddenCount > 0"
      class="more-tag btn btn-light btn-rounded btn-3d py-0 px-2 tag-btn"
      >+{{ hiddenCount }}</span
    >
  </div>
</template>
<script setup>
import { nextTick, ref, defineProps, onMounted, watch } from 'vue'

const props = defineProps({
  skillTags: {
    type: Array,
    required: true,
    default: () => [],
  },
  normalTags: {
    type: Array,
    required: true,
    default: () => [],
  },
})

const totalTags = ref([])
const containerRef = ref(null)
const tagRefs = ref([])
const visibleTags = ref([])
const hiddenCount = ref(0)

const calculateVisibleTags = async () => {
  await nextTick()

  console.log(containerRef.value?.offsetWidth)
  const containerWidth = containerRef.value?.offsetWidth || 0
  let totalWidth = 0
  let count = 0
  console.log(tagRefs)

  for (let i = 0; i < totalTags.value.length; i++) {
    const tagEl = tagRefs.value[i]
    if (!tagEl) continue

    totalWidth += tagEl.offsetWidth + 8 // margin or spacing
    if (totalWidth > containerWidth) break
    count++
  }

  console.log(count)
  visibleTags.value = totalTags.value.slice(0, count)
  console.log('visibleTags', visibleTags.value)
  hiddenCount.value = totalTags.value.length - count
}

onMounted(() => {
  console.log(props.skillTags, props.normalTags)
  props.skillTags.forEach((el) => {
    totalTags.value.push({ type: 'skill', tag_nm: el })
  })
  props.normalTags.forEach((el) => {
    totalTags.value.push({ type: 'normal', tag_nm: el })
  })
  visibleTags.value = totalTags.value
  window.addEventListener('resize', calculateVisibleTags)
  calculateVisibleTags()
})

watch(() => props.normalTags, calculateVisibleTags)
</script>
<style>
.tag-btn {
  font-size: 12px;
}
</style>
