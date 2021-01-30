export const commonBanner = {
  data() {
    return {}
  },
  methods: {
    checkNull(val) {
      if (val === `` || val === undefined || val === null) {
        return true
      }

      return false
    },
    getScreenSize() {
      const screen = this.$vuetify.breakpoint.name

      if (screen === "lg" || screen === "xl") {
        return "lg"
      } else if (screen === "sm" || screen === "md") {
        return "sm"
      } else {
        return "xs"
      }
    },
    selectBannerCheck(banner) {
      if (this.checkNull(banner) === false) {
        return banner.bannerStoreSelectCheck
      }

      return false
    },
    checkRes(res) {
      if (
        this.checkNull(res) === false &&
        res.resultType.toLowerCase() === "info"
      ) {
        return true
      }

      return false
    },
  },
}
