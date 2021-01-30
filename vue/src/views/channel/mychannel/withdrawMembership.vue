<template>
  <v-form ref="form" v-model="valid">
    <v-container>
      <v-row>
        <v-col class="componentPage">
          <v-card flat outlined class="text-left pa-10">
            <div class="ondaji_font_16_medium_grey_70">
              {{ logininfo.member.memberName }}
              {{ $msg.getMsg("W021") /** 로컬러님 */ }}
            </div>
            <div class="ondaji_font_16_thin_grey_70 pt-4">
              {{
                $msg.getMsg(
                  "L011"
                ) /** 지금까지 온닫이와 함께 해주셔서 감사합니다. */
              }}
            </div>
            <div class="ondaji_font_16_thin_grey_70">
              {{
                $msg.getMsg(
                  "L012"
                ) /**온닫이의 부족한 점이 있었다면 너그러운 양해를 부탁드립니다. */
              }}
            </div>
            <div class="ondaji_font_16_thin_grey_70 pt-4">
              {{
                $msg.getMsg(
                  "L013"
                ) /**아래 사항을 확인하시어 개선점을 남겨주세요. */
              }}
            </div>
            <div class="ondaji_font_16_thin_grey_70">
              {{
                $msg.getMsg(
                  "L014"
                ) /** 고객님의 의견을 적극 반영하여 더 좋은 서비스를 위해
              노력하겠습니다. */
              }}
            </div>
            <div class="ondaji_font_16_medium_brown_80 pt-6 pb-1">
              {{ $msg.getMsg("W020") /** 안내사항 */ }}
            </div>
            <v-divider />
            <div>
              <div class="ondaji_font_16_thin_grey_70 pt-1">
                {{
                  $msg.getMsg(
                    "L009"
                  ) /**첫 번째, 사용하고 계신 아이디는 탈퇴할 경우 3개월간 재사용이 불가능합니다. */
                }}
              </div>
              <div class="ondaji_font_16_thin_grey_70">
                {{
                  $msg.getMsg(
                    "L010"
                  ) /**두 번째, 탈퇴 이후 등록한 게시물 등 이용 기록이 모두 삭제되어 복구할 수 없습니다. */
                }}
              </div>
            </div>
            <div class="ondaji_font_16_medium_brown_80 pt-6 pb-1">
              {{ $msg.getMsg("W018") /**탈퇴사유 */ }} ({{
                $msg.getMsg("W022") /**복수선택가능 */
              }})
            </div>
            <v-divider />
            <div>
              <v-checkbox
                hide-details
                class="ondaji_font_16_thin_grey_70"
                color="#3e2723"
                v-model="formData.is_reason1"
                :label="$msg.getMsg('W023') /**이용률 감소*/"
              ></v-checkbox>
              <v-checkbox
                hide-details
                class="ondaji_font_16_thin_grey_70"
                color="#3e2723"
                v-model="formData.is_reason2"
                :label="$msg.getMsg('W024') /**서비스 이용이 불편함*/"
              ></v-checkbox>
              <v-checkbox
                hide-details
                class="ondaji_font_16_thin_grey_70"
                color="#3e2723"
                v-model="formData.is_reason3"
                :label="$msg.getMsg('W025') /**광고 효과 불만족*/"
              ></v-checkbox>
              <v-checkbox
                class="ondaji_font_16_thin_grey_70"
                color="#3e2723"
                hide-details
                v-model="formData.is_reason4"
                :label="$msg.getMsg('W026') /**타서비스 이용*/"
              ></v-checkbox>
              <div
                class="v-messages__message error--text pt-1"
                v-if="
                  !formData.is_reason1 &&
                  !formData.is_reason2 &&
                  !formData.is_reason3 &&
                  !formData.is_reason4 &&
                  isCheck
                "
              >
                {{
                  $msg.formatString(
                    "B007",
                    "W018"
                  ) /**           탈퇴사유를 1개이상 선택하여 주세요 */
                }}
              </div>
            </div>
            <div class="ondaji_font_16_medium_brown_80 pt-6 pt-2">
              {{ $msg.getMsg("W019") }}
            </div>
            <v-divider />
            <v-textarea
              outlined
              v-model="formData.remark"
              color="#3e2723"
              :rules="checkRule.remarkRule"
              class="ondajiInput pt-2"
            ></v-textarea>
            <div class="ondaji_font_16_medium_brown_80">
              {{ $msg.getMsg("W004") }}
            </div>
            <v-divider />
            <v-text-field
              type="password"
              class="ondajiInput pt-2"
              color="#3e2723"
              dense
              outlined
              v-model="formData.memberPw"
              :rules="checkRule.passwordRules"
            ></v-text-field>
            <v-card-actions class="bottomButtons">
              <Buttons-default
                btnType="primary"
                :size="40"
                btnText="탈퇴하기"
                @click="doSave"
              />
            </v-card-actions>
          </v-card> </v-col></v-row
    ></v-container>
  </v-form>
</template>
<style lang="scss">
.componentPage {
  .v-input--selection-controls .v-input__slot > .v-label,
  .v-input--selection-controls .v-radio > .v-label {
    font-size: 16px;
    color: #616161;
  }
}
</style>
<style lang="scss" scoped>
.v-messages__message.error--text {
  font-size: 0.75rem !important;
}
</style>
<script>
export default {
  created() {
    this.logininfo = this.$BaseCommon.memberInfo.getMember()
  },
  watch: {
    formData() {
      this.isCheck = true
    },
  },
  data() {
    return {
      valid: true,
      isCheck: false,
      logininfo: null,
      formData: {
        is_reason1: false,
        is_resaon2: false,
        is_reason3: false,
        is_reason4: false,
        remark: "",
        memberPw: "",
        waiting_day: 0,
      },
      checkRule: {
        remarkRule: [(v) => !!v || this.$msg.formatString("B002", "W017")],
        passwordRules: [(v) => !!v || this.$msg.formatString("B005", "W004")],
        selectRules: [
          (v) =>
            (!!this.is_reason1 &&
              !!this.is_reason2 &&
              !!this.is_reason3 &&
              !!v) ||
            this.$msg.formatString("B007"),
        ],
      },
    }
  },
  methods: {
    doSave() {
      this.isCheck = true
      if (!this.$refs.form.validate()) {
        return
      }
      this.$alert(this.$msg.formatString("B003", "W007"), {
        /** B003 정말로 [0] 하시겠습니까?  W007 회원 탈퇴 */
        alertType: "del",
        yesBtnName: this.$msg.getMsg("W007"),
      }).then((res) => {
        if (res) {
          console.log("콜백처리 True", res)

          this.afterSave()
        } else {
          console.log("콜백처리 False", res)
        }
      })
    },
    afterSave() {
      this.$BaseCommon.restful
        .call2("/memberwithdraw/save", this.formData)
        .then((res) => {
          if (res.resultType === "Error") {
            this.$alert(res.message)
          }
        })
        .catch((err) => {
          this.$alert(err)
        })
    },
  },
}
</script>
