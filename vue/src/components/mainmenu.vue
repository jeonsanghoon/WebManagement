<template>
  <div class="mainmenu">
    <v-container>
      <v-app-bar app flat clipped-left v-if="isShowBar" color="white">
        <v-app-bar-nav-icon small class="ml-0" @click.stop="drawer = !drawer">
          <v-img src="/images/gnb/menu.svg"></v-img>
        </v-app-bar-nav-icon>
        <v-container class="pl-0">
          <v-row no-gutters>
            <v-col>
              <!--<v-img
                src="@/assets/images/logo.png"
                style="width:120px; height:40px"
                position="center"
                class="mainlogo"
                @click="moveMain"
              >
                
              </v-img>
              -->
              <v-btn
                tile
                @click="moveMain"
                width="8.5em"
                depressed
                class="logo_title ml-2 title brown lighten-5 brown--text text--darken-1"
                >ONDAJI</v-btn
              >
            </v-col>
          </v-row>
        </v-container>
        <v-spacer></v-spacer>
        <v-menu left bottom :offset-y="true">
          <template v-slot:activator="{ on }">
            <v-btn icon v-on="on" small class="pt-1">
              <v-badge
                v-if="newCount > 0"
                bordered
                bottom
                color="brown darken-1"
                :content="newCount"
                offset-x="15"
                offset-y="38"
              >
                <v-avatar size="1.8rem">
                  <v-img src="/images/gnb/alert.svg"></v-img>
                </v-avatar>
              </v-badge>
              <v-avatar v-else size="1.8rem">
                <v-img src="/images/gnb/alert.svg"></v-img>
              </v-avatar>
            </v-btn>
          </template>
          <v-list>
            <v-list-item
              v-for="(child, i) in announcelist"
              :key="i"
              @click="announceClick(child.boardCode)"
            >
              <v-icon
                small
                v-if="child.isRead"
                class="brown--text text--darken-1"
                >far fa-envelope-open</v-icon
              >
              <v-icon small v-else class="brown--text text--lighten-3"
                >far fa-envelope-open</v-icon
              >
              <span class="ml-1 body-2">{{ child.title }}</span>
            </v-list-item>
          </v-list>
        </v-menu>
        <v-menu left bottom class="pa-0" :offset-y="true">
          <template v-slot:activator="{ on }">
            <v-btn text class="pl-4" v-on="on">
              <v-avatar size="30" outlined color="brown darken-1" class="pl-0">
                <v-icon color="white">fa fa-user</v-icon>
              </v-avatar>
              <span style="font-weight:bold" class="ml-2">{{ loginName }}</span>
            </v-btn>
          </template>

          <v-list>
            <v-list-item
              v-for="(item, i) in items"
              :key="i"
              @click="myMenuClick(item)"
            >
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-app-bar>
      <v-navigation-drawer
        v-model="drawer"
        app
        clipped
        width="14rem"
        v-if="isShowBar"
      >
        <v-list dense>
          <template v-for="(item, mi) in menulist">
            <v-list-group
              v-if="item.children"
              :key="item.menuName"
              v-model="expandModels[mi]"
              @click="moveMenu(item.menuUrl, 'itemGroup' + String(mi))"
              :class="'itemGroup' + String(mi)"
            >
              <template v-slot:activator>
                <v-list-item-action class="mr-3">
                  <i v-if="item.menuIcon !== ''" :class="item.menuIcon" />
                </v-list-item-action>
                <v-list-item-title>
                  {{ item.menuName }}
                </v-list-item-title>
              </template>
              <v-list-item
                v-for="(child, i) in item.children"
                :key="i"
                @click="
                  moveMenu(
                    child.menuUrl,
                    'itemGroup' + String(mi) + '_child' + String(i)
                  )
                "
                :class="
                  'child-menu' +
                    (' itemGroup' + String(mi) + '_child' + String(i))
                "
              >
                <v-list-item-content class="ml-5">
                  <v-list-item-title>
                    - {{ child.menuName }}
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list-group>
            <v-list-item
              v-else
              :key="item.menuCode + item.menuUrl"
              :class="'item' + String(mi)"
              @click="moveMenu(item.menuUrl, 'item' + String(mi))"
            >
              <v-list-item-action class="mr-3">
                <i v-if="item.menuIcon !== ''" :class="item.menuIcon" />
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>
                  {{ item.menuName }}
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-divider
              v-if="item.isDevider"
              :key="item.menuCode * 100"
              class="mr-4 ml-4 "
            ></v-divider>
          </template>
        </v-list>

        <v-list dense>
          <br />
          <v-divider class="mr-4 ml-4"></v-divider>

          <v-list-item link to="/member/login" v-if="!isLogin">
            <v-list-item-action class="mr-3">
              <v-icon>mdi-account</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>로그인</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item link to="/register/" v-if="!isLogin">
            <v-list-item-action class="mr-3">
              <v-icon>mdi-account</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>회원등록</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>
    </v-container>
  </div>
