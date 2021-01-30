import Vue from "vue"
import VueRouter from "vue-router"
import Home from "../views/Home.vue"
import { BaseCommon } from "@/assets/scripts/Common"

import { Config } from "../assets/scripts/Config"
import { EventBus } from "../assets/scripts/EventBus"

Vue.use(VueRouter)

const routes = [
  {
    path: "/local",
    name: "local",
    beforeRouteEnter: (to, from, next) => {
      console.log("beforeRouteEnter")
      console.log(to)
      console.log(from)
      console.log(next)
      this.$store.dispatch("baseStore/setIsShowBar", true)
    },
    component: Home,
  },
  {
    path: "/",
    name: "userhome",
    component: () => import("../views/User.vue"),
  },
  {
    path: "/user/search",
    name: "usersearch",
    component: () => import("../views/user/search.vue"),
  },
  {
    path: "/user/bannerdetail/:bannerCode",
    name: "userbannerdetail",
    props: true,
    component: () => import("../views/user/bannerDetail.vue"),
  },
  {
    path: "/user/localboxdetail/:localboxCode",
    name: "userlocalboxdetail",
    props: true,
    component: () => import("../views/user/localboxDetail.vue"),
  },
  {
    path: "/member/about",
    name: "about",
    beforeRouteEnter: (to, from, next) => {
      console.log(to)
      console.log(from)
      console.log(next)
      console.log("beforeRouteEnter")
      this.$store.dispatch("baseStore/setIsShowBar", false)
    },
    component: () => import("../views/About.vue"),
  },
  {
    path: "/register/",
    name: "register",
    beforeRouteEnter: (to, from, next) => {
      console.log(to)
      console.log(from)
      console.log(next)
      console.log("beforeRouteEnter")
      this.$store.dispatch("baseStore/setIsShowBar", false)
    },
    component: () => import("../views/register/register.vue"),
  },
  {
    path: "/register/member",
    name: "registermember",
    beforeRouteEnter: (to, from, next) => {
      console.log(to, from, next)
      console.log("beforeRouteEnter")
      this.$store.dispatch("baseStore/setIsShowBar", false)
    },
    component: () => import("../views/register/member/registerMember.vue"),
  },
  {
    path: "/register/company",
    name: "registercompnay",
    beforeRouteEnter: (to, from, next) => {
      console.log(to)
      console.log(from)
      console.log(next)
      console.log("beforeRouteEnter")
      this.$store.dispatch("baseStore/setIsShowBar", false)
    },
    component: () => import("../views/register/company/registerCompany.vue"),
  },
  {
    path: "/member/modify",
    name: "membermod",
    beforeRouteEnter: (to, from, next) => {
      console.log(to)
      console.log(from)
      console.log(next)
      console.log("beforeRouteEnter")
      this.$store.dispatch("baseStore/setIsShowBar", false)
    },
    component: () => import("../views/member/modify.vue"),
  },
  {
    path: "/member/list",
    name: "memberlist",
    beforeRouteEnter: (to, from, next) => {
      console.log(to)
      console.log(from)
      console.log(next)
      console.log("beforeRouteEnter")
      this.$store.dispatch("baseStore/setIsShowBar", false)
    },
    component: () => import("../views/member/list.vue"),
  },
  {
    path: "/member/register2",
    name: "등록",
    beforeRouteEnter: (to, from, next) => {
      console.log(to)
      console.log(from)
      console.log(next)
      console.log("beforeRouteEnter")
      this.$store.dispatch("baseStore/setIsShowBar", false)
    },

    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import("../views/member/register2.vue"),
  },

  {
    path: "/member/login",
    name: "login",
    beforeRouteEnter: () => {
      this.$store.dispatch("baseStore/setIsShowBar", false)
    },
    component: () => import("../views/member/login.vue"),
  },
  {
    path: "/logout",
    name: "logout",
    component: () => import("../views/member/logout.vue"),
  },
  {
    path: "/banner/reg",
    name: "registerbanner",
    beforeRouteEnter: (to, from, next) => {
      console.log(to)
      console.log(from)
      console.log(next)
      console.log("beforeRouteEnter")
      this.$store.dispatch("baseStore/setIsShowBar", false)
    },
    component: () => import("../views/banner/BannerReg/main.vue"),
  },
  {
    path: "/banner/store",
    name: "storebanner",
    beforeRouteEnter: (to, from, next) => {
      console.log(to)
      console.log(from)
      console.log(next)
      console.log("beforeRouteEnter")
      this.$store.dispatch("baseStore/setIsShowBar", false)
    },
    component: () => import("../views/banner/bannerStore/main.vue"),
  },
  {
    path: "/banner/sharelist",
    name: "storebannersharelist",
    beforeRouteEnter: (to, from, next) => {
      console.log(to)
      console.log(from)
      console.log(next)
      console.log("beforeRouteEnter")
      this.$store.dispatch("baseStore/setIsShowBar", false)
    },
    component: () => import("../views/banner/bannerShareList/main.vue"),
  },
  {
    path: "/mediashop",
    name: "localbox",
    beforeRouteEnter: (to, from, next) => {
      console.log(to)
      console.log(from)
      console.log(next)
      console.log("beforeRouteEnter")
      this.$store.dispatch("localbox/setIsShowBar", false)
    },
    component: () => import("../views/mediaShop/make/mediaShopMain.vue"),
  },
  {
    path: "/mediashop/localbox",
    name: "localboxbanner",
    beforeRouteEnter: (to, from, next) => {
      console.log(to)
      console.log(from)
      console.log(next)
      console.log("beforeRouteEnter")
      this.$store.dispatch("localbox/setIsShowBar", false)
    },
    component: () =>
      import("../views/mediaShop/localBox/edit/localBoxEditMain.vue"),
  },
  {
    path: "/mediashop/localbox/sharereq",
    name: "localboxbannershare",
    beforeRouteEnter: (to, from, next) => {
      console.log(to)
      console.log(from)
      console.log(next)
      console.log("beforeRouteEnter")
      this.$store.dispatch("localbox/setIsShowBar", false)
    },
    component: () =>
      import("../views/mediaShop/localBox/shareReq/shareReqMain.vue"),
  },
  {
    path: "/temp/video",
    name: "video",
    component: () => import("../views/temp/video.vue"),
  },
  {
    path: "/temp/temp01",
    name: "temp01",
    component: () => import("../views/temp/templeate01.vue"),
  },
  {
    path: "/temp/temp02",
    name: "temp02",
    component: () => import("../views/temp/temp02.vue"),
  },
  {
    path: "/signage/setting",
    name: "signagesetting",
    component: () => import("../views/signage/setting.vue"),
  },
  {
    path: "/signage/list",
    name: "signagelist",
    component: () => import("../views/signage/list.vue"),
  },
  {
    path: "/signage/banner",
    name: "signageBanner",
    props: true,
    component: () => import("../views/signage/banner.vue"),
  },
  {
    path: "/channel/:id",
    name: "myChannel",
    props: true,
    component: () => import("../views/channel/mychannel.vue"),
  },
]
//BaseCommon.rest
routes.push({
  path: "/temp/daummap",
  name: "daummap",
  component: () => import("../views/temp/daummap.vue"),
})

