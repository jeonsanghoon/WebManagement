<template>
  <v-container class="pa-0">
    <v-col offset-sm="1">
      <v-form ref="inputForm">
        <v-row justify="start" no-gutters>
          <v-col class="d-flex justify-start mt-sm-2" cols="12" sm="3" lg="2">
            <span>이름</span>
          </v-col>
          <v-col cols="12" sm="5" lg="4">
            <v-text-field
              placeholder="성 이름"
              v-model="info.name"
              :rules="[nameRules]"
              maxlength="12"
              filled
              rounded
              dense
              required
            >
            </v-text-field>
          </v-col>
        </v-row>

        <v-row justify="start" no-gutters>
          <v-col class="d-flex justify-start mt-sm-2" cols="12" sm="3" lg="2">
            <span>아이디</span>
          </v-col>
          <v-col cols="12" sm="5" lg="3">
            <v-text-field
              placeholder="ondaji"
              v-model="emailId"
              :rules="[emailIdRules]"
              maxlength="24"
              filled
              rounded
              dense
              required
              :readonly="login"
            ></v-text-field>
          </v-col>

          <v-col
            class="d-flex"
            cols="12"
            sm="5"
            lg="3"
            offset-sm="3"
            offset-lg="0"
          >
            <span class="mr-2 mt-1 mx-lg-2">@</span>
            <v-combobox
              class="dotComboBox"
              placeholder="직접 입력"
              v-model="emailDot"
              :items="emailItems"
              :rules="[emailDotRules]"
              maxlength="24"
              :search-input.sync="emailDot"
              filled
              rounded
              dense
              required
              :readonly="login"
            >
            </v-combobox>
          </v-col>
          <v-col
            class="d-flex justify-end justify-sm-start mx-sm-3"
            cols="12"
            sm="2"
          >
            <v-btn
              class="none-select-button"
              rounded
              text
              height="2.7em"
              @click="dupComConfirm(confirmId, info.id)"
            >
              중복확인
            </v-btn>
          </v-col>
        </v-row>

        <v-row justify="start" no-gutters>
          <v-col class="d-flex justify-start mt-sm-2" cols="12" sm="3" lg="2">
            <span>비밀번호</span>
          </v-col>
          <v-col cols="12" sm="5" lg="4">
            <v-text-field
              placeholder="영문,숫자,특수문자 혼용 8자 이상"
              v-model="info.password"
              :rules="[passwordRules]"
              maxlength="16"
              type="password"
              filled
              rounded
              dense
              required
            >
            </v-text-field>
          </v-col>
        </v-row>

        <v-row justify="start" no-gutters>
          <v-col class="d-flex justify-start mt-sm-2" cols="12" sm="3" lg="2">
            <span>비밀번호 확인</span>
          </v-col>
          <v-col v-if="passwordConfirm.check === true" cols="11" sm="5" lg="4">
            <v-text-field
              placeholder="동일한 비밀번호 입력"
              type="password"
              :rules="[passwordConfirmRules]"
              maxlength="16"
              filled
              rounded
              dense
              required
            >
            </v-text-field>
          </v-col>

          <v-col v-if="passwordConfirm.check === false" cols="12" sm="5" lg="4">
            <v-text-field
              placeholder="동일한 비밀번호 입력"
              type="password"
              :rules="[passwordConfirmRules]"
              maxlength="16"
              filled
              rounded
              dense
              required
            >
            </v-text-field>
          </v-col>
          <v-col
            v-if="passwordConfirm.check === true"
            class="d-flex mt-n6 mx-sm-3 mx-lg-2 pl-3 pl-sm-0 "
            cols="1"
          >
            <v-icon color="#6c5f5c">check_circle</v-icon>
          </v-col>
        </v-row>

        <v-row justify="start" no-gutters>
          <v-col class="d-flex justify-start mt-sm-2" cols="12" sm="3" lg="2">
            <span>생년월일</span>
          </v-col>
          <v-col cols="12" sm="5" lg="3">
            <v-menu
              ref="calendar"
              v-model="calendar"
              :close-on-content-click="false"
              transition="slide-y-transition"
              offset-y
              min-width="18.0em"
            >
              <template v-slot:activator="{ on }">
                <v-text-field
                  placeholder="생년월일 선택"
                  v-on="on"
                  v-model="info.birthDate"
                  :rules="[birthDateRules]"
                  readonly
                  filled
                  rounded
                  dense
                  required
                  @click="selectCurrent"
                ></v-text-field>
              </template>
              <v-date-picker
                v-model="date"
                color="#6c5f5c"
                @input="calendar = false"
              ></v-date-picker>
            </v-menu>
          </v-col>

          <v-col class="mt-sm-3" cols="12" sm="12" lg="1"> </v-col>

          <v-col
            class="d-flex justify-start mt-2 mt-sm-2"
            cols="12"
            sm="3"
            lg="1"
          >
            <span>연락처</span>
          </v-col>
          <v-col cols="12" sm="5" lg="3">
            <Vuetify-phoneInput
              :value="info.phoneNumber"
              :info="{ rounded: true, filled: true }"
              @phoneInputCallback="phoneInputCallback"
            >
            </Vuetify-phoneInput>
          </v-col>
        </v-row>
      </v-form>
    </v-col>

    <v-row class="pt-10" justify="center" no-gutters>
      <div align="center">
        <v-btn
          v-if="login === false"
          class="none-select-button mb-2 mx-4 mx-sm-4 mx-lg-8"
          rounded
          width="12em"
          large
          text
          @click="selectBtn(-1)"
        >
          뒤로가기
        </v-btn>
        <v-btn
          class="select-button mb-2 mx-4 mx-sm-4 mx-lg-8"
          rounded
          width="12em"
          large
          text
          @click="selectBtn(+1)"
        >
          확인
        </v-btn>
      </div>
    </v-row>

    <v-bottom-sheet v-model="dupCheck">
      <v-sheet class="py-5 text-center">
        <div class="pb-4">
          <span>{{ errStr }}</span>
        </div>
        <v-btn
          class="none-select-button"
          rounded
          text
          width="12em"
          height="2.8em"
          @click="dupCheck = false"
        >
          닫기
        </v-btn>
      </v-sheet>
    </v-bottom-sheet>
  </v-container>
