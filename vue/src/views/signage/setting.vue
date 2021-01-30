<template lang="pug">
  v-container(ref="signSetting" fill-height fluid)
    v-row(align="start" justify="start" fill-height fluid)
      v-col(cols="12" class="pb-0")
        Vuetify-menutitle
      v-col(cols="12" fill-height fluid)
        div(class="signageSetting")
          v-container(fill-height fluid class="pa-0")
            v-row
              v-col(cols="1")
                Slider-stepper(v-model="stepper" @change="stepperChange" :height="stepperHeight" :isClick="false")
              v-col(cols="11")
                hooper(ref="carousel" vertical @slide="updateCarousel" :touchDrag="false" :mouseDrag="false" :wheelControl="false" :transition="500" height="auto")
                  slide
                    v-container(class="text-left pt-0" fill-height fluid)
                      v-row(class="fill-height")
                        v-col(cols="12" sm="6" lg="5" class="py-0 py-sm-2")
                          v-card(color="transparent" flat class="pa-0" style="width: 600px")
                            div(class="ondaji_font_36_bold_brown_100 stepperTitle") 사이니지 기기설치 스텝밟기
                            div(class="ondaji_font_24_medium_brown_100 stepperSubTitle pt-2") 새로운 기기설치를 통해 고객을 늘려보세요!
                            div(class="ondaji_font_18_medium_grey_80 pt-12 stepperText") 몇 가지 간단한 단계를 가지고 새로운 기기를 등록해 보세요.
                        v-col(cols="12" sm="6" align-left)
                          v-img(:aspect-ratio="1.7" contain :max-height="'900px'" :max-width="'800px'" :src="require(`@/assets/images/Illustration/start_install.svg`)")
                        v-col(cols="12" sm="6" lg="5" class="py-0 card-action")
                          v-row
                            v-col(cols="12")
                              v-divider(class="ondaji_color_grey_30")
                            v-col(cols="12" class="py-0 text-right")
                              Buttons-default(btnType="primary" rounded appendIcon="fas fa-chevron-right" btnText="시작하기" :size="buttonSize" @click="movePage(2)")
                  slide
                    v-container(class="text-left pt-0" fill-height fluid)
                      v-row(class="fill-height")
                        v-col(cols="12" sm="6" lg="5" class="py-0 py-sm-2")
                          v-card(color="transparent" flat class="pa-0" height="100%")
                            div(class="ondaji_font_36_bold_brown_100 stepperTitle") 사이니지 유형 선택
                            div(class="ondaji_font_24_medium_brown_100 stepperSubTitle pt-2") 이미지를 참고해서 배너 유형을 선택해주세요.
                            div(class="ondaji_font_18_medium_grey_80 pt-12 text-center")
                              Buttons-radio(v-model="isSign1Frame" :isRadioImage="false" :rounded="false" trueText="1FRAME" falseText="6FRAME")
                        v-col(cols="12" sm="6")
                          v-img(v-if="isSign1Frame" :aspect-ratio="1.7" contain :max-height="'900px'" :max-width="'800px'" :src="require(`@/assets/images/Illustration/s1_install.svg`)")
                          v-img(v-else :aspect-ratio="1.7" contain :max-height="'900px'" :max-width="'800px'" :src=" require(`@/assets/images/Illustration/s6_install.svg`)")
                        v-col(cols="12" sm="6" lg="5" class="py-0 card-action")
                          v-row
                            v-col(cols="12")
                              v-divider(class="ondaji_color_grey_30")
                            v-col(cols="6" class="pt-0 px-0")
                              Buttons-default(btnType="text" preAppendIcon="fas fa-chevron-left" btnText="뒤로가기" :size="buttonSize" @click="movePage(1)")
                            v-col(cols="6" class="pt-0 text-right")
                              v-spacer
                              Buttons-default(btnType="primary" rounded appendIcon="fas fa-chevron-right" btnText="다음" :size="buttonSize" @click="movePage(3)")
                  slide
                    v-container(class="text-left pt-0" fill-height fluid)
                      v-row(style="height: 100%")
                        v-col(cols="12" sm="6" lg="5" class="py-0 py-sm-2")
                          v-card(color="transparent" flat class="pa-0" height="100%")
                            div(class="ondaji_font_36_bold_brown_100 stepperTitle") 서비스 선택
                            div(class="ondaji_font_24_medium_brown_100 pt-1 pt-sm-0 stepperSubTitle") 당신의 사이니지에 어떤배너를 송출하고 싶으신가요?
                            div(class="ondaji_font_24_medium_brown_100 pt-1 stepperSubTitle") 설명을 보고 선택해 주세요.
                            div(class="ondaji_font_18_medium_grey_80 pt-12 text-center")
                              Buttons-radio(v-model="isInside" :isRadioImage="false" :rounded="false" trueText="내부 광고용" falseText="외부 광고용")
                        v-col(cols="12" sm="6")
                          v-img(v-if="isInside" :aspect-ratio="1.7" contain :max-height="'900px'" :max-width="'800px'"
                                :src="require(`@/assets/images/Illustration/type_install.svg`)" )
                          v-img(v-else :aspect-ratio="1.7" contain :max-height="'900px'" :max-width="'800px'" :src="require(`@/assets/images/Illustration/type2_install.svg`)")
                        v-col(cols="12" sm="6" lg="5" class="py-0 card-action")
                          v-row
                            v-col(cols="12")
                              v-divider(class="ondaji_color_grey_30")
                            v-col(cols="6" class="pt-0 pl-0")
                              Buttons-default(btnType="text" preAppendIcon="fas fa-chevron-left" btnText="뒤로가기" :size="buttonSize" @click="movePage(2)")
                            v-col(cols="6" class="pt-0 text-right")
                              v-spacer
                              Buttons-default(btnType="primary" rounded appendIcon="fas fa-chevron-right" btnText="다음" :size="buttonSize" @click="movePage(4)")
                  slide
                    v-container(class="stepperTitle text-left pt-0" fill-height fluid)
                      v-row(style="height: 100%")
                        v-col(cols="12" sm="6" lg="5" class="pt-0 py-0 pt-sm-2")
                          v-card(color="transparent" flat class="pa-0" height="100%")
                            div(class="ondaji_font_36_bold_brown_100 stepperTitle") 상세정보 입력
                            v-card-text(class="ondaji_font_24_medium_brown_100 py-0 px-4 pl-sm-6 pt-sm-4")
                              v-container(class="pa-0")
                                v-row
                                  v-col(cols="12" class="px-2 px-sm-0 py-1 py-lg-2")
                                    Vuetify-textbox(v-model="saveData.signName" label="기기명" :errorMessage="errorMessage.signName")
                                  v-col(cols="12" class="px-2 px-sm-0 py-0 py-lg-2")
                                    Vuetify-textbox(v-model="saveData.zipCode" label="설치 주소" placeholder="우편번호" :isBtn="true" btnText="주소검색" readonly   
                                                    @btnClick="mapPopup" :errorMessage="errorMessage.address")
                                    Kakaomap-PostCode(:name="''" @kakaoPostCodeCallback="kakaoPostCodeCallback" :dialog="dialog")
                                  v-col(cols="12" class="px-2 px-sm-0 py-0 py-lg-2")
                                    Vuetify-textbox(v-model="saveData.address" readonly label=" " placeholder="주소")
                                  v-col(cols="12" class="px-2 px-sm-0 py-0 py-lg-2")
                                    Vuetify-textbox(v-model="saveData.address2" label=" " placeholder="상세주소")
                                  v-col(cols="12" class="px-2 px-sm-0 py-0 py-lg-2")
                                    v-row(class="py-0")
                                      v-col(class="label text-left ondaji_font_20_medium_brown_80 px-0 py-2 py-sm-1" cols="4") 기기위치
                                      v-col(class="pt-0 pl-0 px-0 pl-sm-0 pb-0" cols="12" sm="8")
                                        Buttons-radio(v-model="isSignType" trueText="실내" falseText="실외")
                        v-col(cols="12" sm="6" lg="7" class="pa-0")
                        v-col(cols="12" sm="6" lg="5" class="py-0 card-action")
                          v-row
                            v-col(cols="12")
                              v-divider(class="ondaji_color_grey_30")
                            v-col(cols="6" class="pt-0 pl-0")
                              Buttons-default(btnType="text" preAppendIcon="fas fa-chevron-left" btnText="뒤로가기" :size="buttonSize" @click="movePage(3)")
                            v-col(cols="6" class="pt-0 text-right")
                              v-spacer
                              Buttons-default(btnType="request" rounded appendIcon="fas fa-chevron-right" btnText="설치신청" :size="buttonSize" @click="doSave")
                  slide
                    v-container(class="text-left pt-0" fill-height fluid)
                      v-row(style="height: 100%")
                        v-col(cols="12" sm="6" lg="5" class="py-0 py-sm-0 py-lg-2")
                          v-card(color="transparent" flat class="pa-0" height="100%")
                            div(class="ondaji_font_36_bold_brown_100 stepperTitle") 사이니지 설치신청이 완료되었습니다.
                            v-container(fluid fill-height)
                              v-row
                                v-col(cols="12" class="text-center ma-auto")
                                  Buttons-default(btnType="primary" btnText="진행현황보기" :size="buttonSize" :width=" this.$vuetify.breakpoint.xs? '100px': '150px'"
                                                  class="mr-2 mr-0 ma-sm-4" @click="moveList")
                                  Buttons-default(btnType="secondary" btnText="설치신청 계속" :size="buttonSize" 
                                                  :width="this.$vuetify.breakpoint.xs ? '100px' : '150px'"
                                                  class="ml-2 mr-0 ma-sm-4" @click="moveContinue")
                        v-col(cols="12" sm="6")
                          v-img(:aspect-ratio="1.7" contain :max-height="'900px'" :max-width="'800px'" :src="require(`@/assets/images/Illustration/fin_install.svg`)")
                        
                        v-col(cols="12" sm="6" lg="5" class="py-0 pt-sm-5")
                          v-row
                            v-col(cols="12")
                              v-divider(class="ondaji_color_grey_30")
                            v-col(cols="12" class="pt-0 text-right")
                  hooper-navigation(slot="hooper-addons")
