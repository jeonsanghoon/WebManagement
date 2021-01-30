<template>
  <v-form ref="form" v-model="valid">
    <v-container>
      <v-row>
        <v-col class="componentPage">
          <v-card class="mCard" flat outlined px-2>
            <v-alert outlined class="alertMsg">
              <div>개인 계정으로 전환합니다.</div>
              <div class="detail mt-2">
                개인 계정으로 전환하시면 광고 배너와 통계 정보가 모두 삭제되며
                추후 게시물에 접근할 수 없습니다.
              </div>
              <div class="detail">
                비즈니스 계정 전환은 언제든 다시 가능합니다.
              </div>
            </v-alert>
            <div class="ondaji_font_16_bold_brown_80 cardTitle">
              닉네임(로컬러)
            </div>
            <v-divider class="mx-4" />
            <div class="ondaji_font_16_thin_grey_80 cardContent">
              <v-container class="pa-0">
                <v-row
                  ><v-col cols="12" md="4" class="px-3 pl-md-2 py-0">
                    <v-text-field
                      class="ondajiInput"
                      dense
                      outlined
                      v-model="nickname"
                    ></v-text-field></v-col
                  ><v-col cols="12" md="4" class="px-3 pl-md-2 pt-0">
                    <Buttons-default
                      btnType="primary"
                      :size="40"
                      btnText="중복확인"
                      @click="duplicateCheck"
                  /></v-col>
                </v-row>
              </v-container>
            </div>
            <div class="ondaji_font_16_bold_brown_80 cardTitle">
              비밀번호
            </div>
            <v-divider class="mx-4" />
            <div class="ondaji_font_16_thin_grey_80 cardContent">
              <v-text-field
                class="ondajiInput"
                type="password"
                dense
                outlined
                :rules="checkRule.passwordRules"
                v-model="memberPw"
              ></v-text-field>
            </div>
            <v-card-actions class="bottomButtons">
              <Buttons-default
                btnType="request"
                :rounded="false"
                :size="40"
                btnText="전환하기"
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
@import "_commonPublic.scss";
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
    this.nickname = this.logininfo.member.nickname
    //this.phone = this.logininfo.member.phone
  },
  data() {
    return {
      logininfo: {},
      memberPw: "",
      nickname: "",
      chkDupliCate: false,
      checkRule: {
        passwordRules: [(v) => !!v || "비밀번호를 입력하세요"],
      },
    }
  },
  methods: {
    duplicateCheck(cond) {
      this.$BaseCommon.restful
        .call2("/member/dupconfirm", {
          memberCode: this.logininfo.member.memberCode,
          nickname: this.nickname,
        })
        .then((res) => {
          if (res === 1) {
            this.$alert(
              this.$msg.getMsg("L007")
            ) /**중복되는 데이터가 있습니다.*/
            this.chkDupliCate = false
          } else {
            this.$alert(this.$msg.getMsg("L006")) /**정상등록이 가능합니다."*/
            this.chkDupliCate = true

            if (cond.businessNumer === undefined) {
              this.oriCompanyName = this.companyName
              this.oriBranchName = this.branchName
            } else {
              this.oriBusinessNumber = this.businessNumber
            }
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    doSave() {
      if (!this.$refs.form.validate()) {
        return
      }

      if (!this.chkDupliCate) {
        this.$alert("중복확인을 하여 주시기 바랍니다.")
        return
      }

      this.$BaseCommon.restful
        .call2("/member/changeindividual", {
          memberPwReg: this.memberPw,
          nickname: this.nickname,
        })
        .then((res) => {
          if (res.resultType === "Error") {
            this.$alert(res.message)
          } else {
            console.log(res)
            /** 세션정보 다시 셋팅 */
            this.$BaseCommon.memberInfo.getMyInfo().then((res) => {
              this.logininfo = res
              this.$alert("개인계정 전환이 되었습니다.").then(() => {
                this.$router.push("/")
              })
            })

            //this.$BaseCommon.memberInfo.setMember(this.logininfo)
          }
        })
    },
  },
}
</script>
