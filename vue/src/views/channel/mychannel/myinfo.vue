<template>
  <v-form ref="form" v-model="valid">
    <v-container ref="myinfo">
      <v-row>
        <v-col class="componentPage">
          <v-card flat outlined px-2>
            <div class="ondaji_font_16_bold_brown_80 cardTitle">
              회원유형
            </div>
            <v-divider class="mx-4" />

            <div class="ondaji_font_16_thin_grey_80 cardContent">
              {{ logininfo.member.authTypeName }}
            </div>

            <div class="ondaji_font_16_bold_brown_80 cardTitle pt-5">
              아이디
            </div>
            <v-divider class="mx-4" />
            <div class="ondaji_font_16_thin_grey_80 cardContent">
              {{ logininfo.member.memberId }}
            </div>
            <div class="ondaji_font_16_bold_brown_80 cardTitle pt-5">
              닉네임(로컬러)
            </div>
            <v-divider class="mx-4" />
            <div class="ondaji_font_16_thin_grey_80 cardContent">
              {{ logininfo.member.nickname }}
            </div>
            <div class="localbusiness">
              <div class="ondaji_font_16_bold_brown_80 cardTitle pt-5">
                업종
              </div>
              <v-divider class="mx-4" />
              <div class="ondaji_font_16_thin_grey_80 cardContent">
                <Vuetify-selBusinessType v-model="businessType" />
              </div>
            </div>
            <div class="ondaji_font_16_bold_brown_80 cardTitle pt-5">
              연락처
            </div>
            <v-divider class="mx-4 py-0" />
            <div class="ondaji_font_16_thin_grey_80 cardContent">
              <v-text-field
                class="ondajiInput"
                dense
                v-mask="phoneMask(phone)"
                :rules="checkRule.phoneRule"
                outlined
                v-model="phone"
              ></v-text-field>
            </div>
            <div class="localbusiness">
              <div class="ondaji_font_16_bold_brown_80 cardTitle pt-0">
                주소
              </div>
              <v-divider class="mx-4" />
              <div class="ondaji_font_16_thin_grey_80 cardContent">
                <v-container class="py-0">
                  <v-row>
                    <v-col cols="6" sm="8" md="4" class="pl-0 py-0 pr-2">
                      <v-text-field
                        class="ondajiInput"
                        dense
                        outlined
                        readonly
                        v-model="zipCode"
                        @click="searchAddress"
                        :rules="checkRule.addressRule"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="6" sm="4" md="8" class="py-0 pl-2">
                      <Buttons-default
                        btnType="primary"
                        :size="40"
                        btnText="주소검색"
                        @click="searchAddress"
                      />
                    </v-col>
                    <v-col cols="12" md="6" class="py-0 px-0 pl-md-0 pr-md-2">
                      <v-text-field
                        class="ondajiInput"
                        dense
                        readonly
                        outlined
                        @click="searchAddress"
                        v-model="address"
                      >
                      </v-text-field>
                    </v-col>
                    <v-col cols="12" md="6" class="py-0 px-0 pr-md-0 pl-md-2">
                      <v-text-field
                        class="ondajiInput"
                        dense
                        outlined
                        :rules="checkRule.address2Rule"
                        v-model="addressDetail"
                      >
                      </v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
                <Kakaomap-PostCode
                  :name="''"
                  @kakaoPostCodeCallback="kakaoPostCodeCallback"
                  :dialog="dialog"
                ></Kakaomap-PostCode>
              </div>
              <div class="ondaji_font_16_bold_brown_80 cardTitle pt-0">
                {{ $msg.getMsg("W028") }}
              </div>
              <v-divider class="mx-4 py-0" />
              <div class="ondaji_font_16_thin_grey_80 cardContent">
                <v-container class="px-0 py-0">
                  <v-row
                    ><v-col cols="8" md="4" class="py-0">
                      <v-text-field
                        class="ondajiInput"
                        dense
                        outlined
                        :placeholder="$msg.getMsg('L015')"
                        v-mask="'###-##-#####'"
                        v-model="businessNumber"
                        :rules="checkRule.businessNumberRule"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="4" md="4" class="pb-3 pt-0 py-md-0">
                      <Buttons-default
                        btnType="primary"
                        :size="40"
                        :btnText="$msg.getMsg('W001') /** 중복확인 */"
                        @click="checkBusinussNumber"
                      />
                    </v-col>
                  </v-row>
                </v-container>
              </div>
            </div>

            <v-card-actions class="bottomButtons">
              <Buttons-default
                btnType="primary"
                :size="40"
                :btnText="$msg.getMsg('W027') /** 변경하기 */"
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
.ondajiInput {
  &.v-input--is-readonly {
    .v-input__slot {
      background-color: #e0e0e0 !important;

      input {
        font-size: 16px !important;
        color: #424242 !important;
        font-weight: 400 !important;
      }
    }
  }
  .v-input__slot {
    background-color: #f2f2f2 !important;

    input {
      font-size: 16px !important;
      color: #424242 !important;
      font-weight: 400 !important;
    }
  }
}
</style>
<style lang="scss" scoped>
@import "_common.scss";
.localbusiness {
  display: none;
}
</style>

