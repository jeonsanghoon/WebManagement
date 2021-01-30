<template>
  <v-container class="pa-0" fluid>
    <v-row no-gutters>
      <v-col>
        <Vuetify-bannerUpload v-model="uploadModel" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { commonBanner } from "../../../mixins/commonMixins"

export default {
  mixins: [commonBanner],
  props: {
    deviceType: {
      type: Number,
      required: true,
      default: 0,
    },
    selectBanner: {
      type: Object,
      required: false,
      default: null,
    },
  },
  data() {
    return {
      uploadModel: {
        bannerList: [],
        regType: 0,
      },

      signageType: 0,
    }
  },
  created() {
    this.$EventBus.$on("selectSignageType", (select) => {
      this.signageType = select

      // 0: 가로
      // 1: 세로
      if (this.signageType === 0) {
        this.uploadModel.regType = 2
      } else {
        this.uploadModel.regType = 1
      }

      this.uploadModel = Object.assign({}, this.uploadModel)
    })

    this.initRegType()

    if (this.selectBanner != null) {
      this.initSubBanner()
    }
  },
  methods: {
    initSubBanner() {
      const mainBanner = this.selectBanner.mainBanner
      const subBanner = this.selectBanner.subBanner

      this.uploadModel = {
        bannerCode: mainBanner.bannerCode,
        title: mainBanner.title,
        subTitle: mainBanner.subTitle,
        content: mainBanner.content,
        bannerList: subBanner,
      }

      this.initRegType()

      this.initSubBannerOther(mainBanner, subBanner)
    },
    initSubBannerOther(mainBanner, subBanner) {
      if (this.checkNull(mainBanner.playType) === false) {
        this.uploadModel.playType = mainBanner.playType
      }

      if (this.checkNull(mainBanner.bannerImageUrl) === false) {
        this.uploadModel.bannerImageUrl = mainBanner.bannerImageUrl
      }

      if (this.checkNull(mainBanner.thumbImageUrl) === false) {
        this.uploadModel.thumbImageUrl = mainBanner.thumbImageUrl
      }

      if (this.checkNull(mainBanner.bannerMovieUrl) === false) {
        this.uploadModel.bannerMovieUrl = mainBanner.bannerMovieUrl
      }

      if (subBanner.length > 0) {
        this.uploadModel.bannerList = subBanner
      }

      if (mainBanner.isVertical === true) {
        this.uploadModel.regType = 1
      }
    },
    initRegType() {
      if (this.deviceType === 2) {
        this.uploadModel.regType = 0
      } else if (this.deviceType === 1) {
        this.uploadModel.regType = 6
      } else {
        this.signageType = 2
        this.uploadModel.regType = 2
      }
    },
    getUploadModel() {
      return this.uploadModel
    },
  },
  beforeDestroy() {
    this.$EventBus.$off("selectSignageType")
  },
}
</script>
