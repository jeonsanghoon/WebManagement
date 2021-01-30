<template>
  <v-container class="pa-0 ma-0">
    <v-row class="mb-3" align="center" justify="start" no-gutters>
      <v-col class="d-flex justify-start" cols="12" sm="3" lg="3">
        <span>이름</span>
      </v-col>
      <v-col class="mb-2 mb-sm-0 mb-lg-0" cols="12" lg="3" sm="4">
        <v-text-field placeholder="성 이름" hide-details filled rounded dense>
        </v-text-field>
      </v-col>
    </v-row>

    <v-row class="mb-3" align="center" justify="start" no-gutters>
      <v-col class="d-flex justify-start" cols="12" sm="3" lg="3">
        <span>닉네임(로컬사업자명)</span>
      </v-col>
      <v-col class="mb-2 mb-sm-0 mb-lg-0" cols="12" sm="5" lg="4">
        <v-text-field
          placeholder="한글/영문만 사용하여 입력"
          hide-details
          filled
          rounded
          dense
        ></v-text-field>
      </v-col>
      <v-col
        class="d-flex justify-end justify-sm-start justify-lg-start ml-sm-3 ml-lg-3"
        cols="12"
        sm="2"
        lg="2"
      >
        <v-btn class="hover-button" rounded text>
          중복확인
        </v-btn>
      </v-col>
    </v-row>

    <v-row class="mb-3" align="center" justify="start" no-gutters>
      <v-col class="d-flex justify-start" cols="12" sm="3" lg="3">
        <span>아이디</span>
      </v-col>
      <v-col class="mb-2 mb-sm-0 mb-lg-0" cols="10" sm="3" lg="3">
        <v-text-field
          placeholder="ondaji"
          hide-details
          filled
          rounded
          dense
        ></v-text-field>
      </v-col>
      <span class="mx-2 d-flex">@</span>
      <v-col class="mb-2 mb-sm-0 mb-lg-0" cols="12" sm="3" lg="3">
        <v-combobox
          placeholder="ondaji.com"
          v-model="emailSelect"
          :items="emailItems"
          hide-details
          filled
          rounded
          dense
        >
        </v-combobox>
      </v-col>
      <v-col
        class="d-flex justify-end justify-sm-start justify-lg-start ml-sm-3 ml-lg-3"
        cols="12"
        sm="2"
        lg="2"
      >
        <v-btn class="hover-button" rounded text>
          중복확인
        </v-btn>
      </v-col>
    </v-row>

    <v-row class="mb-3" align="center" justify="start" no-gutters>
      <v-col class="d-flex justify-start" cols="12" sm="3" lg="3">
        <span>비밀번호</span>
      </v-col>
      <v-col class="mb-2 mb-sm-0 mb-lg-0" cols="12" sm="5" lg="4">
        <v-text-field
          type="password"
          placeholder="8-12자 영문,숫자,특수문자만 가능"
          :rules="[rules.passwordRule, rules.min]"
          hide-details="auto"
          filled
          rounded
          dense
        >
        </v-text-field>
      </v-col>
    </v-row>

    <v-row class="mb-3" align="center" justify="start" no-gutters>
      <v-col class="d-flex justify-start" cols="12" sm="3" lg="3">
        <span>비밀번호 확인</span>
      </v-col>
      <v-col class="mb-2 mb-sm-0 mb-lg-0" cols="12" sm="5" lg="4">
        <v-text-field
          type="password"
          placeholder="동일한 비밀번호 입력"
          hide-details
          filled
          rounded
          dense
        >
        </v-text-field>
      </v-col>
    </v-row>

    <v-row class="mb-3" align="center" justify="start" no-gutters>
      <v-col class="d-flex justify-start" cols="12" sm="3" lg="3">
        <span>생년월일</span>
      </v-col>
      <v-col class="mb-2 mb-sm-0 mb-lg-0" cols="12" sm="4" lg="3">
        <v-text-field placeholder="990101" hide-details filled rounded dense>
        </v-text-field>
      </v-col>

      <v-col class="mt-sm-3" cols="12" sm="12" lg="2"></v-col>

      <v-col class="d-flex justify-start" cols="12" sm="3" lg="1">
        <span>연락처</span>
      </v-col>
      <v-col class="mb-2 mb-sm-0 mb-lg-0" cols="12" sm="4" lg="3">
        <v-text-field
          placeholder="'-'표시 없이 숫자만 입력"
          hide-details
          filled
          rounded
          dense
        >
        </v-text-field>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      emailSelect: "",
      emailItems: ["google.com", "naver.com", "daum.net", "nate.com"],
      rules: {
        passwordRule: (value) =>
          !!value ||
          this.$msg.getMsg("L008") /** 8-12자 영문,숫자,특수문자만 가능 */,
        required: (value) => !!value || "Required.",
        // min: (v) => v.length >= 12 || '8-12자의 영문,숫자,특수문자만 가능',
        emailMatch: () => "The email and password you entered don't match",
      },
    }
  },
}
</script>

<style lang="scss" scoped>
span {
  font-weight: bold;
  color: #6c5f5c;
}

.hover-button {
  color: white;
  background: #aea6a4;
}
.hover-button:hover {
  background: #6c5f5c;
}

::v-deep {
  .v-input__icon {
    padding-bottom: 0.5em;
  }
}
</style>
