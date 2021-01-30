<template>
  <v-container class="bannerInfo" ref="bannerInfo" pt-0 fluid>
    <v-row class="pt-0">
      <v-col cols="12" class="pa-0 grey lighten-4">
        <div class="pagination ml-2 pa-0 pt-2">
          <div class="dtButton1 caption hide ">
            <v-btn
              small
              depressed
              tile
              class="buttonInfo infoButton cation"
              @mouseover="btnInfoMouseOver"
              @mouseleave="btnInfoMouseLeave"
              ><v-btn
                x-small
                tile
                depressed
                class="infoButton icon mr-1 overline"
                ><v-icon x-small color="white ">fa fa-info</v-icon></v-btn
              ><span class="cation">주의사항</span></v-btn
            >
          </div>
          <div class="dtButton2 hide" @click="btnPreviewClick">
            <v-btn small tile depressed class="buttonDefault caption"
              >모바일 미리보기</v-btn
            >
          </div>
          <v-btn
            small
            class="mr-2 mb-2  carosel_pagination title buttonDefault "
            @click="addBanner"
            :disabled="this.bannerList.length >= maxMobileBanner"
            depressed
            tile
          >
            <v-icon x-small>fa fa-plus</v-icon>
          </v-btn>
          <v-btn
            v-for="index in pageCount"
            :key="index"
            small
            class="mr-2 mb-2 tile carosel_pagination subtitle-2 "
            :ref="'btnPage'"
            @click="movePage(index)"
            depressed
            tile
          >
            {{ index }}
          </v-btn>
        </div>
        <carousel
          ref="carousel"
          :per-page="perPage"
          :mouse-drag="true"
          :navigationEnabled="true"
          :navigation-next-label="
            `<div class='circle'><i class='material-icons md-30'>arrow_forward_ios</i></div>`
          "
          :paginationEnabled="false"
          :navigation-prev-label="
            `<div class='circle prev'><i class='material-icons md-30'>arrow_back_ios</i></div>`
          "
          :paginationPosition="'top'"
          class=" pt-0 pb-0 ma-0"
          @page-change="btnPageInit"
        >
          <slide class="grey lighten-2 ">
            <v-container class="pa-0">
              <v-row>
                <v-col
                  class="pt-0 pb-0 pr-6 text-right transparent--text  subtitle-2 "
                >
                  <a
                    href="javascript:void(0)"
                    class="aDelete blue--text text--accent-4"
                    @click="deleteBanner(-1)"
                    >초기화</a
                  >
                </v-col>
              </v-row>
            </v-container>
            <v-card
              class="pl-4 pr-4 pb-0 ml-2 mr-2 "
              outlined
              :height="bannerCardHeight"
            >
              <v-container class="pa-0">
                <v-row>
                  <v-col class="pt-1 pb-0 text-right">
                    <span class="caption pa-0 transparent--text"> . </span>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col class="pr-4 pl-4 pt-0">
                    <div
                      class="pl-1 subtitle-2 font-weight-bold  pt-0 pb-0"
                      fluid
                    >
                      <v-row>
                        <v-col cols="4" class="pt-0 pb-0 text-left">
                          <span v-if="this.loadData.regType === 6">
                            터치전
                          </span></v-col
                        >
                        <v-col cols="8" class="pt-0 pb-0 text-right">
                          <span v-if="this.loadData.regType === 0">
                            모바일배너1
                          </span>
                          <span v-else-if="this.loadData.regType === 1">
                            사이니지
                          </span>
                          <span v-else-if="this.loadData.regType === 2">
                            사이니지
                          </span>
                          <span v-else>사이니지</span>
                        </v-col>
                      </v-row>
                    </div>
                    <v-divider />
                  </v-col>
                </v-row>
                <v-row>
                  <v-col class="pt-0">
                    <Vuetify-filepond2
                      :imageUrl="this.loadData.bannerImageUrl"
                      :index="this.loadData.index"
                      :uploadSize="this.uploadSize"
                      :height="
                        this.loadData.regType === 1 ||
                        this.loadData.regType === 2
                          ? 387
                          : this.loadData.regType === 6
                          ? 535
                          : 540
                      "
                      :fileType="this.loadData.playType"
                      @uploadCallback="uploadCallback"
                    />
                  </v-col>
                </v-row>

                <v-row v-if="this.loadData.regType === 6">
                  <v-col class="pa-0 pt-0">
                    <div class="dvDetail pl-0 pr-0 pt-0">
                      <v-container class="p1-4 pr-4 pt-2 pb-0 ma-0">
                        <v-row v-if="this.loadData.regType === 6">
                          <v-col :cols="12" class="pa-0 ma-0">
                            <v-text-field
                              maxlength="25"
                              placeholder="제목을 입력해 주세요."
                              class="ml-3 mr-3 pb-0 pt-0 mb-0 mt-0 subtitle-1"
                              v-model="loadData.title"
                              @change="modelChange()"
                            ></v-text-field
                          ></v-col>
                        </v-row>
                        <v-row>
                          <v-col
                            :cols="12"
                            class="pl-0 pr-0 pt-0 pb-0 ma-0 removeboder"
                          >
                            <v-textarea
                              no-resize
                              :outlined="this.loadData.regType !== 6"
                              :height="
                                this.loadData.regType === 6
                                  ? 20
                                  : this.loadData.regType === 1
                                  ? 60
                                  : 145
                              "
                              :rows="this.loadData.regType === 6 ? 2 : 5"
                              :maxlength="30"
                              :counter="false"
                              placeholder="간단한 설명을 입력해 주세요."
                              v-model="loadData.subTitle"
                              @change="modelChange()"
                              class="ml-3 mr-3 pb-0 mb-0  pt-0 mt-0 pl-0 pr-0 body-2"
                            >
                            </v-textarea
                          ></v-col>
                        </v-row>
                      </v-container>
                    </div>
                  </v-col>
                </v-row>
                <v-row v-else-if="this.loadData.regType === 0">
                  <v-col class="pt-0 pb-0 pl-0 pr-0">
                    <v-textarea
                      no-resize
                      :outlined="this.loadData.regType !== 6"
                      :height="105"
                      :rows="this.loadData.regType === 6 ? 3 : 5"
                      :maxlength="220"
                      v-model="loadData.content"
                      :counter="true"
                      placeholder="상세페이지 내용은 온닫이 사이트에서 노출이 됩니다."
                      @change="modelChange()"
                      class="ml-3 mr-3 pb-0 mb-0 mt-0 body-2"
                    >
                    </v-textarea>
                  </v-col>
                </v-row>
              </v-container>
            </v-card>
          </slide>
          <slide class="grey lighten-2" v-if="loadData.regType !== 0">
            <v-container class="pa-0">
              <v-row>
                <v-col class="pt-0 pb-0 pr-6 text-right  subtitle-2">
                  <a
                    href="javascript:void(0);"
                    class="aDelete"
                    @click="deleteBanner(0)"
                    >초기화</a
                  >
                </v-col>
              </v-row>
            </v-container>

            <v-card
              class=" pl-4 pr-4 pb-0  ml-2 mr-2"
              outlined
              :height="bannerCardHeight"
            >
              <v-container class="pa-0">
                <v-row>
                  <v-col class="pt-1 pb-0 text-right">
                    <span class="overline pa-0 grey--text lighten-1"
                      >* 모바일 배너는 5개 이하까지 제공</span
                    >
                  </v-col>
                </v-row>
                <v-row>
                  <v-col class="pr-4 pl-4 pt-0">
                    <div
                      class="pl-1 subtitle-2 font-weight-bold  pt-0 pb-0"
                      fluid
                    >
                      <v-row>
                        <v-col cols="4" class="pt-0 pb-0 text-left">
                          <span v-if="this.loadData.regType === 6">
                            터치후
                          </span>
                        </v-col>
                        <v-col cols="8" class="pt-0 pb-0 text-right">
                          <span v-if="this.loadData.regType === 6">
                            사이니지 /
                          </span>
                          모바일 배너1
                        </v-col>
                      </v-row>
                    </div>
                    <v-divider />
                  </v-col>
                </v-row>
                <v-row>
                  <v-col class="pt-0 pb-0 pt-0">
                    <v-container class="pa-0 ma-0 ">
                      <v-row class="dvDetailImage align-center justify-center">
                        <v-col
                          class="ma-0 pa-0 align-center justify-center"
                          :cols="12"
                        >
                          <v-card
                            outlined
                            class="mr-auto ml-auto detailCard"
                            :width="layDetailImage.cardWidth"
                            :height="layDetailImage.cardHeight"
                          >
                            <div
                              v-if="detailImage === ''"
                              class="text-center"
                              style="width:100%"
                            >
                              <div class="text-center">
                                <v-icon class="grey--text notImage"
                                  >fas fa-photo-video</v-icon
                                >
                              </div>
                              <div class="text-center ma-4">
                                <span
                                  v-if="this.loadData.regType === 6"
                                  style="font-size:0.9em;"
                                >
                                  터치전의 콘텐츠와 동일합니다.
                                </span>
                                <span v-else style="font-size:0.9em;"
                                  >사이니지 콘텐츠와 동일합니다.</span
                                >
                              </div>
                              <div></div>
                            </div>
                            <v-img
                              v-if="detailImage !== ''"
                              class="pa-0 ma-0 align-center justify-center"
                              ref="imgDtl"
                              :src="detailImage"
                              :max-height="layDetailImage.cardHeight - 2"
                              :max-width="layDetailImage.cardWidth - 2"
                              contain
                            ></v-img>
                          </v-card>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col class="pt-4 pb-0 pl-0 pr-0">
                    <v-textarea
                      no-resize
                      outlined
                      :height="
                        this.loadData.regType === 6
                          ? 200
                          : this.loadData.regType === 1 ||
                            this.loadData.regType === 2
                          ? 105
                          : this.loadData.regType === 0
                          ? 100
                          : 420
                      "
                      :maxlength="220"
                      :counter="true"
                      v-model="loadData.content"
                      placeholder="상세내용을 입력해 주세요."
                      @change="modelChange()"
                      class="ml-3 mr-3 pb-0 mb-0 mt-0 body-2"
                    >
                    </v-textarea>
                  </v-col>
                </v-row>
              </v-container>
            </v-card>
          </slide>

          <slide
            class="grey lighten-4"
            v-for="(data, index) in bannerList"
            :key="data.seq"
          >
            <v-container class="pa-0">
              <v-row>
                <v-col class="pt-0 pb-0 pr-6 text-right subtitle-2">
                  <a
                    href="javascript:void(0);"
                    class="aDelete blue--text text--accent-4"
                    @click="deleteBanner(index + 1)"
                    >삭제</a
                  >
                </v-col>
              </v-row>
            </v-container>
            <v-card
              class="pl-4 pr-4 pb-0  ml-2 mr-2 "
              outlined
              :height="bannerCardHeight"
            >
              <v-container class="pa-0">
                <v-row>
                  <v-col class="pt-1 pb-0 text-right">
                    <span class="overline pa-0 grey--text lighten-1"
                      >* 모바일 배너는 5개 이하까지 제공</span
                    >
                  </v-col>
                </v-row>
                <v-row>
                  <v-col class="pr-4 pl-4 pt-0">
                    <div
                      class="pl-1 subtitle-2 font-weight-bold text-right  pt-0 pb-0"
                      fluid
                    >
                      모바일 배너{{ index + 2 }}
                    </div>
                    <v-divider />
                  </v-col>
                </v-row>
                <v-row>
                  <v-col class="pt-0">
                    <Vuetify-filepond2
                      :imageUrl="data.bannerImageUrl"
                      :index="data.seq"
                      :height="540"
                      :uploadSize="'1:1'"
                      :fileType="data.playType"
                      @uploadCallback="uploadCallback"
                      @change="modelChange()"
                    />
                  </v-col>
                </v-row>
                <v-row>
                  <v-col class="pt-0 pb-0 pl-0 pr-0">
                    <v-textarea
                      no-resize
                      outlined
                      name="input-7-4"
                      :height="105"
                      :maxlength="220"
                      :counter="true"
                      placeholder="상세내용을 입력해 주세요."
                      class="ml-3 mr-3 pb-0 mb-0 mt-0 body-2"
                      v-model="bannerList[index].content"
                    >
                    </v-textarea>
                  </v-col>
                </v-row>
              </v-container>
            </v-card>
          </slide>
        </carousel>
      </v-col>
    </v-row>

    <v-dialog v-model="isVideoShow" max-width="1000px">
      <v-card class="ondajiColor">
        <v-card-title class="pb-0 .title">
          썸네일 제작<v-spacer></v-spacer>
          <v-btn icon @click="videoClose(false)">
            <v-icon>fas fa-times</v-icon>
          </v-btn>
        </v-card-title>
        <v-card-text class="pt-0">
          <v-container pb-0 pt-0>
            <Vuetify-videoUpload
              :index="videoIndex"
              :isUpload="false"
              :isThumbnail="true"
              :data="videoData"
              @callBack="callbackVideo"
            />
          </v-container>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-container>
