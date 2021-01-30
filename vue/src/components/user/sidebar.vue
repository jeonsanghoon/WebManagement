<template>
  <v-navigation-drawer
    v-model="isShow"
    app
    clipped
    right
    width="320px"
    v-scroll:#scroll-target="onScroll"
    class="elevation-1"
  >
    <div class="dvNavAlam">
      <v-list class="px-3 py-0 white">
        <v-list-item class="px-0">
          <v-btn small icon class="d-flex d-sm-none  ml-2">
            <v-avatar size="40" color="#3E2723">
              <v-icon
                v-if="
                  !logininfo.isLogin ||
                    logininfo.member.profileUrl === null ||
                    logininfo.member.profileUrl === ''
                "
                size="28"
                >far fa-user-circle</v-icon
              >
              <v-img
                v-else
                :src="
                  `http://ondaji.kro.kr/files/${logininfo.member.profileUrl}`
                "
              ></v-img>
            </v-avatar>
          </v-btn>

          <v-list-item-content class="pl-sm-0 pl-3">
            <v-list-item-title class="ondaji_font_16_bold_grey_100 ">
              <v-btn
                @click="logout"
                height="20px"
                min-width="60px"
                text-left
                text
                v-if="!this.logininfo.isLogin"
                class="ondaji_font_16_bold_grey_100 px-0"
                >로그인<v-icon class="ml-0" right size="16"
                  >fas fa-chevron-right</v-icon
                ></v-btn
              >
              <v-btn
                @click="moveMyInfo"
                height="20px"
                min-width="60px"
                text
                v-else
                class="ondaji_font_16_bold_grey_100  px-0"
                >{{ this.logininfo.member.nickname
                }}<v-icon class="ml-1 mt-1" right size="14"
                  >fas fa-chevron-right</v-icon
                ></v-btn
              >
            </v-list-item-title>
            <v-list-item-subtitle class="ondaji_font_12_medium_grey_60">
              {{
                !this.logininfo.isLogin ? "" : this.logininfo.member.member_id
              }}
            </v-list-item-subtitle>
          </v-list-item-content>

          <v-btn
            rounded
            small
            outlined
            color="#50C5A4"
            class="disBookmark"
            @click="myBookmarkClick"
          >
            <v-icon size="12" class="mr-1">fas fa-bookmark</v-icon>
            <span style="font-size:12px;">북마크</span>
          </v-btn>
        </v-list-item>
      </v-list>
      <v-divider></v-divider>

      <div class="listData">
        <v-list class="px-3">
          <v-badge
            color="ondaji_color_tealgreen_40"
            class="ondaji_font_12_medium_grey_60 ondajialam"
            dot
            bordered
            v-model="isAlam"
          >
            알림
          </v-badge>
          <v-list-item v-if="list.lingth === 0" class="px-1">
            알림정보가 없습니다.
          </v-list-item>
          <v-list-item
            class="px-0 pb-0 list_pt-10"
            v-for="(item, index) in list"
            :key="index"
          >
            <v-list-item-content class="py-0" @click="itemClick(item)">
              <v-container class="pa-0">
                <v-row>
                  <v-col cols="3" class="py-0">
                    <div
                      class="ondaji_chip ondaji_font_12_medium_grey_60 ondaji_color_grey_20"
                      label
                    >
                      <div v-if="item.alamType === 1">배너공유</div>
                      <div v-else>북마크</div>
                    </div>
                  </v-col>
                  <v-col cols="9" class="pl-1 py-0">
                    <v-list-item-subtitle
                      class="ondaji_font_13_medium_grey_100 textWrap_sub"
                      v-html="item.title"
                    >
                    </v-list-item-subtitle>
                    <v-list-item-subtitle
                      class="ondaji_font_12_medium_grey_60 list_pt-5"
                    >
                      <span v-html="item.content"></span
                      ><span style="float:right" v-html="item.regDate2"></span>
                    </v-list-item-subtitle>
                  </v-col>
                </v-row>
              </v-container>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </div>
    </div>
    <template v-slot:append>
      <div class="px-2 text-right">
        <v-btn
          v-if="logininfo.isLogin"
          text
          class="ondaji_font_14_medium_grey_60 px-1"
          @click="logout"
        >
          <v-icon size="16" class="pr-2">fas fa-sign-out-alt</v-icon>로그아웃
        </v-btn>
        <v-btn
          v-else
          text
          class="ondaji_font_14_medium_grey_60 px-1"
          @click="logout"
        >
          <v-icon size="16" class="pr-2">fas fa-sign-in-alt</v-icon>로그인
        </v-btn>
      </div>
    </template>
  </v-navigation-drawer>
