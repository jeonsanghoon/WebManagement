<template>
  <v-form ref="form" v-model="valid">
    <v-container>
      <v-row>
        <v-col class="componentPage">
          <v-card flat outlined px-2>
            <div class="ondaji_font_16_bold_brown_80 cardTitle">
              기존 비밀번호
            </div>
            <v-divider class="mx-4" />

            <div class="ondaji_font_16_thin_grey_80 cardContent">
              <v-text-field
                class="ondajiInput"
                type="password"
                refs="oriPassword"
                dense
                outlined
                :rules="checkRule.passwordRules"
                v-model="oriPassword"
              ></v-text-field>
            </div>
            <div class="ondaji_font_16_bold_brown_80 cardTitle">
              새 비밀번호
            </div>
            <v-divider class="mx-4" />
            <div class="ondaji_font_16_thin_grey_80 cardContent">
              <v-text-field
                class="ondajiInput"
                type="password"
                dense
                outlined
                :rules="checkRule.passwordRules"
                v-model="changedPassword"
              ></v-text-field>
            </div>
            <div class="ondaji_font_16_bold_brown_80 cardTitle">
              비밀번호 확인
            </div>
            <v-divider class="mx-4" />
            <div class="ondaji_font_16_thin_grey_80 cardContent">
              <v-text-field
                class="ondajiInput"
                type="password"
                dense
                outlined
                :rules="checkRule.pwdConfirmRules"
                v-model="confirmPassword"
              ></v-text-field>
              <Vuetify-snackbar v-model="snackbar" />
            </div>
            <v-card-actions class="bottomButtons">
              <Buttons-default
                btnType="primary"
                :size="40"
                btnText="변경하기"
                @click="doSave"
              />
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</template>
<style lang="scss">
.ondajiInput .v-input__slot {
  background-color: #f2f2f2 !important;

  input {
    font-size: 16px !important;
    color: #424242 !important;
    font-weight: 400 !important;
  }
}
</style>
<style lang="scss" scoped>
@import "_common.scss";
</style>
<script>
export default {
  props: {
    value: {},
  },
  created() {
    this.logininfo = this.$BaseCommon.memberInfo.getMember()
    //this.phone = this.logininfo.member.phone
  },
  data() {
    return {
      valid: true,
      logininfo: {},
      oriPassword: "",
      changedPassword: "",
      confirmPassword: "",
      snackbar: {
        isView: false,
        text: "비밀번호가 정상적으로 변경되었습니다.",
      },
      checkRule: {
        passwordRules: [
          (v) => !!v || "비밀번호를 입력하세요",
          (v) =>
            /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/.test(v) ||
            "영문자, 숫자, 특수문자 각 1자리 이상을 사용하여 8자리 이상을 입력하세요",
        ],
        pwdConfirmRules: [
          (v) => !!v || "비밀번호를 확인하세요",
          (v) => v === this.changedPassword || "동일한 비밀번호를 입력해주세요",
        ],
      },
    }
  },
  methods: {
    doSave() {
      if (!this.$refs.form.validate()) {
        return
      }
      this.$BaseCommon.restful
        .call2("/member/changepw2", {
          memberPw: this.oriPassword,
          memberPwReg: this.changedPassword,
        })
        .then((res) => {
          if (res.resultType.toLowerCase() == "error") {
            //this.$alert(res.message)
            this.snackbar = {
              isView: true,
              text: res.message,
              title: "<span style='color:red'>경고</span>",
              isError: true,
            }
            this.oriPassword = ""
          } else {
            this.$alert("비밀번호가 정상적으로 변경되었습니다.").then(() => {
              this.$router.push("/")
            })

            this.oriPassword = ""
            this.changedPassword = ""
            this.confirmPassword = ""
          }
        })
    },
  },
}
</script>
