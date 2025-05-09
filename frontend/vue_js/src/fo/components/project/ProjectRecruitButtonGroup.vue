<template lang="">
    <div class="d-flex gap-2 flex-wrap">
        <div
            v-for="job in selectedJobs"
            :key="job.id"
            class="btn btn-rounded btn-light d-flex align-items-center gap-2 mb-2 btn-3d position-relative"
            style="padding-right: 24px;"
        >
            <span>{{ job.name }}</span>
            <a
                href="#"
                @click.prevent="deleteJob(job.id)"
                class="position-absolute end-0 me-2 text-grey text-decoration-none"
                style="top: 50%; transform: translateY(-50%);"
                title="삭제"
            >×</a>
        </div>
    </div>
</template>
<script>

export default {
  props: {
    selectedJobs: {
      type: Array,
      required: true
    }
  },
  emits: ['remove'],
  methods: {
    deleteJob(id) {
      this.$emit('remove', id)
    }
  }
}
</script>