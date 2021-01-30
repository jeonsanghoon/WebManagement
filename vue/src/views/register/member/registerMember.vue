<template>
  <v-container fill-height>
    <v-row class="fill-height" align="start" justify="center" no-gutters>
      <v-col class="fill-height" cols="12" lg="11" xl="8">
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
                <div v-for="data in stepper" :key="data.name">
                  <v-stepper-content
                    class="pa-0"
                    :step="data.page"
                    :key="data.name"
                  >
                    <div v-if="data.page === 1">
                      <inputInfo @inputInfo="checkInputInfo"></inputInfo>
                    </div>
                    <div v-else-if="data.page === 2">
                      <acceptTerms @acceptTerms="checkAccept"></acceptTerms>
                    </div>
                    <div v-else-if="data.page === 3">
                      <regComplete :info="info"></regComplete>
                    </div>
                  </v-stepper-content>
                </div>
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
import acceptTerms from "../acceptTerms"
import regComplete from "../regComplete"

export default {
  mixins: [commonReg],
  components: {
    inputInfo,
    acceptTerms,
    regComplete,
  },
  data() {
    return {
      page: 1,
      MAX_PAGE: 3,
      stepper: [],

      info: {},
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
          info.title = "약관 동의"
          break
        case 2:
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
          return "#AEA09E"
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
    checkAccept(page, val) {
      if (page < 0) {
        this.page += page

        this.changeStepperColor("back")
      }

      if (page > 0) {
        // 회원가입이 완료되면 페이지 이동.
        this.info.service = val

        this.reqRegister()
      }
    },
    reqRegister() {
      const formData = this.getFromData()

      this.$BaseCommon.restful.call2("/member/save", formData).then((res) => {
        try {
          if (res.message != null) {
            console("reqRegister", res.message)
            return
          }
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
      formData.nickname = this.info.nickName
      formData.memberPwReg = this.info.password
      formData.birthDate = this.info.birthDate.replace(/-/gi, "")
      formData.phone = this.info.phoneNumber.replace(/-/gi, "")
      formData.service = this.info.service
      formData.hide = false

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
span {
  font-weight: bold;
  color: #6c5f5c;
}

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

.hover-button {
  color: white;
  font-weight: bold;
  background: #aea6a4;
}

.hover-button:hover {
  background: #6c5f5c;
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
