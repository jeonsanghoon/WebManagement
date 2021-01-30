<template>
  <v-container class="pa-0">
    <v-row>
      <v-col cols="12" md="4" class="py-0">
        <v-select
          dense
          flat
          outlined
          hide-details
          v-model="sel1"
          item-text="name"
          item-value="code"
          background-color="#f2f2f2"
          :error="this.bError"
          @change="changeSelect(1)"
          :items="list1"
        >
        </v-select></v-col
      ><v-col cols="12" md="4" class="px-3 pt-3 pb-0 py-md-0 px-md-2">
        <v-select
          dense
          flat
          outlined
          hide-details
          :disabled="this.bDisable2"
          v-model="sel2"
          item-text="name"
          item-value="code"
          background-color="#f2f2f2"
          :error="this.bError"
          @change="changeSelect(2)"
          :items="list2"
        >
        </v-select></v-col
      ><v-col cols="12" md="4" class="pa-3 py-md-0 px-md-2">
        <v-select
          dense
          flat
          outlined
          hide-details
          :disabled="this.bDisable3"
          v-model="sel3"
          item-text="name"
          item-value="code"
          background-color="#f2f2f2"
          :error="this.bError"
          @change="changeSelect(3)"
          :items="list3"
        >
        </v-select
      ></v-col>
      <v-col cols="12" class="py-0"
        ><div
          class="error--text caption pl-3"
          v-html="bError ? errorText : ''"
        ></div
      ></v-col>
    </v-row>
  </v-container>
</template>
<script>
export default {
  props: {
    value: { type: Number, default: null },
    error: { type: Boolean, default: false },
    errorText: {
      type: String,
      default: "업종은 필수 선택사항입니다. 모두 선택하세요!",
    },
  },
  created() {
    this.init()
    this.bError = this.error
  },
  watch: {
    value() {
      this.init()
    },
    error(val) {
      this.bError = val
    },
    sel1() {
      // this.checkErrorSel()
    },
    sel2() {
      // this.checkErrorSel()
    },
    sel3(val) {
      if (val === undefined || val === null) {
        this.bError = true
        this.sel3 = null
      } else if (val != null && String(val).length >= 5) this.bError = false
    },
  },
  data() {
    return {
      bError: false,
      sel1: "",
      sel2: "",
      sel3: "",
      bDisable2: false,
      bDisable3: false,

      list1: [],
      list2: [],
      list3: [],
    }
  },
  methods: {
    init() {
      if (this.value == this.sel3) return
      if (String(this.value).length < 5 && this.list1.length > 0) return
      if (this.value === null) {
        this.bDisable2 = true
        this.bDisable3 = true
        this.getBusinessType(1, null, null)
      } else {
        this.bDisable2 = false
        this.bDisable3 = false

        let sTmp =
          (String(this.value).length === 5 ? "0" : "") + String(this.value)
        let val1 = Number(sTmp.substring(0, 2))
        let val2 = Number(sTmp.substring(0, 4))
        let val3 = Number(sTmp)

        console.log("selevalue>>>", val1, val2, val3)
        this.getBusinessType(1, null, val1)
        this.getBusinessType(2, val1, val2)
        this.getBusinessType(3, val2, val3)
      }
    },
    getBusinessType(level, parentCode, defaultVal) {
      if (defaultVal === undefined) {
        if (level === 1) defaultVal = 1
      }
      //defaultVal = defaultVal === undefined ?  "01" : defaultVal
      if (level === 2) {
        this.bDisable2 = false
        this.bDisable3 = true
      }
      if (level === 3) this.bDisable3 = false
      parentCode = level === 1 ? null : parentCode

      this.$BaseCommon.restful
        .call2("/member/getbusinesstype", {
          level: level,
          parentCode: parentCode,
        })
        .then((res) => {
          if (level === 1) {
            this.list1 = res
            this.sel1 = defaultVal
          } else if (level === 2) {
            this.list2 = res
            this.sel2 = defaultVal
          } else if (level === 3) {
            this.list3 = res
            this.sel3 = defaultVal
          }
        })
    },
    changeSelect(level) {
      if (level === 1) {
        this.getBusinessType(2, this.sel1)
        this.getBusinessType(3, this.sel1)

        this.$emit("input", this.sel1)
        this.bDisable3 = true
      }
      if (level === 2) {
        this.getBusinessType(3, this.sel2)
        this.$emit("input", this.sel2)
        this.bDisable3 = false
      }
      if (level === 3) this.$emit("input", this.sel3)
    },
    checkErrorSel() {
      if (this.sel2.length < 3) {
        this.bError = true
        this.sel2 = ""
      }
      if (this.sel3.length < 5) {
        this.bError = true
        this.sel3 = ""
      } else {
        this.bError = false
      }
    },
  },
}
</script>
