<template>
  <v-container v-if="info != undefined" class="pa-0">
    <v-row class="mb-6" justify="center" no-gutters>
      <v-col class="d-flex justify-center mb-6" cols="12">
        <span v-if="login === true">수정이 완료되었습니다!</span>
        <span v-else>온닫이 이웃이 되어 주셔서 감사합니다!</span>
      </v-col>
    </v-row>

    <v-row class="ma-auto" justify="center">
      <v-col
        class="complete-background flex-shrink-1 px-6 px-sm-12 px-lg-12"
        cols="10"
        sm="auto"
        lg="auto"
      >
        <v-row
          class="justify-sm-start justify-lg-start pt-6 px-sm-6 px-lg-12"
          no-gutters
          justify="center"
        >
          <v-col
            class="d-flex justify-center mr-sm-12 mr-lg-12"
            cols="12"
            sm="auto"
            lg="auto"
          >
            <span>이메일</span>
          </v-col>
          <v-col class="text-truncate ml-sm-4 ml-lg-6" cols="auto">
            <span>{{ info.emailId }}</span>
          </v-col>
          <v-col cols="auto">
            <span>@</span>
          </v-col>
          <v-col class="text-truncate" cols="auto">
            <span>{{ info.emailDot }}</span>
          </v-col>
        </v-row>
        <v-row
          class="justify-sm-start justify-lg-start py-6 px-sm-6 px-lg-12"
          no-gutters
          justify="center"
        >
          <v-col class="d-flex justify-center" cols="12" sm="auto" lg="auto">
            <span>로컬사업자명</span>
          </v-col>
          <v-col
            class="justify-center text-truncate ml-sm-4 ml-lg-6"
            cols="auto"
          >
            <span>{{ info.nickName }}</span>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
    <v-row class="pt-12 mt-10" justify="center" no-gutters>
      <div align="center">
        <v-btn
          v-if="login === true"
          class="select-button mb-2 mx-4 mx-sm-4 mx-lg-8"
          rounded
          width="15em"
          large
          text
          to="/"
        >
          메인으로
        </v-btn>
        <v-btn
          v-else
          class="select-button mb-2 mx-4 mx-sm-4 mx-lg-8"
          rounded
          width="15em"
          large
          text
          :to="BaseCommon.memberInfo.moveLogin()"
        >
          로그인하기
        </v-btn>
      </div>
    </v-row>
  </v-container>
</template>

<script>
import { commonReg } from "./mixins/commonMixins"

export default {
  mixins: [commonReg],
  props: {
    info: {
      type: Object,
    },
  },
}
</script>

<style lang="scss" scoped>
@import "./styles/commonStyle.scss";

.complete-background {
  border: solid 1px #aea6a4;
  background-color: #f5f5f5;
  border-radius: 0.3em;
}
</style>
