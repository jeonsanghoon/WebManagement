<template>
  <v-container class="pa-0 ">
    <v-col
      class="pl-sm-7 pl-lg-0"
      cols="12"
      sm="12"
      lg="11"
      offset-sm="1"
      offset-lg="1"
    >
      <v-form ref="inputForm">
        <v-row justify="start" no-gutters>
          <v-col class="d-flex justify-start mt-sm-0" cols="12" sm="3" lg="2">
            <span class="text-left"
              >로컬사업자명<span class="d-flex" style="font-size:0.75em;"
                >(닉네임)</span
              ></span
            >
          </v-col>
          <v-col cols="12" sm="5" lg="3">
            <v-text-field
              placeholder="업체명"
              v-model="name"
              :rules="[nameRules]"
              maxlength="12"
              filled
              rounded
              dense
              required
              :readonly="login"
            >
            </v-text-field>
          </v-col>
          <v-col
            class="mt-sm-2 mt-lg-0 ml-lg-2"
            cols="12"
            sm="5"
            lg="3"
            offset-sm="3"
            offset-lg="0"
          >
            <v-text-field
              placeholder="지점"
              v-model="place"
              :rules="[placeRules]"
              maxlength="12"
              filled
              rounded
              dense
              required
              :readonly="login"
            >
            </v-text-field>
          </v-col>
          <v-col
            class="d-flex justify-end justify-sm-start mx-sm-3 mt-sm-2 mt-lg-0"
            cols="12"
            sm="2"
          >
            <v-btn
              class="none-select-button"
              rounded
              text
              height="2.7em"
              @click="dupComConfirm(confirmNick, info.name)"
            >
              중복확인
            </v-btn>
          </v-col>
        </v-row>

        <v-row justify="start" no-gutters>
          <v-col class="d-flex justify-start mt-sm-2" cols="12" sm="3" lg="2">
            <span>업종</span>
          </v-col>
          <v-col cols="12" sm="5" lg="3">
            <v-select
              placeholder="대분류"
              v-model="type"
              :items="businessType[0]"
              item-text="name"
              item-value="code"
              :rules="[typeRules]"
              filled
              rounded
              dense
              return-object
              required
            >
            </v-select>
          </v-col>

          <v-col
            class="mt-sm-2 mt-lg-0 ml-lg-2"
            cols="12"
            sm="5"
            lg="3"
            offset-sm="3"
            offset-lg="0"
          >
            <v-select
              placeholder="중분류"
              v-model="mdType"
              :items="businessType[1]"
              item-text="name"
              item-value="code"
              :rules="[typeRules]"
              no-data-text="대분류를 선택해주세요."
              filled
              rounded
              dense
              return-object
              required
            >
            </v-select>
          </v-col>

          <v-col
            class="mt-sm-2 mt-lg-0 ml-lg-2"
            cols="12"
            sm="5"
            lg="3"
            offset-sm="3"
            offset-lg="0"
          >
            <v-select
              placeholder="소분류"
              v-model="smType"
              :items="businessType[2]"
              item-text="name"
              item-value="code"
              :rules="[typeRules]"
              no-data-text="중분류를 선택해주세요."
              filled
              rounded
              dense
              return-object
              required
            >
            </v-select>
          </v-col>
        </v-row>

        <v-row justify="start" no-gutters>
          <v-col class="d-flex justify-start mt-sm-2" cols="12" sm="3" lg="2">
            <span>사업자등록번호</span>
          </v-col>
          <v-col cols="12" sm="5" lg="4">
            <v-text-field
              placeholder="'-'제외, 숫자만 기입"
              v-mask="'###-##-#####'"
              v-model="businessNumber"
              :rules="[businessNumberRules]"
              maxlength="12"
              filled
              rounded
              dense
              required
              :readonly="login"
            >
            </v-text-field>
          </v-col>
          <v-col
            class="d-flex justify-end justify-sm-start mx-sm-3 mt-lg-0"
            cols="12"
            sm="2"
          >
            <v-btn
              class="none-select-button"
              rounded
              text
              height="2.8em"
              @click="dupComConfirm(confirmNum, info.businessNumber)"
            >
              중복확인
            </v-btn>
          </v-col>
        </v-row>
      </v-form>
    </v-col>

    <v-row class="pt-10" justify="center" no-gutters>
      <div align="center">
        <v-btn
          class="none-select-button mb-2 mx-4 mx-sm-4 mx-lg-8"
          rounded
          width="12em"
          large
          text
          @click="selectBtn(-1)"
        >
          뒤로가기
        </v-btn>
        <v-btn
          class="select-button mb-2 mx-4 mx-sm-4 mx-lg-8"
          rounded
          width="12em"
          large
          text
          @click="selectBtn(+1)"
        >
          확인
        </v-btn>
      </div>
    </v-row>

    <v-bottom-sheet v-model="dupCheck">
      <v-sheet class="py-5 text-center">
        <div class="pb-4">
          <span>{{ errStr }}</span>
        </div>
        <v-btn
          class="none-select-button"
          rounded
          text
          width="12em"
          height="2.8em"
          @click="dupCheck = false"
        >
          닫기
        </v-btn>
      </v-sheet>
    </v-bottom-sheet>
  </v-container>
</template>

