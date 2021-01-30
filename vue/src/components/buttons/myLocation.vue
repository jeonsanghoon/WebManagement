<template lang="pug">
  div
    v-btn(class="mt-2 ml-0 elevation-0 mylocation d-none d-sm-flex" 
          color="ondaji_color_tealgreen_40 ondaji_font_12_medium_white" @click="click")
      v-img(max-width="22px" class="pr-1" src="@/assets/images/map/Location-gps-on-02.svg")
      span(class="pl-1") 내 위치
    v-btn(class="mt-2 ml-0 elevation-0 mylocation2  d-flex d-sm-none"
          color="ondaji_color_tealgreen_40" @click="click")
      v-img(width="18px" height="18px" src="@/assets/images/map/Location-gps-on-02.svg")
</template>
<style lang="scss" scoped>
.mylocation {
  height: 24px !important;
  width: 72px !important;
  padding: 0px !important;
  padding-right: 0.25rem !important;
  &2 {
    padding: 1px 1px !important;
    height: 24px !important;
    width: 24px !important;
    min-width: 20px !important;
  }
}
</style>
<script>
export default {
  methods: {
    click() {
      this.$emit("click")
    },
  },
}
</script>
