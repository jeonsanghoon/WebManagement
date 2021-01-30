<template>
  <v-container fill-height class="ondijiBackground fullContent">
    <v-row
      justify="center"
      align="center"
      style="margin-left:0px;margin-right:0px;"
    >
      <v-col cols="12" justify="center" align="center" class="findID pa-0">
        <carousel
          ref="carousel"
          :per-page="1"
          :indicators="false"
          :slideOnSwipe="false"
          :scrollPerPage="false"
          :touchDrag="false"
          :mouseDrag="false"
          paginationPosition="top"
        >
          <Slide name="slide1">
            <v-card outlined class="cardForm">
              <v-form ref="form" v-model="valid">
                <v-card-title class="card-title">
                  <v-spacer></v-spacer>
                  <v-btn small icon @click="close">
                    <v-icon>fas fa-times</v-icon>
                  </v-btn>
                </v-card-title>
                <v-card-text class="card-text">
                  <v-container>
                    <v-row>
                      <v-col cols="12" class="titleOndaji font-weight-bold">
                        비밀번호 재설정
                      </v-col>
                    </v-row>
                    <v-row class="cardDiscription">
                      <v-col cols="12" class="caption">
                        비밀번호를 잊으셨나요? <br />아래의 정보를 입력해
                        주세요.</v-col
                      >
                    </v-row>

                    <v-row>
                      <v-col cols="12" class="card-input">
                        <v-text-field
                          label="이름"
                          v-model="memberName"
                          v-mask="'XXXXXXX'"
                          width="50px"
                          placeholder="이온닫"
                          class="text-left body-2"
                          color="brown darken-4"
                          :rules="checkRule.nameRules"
                          v-on:keyup.enter="sendAuthEmail(false)"
                        >
                        </v-text-field>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col cols="12" class="card-input">
                        <v-input class="pt-0 pb-0">
                          <v-text-field
                            label="이메일"
                            placeholder="ondaji@ondaji.com"
                            v-model="email"
                            width="50px"
                            class="text-left body-2"
                            color="brown darken-4"
                            :rules="checkRule.emailRules"
                            v-on:keyup.enter="sendAuthEmail(false)"
                          >
                          </v-text-field
                          ><v-btn
                            small
                            rounded
                            depressed
                            width="100px"
                            class="ml-2 font-weight-bold btnAuth"
                            @click="sendAuthEmail(false)"
                            >인증번호받기</v-btn
                          >
                        </v-input>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-text>
                <v-card-actions class="card-actions_left">
                  <span
                    class="red--text caption "
                    v-if="errorMessage != ''"
                    v-html="errorMessage"
                  ></span>
                </v-card-actions>
              </v-form>
            </v-card>
          </Slide>
          <slide name="slide2">
            <v-card outlined class="cardForm">
              <v-form ref="form2_1" v-model="valid">
                <v-card-title class="card-title">
                  <v-spacer></v-spacer>
                  <v-btn small icon @click="close"
                    ><v-icon>fas fa-times</v-icon></v-btn
                  >
                </v-card-title>
                <v-card-text class="card-text">
                  <v-container>
                    <v-row>
                      <v-col cols="12" class="titleOndaji font-weight-bold">
                        비밀번호 재설정
                      </v-col>
                    </v-row>
                    <v-row class="cardDiscription">
                      <v-col cols="12" class="caption"
                        >인증번호가 <b>{{ memberName }}</b
                        >님의 메일(<strong class="blue--text">{{
                          displayEmail
                        }}</strong
                        >)로 발송되었습니다.</v-col
                      >
                    </v-row>
                    <v-row>
                      <v-col class="card-input">
                        <v-input class="pt-0 pb-0">
                          <v-text-field
                            v-model="authNumber"
                            v-mask="'######'"
                            label="인증번호"
                            color="brown darken-4"
                            class="mr-2"
                            placeholder="6자리 숫자"
                            :disabled="isDisableAuthNumer"
                            required
                            :rules="checkRule.authNumberRules"
                            :error-messages="errorMessage2"
                          ></v-text-field>
                          <span class="pa-2 timer" v-text="nTimer"></span>
                          <v-btn
                            small
                            rounded
                            depressed
                            width="110px"
                            class="font-weight-bold btnAuth"
                            @click="sendAuthEmail(true)"
                            >인증번호 재발송</v-btn
                          >
                        </v-input>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col class="card-input pt-5">
                        <span class="caption">
                          <v-tooltip v-model="tooltipshow" right>
                            <template v-slot:activator="{ on, attrs }">
                              <v-icon
                                v-bind="attrs"
                                v-on="on"
                                small
                                @click="tooltipshow = !tooltipshow"
                                >fas fa-question-circle</v-icon
                              >
                              메일이 오지 않나요?
                            </template>
                            <div class="caption pl-0 pr-4 pt-0">
                              온닫이가 발송한 메일이 스팸 메일로
                              <br />
                              분류된 것은 아닌지 확인해 주세요.
                            </div>
                            <div class="caption pt-2 pr-4">
                              스팸 메일함에도 메일이 없다면
                              <br />‘인증번호 재발송’을 눌러 주세요.
                            </div>
                          </v-tooltip>

                          <br />
                        </span>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-text>
                <v-card-actions class="card-actions">
                  <Vuetify-button
                    rounded
                    class="ma-2"
                    width="80px"
                    :btntype="this.$Config.vuetify.btn.type.confirm"
                    :text="'다음'"
                    @click="authConfirm"
                  ></Vuetify-button>
                </v-card-actions>
              </v-form>
            </v-card>
          </slide>
          <Slide name="slide3">
            <v-card outlined class="cardForm">
              <v-form ref="form1" v-model="valid">
                <v-card-title class="card-title">
                  <v-spacer></v-spacer>
                  <v-btn small icon @click="close"
                    ><v-icon>fas fa-times</v-icon></v-btn
                  >
                </v-card-title>
                <v-card-text class="card-text">
                  <v-container>
                    <v-row>
                      <v-col cols="12" class="titleOndaji font-weight-bold">
                        비밀번호 재설정
                      </v-col>
                    </v-row>
                    <v-row class="cardDiscription">
                      <v-col cols="12" class="caption"
                        >새롭게 사용할 비밀번호를 입력해 주세요.</v-col
                      >
                    </v-row>
                    <v-row>
                      <v-col class="card-input">
                        <v-text-field
                          v-model="memberPw"
                          label="새 비밀번호"
                          placeholder="영문,숫자,특수문자 혼용 8자 이상"
                          ref="memberPw"
                          type="password"
                          color="brown darken-4"
                          required
                          :rules="checkRule.passwordRules"
                          class="pt-0"
                          v-on:keyup.enter="login"
                        />
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col class="card-input">
                        <v-text-field
                          v-model="memberPwConfirm"
                          label="새 비밀번호 확인"
                          placeholder="동일한 비밀번호를 입력하세요"
                          ref="memberPwConfirm"
                          type="password"
                          color="brown darken-4"
                          required
                          :rules="checkRule.pwdConfirmRules"
                          class="pt-0"
                          @keyup.enter="login"
                        />
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-text>
                <v-card-actions class="card-actions">
                  <Vuetify-button
                    rounded
                    class="ma-2"
                    width="80px"
                    :btntype="this.$Config.vuetify.btn.type.confirm"
                    :text="'다음'"
                    @click="changePw"
                  ></Vuetify-button>
                </v-card-actions>
              </v-form>
            </v-card>
          </Slide>

          <Slide name="slide4">
            <v-card outlined class="cardForm">
              <v-form ref="form2" v-model="valid">
                <v-card-title class="card-title">
                  <v-spacer></v-spacer>
                  <v-btn small icon @click="close">
                    <v-icon>fas fa-times</v-icon>
                  </v-btn>
                </v-card-title>
                <v-card-text class="card-text">
                  <v-container>
                    <v-row>
                      <v-col cols="12" class="titleOndaji font-weight-bold">
                        비밀번호 재설정 완료
                      </v-col>
                    </v-row>
                    <v-row class="cardDiscription">
                      <v-col cols="12" class="caption">
                        <div>
                          고객님의 비밀번호가 성공적으로 재설정되었습니다.
                        </div>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-text>
                <v-card-actions class="card-actions">
                  <Vuetify-button
                    rounded
                    class="ma-2"
                    :btntype="this.$Config.vuetify.btn.type.confirm"
                    text="로그인"
                    @click="close"
                  ></Vuetify-button>
                </v-card-actions>
              </v-form>
            </v-card>
          </Slide>
        </carousel>
      </v-col>
    </v-row>
  </v-container>
