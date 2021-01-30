<template lang="pug">
  v-container(class=" px-sm-6 px-xs-0")
    v-row
      v-col(cols="12")
        v-container(class="nomalContent")
          v-row
            v-col(cols="12" class=" pt-2 px-2 px-sm-5 px-lg-8  pb-0 d-flex flex-wrap")
              v-menu(class="ml-2" :close-on-content-click="isMenu" transition="slide-y-transition" :nudge-width="200" max-width="350px"
                      min-width="250px" style="box-shadow:none!important;" offset-y v-model="isOpened")
                  template(v-slot:activator="{ on, attrs }")
                    div(v-bind="attrs" v-on="on" :class="'selArea d-inline-flex '")
                      span(class="ondaji_font_24_bold_grey_70 mt-1 mr-1" v-text="areaName") 서울시 동작구
                      span(class="ondaji_font_20_thin_grey_70 spArea2 mr-1") 이야기
                      v-btn(icon flat :class="`mt-2 btnArea  ${isOpened ? 'hover' : ''}`") 
                        v-icon(color="#50C5A4" size="18") fas fa-chevron-circle-down
                  v-card(style="height:250px;width:100%;" outlined class="ml-5 pl-0 pr-3 pt-1" color="transparent")
                    v-card(class="d-flex flex-wrap  pa-2" color="#eaeaea")
                      Kakaomap-category( v-model="areaCode" class="pl-0 mt-0 ml-2" @change="categoryChange" )
                      v-btn(class="ml-2 btnCategory ondaji_font_12_medium_white ondaji_color_tealgreen_40"
                            rounded outlined @click="menuClose") 설정
              v-spacer
              Buttons-myLocation(@click="getCurrentBannerList")
            v-col(cols="12" class="pt-0 pb-2")
            v-col(class="pa-2 px-sm-5 px-lg-8 bannerItem" :xl="4" :lg="4" :md="4" :sm="6" :xs="6" cols="6"
                  v-for="(item, index) in bannerList" :key="item.bannerCode")
              Vuetify-bannerCard(v-model="bannerList[index]")
</template>
<style lang="scss" scoped>
.btnArea {
  height: 32px;
  width: 32px;
  &.hover {
    background-color: #eaeaea;
  }
}
.bannerItem {
  margin-bottom: 30px !important;
}
.mylocation {
  height: 24px !important;
  width: 72px !important;
  padding: 0px !important;
  padding-right: 0.25rem !important;
  &2 {
    padding: 1px 1px !important;
    height: 24px !important;
    width: 24px !important;
    min-width: 20px !important;
  }
}
.selArea {
  cursor: pointer !important;
}
.spArea2 {
  margin-top: 0.5rem;
}
.btnCategory {
  height: 26px !important;
  min-width: 54px !important;
  margin-top: 1px;
  border: 0px;
  margin-left: 4px;
}
.item {
  margin-left: 0.875rem;
}
.content {
  overflow: hidden;
}
.menuable__content__active {
  box-shadow: none;
  margin-left: -8px;
}
</style>
<script>
// @ is an alias to /src

export default {
  data() {
    return {
      isOpened: false,
      title: "지역채널",
      bannerList: [],
      areaCode: "",
      cond: null,
      isMenu: false,
      areaName: "",
      popAreaName: "",
    }
  },
  created() {
    console.log("aa>>", localStorage.keywordArea)
    if (
      this.$BaseCommon.stringInfo.isNullEmpty(localStorage.keywordArea) ||
      this.$BaseCommon.stringInfo.isNullEmpty(
        localStorage.keywordArea.square_code
      )
    )
      this.getCurrentBannerList()
    else {
      let area = JSON.parse(localStorage.keywordArea)
      console.log("위치로그 >>>> ", area)
      this.areaName = `${area.sidoName} ${area.sigunguName} ${area.dong2Name}`
      this.areaCode = area.code
      this.getBannerList()
    }
    // this.getBannerList({areaCode : "01"})
  },
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({ el: this.$el, layoutMode: "user" })
    console.log("moment >> ", this.$moment(new Date()).format("YYYYMMDD"))
  },
  methods: {
    getCurrentBannerList() {
      let self = this
      this.$BaseCommon.mapInfo.getCurrentAddress().then((res) => {
        /** 검색위치 */
        self.areaName = `${res.sigunguName} ${res.dong2Name}`
        localStorage.keywordArea = JSON.stringify(res)
        self.areaCode = res.code

        self.getBannerList()
      })
    },
    menuClose() {
      this.isMenu = true
      this.areaName = this.$BaseCommon.stringInfo.isNullEmpty(this.popAreaName)
        ? this.areaName
        : this.popAreaName
      this.getBannerList(this.cond)

      setTimeout(() => {
        this.isMenu = false
      }, 100)
    },
    categoryChange(areaCode, areaName) {
      this.cond.areaCode = areaCode

      this.popAreaName = areaName
    },
    getBannerList(cond) {
      if (cond === undefined) cond = { areaCode: this.areaCode }
      cond.areaName = this.areaName
      this.cond = cond
      this.cond.currentArea = localStorage.keywordArea
      this.areaCode = cond.areaCode
      // this.BaseCommon.stringInfo.toString(localStorage.)
      cond.latitude = this.$BaseCommon.mapInfo.getCurrentUserLocation().latitude
      cond.longitude = this.$BaseCommon.mapInfo.getCurrentUserLocation().longitude

      console.log("getBannerList", cond)
      this.$BaseCommon.restful
        .call2("/user/getUserBannerList", cond)
        .then((res) => {
          console.log("/user/getuserbannerList >>>>>>", res)
          this.bannerList = [...res]
        })
        .catch((err) => {
          console.log(err)
        })
    },
    search(res) {
      if (res.search === null) {
        this.getBannerList({
          keywordName: res.searchText,
          areaCode: this.areaCode,
        })
      } else {
        this.getBannerList({
          keywordCode: res.search.keywordCode,
          keywordName: res.search.keywordName,
          areaCode: this.areaCode,
        })
      }
    },
  },
  beforeDestroy() {
    //
  },
}
</script>