<script>
export default {
  name: "myinfo",
  props: {
    value: {},
  },
  created() {
    this.logininfo = this.$BaseCommon.memberInfo.getMember()
    this.phone =
      this.logininfo.member.phone === null ? "" : this.logininfo.member.phone
    this.businessType = this.logininfo.member.businessType
    this.zipCode = this.logininfo.member.zipCode
    this.address = this.logininfo.member.address
    this.addressDetail = this.logininfo.member.addressDetail
    this.businessNumber = this.logininfo.member.businessNumber
    this.oriBusinessNumber = this.businessNumber
  },
  watch: {
    businessNumber() {
      this.isBusiness = false
    },
  },
  mounted() {
    if (this.logininfo.member.authType === 20) {
      this.$refs.myinfo.querySelectorAll(".localbusiness").forEach((el) => {
        el.style.display = "block"
      })
    } else {
      this.$refs.myinfo.querySelectorAll(".localbusiness").forEach((el) => {
        el.style.display = "none"
      })
    }
  },
  data() {
    return {
      valid: true,
      dialog: false,
      logininfo: {},
      businessType: "" /*100404*/,
      isBusiness: false,
      phone: "",
      zipCode: "",
      address: "",
      addressDetail: "",
      region: "",
      oriBusinessNumber: "",
      businessNumber: "",

      checkRule: {
        phoneRule: [
          (v) => !!v || this.$msg.getMsg("L002"),
        ] /** 연락처를 등록하세요. */,
        addressRule: [
          (v) => !!v || this.$msg.getMsg("L003"),
        ] /**주소검색을 하여 주소를 등록하세요.*/,
        address2Rule: [
          (v) => !!v || this.$msg.getMsg("L004"),
        ] /**주소상세를 등록하세요.*/,
        businessNumberRule: [
          (v) =>
            v.length == 12 ||
            this.$msg.getMsg(
              "L005"
            ) /**사업자등록번호가 유효하지 않습니다.(10자리) */,
        ],
      },
    }
  },
  methods: {
    phoneMask(val) {
      return val.substring(0, 2) === "02"
        ? val.length <= 11
          ? "##-###-####"
          : "##-####-####"
        : val.length <= 12
        ? "###-###-####"
        : "###-####-####"
    },
    doSave() {
      if (!this.$refs.form.validate()) {
        return
      }

      if (this.oriBusinessNumber !== this.businessNumber) {
        this.$alert(this.$msg.getMsg("L001"))
        return
      }

      var param = new Object()
      param.memberCode = this.logininfo.member.memberCode
      param.phone = this.phone.replaceAll("-", "")
      param.address = this.address
      param.addressDetail = this.addressDetail
      param.roadAddress = this.roadAddress
      param.zipCode = this.zipCode
      param.businessType = this.businessType
      param.businessNumber = this.businessNumber.replaceAll("-", "")
      this.$BaseCommon.restful
        .call2("/member/myInfoUpdate", param)
        .then((res) => {
          if (res.resultType === "Error") {
            this.$alert(res.message)
          } else {
            this.$alert(this.$msg.getMsg("L006")) /**"정상등록 되었습니다." */

            this.$BaseCommon.memberInfo.getMyInfo().then((res) => {
              this.logininfo = res
              this.$router.push("/")
            })
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    searchAddress() {
      this.dialog = true
    },
    kakaoPostCodeCallback(res, bConfirm) {
      this.dialog = false
      if (!bConfirm) {
        return
      }

      console.log(
        "주소 >> ",
        res.jibunAddress,
        res.autoJibunAddress,
        res.roadAddress
      )
      //this.saveData.zipCode = res.zipCode
      this.zipCode = res.zonecode
      this.address = res.address
      if (res.sido !== undefined)
        this.region = `${res.sido} ${res.sigungu} ${res.bname}`

      this.dialog = false
    },
    checkBusinussNumber() {
      var chkNum = this.businessNumber.replaceAll("-", "")

      this.$BaseCommon.restful
        .call2("/member/getList", {
          businessNumber: chkNum,
        })
        .then((res) => {
          if (
            res.length > 1 ||
            (res.length === 1 &&
              res[0].memberCode != this.logininfo.member.memberCode)
          ) {
            this.$alert(this.$msg.getMsg("L007"))
          } else {
            this.oriBusinessNumber = this.businessNumber
            this.$alert(this.$msg.getMsg("L006"))
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
  },
}
</script>
