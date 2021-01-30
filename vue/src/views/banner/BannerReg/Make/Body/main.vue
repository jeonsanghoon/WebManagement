<template>
  <v-container class="px-2 pt-sm-8 pb-sm-0" fluid>
    <!-- SIGNAGE TYPE -->
    <v-row v-if="deviceType === 0" class="mb-8" no-gutters>
      <v-col class="d-flex justify-start mb-2 mt-sm-2" cols="12" sm="2" lg="1">
        <span class="span-title">유형</span>
      </v-col>
      <v-col class="d-flex justify-start" cols="12" sm="8">
        <template v-for="(type, index) in btnSignageTypeInfoArr">
          <bannerBtn
            :info="type.info"
            @click:button="setSignageType(index)"
            :key="index"
          >
            <v-col class="pa-0" cols="12">
              <v-img
                src="@/assets/images/banner_icons/temp_signage1.svg"
                contain
                max-width="46"
                max-height="40"
                :style="type.style"
              ></v-img>
              <span
                class="mt-0"
                style="font-size: 0.65rem; font-weight: normal;"
              >
                {{ type.content }}
              </span>
            </v-col>
          </bannerBtn>
        </template>
      </v-col>
    </v-row>

    <!-- BANNER TITLE -->
    <template v-for="(field, index) in textFieldInfoArr">
      <v-row no-gutters :key="index">
        <v-col
          class="d-flex justify-start mb-2 mt-sm-1"
          cols="12"
          sm="2"
          lg="1"
        >
          <span class="span-title text-start">
            {{ field.content }}

            <span
              v-if="textFieldInfoArr.length === index + 1"
              class="d-flex"
              style="font-weight: normal; font-size:0.75rem;"
              >(선택 입력)</span
            >
          </span>
        </v-col>
        <v-col cols="12" sm="8">
          <bannerTextField :info="field.info"></bannerTextField>
        </v-col>
      </v-row>
    </template>

    <!-- BANNER TYPE -->
    <v-row class="mb-6 mb-sm-3 mb-lg-1" no-gutters>
      <v-col
        class="d-flex justify-start mb-2 mt-sm-1 mb-sm-6 mt-lg-2"
        cols="12"
        sm="2"
        lg="1"
      >
        <span class="span-title">배너유형</span>
      </v-col>
      <v-col
        class="d-flex justify-start mb-6 mb-sm-0 mt-lg-2"
        cols="12"
        sm="10"
        lg="4"
      >
        <template v-for="(type, index) in btnBannerTypeInfoArr">
          <bannerBtn
            :info="type.info"
            @click:button="setBannerType(index)"
            :key="index"
          >
            <v-icon
              color="brown darken-4"
              v-if="selectBannerType === 0 && index === 0"
              >radio_button_checked</v-icon
            >
            <v-icon
              color="brown darken-4"
              v-else-if="selectBannerType === 1 && index === 1"
              >radio_button_checked</v-icon
            >
            <v-icon v-else>radio_button_unchecked</v-icon>
            <span>{{ type.content }}</span>
          </bannerBtn>
        </template>
      </v-col>

      <!-- KEYWORD -->
      <v-col class="d-flex justify-start mb-2" cols="12" sm="2" lg="1">
        <span class="span-title text-left mt-lg-1">
          키워드
          <span class="d-flex" style="font-weight: normal; font-size:0.75rem;"
            >(선택 입력)</span
          >
        </span>
      </v-col>
      <v-col cols="12" sm="8" lg="5">
        <autoComplete ref="autocomplete" :info="keywordInfo"></autoComplete>
      </v-col>
    </v-row>

    <!-- RANGE -->
    <v-row class="mt-lg-0 mb-6" no-gutters>
      <v-col sm="12" lg="5">
        <v-row no-gutters>
          <v-col class="d-flex justify-start mt-sm-1 mb-2" cols="12" sm="2">
            <span class="span-title">광고영역</span>
          </v-col>

          <v-col
            class="d-flex justify-start pa-0 ml-lg-4 mb-5"
            cols="12"
            sm="8"
          >
            <div class="range-box">
              <span class="range-text" v-if="rangeInfo.list.length > 0"
                >{{ rangeInfoRadius }}km</span
              >
            </div>

            <Kakaomap-multiMap
              v-model="tempRangeInfo"
              :dialog="mapDialog"
              @callback="kakaomapMultiCallback"
            />

            <bannerBtn
              class="mt-1 ml-auto"
              :info="btnRangeInfo.info"
              @click:button="mapDialog = !mapDialog"
            >
              {{ btnRangeInfo.content }}
            </bannerBtn>
          </v-col>

          <!-- PLACE TAG -->
          <v-col
            v-if="placeArr[0].length > 0"
            class="d-flex justify-start mb-2 mb-sm-1 pl-lg-4"
            cols="12"
            offset-sm="2"
          >
            <span class="span-title" style="color: #AAA19F; font-size: 0.90rem;"
              >지역태그</span
            >
          </v-col>

          <template v-for="(place, index) in placeArr">
            <v-col
              v-if="place.length > 0"
              class="mt-sm-2 ml-lg-4 flex-wrap"
              cols="12"
              :key="index"
            >
              <v-row justify="start" no-gutters>
                <v-col cols="auto" offset-sm="2">
                  <span class="place-title" :style="placeTagStyle">
                    {{ index + 1 }} <span class="place-divider">|</span>
                  </span>
                </v-col>

                <v-col class="text-left pr-sm-4" cols="11" sm="8">
                  <v-col
                    class="d-inline-flex pa-0"
                    cols="auto"
                    v-for="(placeInfo, index) in place"
                    :key="index"
                  >
                    <span
                      class="place-tag mb-2"
                      :style="placeTagStyle"
                      @mouseover="placeInfo.hover = true"
                      @mouseout="placeInfo.hover = false"
                    >
                      {{ placeInfo.name }}

                      <span
                        v-if="placeInfo.hover === true"
                        class="place-tag-hover ondaji_font_10_medium_white"
                      >
                        {{ placeInfo.hoverName }}
                      </span>
                    </span>
                  </v-col>
                </v-col>
              </v-row>
            </v-col>
          </template>
        </v-row>
      </v-col>

      <!-- REMARK -->
      <v-col
        class="d-flex justify-start mb-2 mt-5 mt-lg-0"
        cols="12"
        sm="2"
        lg="1"
      >
        <span class="span-title text-left"
          >비고
          <span class="d-flex" style="font-weight: normal; font-size:0.75rem;"
            >(선택 입력)</span
          >
        </span>
      </v-col>
      <v-col
        class="d-flex justify-start mt-sm-5 mt-lg-0"
        cols="12"
        sm="8"
        lg="5"
      >
        <bannerTextAreas :info="textAreasInfo"></bannerTextAreas>
      </v-col>
    </v-row>

    <!-- UNDER VIEW TYPE -->
    <!-- <v-row
      v-if="selectBannerCheck(selectBanner) === true"
      class="mb-6 mb-sm-3 mb-lg-1"
      no-gutters
    >
      <v-menu open-on-hover bottom offset-y>
        <template v-slot:activator="{ on }">
          <v-col
            class="d-flex justify-start mb-2 mt-sm-1 mb-sm-6 mt-lg-3"
            cols="12"
            sm="2"
            lg="1"
            v-on="on"
          >
            <span class="span-title d-flex"
              >하단배너
              <v-img
                contain
                width="25"
                height="25"
                color="#BEB7B6"
                src="@/assets/images/banner_icons/alert-circle.svg"
              >
              </v-img
            ></span>
          </v-col>
        </template>

        <v-col class="pa-0" cols="12">
          <bannerMakeUnderViewNotice></bannerMakeUnderViewNotice>
        </v-col>
      </v-menu>

      <v-col class="d-flex justify-start mt-lg-2" cols="12" sm="10" lg="4">
        <template v-for="(type, index) in btnUnderViewTypeInfoArr">
          <bannerBtn
            :info="type.info"
            @click:button="setUnderViewType(index)"
            :key="index"
          >
            <v-icon
              color="brown darken-4"
              v-if="selectUnderViewType === 0 && index === 0"
              >radio_button_checked</v-icon
            >
            <v-icon
              color="brown darken-4"
              v-else-if="selectUnderViewType === 1 && index === 1"
              >radio_button_checked</v-icon
            >
            <v-icon v-else>radio_button_unchecked</v-icon>
            <span>{{ type.content }}</span>
          </bannerBtn>
        </template>
      </v-col>
    </v-row> -->

    <v-row v-if="lodingPlaceTag === true" justify="center" no-gutters>
      <bannerProgressModal :dialog.sync="dialog"
        >지역태그 추출중</bannerProgressModal
      >
    </v-row>
  </v-container>
