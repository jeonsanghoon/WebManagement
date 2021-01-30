<template lang="pug">
  v-container(class="bannerDetail fullContent")
    v-row(class="bannerDetailRow")
      v-col(cols="12")
        v-container(class="content ")
          v-row(class="contentRow")
            v-col(cols="12" class="text-left colBoder")
              v-btn(icon)
                v-icon(class="backIcon" @click="() => { this.$router.go(-1)}") fas fa-chevron-left
            v-col(cols="12" class="pt-0 pb-0 colBoder")
              carousel(class="carousel" :per-page="1" :navigationEnabled="true" 
                      :navigationNextLabel="`<div class='circle'><i class='material-icons btnCarouselArrow'>arrow_forward_ios</i></div>`"
                      :navigationPrevLabel="`<div class='circle prev'><i class='material-icons btnCarouselArrow'>arrow_back_ios</i></div>`"
                      @page-change="caroselChange")
                slide
                  v-img(v-if="data.playType === 1" :src="cPicture('/files/' + data.bannerImageUrl)" @error="onImgError" contain aspect-ratio="1")
                  video(v-if="data.playType === 2" class="vod" autoplay loop="loop" muted="muted" webkit-playsinline="webkit-playsinline" playsinline="playsinline" id="pcvid")
                    source(:src="'http://ondaji.kro.kr/files/' + data.bannerMovieUrl")
                slide(v-for="(slide, i) in subList" :key="i")
                  v-img(v-if="slide.playType === 1" :src="cPicture('/files/' + slide.bannerImageUrl)" @error="onImgError" contain aspect-ratio="1")
                  video(v-if="slide.playType === 2" class="vod" autoplay loop="loop" muted="muted" webkit-playsinline="webkit-playsinline" playsinline="playsinline" id="pcvid")
                    source(:src="'http://ondaji.kro.kr/files/' + slide.bannerVideoUrl")
            v-col(class="colBoder text-left")
              v-list(subheader class="mt-2 ")
                v-list-item(style="height:40px")
                  v-list-item-avatar(size="36")
                    v-icon(v-if="data.profileUrl === null || data.profileUrl === ''" size="24") far fa-user-circle
                    v-img(v-else :src="cPicture('/files/' + data.profileUrl)" @error="onImgError")
                  v-list-item-content
                    v-list-item-title(class="ondaji_font_12_bold_grey_100" v-text="data.localboxName === null || data.localboxName === '' ? data.memberName : data.localboxName")
                    v-list-item-subtitle(class="ondaji_font_12_thin_grey_100" v-text="data.areaName")
                    v-list-item-subtitle(v-text="data.desc")
                  v-spacer
                  div(v-if="isVisibleBookmark" class="dvBookmark")
                    v-btn(v-if="data.isBookmark" @click="bookmarkClick(data, false)" outlined color="#bdbdbd" width="36px" )
                      v-icon(class="mt-1") fas fa-bookmark
                    v-btn(v-else color="#bdbdbd" outlined width="36px" @click="bookmarkClick(data, true)")
                      v-icon(class="mt-1") far fa-bookmark
            v-col(cols="12" class="text-left px-4 py-2")
              div(class="ondaji_font_16_bold_grey_100 pb-2") {{ data.title }}
              div(v-if="page === 0" class="ondaji_font_16_thin_grey_80 pb-6") {{ data.content }}
              div(v-if="page > 0" class="ondaji_font_16_thin_grey_80 pb-6") {{ selectedSubItem.content }}
              div(class="ondaji_font_14_thin_grey_60")
                span(class="mr-3" v-text="setRegDiff(data.diffMin)")
                span(class="mr-3" v-text="'북마크 ' + (data.bookmarkCnt === null ? 0 : data.bookmarkCnt)")
                span(v-text="'조회수 ' + data.searchCnt")
              div(class="mt-2 ondaji_font_14_medium_green_100")
                span(class="mr-2 keywords" @click="keywordClick(key1)" v-for="(key1, idx) in arrKeyword" :key="idx") {{ '#' + key1.split("|")[1] }}
            v-col(cols="12" class="pt-4 px-30")
              v-divider(class="divider")
            v-col(cols="12" class="pt-0 ")
              v-container(fluid class="pl-2 pr-2 bannerContainer")
                v-row
                  v-col(class="pa-1 bannerItem" cols="6" v-for="(item, index) in bannerList" :key="item.bannerCode")
                    Vuetify-bannerCard(v-model="bannerList[index]" cardType="sublist")
          Vuetify-snackbar(v-model="bookmarkSnackbar")