</template>
<style lang="scss">
.hooper {
  &-prev,
  &-next {
    display: none;
  }
}
</style>

<style lang="scss" scoped>
.stepperTitle {
  padding-top: 0px;
}
.col.label {
  max-width: inherit;
  min-width: inherit;
}
.signageSetting {
  padding-top: 3vw;
  padding-left: 7vw;
  .v-card {
    position: relative;
    padding-bottom: 50px;
  }
}
.card-action {
  bottom: 20px;
  width: 100%;
  margin-top: auto;
}
@include screen_xs {
  .signageSetting {
    padding-top: 8px;
    padding-left: 8px;
  }

  .stepperTitle.ondaji_font_36_bold_brown_100 {
    padding-top: 0px;
    font-size: 20px !important;
  }
  .stepperSubTitle.ondaji_font_24_medium_brown_100 {
    font-size: 16px !important;
    font-weight: 500 !important;
  }
  .stepperText.ondaji_font_18_medium_grey_80 {
    font-size: 14px !important;
    font-weight: 400 !important;
    padding-top: 24px !important;
  }

  .label.ondaji_font_20_medium_brown_80 {
    font-size: 16px !important;
    margin: 0px;
  }
}
.hr {
  width: 100%;
  margin-bottom: 100px;
}

.hooper {
  height: 100%;
  &:focus {
    outline: -webkit-focus-ring-color auto 0px;
  }
}
.hooper .v-application .hooper-list ul {
  padding: 0px;
}
</style>

