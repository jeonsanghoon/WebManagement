export const dupCheck = {
  data() {
    return {
      dupError: "중복확인을 해주세요.",
      errStr: "",
      dupCheck: false,
    }
  },
  methods: {
    dupConfirm(url, dupConfirm, val) {
      let formData = this.getDupConfirmForm(dupConfirm, val)

      this.$BaseCommon.restful.call2(url, formData).then((res) => {
        this.resDupConfirm(dupConfirm, res)
      })
    },
    getDupConfirmForm(dupConfirm, val) {
      let formData = {}

      switch (dupConfirm.name) {
        case "닉네임":
          formData.nickname = val
          break
        case "아이디":
          formData.memberId = val
          break
        case "로컬사업자명":
          formData.companyName = val[0]
          formData.branchName = val[1]
          break
        case "사업자등록번호":
          formData.businessNumber = val.replace(/-/gi, "")
          break
      }

      return formData
    },
    resDupConfirm(dupConfirm, res) {
      if (1 === res) {
        this.errStr = `이미 사용중인 ${dupConfirm.name}입니다.`
        this.dupCheck = true

        dupConfirm.dupCheck = false
      } else {
        this.errStr = `사용 가능한 ${dupConfirm.name}입니다.`
        this.dupCheck = true

        dupConfirm.dupCheck = true
      }
    },
    checkDup(arr) {
      if (this.login === true) {
        return true
      }

      let err = ""

      arr.forEach((e) => {
        if (err === "" && e.dupCheck === false) {
          err += `${e.name}`
        } else if (e.dupCheck === false) {
          err += `, ${e.name}`
        }
      })

      if (err != "") {
        err += ` ${this.dupError}`

        this.errStr = err
        this.dupCheck = true

        return false
      }
      return true
    },
    setDupCheck(val, check) {
      val.dupCheck = check
    },
  },
}
