<template>
  <v-container class="pa-0" fluid>
    <v-row no-gutters>
      <v-col cols="12">
        <bannerMakeHeader
          :deviceType="deviceType"
          :selectBanner="selectBanner"
          @backPage="backPage"
          @click:delete="reqDelete()"
          @click:tempSave="reqSave(SAVE_TYPE.TEMP_SAVE)"
          @click:save="reqSave(SAVE_TYPE.COMPLETE_SAVE)"
        ></bannerMakeHeader>
      </v-col>
    </v-row>

    <v-row no-gutters>
      <v-col class="pl-sm-12" cols="12">
        <bannerMakeContent
          :deviceType="deviceType"
          ref="mainBannerInfo"
          :selectBanner="selectBanner"
        ></bannerMakeContent>
      </v-col>
    </v-row>

    <v-row no-gutters>
      <v-col cols="12">
        <bannerMakeFooter
          :deviceType="deviceType"
          ref="subBannerInfo"
          :selectBanner="selectBanner"
        ></bannerMakeFooter>
      </v-col>
    </v-row>

    <v-row justify="center" no-gutters>
      <bannerInputCheckModal
        :info="checkInfo"
        :dialog.sync="dialog"
      ></bannerInputCheckModal>
    </v-row>

    <bannerSnackBar :info="tempSaveInfo"></bannerSnackBar>
    <bannerSnackBar :info="modifyInfo"></bannerSnackBar>
  </v-container>
</template>

<script>
import { commonBanner } from "../../mixins/commonMixins"

import bannerMakeHeader from "./Header/main"
import bannerMakeContent from "./Body/main"
import bannerMakeFooter from "./Footer/main"
import bannerInputCheckModal from "../../components/bannerInputCheckModal"
import bannerSnackBar from "../../components/bannerSnackBar"

