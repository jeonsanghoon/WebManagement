<template lang="pug">
  div(class="mainmenu")
    v-container
      v-app-bar(app flat clipped-left v-if="isShowBar" color="white")
        v-app-bar-nav-icon(small class="ml-0" @click.stop="drawer = !drawer")
          v-img(src="/images/gnb/menu.svg")
        v-container(class="pl-0")
          v-row(no-gutters class="text-left")
            v-col
              v-btn(tile @click="moveMain" depressed class="logoTitle ml-2 title brown lighten-5 brown--text text--darken-1") ONDAJI
        v-spacer
        v-menu(left bottom :offset-y="true")
          template(v-slot:activator="{ on }")
            v-btn(icon v-on="on" small class="pt-1")
              v-badge(v-if="newCount > 0" bordered bottom color="brown darken-1" :content="newCount" offset-x="15" offset-y="38" )
                v-avatar(size="1.8rem")
                  v-img(src="/images/gnb/alert.svg")
              v-avatar(v-else size="1.8rem")
                v-img(src="/images/gnb/alert.svg")
          v-list
            v-list-item(v-for="(child, i) in announcelist" :key="i" @click="announceClick(child.boardCode)")
              v-icon(small v-if="child.isRead" class="brown--text text--darken-1") far fa-envelope-open
              v-icon(small v-else class="brown--text text--lighten-3") far fa-envelope-open
              span(class="ml-1 body-2") {{ child.title }}
        v-menu(left bottom class="pa-0" :offset-y="true")
          template(v-slot:activator="{ on }")
            v-btn(text class="pl-4" v-on="on")
              v-avatar(size="30" outlined color="brown darken-1" class="pl-0")
                v-icon(color="white") fa fa-user
              span(style="font-weight: bold" class="ml-2") {{loginName}}
          v-list
            v-list-item(class="menuitem" @click="myMenuClick({ url: '/' })")
              v-list-item-title(v-text="'사용자페이지'")
            v-divider
            v-list-item(v-for="(item, i) in items" :key="i" @click="myMenuClick(item)")
              v-list-item-title {{ item.title }}
      v-navigation-drawer(v-model="drawer" app clipped width="14rem" v-if="isShowBar && isSideBar")
        v-list(dense)
          template(v-for="(item, mi) in menulist")
            v-list-group(v-if="item.children" :key="item.menuName" v-model="expandModels[mi]" @click="moveMenu(item.menuUrl, 'itemGroup' + String(mi))" :class="'itemGroup' + String(mi)")
              template(v-slot:activator)
                v-list-item-action(class="mr-3")
                  em(v-if="item.menuIcon !== ''" :class="item.menuIcon")
                v-list-item-title {{ item.menuName }}
              v-list-item(v-for="(child, i) in item.children" :key="i" @click="moveMenu(child.menuUrl,'itemGroup' + String(mi) + '_child' + String(i))"
                          :class="'child-menu' + (' itemGroup' + String(mi) + '_child' + String(i))")
                v-list-item-content(class="ml-5")
                  v-list-item-title - {{ child.menuName }}
            v-list-item(v-else :key="item.menuCode + item.menuUrl" :class="'item' + String(mi)" @click="moveMenu(item.menuUrl, 'item' + String(mi))")
              v-list-item-action(class="mr-3")
                i(v-if="item.menuIcon !== ''" :class="item.menuIcon")
              v-list-item-content
                v-list-item-title {{ item.menuName }}
            v-divider(v-if="item.isDevider" :key="item.menuCode * 100" class="mr-4 ml-4")
        v-list(dense)
          br 
          v-divider(class="mr-4 ml-4")
          v-list-item(link @click="$BaseCommon.memberInfo.moveLogin('local')" v-if="!isLogin")
            v-list-item-action(class="mr-3")
              v-icon(mdi-account)
            v-list-item-content
              v-list-item-title 로그인
          v-list-item(link to="/register/" v-if="!isLogin")
            v-list-item-action(class="mr-3")
              v-icon mdi-account
            v-list-item-content
              v-list-item-title 회원등록
