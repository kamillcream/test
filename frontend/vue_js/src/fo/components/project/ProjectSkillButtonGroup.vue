<template>
  <div class="d-flex gap-2 flex-wrap">
    <div
      v-for="skill in selectedSkills"
      :key="skill.name"
      class="btn btn-rounded btn-light d-flex align-items-center gap-2 mb-2 btn-3d position-relative"
      style="padding-right: 24px"
    >
      <img
        :src="generateIconUrl(skill.name || skill)"
        :alt="skill.name || skill"
        width="20"
        height="20"
      />
      <span>{{ skill.name || skill }}</span>
      <a
        href="#"
        @click.prevent="deleteSkill(skill.name || skill)"
        class="position-absolute end-0 me-2 text-grey text-decoration-none"
        style="top: 50%; transform: translateY(-50%)"
        title="삭제"
      >
        ×
      </a>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    selectedSkills: {
      type: Array,
      required: true,
    },
  },
  emits: ['remove'],
  methods: {
    deleteSkill(name) {
      this.$emit('remove', name)
    },
    generateIconUrl(name) {
      const exceptionList = [
        '전자정부 프레임워크',
        'myBatis',
        'Notepad++',
        'PyCharm',
        'Sublime Text',
      ]
      if (exceptionList.includes(name)) return null

      const processed = name
        .toLowerCase()
        .replace('#', 'sharp')
        .replace('++', 'plusplus')

      return `https://cdn.jsdelivr.net/gh/devicons/devicon/icons/${processed}/${processed}-original.svg`
    },
  },
}
</script>
