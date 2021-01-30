<template>
  <v-sheet class="overflow-hidden sheetMain" color="#FAFAFA">
    <v-row class="fill-height" align="center">
      <v-row
        align="end"
        class="left-draw-bar overflow-hidden"
        style="height:inherit"
      ></v-row>

      <v-btn
        v-if="getScreenSize() != `xs`"
        class="text-toggle-btn pa-2"
        depressed
        rounded
        color="#FAFAFA"
        @click="reqGetTempBaner"
      >
        <span class="temp-banner-text">임시보관 배너</span>
      </v-btn>

      <v-btn
        class="left-toggle-btn"
        fab
        depressed
        color="#3e2723"
        @click="reqGetTempBaner"
        width="40"
        height="40"
      >
        <v-icon class="pr-3" color="white">chevron_left</v-icon>
      </v-btn>

      <v-row class="pl-4 pl-sm-0" no-gutters>
        <v-col class="text-start" cols="10" sm="9" offset-sm="1">
          <v-row class="mb-3 mb-sm-4" no-gutters>
            <span class="span-title">배너유형 선택</span>
          </v-row>
          <v-row no-gutters>
            <span class="span-sub-title">
              <span v-if="getScreenSize() != 'xs'">우측</span>
              이미지를 참고해서 배너유형을 선택해주세요.
            </span>
          </v-row>
        </v-col>

        <div style="height: 15vh;"></div>

        <v-col
          ref="deviceBtnBox"
          class="mt-2 mt-sm-0 deviceBtnBox"
          cols="12"
          sm="4"
          offset-sm="1"
        >
          <v-row class="mb-2 mb-sm-6" no-gutters justify="start">
            <v-btn
              class="device-type-btn"
              depressed
              tile
              width="189"
              @click="selectDeviceType(SIGNAGE_1FRAME_TYPE)"
              :style="selectBtnStyle[SIGNAGE_1FRAME_TYPE]"
            >
              <v-icon
                class="circle-btn"
                v-if="deviceType === SIGNAGE_1FRAME_TYPE"
                >radio_button_checked</v-icon
              >
              <v-icon v-else class="circle-btn">radio_button_unchecked</v-icon>
              <span class="device-select-btn mr-auto">사이니지 1 FRAME</span>
            </v-btn>
          </v-row>
          <v-row class="mb-2 mb-sm-6" no-gutters justify="start">
            <v-btn
              class="device-type-btn"
              depressed
              tile
              width="189"
              @click="selectDeviceType(SIGNAGE_6FRAME_TYPE)"
              :style="selectBtnStyle[SIGNAGE_6FRAME_TYPE]"
            >
              <v-icon
                class="circle-btn"
                v-if="deviceType === SIGNAGE_6FRAME_TYPE"
                >radio_button_checked</v-icon
              >
              <v-icon v-else class="circle-btn">radio_button_unchecked</v-icon>
              <span class="device-select-btn mr-auto">사이니지 6 FRAME</span>
            </v-btn>
          </v-row>
          <v-row class="mb-7 mb-sm-12" no-gutters justify="start">
            <v-btn
              class="device-type-btn"
              depressed
              tile
              width="189"
              @click="selectDeviceType(MOBILE_TYPE)"
              :style="selectBtnStyle[MOBILE_TYPE]"
            >
              <v-icon class="circle-btn" v-if="deviceType === MOBILE_TYPE"
                >radio_button_checked</v-icon
              >
              <v-icon v-else class="circle-btn">radio_button_unchecked</v-icon>
              <span class="device-select-btn mr-auto">모바일</span>
            </v-btn>
          </v-row>

          <v-row
            class="mb-6 mb-sm-0 makeButton"
            align="end"
            justify="start"
            no-gutters
          >
            <v-btn
              class="pr-2"
              @click="bannerMake"
              color="#42322E"
              rounded
              depressed
              width="123"
            >
              <span class="next-select-text">다음</span>
              <v-icon class="next-select-btn ml-auto" color="white"
                >chevron_right</v-icon
              >
            </v-btn>
          </v-row>
        </v-col>

        <v-col
          class=" mt-sm-0 d-flex justify-sm-end"
          cols="12"
          sm="4"
          lg="2"
          offset-sm="1"
        >
          <v-img
            v-if="deviceType === SIGNAGE_1FRAME_TYPE"
            contain
            class="deviceImage"
            src="@/assets/images/banner_icons/banner_sigange1frame.svg"
          ></v-img>
          <v-img
            v-else-if="deviceType === SIGNAGE_6FRAME_TYPE"
            contain
            class="deviceImage"
            src="@/assets/images/banner_icons/banner_sigange6frame.svg"
          ></v-img>
          <v-img
            v-else-if="deviceType === MOBILE_TYPE"
            contain
            class="deviceImage"
            src="@/assets/images/banner_icons/banner_mobile.svg"
          ></v-img>
        </v-col>
      </v-row>

      <v-navigation-drawer
        ref="bannerDrawer"
        v-model="drawer"
        :width="drawerWidth"
        absolute
        temporary
        right
      >
        <v-row
          align="end"
          class="right-draw-bar overflow-hidden"
          :style="rightDrawBoxHeight"
        >
        </v-row>

        <v-row no-gutters>
          <v-col class="drawer-box" cols="11" offset="1">
            <v-row no-gutters>
              <v-col class="mt-1 pr-3 d-flex align-start justify-end" cols="12">
                <v-img
                  class="drawer-notice-icon"
                  src="@/assets/images/banner_icons/notice.svg"
                  contain
                  max-width="10"
                ></v-img>
                <span class="drawer-notice text-start">
                  임시보관 배너는 10개까지 저장되며 과거순으로 삭제됩니다.</span
                >
              </v-col>
            </v-row>
            <v-row class="pl-2 pl-sm-4" no-gutters>
              <v-col class="d-flex justify-start" cols="12">
                <span class="drawer-title">임시보관 배너</span>
              </v-col>
            </v-row>

            <v-row class="mt-5" no-gutters>
              <v-col class="d-flex justify-start" cols="12">
                <v-col class="d-flex justify-center" cols="3">
                  <span class="drawer-content-title">썸네일</span>
                </v-col>
                <v-col class="d-flex justify-center" cols="5">
                  <span class="drawer-content-title">배너명</span>
                </v-col>
                <v-col class="d-flex justify-center" cols="4">
                  <span class="drawer-content-title">저장일</span>
                </v-col>
              </v-col>
            </v-row>

            <v-row class="" no-gutters>
              <v-col class="drawer-divider" cols="12"> </v-col>
            </v-row>

            <v-list>
              <v-list-item-group>
                <v-list-item
                  class="pl-0"
                  v-for="(banner, i) in bannerInfo"
                  @click="reqTempBanerInfo(banner)"
                  :key="i"
                >
                  <v-col
                    class="px-0 d-flex justify-start"
                    cols="12"
                    style="cursor: pointer;"
                  >
                    <v-col
                      class="pa-0 d-flex align-center justify-center"
                      cols="3"
                    >
                      <div class="drawer-content-box">
                        <v-img
                          v-if="checkNull(banner.thumbImageUrl) === false"
                          contain
                          height="62"
                          :src="`/files/${banner.thumbImageUrl}`"
                        />
                      </div>
                    </v-col>
                    <v-col
                      class="pa-0 d-flex align-center justify-start"
                      cols="5"
                    >
                      <span class="drawer-content-banner text-start">{{
                        banner.title
                      }}</span>
                      <v-img
                        class="ml-2"
                        v-if="banner.frameType === 1"
                        src="@/assets/images/banner_icons/temp_signage1.svg"
                        max-width="10"
                        contain
                      ></v-img>
                      <v-img
                        class="ml-2"
                        v-else-if="banner.frameType === 6"
                        src="@/assets/images/banner_icons/temp_signage6.svg"
                        max-width="10"
                        contain
                      ></v-img>
                      <v-img
                        class="ml-2"
                        v-else
                        src="@/assets/images/banner_icons/temp_mobile.svg"
                        max-width="10"
                        contain
                      ></v-img>
                    </v-col>
                    <v-col
                      class="pa-0 d-flex align-center justify-center"
                      cols="4"
                    >
                      <span class="drawer-content-date text-start">{{
                        $moment(banner.updateDate).format("YYYY-MM-DD")
                      }}</span>
                    </v-col>
                  </v-col>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-col>
        </v-row>

        <v-row no-gutters>
          <v-col cols="11" offset="1">
            <v-divider></v-divider>
          </v-col>
        </v-row>
        <v-btn
          class="right-toggle-btn"
          fab
          depressed
          color="#3e2723"
          @click="clickToggle"
          width="40"
          height="40"
        >
          <v-icon class="pl-3" color="white">chevron_right</v-icon>
        </v-btn>
      </v-navigation-drawer>
    </v-row>
  </v-sheet>