</template>
<style>
.VueCarousel-navigation-button.VueCarousel-navigation-next {
  right: 60px;
}
.VueCarousel-navigation-button.VueCarousel-navigation-prev {
  left: 60px;
}
.bannerInfo {
  text-align: left;
}

.bannerInfo .detailCard .notImage {
  color: rgb(46, 46, 46);
  font-size: 3.3em;
}
.bannerInfo .spacer {
  flex-grow: unset !important;
}
.bannerInfo .circle {
  background-color: #fff;
  border: 1px solid #e0e0e0;
  height: 40px;
  border-radius: 50%;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  width: 40px;
  padding: 10px;
}
.bannerInfo .dtButton1 {
  position: absolute;
  left: 0px;
  top: -30px;
}
.bannerInfo .dtButton2 {
  position: absolute;
  right: 10px;
  top: -30px;
}
.bannerInfo .v-text-field input {
  font-size: 0.9em;
}
.bannerInfo .v-input input {
  font-size: 0.9em;
}

.bannerInfo .circle.prev {
  padding-left: 12px;
}
.bannerInfo .VueCarousel {
  margin-top: -45px !important;
}
.bannerInfo .VueCarousel-slide {
  padding-top: 80px;
  padding-bottom: 20px;
}
/*.bannerInfo .VueCarousel-navigation-button.VueCarousel-navigation--disabled {
    display: none;
}*/
.bannerInfo
  .VueCarousel-navigation-button:not(.VueCarousel-navigation--disabled)
  .circle {
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1), 0 5px 5px rgba(0, 0, 0, 0.1);
}

