<template>
  <div>
    <div class="daterangepicker ltr show-calendar openscenter">
      <div class="calendar-wrapper">
        <div class="drp-calendar left">
          <div class="calendar-table">
            <table class="table-condensed">
              <thead>
                <tr>
                  <th class="prev available" @click="prevMonth">
                    <span>&lt;</span>
                  </th>
                  <th colspan="5" class="month">
                    {{ getMonthYear(leftMonth) }}
                  </th>
                  <th></th>
                </tr>
                <tr>
                  <th>일</th>
                  <th>월</th>
                  <th>화</th>
                  <th>수</th>
                  <th>목</th>
                  <th>금</th>
                  <th>토</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(week, rowIndex) in leftCalendar"
                  :key="'left-' + rowIndex"
                >
                  <td
                    v-for="(day, colIndex) in week"
                    :key="'left-' + colIndex + '-' + rowIndex"
                    :class="dayClass(day.date, leftMonth.month)"
                    @click="selectDate(day.date)"
                  >
                    {{ day.label }}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <div class="drp-calendar right">
          <div class="calendar-table">
            <table class="table-condensed">
              <thead>
                <tr>
                  <th></th>
                  <th colspan="5" class="month">
                    {{ getMonthYear(rightMonth) }}
                  </th>
                  <th class="next available" @click="nextMonth">
                    <span>&gt;</span>
                  </th>
                </tr>
                <tr>
                  <th>일</th>
                  <th>월</th>
                  <th>화</th>
                  <th>수</th>
                  <th>목</th>
                  <th>금</th>
                  <th>토</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(week, rowIndex) in rightCalendar"
                  :key="'right-' + rowIndex"
                >
                  <td
                    v-for="(day, colIndex) in week"
                    :key="'right-' + colIndex + '-' + rowIndex"
                    :class="dayClass(day.date, rightMonth.month)"
                    @click="selectDate(day.date)"
                  >
                    {{ day.label }}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
    <div class="time-picker-wrapper">
      <h6 class="time-picker-title">시간 선택 (30분 단위)</h6>
      <p v-if="!selectedDate" class="time-warning">날짜를 먼저 선택해주세요.</p>
      <div class="time-grid">
        <button
          v-for="time in timeOptions"
          :key="time"
          :class="['time-slot', { selected: isSelectedTime(time) }]"
          :disabled="!selectedDate"
          @click="toggleTime(time)"
        >
          {{ time }}
        </button>
      </div>
    </div>
    <div class="drp-buttons">
      <span class="drp-selected">{{ formattedRange }}</span>
      <button
        class="cancelBtn btn btn-sm btn-default"
        type="button"
        @click="useModalStore().closeModal()"
      >
        취소
      </button>
      <button
        class="applyBtn btn btn-sm btn-primary"
        type="button"
        @click="confirmSelection"
      >
        적용
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, defineEmits, defineProps } from 'vue'
import { useModalStore } from '../../stores/modalStore.js'

const props = defineProps({
  onConfirm: Function,
  interviewTimes: {
    type: Array,
    default: () => [],
  },
})

const selectedDate = ref(null)
const selectedTimes = ref({})

props.interviewTimes.forEach(({ date, times }) => {
  selectedTimes.value[date] = [...times]
})

const leftMonth = ref({ month: 4, year: 2025 })
const rightMonth = ref({ month: 5, year: 2025 })

console.log(selectedTimes.value)

const emit = defineEmits(['confirm'])

const timeOptions = Array.from({ length: 19 }, (_, i) => {
  const hour = 9 + Math.floor(i / 2) // 9시부터 시작
  const minute = i % 2 === 0 ? '00' : '30'
  return `${String(hour).padStart(2, '0')}:${minute}`
})

const isSelectedTime = (time) => {
  const date = selectedDate.value
  return selectedTimes.value[date]?.includes(time)
}

const toggleTime = (time) => {
  const date = selectedDate.value
  if (!selectedTimes.value[date]) {
    selectedTimes.value[date] = []
  }
  const idx = selectedTimes.value[date].indexOf(time)
  if (idx === -1) {
    selectedTimes.value[date].push(time)
  } else {
    selectedTimes.value[date].splice(idx, 1)
  }
}

function dayClass(d, currentMonth) {
  const dateString = formatDate(d)
  const isSameMonth = d.getMonth() === currentMonth
  const isPast = d < new Date().setHours(0, 0, 0, 0)

  return {
    available: !isPast,
    selected: dateString === selectedDate.value && isSameMonth && !isPast,
    'has-times':
      !!selectedTimes.value[dateString]?.length && isSameMonth && !isPast,
    disabled: isPast,
  }
}