</template>

<script>
import { commonBanner } from "../../mixins/commonMixins"

export default {
  mixins: [commonBanner],

  data() {
    return {
      deviceType: 0,

      SIGNAGE_1FRAME_TYPE: 0,
      SIGNAGE_6FRAME_TYPE: 1,
      MOBILE_TYPE: 2,

      bannerInfo: {},
      selectBanner: {},

      selectBtnStyle: [],

      drawer: null,

      sheetHeight: 0,
      leftDrawBoxHeight: 0,
      rightDrawBoxHeight: 0,

      icon: {
        width: 215,
        height: 405,
      },

      drawerWidth: 500,

      nextBtnRowStyle: 0,
    }
  },
  created() {
    for (let i = 0; i < 3; i++) {
      this.selectBtnStyle.push(``)
    }

    this.selectBtnStyle[0] = `background-color: #71615E !important;`
  },
  mounted() {
    if (this.getScreenSize() === "lg") {
      this.sheetHeight = screen.height * 0.74
      this.leftDrawBoxHeight = `height: ${screen.height * 0.755}px;`
      this.rightDrawBoxHeight = `height: 103%;`
      this.nextBtnRowStyle = `height: ${this.$refs.deviceBtnBox.offsetHeight -
        43}px;`
    } else if (this.getScreenSize() === "sm") {
      this.sheetHeight = screen.height * 0.83
      this.leftDrawBoxHeight = `height: ${screen.height * 0.85}px;`
      this.rightDrawBoxHeight = `height: 103%;`
      this.nextBtnRowStyle = `height: ${this.$refs.deviceBtnBox.offsetHeight -
        43}px;`
    } else {
      if (screen.height < 650) {
        this.sheetHeight = screen.height * 0.85
        this.leftDrawBoxHeight = `height: ${screen.height * 0.885}px;`
        this.rightDrawBoxHeight = `height: 101.5%;`
      } else if (screen.height < 700) {
        this.sheetHeight = screen.height * 0.83
        this.leftDrawBoxHeight = `height: ${screen.height * 0.865}px;`
        this.rightDrawBoxHeight = `height: 103%;`
      } else {
        this.sheetHeight = screen.height * 0.8
        this.leftDrawBoxHeight = `height: ${screen.height * 0.83}px;`
        this.rightDrawBoxHeight = `height: 103%;`
      }

      this.nextBtnRowStyle = `height: ${this.$refs.deviceBtnBox.offsetHeight -
        200}px;`

      this.icon.width *= 0.5
      this.icon.height *= 0.5
    }
  },
  methods: {
    selectDeviceType(type) {
      for (let i = 0; i < 3; i++) {
        this.selectBtnStyle[i] = ``
      }

      this.selectBtnStyle[type] = `background-color: #71615E !important;`

      this.deviceType = type
    },
    bannerMake() {
      this.$emit("selectDeviceType", this.deviceType)
    },
    reqGetTempBaner() {
      this.drawer = !this.drawer

      let formData = {}
      formData.memberCode = this.$BaseCommon.memberInfo.getMember().member.memberCode
      formData.status = 1

      this.$BaseCommon.restful
        .call2("/banner/getsimpletempbanner", formData)
        .then((res) => {
          if (this.checkNull(res) === false) {
            this.bannerInfo = res
          }
        })
    },
    reqTempBanerInfo(info) {
      this.selectBanner = {}

      this.selectBanner.mainBanner = info

      this.$BaseCommon.restful
        .call2("/banner/getbanner", this.selectBanner.mainBanner.bannerCode)
        .then((res) => {
          if (this.checkNull(res) === false) {
            this.resInfo(res)
          }
        })
    },
    resInfo(select) {
      let deviceType = this.selectBanner.mainBanner.device_type
      // 1: 모바일
      // 2: 사이니지
      if (deviceType === 1) {
        deviceType = this.MOBILE_TYPE
      } else {
        const frameType = this.selectBanner.mainBanner.frameType

        if (frameType === 1) {
          deviceType = this.SIGNAGE_1FRAME_TYPE
        } else {
          deviceType = this.SIGNAGE_6FRAME_TYPE
        }
      }

      this.selectBanner.keywords = select.keywords
      this.selectBanner.places = select.places
      this.selectBanner.placeSquare = select.placeSquare
      this.selectBanner.subBanner = select.bannerSub

      this.$emit(`resBannerInfo`, this.selectBanner, deviceType)
    },
    clickToggle() {
      this.drawer = !this.drawer
    },
  },
}
</script>