export default {
  mixins: [commonBanner],
  components: {
    bannerMakeHeader,
    bannerMakeContent,
    bannerMakeFooter,
    bannerInputCheckModal,
    bannerSnackBar,
  },
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
      SAVE_TYPE: {
        TEMP_SAVE: 1,
        COMPLETE_SAVE: 9,
      },

      checkInfo: {},

      bannerCode: 0,
      dialog: false,

      tempSaveInfo: {
        model: false,
        text: ``,
      },

      modifyInfo: {
        model: false,
        text: ``,
      },

      saveCheck: false,
    }
  },
  created() {
    if (this.selectBanner != null) {
      this.setBannerMainInfo()
    }
  },
  methods: {
    setBannerMainInfo() {
      this.bannerCode = this.selectBanner.mainBanner.bannerCode
      this.selectBanner.mainBanner.keywords = this.selectBanner.keywords
      this.selectBanner.mainBanner.places = this.selectBanner.places
      this.selectBanner.mainBanner.placeSquare = this.selectBanner.placeSquare
    },
    reqDelete() {
      this.$alert("삭제하시겠습니까?", { alertType: "del" }).then((res) => {
        if (res) {
          let formData = {}

          formData.bannerCode = this.bannerCode

          this.$BaseCommon.restful
            .call2("/banner/delete", formData)
            .then((res) => {
              if (this.checkRes(res) === true) {
                this.$alert("삭제가 완료되었습니다.").then(() => {
                  this.$router.go(-1)
                })
              }
            })
            .catch((res) => {
              console.log("/banner/delete >> ", res)
            })
        }
      })
    },
    reqSave(status) {
      // 저장 응답 체크
      if (this.saveCheck === true) {
        return
      }

      const info = this.$refs.mainBannerInfo.getInfo(status)
      const tempBannerSub = this.$refs.subBannerInfo.getInfo()

      // 필수항목 누락 체크
      if (status != this.SAVE_TYPE.TEMP_SAVE) {
        this.checkInput(info, tempBannerSub)

        if (this.checkInfo.inputCheck === false) {
          this.dialog = true
          return
        }
        // 부제목은 텍스트 체크만
        if (this.checkInfo.checkSubTitle === false) {
          return
        }
      }

      const bannerSub = this.getBannerSubInfo(tempBannerSub.bannerList)
      const banner = this.getBannerInfo(info, tempBannerSub)
      const keywords = info.keyword
      const places = info.place
      const placeSquare = info.placeSquare

      const formData = {
        banner: banner,
        bannerSub: bannerSub,
        keywords: keywords,
        places: places,
        placeSquare: placeSquare,
      }

      this.saveCheck = true

      this.$BaseCommon.restful
        .call2("/banner/save", formData)
        .then((res) => {
          if (this.checkNull(res) === false) {
            if (res.data != null) {
              this.bannerCode = Number(res.data)

              if (info.status === 1) {
                this.tempSaveInfo.model = true
                this.tempSaveInfo.text = `배너가 임시보관 되었습니다.`
              } else if (this.selectBannerCheck(this.selectBanner) === true) {
                this.modifyInfo.model = true
                this.modifyInfo.text = `배너가 수정되었습니다.`
              } else {
                this.$emit("click:reqSave", true)
              }
            } else if (res.resultType.toLowerCase() === "error") {
              if (info.status === 1) {
                this.tempSaveInfo.model = true
                this.tempSaveInfo.text = `배너 임시보관을 실패했습니다.`
              } else if (this.selectBannerCheck(this.selectBanner) === true) {
                this.modifyInfo.model = true
                this.modifyInfo.text = `배너가 수정을 실패했습니다.`
              } else {
                this.tempSaveInfo.model = true
                this.tempSaveInfo.text = `배너 저장을 실패했습니다.`
              }
            }
            this.saveCheck = false
          }
        })
        .catch((res) => {
          console.log("/banner/save >> ", res)
        })
    },
    checkInput(info, tempBannerSub) {
      this.checkInfo = {}

      if (this.deviceType != 1) {
        if (
          this.checkNull(info.title) === true ||
          /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|\s|.;,…:*\-()[\]/?!'"‘’“”+%]+$/.test(
            info.title
          ) === false
        ) {
          this.checkInfo.checkTitle = false
        }
      } else {
        if (
          this.checkNull(tempBannerSub.title) === true ||
          /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|\s|.;,…:*\-()[\]/?!'"‘’“”+%]+$/.test(
            tempBannerSub.title
          ) === false
        ) {
          this.checkInfo.checkTitle = false
        }
      }

      if (this.checkNull(info.place) === true) {
        this.checkInfo.checkRange = false
      }

      if (this.checkNull(tempBannerSub.bannerImageUrl) === true) {
        this.checkInfo.checkFile = false
      }

      if ((Object.keys(this.checkInfo).length === 0) === false) {
        this.checkInfo.inputCheck = false
      } else {
        this.checkInfo.inputCheck = true
      }

      if (this.deviceType != 1) {
        if (
          /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|\s|.;,…:*\-()[\]/?!'"‘’“”+%]+$/.test(
            info.subTitle
          ) === false
        ) {
          if (this.checkNull(info.subTitle) === false) {
            this.checkInfo.checkSubTitle = false
          }
        }
      } else {
        if (
          /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|\s|.;,…:*\-()[\]/?!'"‘’“”+%]+$/.test(
            tempBannerSub.subTitle
          ) === false
        ) {
          if (this.checkNull(tempBannerSub.subTitle) === false) {
            this.checkInfo.checkSubTitle = false
          }
        }
      }
    },
    getBannerSubInfo(bannerList) {
      let formData = []

      if (bannerList.length === 0) {
        return formData
      }

      bannerList.forEach((e) => {
        if (this.checkNull(e.bannerImageUrl) === false) {
          formData.push(e)
        }
      })

      return formData
    },
    getBannerInfo(info, subInfo) {
      let formData = {}

      if (this.bannerCode != 0) {
        formData.bannerCode = this.bannerCode
      }

      // 디바이스 유형
      // 1. 모바일
      // 2. 사이니지
      if (this.deviceType === 0 || this.deviceType === 1) {
        formData.deviceType = 2
      } else if (this.deviceType === 2) {
        formData.deviceType = 1
      }

      formData.deviceType2 = this.deviceType + 1

      // 화면 분할
      // 1. 1Frame
      // 6. 6Frame
      if (this.deviceType === 0) {
        formData.frameType = 1
      } else if (this.deviceType === 1) {
        formData.frameType = 6
      }

      // 사이니지 유형
      // 0. 가로
      // 1. 세로
      if (this.deviceType === 0 && this.checkNull(info.signageType) === false) {
        formData.isVertical = info.signageType
      }

      formData.bannerType = info.bannerType + 1

      if (this.deviceType === 1) {
        formData.title = subInfo.title
        formData.subTitle = subInfo.subTitle
      } else {
        formData.title = info.title
        formData.subTitle = info.subTitle
      }

      formData.content = subInfo.content

      formData.status = info.status

      formData.remark = info.remark

      formData.bottomBannerType = info.underViewType + 1

      formData.memberCode = this.$BaseCommon.memberInfo.getMember().member.memberCode

      formData.playType = subInfo.playType
      formData.thumbImageUrl = subInfo.thumbImageUrl
      formData.bannerImageUrl = subInfo.bannerImageUrl
      formData.bannerMovieUrl = subInfo.bannerMovieUrl

      return formData
    },
    backPage() {
      this.$emit(`backPage`)
    },
  },
  beforeDestroy() {
    this.$EventBus.$off(`resBannerInfo`)
  },
}
</script>

<style lang="scss" scoped>
span {
  color: #ffffff;
}
</style>
