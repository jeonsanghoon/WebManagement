<template>
  <v-container fluid grid-list-xl>
    <v-row>
      <v-col xs="12" md="12">
        <v-breadcrumbs :items="breadcrumbsItems">
          <template v-slot:divider>
            <v-icon>mdi-chevron-right</v-icon>
          </template>
        </v-breadcrumbs>
      </v-col>
    </v-row>
    <v-row>
      <v-col xs="12" md="12">
        <h3 class="text-left pa-4">회원등록</h3>
      </v-col>
    </v-row>
    <v-row>
      <v-col xs="12" md="12">
        <v-card class="pa-5 ml-5 mr-5">
          <v-row>
            <v-col xs="12" md="8">
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
                    <v-row>
                      <v-col xs="12" md="6">
                        <v-text-field
                          v-model="formData.memberId"
                          prepend-icon="email"
                          label="아이디(E-mail)"
                          :rules="checkRule.emailRules"
                          class="purple-input"
                          required
                          @change="memberIdChange"
                        />
                      </v-col>
                      <v-col xs="12" md="6">
                        <v-text-field
                          v-model="formData.memberName"
                          prepend-icon="account_box"
                          label="사용자이름"
                          class="purple-input"
                        />
                      </v-col>
                      <v-col xs="12" md="6">
                        <v-text-field
                          v-model="formData.memberPw"
                          label="암호"
                          prepend-icon="lock"
                          type="password"
                          :rules="checkRule.passwordRules"
                          required
                        />
                      </v-col>
                      <v-col xs="12" md="6">
                        <v-text-field
                          v-model="formData.memberPwConfirm"
                          label="암호확인"
                          prepend-icon="lock"
                          type="password"
                          :rules="checkRule.pwdConfirmRules"
                          required
                        />
                      </v-col>
                      <v-col xs="12" md="6">
                        <v-menu
                          ref="menu"
                          v-model="menu"
                          :close-on-content-click="false"
                          transition="scale-transition"
                          offset-y
                          min-width="290px"
                        >
                          <template v-slot:activator="{ on }">
                            <v-text-field
                              v-model="birthDate"
                              label="생년월일"
                              prepend-icon="event"
                              readonly
                              v-on="on"
                            ></v-text-field>
                          </template>
                          <v-date-picker
                            ref="picker"
                            v-model="birthDate"
                            :max="new Date().toISOString().substr(0, 10)"
                            min="1950-01-01"
                            @input="menu = false"
                          ></v-date-picker>
                        </v-menu>
                      </v-col>
                      <v-col xs="12" md="6">
                        <Vuetify-selectsCommon
                          :options="selectOption"
                          class="mt-4"
                          @selectChange="selectChange"
                        ></Vuetify-selectsCommon>
                      </v-col>
                    </v-row>
                  </v-layout>
                </v-container>
              </v-form>
            </v-col>
            <v-col xs="12" md="4">
              <vue-dropzone
                ref="myVueDropzone"
                id="dropzone"
                :options="dropzoneOptions"
                :useCustomSlot="true"
                v-on:vdropzone-success="imageUploadSuccess"
              >
                <div class="dropzone-custom-content">
                  <h3 class="dropzone-custom-title">
                    Drag and drop
                  </h3>
                  <div class="subtitle">
                    ...or click
                  </div>
                </div>
              </vue-dropzone>
            </v-col>
          </v-row>
          <v-row>
            <v-col xs="12" md="12">
              <v-btn @click="save" color="success" dark>저장</v-btn>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import vue2Dropzone from "vue2-dropzone"
import "vue2-dropzone/dist/vue2Dropzone.min.css"

export default {
  name: "memberRegister",
  components: {
    vueDropzone: vue2Dropzone,
  },
  data() {
    return {
      dropzoneOptions: {
        url: "/file/upload",
        thumbnailWidth: 300,
        addRemoveLinks: true,
        maxFilesize: 2,
        maxFiles: 1,
        headers: { "My-Awesome-Header": "header value" },
        init: function() {},
      },

      breadcrumbsItems: [
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
      menu: false,
      moment: this.$moment,
      selectOption: { mainCode: "A001", hide: false, label: "권한", value: 1 },
      birthDate: new Date().toISOString().substr(0, 10),
      isIdDuplicate: false,
      formData: {
        memberCode: 0,
        memberId: "",
        memberName: "",
        memberPw: "",
        memberPwConfirm: "",
        authType: 1,
        birthDate: new Date().toISOString().substr(0, 10),
        memberImg: "",
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
    }
  },
  watch: {
    menu(val) {
      val && setTimeout(() => (this.$refs.picker.activePicker = "YEAR"))
    },
  },
  methods: {
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
          this.formData.memberCode = Number(res.data)

          console.log(res)
          if (
            !(
              res.resultType == null ||
              res.resultType == "" ||
              res.Message == ""
            )
          ) {
            this.messageInfo.value = true
            this.messageInfo.type = res.resultType.toLowerCase()
            this.messageInfo.message = res.Message
          } else if (res.Message === "") {
            console.log("")
          }
        })
    },

    /**이벤트 메서드 */
    memberIdChange: function(val) {
      this.$BaseCommon.restful
        .call("/member/getList", { memberId: val })
        .then((res) => {
          function fn() {
            return "I am a result. Rarr"
          }
          console.log(`foo ${fn()} bar`)
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
    selectChange: function(selectedData) {
      this.formData.authType = selectedData
    },
    imageUploadSuccess: function(file, response) {
      console.log(file)
      console.log(response)

      if (response.length > 0) {
        this.formData.memberImg = response[0].fileName
        this.formData.memberImgsize = file.size
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
