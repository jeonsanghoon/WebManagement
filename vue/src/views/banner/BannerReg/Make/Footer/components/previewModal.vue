<template>
  <v-dialog v-model="dialog" persistent :max-width="dialogWidth">
    <v-card>
      <header>
        <bannerHeader :info="headerInfo" @close="close"></bannerHeader>
      </header>

      <content>
        <v-slide-group v-model="slideSeq" center-active>
          <v-slide-item v-for="(info, i) in previewInfoArr" :key="i">
            <bannerPreviewCard :info="info"></bannerPreviewCard>
          </v-slide-item>
        </v-slide-group>
      </content>
    </v-card>
  </v-dialog>
</template>

<script>
import { commonBanner } from "../../../../mixins/commonMixins"

import bannerHeader from "../../../../components/bannerHeader"
import bannerPreviewCard from "./previewCard"

export default {
  mixins: [commonBanner],
  components: {
    bannerHeader,
    bannerPreviewCard,
  },
  props: {
    deviceType: {
      type: Number,
      required: true,
      default: 0,
    },
    info: {
      type: Object,
      required: false,
      default: null,
    },
    dialog: {
      type: Boolean,
      required: true,
      default: false,
    },
    selectBanner: {
      type: Object,
      required: false,
      default: null,
    },
  },
  data() {
    return {
      dialogWidth: 0,

      headerInfo: {
        class: `px-1`,
        style: `background-color: #FAFAFA;`,
        title: `모바일 미리보기`,
        classTitle: `ml-lg-2`,
        styleTitle: `font-weight: bold;`,
        closeIcon: `fas fa-times`,
        classCloseIcon: `ml-auto mr-2 mr-lg-2`,
        styleCloseIcon: `padding-top: 0.15rem; font-size: 1.1rem; width: 20px; height: 20px;`,
        divider: true,
      },

      // 배너명, 부제목등 메인 화면 정보
      mainBannerInfo: null,
      // 추가 배너 정보
      subBannerInfo: null,
      // 모바일 미리보기 시 노출되는 배너 정보
      previewInfoArr: [],
      // 슬라이드 순서는 무조건 첫번째에서 시작
      slideSeq: 0,
    }
  },
  watch: {
    dialog() {
      const len = this.previewInfoArr.length
      const lgWidth = 495
      const smWidth = 350
      const xsWidth = 300

      if (this.getScreenSize() === "lg") {
        if (len === 1) {
          this.dialogWidth = `${lgWidth}px`
          this.headerInfo.classTitle = `ml-lg-5`
          this.headerInfo.classCloseIcon = `ml-auto mr-lg-5`
        } else if (len === 2) {
          this.dialogWidth = `${lgWidth * 1.7}px`
        } else if (len === 3) {
          this.dialogWidth = `${lgWidth * 2.5}px`
          this.headerInfo.classTitle = `ml-lg-2`
          this.headerInfo.classCloseIcon = `ml-auto mr-lg-2`
        } else {
          this.dialogWidth = `${lgWidth * 2.65}px`
          this.headerInfo.classTitle = `ml-lg-5`
          this.headerInfo.classCloseIcon = `ml-auto mr-lg-5`
        }
      } else if (this.getScreenSize() === "sm") {
        this.headerInfo.classTitle = `ml-sm-1`
        this.headerInfo.classCloseIcon = `ml-auto mr-sm-1`

        if (len === 1) {
          this.dialogWidth = `${smWidth}px`
        } else {
          this.dialogWidth = `${smWidth * 1.8}px`
        }
      } else {
        this.dialogWidth = `${xsWidth}px`
      }
    },
  },
  created() {
    // 모바일 미리보기 버튼을 클릭하면 아래 정보들을 세팅
    this.$EventBus.$on(`getMainBannerInfo`, (mainBannerInfo) => {
      this.mainBannerInfo = mainBannerInfo
    })

    this.$EventBus.$on("getSubBannerInfo", (subBannerInfo) => {
      this.subBannerInfo = subBannerInfo
      this.setPreviewInfo()
    })
  },
  methods: {
    setPreviewInfo() {
      this.slideSeq = 0

      // 내용에 상관 없이 최소 하나 이상의 배너를 보여줌
      const len = this.subBannerInfo.bannerList.length + 1
      const bannerSize = this.getBannerSize(len)

      this.setFirstBanner(len, bannerSize)
      this.setOtherBanner(len, bannerSize)
    },
    setFirstBanner(len, bannerSize) {
      let previewInfo = new Object()

      const fileUrl = this.subBannerInfo.thumbImageUrl

      if (this.checkNull(fileUrl) === false) {
        previewInfo.src = `/files/${fileUrl}`
      }

      previewInfo.content = this.subBannerInfo.content

      this.setCommonInfo(previewInfo, len, bannerSize)
    },
    setOtherBanner(len, bannerSize) {
      this.subBannerInfo.bannerList.forEach((e) => {
        let previewInfo = new Object()

        if (this.checkNull(e.tbannerImageUrl) === false) {
          if (e.playType === 1) {
            previewInfo.src = `/files/${e.tbannerImageUrl}`
          } else {
            previewInfo.src = e.tbannerImageUrl
          }
        }

        previewInfo.content = e.content

        this.setCommonInfo(previewInfo, len, bannerSize)
      })
    },
    setCommonInfo(previewInfo, len, bannerSize) {
      previewInfo.nick = this.$BaseCommon.memberInfo.getMember().member.nickname
      previewInfo.local = ``

      if (this.deviceType === 1) {
        previewInfo.title = this.subBannerInfo.title
      } else {
        previewInfo.title = this.mainBannerInfo.title
      }

      previewInfo.class = bannerSize
      previewInfo.len = len

      this.pushBanner(previewInfo)
    },
    pushBanner(info) {
      info.index = this.previewInfoArr.length + 1

      this.previewInfoArr.push(info)
    },
    getBannerSize(len) {
      const bannerClass = ""
      let size = len === 1 ? `ml-1 ml-sm-4 ml-lg-11` : `ml-0 ml-sm-1 ml-lg-2`

      if (this.getScreenSize() === `lg`) {
        if (len === 3) {
          size = `ml-lg-1`
        } else if (len > 3) {
          size = `ml-lg-0`
        }
      }

      return `${bannerClass} ${size}`
    },
    close() {
      this.previewInfoArr = []
      this.mainBannerInfo = null
      this.subBannerInfo = null

      this.$emit("update:dialog", false)
    },
  },
  beforeDestroy() {
    this.$EventBus.$off("getMainBannerInfo")
    this.$EventBus.$off("getSubBannerInfo")
  },
}
</script>

<style lang="scss" scoped>
.v-card {
  background-color: #fafafa;
}

::v-deep {
  .v-dialog {
    margin-left: 0.7rem;
    margin-right: 0.7rem;
  }
}
</style>
