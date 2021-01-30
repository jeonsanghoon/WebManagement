<template>
  <v-container>
    <v-row class="fill-height" align="start" justify="center" no-gutters>
      <v-col class="fill-height" cols="12" lg="11" xl="9">
        <v-stepper class="fill-height" alt-labels v-model="page">
          <v-row class="mt-7" justify="center" no-gutters>
            <v-col cols="12" sm="11" lg="10">
              <v-stepper-header>
                <template v-for="(data, index) in stepper">
                  <v-stepper-step
                    ref="infoStepper"
                    color="#B8B1B0"
                    :complete="page > data.page"
                    step=""
                    :key="data.name"
                  >
                    <p
                      :style="{
                        'font-weight': selectPage(index, 'w'),
                        color: selectPage(index, 'c'),
                      }"
                    >
                      {{ data.title }}
                    </p>
                  </v-stepper-step>

                  <v-divider v-if="data.page !== MAX_PAGE" :key="data.name">
                  </v-divider>
                </template>
              </v-stepper-header>
            </v-col>
          </v-row>

          <v-row class="content-box" align="center">
            <v-col class="mb-10">
              <v-stepper-items>
                <template v-for="data in stepper">
                  <v-stepper-content
                    class="pa-0"
                    :step="data.page"
                    :key="data.name"
                  >
                    <div v-if="data.page === 1">
                      <inputInfo @inputInfo="checkInputInfo"></inputInfo>
                    </div>
                    <div v-else-if="data.page === 2">
                      <inputCompanyInfo
                        @inputCompanyInfo="checkinputCompanyInfo"
                      ></inputCompanyInfo>
                    </div>
                    <div v-else-if="data.page === 3">
                      <acceptTerms @acceptTerms="checkAccept"></acceptTerms>
                    </div>
                    <div v-else-if="data.page === 4">
                      <regComplete :info="info"></regComplete>
                    </div>
                  </v-stepper-content>
                </template>
              </v-stepper-items>
            </v-col>
          </v-row>
        </v-stepper>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { commonReg } from "../mixins/commonMixins"

import inputInfo from "./inputInfo"
import inputCompanyInfo from "./inputCompanyInfo"
import acceptTerms from "../acceptTerms"
import regComplete from "../regComplete"

export default {
  mixins: [commonReg],
  components: {
    inputInfo,
    inputCompanyInfo,
    acceptTerms,
    regComplete,
  },
  data() {
    return {
      page: 1,
      MAX_PAGE: 4,
      stepper: [],

      info: {},
      companyInfo: {},
    }
  },
  created() {
    for (let i = 0; i < this.MAX_PAGE; i++) {
      let info = new Object()

      info.page = i + 1

      switch (i) {
        case 0:
          info.title = "정보 입력"
          break
        case 1:
          info.title = "업체 정보"
          break
        case 2:
          info.title = "약관 동의"
          break
        case 3:
          info.title = "가입 완료"
          break
      }

      this.stepper.push(info)
    }
  },
  methods: {
    selectPage(page, type) {
      page = page + 1

      if (this.page === page) {
        if (type === "w") {
          return "bold"
        } else if (type === "c") {
          return "#231815"
        }
      } else {
        if (type === "w") {
          return "normal"
        } else if (type === "c") {
          return "#B8B1B0"
        }
      }
    },
    changeStepperColor(dir) {
      const page = this.page - 1

      if (dir === "back") {
        this.$refs.infoStepper[page + 1].$el.children[0].className =
          "none far fa-circle v-stepper__step__step"
        this.$refs.infoStepper[page].$el.children[0].className =
          "select fas fa-circle v-stepper__step__step"
        return
      }

      if (dir === "forward") {
        this.$refs.infoStepper[page - 1].$el.children[0].className =
          "none far fa-circle v-stepper__step__step"
        this.$refs.infoStepper[page].$el.children[0].className =
          "select fas fa-circle v-stepper__step__step"
        return
      }
    },
    checkInputInfo(page, val) {
      this.page += page

      if (page < 0) {
        this.$router.push("/register/")
      }
      if (page > 0) {
        this.info = val

        this.changeStepperColor("forward")
      }
    },
    checkinputCompanyInfo(page, val) {
      this.page += page

      if (page < 0) {
        this.changeStepperColor("back")
      }
      if (page > 0) {
        this.companyInfo = val

        this.changeStepperColor("forward")
      }
    },
    checkAccept(page, val) {
      if (page < 0) {
        this.page += page

        this.changeStepperColor("back")
      }

      if (page > 0) {
        // 회원가입이 완료되면 페이지 이동
        this.info.service = val

        this.reqRegister()
      }
    },
    reqRegister() {
      const formData = this.getFromData()

      this.$BaseCommon.restful.call2("/member/save", formData).then((res) => {
        try {
          if (res.message != null) {
            console(res.message)
            return
          }
          // regComplete 에 사업자명을 노출해주는 변수가 info.nickName
          const nickName = `${this.companyInfo.name} ${this.companyInfo.place}`

          this.$set(this.info, `nickName`, nickName)

          this.page += 1
          this.changeStepperColor("forward")

          this.$BaseCommon.memberInfo.getMember().loginId = this.info.id
        } catch (e) {
          console.log("reqRegister", e)
        }
      })
    },
    getFromData() {
      let formData = {}

      if (this.loginCheck() === true) {
        formData.memberCode = this.loginInfo.memberCode
      }

      formData.memberId = this.info.id
      formData.memberName = this.info.name
      formData.memberPwReg = this.info.password
      formData.birthDate = this.info.birthDate.replace(/-/gi, "")
      formData.phone = this.info.phoneNumber.replace(/-/gi, "")
      formData.service = this.info.service
      formData.hide = false
      formData.authType = 20
      formData.companyName = this.companyInfo.name
      formData.branchName = this.companyInfo.place
      formData.businessType = this.companyInfo.businessType
      formData.businessNumber = this.companyInfo.businessNumber.replace(
        /-/gi,
        ""
      )
      formData.profitType = this.companyInfo.profitType

      return formData
    },
  },
  mounted() {
    this.$refs.infoStepper[0].$el.children[0].className =
      "select fas fa-circle v-stepper__step__step"

    for (let i = 1; i < this.MAX_PAGE; i++) {
      this.$refs.infoStepper[i].$el.children[0].className =
        "none far fa-circle v-stepper__step__step"
    }
  },
}
</script>

<style lang="scss" scoped>
@import "../styles/commonStyle.scss";

.v-stepper,
.v-stepper__header {
  box-shadow: none;
  background-color: #fafafa;
}

.content-box {
  margin-top: 100px;
}

@media only screen and (max-width: $grid-breakpoints_md) {
  .content-box {
    margin-top: 20px;
  }
}

.v-divider {
  border: 2px solid black;
}

.view-border {
  border: 1px solid black;
}

.none {
  background-color: #b8b1b0;
}

::v-deep {
  .select {
    background-color: #ffffff !important;
    font-size: 1.5em;
    color: #231815 !important;
  }

  .none {
    background-color: #ffffff !important;
    font-size: 1.5em;
    color: #b8b1b0 !important;
  }
}
</style>