</template>
<style scoped lang="scss">
@import "/fonts/style.css";

.v-application .primary--text {
  color: black !important;
  caret-color: black !important;
}
.v-icon.v-icon {
  width: 12px;
  height: 12px;
}

.v-icon {
  font-size: 18px;
}
.v-list {
  .v-list-item--active {
    .v-icon {
      color: #000000 !important;
      width: 12px;
      height: 12px;
    }
    .v-list-item__title {
      color: black;
      font-weight: bold;
    }
  }
}
.mainlogo {
  cursor: pointer;
}
</style>
<script>
export default {
  name: "mainmenu",
  data: () => {
    return {
      drawer: null,
      isShowBar: true,
      loginName: "",
      isLogin: false,
      items: [
        { title: "내정보", url: "/" },
        { title: "로그아웃", url: "-1" },
      ],
      myinfodisplay: "",
      menulist: [],
      announcelist: [],
      newCount: 0,
      miniVeriant: false,
      isSub: false,
      expandModels: [],
    }
  },
  created() {
    this.setLogin()
    //this.getAnnounceList();
    this.$EventBus.$on("announceChange", () => {
      this.getAnnounceList()
    })
    this.$EventBus.$on("loginAfterMenuSet", () => {
      this.setMenu(this.$BaseCommon.memberInfo.getMember().menuList)
    })
  },
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({ el: this.$el, isTop: false })
    this.$store.watch(() => {
      console.log("mounted>>>> Store 로그")
      this.isShowBar = this.$BaseCommon.settingInfo.getIsShowBar()
      let logininfo = this.$BaseCommon.memberInfo.getMember()
      this.loginName =
        this.isLogin === true ? logininfo.member.memberName : "로그인"
      logininfo = logininfo === undefined ? null : logininfo
      if (logininfo !== null && logininfo.isLogin) {
        this.myinfodisplay = logininfo.member.memberName + " 님"
        this.items[1].title = "로그아웃"
        this.isLogin = logininfo.isLogin
      } else {
        this.myinfodisplay = ""
        this.items[1].title = "로그인"
        this.isLogin = false
      }
      this.getAnnounceList()
    })
  },
  methods: {
    getAnnounceList: function() {
      let self = this
      this.$BaseCommon.restful
        .call("/board/getlist", { boardType: 1 })
        .then((res) => {
          self.announcelist = res
          self.newCount = res.filter((w) => w.isRead).length
        })
        .catch((err) => {
          console.log("에러호출 getAnnounceList", err)
        })
    },
    setLogin: function() {
      let self = this
      if (self.$BaseCommon.memberInfo.getMember().isLogin) return

      this.$BaseCommon.restful
        .call2("/member/getmyinfo")
        .then((res) => {
          if (res != null && res.isLogin) {
            this.myinfodisplay = res.member.memberName + " 님"
            this.items[1].title = "로그아웃"
          } else {
            this.items[1].title = "로그인"
          }
          this.isLogin = res.isLogin
          self.$BaseCommon.memberInfo.setMember(res)
          console.log("로그인 정보 확인 >> ", res)
          console.log("메뉴정보", self.$BaseCommon.memberInfo.getMember())
          this.setMenu(self.$BaseCommon.memberInfo.getMember().menuList)
        })
        .catch((err) => {
          this.loginName = "로그인"
          this.myinfodisplay = "로그인정보가 잘못되었습니다."
          console.log("에러호출", err)
        })
    },
    myMenuClick: function(item) {
      if (item.url !== "-1") {
        this.$BaseCommon.restful
          .call2("/member/getmyinfo")
          .then((res) => {
            this.$BaseCommon.memberInfo.setMember(res)
            let loginInfo = this.$BaseCommon.memberInfo.getMember().member

            if (loginInfo != null) {
              if (loginInfo.companyName === null) {
                this.$router.push("/register/member")
              } else {
                this.$router.push("/register/company")
              }
            }
          })
          .catch((err) => {
            console.log("myMenuClick: ", err)
          })
      }
    },
    setMenu: function(menuList) {
      this.expandModels = []
      if (menuList == null) return
      var list = new Array()
      let chkIndex = 0
      for (let i = 0; i < menuList.length; i++) {
        this.expandModels.push(i === 0 ? true : false)
        if (
          !(
            menuList[i].menuIconStyle === "" ||
            menuList[i].menuIconStyle === null
          )
        ) {
          let arr = menuList[i].menuIconStyle.split("|")

          if (arr.length == 1) menuList[i].menuIconStyle = arr[0]
          else if (arr.length == 2) {
            menuList[i].menuIconStyle = arr[0]
            menuList[i].isDevider = arr[1] === "line" ? true : false
          }
        }
        if (menuList[i].menuLevel === 1) {
          list.push(menuList[i])
        } else if (menuList[i].menuLevel === 2) {
          chkIndex = list.length - 1
          if (
            list[chkIndex].children === undefined ||
            list[chkIndex].children === null
          ) {
            list[chkIndex].children = new Array()
          }
          list[chkIndex].children.push(menuList[i])
        }
      }
      console.log("메뉴로그 >> ", list)
      this.menulist = list
    },
    moveMenu(url, menuClsID) {
      this.setMenuColor(menuClsID)
      if (!this.$BaseCommon.stringInfo.isNullEmpty(url)) this.$router.push(url)
    },
    setMenuColor(menuClsID) {
      if (document.querySelector(".v-list-item--active") !== null) {
        let arrActive = document.querySelectorAll(".v-list-item--active")
        for (let i = 0; i < arrActive.length; i++) {
          arrActive[i].classList.remove("v-list-item--active")
        }
      }

      document
        .querySelector("." + menuClsID)
        .classList.add("v-list-item--active")
      if (String(menuClsID).indexOf("_") >= 0) {
        setTimeout(() => {
          document
            .querySelector(
              "." + String(menuClsID).split("_")[0] + " .v-list-item"
            )
            .classList.add("v-list-item--active")
        }, 50)
      } else if (String(menuClsID).indexOf("Group") >= 0) {
        setTimeout(() => {
          document
            .querySelector("." + String(menuClsID) + " .v-list-item")
            .classList.add("v-list-item--active")
        }, 50)
      } else {
        for (let i = 0; i < this.menulist.length; i++) {
          this.expandModels[i] = false
        }
        this.expandModels = Object.assign([], this.expandModels)
      }
    },
    announceClick(boardCode) {
      console.log("announceClick", boardCode)
      let self = this

      if (this.$BaseCommon.hostInfo.getCurrentPath() !== "/board/register") {
        this.$router
          .push({
            name: "boardregister",
            params: { boardType: 1, boardCode: boardCode },
          })
          .catch((err) => {
            console.log(err)
            self.$EventBus.$emit("boardLoad", {
              boardType: 1,
              boardCode: boardCode,
            })
          })
      } else {
        if (this.$Config.isFormChange === true) {
          let option = {
            color: "warning",
            title: "데이터 확인",
            icon: "warning",
            buttonTrueText: "예",
            buttonFalseText: "아니오",
            isDark: false,
          }
          this.$confirm(this.$Config.msg.checkForm, option).then((res) => {
            console.log(res)
            if (res) {
              self.boardRegLoad(boardCode)
            }
          })
        } else self.boardRegLoad(boardCode)
      }
    },
    boardRegLoad(boardCode) {
      this.$Config.isFormChange = false
      this.$Config.isFormChangeCheck = false

      this.$EventBus.$emit("boardLoad", {
        boardType: 1,
        boardCode: boardCode,
      })
    },
    moveMain() {
      this.$BaseCommon.hostInfo.redirect("/", null)
    },
  },
  beforeDestroy() {
    console.log("beforeDestroy")
    this.$EventBus.$off("announceChange")
    this.$EventBus.$off("loginAfterMenuSet")
  },
  computed: {
    mini() {
      let bMini = false
      if (
        this.$vuetify.breakpoint.mdAndDown &&
        !this.$vuetify.breakpoint.smAndDown
      )
        bMini = true

      return bMini
    },
    permanent() {
      return !this.$vuetify.breakpoint.smAndDown
    },
  },
}
</script>