</template>
<style>
.disBookmark {
  border-width: 2px;
}
.ondajialam .v-badge__badge {
  width: 5px !important;
  height: 5px !important;
}
</style>

<style scoped lang="scss">
.far.fa-user-circle {
  color: #ffffff;
  opacity: 0.4;
}
.textWrap_sub {
  -webkit-line-clamp: 2;
  white-space: normal !important;
}
.dvNavAlam {
  background-color: rgba($color: #f2f2f2, $alpha: 0.3) !important;
  height: 100%;
}
.list_pt-10 {
  padding-top: 10px;
  padding-bottom: 0px;
}
.list_pt-5 {
  padding-top: 5px;
}
.ondaji_chip {
  text-align: center;
  width: 56px;
  height: 20px;
  margin-right: 8px;
  display: flex;
  justify-content: center;
  div {
    padding-top: 4px;
  }
}

.v-badge__badge {
  width: 5px !important;
  height: 5px !important;
}
</style>
<script>
export default {
  props: {
    value: {
      default: false,
    },
  },
  created() {
    this.logininfo = this.$BaseCommon.memberInfo.getMember()
    this.getAlamMsgList(1)
  },
  watch: {
    value(val) {
      this.isShow = val

      if (this.isShow) this.getAlamMsgList(1)
      this.$emit("input", val)
      this.logininfo = Object.assign(
        {},
        this.$BaseCommon.memberInfo.getMember()
      )
    },
  },
  data() {
    return {
      logininfo: { member: { profileUrl: null } },
      isShow: false,
      isAlam: false,
      page: 1,
      pageSze: 20,
      list: [],
      isLast: false,
    }
  },
  methods: {
    getAlamMsgList(_page) {
      this.page = _page
      let cond = { page: _page - 1, pageSize: this.pageSze, member_code: -1 }
      this.$BaseCommon.restful
        .call2("/usercommon/getalammsglist", cond)
        .then((res) => {
          this.list = [...res]
          console.log("/usercommon/getalammsglist", this.list)
          if (this.list == 0) {
            this.isLast = true
          }
          if (this.page > 1 || this.list.length > 0) {
            this.$EventBus.$emit("isAlam", true)
            this.isAlam = true
          } else {
            this.$EventBus.$emit("isAlam", false)
            this.isAlam = false
          }
        })
        .catch((err) => {
          console.log("getalammsglist: ", err)
        })
    },
    itemClick(item) {
      if (item.alamType === 1) {
        this.$alert("공유요청서 페이지로 이동합니다. 코드(" + item.code + ")", {
          title: "개발중",
        })
      } else if (item.alamType === 2) {
        this.$alert("통계-배너 페이지로 이동합니다. 코드(" + item.code + ")", {
          title: "미개발",
        })
      } else if (item.alamType === 3) {
        this.$alert(
          "통계-로컬박스 페이지로 이동합니다. 코드(" + item.code + ")",
          { title: "미개발" }
        )
      } else if (item.alamType === 9) {
        this.$alert("로컬박스 페이지로 이동합니다. 코드(" + item.code + ")", {
          title: "미개발",
        })
      }
    },
    onScroll(e) {
      if (e.target.scrollHeight === e.target.scrollTop + e.target.offsetHeight)
        this.getAlamMsgList(this.page + 1)
    },
    logout() {
      this.$BaseCommon.memberInfo.moveLogin()
    },
    moveMyInfo() {
      this.$alert("나의 공간 개인 정보 수정 페이지로 이동합니다").then(() => {
        this.$router.push("/channel/2")
      })
    },
    myBookmarkClick() {
      this.$alert("나의 공간 북마크 페이지로 이동합니다").then(() => {
        this.$router.push("/channel/1")
      })
    },
  },
}
</script>
