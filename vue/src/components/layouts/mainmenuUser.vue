<template>
  <div class="mainmenu">
    <v-container>
      <v-app-bar
        app
        flat
        clipped-right
        v-if="isShowBar"
        color="white"
        class="userAppbar"
      >
        <v-toolbar-title class="pl-2 mr-12 align-center ">
          <v-btn
            tile
            @click="moveMain"
            depressed
            class="logo_title ml-2 title brown lighten-5 brown--text text--darken-1 px-sm-4 px-md-10"
            >User</v-btn
          >
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <Vuetify-searchBar
          class="d-none d-sm-flex "
          @callback="searchCallback"
        />
        <Vuetify-searchBar2
          class="d-flex d-sm-none"
          @callback="searchCallback"
        />
        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              class="pb-1 btnToManage"
              icon
              v-bind="attrs"
              v-on="on"
              @click="
                () => {
                  $router.push('local')
                }
              "
            >
              <v-img
                max-width="40"
                max-height="40"
                src="@/assets/images/user/To.svg"
              ></v-img>
            </v-btn>
          </template>
          <span>작업실 가기</span>
        </v-tooltip>
        <v-badge
          avatar
          dot
          overlap
          v-model="isAlam"
          color="ondaji_color_tealgreen_40 btnMyUser"
        >
          <v-btn icon class="mr-0" @click="userClick">
            <v-avatar size="28" color="#3e2723">
              <v-icon
                v-if="
                  !logininfo.isLogin || logininfo.member.profileUrl === null
                "
                size="24"
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
        </v-badge>
      </v-app-bar>
      <User-sidebar v-model="isUserMenu" />
    </v-container>
  </div>
</template>
<style lang="scss">
.btnMyUser {
  bottom: calc(100% - 18px) !important;
  left: calc(100% - 18px) !important;
}

@include mobile {
  .userAppbar .v-toolbar__content,
  .userAppbar .v-toolbar__extension {
    padding: 0px !important;
  }
}
</style>
<style scoped lang="scss">
.v-navigation-drawer {
  border-left: 1px solid #eaeaea;
}
.btnToManage {
  margin-left: 10px;
}

@include mobile {
  .btnToManage {
    margin-left: 0px;
    margin-right: 0px;
  }
}
.far.fa-user-circle {
  color: #ffffff;
  opacity: 0.4;
}
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
      isMyinfoDrawer: true,
      isShowBar: true,

      loginName: "",
      isLogin: false,
      myinfodisplay: "",
      menulist: [],
      announcelist: [],
      newCount: 0,
      miniVeriant: false,
      isSub: false,
      expandModels: [],
      isAlam: true,

      logininfo: {
        isLogin: false,
        member: {},
      },
      isUserMenu: false,
    }
  },
  created() {
    this.logininfo = this.$BaseCommon.memberInfo.getMember()
    console.log("로그인로그>>>>>>>>>>>>", this.logininfo)
    // this.setLogin()
    //this.getAnnounceList();
    this.$EventBus.$on("announceChange", () => {})

    /* 리플레시전 확인 메세지 */
    this.$EventBus.$on("isAlam", (_isalam) => {
      this.isAlam = _isalam
    })

    this.$EventBus.$on("setShowBar", (val) => {
      this.isShowBar = val
    })
  },
  beforeDestroy() {
    console.log("announceChange")
    this.$EventBus.$off("isAlam")
    this.$EventBus.$off("setShowBar")
  },
  methods: {
    setLogin: function() {
      let self = this
      if (self.menulist.length > 0) return

      this.$BaseCommon.restful
        .call2("/member/getmyinfo")
        .then((res) => {
          self.$BaseCommon.memberInfo.setMember(res)
          console.log("로그인 정보 확인 >> ", res)
          console.log("메뉴정보", self.$BaseCommon.memberInfo.getMember())
          this.setMenu(self.$BaseCommon.memberInfo.getMember().userMenuList)
        })
        .catch((err) => {
          this.loginName = "로그인"
          this.myinfodisplay = "로그인정보가 잘못되었습니다."
          console.log("에러호출", err)
        })
    },
    myMenuClick: function(item) {
      if (item.url === "-1") {
        console.log("로그아웃")
      } else {
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
          this.$alert(this.$Config.msg.checkForm, {
            alertType: "confirm",
          }).then((res) => {
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
    search(res) {
      this.$EventBus.$emit("search", res)
    },
    searchCallback(res) {
      console.log("searchCallback >>> ", res)
      if (this.$BaseCommon.hostInfo.getCurrentName() === "usersearch") {
        this.$EventBus.$emit("keywordSearch", res)
      } else this.$router.push({ name: "usersearch", params: res })
    },
    userClick() {
      this.isUserMenu = !this.isUserMenu
    },
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
