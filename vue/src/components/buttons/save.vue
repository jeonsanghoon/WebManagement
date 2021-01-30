<template lang="pug">
    v-btn(type="button" class="saveButton" @click="click" @keyup.enter="enter") {{ text }}
</template>
<style scoped>
.saveButton {
  background-color: #6c5d59 !important;
  color: #ffffff !important;
  font-weight: bold !important;
  cursor: pointer;
}
.saveButton:hover {
  background-color: #4e342e !important;
}
</style>
<script>
export default {
  props: {
    text: {
      type: String,
      default: "저장하기",
    },
  },
  methods: {
    click() {
      this.$emit("callback", true)
    },
    enter() {
      this.$emit("keyup.enter", true)
    },
  },
}
</script>
