<template>
  <div class="d-flex gap-2 flex-wrap">
    <div
      v-for="interviewTime in interviewTimes"
      :key="interviewTime.date"
      class="btn btn-rounded btn-light d-flex align-items-center gap-2 mb-2 btn-3d position-relative"
      style="padding-right: 24px"
    >
      <span>{{ formatDate(interviewTime.date) }}</span>

      <span
        class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-primary"
        style="font-size: 0.7rem"
      >
        +{{ interviewTime.timeSlots.length }}
      </span>

      <a
        href="#"
        @click.prevent="deleteTime(interviewTime.date)"
        class="position-absolute end-0 me-2 text-grey text-decoration-none"
        style="top: 50%; transform: translateY(-50%)"
        title="삭제"
        >×</a
      >
    </div>
  </div>
</template>

<script>
export default {
  props: {
    interviewTimes: {
      type: Array,
      required: true,
    },
  },
  emits: ['remove'],
  methods: {
    deleteTime(date) {
      this.$emit('remove', date)
    },
    formatDate(isoDate) {
      const [y, m, d] = isoDate.split('-')
      return `${y.slice(2)}.${m}.${d}`
    },
  },
}
</script>
