<template>
  <v-container ref="myChannel">
    <v-row align="start" justify="start">
      <v-col cols="12">
        <Vuetify-menutitle
          :title="$msg.getMsg('W011') /** 나의 공간 */"
          icon="fas fa-user-circle"
        />
      </v-col>
      <v-col cols="12" class="mt-0 pt-0">
        <v-container class="nomalContent mychannel">
          <v-row>
            <v-col
              cols="12"
              class="pt-2 px-4 px-sm-5 px-lg-8 pb-0 justify-start align-start"
            >
              <v-select
                dense
                flat
                style="max-width: 600px"
                color="#E7E7E7"
                class="selMyChannel d-flex d-md-none"
                bottom
                v-model="selMenu"
                :items="menuItem"
                hide-details
                item-text="text"
                item-value="value"
                outlined
                @change="menuClick"
              ></v-select>
              <v-list class="transparent pa-0">
                <v-list-item class="px-0 align-start justify-start">
                  <v-card
                    flat
                    color="transparent"
                    class="mychannelMenu d-none d-md-flex"
                    tile
                  >
                    <v-list class="transparent py-0">
                      <v-list-item-group>
                        <v-list-item>
                          <v-avatar size="48" class="pa-0">
                            <v-img
                              v-if="
                                !logininfo.isLogin ||
                                logininfo.member.profileUrl === null ||
                                logininfo.member.profileUrl === ''
                              "
                              src="@/assets/images/user/profile.svg"
                            />

                            <v-img
                              v-else
                              :src="`http://ondaji.kro.kr/files/${logininfo.member.profileUrl}`"
                            ></v-img>
                          </v-avatar>
                          <v-list-item-content
                            class="pl-2 ondaji_font_20_bold_brown_100"
                          >
                            <v-list-item-subtitle
                              class="ondaji_font_20_bold_brown_100 text-left"
                              v-text="logininfo.member.memberName"
                            >
                            </v-list-item-subtitle>

                            <v-list-item-subtitle
                              class="ondajI_font_14_medium_grey_60 text-left"
                              v-text="
                                logininfo.member.areaName === null
                                  ? '지역없음'
                                  : logininfo.member.areaName.split(' ')[1] +
                                    ' ' +
                                    logininfo.member.areaName.split(' ')[2]
                              "
                            >
                            </v-list-item-subtitle>
                          </v-list-item-content>
                        </v-list-item>
                        <v-list-item
                          v-for="(item, index) in menuItem"
                          :key="index"
                        >
                          <v-list-item-content
                            v-if="item.value === '1'"
                            :class="{ active: item.value === selMenu }"
                            @click="menuClick(item)"
                          >
                            <v-list-item-subtitle>
                              <div
                                class="bookmark ondaji_font_16_medium_tealgreen_70 ondaji_color_green_10 text-left"
                              >
                                <span
                                  :class="
                                    String(item.value) === String(selMenu)
                                      ? 'font-weight-bold'
                                      : ''
                                  "
                                  >{{ $msg.getMsg("W002") }}</span
                                >
                                <v-icon size="20" color="#069b73"
                                  >fas fa-bookmark</v-icon
                                >
                              </div>
                            </v-list-item-subtitle>
                          </v-list-item-content>
                          <v-list-item-content
                            v-else
                            :class="{ active: item.value === selMenu }"
                            @click="menuClick(item)"
                          >
                            <div
                              :class="
                                String(item.value) === String(selMenu)
                                  ? 'ondaji_font_18_bold_grey_60'
                                  : 'ondaji_font_16_medium_grey_60'
                              "
                            >
                              {{ item.text }}
                            </div>
                          </v-list-item-content>
                        </v-list-item>
                      </v-list-item-group>
                    </v-list>
                  </v-card>
                  <v-container class="bookmarklist px-0">
                    <v-row>
                      <v-col class="py-0 px-0">
                        <cpBookmark v-if="this.selMenu === '1'" />
                        <cpMyinfo v-if="this.selMenu === '2'" />
                        <cpChangePassword v-if="this.selMenu === '3'" />
                        <cpChangeBusiness v-if="this.selMenu === '4'" />
                        <cpChangeIndividual v-if="this.selMenu === '44'" />
                        <cpWithDrawMembership v-if="this.selMenu === '5'" />
                      </v-col>
                    </v-row>
                  </v-container>
                </v-list-item>
              </v-list>
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
  </v-container>
</template>
<style lang="scss">
.mychannel {
  .theme--light.v-tabs > .v-tabs-bar {
    background-color: inherit;
  }
  .v-tabs-items {
    background-color: inherit;
  }
  padding-top: 0px !important;
}

