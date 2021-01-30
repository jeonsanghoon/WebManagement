export const commonMediaShop = {
  data() {
    return {
      PAGE_TOTAL_NUM: 5,

      FILTER_TYPE: {
        BANNER_TITLE: 0,
        LOCAL_NAME: 1,
        COMPANY_TYPE: 2,
      },

      MENU_NUM: 6,

      ORDER_BTN_TYPE: {
        TOP: 0,
        UP: 1,
        DOWN: 2,
        BOTTOM: 3,
      },
    }
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
    checkRes(res) {
      if (
        this.checkNull(res) === false &&
        res.resultType.toLowerCase() === "info"
      ) {
        return true
      }

      return false
    },
    initFormData(startNo = 0) {
      let formData = {}

      formData.memberCode = this.$BaseCommon.memberInfo.getMember().member.memberCode
      formData.pageStartNo = startNo
      formData.pageInDataNum = this.PAGE_TOTAL_NUM

      return formData
    },
    checkObjectEmpty(obj) {
      if ((Object.keys(obj).length === 0) === false) {
        return true
      }

      return false
    },
    reqLocalBoxCode() {
      let formData = {}

      formData.memberCode = this.$BaseCommon.memberInfo.getMember().member.memberCode

      this.$BaseCommon.restful
        .call2("/mediashop/localbox/getlocalboxcode", formData)
        .then((res) => {
          if (this.checkNull(res) === false) {
            if (this.$router.currentRoute.name != "localboxbanner") {
              this.$router.push({
                name: "localboxbanner",
                params: { call: true },
              })
            }
          } else {
            if (this.$router.currentRoute.name != "localbox") {
              this.$router.push({ name: "localbox", params: { call: true } })
            }
          }
        })
        .catch((res) => {
          console.log("/mediashop/localbox/getlocalboxcode >> ", res)
        })
    },
  },
}