</template>
<style lang="scss">
.menuitem {
  cursor: pointer !important;
}
</style>
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
.logoTitle {
  max-width: "8.5em";
  width: "100%";
}
</style>
<script>
export default {
  name: "mainmenu",
  data: () => {
    return {
      drawer: null,
      isShowBar: true,
      isSideBar: false,
      loginName: "",
      isLogin: false,
      items: [
        { title: "나의공간", url: "/channel/1" },
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
      setTimeout(() => {
        console.log("loginAfterMenuSet >>>>>> ")
        if (this.menulist.length === 0)
          this.setMenu(this.$BaseCommon.memberInfo.getMember().menuList)
      }, 200)
    })
  },
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({ el: this.$el, isTop: false })
    this.$store.watch(() => {
      this.isSideBar = this.$BaseCommon.settingInfo.getIsSideBar()
      this.isShowBar = this.$BaseCommon.settingInfo.getIsShowBar()

      let logininfo = this.$BaseCommon.memberInfo.getMember()

      console.log("mounted>>>> Store 로그", this.isShowBar, logininfo)
      this.loginName =
        this.isLogin === true
          ? this.$BaseCommon.stringInfo.isNullEmpty(logininfo.member.nickname)
            ? logininfo.member.memberName
            : logininfo.member.nickname
          : "로그인"
      logininfo = logininfo === undefined ? null : logininfo
      if (logininfo !== null && logininfo.isLogin) {
        this.myinfodisplay =
          (this.$BaseCommon.stringInfo.isNullEmpty(logininfo.member.nickname)
            ? logininfo.member.memberName
            : logininfo.member.nickname) + " 님"
        this.items[1].title = "로그아웃"
        this.isLogin = logininfo.isLogin
      } else {
        this.myinfodisplay = ""
        this.items[1].title = "로그인"
        this.isLogin = false
      }
      if (this.announcelist.length === 0) this.getAnnounceList()

      if (this.menulist.length === 0)
        this.setMenu(this.$BaseCommon.memberInfo.getMember().menuList)
    })
  },
  methods: {
    getAnnounceList: function () {
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
    setLogin: function () {
      let self = this
      if (self.$BaseCommon.memberInfo.getMember().isLogin) return

      this.$BaseCommon.restful
        .call("/member/getmyinfo")
        .then((res) => {
          if (res != null && res.isLogin) {
            this.myinfodisplay = res.member.memberName + " 님"
            this.items[1].title = "로그아웃"
            this.isLogin = res.isLogin
            self.$BaseCommon.memberInfo.setMember(res)
            console.log("로그인 정보 확인 >> ", res)
          } else {
            this.items[1].title = "로그인"
          }

          if (this.menulist.length === 0)
            this.setMenu(self.$BaseCommon.memberInfo.getMember().menuList)
        })
        .catch((err) => {
          this.loginName = "로그인"
          this.myinfodisplay = "로그인정보가 잘못되었습니다."
          console.log("에러호출", err)
        })
    },
    myMenuClick: function (item) {
      if (item.url === "-1") {
        console.log("로그아웃")
        this.$BaseCommon.restful
          .call("/member/logout")
          .then((res) => {
            this.$BaseCommon.memberInfo.setMember(res)
            console.log(res)
          })
          .catch((err) => {
            console.log(err)
          })
        this.$router.push("/member/login")
      } else {
        this.$router.push(item.url)
      }
    },
    setMenu: function (menuList) {
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
          if (list[chkIndex].children === undefined) {
            list[chkIndex].children = new Array()
          }
          if (
            list[chkIndex].children.filter(
              (w) => w.menuCode === menuList[i].menuCode
            ).length === 0
          )
            list[chkIndex].children.push(menuList[i])
        }
      }
      console.log("메뉴로그 >> ", menuList, list)
      this.menulist = Object.assign({}, [])

      this.menulist = Object.assign({}, list)
    },
    moveMenu(url, menuClsID) {
      this.setMenuColor(menuClsID)
      if (this.$router.currentRoute.path != url) {
        if (!this.$BaseCommon.stringInfo.isNullEmpty(url))
          this.$router.push(url)
      }
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
      this.$BaseCommon.hostInfo.redirect("/local", null)
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