</template>

<style lang="scss" scoped>
.VueCarousel-slide {
  padding-right: 0.5em;
  padding-left: 0.5em;
}
.card form {
  height: 100%;
}
.cardForm {
  min-width: 20rem;
  max-width: 25rem;
  border-radius: 0.625rem !important;
  height: 35rem;
  padding-left: 0rem;
  padding-right: 0rem;
  .card {
    &-title {
      padding-top: 0.5rem;
      padding-right: 0.5rem;
    }

    &-text {
      padding-left: 1.5rem;
      padding-right: 1.5rem;
      text-align: left;
    }
    &-input {
      font-size: 0.875rem;
      padding-top: 0px;
      padding-bottom: 0.5rem;
      height: 4.5rem;
    }
    &-action {
      padding-left: 1.5rem;
      padding-right: 1.5rem;
    }
  }

  & .cardDiscription {
    height: 5rem;
    opacity: 0.8;
    color: #887d7a;
  }

  .card-actions {
    position: absolute;
    bottom: 0px;
    right: 0.5rem;
  }
  .card-actions_left {
    position: absolute;
    bottom: 0px;
    left: 0.5rem;
  }
}
.timer {
  position: absolute;
  right: 7rem;
}
</style>
<style>
.btnAuth {
  border: 1px solid #bdbdbd !important;
  background-color: #e9e9e9 !important;
  font-size: 0.625rem;
}
.findID .VueCarousel-pagination {
  display: none;
}
.v-input--checkbox.idRemember label {
  left: -0.2em !important;
}
.v-input--checkbox.idRemember .v-icon {
  font-size: 1.2em !important;
}
.link {
  text-decoration: underline;
  cursor: pointer;
}
</style>