.bannerInfo
  .VueCarousel-navigation-button:not(.VueCarousel-navigation--disabled)
  .circle:hover {
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}
.bannerInfo .material-icons.md-30 {
  font-size: 20px;

  color: #757575;
}
.bannerInfo
  .removeboder
  .theme--light.v-text-field
  > .v-input__control
  > .v-input__slot:before {
  border-style: none;
}
.bannerInfo
  .theme--light.v-text-field
  > .v-input__control
  > .v-input__slot:after {
  border-style: none;
}
</style>
<style lang="scss" scoped>
.bannerInfo .aDelete {
  text-decoration: none;
  &:hover {
    text-decoration: underline;
  }
}
.bannerInfo .v-btn:not(.v-btn--round).v-size--small.carosel_pagination {
  min-width: 30px;

  padding: 5px;
}

.bannerInfo .infoButton {
  background-color: transparent !important;
  padding-left: 0px;
  font-size: 12px;
  font-weight: bold;
  min-height: 20px !important;
  min-width: 20px !important;
}
.bannerInfo .infoButton .v-icon {
  font-size: 3px !important;
}
.bannerInfo .infoButton:hover {
  background-color: #4e342e !important;
}
.bannerInfo .infoButton.icon {
  padding: 5px;
  background-color: #4e342e !important;
}
.bannerInfo .pagination {
  position: relative;
  z-index: 1;
  top: 40px;
  left: 0px;
}

