<template>
  <v-container>
    <v-row class="align-left justify-left">
      <v-col cols="12" class="pb-0 d-flex flex-wrap ">
        <v-container class="pa-0" style="max-width:900px">
          <v-row>
            <v-col cols="12" class="py-0">
              <v-expansion-panels
                v-model="panel"
                multiple
                flat
                class="expansion "
              >
                <v-expansion-panel>
                  <v-expansion-panel-header expand-icon="">
                    <div style="width:100%" class="d-flex flex-wrap">
                      <v-avatar size="20" color="#3E2723">
                        <v-icon v-if="data.profileUrl === null" dark
                          >far fa-user-circle</v-icon
                        >
                        <v-img
                          v-else
                          :src="'/files/' + data.profileUrl"
                        ></v-img>
                      </v-avatar>
                      <span
                        class="pl-1 pt-1 ondaji_font_16_bold_brown_100"
                        v-text="
                          data.localboxName === null
                            ? data.memberName
                            : data.localboxName
                        "
                        >{{
                      }}</span>
                      <v-btn small icon>
                        <v-icon color="#3E2723" size="12" v-if="isDetail">
                          fas fa-chevron-up
                        </v-icon>
                        <v-icon color="#3E2723" size="12" v-else>
                          fas fa-chevron-down
                        </v-icon>
                      </v-btn>
                      <v-spacer></v-spacer>
                      <v-btn rounded small outlined color="#50C5A4">
                        <v-icon size="12" class="mr-1">fas fa-bookmark</v-icon>
                        <span style="font-size:12px;">{{
                          getBookmarkCnt(this.data.bookmarkCnt)
                        }}</span>
                      </v-btn>
                    </div>
                  </v-expansion-panel-header>
                  <v-expansion-panel-content class="text-left">
                    <span class="ondaji_font_14_medium_grey_60">{{
                      data.desc
                    }}</span>
                    <v-card flat class="pa-0 mt-2 ondaji_color_grey_10">
                      <v-container fluid class="pt-0">
                        <v-row>
                          <!--authType:20 사업자 authType: 일반회원-->
                          <v-col
                            cols="12"
                            class="d-flex pb-0"
                            v-if="data.authType === 20"
                          >
                            <div
                              class="pb-0 ondaji_font_12_medium_grey_60"
                              style="width:120px!important;"
                            >
                              업종
                            </div>
                            <div class="ondaji_font_12_thin_grey_60">
                              {{ data.businessTypeName }}
                            </div>
                          </v-col>
                          <v-col cols="12" class="pb-0 d-none">
                            <div
                              class="pb-0 ondaji_font_12_medium_grey_60"
                              style="width:120px!important;"
                            >
                              사이니지 이용현황
                            </div>
                            <div class="ondaji_font_12_thin_grey_60">
                              {{ data.frame1cnt !== null ? "1 Frame" : "" }}
                              <span
                                class="px-1"
                                v-if="
                                  data.frame1cnt !== null &&
                                    data.frame6Cnt !== null
                                "
                                >/</span
                              >
                              {{ data.frame6Cnt !== null ? "6 Frame" : "" }}
                            </div>
                          </v-col>
                          <v-col
                            cols="12"
                            class="d-flex pb-0"
                            v-if="data.authType === 20"
                          >
                            <div
                              class="pb-0 ondaji_font_12_medium_grey_60"
                              style="width:120px!important;"
                            >
                              영업시간
                            </div>
                            <div class="ondaji_font_12_thin_grey_60">
                              {{ data.businessTime }}
                            </div>
                          </v-col>
                          <v-col cols="12" class="d-flex pb-0">
                            <div
                              class="pb-0 ondaji_font_12_medium_grey_60"
                              style="width:120px!important;"
                            >
                              연락처
                            </div>
                            <div
                              class="ondaji_font_12_thin_grey_60"
                              v-if="data.phone !== null"
                            >
                              {{ data.phone.phone() }}
                            </div>
                          </v-col>
                          <v-col cols="12" class="d-flex pb-0">
                            <div
                              class="pb-0 ondaji_font_12_medium_grey_60"
                              style="width:120px!important;"
                            >
                              주소
                            </div>
                            <div class="ondaji_font_12_thin_grey_60">
                              {{ data.address }} {{ data.addressDetail }}
                            </div>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-card>
                    <v-alert
                      class="mt-4 py-1 px-3"
                      v-if="data.announce != null && data.announce !== ''"
                    >
                      <span class="ondaji_font_14_medium_brown_80"
                        ><v-icon size="20" class="mr-1"
                          >fas fa-volume-down</v-icon
                        >
                        공지사항</span
                      >
                      |
                      <span class="ondaji_font_14_thin_grey_70">{{
                        data.announce
                      }}</span>
                    </v-alert>
                  </v-expansion-panel-content>
                </v-expansion-panel>
              </v-expansion-panels>

              <v-spacer></v-spacer>
            </v-col>
          </v-row>
        </v-container>
      </v-col>

      <v-col cols="12" class="px-0">
        <v-container fluid class="bannerListConatiner py-0  px-0 align-left ">
          <v-row>
            <v-col cols="12">
              <Vuetify-searchBar @callback="searchCallback" />
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12">
              <v-tabs class="tab1" color="#3e2723">
                <v-tab
                  ><v-icon size="14" class="pr-1">fas fa-history</v-icon
                  >최신순</v-tab
                >
                <v-tab>조회순</v-tab>
                <v-tab-item class="pl-0 pr-0 pt-2 text-left">
                  <v-container fluid class="pl-2 pr-2 bannerContainer">
                    <v-row v-if="bannerList !== null && bannerList.length > 0">
                      <v-col
                        class="pa-1 bannerItem"
                        cols="6"
                        lg="3"
                        md="4"
                        sm="4"
                        v-for="(item, index) in bannerList"
                        :key="item.bannerCode"
                      >
                        <Vuetify-bannerCard
                          v-model="bannerList[index]"
                          cardType="sublist"
                        />
                      </v-col>
                    </v-row>
                    <v-row
                      v-else-if="
                        this.bannerList !== null && bannerList.length === 0
                      "
                    >
                      <v-col class="pa-1" cols="12">
                        <Table-searchTextEmpty />
                      </v-col>
                    </v-row>
                  </v-container>
                </v-tab-item>
                <v-tab-item class="pl-0 pr-0 pt-2 text-left">
                  <v-container fluid class="pl-2 pr-2 bannerContainer">
                    <v-row
                      v-if="bannerList2 !== null && bannerList2.length > 0"
                    >
                      <v-col
                        class="pa-1 bannerItem"
                        cols="6"
                        lg="3"
                        md="4"
                        sm="4"
                        v-for="(item, index) in bannerList2"
                        :key="item.bannerCode"
                      >
                        <Vuetify-bannerCard
                          v-model="bannerList2[index]"
                          cardType="sublist"
                        />
                      </v-col>
                    </v-row>
                    <v-row
                      v-else-if="
                        this.bannerList2 !== null &&
                          this.bannerList2.length === 0
                      "
                    >
                      <v-col class="pa-1" cols="12">
                        <Table-searchTextEmpty />
                      </v-col>
                    </v-row>
                  </v-container>
                </v-tab-item>
              </v-tabs>
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
  </v-container>
