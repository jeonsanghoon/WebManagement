<template>
  <v-container class="bannerTabContainer px-sm-6 px-xs-0">
    <v-row>
      <v-col cols="12">
        <v-container class="nomalContent">
          <v-row>
            <v-col
              cols="12"
              class="pt-2 px-4 px-sm-5 px-lg-8  pb-0 d-flex flex-wrap"
            >
              <v-menu
                class="ml-2"
                :close-on-content-click="isMenu"
                transition="slide-y-transition"
                :nudge-width="200"
                max-width="350px"
                min-width="250px"
                style="box-shadow:none!important;"
                offset-y
              >
                <template v-slot:activator="{ on, attrs }">
                  <div v-bind="attrs" v-on="on" class="d-inline-flex">
                    <span
                      class="ondaji_font_24_bold_grey_70 mt-1 mr-1"
                      v-text="areaName"
                    >
                      서울시 동작구
                    </span>
                    <span class="ondaji_font_20_thin_grey_70 spArea2 mr-1"
                      >이야기</span
                    >
                    <v-icon color="#50C5A4" size="18" class="pl-0 mt-1"
                      >fas fa-chevron-circle-down</v-icon
                    >
                  </div>
                </template>

                <v-card
                  style="height:250px;"
                  outlined
                  class="pl-0 pr-3 "
                  color="transparent"
                >
                  <v-card class="d-flex flex-wrap  pa-2" color="#eaeaea">
                    <Kakaomap-category
                      v-model="areaCode"
                      class="pl-0 mt-0 ml-2"
                      @change="categoryChange"
                    />
                    <v-btn
                      rounded
                      class="ml-2  btnCategory ondaji_font_14_medium_white ondaji_color_tealgreen_40"
                      outlined
                      @click="menuClose"
                      >설정</v-btn
                    >
                  </v-card>
                </v-card>
              </v-menu>
              <v-spacer></v-spacer>
              <Buttons-myLocation @click="getCurrentBannerList" />
            </v-col>
            <v-col cols="12" class="pt-0 px-sm-0 pb-2">
              <v-tabs class="tab1 " color="#3e2723">
                <v-tab style="background-color:#fafafa">동네정보</v-tab>
                <v-tab style="background-color:#fafafa">로컬러</v-tab>
                <v-tab-item class="pa-0  text-left">
                  <v-container
                    class="pt-lg-10"
                    fluid
                    v-if="bannerList !== null && bannerList.length > 0"
                  >
                    <v-row>
                      <v-col
                        class="pa-2 px-sm-5 px-lg-8 bannerItem"
                        :md="4"
                        :sm="6"
                        :xs="6"
                        cols="6"
                        v-for="(item, index) in bannerList"
                        :key="item.bannerCode"
                      >
                        <Vuetify-bannerCard
                          v-model="bannerList[index]"
                          :keywordName="cond.keywordName"
                        />
                      </v-col>
                    </v-row>
                  </v-container>
                  <div
                    v-else-if="bannerList !== null && bannerList.length === 0"
                  >
                    <Table-searchTextEmpty
                      v-model="searchKeywordName"
                      class="mt-5"
                    />
                  </div>
                </v-tab-item>
                <v-tab-item class="pa-0  text-left">
                  <v-list
                    subheader
                    three-line
                    class="pa-0 px-sm-5 px-lg-8"
                    v-if="localboxList !== null && localboxList.length > 0"
                  >
                    <v-list-item
                      class="px-lg-10 py-2 pl-xs-2 pl-sm-3 px-0"
                      v-for="(item, index) in localboxList"
                      :key="index"
                      @click="localboxClick(item, 1)"
                    >
                      <v-list-item-avatar size="90">
                        <v-img
                          :src="cPicture('/files/' + item.profileUrl)"
                          @error="onImgError"
                          class="ondaji_color_grey_20"
                        ></v-img>
                      </v-list-item-avatar>

                      <v-list-item-content class="pl-2">
                        <v-list-item-title
                          class="item_title ondaji_font_18_bold_brown_80"
                          v-html="changeKeywordCss(item.name)"
                        ></v-list-item-title>
                        <v-list-item-subtitle
                          class="item_subtitle ondaji_font_16_medium_brown_80"
                          v-html="item.area_address"
                        ></v-list-item-subtitle>
                        <v-list-item-subtitle
                          class="ondaji_font_13_medium_grey_60"
                          v-html="item.desc"
                        >
                        </v-list-item-subtitle>
                      </v-list-item-content>

                      <div class="dvBookmark ml-lg-10" v-if="isVisibleBookmark">
                        <v-btn
                          class="btnBookmark"
                          text
                          v-if="item.isBookmark"
                          @click="bookmarkClick(item, false)"
                        >
                          <v-icon color="#50C5A4" size="32"
                            >fas fa-bookmark</v-icon
                          >
                        </v-btn>
                        <v-btn
                          text
                          class="btnBookmark"
                          v-else
                          @click="bookmarkClick(item, true)"
                        >
                          <v-icon color="#50C5A4" size="32"
                            >far fa-bookmark</v-icon
                          >
                        </v-btn>
                      </div>
                    </v-list-item>
                  </v-list>
                  <div
                    v-else-if="
                      localboxList !== null && localboxList.length === 0
                    "
                  >
                    <Table-searchTextEmpty
                      v-model="searchKeywordName"
                      class="mt-5"
                    />
                  </div>
                </v-tab-item>
              </v-tabs>
            </v-col>
            <v-col cols="12" style="display:contents;"> </v-col>
          </v-row>
          <v-row align="start" justify="start"> </v-row>
          <Vuetify-snackbar v-model="bookmarkSnackbar" />
        </v-container>
      </v-col>
    </v-row>
  </v-container>