</template>

<script>
import { commonBanner } from "../../../mixins/commonMixins"
import { componentsBanner } from "../../../mixins/componentsMixins.js"

import bannerTextField from "../../../components/bannerTextField"
import bannerTextAreas from "../../../components/bannerTextAreas"
import bannerBtn from "../../../components/bannerBtn"
import autoComplete from "@/components/vuetify/autoComplete"
import bannerProgressModal from "@/components/vuetify/progressModal"
// import bannerMakeUnderViewNotice from './components/underViewNotice'

export default {
  mixins: [commonBanner, componentsBanner],

  components: {
    bannerTextField,
    bannerTextAreas,
    bannerBtn,
    autoComplete,
    bannerProgressModal,
    // bannerMakeUnderViewNotice,
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
      TypeInfo() {
        this.info = {
          class: ``,
        }
      },

      SAVE_TYPE: {
        TEMP_SAVE: 1,
        COMPLETE_SAVE: 9,
      },

      selectSignageType: 0,
      bannerTitle: ``,
      bannerSubTitle: ``,
      selectBannerType: 0,
      selectUnderViewType: 0,

      keywordInfo: {
        rounded: true,
      },

      placeTagStyle: ``,
      placeArr: [[], [], []],
      placeSquareInfo: null,

      mapDialog: false,
      rangeInfo: {
        count: 0,
        list: [],
      },
      tempRangeInfo: null,
      rangeInfoRadius: 0,
      RANGE_NUM: 500,
      RANGE_MAX: 100,

      dialog: true,
      lodingPlaceTag: false,

      btnSignageTypeInfoArr: [],
      titleFieldInfo: {},
      subTitleFieldInfo: {},
      textFieldInfoArr: [],
      btnBannerTypeInfoArr: [],
      btnRangeInfo: {},
      rangeTextFieldInfo: {},
      textAreasInfo: {},
      btnUnderViewTypeInfoArr: [],

      titleRules: (v) => {
        return this.checkTitle(v)
      },

      titleError: `한글,영문,숫자만 입력해주세요.`,
    }
  },
  created() {
    this.$EventBus.$on(`getBannerInfo`, () => {
      this.$EventBus.$emit(`getMainBannerInfo`, this.getInfo(this.status))
    })

    this.tempRangeInfo = this.rangeInfo

    this.initTextField()
    this.initBtn()

    this.keywordInfo.type = 2

    if (this.selectBanner != null) {
      this.initMainBanner()
    }
  },

  methods: {
    initMainBanner() {
      const mainBanner = this.selectBanner.mainBanner

      if (this.deviceType != 1) {
        this.textFieldInfoArr[0].info.model = mainBanner.title
        this.textFieldInfoArr[1].info.model = mainBanner.subTitle
      }

      if (this.deviceType === 0) {
        this.setSignageType(Number(mainBanner.isVertical))
      }
      this.setBannerType(mainBanner.bannerType - 1)
      this.textAreasInfo.model = mainBanner.remark

      this.keywordInfo.keywords = mainBanner.keywords

      this.rangeInfo.count = mainBanner.places.length
      this.rangeInfo.list = mainBanner.places

      if (this.rangeInfo.count > 0) {
        this.rangeInfoRadius = this.rangeInfo.list[0].radius / this.RANGE_NUM
      }

      if (mainBanner.placeSquare.length > 0) {
        this.initPlaceSquare(mainBanner)
      }
      // if (this.selectBannerCheck(this.selectBanner) === true) {
      //   this.setUnderViewType(mainBanner.bottom_bannerType - 1)
      // }
    },
    initPlaceSquare(mainBanner) {
      let code = mainBanner.placeSquare[0].placeCode

      let totalTempArr = []
      let tempArr = new Array()

      mainBanner.placeSquare.forEach((square, i) => {
        if (code != square.placeCode) {
          code = square.placeCode

          totalTempArr.push(tempArr)

          tempArr = new Array()
        }

        tempArr.push(square)

        if (i === mainBanner.placeSquare.length - 1) {
          totalTempArr.push(tempArr)
        }
      })

      this.initPlaceTag(totalTempArr)
    },
    checkTitle(val) {
      if (
        this.checkNull(val) === true ||
        /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|\s|.;,…:*\-()[\]/?!'"‘’“”+%]+$/.test(val) ===
          false
      ) {
        return this.titleError
      }

      return true
    },
    setPlace(outIndex, arr) {
      let totalGage = 0

      // 점유율 내림차순 정렬
      arr.sort((a, b) => {
        return b[`gageRate`] - a[`gageRate`]
      })

      arr.forEach((e) => {
        totalGage += e.gageRate
      })

      let index = 0
      let start = ``

      // 지역 점유율이 100 초과 -> 첫 지역부터 점유율 감소
      // 지역 점유율이 100 미만 -> 마지막 지역부터 점유율 증가
      if (totalGage >= 100) {
        start = `left`
      } else {
        index = arr.length - 1
        start = `right`
      }

      let tag = arr[index]

      while (tag != undefined) {
        if (totalGage > 100) {
          tag.gageRate -= 1
          totalGage -= 1
        } else if (totalGage < 100) {
          tag.gageRate += 1
          totalGage += 1
        }

        this.setTagName(tag)

        this.placeArr[outIndex].unshift(tag)

        if (start === `left`) {
          tag = arr[++index]
        } else {
          tag = arr[--index]
        }
      }
    },
    setTagName(tag) {
      let names = tag.name.split(" ")

      tag.name = names[names.length - 1]

      // 동,구,시 등 지역구를 가져옴
      const lastChar = tag.name.charAt(tag.name.length - 1)

      tag.hoverName = `지역:${tag.name} / 비율:${tag.gageRate}%`
      tag.hover = false

      // 지역 길이가 길면 지역 이름 생략 후
      // 지역구의 이름을 추가
      if (tag.name.length > 4 && this.rangeInfoRadius != this.RANGE_MAX) {
        tag.name = tag.name.substring(0, 3)
        tag.name += `...${lastChar}(${tag.gageRate})`
      } else {
        tag.name += `(${tag.gageRate})`
      }
    },
    getInfo(status) {
      let info = {}

      if (this.deviceType === 0) {
        info.signageType = this.selectSignageType
      }

      if (this.deviceType != 1) {
        info.title = this.textFieldInfoArr[0].info.model
        info.subTitle = this.textFieldInfoArr[1].info.model
      }

      info.bannerType = this.selectBannerType

      info.keyword = this.$refs.autocomplete.getKeywords()

      if (this.rangeInfo.list.length != 0) {
        info.place = this.rangeInfo.list
        info.placeSquare = this.placeSquareInfo
      }
      info.remark = this.textAreasInfo.model

      info.status = status

      info.underViewType = 0

      // if (this.selectBannerCheck(this.selectBanner) === true) {
      //   info.underViewType = this.selectUnderViewType
      // } else {
      //   info.underViewType = 0
      // }

      return info
    },
    kakaomapMultiCallback(res) {
      this.mapDialog = false

      if (res.list === null || res.list.length === 0) {
        this.tempRangeInfo = this.rangeInfo
        return
      }

      this.lodingPlaceTag = true

      this.rangeInfo = res
      this.tempRangeInfo = res

      let list = this.rangeInfo.list

      this.rangeInfoRadius = list[0].radius / this.RANGE_NUM

      let formData = []

      list.forEach((e) => {
        let placeTag = {}

        placeTag.latitude = e.latitude
        placeTag.longitude = e.longitude
        placeTag.radiusType = e.radius

        formData.push(placeTag)
      })

      if (formData.length > 0) {
        this.$BaseCommon.restful
          .call2("/banner/getplacetag", formData)
          .then((res) => {
            if (res.length > 0) {
              this.initPlaceTag(res)
            }
          })
      }
    },
    initPlaceTag(res) {
      // placeArr 초기화
      this.placeArr.forEach((place) => {
        place.length = 0
      })

      JSON.parse(JSON.stringify(res)).forEach((e, i) => {
        this.setPlace(i, e)
      })

      this.placeSquareInfo = res
      this.lodingPlaceTag = false
    },
    setSignageType(select) {
      this.selectSignageType = select
      this.$EventBus.$emit("selectSignageType", this.selectSignageType)

      const none = select === 0 ? select + 1 : select - 1

      let btnSelect = this.btnSignageTypeInfoArr[select].info
      let btnNone = this.btnSignageTypeInfoArr[none].info

      btnSelect.class = "select-white-btn pa-0 mr-8"
      this.$set(btnSelect, btnSelect.check, !btnSelect.check)

      btnNone.class = "white-btn pa-0 mr-8"
      this.$set(btnNone, btnNone.check, !btnNone.check)
    },
    setBannerType(select) {
      this.selectBannerType = select

      const none = select === 0 ? select + 1 : select - 1

      let btnSelect = this.btnBannerTypeInfoArr[select].info
      let btnNone = this.btnBannerTypeInfoArr[none].info

      btnSelect.class = "select-darken-grey-btn mr-3"
      btnNone.class = "darken-grey-btn mr-3"
    },
    setUnderViewType(select) {
      this.selectUnderViewType = select

      const none = select === 0 ? select + 1 : select - 1

      let btnSelect = this.btnUnderViewTypeInfoArr[select].info
      let btnNone = this.btnUnderViewTypeInfoArr[none].info

      btnSelect.class = "select-darken-grey-btn mr-3"
      btnNone.class = "darken-grey-btn mr-3"
    },
    initTextField() {
      if (this.deviceType != 1) {
        const max = this.deviceType != 1 ? 2 : 1

        for (let i = 0; i < max; i++) {
          let field = new this.TypeInfo()

          field.info.rules = this.titleRules

          field.info.filled = true
          field.info.rounded = true
          field.info.dense = true
          field.info.required = true
          field.info.backgroundColor = `white`

          if (i === 0) {
            field.info.model = this.bannerTitle
            field.content = `배너명`
            field.info.placeholder = `광고의 제목을 입력하세요.`
            field.info.maxlength = 22
          } else {
            field.info.model = this.bannerSubTitle
            field.content = `부제목`
            field.info.placeholder = `간단한 설명을 입력하세요.`
            field.info.maxlength = 30
          }

          this.textFieldInfoArr.push(field)
        }
      }

      this.textAreasInfo.model = ``
      this.textAreasInfo.label = `ex) 광고모델 계약기간 종료`
      this.textAreasInfo.flat = true
      this.textAreasInfo.maxlength = 1000
      this.textAreasInfo.filled = true
      this.textAreasInfo.rounded = true
      this.textAreasInfo.dense = true
      this.textAreasInfo.solo = true
      this.textAreasInfo.autoGrow = true
      this.textAreasInfo.hideDetails = true
      this.textAreasInfo.rows = "6"
      this.textAreasInfo.color = `#BDBDBD`

      this.rangeTextFieldInfo.filled = true
      this.rangeTextFieldInfo.rounded = true
      this.rangeTextFieldInfo.dense = true
      this.rangeTextFieldInfo.required = true
      this.rangeTextFieldInfo.readonly = true
      this.rangeTextFieldInfo.backgroundColor = `white`
      this.rangeTextFieldInfo.style = `max-width: 6rem;`
    },
    initBtn() {
      if (this.deviceType === 0) {
        this.initBtnSignageType()
      }

      this.initBtnBannerType()
      this.initBtnRange()

      if (this.selectBannerCheck(this.selectBanner) === true) {
        this.initUnderViewType()
      }
    },
    initBtnSignageType() {
      for (let i = 0; i < 2; i++) {
        let type = new this.TypeInfo()

        type.info.width = `4.5rem`
        type.info.height = `4.5rem`

        if (i === 0) {
          type.info.class = `select-white-btn pa-0 mr-6`
          type.style = `margin-left: 0.5rem; transform: rotate(90deg);`
          type.content = `16:9`
        } else {
          type.info.class = `white-btn pa-0 mr-6`
          type.style = `margin-left: 0.75rem;`
          type.content = `9:16`
        }

        this.btnSignageTypeInfoArr.push(type)
      }
    },
    initBtnBannerType() {
      for (let i = 0; i < 2; i++) {
        let type = new this.TypeInfo()

        type.info.width = `6.5rem`
        type.info.height = `2.0rem`

        if (i === 0) {
          type.info.class = `select-darken-grey-btn mr-3`
          type.content = `정보배너`
        } else {
          type.info.class = `darken-grey-btn mr-3`
          type.content = `광고배너`
        }

        this.btnBannerTypeInfoArr.push(type)
      }
    },
    initBtnRange() {
      let type = new this.TypeInfo()

      type.info.class = `brown-btn`
      type.info.width = `7.0rem`
      type.info.height = `2.0rem`

      type.content = `설정하기`

      this.btnRangeInfo = type
    },
    initUnderViewType() {
      for (let i = 0; i < 2; i++) {
        let type = new this.TypeInfo()

        type.info.width = `6.5rem`
        type.info.height = `2.0rem`

        if (i === 0) {
          type.info.class = `select-darken-grey-btn mr-3`
          type.content = `로컬배너`
        } else {
          type.info.class = `darken-grey-btn mr-3`
          type.content = `지역광고`
        }

        this.btnUnderViewTypeInfoArr.push(type)
      }
    },
  },
  beforeDestroy() {
    this.$EventBus.$off(`getBannerInfo`)
  },
}
</script>

