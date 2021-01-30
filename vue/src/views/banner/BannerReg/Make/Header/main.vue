<template>
  <v-container class="pa-0" fluid>
    <!-- TEXT -->
    <v-row
      class="header-box py-2 px-1 py-sm-4 px-sm-5 px-lg-7"
      align="center"
      no-gutters
    >
      <v-col class="d-sm-flex pa-0 text-left" cols="12" lg="8">
        <span class="header-title"
          ><v-icon class="back-icon" @click="backPage">fas fa-arrow-left</v-icon
          >{{ title[deviceType] }}</span
        >

        <!-- 배너 공유함에서 배너 선택 시 노출 -->
        <!-- 모바일 -->
        <v-col
          v-if="
            selectBannerCheck(selectBanner) === true && getScreenSize() === 'xs'
          "
          class="d-sm-flex align-center pa-0"
          cols="12"
        >
          <v-col class="text-right pa-0" cols="12">
            <span class="header-sub-title mr-3">{{ text.bannerType }}</span>
            <span class="header-sub-content">{{ bannerType }}</span>
          </v-col>

          <v-col class="text-right pa-0" cols="12">
            <span class="header-sub-title">{{ text.insertDate }}</span>
            <span class="header-sub-content">{{ insertDate }}</span>
          </v-col>

          <v-col class="text-right pa-0 mb-3" cols="12">
            <span class="header-sub-title">{{ text.updateDate }}</span>
            <span class="header-sub-content">{{ updateDate }}</span>
          </v-col>
        </v-col>

        <!-- 데스크탑 & 타블렛 -->
        <v-col
          v-else-if="
            selectBannerCheck(selectBanner) === true && getScreenSize() != 'xs'
          "
          class="d-flex align-center pa-0 ml-sm-3 ml-lg-5"
          cols="8"
        >
          <v-col class="text-left pa-0" cols="12">
            <span class="header-sub-title">{{ text.bannerType }}</span>
            <span class="header-sub-content">{{ bannerType }}</span>

            <span class="header-sub-title">{{ text.insertDate }}</span>
            <span class="header-sub-content">{{ insertDate }}</span>

            <span class="header-sub-title">{{ text.updateDate }}</span>
            <span class="header-sub-content">{{ updateDate }}</span>
          </v-col>
        </v-col>
      </v-col>

      <!-- BUTTON -->
      <v-col
        class="d-flex justify-end ml-auto mb-1 mt-sm-3 mt-lg-0"
        cols="12"
        sm="4"
        lg="4"
      >
        <bannerBtn
          v-if="selectBannerCheck(selectBanner) === true"
          :info="btnLeftInfo"
          @click:button="save(`click:delete`)"
        >
          {{ btnLeftInfo.content }}
        </bannerBtn>
        <bannerBtn
          v-else
          :info="btnLeftInfo"
          @click:button="save(`click:tempSave`)"
        >
          {{ btnLeftInfo.content }}
        </bannerBtn>

        <bannerBtn :info="btnRightInfo" @click:button="save(`click:save`)">
          {{ btnRightInfo.content }}
        </bannerBtn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { commonBanner } from "../../../mixins/commonMixins"
import { componentsBanner } from "../../../mixins/componentsMixins.js"

import bannerBtn from "../../../components/bannerBtn"

export default {
  mixins: [commonBanner, componentsBanner],
  components: {
    bannerBtn,
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
      btnLeftInfo: {},
      btnRightInfo: {},

      title: [],

      text: {
        btnTempSave: `임시보관`,
        btnSave: `저장하기`,

        btnDelete: `삭제하기`,
        btnModify: `수정하기`,

        bannerType: `배너유형`,
        insertDate: `제작일`,
        updateDate: `최근 수정일`,

        infoBanner: `정보배너`,
        paidBanner: `광고배너`,
      },
    }
  },
  created() {
    this.title[0] = `사이니지 1FRAME`
    this.title[1] = `사이니지 6FRAME`
    this.title[2] = `모바일`

    this.initBtn()
  },
  computed: {
    bannerType() {
      const type = this.selectBanner.mainBanner.bannerType

      if (type === 1) {
        return this.text.infoBanner
      } else {
        return this.text.paidBanner
      }
    },
    insertDate() {
      return this.$moment(this.selectBanner.mainBanner.insertDate).format(
        "YYYY-MM-DD"
      )
    },
    updateDate() {
      return this.$moment(this.selectBanner.mainBanner.updateDate).format(
        "YYYY-MM-DD"
      )
    },
  },
  methods: {
    initBtn() {
      if (this.selectBannerCheck(this.selectBanner) === true) {
        this.btnLeftInfo.content = this.text.btnDelete
        this.btnRightInfo.content = this.text.btnModify
      } else {
        this.btnLeftInfo.content = this.text.btnTempSave
        this.btnRightInfo.content = this.text.btnSave
      }

      this.btnLeftInfo.class = `transparent-btn`

      this.btnRightInfo.class = `darken-brown-btn ml-sm-6`

      const info = {
        width: `6em`,
        height: `2.2em`,
        rounded: false,
      }

      this.setBtn([this.btnLeftInfo, this.btnRightInfo], info)
    },
    save(emit) {
      this.$emit(emit)
    },
    backPage() {
      if (this.selectBannerCheck(this.selectBanner) === true) {
        this.$router.go(-1)
      } else {
        this.$emit(`backPage`)
      }
    },
  },
}
</script>

<style lang="scss" scoped>
span {
  font-weight: normal;
}

.header-box {
  background-color: #f1f0f0;
}

.header-title {
  font-weight: bold;
  font-size: 1rem;
  color: #6b615f;
}

.header-sub-title {
  font-weight: bold;
  font-size: 0.75rem;
}

.header-sub-content {
  font-size: 0.75rem;
  margin-left: 0.4rem;
  margin-right: 0.5rem;
}

@media screen and (max-width: 599px) {
  .header-sub-content {
    margin-left: 0.6rem;
    margin-right: 0.2rem;
  }
}

.back-icon {
  padding-bottom: 0.2rem;
  padding-right: 0.4rem;
  color: #616161;
  cursor: pointer;
}
</style>