</template>

<script>
import { commonReg } from "../mixins/commonMixins"
import { regCheck } from "../mixins/regCheckMixins"
import { dupCheck } from "../mixins/dupCheckMixins"

export default {
  mixins: [commonReg, regCheck, dupCheck],
  data() {
    return {
      info: {
        name: "",
        id: "",
        password: "",
        birthDate: "",
        phoneNumber: "",
      },

      // watch 사용을 위해 변수 따로 선언
      // 글자 변경이 있다면 중복확인 필요
      emailId: "",
      emailDot: "",

      // select 박스 감지를 위해 선언
      emailDotInit: false,
      emailItems: ["google.com", "naver.com", "daum.net", "nate.com"],

      date: "2000-01-01",
      calendar: false,

      passwordConfirm: { check: false },

      confirmId: {
        err: false,
        dupCheck: false,
        idErr: false,
        dotErr: false,
        name: "아이디",
      },

      nameRules: () => {
        return this.checkName(this.info.name)
      },
      emailIdRules: () => {
        return this.checkEmailId(this.emailId, this.confirmId)
      },
      emailDotRules: () => {
        return this.checkEmailDot(
          this.emailDot,
          this.confirmId,
          this.emailDotInit
        )
      },
      passwordRules: () => {
        return this.checkPassword(this.info.password)
      },
      passwordConfirmRules: (v) => {
        return this.checkPasswordConfirm(
          v,
          this.info.password,
          this.passwordConfirm
        )
      },
      birthDateRules: () => {
        return this.checkbirthDate(this.info.birthDate)
      },
    }
  },
  created() {
    if (this.loginCheck() === true) {
      this.info.name = this.loginInfo.memberName
      this.emailId = this.loginInfo.memberId.split("@")[0]
      this.emailDot = this.loginInfo.memberId.split("@")[1]
      this.info.id = this.loginInfo.memberId
      this.info.birthDate = this.moment(this.loginInfo.birthDate).format(
        "YYYY-MM-DD"
      )
      this.date = this.info.birthDate
      this.info.phoneNumber = this.loginInfo.phone
    }
  },
  watch: {
    date() {
      this.info.birthDate = this.date
    },
    emailId() {
      this.setDupCheck(this.confirmId, false)
    },
    emailDot() {
      if (
        this.checkNull(this.emailDot) === false &&
        this.emailDotInit === false
      ) {
        this.emailDotInit = true
      }

      this.confirmId.dupCheck = false
    },
  },
  methods: {
    selectCurrent() {
      this.info.birthDate = this.date
    },
    dupComConfirm(dupConfirm, val) {
      if (dupConfirm.err === false) {
        return
      }

      if (dupConfirm.name === "아이디") {
        this.info.id = `${this.emailId}@${this.emailDot}`

        val = this.info.id
      }

      this.dupConfirm("/member/dupconfirm", dupConfirm, val)
    },
    selectBtn(page) {
      this.emailDotInit = true

      if (page < 0) {
        this.$emit("inputInfo", page)
      } else if (page > 0) {
        if (
          this.$refs.inputForm.validate() === true &&
          this.checkComDup() === true
        ) {
          this.info.emailId = this.emailId
          this.info.emailDot = this.emailDot

          this.$emit("inputInfo", page, this.info)
        }
      }
    },
    checkComDup() {
      const dupArr = [this.confirmId]

      return this.checkDup(dupArr)
    },
    phoneInputCallback(val) {
      this.info.phoneNumber = val
    },
  },
}
</script>

<style lang="scss" scoped>
@import "../styles/commonStyle.scss";

::v-deep {
  input::placeholder {
    font-size: 0.75em;
    color: #b8b8b8 !important;
  }
}
</style>