<style lang="scss" scoped>
@mixin box-common-style {
  background-color: white;
  border-radius: 1.5rem;
  border: 1px solid #bdbdbd;
}

span {
  font-weight: bold;
}

.v-icon {
  color: #3e2723;
  font-size: 1rem;
  margin-right: 0.3rem;
}

.span-title {
  font-weight: bold;
  color: #6b615f;
  font-size: 1rem;
}

.span-sub {
  font-weight: normal;
  font-size: 0.75rem;
}

.span-ad-sub {
  font-weight: normal;
  font-size: 0.8rem;
  color: #6b615f;
}

.place-title {
  color: #aaa19f;
  font-size: 0.625rem;
  margin-top: 0.1rem;
}

.place-tag {
  position: relative;
  color: #725d58;
  border-bottom: 1px solid #725d58;
  margin-left: 0.6rem;
  font-size: 0.563rem;
  &:hover {
    background-color: #e0e0e0;
  }
}

.place-tag-hover {
  position: absolute;
  display: inline-block;
  top: 1rem;
  left: 0%;
  border-radius: 0.188rem;
  background-color: #757575f2;
  padding: 0.375rem 0.75rem;
  z-index: 1;
  width: auto;
  white-space: nowrap;
}

.place-divider {
  margin: 0 0.2rem;
  font-weight: normal;
}

.range-box {
  @include box-common-style;
  width: 6.5rem;
  height: 2.3rem;
  padding-top: 0.4rem;
}

.range-text {
  color: #6b615f;
  margin-right: 2rem;
}

.round-box {
  @include box-common-style;
}
</style>