routes.push({
  path: "/temp/daummap2",
  name: "daummap2",
  component: () => import("../views/temp/daummap2.vue"),
})

routes.push({
  path: "/temp/buttons",
  name: "buttons",
  component: () => import("../views/temp/buttons.vue"),
})

routes.push({
  path: "/temp/buttons2",
  name: "buttons2",
  component: () => import("../views/temp/buttons2.vue"),
})

routes.push({
  path: "/temp/kakaoPostCode",
  name: "kakaoPostCode",
  component: () => import("../views/temp/kakaoPostCode.vue"),
})

routes.push({
  path: "/temp/temp03",
  name: "htmleditor",
  component: () => import("../views/temp/temp03.vue"),
})

routes.push({
  path: "/temp/temp04",
  name: "dataTableDrag",
  component: () => import("../views/temp/temp04.vue"),
})

routes.push({
  path: "/temp/bannerdrag",
  name: "bannerdrag",
  component: () => import("../views/temp/bannerDrag.vue"),
})

routes.push({
  path: "/temp/bannerdrag2",
  name: "bannerdrag2",
  component: () => import("../views/temp/bannerDrag2.vue"),
})

routes.push({
  path: "/temp/toastUI",
  name: "tostui",
  component: () => import("../views/temp/toastUI.vue"),
})

routes.push({
  path: "/temp/tabledesc",
  name: "tabledesc",
  component: () => import("../views/temp/tableDesc.vue"),
})

routes.push({
  path: "/temp/fileupload",
  name: "fileupload",
  component: () => import("../views/temp/fileupload.vue"),
})

routes.push({
  path: "/temp/autocomplete",
  name: "autocomplete",
  component: () => import("../components/vuetify/autoComplete.vue"),
})

routes.push({
  path: "/temp/register",
  name: "register2",
  component: () => import("../views/member/register.vue"),
})

routes.push({
  path: "/common/commonreg",
  name: "commonreg",
  component: () => import("../views/common/commonReg.vue"),
})

routes.push({
  path: "/menu/menureg",
  name: "menureg",
  component: () => import("../views/menu/menuReg.vue"),
})