</template>
<style lang="scss">
.bannerTabContainer .theme--light.v-tabs > .v-tabs-bar {
  background-color: transparent;
}
.bannerTabContainer .v-slide-group__content {
  margin-left: 32px;
  margin-right: 32px;
  border-bottom: 1px solid #c4c4c4;
}
.bannerTabContainer .theme--light.v-tabs-items {
  background-color: #fafafa;
}
.bannerItem {
  margin-bottom: 30px !important;
}
@include mobile {
  .bannerTabContainer .v-slide-group__content {
    margin-left: 16px;
    margin-right: 16px;
  }
}

@include tablet {
  .bannerTabContainer .v-slide-group__content {
    margin-left: 20px;
    margin-right: 20px;
  }
}
</style>
<style lang="scss" scoped>
.dvBookmark {
  margin-right: 500px;
}
.v-list {
  background-color: inherit;
}
.v-item-group {
  background-color: #fafafa !important;
  border-bottom: 1px solid #c4c4c4 !important;
}

.v-slide-group.v-tabs-bar {
  border-bottom: 1px solid #c4c4c4;
}
.v-list-item {
  background-color: #fafafa !important;
  border-bottom: 1px solid #c4c4c4;
}

.theme--light.v-tabs > .v-tabs-bar {
  background-color: #fafafa !important;
}
.ondaji.User .item {
  &_title {
    padding-bottom: 0.5rem;
  }
  &_subtitle {
    padding-bottom: 0.5rem;
  }
}
.btnBookmark {
  margin-right: 1rem;
  min-width: 68px !important;
  max-width: 32px !important;
  min-height: 68px !important;
  border: solid 1px #c4c4c4;
  .v-icon {
    font-size: 2rem !important;
  }
}
@media screen and (max-width: (1440px)) {
  .dvBookmark,
  .btnBookmark {
    margin-right: 0px;
  }
}
@media (min-width: 0px) and (max-width: ($grid-breakpoints_sm - 1px)) {
  .dvBookmark,
  .btnBookmark {
    min-width: 32px !important;
    max-width: 32px !important;
    min-height: 32px !important;
    max-height: 32px !important;
    .v-icon {
      font-size: 20px !important;
    }
  }

  .v-avatar.v-list-item__avatar {
    height: 60px !important;
    min-width: 60px !important;
    width: 60px !important;
  }
  .ondaji.User .item {
    &_title {
      font-size: 16px !important;
      padding-bottom: 0.5rem;
    }
    &_subtitle {
      font-size: 14px !important;
      padding-bottom: 0.5rem;
    }
  }
}
.ondaji.User .item {
  &_title {
    padding-bottom: 0.5rem;
  }
  &_subtitle {
    padding-bottom: 0.5rem;
  }
}
.mylocation {
  padding: 0.5rem;
  height: 24px !important;
  width: 80px !important;
  &2 {
    padding: 1px 1px !important;
    height: 24px !important;
    width: 24px !important;
    min-width: 20px !important;
  }
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
.tab1 {
  background-color: #fafafa !important;
  .v-item-group {
    @extend .tab1;
  }
  .v-slide-group {
    @extend .tab1;
  }
  .v-slide-group {
    @extend .tab1;
  }
}
.tab1 .item {
  margin-right: 0.5rem;
  display: inline-block;
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
export default {
  data() {
    return {
      title: "지역채널",
      bannerList: null,
      localboxList: null,
      areaCode: "01|01|01",
      cond: null,
      isMenu: false,
      areaName: "",
      popAreaName: "",
      failedImage: false,
      searchKeywordName: "",
      isVisibleBookmark: false,
      bookmarkSnackbar: {
        isView: false,
        title: "로컬러",
        text: "북마크가 추가되었습니다.",
      },
    }
  },
  created() {
    if (!this.$BaseCommon.stringInfo.isNullEmpty(localStorage.keywordArea)) {
      let area = JSON.parse(localStorage.keywordArea)
      console.log("위치로그 >>>> ", area)
      this.areaName = `${area.sigunguName} ${area.dong2Name}`
      this.areaCode = area.code
    }
    this.search({
      keywordCode:
        this.$route.params.keywordCode === undefined
          ? null
          : this.$route.params.keywordCode,
      keywordName:
        this.$route.params.keywordName === undefined
          ? null
          : this.$route.params.keywordName,
      areaCode: this.areaCode,
    })

    if (this.$BaseCommon.memberInfo.getMember().isLogin)
      this.isVisibleBookmark = true
  },
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({ el: this.$el, layoutMode: "user" })
    this.$EventBus.$on("keywordSearch", (param) => {
      console.log("keywordSearch", param)

      this.search({
        keywordCode: param.keywordCode === undefined ? null : param.keywordCode,
        keywordName: param.keywordName === undefined ? null : param.keywordName,
        areaCode: this.areaCode,
      })
    })

    console.log("moment >> ", this.$moment(new Date()).format("YYYYMMDD"))
  },
  methods: {
    getCurrentBannerList() {
      this.$BaseCommon.mapInfo.getCurrentAddress().then((res) => {
        this.areaName = `${res.sigunguName} ${res.dong2Name}`
        this.areaCode = res.code
        localStorage.keywordArea = JSON.stringify(res) /**검색위치 */
        this.search()
      })
    },
    menuClose() {
      this.isMenu = true
      this.areaName = this.$BaseCommon.stringInfo.isNullEmpty(this.popAreaName)
        ? this.areaName
        : this.popAreaName
      this.search(this.cond)

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
      this.cond.latitude = this.$BaseCommon.mapInfo.getCurrentUserLocation().latitude
      this.cond.longitude = this.$BaseCommon.mapInfo.getCurrentUserLocation().longitude

      console.log("getBannerList", cond)
      this.$BaseCommon.restful
        .call2("/user/getuserbannerList", cond)
        .then((res) => {
          this.bannerList = [...res]
          console.log("배너조회>>", this.bannerList)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    getLocalboxList(cond) {
      if (cond === undefined) cond = { areaCode: this.areaCode }
      cond.areaName = this.areaName

      this.cond = cond
      this.cond.currentArea = localStorage.keywordArea
      console.log("getLoclboxList", cond)
      this.$BaseCommon.restful
        .call2("/user/getuserlocalboxList", cond)
        .then((res) => {
          this.localboxList = [...res]
        })
        .catch((err) => {
          console.log(err)
        })
    },
    search(cond) {
      if (cond === undefined) {
        this.searchKeywordName = ""
        this.getBannerList()
        this.getLocalboxList()
      } else {
        this.searchKeywordName = cond.keywordName
        this.getBannerList(cond)
        this.getLocalboxList(cond)
      }
    },
    cPicture: function(imageUrl) {
      return this.failedImage ? "http://ondaji.kro.kr" + imageUrl : imageUrl
    },
    changeKeywordCss(val) {
      return this.$BaseCommon.stringInfo.changeKeywordCss(
        val,
        this.cond.keywordName,
        2
      )
    },
    onImgError: function(event) {
      console.log(event)
      this.failedImage = true
      //this.$forceUpdate();
    },
    bookmarkClick(item, isBookmark) {
      event.stopPropagation()
      console.log("bookmark", item, isBookmark)

      let data = {
        //bookmarkCode: item.bookmarkCode,
        bookmarkType: 2,
        isBookmark: isBookmark,
        localboxCode: item.localboxCode,
        memberCode: -1,
      }
      this.$BaseCommon.restful
        .call2("/member/bookmarkSave", data)
        .then((res) => {
          if (res.resultType === "Error") {
            console.log(res)
            this.$alert(res.message).then(() => {
              if (!this.$BaseCommon.LoginInfo().isLoin) {
                this.$router.push({
                  name: "login",
                  params: { loginMode: "user" },
                })
              }
            })
          } else {
            item.isBookmark = isBookmark
            this.bookmarkSnackbar = {
              isView: true,
              icon: "far fa-check-circle",
              title: "로컬러",
              text: isBookmark
                ? "북마크가 <span style='color:#50C5A4;opacity:1'>추가</span>되었습니다."
                : "북마크가 <span style='color:#FFCB65;opacity:1'>삭제</span>되었습니다.",
            }
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    localboxClick(item, nCnt) {
      console.log("localboxClick", item, nCnt)
      this.$router
        .push({ path: "/user/localboxdetail/" + item.localboxCode })
        .push({ path: "/user/bannerdetail/" + this.data.bannerCode })
    },
  },
  beforeDestroy() {
    console.log("beforeDestroy")
    this.$EventBus.$off("keywordSearch")
  },
}
</script>
