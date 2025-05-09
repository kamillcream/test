<template>
  <div class="calendar-container">
    <label>날짜 선택 :</label>
    <DatePicker
      v-model="dateRange"
      type="daterange"
      format="YYYY-MM-DD"
      range-separator=" ~ "
      start-placeholder="시작일"
      end-placeholder="종료일"
      :editable="false"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import DatePicker from 'vue-datepicker-next'
import 'vue-datepicker-next/index.css'

const dateRange = ref([])
</script>

<style scoped>
.calendar-container {
  border: 1px dotted #aaa;
  width: 320px;
  padding: 10px;
  font-family: sans-serif;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.calendar-grid {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 5px;
}

.day-cell {
  padding: 5px;
  text-align: center;
  border-radius: 50%;
  cursor: pointer;
}

.day-cell.today {
  border: 1px solid #000;
}

.day-cell.selected {
  background-color: #1abc9c;
  color: white;
}

.section-label {
  margin-top: 10px;
  font-weight: bold;
}

.time-row {
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
  margin: 5px 0;
}

.time-button {
  padding: 5px 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  cursor: pointer;
}

.time-button.selected {
  background-color: #1abc9c;
  color: white;
}

.actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 10px;
}

button.confirm {
  background-color: #1abc9c;
  color: white;
  padding: 5px 10px;
}

button.cancel {
  background-color: #3498db;
  color: white;
  padding: 5px 10px;
}
</style>