<script>
import { Hooper, Slide, Navigation as HooperNavigation } from "hooper"
import "hooper/dist/hooper.css"
export default {
  components: {
    Hooper,
    Slide,
    HooperNavigation,
  },
  watch: {
    sliderIdx(val) {
      this.$refs.carousel.slideTo(val)
    },
    stepper(val) {
      console.log(val)

      this.$refs.carousel.slideTo(val.value)
    },
  },
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({ el: this.$el, isTop: true })
    if (this.$vuetify.breakpoint.xs) {
      this.buttonSize = 36
    }
  },
  created() {
    this.setStepperHeight()
  },
  data() {
    return {
      sliderIdx: 1,
      stepperHeight: "70vh",
      isSign1Frame: true,
      isSignType: true,
      isInside: true,
      buttonSize: 40,

      stepper: {
        total: 4,
        value: 0,
        lineHeight: 160,
        disabled: false,
      },

      saveData: {
        signCode: -1,
        signName: "",
        signType: 1,
        frameType: 1,
        isVertical: true,
        isInside: true,
        memberCode: 1,
        remark: "",
        zipCode: "",
        address: "",
        address2: "",
        placeList: [],
      },
      errorMessage: {
        signName: "",
        address: "",
      },
      dialog: false,
    }
  },
  methods: {
    setStepperHeight() {
      switch (this.$vuetify.breakpoint.name) {
        case "xs":
          this.stepperHeight = "70vh"
          break
        case "sm":
          this.stepperHeight = "65vh"
          break
        case "lg":
          this.stepperHeight = "64vh"
          break
        case "xl":
          this.stepperHeight = "60vh"
          break
        default:
          this.stepperHeight = "70vh"
          break
      }
    },
    stepperChange(val) {
      console.log("stepperChange", val)
      this.stepper.value = val
      this.$refs.carousel.slideTo(val - 1)
    },
    slidePrev() {
      this.$refs.carousel.slidePrev()
    },
    slideNext() {
      this.$refs.carousel.slideNext()
    },
    updateCarousel(payload) {
      this.sliderIdx = payload.currentSlide + 1
      let tmp = Object.assign(this.stepper, { value: this.sliderIdx - 1 })

      this.stepper = Object.assign({}, tmp)
    },
    movePage(page) {
      this.$refs.carousel.slideTo(page - 1)
    },
    mapPopup() {
      this.dialog = true
    },
    kakaoPostCodeCallback(res, bConfirm) {
      console.log(res)
      this.dialog = false
      if (!bConfirm) {
        return
      }
      // eslint-disable-next-line no-debugger
      debugger
      this.saveData.zipCode = res.zipCode
      this.saveData.address = res.address
      this.saveData.region = res.region
      res.address2 = this.address2

      this.saveData.placeList = []
      this.saveData.placeList.push(res)
      this.dialog = false
    },
    checkData() {
      let bChk = true
      if (this.$BaseCommon.stringInfo.isNullEmpty(this.saveData.signName)) {
        this.errorMessage.signName = "기기명을 입력하세요"
        bChk = false
      }

      if (this.saveData.placeList.length === 0) {
        this.errorMessage.address = "주소검색을 통해 주소를 등록하세요"
        bChk = false
      }
      return bChk
    },
    doSave() {
      if (!this.checkData()) return

      if (this.isSign1Frame) this.saveData.frameType = 1
      else this.saveData.frameType = 6

      this.saveData.isInside = this.isInside ? 1 : 2
      this.saveData.signType = this.isSignType ? 1 : 2

      this.$BaseCommon.restful
        .call("/signage/save", this.saveData)
        .then((res) => {
          if (res.resultType === "Error") {
            this.$alert(res.message)
          } else {
            this.movePage(5)
          }
          console.log(res)
        })
    },
    moveContinue() {
      //this.$alert("설치신청 계속")
      this.$router.go(this.$router.currentRoute)
    },
    moveList() {
      //this.$alert("진행현황보기미개발")
      this.$router.go("list")
      this.$BaseCommon.hostInfo.redirect("/signage/list")
    },
  },
}
</script>
