<template>
  <v-container fill-height class="ondijiBackground fullContent">
    <v-row
      justify="center"
      align="center"
      style="margin-left:0px;margin-right:0px;"
    >
      <v-col cols="12" class="pa-0" justify="center" align="center">
        <v-card outlined class="loginForm pt-10 mx-2">
          <v-form
            ref="form"
            method="post"
            v-model="valid"
            class="loginForm pt-0 pb-3 pl-2 pr-2"
            @submit.prevent
          >
            <v-card-text class="text-center pt-0">
              <v-row>
                <v-col cols="12" class="pt-0 pb-2 headline font-weight-bold">
                  온닫이
                </v-col>
              </v-row>
              <v-row>
                <v-col class="subtitle-2 pt-0 pb-0 font-weight-bold">
                  소소한 동네 이야기
                </v-col>
              </v-row>
              <v-row>
                <v-col class="pt-10 mt-6 pb-0">
                  <v-text-field
                    v-model="memberId"
                    placeholder="이메일"
                    id="memberId"
                    name="memberId"
                    ref="memberId"
                    autofocus
                    type="text"
                    color="brown darken-4"
                    background-color="white"
                    back
                    required
                    :rules="checkRule.emailRules"
                    class="text-center pa-0 ml-4 mr-4 font10"
                    v-on:keyup.enter="
                      () => {
                        this.login()
                      }
                    "
                  />
                </v-col>
              </v-row>
              <v-row>
                <v-col class="pt-0 pb-0">
                  <v-text-field
                    v-model="memberPw"
                    placeholder="비밀번호"
                    ref="memberPw"
                    type="password"
                    color="brown darken-4"
                    required
                    :rules="checkRule.passwordRules"
                    class="text-center  ml-4 mr-4 pt-0"
                    :error-messages="messagedata"
                    v-on:keyup.enter="login"
                  />
                </v-col>
              </v-row>
              <v-row>
                <v-col class="pt-0 pb-0 mt-0 mb-0">
                  <v-checkbox
                    v-model="isLoginRemember"
                    class="mx-2 idRemember"
                    label="로그인 유지하기"
                    name="rememberMe"
                    color="#BCAAA4"
                  ></v-checkbox>
                </v-col>
              </v-row>
              <v-row>
                <v-col class="pt-0 pr-4 pl-4">
                  <v-btn
                    rounded
                    class="buttonConfirm "
                    width="100%"
                    @click="login"
                    >로그인</v-btn
                  >
                  <input
                    type="submit"
                    ref="btnsumitLogin"
                    value="click"
                    style="display:none;"
                  />
                </v-col>
              </v-row>
              <v-row>
                <v-col class="caption text-right pt-0 pb-0">
                  아직 온닫이 이웃이 아니신가요?
                  <span class="subtitle-2 ml-2 link" @click="memberJoin"
                    >회원가입</span
                  >
                </v-col>
              </v-row>
              <v-row>
                <v-col class="mt-10 pt-11 caption  text-left pb-0"
                  >온닫이 ID 또는 암호를 잊으셨나요?
                </v-col>
              </v-row>
              <v-row>
                <v-col class="subtitle-2  text-left pt-2 pb-0">
                  <span
                    class="mr-4 pointer font-weight-bold font10 brown--text text--darken-4"
                    @click="findMemberId"
                    >아이디 찾기</span
                  >|<span
                    class="ml-4 pointer font-weight-bold font10 brown--text text--darken-4"
                    @click="findMemberPw"
                    >비밀번호 찾기</span
                  >
                </v-col>
              </v-row>
            </v-card-text>
          </v-form>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<style lang="scss" scoped>
.loginForm {
  min-width: 18rem;
  max-width: 25rem;
  border-radius: 10px !important;
}
.link {
  text-decoration: underline;
  cursor: pointer;
}
.pointer {
  cursor: pointer;
}
</style>
<style>
.loginForm .text-center input {
  text-align: center !important;
}
.v-input--checkbox.idRemember label {
  left: -0.2em !important;
}
.v-input--checkbox.idRemember .v-icon {
  font-size: 1.2em !important;
}
</style>
<style>
.loginForm .v-input--selection-controls {
  margin-top: 4px !important;
  margin-bottom: 4px !important;
}
</style>
<script>
import ax from "axios"
export const axios = ax

export default {
  data() {
    return {
      valid: true,
      msg: "로그인페이지",
      memberId: "",
      memberPw: "",
      memberImg: "",
      drawer: false,
      messagedata: "",
      isShowBar: false,
      isIDRemember: false,
      isLoginRemember: true,
      loginMode: "user",
      checkRule: {
        emailRules: [
          (v) => !!v || "이메일을 입력하세요",
          (v) => /.+@.+\..+/.test(v) || "이메일 형식이 맞지 않습니다.",
        ],
        passwordRules: [(v) => !!v || "암호를 입력하세요"],
      },
    }
  },
  created() {
    const loginId = this.$BaseCommon.memberInfo.getMember().loginId

    this.memberId = loginId != undefined ? loginId : this.memberId
    this.loginMode =
      this.$route.params.loginMode === undefined
        ? "user"
        : this.$route.params.loginMode
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
  beforeCreate() {},
  methods: {
    formclear: function() {
      this.memberId = ""
      this.memberPw = ""
    },

    login() {
      if (!this.$refs.form.validate()) {
        return
      }

      this.$BaseCommon.restful
        .call("/member/logindo", {
          memberId: this.memberId,
          memberPw: this.memberPw,
          rememberMe: this.isLoginRemember,
        })
        .then((res) => {
          if (res.isLogin === false) {
            this.$BaseCommon.memberInfo.setMember(null)
            this.loginfail()
          } else {
            if (this.isIDRemember) localStorage.memberId = this.memberId
            else localStorage.memberId = ""
            this.$BaseCommon.memberInfo.setMember(res)

            this.$EventBus.$emit("announceChange")
            this.$EventBus.$emit("loginAfterMenuSet")
            this.messagedata = ""
            if (this.loginMode === "local") this.$router.push("/local")
            else {
              this.$router.push("/")
            }
          }
        })
        .catch((err) => {
          console.log(err)
          this.loginfail()
        })
    },
    loginfail() {
      this.messagedata = ""
      this.formclear()
    },
    memberJoin() {
      this.$router.push("/register/")
    },
    findMemberId() {
      this.$router.push("/member/findid")
    },
    findMemberPw() {
      this.$router.push("/member/findpw")
    },
  },
}
</script>
