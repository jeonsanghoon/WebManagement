<template>
  <v-container>
    <v-row>
      <v-col class="componentPage">
        <v-tabs class="tab1 " color="#3e2723">
          <v-tab style="background-color:#fafafa"
            >{{ $msg.getMsg("W009") /**동네정보 */ }}
            <span
              class="pa-1"
              v-html="bannerList.length > 0 ? bannerList.length : ''"
          /></v-tab>
          <v-tab style="background-color:#fafafa"
            >{{ $msg.getMsg("W010") /**로컬러 */ }}
            <span
              class="pa-1"
              v-html="localboxList.length > 0 ? localboxList.length : ''"
            />
          </v-tab>
          <v-tab-item class="pa-0  text-left">
            <v-container
              class="pt-lg-10"
              fluid
              v-if="bannerList !== null && bannerList.length > 0"
            >
              <v-row>
                <v-col
                  class="pa-2 px-sm-5 px-lg-8 bannerItem"
                  :md="3"
                  :sm="4"
                  :xs="6"
                  cols="6"
                  v-for="(item, index) in bannerList"
                  :key="item.bannerCode"
                >
                  <Vuetify-bannerCard v-model="bannerList[index]" />
                </v-col>
              </v-row>
            </v-container>
            <div v-else-if="bannerList !== null && bannerList.length === 0">
              <Table-searchTextEmpty class="mt-5" />
            </div>
          </v-tab-item>
          <v-tab-item class="pa-0  text-left">
            <v-list
              subheader
              three-line
              class="pa-0 "
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
                    <v-icon color="#50C5A4" size="32">fas fa-bookmark</v-icon>
                  </v-btn>
                  <v-btn
                    text
                    class="btnBookmark"
                    v-else
                    @click="bookmarkClick(item, true)"
                  >
                    <v-icon color="#50C5A4" size="32">far fa-bookmark</v-icon>
                  </v-btn>
                </div>
              </v-list-item>
            </v-list>
            <div v-else-if="localboxList !== null && localboxList.length === 0">
              <Table-searchTextEmpty class="mt-5" />
            </div>
          </v-tab-item>
        </v-tabs>
        <Vuetify-snackbar v-model="bookmarkSnackbar" />
      </v-col>
    </v-row>
  </v-container>
</template>
<style lang="scss" scoped>
.componentPage {
  padding-top: 0px;
}
.bookmarklist {
  padding-top: 0px;
  margin-bottom: auto;
  .v-list {
    background-color: inherit;

    .v-list-item:first-child {
      border-top: 1px solid #c4c4c4 !important;
    }
    .v-list-item {
      border-bottom: 1px solid #c4c4c4 !important;
    }
  }
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
</style>
<script>
export default {
  name: "bookmark",
  created() {
    this.logininfo = this.$BaseCommon.memberInfo.getMember()
    this.search()
    if (this.$BaseCommon.memberInfo.getMember().isLogin)
      this.isVisibleBookmark = true
  },
  data() {
    return {
      logininfo: {},
      bannerList: [],
      failedImage: false,
      localboxList: [],
      isVisibleBookmark: false,
      bookmarkSnackbar: {
        isView: false,
        title: this.$msg.getMsg("W010") /**로컬러 */,
        text: this.$msg.getMsg("W002") /**북마크 */ + "가 추가되었습니다.",
      },
    }
  },
  methods: {
    search() {
      let cond = {
        isBookmark: true,
        memberCode: this.logininfo.member.memberCode,
      }
      this.getBannerList(cond)
      this.getLocalboxList(cond)
    },
    getBannerList(cond) {
      cond.orderBy = "bd"

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
      cond.orderBy = null
      this.$BaseCommon.restful
        .call2("/user/getuserlocalboxList", cond)
        .then((res) => {
          this.localboxList = [...res]
        })
        .catch((err) => {
          console.log(err)
        })
    },
    cPicture: function(imageUrl) {
      return this.failedImage ? "http://ondaji.kro.kr" + imageUrl : imageUrl
    },
    changeKeywordCss(val) {
      return this.$BaseCommon.stringInfo.changeKeywordCss(val, "", 2)
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
          } else {
            item.isBookmark = isBookmark
            this.bookmarkSnackbar = {
              isView: true,
              icon: "far fa-check-circle",
              title: this.$msg.getMsg("W010"),
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
}
</script>
