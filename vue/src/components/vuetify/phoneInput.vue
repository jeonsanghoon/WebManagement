<template>
  <v-text-field
    :class="info.class"
    label="'-'제외, 숫자만 기입"
    v-model="phoneNumber"
    :rules="[phoneNumberRules]"
    maxlength="13"
    :background-color="info.backgroundColor"
    :color="info.color"
    :filled="info.filled"
    :rounded="info.rounded"
    dense
    single-line
  >
  </v-text-field>
</template>

<script>
export default {
  props: {
    value: String,
    info: { type: Object, required: true },
  },
  created() {
    this.phoneNumber = this.value
  },
  data() {
    return {
      phoneNumber: '',
      phoneNumberError: '숫자만 입력 가능합니다.',
      phoneNumberFormError: '연락처 형식이 잘못되었습니다.',

      checkNull(val) {
        if (val === '' || val === null || val === undefined) {
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

      phoneNumberRules: () => {
        return this.checkPhoneNumber(this.phoneNumber)
      },
    }
  },
  watch: {
    phoneNumber() {
      this.$emit('phoneInputCallback', this.phoneNumber)
    },
  },
  methods: {
    checkPhoneNumber(val) {
      if (this.checkNull(val) === true) {
        return this.phoneNumberError
      }

      return this.phoneFomatter(val)
    },
    phoneFomatter(val) {
      if (/[^(0-9)]/gi.test(val)) {
        val = val.replace(/[^(0-9)]/gi, '')
      }

      var formatNum = ''

      if (val.length == 11) {
        formatNum = val.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3')
      } else if (val.length == 8) {
        formatNum = val.replace(/(\d{4})(\d{4})/, '$1-$2')
      } else {
        if (val.indexOf('02') === 0) {
          if (val.length === 9) {
            formatNum = val.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3')
          } else {
            formatNum = val.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3')
          }
        } else {
          formatNum = val.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3')
        }
      }

      this.phoneNumber = formatNum

      if (this.phoneNumber.indexOf('-') === -1) {
        return this.phoneNumberFormError
      }

      return this.checkLen(val, 8, 11)
    },
  },
}
</script>

<style lang="scss" scoped>
::v-deep {
  .v-label {
    font-weight: 400;
    font-size: 0.65rem;
    color: #BDBDBD !important;
  }
}
</style>
