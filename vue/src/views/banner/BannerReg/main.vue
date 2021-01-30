<template>
  <v-container>
    <v-row align="start" justify="start">
      <v-col>
        <header>
          <Vuetify-menutitle />
        </header>

        <content>
          <template v-if="page === SELECT">
            <bannerSelectMain
              @selectDeviceType="selectDeviceType"
              @resBannerInfo="resBannerInfo"
            ></bannerSelectMain>
          </template>
          <template v-if="page === MAKE">
            <bannerMakeMain
              @backPage="backPage"
              @click:reqSave="complete"
              :deviceType="deviceType"
              :selectBanner="selectBanner"
            ></bannerMakeMain>
          </template>
          <template v-else-if="page === COMPLETE">
            <!-- 배너 보관함 작업 완료 후 해당 링크 가기 추가 필요 -->
            <bannerRegComplete
              @click:makeContinue="page = SELECT"
            ></bannerRegComplete>
          </template>
        </content>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { commonBanner } from "../mixins/commonMixins"

import bannerSelectMain from "./Select/main"
import bannerMakeMain from "./Make/main"
import bannerRegComplete from "./Complete/main"

export default {
  mixins: [commonBanner],

  components: {
    bannerSelectMain,
    bannerMakeMain,
    bannerRegComplete,
  },
  data() {
    return {
      // 페이지 순서
      // SELECT: 기기 선택
      // MAKE: 배너 제작
      // COMPLETE: 제작 완료
      SELECT: 1,
      MAKE: 2,
      COMPLETE: 3,

      page: 0,

      deviceType: 0,

      selectBanner: null,
    }
  },
  created() {
    this.page = this.SELECT
    this.deviceType = 0

    // 배너 보관함에서 배너 선택
    const mainBannerInfo = this.$route.params.mainBannerInfo

    if (this.checkNull(mainBannerInfo) === false) {
      const subBannerInfo = this.$route.params.subBannerInfo
      this.setBanner(mainBannerInfo, subBannerInfo)
    }
  },
  methods: {
    selectDeviceType(type) {
      this.selectBanner = null
      this.deviceType = type
      this.page = this.MAKE
    },
    complete(val) {
      if (val === true) {
        this.page = this.COMPLETE
      }
    },
    backPage() {
      this.page = this.SELECT
      this.deviceType = 0
    },
    resBannerInfo(selectBanner, type) {
      this.selectDeviceType(type)
      this.selectBanner = selectBanner
    },
    setBanner(main, sub) {
      this.selectBanner = new Object()

      this.selectBanner.mainBanner = main
      this.selectBanner.keywords = sub.keywords
      this.selectBanner.places = sub.places
      this.selectBanner.placeSquare = sub.placeSquare
      this.selectBanner.subBanner = sub.bannerSub
      this.selectBanner.bannerStoreSelectCheck = true

      this.deviceType = main.deviceType2 - 1
      this.page = this.MAKE

      this.resBannerInfo(this.selectBanner, this.deviceType)
    },
  },
  mounted() {
    this.$store.watch(() => {
      this.$store.dispatch("baseStore/setIsShowBar", true)
    })
  },
}
</script>

<style lang="scss" scoped>
.banner-background {
  background-color: #fafafa;
}
</style>
