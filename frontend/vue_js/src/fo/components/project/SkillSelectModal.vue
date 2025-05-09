<template lang="">
  <Teleport to="body">
  <div class="modal-backdrop">
    <div class="modal-content-wrapper">
      <div class="modal-header">
        <h5 class="modal-title" id="customModalLabel">기술 선택</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
      </div>
      <div class="modal-body">
        <form id="techForm">
          <h6 class="section-title">언어</h6>
          <div class="row row-cols-3 card-grid mb-3" id="langSection">
            <div class="col" v-for="language in languages" :key="language">
              <button type="button" class="tech-card" :class="{ selected: isSelected(language) }" @click="toggleSkill(language)">
                <img :src="`https://cdn.jsdelivr.net/gh/devicons/devicon/icons/${language.toLowerCase().replace('#','sharp').replace('++','plusplus')}/${language.toLowerCase().replace('#','sharp').replace('++','plusplus')}-original.svg`" :alt="language" v-if="!['전자정부 프레임워크','myBatis','Notepad++','PyCharm','Sublime Text'].includes(language)" />
                <span>{{ language }}</span>
              </button>
            </div>
          </div>

          <h6 class="section-title">프레임워크</h6>
          <div class="row row-cols-3 card-grid mb-3" id="frameworkSection">
            <div class="col" v-for="framework in frameworks" :key="framework">
              <button type="button" class="tech-card" :class="{ selected: isSelected(framework) }" @click="toggleSkill(framework)">
                <img v-if="!['전자정부 프레임워크','myBatis','Ember','BackboneJS'].includes(framework)" :src="`https://cdn.jsdelivr.net/gh/devicons/devicon/icons/${framework.toLowerCase().replaceAll(/\.| /g,'').replace('springboot','spring')}/${framework.toLowerCase().replaceAll(/\.| /g,'').replace('springboot','spring')}-original.svg`" :alt="framework" />
                <span>{{ framework }}</span>
              </button>
            </div>
          </div>

          <h6 class="section-title">툴</h6>
          <div class="row row-cols-3 card-grid mb-3" id="toolSection">
            <div class="col" v-for="tool in tools" :key="tool">
              <button type="button" class="tech-card" :class="{ selected: isSelected(tool) }" @click="toggleSkill(tool)">
                <img v-if="!['Notepad++','PyCharm','Sublime Text'].includes(tool)" :src="`https://cdn.jsdelivr.net/gh/devicons/devicon/icons/${tool.toLowerCase().replace(' ','').replace('++','plusplus')}/${tool.toLowerCase().replace(' ','').replace('++','plusplus')}-original.svg`" :alt="tool" />
                <span>{{ tool }}</span>
              </button>
            </div>
          </div>

          <div class="mt-4 d-flex justify-content-end">
            <button @click="confirmSelection" type="button" class="btn btn-primary">선택 완료</button>
            <button type="button" class="btn btn-secondary ms-2" data-bs-dismiss="modal">닫기</button>
          </div>
        </form>
      </div>
    
    </div>
  </div>
  </Teleport>
</template>

<script>
import { ref } from 'vue'
import { useModalStore } from '../../stores/modalStore.js'

export default {
  name: 'SkillSelectModal',
  emits: ['confirm', 'remove'],
  setup(props, { emit }) {
    const selectedSkills = ref([])

    const languages = ref(['Java','Python','JavaScript','PHP','TypeScript','C','C#','C++','Go'])
    const frameworks = ref(['Spring Boot','Spring','React.js','Vue.js','Angular.js','Express.js','Django','Bootstrap','Ember','BackboneJS','전자정부 프레임워크','myBatis'])
    const tools = ref(['VSCode','IntelliJ','Vim','Android Studio','Eclipse','Visual Studio','Notepad++','PyCharm','Sublime Text'])

    const toggleSkill = (skillName) => {
      const index = selectedSkills.value.findIndex(s => s.name === skillName)
      if (index === -1) {
        selectedSkills.value.push({
          name: skillName,
          imageUrl: generateIconUrl(skillName)
        })
      } else {
        selectedSkills.value.splice(index, 1)
      }
    }

    const generateIconUrl = (name) => {
      const exceptionList = ['전자정부 프레임워크', 'myBatis', 'Notepad++', 'PyCharm', 'Sublime Text']
      if (exceptionList.includes(name)) return null

      const processed = name.toLowerCase().replace('#', 'sharp').replace('++', 'plusplus')
      return `https://cdn.jsdelivr.net/gh/devicons/devicon/icons/${processed}/${processed}-original.svg`
    }

    const isSelected = (skillName) =>
    selectedSkills.value.some(s => s.name === skillName || s === skillName)

    const confirmSelection = () => {
      emit('confirm', selectedSkills.value)
      const modalStore = useModalStore()
      modalStore.closeModal()   
    }

    return {
      selectedSkills,
      languages,
      frameworks,
      tools,
      toggleSkill,
      isSelected,
      confirmSelection
    }
  }
}
</script>
<style scoped>
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}
.modal-content-wrapper {
  background-color: white;
  border-radius: 12px;
  padding: 24px;
  width: 720px;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}
.tech-card {
  width: 100%;
  height: 60px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.2s ease-in-out;
  padding: 0.5rem;
}

.tech-card img {
  width: 20px;
  height: 20px;
}

.tech-card:hover {
  background-color: #d9d9d9;
  border-color: #0d6efd;
}

.tech-card.selected {
  background-color: #d9d9d9 !important;
  border-color: #0d6efd;
  box-shadow: 0 0 0 2px #0d6efd33;
}
</style>