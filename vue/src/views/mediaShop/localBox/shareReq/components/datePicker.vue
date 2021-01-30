<template>
  <div class="picker-wrap">
    <date-picker
      ref="datePicker"
      class="date-picker"
      mode="range"
      :value="date"
    />

    <v-icon v-if="clickCheck === false" class="calendar-icon" size="14">
      far fa-calendar-alt
    </v-icon>

    <v-img
      v-else
      class="close-icon"
      contain
      width="14"
      height="14"
      src="@/assets/images/icons/close_round.svg"
      @click="clearDate"
    />
  </div>
</template>

<script>
export default {
  data() {
    return {
      date: {
        start: '',
        end: '',
      },
      clickCheck: false,
    }
  },
  methods: {
    clearDate() {
      this.date.start = ''
      this.date.end = ''
      this.$refs.datePicker.$el.querySelector('input').value = ''
      this.clickCheck = false
    },
  },
}
</script>

<style lang="scss" scoped>
.picker-wrap {
  position: relative;

  .calendar-icon,
  .close-icon {
    position: absolute;
    top: 12px;
    right: 12px;
  }
}
</style>