<script>
import { commonReg } from "../mixins/commonMixins"
import { regCheck } from "../mixins/regCheckMixins"
import { dupCheck } from "../mixins/dupCheckMixins"

export default {
  mixins: [commonReg, regCheck, dupCheck],
  data() {
    return {
      info: {
        name: "",
        place: "",
        businessType: "",
        businessNumber: "",
        profitType: "",
      },

      // watch 사용을 위해 변수 따로 선언
      // 글자 변경이 있다면 중복확인 필요
      name: "",
      place: "",
      businessNumber: "",

      MAX_BUSINESS_CNT: 3,
      businessType: [[], [], []],

      // 업종 구분
      // type: 대분류
      // mdType: 중분류
      // smType: 소분류
      type: "",
      mdType: "",
      smType: "",

      confirmNick: {
        err: false,
        dupCheck: false,
        nameErr: false,
        placeErr: false,
        name: "로컬사업자명",
      },

      confirmNum: {
        err: false,
        dupCheck: false,
        name: "사업자등록번호",
      },

      nameRules: () => {
        return this.checkCompanyName(this.name, this.confirmNick)
      },
      placeRules: () => {
        return this.checkPlace(this.place, this.confirmNick)
      },
      typeRules: (val) => {
        return this.checkType(val)
      },
      businessNumberRules: () => {
        return this.checkBusinessNumber(
          this.info.businessNumber,
          this.confirmNum
        )
      },
    }
  },
  created() {
    this.getTypeItmes(null, 0)

    if (this.loginCheck() === true) {
      this.info.name = this.loginInfo.companyName
      this.info.place = this.loginInfo.branchName
      this.name = this.loginInfo.companyName
      this.place = this.loginInfo.branchName
      this.info.businessType = this.loginInfo.businessType
      this.businessNumber = this.loginInfo.businessNumber

      let type = []

      this.getType(type)

      this.getTypeItmes(type[0], 1)
      this.getTypeItmes(type[1], 2)
    }
  },
  watch: {
    name() {
      this.setDupCheck(this.confirmNick, false)
    },
    place() {
      this.setDupCheck(this.confirmNick, false)
    },
    businessNumber() {
      this.info.businessNumber = this.businessNumber

      this.setDupCheck(this.confirmNum, false)
    },
    type() {
      this.mdType = ""

      this.getTypeItmes(this.type.code, 1)
    },
    mdType() {
      this.smType = ""

      if (this.mdType != "") {
        this.getTypeItmes(this.mdType.code, 2)
      }
    },
  },
  methods: {
    dupComConfirm(dupConfirm, val) {
      if (dupConfirm.err === false) {
        return
      }

      if (dupConfirm.name === "로컬사업자명") {
        val = []
        this.info.name = this.name
        this.info.place = this.place

        val[0] = this.name
        val[1] = this.place
      }

      this.dupConfirm("/member/dupconfirmcompany", dupConfirm, val)
    },
    getTypeItmes(val, index) {
      const formData = {}

      formData.parentCode = val

      this.$BaseCommon.restful
        .call2("/member/getbusinesstype", formData)
        .then((res) => {
          this.setType(res, index)
        })
    },
    setType(val, index) {
      if (index == 1) {
        this.clearType()
      }

      val.forEach((e) => {
        let type = new Object()

        type.code = e.code
        type.name = e.name

        this.businessType[index].push(type)

        if (this.loginCheck() === true) {
          this.selectType(e, index)
        }
      })
    },
    clearType() {
      for (let i = 1; i < this.MAX_BUSINESS_CNT; i++) {
        while (this.businessType[i].length > 0) {
          this.businessType[i].pop()
        }
      }
    },
    selectType(val, index) {
      let type = []

      this.getType(type)

      if (val.code === type[index]) {
        if (index === 0) {
          this.type = val
        } else if (index === 1) {
          this.mdType = val
        } else if (index === 2) {
          this.smType = val
        }
      }
    },
    getType(val) {
      if (this.info.businessType === null) {
        return
      }

      const type = this.info.businessType.toString()

      if (type.length === 5) {
        val[0] = Number(type.substring(0, 1))
        val[1] = Number(type.substring(0, 3))
      } else {
        val[0] = Number(type.substring(0, 2))
        val[1] = Number(type.substring(0, 4))
      }
      val[2] = Number(type)
    },
    selectBtn(page) {
      if (page < 0) {
        this.$emit("inputCompanyInfo", page)
      } else if (page > 0) {
        if (
          this.$refs.inputForm.validate() === true &&
          this.checkComDup() === true
        ) {
          this.info.businessType = this.smType.code
          this.checkProfitType()

          this.$emit("inputCompanyInfo", page, this.info)
        }
      }
    },
    checkComDup() {
      const dupArr = [this.confirmNick, this.confirmNum]

      return this.checkDup(dupArr)
    },
    checkProfitType() {
      const num = this.info.businessNumber.substring(4, 6)

      if (num === "82" || num === "83") {
        this.info.profitType = 0
      } else {
        this.info.profitType = 1
      }
    },
  },
}
</script>

<style lang="scss" scoped>
@import "../styles/commonStyle.scss";

::v-deep {
  input::placeholder {
    font-size: 0.75em;
    color: #b8b8b8 !important;
  }
}
</style>