<script>
import ax from "axios"
export const axios = ax
import { Carousel, Slide } from "vue-carousel"
export default {
  components: {
    Carousel,
    Slide,
  },
  data() {
    return {
      tooltipshow: false,
      valid: true,

      birthDate: "",
      authNumber: "",
      memberCode: 0,
      memberPw: "",
      memberPwConfirm: "",
      memberName: "",
      email: "",
      displayEmail: "",
      errorMessage: "",
      errorMessage2: "",
      nTimer: "3:00",
      timer: null,
      nAuthTime: 180,
      defaultTime: 180,
      isDisableAuthNumer: true,
      checkRule: {
        emailRules: [
          (v) => !!v || "이메일을 입력하세요",
          (v) => /.+@.+\..+/.test(v) || "이메일 형식이 맞지 않습니다.",
        ],
        nameRules: [(v) => !!v || "이름을 입력하세요"],
        passwordRules: [
          (v) => !!v || "비밀번호를 입력하세요",
          (v) =>
            /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/.test(v) ||
            "영문자, 숫자, 특수문자 혼용 8자리 이상을 입력하세요",
        ],
        pwdConfirmRules: [
          (v) => !!v || "비밀번호를 확인하세요",
          (v) => v === this.memberPw || "동일한 비밀번호를 입력해주세요",
        ],
        authNumberRules: [
          (v) => v.length === 6 || "인증번호(6자리)를 입력하세요",
        ],
        ///authNumberRules:[(v) => v.length != 6 || "6자리를 입력하세요"],
      },
    }
  },
  created() {
    const loginId = this.$BaseCommon.memberInfo.getMember().loginId
    this.memberId = loginId != undefined ? loginId : this.memberId
  },
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({
      el: this.$el,
      isBar: false,
      isTop: false,
    })
    if (!this.$BaseCommon.stringInfo.isNullEmpty(localStorage.memberId)) {
      this.isIDRemember = true
      this.memberId = localStorage.memberId
    } else {
      this.isIDRemember = false
    }
  },
  methods: {
    close() {
      //this.$router.push("/member/login")
      this.$BaseCommon.memberInfo.moveLogin()
    },
    sendAuthEmail(isRe) {
      if (!this.$refs.form.validate()) {
        return
      }
      isRe = isRe === undefined ? false : isRe
      this.displayEmail = this.$BaseCommon.dataInfo.displayEmail(this.email)

      this.$BaseCommon.restful
        .call("/member/resetPasswordEmail", {
          memberName: this.memberName,
          memberId: this.email,
        })
        .then((res) => {
          console.log(res)
          if (res.resultType.toLowerCase() == "info") {
            this.memberCode = Number(res.data)

            this.errorMessage = ""
            this.next(1)
            clearTimeout(this.timer)
            this.timer = null
            if (isRe) {
              this.$alert("인증번호가 이메일로 재발송 되었습니다.", {
                yesBtnName: "확인",
              }).then((res) => {
                console.log(res)
                this.startTimer()
              })
            } else this.startTimer()
          } else {
            /* if (res.data === "email") this.email = ""
            else if (res.data === "name") this.memberName = ""
            */
            this.errorMessage = res.message
          }
        })
        .catch((res) => {
          console.log("/member/resetPasswordEmail >> ", res)
        })
    },
    startTimer() {
      this.nAuthTime = this.defaultTime
      this.isDisableAuthNumer = false
      this.authTimeExec()
    },
    changePw() {
      if (!this.$refs.form1.validate()) {
        return
      }
      let self = this
      this.$BaseCommon.restful
        .call("/member/changepw", {
          memberCode: this.memberCode,
          memberPwReg: this.memberPw,
        })
        .then((res) => {
          if (res.resultType.toLowerCase() == "info") {
            console.log(res)
            self.next(3)
          } else {
            console.log("변경에러")
          }
        })
        .catch((res) => {
          console.log(res)
        })
    },
    next(page) {
      this.$refs.carousel.currentPage = page
      this.$refs.carousel.goToPage(page)
    },
    authConfirm() {
      if (!this.$refs.form.validate()) {
        return
      }

      if (this.authNumber.length != 6) {
        this.errorMessage2 = "인증번호 6자리를 입력해 주세요"
        return
      }

      this.$BaseCommon.restful
        .call("/member/memberAuthConfirm", {
          memberCode: this.memberCode,
          authNumber: this.authNumber,
        })
        .then((res) => {
          console.log(res)
          if (res.resultType.toLowerCase() == "info") {
            this.next(2)
            if (this.timer !== null) clearTimeout(this.timer)
            this.errorMessage2 = ""
          } else {
            this.errorMessage2 = res.message
          }
        })
    },
    authTimeExec() {
      this.timer = setTimeout(() => {
        let min = String(this.nAuthTime % 60)
        min = (min.length === 1 ? "0" : "") + min
        this.nTimer = `${String(Math.floor(this.nAuthTime / 60))}:${min}`
        if (this.nAuthTime > 0) {
          this.authTimeExec()
        } else {
          this.isDisableAuthNumer = true
          this.$alert("인증번호를 재발송하여 다시 인증받으세요", {
            title: "인증시간 초과",
            yesBtnName: "확인",
          }).then((res) => {
            this.nTimer = "3:00"
            console.log(res)
          })
        }
        this.nAuthTime--
      }, 1000)
    },
  },
}
</script>