.bannerInfo .pagination .pageButtonClicked {
  background-color: rgba($color: #82b1ff, $alpha: 0.2) !important;
  border: 1px solid #82b1ff !important;
}

.bannerInfo .pagination .carosel_pagination {
  border: 1px solid #bdbdbd;
}

.bannerInfo .dvDetail {
  border-radius: 5px;
  border: 1px solid #bdbdbd;
  padding: 8px;
  margin-left: 12px;
  margin-right: 12px;
  margin-bottom: 12px;
  padding-bottom: 4px;
}
.bannerInfo .dvDetailImage {
  background-size: contain;
  background-position: center;
}
.bannerInfo .dvDetailImage .v-card {
  display: flex;
  align-items: center;
  background-color: #f5eee8;
}

.bannerInfo
  .v-btn.buttonDefault.v-btn--disabled:not(.v-btn--flat):not(.v-btn--text):not(.v-btn--outlined) {
  color: rgba(0, 0, 0, 0.12) !important;
}

.bannerInfo
  .theme--light.v-btn.v-btn--disabled:not(.v-btn--flat):not(.v-btn--text):not(.v-btn--outlined) {
  background-color: rgba($color: #4e342e, $alpha: 0.2) !important;
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
    /** 0: 모바일 1: 1Frame 사이니지(9:16) 2: 1Frame 사이니지(16:9) 6: 6Frame 사이니지 */
    regType: {
      type: Number,
      default: 0,
    },
    value: {
      type: Object,
    },
  },
  data: () => {
    return {
      bannerCardHeight: 480,
      fileuploadHeight: 480,
      loadData: {
        index: 0,
        bannerCode: 0,
        title: "",
        subTitle: "",
        content: "",
        bannerImageUrl: "",
        thumbImageUrl: "",
        bannerMovieUrl: "",
      },
      detailImage: "",
      layDetailImage: {
        baseHeight: 310,
        cardHeight: 310,
        cardWidth: 100,
      },
      isVideoShow: false,
      /** 디바이스유형  D001 1: 모바일 2:사이니지 */
      videoIndex: 1,
      deviceType: 1,
      /** 화면분할 D002 1:1Frame 6:6Frame  모바일일 경우 1Frame*/
      frameType: 1,
      uploadSize: "9:16",
      isVertical: true,
      page: 1,
      perPage: 4,
      pageCount: 4,
      maxMobileBanner: 4,

      /** 배너리스트
       *  사이니지 일경우 1번, 2번카드는 첫번재 리스트에 저장된다.
       *  동영상은 모든 배너중 1개만 등록된다.
       */
      bannerList: [{}],
      videoData: null,
    }
  },
  created() {
    this.initBefore()
    //this.init()
  },
  watch: {
    bannerList: (val, oldVal) => {
      console.log("bannerList =>", val, oldVal)
      //if (val.length > 0) this.setPageCount()
    },
    value: {
      handler: function(val) {
        this.initBefore(val)
      },
    },
  },
  methods: {
    initBefore(val) {
      window.addEventListener("resize", this.handleResize)
      if (val !== undefined) this.loadData = Object.assign(this.loadData, val)
      else this.loadData = Object.assign(this.loadData, this.value)

      let chkImage =
        this.loadData.thumbImageUrl === ""
          ? this.detailImage
          : "/files/" + this.loadData.thumbImageUrl
      this.$BaseCommon.formInfo.checkImage(
        chkImage,
        () => {
          this.detailImage = chkImage
        },
        () => {
          this.detailImage = ""
        }
      )
      this.bannerList = this.loadData.bannerList

      switch (this.loadData.regType) {
        case 1:
          this.fileuploadHeight = this.uploadSize = "9:16"
          break
        case 2:
          this.uploadSize = "16:9"
          break
        default:
          this.uploadSize = "1:1"
          break
      }
      setTimeout(() => {
        this.handleResize()
        this.init()
        this.setPlayType()
      }, 500)

      console.log("로드 모델 >>", this.loadData)
    },
    init() {
      if (this.loadData.playType === undefined) this.loadData.playType = 0

      if (this.loadData.regType === 0) {
        //this.$refs.carousel.$el.querySelector('.dvDetailImage').style.backgroundImage = "url('/images/uploadFrame/Frame1_1.svg')"
        this.layDetailImage.baseHeight = 270
        this.layDetailImage.cardHeight = this.layDetailImage.baseHeight
        this.layDetailImage.cardWidth = this.layDetailImage.baseHeight
      } else if (this.loadData.regType === 1) {
        this.layDetailImage.baseHeight = 266
        this.layDetailImage.cardHeight = this.layDetailImage.baseHeight
        this.layDetailImage.cardWidth =
          (this.layDetailImage.baseHeight * 9) / 16.0
      } else if (this.loadData.regType === 2) {
        this.layDetailImage.baseHeight = 266
        setTimeout(() => {
          this.layDetailImage.cardWidth =
            this.$refs.carousel.$el.querySelector(".dvDetailImage")
              .clientWidth - 24
          this.layDetailImage.cardHeight =
            (this.layDetailImage.cardWidth * 9) / 16
        }, 200)
      } else if (this.loadData.regType === 6) {
        this.loadData.playType = 1
        //this.$refs.carousel.$el.querySelector('.dvDetailImage').style.backgroundImage = "url('/images/uploadFrame/Frame1_1.svg')"
        this.layDetailImage.baseHeight = 171
        this.layDetailImage.cardHeight = this.layDetailImage.baseHeight
        this.layDetailImage.cardWidth = this.layDetailImage.baseHeight
      }
      for (let i = 0; i < this.bannerList.length; i++) {
        if (this.bannerList[i].playType === undefined)
          this.bannerList.playType = 0
      }
      if (this.$refs.carousel.$el.querySelector(".dvDetailImage") !== null)
        this.$refs.carousel.$el.querySelector(".dvDetailImage").style.height =
          String(this.layDetailImage.baseHeight) + "px"
    },
    handleResize() {
      if (window.innerWidth < 600) this.perPage = 1
      else if (window.innerWidth < 1024) this.perPage = 2
      else if (window.innerWidth < 1650) this.perPage = 3
      else if (window.innerWidth < 1920) this.perPage = 4
      else this.perPage = 5

      this.setPageCount()
      this.page = 1
      this.btnPageInit(0)
      this.movePage(this.page)
      console.log(this.pageCount)
    },

    movePage(clickPage) {
      console.log("movePage", clickPage)
      this.page = clickPage
      this.$refs.carousel.currentPage = this.page - 1
      this.$refs.carousel.goToPage(this.page - 1)
      //this.btnPageInit(this.page)
    },
    btnPageInit(changePage) {
      if (isNaN(changePage)) return

      if (changePage !== undefined) this.page = changePage + 1
      if (this.$refs.btnPage === undefined) return
      let btn = this.$refs.btnPage
      for (let i = 0; i < btn.length; i++) {
        btn[i].$el.classList.remove("pageButtonClicked")
      }
      btn[this.page - 1].$el.classList.add("pageButtonClicked")
    },
    detailImageInit() {
      //   this.regType
    },
    callbackVideo(res, index) {
      this.isVideoShow = false
      if (index === 0) {
        this.loadData.bannerImageUrl = res.pickThumb
        this.loadData.bannerImageUrl = res.pickThumb
        this.loadData.playType = 2
        this.detailImage = res.pickThumb
        this.loadData.fileData.pickThumb = res.pickThumb
      } else {
        console.log("video Upload Callback >> ", res, index)
        console.log("썸네일 만든후", res, index)

        this.bannerList[index - 1].thumbImageUrl = res.pickThumb
        this.bannerList[index - 1].bannerImageUrl = res.pickThumb

        this.bannerList[index - 1].playType = 2
        this.bannerList[index - 1].fileData.pickThumb = res.pickThumb
      }

      this.setPlayType()
      this.loadData.bannerList = this.bannerList
      this.modelChange()
      //this.$EventBus.$emit('uploadImage_' + index, res)
    },
    setPlayType() {
      let nCnt = this.bannerList.filter((w) => w.playType === 2).length
      if (this.loadData.playType === 2) nCnt = 1
      for (let i = 0; i < this.bannerList.length; i++) {
        if (nCnt > 0) {
          if (
            this.bannerList[i].playType === undefined ||
            this.bannerList[i].playType === 0
          ) {
            this.bannerList[i].playType = 1
          }
        }
        if (isNaN(this.bannerList[i].seq))
          this.bannerList[i].seq = this.getMaxSeq() + 1
      }
      //this.loadData.playType = 1
      if (this.loadData.playType === undefined) this.loadData.playType = 0
      else if (this.loadData.playType !== 2) {
        if (nCnt > 0) {
          if (this.loadData.playType === undefined) this.loadData.playType = 1
        } else {
          this.loadData.playType = 0
        }
      }
      if (this.loadData.regType === 6) this.loadData.playType = 1
    },
    videoClose() {
      this.isVideoShow = false
    },
    setPageCount() {
      this.pageCount =
        Math.floor(((this.bannerList.length + 2.0) / this.perPage) * 1.0) +
        ((this.bannerList.length + 2) % this.perPage > 0 ? 1 : 0)
    },
    getMaxSeq() {
      let maxSeq = Math.max.apply(
        Math,
        this.bannerList.map(function(o) {
          return o.seq
        })
      )
      if (isNaN(maxSeq) || maxSeq === -Infinity) maxSeq = 0
      return maxSeq
    },
    addBanner() {
      if (this.bannerList.length >= 4) return
      let playType =
        this.bannerList.filter((w) => w.playType === 2).length === 0 ? 0 : 1
      playType = this.loadData.playType === 2 ? 1 : playType
      let maxSeq = this.getMaxSeq()
      this.bannerList.push({ playType: playType, seq: maxSeq + 1 })

      this.setPageCount()
      this.modelChange()
      setTimeout(() => {
        this.movePage(this.pageCount)
      }, 300)
    },
    uploadCallback(data, idx) {
      this.loadData.playType = 1
      if (idx === 0) this.loadData.fileData = data
      else this.bannerList[idx - 1].fileData = data
      if (data.fileContentType !== "video/mp4") {
        if (idx === 0) {
          console.log("uploadCallback-1>>>", idx, data)
          this.loadData.bannerImageUrl = data.attachmentUrl
          this.loadData.thumbImageUrl =
            data.thumbNailImage === null
              ? data.attachmentUrl
              : data.thumbNailImage
          this.loadData.playType = 1
          this.detailImage = "/files/" + this.loadData.thumbImageUrl
        } else {
          this.bannerList[idx - 1].bannerImageUrl = data.attachmentUrl
          this.bannerList[idx - 1].thumbImageUrl = data.thumbNailImage
          this.bannerList[idx - 1].playType = 1
        }
      }

      if (data.fileContentType === "video/mp4") {
        console.log("uploadCallback >> video", data)
        if (idx === 0) {
          this.loadData.playType = 2
          this.loadData.thumbImageUrl = data.thumbNailImage
          this.loadData.bannerMovieUrl = data.attachmentUrl

          this.isVideoShow = true
          this.videoIndex = idx
          setTimeout(() => {
            this.videoData = {
              videoUrl: this.loadData.bannerMovieUrl,
              arrThumbNailImage: data.arrThumbNailImage,
            }
          }, 500)
        } else {
          this.bannerList[idx - 1].playType = 2
          this.bannerList[idx - 1].thumbImageUrl = data.thumbNailImage
          this.bannerList[idx - 1].bannerMovieUrl = data.attachmentUrl
          this.isVideoShow = true
          this.videoIndex = idx
          setTimeout(() => {
            this.videoData = {
              videoUrl: this.bannerList[idx - 1].bannerMovieUrl,
              arrThumbNailImage: data.arrThumbNailImage,
            }
          }, 500)
        }
      }
      this.loadData.bannerList = this.bannerList
      this.modelChange()
    },
    modelChange() {
      let rtn = Object.assign({}, this.loadData)
      delete rtn.fileData
      rtn.bannerList = [...this.bannerList]
      rtn.bannerList.forEach(function(v) {
        delete v.fileData
      })
      this.$emit("input", rtn)
    },
    deleteBanner(idx) {
      let delFileName = ""
      if (idx === -1) {
        if (this.bannerList.filter((w) => w.playType === 2).length > 0)
          this.loadData.playType = 1
        else this.loadData.playType = 0
        if (
          this.$BaseCommon.stringInfo.isNullEmpty(this.loadData.bannerMovieUrl)
        ) {
          delFileName = this.loadData.bannerImageUrl
        } else {
          delFileName = this.loadData.bannerMovieUrl
        }

        this.loadData.title = ""
        this.loadData.subTitle = ""
        this.loadData.bannerImageUrl = ""
        this.loadData.thumbImageUrl = ""
        this.loadData.bannerMovieUrl = ""
        this.detailImage = ""
      } else if (idx === 0) {
        this.loadData.content = ""
      } else {
        if (
          this.$BaseCommon.stringInfo.isNullEmpty(
            this.bannerList[idx - 1].bannerMovieUrl
          )
        ) {
          delFileName = this.bannerList[idx - 1].bannerImageUrl
        } else {
          delFileName = this.bannerList[idx - 1].bannerMovieUrl
        }
        this.bannerList[idx - 1].bannerImageUrl = ""
        this.bannerList[idx - 1].bannerImageUrl = ""
        this.bannerList[idx - 1].thumbImageUrl = ""
        this.bannerList[idx - 1].bannerMovieUrl = ""
        this.bannerList[idx - 1].content = ""

        //this.$EventBus.$emit('setUploadImage', idx, { pickThumb: this.bannerList[idx - 1].bannerImageUrl })

        this.bannerList.splice(idx - 1, 1)
      }
      if (this.bannerList.length === 0) {
        this.addBanner()
      }

      this.modelChange()
      if (this.$BaseCommon.stringInfo.isNullEmpty(delFileName)) return
      this.$BaseCommon.restful
        .call("/file/deletefile2", { attachmentUrl: delFileName })
        .then((res) => {
          if (res != null) {
            if (res.resultType.toLowerCase() == "error") {
              //this.messageInfo.value = true
              //this.messageInfo.message = res.message
            } else {
              //this.messageInfo.value = false
              //this.messageInfo.message = ''
              ///this.uploadfile = null
            }
          }
        })
    },
    btnInfoMouseOver() {
      this.$emit("infoShow", true)
    },
    btnInfoMouseLeave() {
      console.log("infoShow", false)
      this.$emit("infoShow", false)
    },
    btnPreviewClick() {
      console.log("모바일 미리보기 이벤트")
      this.$emit("mobilePreview")
    },
  },
  destroyed() {
    window.removeEventListener("resize", this.handleResize)
  },
}
</script>
