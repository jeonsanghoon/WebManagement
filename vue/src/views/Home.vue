<template lang="pug">
  v-container
    v-row(align="start" justify="start")
      v-col
        Vuetify-menutitle
    v-row
      v-col
        h1 온닫이에 오신 것을 환영합니다.
        h2(class="pt-5") 대시보드로 사용될 페이지입니다.
        //Kakaomap-category(v-model="areaCode")
        //Buttons-radioGroup 
  
</template>
<style lang="scss"></style>

<script>
// @ is an alias to /src

export default {
  components: {},
  name: "home",
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({ el: this.$el, isTop: true })
    console.log("moment >> ", this.$moment(new Date()).format("YYYYMMDD"))
  },
  watch: {
    daterange(val) {
      console.log(val)
    },
  },
  data() {
    return {
      areaCode: "02|10|16" /** 초기화코드 */,
    }
  },
  methods: {},
}
</script>