.v-list-item.primary--text {
  .v-list-item__title {
    font-weight: 900 !important;
  }
  color: inherit !important;
}
input:focus {
  background-color: transparent !important;
}
</style>
<style lang="scss" scoped>
.selMyChannel {
  font-weight: 900;
}
.bookmarklist {
  padding-top: 0px;
}

.mychannelMenu {
  width: 195px;
  margin-top: 0px;
  margin-bottom: auto;
  background-color: inherit;
  .v-list-item {
    padding-left: 8px;

    .listItem {
      padding-left: 8px;
      &.focus div {
        font-weight: 900 !important;
      }
    }
  }

  .bookmark {
    width: 147px;
    height: 38px;
    border-radius: 3px 50px 50px 3px;
    display: table-cell;
    vertical-align: middle;
    padding-left: 8px;
    padding-right: 8px;
    .v-icon {
      float: right;
      padding-right: 12px;
      color: "#069b73" !important;
    }
  }
}
</style>
<script>
export default {
  props: {
    id: {
      type: Number,
      default: 1,
    },
  },
  components: {
    cpBookmark: () => import("./mychannel/bookmark"),
    cpMyinfo: () => import("./mychannel/myinfo"),
    cpChangePassword: () => import("./mychannel/password"),
    cpChangeBusiness: () => import("./mychannel/changebusiness"),
    cpChangeIndividual: () => import("./mychannel/changeindividual"),
    cpWithDrawMembership: () => import("./mychannel/withdrawMembership"),
  },
  created() {
    this.logininfo = this.$BaseCommon.memberInfo.getMember()
    if (this.$BaseCommon.memberInfo.getMember().isLogin)
      this.isVisibleBookmark = true
    this.search()
    this.myInfoChange()

    this.selMenu = this.id
  },
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({
      el: this.$el,
      isSideBar: false,
    })
  },
  data() {
    return {
      logininfo: {},
      selMenu: "1",
      bannerList: null,
      failedImage: false,
      localboxList: null,
      isVisibleBookmark: false,

      menuItem: [
        {
          value: "1",
          text: this.$msg.getMsg("W002") /**북마크 */,
        },
        {
          value: "2",
          text: this.$msg.getMsg("W003") /**개인정보 */,
        },
        {
          value: "3",
          text: this.$msg.getMsg("W004") /**비밀번호 */,
        },
        {
          value: "5",
          text: this.$msg.getMsg("W007") /**회원 탈퇴 */,
        },
      ],
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
    cPicture: function (imageUrl) {
      return this.failedImage ? "http://ondaji.kro.kr" + imageUrl : imageUrl
    },
    changeKeywordCss(val) {
      return this.$BaseCommon.stringInfo.changeKeywordCss(val, "", 2)
    },
    onImgError: function (event) {
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
              isView: false,
              icon: "far fa-check-circle",
              title: this.$msg.getMsg("W010") /**로컬러 */,
              text: isBookmark
                ? `${
                    this.$msg.getMsg("W002") /**북마크 */
                  }가 <span style='color:#50C5A4;opacity:1'>${
                    this.$msg.getMsg("W012") /**추가 */
                  }</span>되었습니다.`
                : `${this.$msg.getMsg(
                    "W002"
                  )}가 <span style='color:#FFCB65;opacity:1'>${
                    this.$msg.getMsg("W013") /**삭제 */
                  }</span>되었습니다.`,
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
    menuClick(item) {
      //item = item === "-1" ? this.selMenu : item

      if (!isNaN(item)) {
        item = this.menuItem.find((w) => w.value === item)
      }
      this.selMenu = item.value
      this.$refs.myChannel
        .querySelectorAll(".mychannelMenu .listItem")
        .forEach((el) => {
          if (el.classList.contains("val-" + item.value)) {
            el.classList.add("focus")
          } else {
            el.classList.remove("focus")
          }
        })
    },
    myInfoChange() {
      let tmpMenu = [
        {
          value: "4",
          text: this.$msg.getMsg("W005") /**비즈니스 계정전환 */,
        },
        {
          value: "44",
          text: this.$msg.getMsg("W006") /**개인 계정전환 */,
        },
      ]
      if (this.logininfo.authType === "LocalMember") {
        let n = this.menuItem.findIndex((w) => w.value === "4")
        if (n >= 0) this.menuItem.splice(n, 1)
        if (this.menuItem.findIndex((w) => w.value === "44") < 0)
          this.menuItem.splice(n, 0, tmpMenu[1])
      }
      if (this.logininfo.authType === "Member") {
        let n2 = this.menuItem.findIndex((w) => w.value === "44")
        if (n2 >= 0) this.menuItem.splice(n2, 1)
        if (this.menuItem.findIndex((w) => w.value === "4") < 0)
          this.menuItem.splice(n2, 0, tmpMenu[0])
      }
    },
  },
}
</script>