<style lang="scss" scope>
$deviceImageH: 402px;
$deviceImageW: 215px;
.deviceImage {
  max-width: $deviceImageW/2;
  max-height: $deviceImageH/2;
}
.sheetMain {
  position: absolute;
  height: auto;
}

@media only screen and (min-width: $grid-breakpoints_sm) {
  .sheetMain {
    position: relative;
    height: 80vh;
  }
  .makeButton {
    position: absolute;
    bottom: 0;
  }
  .deviceImage {
    max-width: $deviceImageW;
    max-height: $deviceImageH;
  }
}
.drawer-box {
  border-left: 0.05rem solid #d4d4d4;
}

.deviceBtnBox {
  position: relative;
}
.span-title {
  font-size: 1.125rem;
  font-weight: bold;
  color: #3e2723;
}

.span-sub-title {
  font-size: 1.125rem;
  color: #3e2723;
}

.device-type-btn {
  background-color: #aea6a4 !important;
  border-radius: 3px;
  &:hover {
    background-color: #938783 !important;
  }
}

.device-select-btn {
  font-size: 0.875rem;
  font-weight: bold;
  color: #f6f6f6;
}

.temp-banner-text {
  font-weight: bold;
  font-size: 0.813rem;
  color: #9a9290;
}

.text-toggle-btn {
  position: absolute;
  top: 4.6rem;
  right: 2rem;
}