function formatDate(d) {
  const date = new Date(d)
  return `${date.getFullYear()}-${(date.getMonth() + 1)
    .toString()
    .padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`
}

function selectDate(date) {
  const today = new Date()
  today.setHours(0, 0, 0, 0)
  if (date < today) return // 지난 날짜는 클릭 무시

  const formatted = formatDate(date)

  if (selectedDate.value === formatted) {
    selectedDate.value = null
  } else {
    selectedDate.value = formatted
  }
}

function getMonthYear(obj) {
  return `${obj.month + 1}월 ${obj.year}`
}

function generateCalendar(month, year) {
  const weeks = []
  const firstDay = new Date(year, month, 1).getDay()
  let current = 1 - firstDay
  for (let w = 0; w < 6; w++) {
    const week = []
    for (let d = 0; d < 7; d++) {
      const date = new Date(year, month, current)
      const isSameMonth = date.getMonth() === month
      week.push({ date, label: isSameMonth ? date.getDate() : '' })
      current++
    }
    weeks.push(week)
  }
  return weeks
}

const leftCalendar = computed(() =>
  generateCalendar(leftMonth.value.month, leftMonth.value.year),
)
const rightCalendar = computed(() =>
  generateCalendar(rightMonth.value.month, rightMonth.value.year),
)

function prevMonth() {
  if (leftMonth.value.month === 0) {
    leftMonth.value.month = 11
    leftMonth.value.year--
  } else {
    leftMonth.value.month--
  }
  rightMonth.value.month = leftMonth.value.month + 1
  rightMonth.value.year = leftMonth.value.year
  if (rightMonth.value.month > 11) {
    rightMonth.value.month = 0
    rightMonth.value.year++
  }
}

function nextMonth() {
  if (rightMonth.value.month === 11) {
    rightMonth.value.month = 0
    rightMonth.value.year++
  } else {
    rightMonth.value.month++
  }
  leftMonth.value.month = rightMonth.value.month - 1
  leftMonth.value.year = rightMonth.value.year
  if (leftMonth.value.month < 0) {
    leftMonth.value.month = 11
    leftMonth.value.year--
  }
}

const confirmSelection = () => {
  const result = Object.entries(selectedTimes.value).map(([date, times]) => ({
    date,
    times,
  }))
  emit('confirm', result)
  useModalStore().closeModal()
}
</script>
<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 60px;
  padding-bottom: 60px;
}

.daterangepicker {
  display: flex;
  flex-direction: column;
  gap: 20px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 6px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 30px 40px;
  width: fit-content;
}

.calendar-wrapper {
  display: flex;
  flex-direction: row;
  gap: 20px;
  justify-content: center;
}

.drp-calendar {
  flex: 1;
}

.calendar-table {
  width: 100%;
  text-align: center;
}

.calendar-table th,
.calendar-table td {
  width: 40px;
  height: 40px;
  line-height: 40px;
  text-align: center;
  border-radius: 0;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.calendar-table td.available:hover {
  background-color: #e0f7fa;
}

.calendar-table td.start-date,
.calendar-table td.end-date {
  background-color: #00acc1;
  color: white;
  font-weight: bold;
}

.calendar-table td.in-range {
  background-color: #b2ebf2;
}

.calendar-table td.selected {
  border: 2px solid #00acc1;
}

.drp-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  padding: 30px 40px;
  border-top: 1px solid #ddd;
}

.drp-selected {
  flex: 1;
  align-self: center;
  font-weight: bold;
  text-align: left;
  padding-left: 5px;
}
.drp-selected::before {
  font-weight: normal;
  color: #555;
  margin-right: 4px;
}

td.disabled {
  background-color: #f0f0f0;
  color: #aaa;
  cursor: not-allowed;
  pointer-events: none;
}

td.selected {
  background-color: #0088cc;
  color: white;
  font-weight: bold;
  border-radius: 0;
}

td.has-times:not(.selected) {
  background-color: #e6f0ff;
  font-weight: 600;
}

.time-picker-wrapper {
  padding: 0 40px 30px;
  border-top: 1px solid #ddd;
}

.time-picker-title {
  margin: 12px 0;
  font-size: 14px;
  font-weight: bold;
  color: #555;
}

.time-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.time-slot {
  padding: 6px 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  background: white;
  font-size: 13px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.time-slot.selected {
  background-color: #0088cc;
  color: white;
  border-color: #0088cc;
}

.time-slot:disabled {
  background-color: #f5f5f5;
  color: #bbb;
  border-color: #ddd;
  cursor: not-allowed;
  pointer-events: none;
}

.time-warning {
  color: #d9534f;
  font-size: 13px;
  margin-bottom: 10px;
}
</style>
