<template>
  <v-container fill-height>
    <v-row align="center" style="height:inherit">
      <v-col>
        <v-row class="mt-12 py-12" align="center" justify="center">
          <v-col class="d-flex justify-center" cols="12">
            <span>해당하시는 회원 유형을 선택해주세요.</span>
          </v-col>
        </v-row>

        <v-row class="py-12" align="center" justify="center">
          <v-btn
            class="none-select-button mb-2 mx-4 mx-sm-4 mx-lg-8"
            rounded
            width="15rem"
            large
            text
            link
            to="member"
          >
            개인
          </v-btn>
          <v-btn
            class="none-select-button mb-2 mx-4 mx-sm-4 mx-lg-8"
            rounded
            width="15rem"
            large
            text
            link
            to="company"
          >
            기업·기관
          </v-btn>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { commonReg } from './mixins/commonMixins'
export default {
  mixins: [commonReg],
  data() {
    return {
      main: true,
    }
  },
}
</script>

<style lang="scss" scoped>
@import './styles/commonStyle.scss';
</style>
