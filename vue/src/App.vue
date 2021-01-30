<template>
  <div>
    <userLayout v-if="layoutMode === 'user'" />
    <localLayout v-else />
  </div>
</template>
<style scoped>
.routerview {
  max-width: 100%;
  margin-left: 0px;
  margin-right: 0px;
  top: 200px;
  background-color: #fafafa;
}
.maincontent {
  background-color: #fafafa;
}
</style>
<style lang="scss">
@import "@/assets/styles/Common.scss";
</style>

<script>
import localLayout from "@/components/layouts/local"
import userLayout from "@/components/layouts/user"
import { BaseCommon } from "@/assets/scripts/Common"
export default {
  components: {
    localLayout,
    userLayout,
  },
  created() {
    this.$BaseCommon.hostInfo.viewPortInit()
  },
  methods: {
    resize() {
      let viewport = document.querySelector("meta[name=viewport]")
      let winWidth = window.outerWidth
      console.log("윈도우 위드 >>>>> ", window.outerWidth)
      if (winWidth > 1200) viewport.setAttribute("content", "width=1920")
      else if (winWidth > 600) viewport.setAttribute("content", "width=800")
      else viewport.setAttribute("content", "width=360")
    },
  },
  data: () => ({
    layoutMode: "user" /** local,user */,
  }),
  mounted() {
    this.$store.watch(() => {
      this.layoutMode = BaseCommon.settingInfo.getLayoutMode()
    })
  },
}
</script>
