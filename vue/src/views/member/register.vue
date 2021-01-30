<template>
  <v-container fluid grid-list-xl>
    <v-layout wrap>
      <v-flex xs12 md12 class="pb-0">
        <v-breadcrumbs class="pl-0" :items="items">
          <template v-slot:divider>
            <v-icon>mdi-chevron-right</v-icon>
          </template>
        </v-breadcrumbs>
      </v-flex>
      <v-flex xs12 md12 class="pt-0">
        <h3 class="text-left pa">회원등록</h3>
      </v-flex>
      <v-flex xs12 md12>
        <v-card class="pl-5 pr-5 pb-7 blue-grey lighten-5">
          <v-layout wrap class="pt-3">
            <v-flex xs12 md8>
              <v-alert
                :color="messageInfo.type"
                icon="check_circle"
                :value="messageInfo.value"
                v-if="messageInfo.value"
              >
                {{ messageInfo.message }}
              </v-alert>
              <v-form ref="form" lazy-validation>
                <v-container py-0>
                  <v-layout wrap>
                    <v-flex xs12 md6>
                      <v-text-field
                        v-model="formData.memberId"
                        prepend-icon="email"
                        label="아이디(E-mail)"
                        :rules="checkRule.emailRules"
                        class="purple-input"
                        required
                        @change="memberIdChange"
                      />
                    </v-flex>
                    <v-flex xs12 md6>
                      <v-text-field
                        v-model="formData.memberName"
                        prepend-icon="account_box"
                        label="사용자이름"
                        class="purple-input"
                      />
                    </v-flex>
                    <v-flex xs12 md6>
                      <v-text-field
                        v-model="formData.memberPw"
                        label="암호"
                        prepend-icon="lock"
                        type="password"
                        :rules="checkRule.passwordRules"
                        required
                      />
                    </v-flex>
                    <v-flex xs12 md6>
                      <v-text-field
                        v-model="formData.memberPwConfirm"
                        label="암호확인"
                        prepend-icon="lock"
                        type="password"
                        :rules="checkRule.pwdConfirmRules"
                        required
                      />
                    </v-flex>
                    <v-flex xs12 md6>
                      <Vuetify-datePicker
                        :options="birthDateOption"
                        :value="birthDateOption.value"
                        @dateChange="dateChange"
                      />
                    </v-flex>
                    <v-flex xs12 md6>
                      <Vuetify-selectsCommon
                        :options="selectOption"
                        :value="selectOption.value"
                        class="mt-4"
                        @selectChange="selectChange"
                      ></Vuetify-selectsCommon>
                    </v-flex>
                  </v-layout>
                </v-container>
              </v-form>
            </v-flex>
            <v-flex xs12 md4>
              <Vuetify-filepond
                :options="fileuploadOption"
                @uploadCallback="uploadCallback"
                @deleteCallback="deletefileCallback"
              />
            </v-flex>
            <v-flex xs12 md12>
              <v-btn @click="save" color="success" dark>저장</v-btn>
              <v-btn color="info" class="mr-4 ml-10" @click="reset"
                >초기화</v-btn
              >
            </v-flex>
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: "memberRegister",

  data() {
    return {
      moment: this.$moment,
      items: [
        {
          text: "회원정보",
          disabled: false,
        },
        {
          text: "회원등록",
          disabled: false,
          href: "breadcrumbs_link_1",
        },
      ],
      isIdDuplicate: false,
      formData: {
        memberCode: 0,
        memberId: "",
        memberName: "",
        memberPw: "",
        memberPwConfirm: "",
        authType: 1,
        birthDate: this.$moment(new Date()).format("YYYY-MM-DD"),
        memberImg: "",
        memberImgUrl: "",
        memberImgsize: 0,
        hide: false,
      },
      messageInfo: {
        type: "error",
        message: "",
        value: false,
      },
      checkRule: {
        emailRules: [
          (v) => !!v || "이메일을 입력하세요",
          (v) => /.+@.+\..+/.test(v) || "이메일을 입력하세요",
        ],
        passwordRules: [
          (v) => !!v || "암호를 입력하세요",
          (v) =>
            /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/.test(v) ||
            "영문자, 숫자, 특수문자 각 1자리 이상을 사용하여 8자리 이상을 입력하세요",
          /*
           v=> /[A-Z]+/.test(v) || '대문자 1자리 이상 숫자를 입력하세요',
           v=> /[0-9]+/.test(v) || '1자리 이상 숫자를 입력하세요',
           */
        ],
        pwdConfirmRules: [
          (v) => !!v || "암호를 확인하세요",
          (v) => v === this.formData.memberPw || "동일한 암호를 입력해주세요",
        ],
      },
      /** 옵션 */
      selectOption: {
        tagName: "authType",
        mainCode: "A001",
        hide: false,
        label: "권한",
        value: 1,
      },
      birthDateOption: {
        tagName: "birthDate",
        min: "1920-01-01",
        max: this.$moment(new Date()).format("YYYY-MM-DD"),
        value: "2000-01-01",
        label: "생년월일",
      },
      fileuploadOption: {
        tagName: "memberImg",
      },
    }
  },
  created() {
    console.log(this.$route.params.memberCode)
    this.doQuery(this.$route.params.memberCode)
  },
  methods: {
    reset() {
      this.$refs.form.reset()
    },
    /** 조회 메서드 */
    doQuery(memberCode) {
      console.log(memberCode)
      this.$BaseCommon.restful
        .call("/member/getList", { memberCode: memberCode })
        .then((res) => {
          if (res.length === 1) {
            console.log(this.formData)
            this.formData = res[0]
            this.selectOption.value = this.formData.authType
            this.birthDateOption.value = this.$moment(
              this.formData.birthDate
            ).format("YYYY-MM-DD")
          }
        })
    },
    /** 저장 메서드 */
    save() {
      if (!this.$refs.form.validate()) {
        return
      }
      console.log(this.formData)
      this.formData.birthDate = this.$moment(this.birthDate).format("YYYYMMDD")
      this.formData.memberPwReg = this.formData.memberPw
      this.$BaseCommon.restful
        .call("/member/save", this.formData)
        .then((res) => {
          try {
            this.formData.memberCode = Number(res.data)

            console.log(res)
            if (
              !(
                res.resultType === null ||
                res.resultType === "" ||
                res.message === ""
              )
            ) {
              this.messageInfo.value = true
              this.messageInfo.type = res.resultType.toLowerCase()
              if (res.message === null || res.message === "")
                res.message = "정상적으로 저장되었습니다."
              this.messageInfo.message = res.message
            }
          } catch (e) {
            console.log(e)
          }
        })
    },

    /**이벤트 메서드 */
    memberIdChange: function(val) {
      console.log(val)
      this.$BaseCommon.restful
        .call("/member/getList", { memberId: val })
        .then((res) => {
          if (res.length >= 1) {
            this.messageInfo.type = "warning"
            this.messageInfo.value = true
            this.messageInfo.message = `${this.formData.memberId}가 등록된 아이디가 입력되어서 초기화 하였습니다.`
            this.formData.memberId = ""
          } else {
            this.messageInfo.value = false
            this.messageInfo.message = ""
          }
        })
    },

    /** 컴포넌트 콜백 메서드 */
    selectChange: function(selectedData, name) {
      console.log(name)
      this.formData.authType = selectedData
    },
    dateChange: function(res, name) {
      console.log(name)
      this.formData.birthDate = this.$moment(res).format("YYYYMMDD")
    },
    uploadCallback: function(name, response) {
      console.log(name)
      console.log(response)

      if (response.length > 0) {
        this.formData.memberImg = response[0].oriFileName
        this.formData.memberImgurl = response[0].attachmentUrl
        this.formData.memberImgsize = response[0].fileSize
      }
    },
    deletefileCallback: function(res) {
      console.log(res)
      if (this.formData.memberCode > 0) {
        console.log(this.formData.memberCode)
      }
    },
  },
  mounted() {
    this.$store.watch(() => {
      this.$store.dispatch("baseStore/setIsShowBar", true)
    })
  },
  computed: {
    passwordConfirmationRule() {
      return () =>
        this.formData.memberPw === this.formData.memberPwConfirm ||
        "암호가 같지 않습니다."
    },
  },
}
</script>
