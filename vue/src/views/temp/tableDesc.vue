<template>
  <v-container fill-height class="ondijiBackground fullContent">
    <v-row
      justify="center"
      align="center"
      style="margin-left:0px;margin-right:0px;"
    >
      <v-col cols="12" class="pa-0" justify="center" align="center">
        <div v-html="this.loading"></div>
        <div v-html="this.html"></div>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
export default {
  data() {
    return {
      html: "",
      loading: "약 5초 기다리세요~",
    }
  },
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({
      el: this.$el,
      isBar: false,
      isTop: false,
    })
  },
  created() {
    this.$BaseCommon.restful.call2("/common/getTableDesc").then((res) => {
      this.html = res
      this.loading = ""
    })
  },
}
</script>
