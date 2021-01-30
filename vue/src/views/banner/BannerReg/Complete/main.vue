<template>
  <v-container class="pa-0" fluid>
    <v-row class="px-4 px-sm-0" align="center" no-gutters style="height: 83vh">
      <v-col offset-sm="1" offset-lg="2">
        <header>
          <v-row no-gutters>
            <div align="start">
              <span class="complete-title">{{ title }}</span>
            </div>
          </v-row>
        </header>

        <content>
          <v-row class="mt-4 mt-sm-4" no-gutters style="height: 15vh">
            <v-col cols="12" align="start">
              <v-row no-gutters>
                <v-col cols="12" sm="8" lg="5">
                  <span class="complete-content">
                    {{ subTitle }}
                  </span>
                </v-col>
                <v-col
                  class="d-flex align-sm-end justify-sm-end"
                  cols="12"
                  sm="2"
                  lg="1"
                >
                  <span class="complete-caption" @click="dialog = !dialog">
                    {{ guide }}
                  </span>
                </v-col>
              </v-row>
            </v-col>
          </v-row>

          <v-row class="mt-10 mt-sm-0" justify="center" no-gutters>
            <v-col class="d-sm-flex justify-sm-start" cols="8" sm="12">
              <bannerBtn :info="btnContinueInfo" @click:button="makeContinue">
                배너제작 계속
              </bannerBtn>
              <bannerBtn
                :info="btnCompleteInfo"
              >
                배너보관함 가기
              </bannerBtn>
            </v-col>
          </v-row>
        </content>
      </v-col>
    </v-row>
    <!-- modal -->
    <v-row justify="center" no-gutters>
      <bannerCompleteModal :dialog.sync="dialog"></bannerCompleteModal>
    </v-row>
  </v-container>
</template>

<script>
import bannerCompleteModal from './components/completeModal'
import bannerBtn from '../../components/bannerBtn'

export default {
  components: {
    bannerCompleteModal,
    bannerBtn,
  },
  data() {
    return {
      title: `배너 제작이 완료되었습니다!`,
      subTitle: `배너보관함에서 서비스를 활성화한 후, 미디어 작업실에서 배너 게시를 해야 온닫이 
        사이트 또는 사이니지에 노출이 되어 광고 효과가 발생합니다.`,
      guide: `가이드 보기`,
      dialog: false,

      btnContinueInfo: {},
      btnCompleteInfo: {},
    }
  },
  created() {
    this.btnContinueInfo.class = `lighten-brown-btn mb-4 mb-sm-0`
    this.btnContinueInfo.width = `12rem`
    this.btnContinueInfo.height = `2.5rem`
    this.btnContinueInfo.rounded = true

    this.btnCompleteInfo.class = `darken-brown-btn offset-sm-1`
    this.btnCompleteInfo.width = `12rem`
    this.btnCompleteInfo.height = `2.5rem`
    this.btnCompleteInfo.rounded = true
    this.btnCompleteInfo.to = "/banner/store"
  },
  methods: {
    makeContinue() {
      this.$emit('click:makeContinue')
    },
  },
}
</script>

<style lang="scss" scoped>
span {
  color: #3e2723;
}

.complete-title {
  font-weight: 700;
  font-size: 1.714rem;
}

.complete-sub-title {
  font-weight: 500;
  font-size: 1.286rem;
}

.complete-caption {
  font-weight: 700;
  color: #2962ff;
  border-bottom: 1px solid #2962ff;
  cursor: pointer;
}
</style>
