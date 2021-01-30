<template>
  <v-form ref="form" v-model="valid">
    <v-container>
      <v-row>
        <v-col class="componentPage">
          <v-card class="mCard" flat outlined px-2>
            <v-alert outlined class="alertMsg">
              <div>비즈니스(기업·기관)계정으로 전환합니다.</div>
            </v-alert>
            <div class="ondaji_font_16_bold_brown_80 cardTitle">
              닉네임(로컬러)
            </div>
            <v-divider class="mx-4" />
            <div class="ondaji_font_16_thin_grey_80 cardContent">
              <v-container class="pa-0">
                <v-row
                  ><v-col cols="12" md="4" class="py-0">
                    <v-text-field
                      class="ondajiInput"
                      v-model="formData.companyName"
                      dense
                      outlined
                      :rules="checkRule.companyNameRules"
                    ></v-text-field></v-col
                  ><v-col cols="12" md="4" class="px-3 pl-md-2 py-0">
                    <v-text-field
                      class="ondajiInput"
                      v-model="formData.branchName"
                      dense
                      outlined
                      :rules="checkRule.branchNameRules"
                    ></v-text-field></v-col
                  ><v-col cols="12" md="4" class="px-3 pl-md-2 pt-0">
                    <Buttons-default
                      btnType="primary"
                      :size="40"
                      btnText="중복확인"
                      @click="duplicatenickname"
                  /></v-col>
                </v-row>
              </v-container>
            </div>
            <div class="ondaji_font_16_bold_brown_80 cardTitle pt-3 pt-md-0">
              업종
            </div>
            <v-divider class="mx-4" />
            <div class="ondaji_font_16_thin_grey_80 cardContent">
              <Vuetify-selBusinessType
                v-model="formData.businessType"
                :error="isBusinessError"
              />
            </div>
            <div class="ondaji_font_16_bold_brown_80 pt-6 cardTitle">
              사업자 등록번호(고유번호)
            </div>
            <v-divider class="mx-4" />
            <div class="ondaji_font_16_thin_grey_80 cardContent">
              <v-container class="px-0 py-0">
                <v-row
                  ><v-col cols="12" md="4" class="py-0">
                    <v-text-field
                      class="ondajiInput"
                      dense
                      outlined
                      v-mask="'###-##-#####'"
                      v-model="businessNumber"
                      :rules="checkRule.businessNumberRules"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" class="pb-3 pt-0 py-md-0">
                    <Buttons-default
                      btnType="primary"
                      :size="40"
                      btnText="중복확인"
                      @click="duplicateBusinessNumber"
                    />
                  </v-col>
                </v-row>
              </v-container>
            </div>
            <div class="ondaji_font_16_bold_brown_80 cardTitle pt-md-0">
              비밀번호
            </div>
            <v-divider class="mx-4" />
            <div class="ondaji_font_16_thin_grey_80 cardContent">
              <v-text-field
                v-model="formData.memberPw"
                class="ondajiInput"
                type="password"
                dense
                :rules="checkRule.passwordRules"
                outlined
              ></v-text-field>
            </div>
            <div class="cardContent">
              <Buttons-default
                btnType="request"
                :rounded="false"
                :size="40"
                btnText="전환하기"
                @click="doSave"
              />
            </div>
            <Vuetify-snackbar v-model="snackbar" />
          </v-card> </v-col
      ></v-row>
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
  name: "changebusiness",
  props: {
    value: {},
  },
  created() {
    this.logininfo = this.$BaseCommon.memberInfo.getMember()
    this.formData.companyName = this.logininfo.member.companyName
    this.formData.branchName = this.logininfo.member.branchName
    this.formData.businessType = this.logininfo.member.businessType
    this.businessNumber = this.logininfo.member.businessNumber
    this.oriCompanyName = this.companyName
    this.oriBranchName = this.branchName
  },
  data() {
    return {
      valid: true,
      logininfo: {},
      oriCompanyName: "",
      oriBranchName: "",
      isBusinessError: false,
      businessNumber: "",
      chkDuplicate: false,
      chkDuplicateBusinessNumber: false,
      formData: {
        companyName: "",
        branchName: "",
        businessType: "",
        businessNumber: "",
        memberPw: "",
      },
      checkRule: {
        companyNameRules: [(v) => !!v || "닉네임(로컬러)1을 입력하세요"],
        branchNameRules: [(v) => !!v || "닉네임(로컬러)2를 입력하세요"],
        businessNumberRules: [(v) => !!v || "사업자번호를 입력하세요"],
        passwordRules: [(v) => !!v || "비밀번호를 입력하세요"],
      },
      snackbar: {
        isView: false,
        text: "",
      },
    }
  },
  methods: {
    duplicatenickname() {
      this.duplicateCheck({
        companyName: this.formData.companyName,
        branchName: this.formData.branchName,
        chkMemberCode: this.logininfo.member.memberCode,
      })
    },
    duplicateBusinessNumber() {
      this.formData.businessNumber = this.businessNumber.replaceAll("-", "")
      this.duplicateCheck({
        businessNumber: this.businessNumber.replaceAll("-", ""),
        chkMemberCode: this.logininfo.member.memberCode,
      })
    },
    duplicateCheck(cond) {
      this.$BaseCommon.restful
        .call2("/member/dupconfirmcompany", cond)
        .then((res) => {
          if (res === 1) {
            this.$alert(
              this.$msg.getMsg("L007")
            ) /**중복되는 데이터가 있습니다.*/
            if (cond.businessNumber === undefined) {
              this.chkDuplicate = false
            } else {
              this.chkDuplicateBusinessNumber = true
            }
          } else {
            this.$alert(this.$msg.getMsg("L006")) /**정상등록이 가능합니다."*/
            if (cond.businessNumber === undefined) {
              this.oriCompanyName = this.formData.companyName
              this.oriBranchName = this.formData.branchName
              this.chkDuplicate = true
            } else {
              this.oriBusinessNumber = this.formData.businessNumber
              this.chkDuplicateBusinessNumber = true
            }
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    doSave() {
      if (this.formData.businessType === null) this.isBusinessError = true
      if (!this.$refs.form.validate()) {
        return
      }
      if (!this.chkDuplicate) {
        this.snackbar = {
          isView: true,
          text: "닉네임 중복체크를 하여 주시기 바랍니다.",
          title: "<span style='color:red'>경고</span>",
          isError: true,
        }
        this.oriPassword = ""

        return
      }

      if (!this.chkDuplicateBusinessNumber) {
        this.snackbar = {
          isView: true,
          text: "사업자번호 중복체크를 하여 주시기 바랍니다.",
          title: "<span style='color:red'>경고</span>",
          isError: true,
        }
        return
      }

      this.formData.businessNumber = this.businessNumber.replaceAll("-", "")
      this.$BaseCommon.restful
        .call2("/membercompany/changeBusiness", this.formData)
        .then((res) => {
          if (res.resultType === "Error") {
            this.$alert(res.message)
          } else {
            console.log(res)
            /** 세션정보 다시 셋팅 */
            this.$BaseCommon.memberInfo.getMyInfo().then((res) => {
              this.logininfo = res

              this.$alert("비즈니스 계정전환이 되었습니다.").then(() => {
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