routes.push({
  path: "/board/list",
  name: "boardlist",
  component: () => import("../views/board/list.vue"),
})

routes.push({
  path: "/board/register",
  name: "boardregister",
  component: () => import("../views/board/register.vue"),
  beforeRouterUpdate: (to, from, next) => {
    next()
  },
})

routes.push({
  path: "/temp/videopreview",
  name: "videoPreview",
  component: () => import("../views/temp/videoPreview.vue"),
})

routes.push({
  path: "/temp/bannerupload",
  name: "bannerupload",
  component: () => import("../views/temp/bannerUpload.vue"),
})

routes.push({
  path: "/temp/bannerupload2",
  name: "bannerupload2",
  component: () => import("../views/temp/bannerUpload2.vue"),
})

routes.push({
  path: "/statistics/loglist",
  name: "loglist",
  component: () => import("../views/statistics/loglist.vue"),
})

routes.push({
  path: "/member/findid",
  name: "findId",
  component: () => import("../views/member/findId.vue"),
})

routes.push({
  path: "/member/findpw",
  name: "findpw",
  component: () => import("../views/member/findPw.vue"),
})

routes.push({
  path: "/search",
  beforeRouteEnter: (to, from, next) => {
    console.log(to)
    console.log(from)
    console.log(next)
    this.$store.dispatch("baseStore/setIsShowBar", false)
  },
  component: () => import("../views/common/search.vue"),
})

routes.push({
  path: "/error",
  beforeRouteEnter: (to, from, next) => {
    console.log(to)
    console.log(from)
    console.log(next)
    this.$store.dispatch("baseStore/setIsShowBar", false)
  },
  component: () => import("../views/error/error.vue"),
})

routes.push({
  path: "/temp/font",
  name: "font",
  component: () => import("../views/temp/font.vue"),
})
routes.push({
  path: "/temp/date",
  name: "font",
  component: () => import("../views/temp/date.vue"),
})
routes.push({
  path: "/temp/colors",
  name: "color",
  component: () => import("../views/temp/colors.vue"),
})

routes.push({
  path: "/temp/bannerlist",
  name: "tempbannerlist",
  component: () => import("../views/temp/bannerList.vue"),
})

const router = new VueRouter({
  mode: "history",
  routes,
})

router.beforeEach((to, from, next) => {
  console.log("router.beforeEach", to, from, next)

  if (to.matched.length < 1) {
    // alert("페이지없음")
    next("/error")
    return
  }
  if (
    to.path === "/assets/" ||
    to.path === "/member/login" ||
    to.path === "/" ||
    to.path === "/error" ||
    to.path.indexOf("/temp/") >= 0 ||
    to.path.indexOf("/files/") >= 0 ||
    to.path.indexOf("/member/") >= 0 ||
    to.path.indexOf("/keyword/") >= 0 ||
    to.path.indexOf("/user") >= 0 ||
    to.path.indexOf("/register/") >= 0
  ) {
    next()

    return
  }

  let menu = BaseCommon.menuInfo.getMenu(to.path)
  BaseCommon.restful
    .call("/mongodb/insertLog", {
      pageName: menu.menuName,
      pageUrl: to.path,
      deviceType: BaseCommon.hostInfo.isMobile.any() ? "Moblie" : "PC",
    })
    .then((res) => {
      console.log(res)
    })
    .catch((e) => {
      console.log("/mongodb/insertLog error", e)
    })
  if (!BaseCommon.memberInfo.getMember().isLogin) {
    BaseCommon.restful
      .call("/member/getmyinfo")
      .then((res) => {
        //this.isLogin = res.isLogin;
        BaseCommon.memberInfo.setMember(res)
        console.log("로그인 정보 확인 >> ", res)
        console.log("메뉴정보", BaseCommon.memberInfo.getMember())

        if (res != null && res.isLogin) {
          next()
          return
        } else {
          if (to.name === "local") {
            next({
              name: "login",
              params: { loginMode: "local" },
            })
          } else {
            next("/member/login")
          }
        }
      })
      .catch((err) => {
        console.log("에러호출>>>>>", err)
        next("/member/login")
      })
  } else {
    next()
  }
  //next()
  /*if (!BaseCommon.memberInfo.getMember().isLogin) {
        if (to.path !== "/member/login")
            next("/member/login")
        else
            next()
    } else { next() }
*/
})
router.afterEach((to, from) => {
  console.log("afterEach>>", to, from)
})
router.beforeResolve((to, from, next) => {
  if (Config.isFormChange === true) {
    EventBus.$emit("confirmMessage", Config.msg.checkForm, next)
  } else next()
})

export default router