</template>
<style lang="scss" scoped>
.localboxinfo {
  max-width: 800px;
  border-radius: 3px;
}
.v-alert {
  background-color: #ededed !important;
  border-radius: 50px;
  height: 32px;
}
.expansion {
  max-width: 500px;
  margin-top: 20px;
  background-color: transparent;
  .v-expansion-panel-header {
    padding-left: 0px;
    padding-right: 0px;
    height: 34px;
  }
  .v-expansion-panel {
    background-color: transparent;
  }
}

.bannerListConatiner {
  max-width: 900px;

  .container {
    background-color: transparent;
  }
  .tab1 {
    max-width: 800px;
  }
}
.maincontent {
  padding-left: 0px !important;
  padding-right: 0px !important;
}
</style>
<style lang="scss">
.expansion {
  .v-expansion-panel-content__wrap {
    padding-left: 0px !important;
    padding-right: 0px !important;
  }
}
.tab1 {
  background-color: #f5f5f5 !important;
  .v-item-group {
    background-color: transparent !important;
  }
  .v-slide-group {
    background-color: transparent;
  }
  .v-slide-group {
    background-color: transparent;
  }
}
</style>

<script>
export default {
  props: {
    localboxCode: {},
  },
  created() {
    console.log("상세", this.$route.params)

    this.getData()
  },
  watch: {
    panel(val) {
      if (val.length === 0) this.isDetail = false
      else this.isDetail = true
    },
  },
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({ el: this.$el, layoutMode: "user" })
  },
  data() {
    return {
      panel: [0],
      isDetail: true,
      title: "로컬러상세",
      localboxName: "수라선 역삼점",
      data: {},
      bannerList: null,
      bannerList2: null,
    }
  },
  methods: {
    searchCallback(res) {
      let cond = {
        keywordCode: res.keywordCode,
        keywordName: res.keywordName,
        areaCode: this.data.areaCode,
        areaName: this.data.areaName,
        orderBy: "upd desc",
      }

      this.getBannerList(cond)
      let cond2 = Object.assign({}, cond)
      cond2.orderBy = "search_cnt desc"

      this.getBannerList(cond2)
    },
    expansionClick() {
      this.isDetail = !this.isDetail
    },
    getData() {
      this.$BaseCommon.restful
        .call2("/user/getlocalboxdetail", { searchCode: this.localboxCode })
        .then((res) => {
          this.data = res
          let cond = {
            areaCode: res.areaCode,
            areaName: res.areaName,
            orderBy: "upd desc",
          }
          this.getBannerList(cond)
          let cond2 = Object.assign({}, cond)
          cond2.orderBy = "search_cnt desc"
          this.getBannerList(cond2)

          console.log(res)
        })
    },
    getBannerList(cond) {
      this.$BaseCommon.restful
        .call2("/user/getuserbannerList", cond)
        .then((res) => {
          if (cond.orderBy == "upd desc") this.bannerList = [...res]
          else this.bannerList2 = [...res]
        })
        .catch((err) => {
          console.log(err)
        })
    },
    getBookmarkCnt() {
      return this.$BaseCommon.numberInfo.getNumberToKorean(
        this.data.bookmarkCnt
      )
    },
  },
}
</script>
