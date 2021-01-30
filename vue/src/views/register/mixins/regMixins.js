export const reg = {
  data() {
    return {
      // 경고문구
      nameError: "한글,영문만 입력해주세요.",
      nickError: "한글,영문,숫자만 입력해주세요.",
      emailIdError: "이메일 ID를 입력해주세요.",
      emailInputError: "메일을 선택 혹은 입력해주세요.",
      emailDotError: "xxx.xxx 형식으로 입력해주세요.",
      passwordError:
        "영문, 숫자, 특수문자 각 1자리 이상을 사용하여 8자리 이상을 입력해주세요.",
      passwordConfirmError: "동일한 비밀번호를 입력해주세요.",
      birthDateError: "생년월일을 선택해주세요.",
      phoneNumberError: "숫자만 입력 가능합니다.",
      dupError: "중복확인을 해주세요.",
      typeError: "업종을 선택해주세요.",
      businessNumberError: "숫자 10자리를 입력해주세요.",
      phoneNumberFormError: "연락처 형식이 잘못되었습니다.",
      acceptTermsError: "필수 동의 항목을 모두 동의해주시기 바랍니다.",

      errStr: "",
      dupCheck: false,
    }
  },
  methods: {
    checkNull(val) {
      if (val === "" || val === null || val === undefined) {
        return true
      }
      return false
    },
    checkLen(val, min, max) {
      if (val.length < min || val.length > max) {
        return `${min} - ${max}자로 입력해주세요.`
      }

      return true
    },
    checkName(val) {
      if (
        this.checkNull(val) === true ||
        /^[ㄱ-ㅎ|가-힣|a-z|A-Z|*]+$/.test(val) === false
      ) {
        return this.nameError
      }

      return this.checkLen(val, 2, 12)
    },
    checkNick(val, dupConfirm) {
      if (
        this.checkNull(val) === true ||
        /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|*]+$/.test(val) === false
      ) {
        dupConfirm.err = false
        return this.nickError
      }

      const err = this.checkLen(val, 2, 12)

      if (err != true) {
        dupConfirm.err = false
        return err
      }

      dupConfirm.err = true
      return true
    },
    checkEmailId(val, dupConfirm) {
      if (this.checkNull(val) === true) {
        dupConfirm.idErr = false
        dupConfirm.err = false

        return this.emailIdError
      }

      const err = this.checkLen(val, 1, 24)

      if (err != true) {
        dupConfirm.idErr = false
        dupConfirm.err = false

        return err
      }

      dupConfirm.idErr = true
      dupConfirm.err = dupConfirm.idErr && dupConfirm.dotErr

      return true
    },
    checkEmailDot(val, dupConfirm, init) {
      if (init === false) {
        return true
      }

      if (this.checkNull(val) === true || /.+\../.test(val) === false) {
        dupConfirm.dotErr = false
        dupConfirm.err = false

        return this.emailDotError
      }

      const err = this.checkLen(val, 2, 24)

      if (err != true) {
        dupConfirm.dotErr = false
        dupConfirm.err = false

        return err
      }

      dupConfirm.dotErr = true
      dupConfirm.err = dupConfirm.idErr && dupConfirm.dotErr

      return true
    },
    checkPassword(val) {
      if (
        this.checkNull(val) === true ||
        /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9])/.test(val) === false
      ) {
        return this.passwordError
      }

      return this.checkLen(val, 8, 16)
    },
    checkPasswordConfirm(val, pw, confirm) {
      if (this.checkNull(val) === true) {
        return this.passwordConfirmError
      }
      if (val === pw) {
        confirm.check = true
      } else {
        confirm.check = false
        return this.passwordConfirmError
      }

      return true
    },
    checkbirthDate(val) {
      if (this.checkNull(val) === true) {
        return this.birthDateError
      }

      return true
    },
    checkPhoneNumber(val) {
      if (this.checkNull(val) === true) {
        return this.phoneNumberError
      }

      return this.phoneFomatter(val)
    },
    phoneFomatter(val) {
      if (/[^(0-9)]/gi.test(val)) {
        val = val.replace(/[^(0-9)]/gi, "")
      }

      var formatNum = ""

      if (val.length == 11) {
        formatNum = val.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3")
      } else if (val.length == 8) {
        formatNum = val.replace(/(\d{4})(\d{4})/, "$1-$2")
      } else {
        if (val.indexOf("02") === 0) {
          if (val.length === 9) {
            formatNum = val.replace(/(\d{2})(\d{3})(\d{4})/, "$1-$2-$3")
          } else {
            formatNum = val.replace(/(\d{2})(\d{4})(\d{4})/, "$1-$2-$3")
          }
        } else {
          formatNum = val.replace(/(\d{3})(\d{3})(\d{4})/, "$1-$2-$3")
        }
      }

      this.info.phoneNumber = formatNum

      if (this.info.phoneNumber.indexOf("-") === -1) {
        return this.phoneNumberFormError
      }

      return this.checkLen(val, 8, 11)
    },
    checkCompanyName(val, dupConfirm) {
      if (
        this.checkNull(val) === true ||
        /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|*]+$/.test(val) === false
      ) {
        dupConfirm.nameErr = false
        dupConfirm.err = false

        return this.nameError
      }

      const err = this.checkLen(val, 2, 12)

      if (err != true) {
        dupConfirm.nameErr = false
        dupConfirm.err = false

        return err
      }

      dupConfirm.nameErr = true
      dupConfirm.err = dupConfirm.nameErr && dupConfirm.placeErr

      return true
    },

    checkPlace(val, dupConfirm) {
      if (
        this.checkNull(val) === true ||
        /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|*]+$/.test(val) === false
      ) {
        dupConfirm.placeErr = false
        dupConfirm.err = false

        return this.nameError
      }

      const err = this.checkLen(val, 2, 12)

      if (err != true) {
        dupConfirm.placeErr = false
        dupConfirm.err = false

        return err
      }

      dupConfirm.placeErr = true
      dupConfirm.err = dupConfirm.nameErr && dupConfirm.placeErr

      return true
    },

    checkType(val) {
      if (this.checkNull(val) === true) {
        return this.typeError
      }

      return true
    },
    checkBusinessNumber(val) {
      if (this.checkNull(val) === true || val.length > 12 || val.length < 12) {
        this.dupConfirmNum.err = false

        return this.businessNumberError
      }

      this.dupConfirmNum.err = true

      return true
    },
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
          formData.companyName = val
          break
        case "사업자등록번호":
          formData.businessNumber = val.replace(/-/gi, "")
          break
        default:
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
      }

      if (err != "") {
        this.errStr = err
        this.dupCheck = true
        return false
      }

      return true
    },
  },
}