.left-toggle-btn {
  position: absolute;
  top: 4.5rem;
  right: -0.8rem;
}

.right-toggle-btn {
  position: absolute;
  top: 4.5rem;
  left: -0.8rem;
}

.left-draw-bar {
  background-color: #3e2723;
  position: absolute;
  top: -0.7rem;
  right: 0;
  border-top: 2rem solid #fafafa;
  border-bottom: 2rem solid #fafafa;
  border-right: 1.7rem solid #3e2723;
}

.right-draw-bar {
  background-color: #3e2723;
  position: absolute;
  top: -0.7rem;
  border-top: 2rem solid #ffffff;
  border-bottom: 2rem solid #ffffff;
  border-left: 1.7rem solid #3e2723;
}

.circle-btn {
  color: white !important;
  font-size: 0.875rem !important;
  padding-top: 0.2rem;
  padding-right: 0.875rem;
}

.next-select-text {
  color: white;
  font-size: 0.875rem;
  font-weight: bold;
}

.drawer-notice-icon {
  padding-top: 0.4rem;
}

.drawer-notice {
  font-weight: bold;
  font-size: 0.625rem;
  color: #bdbdbd;
  padding-left: 0.5rem;
}

.drawer-title {
  font-weight: bold;
  font-size: 1.125rem;
  color: #3e2723;
  padding-top: 3rem;
}

.drawer-content-title {
  font-size: 1rem;
  color: #887d7a;
}

.drawer-divider {
  border: 5px solid #f4f0eb;
}

.drawer-content-box {
  height: 62px;
  width: 62px;
  background-color: #ededed;
}

.drawer-content-banner {
  font-weight: bold;
  font-size: 1rem;
  color: #707070;
  padding-left: 1rem;
}

.drawer-content-date {
  font-weight: bold;
  font-size: 1rem;
  color: #707070;
  padding-left: 1rem;
}
</style>
