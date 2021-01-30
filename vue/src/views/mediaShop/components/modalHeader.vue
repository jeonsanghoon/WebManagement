<template>
  <v-container class="pa-0">
    <div class="header-wrap d-flex align-center">
      <span class="ondaji_font_14_bold_brown_100">
        <slot>
          타이틀
        </slot>
      </span>
      <v-btn
        class="ml-auto"
        text
        icon
        color="ondaji_color_grey_70"
        x-small
        @click="close"
      >
        <v-icon>
          fas fa-times
        </v-icon>
      </v-btn>
    </div>
    <div class="line-wrap" />
  </v-container>
</template>

<script>
export default {
  methods: {
    close() {
      this.$emit('close')
    },
  },
}
</script>

<style lang="scss" scoped>
.header-wrap {
  background: white;
  height: 45px;
  padding: 0 22px;
}

.line-wrap {
  border: 1px solid #e0e0e0;
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .header-wrap {
    padding: 0 4px;
  }
}
</style>