</template>
<style lang="scss">
.bannerDetail {
  padding-top: 40px;
  .divider {
    border-color: #e0e0e0;
  }
  .dvBookmark {
    .v-btn:not(.v-btn--round).v-size--default {
      min-width: 36px;
    }
    .v-icon {
      color: #50c5a4 !important;
      font-size: 20px;
    }
  }
  .backIcon {
    font-size: 13px !important;
    color: #3e2723 !important;
  }

  .v-image__image {
    background-color: #e7e7e7;
    //border-radius: 3px;
  }
  .adIcon {
    position: absolute;
    z-index: 1;
    right: 5px;
    top: 5px;
    background: rgb(104, 104, 104);
    padding-right: 4px;
    padding-left: 4px;
    padding-top: 3px;
    border-radius: 3px;
    color: white;
    margin: 0px;
  }
  .contentSize {
    max-width: 424px;
    min-width: 340px;
    max-height: 424px;
    min-height: 340px;

    width: auto;
    height: 100vw;
  }
  .VueCarousel-slide {
    @extend .contentSize;
  }
  .content {
    @extend .contentSize;
    padding-top: 0px;
    padding-bottom: 0px;
    background-color: #fff !important;
    .row.contentRow {
      background-color: inherit;
      border: 1px solid #e0e0e0;
    }
    .colBoder {
      padding: 0px;
      border-bottom: 1px solid #e0e0e0;
    }
  }
  .carousel {
    @extend .contentSize;
    .VueCarousel-navigation-button.VueCarousel-navigation-next {
      right: 40px;
    }
    .VueCarousel-navigation-button.VueCarousel-navigation-prev {
      left: 40px;
    }
    .VueCarousel-navigation--disabled {
      display: none;
    }
    .btnCarouselArrow {
      font-size: 12px;
      margin: auto;
    }
    .prev {
      .btnCarouselArrow {
        padding-left: 4px;
      }
    }
    .circle {
      background-color: #fff;
      border: 1px solid #e0e0e0;
      display: flex;
      align-items: center;
      text-align: center;
      margin: auto;
      height: 25px;
      border-radius: 50%;
      -moz-border-radius: 50%;
      -webkit-border-radius: 50%;
      width: 25px;
      font-size: 0.5rem;
      margin: auto;
      opacity: 1;

      .VueCarousel-navigation-next {
        margin-right: 20px !important;
      }
    }
  }
  .vod {
    width: 100% !important;
    height: auto !important;
  }
  .VueCarousel-pagination {
    background-color: #f2f2f2 !important;
    .VueCarousel-dot-container {
      margin-top: 0px !important;
      border-top: 1px solid #e0e0e0;
      .VueCarousel-dot {
        background-color: #aaaaaa !important;
        &.VueCarousel-dot--active {
          background-color: #50c5a4 !important;
        }
      }
      button {
        margin-top: 0px !important;
        padding-right: 3px !important;
        padding-left: 3px !important;
        height: 8px !important;
        width: 8px !important;
      }
    }
  }
  .keywords {
    cursor: pointer;
  }

  .localBannerList {
    padding-top: 10px !important;
    padding-left: 4px;
    .item {
      margin-top: 12px;
      margin-left: 12px;
    }
  }
  .bannerComponent {
    .card-image {
      height: 120px;
    }
  }
}
</style>
<script>
import { Carousel, Slide } from "vue-carousel"
export default {
  components: {
    Carousel,
    Slide,
  },
  props: {
    bannerCode: {
      type: Number,
      default: -1,
    },
  },
  created() {
    this.bannerDetailInit()
  },
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({ el: this.$el, layoutMode: "user" })
    this.$EventBus.$on("bannerDetailInit", () => {
      this.bannerDetailInit()
    })
  },
  data() {
    return {
      title: "배너상세",
      data: null,
      subList: [],
      failedImage: false,
      isVisibleBookmark: false,
      page: 0,
      selectedSubItem: null,
      arrKeyword: [],
      bannerList: [],
      bookmarkSnackbar: {
        isView: false,
        title: "배너",
        text: "북마크가 추가되었습니다.",
      },
    }
  },

  methods: {
    bannerDetailInit() {
      if (this.$BaseCommon.memberInfo.getMember().isLogin)
        this.isVisibleBookmark = true
      this.getList()
    },
    getList() {
      let addSearchCnt = this.$BaseCommon.dataInfo.getAddClickCount(
        "clickedBanners",
        this.bannerCode
      )

      this.$BaseCommon.restful
        .call2("/user/getUserBannerDetail", {
          searchCode: this.bannerCode,
          addSearchCnt: addSearchCnt,
        })
        .then((res) => {
          this.data = res
          console.log("/user/getUserBannerDetail >> ", this.data)
          this.subList = res.subList
          this.page = res.subList.length > 0 ? 0 : -1
          this.selectedSubItem = this.page >= 0 ? this.subList[0] : {}
          this.setKeyword(res.keywords)
          if (res.areaCode !== null)
            this.getBannerList({
              areaCode: res.areaCode,
              areaName: res.areaName,
            })
          else this.bannerList = []
          window.scrollTo(0, 0)
        })
    },
    cPicture: function(imageUrl) {
      console.log("이미지 >>>>>>>>>> http://ondaji.kro.kr" + imageUrl)
      return this.failedImage ? "http://ondaji.kro.kr" + imageUrl : imageUrl
    },

    onImgError: function(event) {
      console.log(event)
      this.failedImage = true
      //this.$forceUpdate();
    },
    bookmarkClick(item, isBookmark) {
      let data = {
        //bookmarkCode: item.bookmarkCode,
        bookmarkType: 1,
        isBookmark: isBookmark,
        bannerCode: item.bannerCode,
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
          } else item.isBookmark = isBookmark

          this.bookmarkSnackbar = {
            isView: true,
            icon: "far fa-check-circle",
            title: "배너",
            text: isBookmark
              ? "북마크가 <span style='color:#50C5A4;opacity:1'>추가</span>되었습니다."
              : "북마크가 <span style='color:#FFCB65;opacity:1'>삭제</span>되었습니다.",
          }
        })
        .catch((err) => {
          this.$alert(err)
        })
    },

    caroselChange(val) {
      this.page = val
      val = val >= 1 ? val - 1 : val

      this.selectedSubItem = this.subList[val]
    },
    setRegDiff(val) {
      return this.$BaseCommon.dateInfo.getDateDiffString(val)
    },
    setKeyword(val) {
      if (val === null) return ""
      this.arrKeyword = val.split(",")
    },
    keywordClick(val) {
      let item = {
        keywordCode: val.split("|")[0],
        keywordName: val.split("|")[1],
      }
      console.log("keywordClick>>", item)
      this.$router.push({ name: "usersearch", params: item })
    },
    getBannerList(cond) {
      console.log("getBannerList", cond)
      this.$BaseCommon.restful
        .call2("/user/getuserbannerList", cond)
        .then((res) => {
          this.bannerList = [...res]
        })
        .catch((err) => {
          console.log(err)
        })
    },
  },
  beforeDestroy() {
    console.log("beforeDestroy")
    this.$EventBus.$off("bannerDetailInit")
  },
}
</script>
