import axios from "axios"
import store from "@/store/store"
import router from "@/router/index.js"
import { EventBus } from "@/assets/scripts/EventBus"
import { Config } from "@/assets/scripts/Config"

/** 공통함수 */
export const BaseCommon = {
  /** restful */
  restful: {
    /** 호출함수 */
    call: async (
      url,
      objParam = null,
      method = "post",
      bLoading = true,
      headers = {
        "Content-type": "application/json",
        "Access-Control-Allow-Origin": window.location.origin,
        "Access-Control-Allow-Credentials": true,
        withCredentials: true,
      }
    ) => {
      if (bLoading) store.dispatch("baseStore/setIsLoading", true)
      let rtn
      try {
        let res
        method === "post"
          ? (res = await axios.post(url, objParam, {
              headers,
            }))
          : (res = await axios.get(url, objParam, {
              headers,
            }))
        rtn = await res.data
      } catch (e) {
        console.log(e)
        throw e
      } finally {
        if (bLoading) store.dispatch("baseStore/setIsLoading", false)
      }
      return rtn
    },
    call2: async (url, objParam = null, method = "post") => {
      return BaseCommon.restful.call(url, objParam, method, false)
    },

    imageDataUpload: async (imageData) => {
      let arrList = []
      if (!Array.isArray(imageData)) {
        arrList.push(imageData)
      } else {
        arrList = [...imageData]
      }

      //let saveList = []
      var formData = new FormData() // formData 생성
      arrList.forEach((data, idx) => {
        var blobBin = atob(data) // base64 데이터 디코딩
        var array = []
        for (var i = 0; i < blobBin.length; i++) {
          array.push(blobBin.charCodeAt(i))
        }
        let file = new Blob([new Uint8Array(array)], {
          type: "image/png",
        }) // Blob 생성

        const fileName =
          "canvas_img_" + new Date().getMilliseconds() + String(idx) + ".png"
        formData.append("image", file, fileName) // file data 추가
        formData.append("extention", "png") // file data 추가
        //
        // saveList.push(file)
      })
      return BaseCommon.restful.call(
        "/file/uploadimge",
        formData,
        "post",
        false,
        {
          "Content-type": "multipart/form-data",
        }
      )
    },
    multiImgProcess: async (data) => {
      var formData = new FormData()
      var delFile = []

      data.forEach((img) => {
        if (img.state === "a") {
          let src = img.src.split(",")
          var blobBin = atob(src[1])
          var array = []
          for (var i = 0; i < blobBin.length; i++) {
            array.push(blobBin.charCodeAt(i))
          }
          // data:image/xxx;base64
          // 5번째 위치(:)부터 (;) 위치까지 자름
          // type = image/xxx
          var type = src[0].slice(5, src[0].indexOf(";"))
          var file = new Blob([new Uint8Array(array)], {
            type: type,
          })

          formData.append("add", file)
        } else if (img.state === "d") {
          // 삭제 이미지는 path만 저장
          delFile.push(img.src)
        }
      })

      if (delFile.length > 0) {
        formData.append("del", delFile)
      }
      return BaseCommon.restful.call(
        "/file/multiimgprocess",
        formData,
        "post",
        false,
        {
          "Content-type": "multipart/form-data",
        }
      )
    },
  },
  /** 로그인정보 */
  memberInfo: {
    /** 로그인 정보 가져오기 */
    getMember: () => {
      let user = store.getters["baseStore/getMemberInfo"]
      user =
        user === null || user === undefined
          ? {
              isLogin: false,
            }
          : user
      return user
    },
    /** 로그인정보 셋팅하기 */
    setMember: (loginInfo) => {
      store.dispatch("baseStore/setMemberInfo", loginInfo)
    },
    /** 로그아웃 후 로그인 화면으로이동 */
    moveLogin(loginmode) {
      loginmode = loginmode === undefined ? "user" : loginmode

      BaseCommon.restful.call("/member/logout").then((res) => {
        console.log(res)
        this.setMember(res)
        //router.push({ name: "login", params: { loginMode: loginmode } })
        BaseCommon.hostInfo.redirectName("login", { loginMode: loginmode })
      })
    } /** 세션의 나의정보 셋팅하기 */,
    getMyInfo: async () => {
      let res = await BaseCommon.restful.call(
        "/member/getmyinfo",
        null,
        "post",
        false
      )
      BaseCommon.memberInfo.setMember(res)
      return res
    },
  },
  /**
   * 로그인 정보 다시 가져오기
   */
  async LoginInfo() {
    let rtn = await BaseCommon.restful.call2("/member/getmyinfo")

    this.memberInfo.setMember(rtn)
    return rtn
  },

  /**
   * 메뉴정보 가져오기
   */
  menuInfo: {
    getMenu: (url) => {
      try {
        let menu = BaseCommon.memberInfo.getMember().menuList
        if (menu === undefined || menu.length === 0)
          return {
            menuName: "",
          }
        let menufilter = menu.filter((w) => w.menuUrl === url)
        if (menufilter === undefined || menufilter.length === 0)
          return {
            menuName: "",
          }
        else return menufilter[0]
      } catch (e) {
        console.log(e)
        let rtn = {
          menuName: [],
        }
        return rtn
      }
    },
  },
  /** 셋팅정보 */
  settingInfo: {
    /** 레이아웃 상단 하단 바정보 가져오기 */
    getIsShowBar: () => store.getters["baseStore/getIsShowBar"],
    /** 레이아웃 상단 하단 바정보 셋팅하기  */
    setIsShowBar: (bTrue) => store.dispatch("baseStore/setIsShowBar", bTrue),
    /** 레이아웃 상단 하단 바정보 가져오기 */
    getIsSideBar: () => store.getters["baseStore/getIsSideBar"],
    /** 레이아웃 상단 하단 바정보 셋팅하기  */
    setIsSideBar: (bTrue) => store.dispatch("baseStore/setIsSideBar", bTrue),

    /** 레이아웃 상단 하단 바정보 가져오기 */
    getIsAnnounce: () => store.getters["baseStore/getIsAnnounce"],
    /** 레이아웃 상단 하단 바정보 가져오기 */
    getLayoutMode: () => store.getters["baseStore/getLayoutMode"],

    /** 레이아웃 상단 하단 바정보 셋팅하기  */
    setIsAnnounce: (bTrue) => store.dispatch("baseStore/setIsAnnounce", bTrue),
    messageShow: (text, color) => {
      EventBus.$emit("messageShow", text, color)
    },
    /**
     *
     * @param option {
     * el : this.$el (무조건 입력)
     * isBar : app bar 표시 여부 (디폴트 true)
     * isTop : content 정렬 top 여부 (디폴트 true)
     * isEditCheck : 화면수정 체크여부 (디폴트 false)
     * }
     */
    pageInit(option) {
      option = Object.assign(
        {
          el: null,
          isBar: true,
          isTop: true,
          isEditCheck: false,
          layoutMode: "local",
          isSideBar: true,
        },
        option
      )

      let el = option.el,
        isBar = option.isBar,
        isTop = option.isTop,
        isEditCheck = option.isEditCheck,
        layoutMode = option.layoutMode,
        isSideBar = option.isSideBar

      EventBus.$emit("routerViewAlign", isTop ? "top" : "center")
      store.watch(() => {
        store.dispatch("baseStore/setIsShowBar", isBar)
        store.dispatch("baseStore/setLayoutMode", layoutMode)
        store.dispatch("baseStore/setIsSideBar", isSideBar)
      })

      if (isEditCheck === true) {
        Config.isFormChange = false

        el.querySelectorAll("input,select,textarea").forEach((element) => {
          element.addEventListener("change", (event) => {
            console.log("변경되었어요", event)
            Config.isFormChange = true
          })
        })
      }
    },
  },
  /** 공지사항 정보 변경정보 */

  hostInfo: {
    /**
     * url 이동
     */
    isMobile: {
      Android: function() {
        return navigator.userAgent.match(/Android/i) == null ? false : true
      },
      BlackBerry: function() {
        return navigator.userAgent.match(/BlackBerry/i) == null ? false : true
      },
      IOS: function() {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i) == null
          ? false
          : true
      },
      Opera: function() {
        return navigator.userAgent.match(/Opera Mini/i) == null ? false : true
      },
      Windows: function() {
        return navigator.userAgent.match(/IEMobile/i) == null ? false : true
      },
      any: function() {
        let bRtn =
          BaseCommon.hostInfo.isMobile.Android() ||
          BaseCommon.hostInfo.isMobile.BlackBerry() ||
          BaseCommon.hostInfo.isMobile.IOS() ||
          BaseCommon.hostInfo.isMobile.Opera() ||
          BaseCommon.hostInfo.isMobile.Windows()

        return bRtn
      },
    },
    viewPortInit() {
      if (!BaseCommon.hostInfo.isMobile.any()) return false
      let viewport = document.querySelector("meta[name=viewport]")
      let winWidth = window.outerWidth
      console.log("윈도우 위드 >>>>> ", window.outerWidth)
      if (winWidth > 1200) viewport.setAttribute("content", "width=1920")
      else if (winWidth > 600) viewport.setAttribute("content", "width=800")
      else viewport.setAttribute("content", "width=360")
    },
    redirect(path, params = null, isEdit = true) {
      console.log("redirect", path, params)

      if (!isEdit) {
        Config.isFormChange = false
        Config.isFormChangeCheck = false
      }
      if (BaseCommon.hostInfo.getCurrentPath() !== path) {
        router.push({
          path: path,
          params: params,
        })
      }
    },
    redirectName(name, params) {
      if (BaseCommon.hostInfo.getCurrentName() !== name) {
        router.push({
          name: name,
          params: params,
        })
      } else {
        router.push({
          params: params,
        })
      }
    },
    getCurrent() {
      return router.history.current
    },
    getCurrentPath() {
      return router.history.current.path
    },
    getCurrentName() {
      return router.history.current.name
    },
    getCurrentParam() {
      return router.params
    },
    getCurrentMenuInfo() {
      try {
        return BaseCommon.memberInfo
          .getMember()
          .menuList.filter(
            (w) => w.menuUrl === BaseCommon.hostInfo.getCurrentPath()
          )[0]
      } catch (e) {
        return null
      }
    },
  },
  /**
   * 폼정보
   */
  formInfo: {
    /** 동적 form submit */
    formSubmit: (url, jsonDatas) => {
      var newForm = document.createElement("form")

      newForm.name = "newForm"
      newForm.method = "post"
      newForm.action =
        window.location.origin.toLowerCase().indexOf("localhost") >= 0
          ? "http://localhost:8080" + url
          : url
      newForm.target = "_self"

      Object.keys(jsonDatas).forEach(function(key) {
        var input = document.createElement("input")
        input.setAttribute("type", "hidden")
        input.setAttribute("name", key)
        input.setAttribute("value", jsonDatas[key])
        newForm.appendChild(input)
      })

      document.body.appendChild(newForm)

      // submit form
      newForm.submit()
    },
    /**
     * input 정보
     */
    input: {
      /**
       * input focus
       */
      focus: (refName) => {
        try {
          this.$refs[refName].focus()
          this.$refs[refName].select()
        } catch (e) {
          console.log(e)
        }
      },
    },
    select: {
      getText: (obj, idx = 0) => {
        return obj.selectedOptions[idx].text
      },
      getVal: (obj, idx = 0) => {
        return obj.selectedOptions[idx].value
      },
    },
    checkImage: (imageSrc, good, bad) => {
      var img = new Image()
      img.onload = good
      img.onerror = bad
      img.src = imageSrc
    },
    filedownload(filename) {
      BaseCommon.hostInfo.redirect("/file/download/" + filename)
    },
    filedownload2: async (filepath) => {
      console.log(filepath)
      const axiosConfig = {
        method: "get",
        url: "/files/" + filepath,
        responseType: "blob",
        //                params: req,
      }

      try {
        const response = await axios(axiosConfig)
        const blob = new Blob([response.data], {
          type: response.headers["content-type"],
        })
        const anchor = document.createElement("a")
        anchor.href = window.URL.createObjectURL(blob)
        anchor.target = "_self"
        anchor.download = filepath

        anchor.click()
      } catch (err) {
        // toast.error("Error on force file download process. Try again.");
      }
    },
    removeElement(elemID) {
      try {
        setTimeout("document.querySelector('" + elemID + "').remove()", 1000)
      } catch (e) {
        console.log(e)
      }
    },
  },
  stringInfo: {
    isNullEmpty(val) {
      return val === undefined || val === null || val === "" ? true : false
    },
    toString(val) {
      return this.isNullEmpty(val) ? "" : val
    },
    replaceAll(str, searchStr, replaceStr) {
      try {
        return str.split(searchStr).join(replaceStr)
      } catch (e) {
        return ""
      }
    },
    changeKeywordCss(val, keyword_name, nType = 1) {
      let rtn

      if (nType === 1) {
        rtn =
          "<strong>" +
          String(val)
            .split(keyword_name)
            .join(
              `<span style='font-weight:500;opacity:0.6;'>${keyword_name}</span>`
            ) +
          "</strong>"
      } else {
        rtn = String(val)
          .split(keyword_name)
          .join(
            `<strong style='color:#0ba880!important'>${keyword_name}</strong>`
          )
      }
      return rtn
    },
    formatString(val1, ...params) {
      params.forEach((data, index) => {
        val1 = val1.replaceAll(`[${index}]`, data)
      })
      return val1
    },
  },
  dataInfo: {
    objectToQuerystring: (params) => {
      if (params === undefined || params === null) return ""
      return Object.keys(params)
        .map((key) => key + "=" + params[key])
        .join("&")
    },
    displayEmail(val) {
      let arr = val.split("@")
      if (arr.length !== 2) return ""
      let rtnEmail = ""
      if (arr[0].length < 3) {
        rtnEmail = arr[0] + "***" + "@" + arr[1]
      } else {
        rtnEmail = arr[0].substring(0, 3) + "***" + "@" + arr[1]
      }

      return rtnEmail
    },
    getAddClickCount(name, key) {
      let rtnCnt = 1
      let arrList = []
      if (localStorage[name] == undefined) {
        arrList.push(key)
      } else {
        arrList = JSON.parse(localStorage[name])
        rtnCnt = arrList.filter((w) => w === key).length === 0 ? 1 : 0
        if (rtnCnt === 1) arrList.push(key)
      }

      localStorage[name] = JSON.stringify(arrList)
      return rtnCnt
    },
    /**
     * Array 행 삭제
     * @param {*} arr
     * @param {*} key
     * @param {*} val
     */
    arrayDelete(arr, key, val) {
      arr.slice(
        arr.findIndex((e) => e[key] === val),
        1
      )

      return arr
    },
  },
  mapInfo: {
    /** 위경도로 거리 계산하기 */
    getDistance: (lat1, long1, lat2, long2) => {
      var R = 6371 // Radius of the earth in km
      var dLat = deg2rad(lat2 - lat1) // deg2rad below
      var dLon = deg2rad(long2 - long1)
      var a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(deg2rad(lat1)) *
          Math.cos(deg2rad(lat2)) *
          Math.sin(dLon / 2) *
          Math.sin(dLon / 2)
      var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
      var d = R * c // Distance in km
      function deg2rad(deg) {
        return deg * (Math.PI / 180)
      }
      return d
    },
    getPosition: () => {
      return new Promise((res, rej) => {
        navigator.geolocation.getCurrentPosition(res, rej)
      })
    },
    /** 현재위치 가져오기 */
    getCurrentArea: async (
      defaultLat = 37.51249,
      defaultLongi = 127.044501
    ) => {
      let lat, longi
      try {
        let position = await BaseCommon.mapInfo.getPosition()
        lat = position.coords.latitude // 위도
        longi = position.coords.longitude // 경도
        localStorage.userLocation = JSON.stringify({
          latitude: lat,
          longitude: longi,
        })
      } catch (e) {
        console.log(e)
        lat = defaultLat
        longi = defaultLongi
      }

      let rtn = await BaseCommon.restful.call2("/common/getCurrentDong", {
        lat: lat,
        long: longi,
      })
      console.log("getcurrentArea>>", rtn)
      return rtn[0]
    },
    getCurrentUserLocation() {
      if (BaseCommon.stringInfo.toString(localStorage.userLocation) !== "") {
        return JSON.parse(localStorage.userLocation)
      } else {
        return { latitude: null, longitude: null }
      }
    },
    getAddressArea: async (cond) => {
      let rtn = await BaseCommon.restful.call2("/common/getCodeByAddress", cond)

      return rtn
    },
    getCurrentAddress: async (lat, longi) => {
      let defaultLat = 37.51249
      let defaultLongi = 127.044501

      if (lat === undefined) {
        try {
          let position = await BaseCommon.mapInfo.getPosition()
          lat = position.coords.latitude // 위도
          longi = position.coords.longitude // 경도
          localStorage.userLocation = JSON.stringify({
            latitude: lat,
            longitude: longi,
          })
        } catch (e) {
          console.log(e)
          lat = defaultLat
          longi = defaultLongi
        }
      }

      let rtn = await BaseCommon.restful.call2("/kakaoapi/coord2regioncode", {
        lat: lat,
        longi: longi,
      })
      console.log("getcurrentAddress>>", rtn)
      if (rtn.documents.length > 0) {
        let address = rtn.documents[rtn.documents.length - 1]
        let cond = {
          sidoName: address.region_1depth_name,
          sigunguName: address.region_2depth_name,
          dong2Name: address.region_3depth_name,
          latitude: lat,
          longitude: longi,
        }
        console.log("조회조건 >>> ", cond)
        let rtn2 = await BaseCommon.restful.call2(
          "/common/getCodeByAddress",
          cond
        )
        console.log("getcurrentAddress2>>", rtn2[0])
        rtn2[0].latitude = lat
        rtn2[0].longitude = longi

        return rtn2[0]
      }
      return null
    },
  },
  imageInfo: {
    failed_image: false,
    cPicture: function(imageUrl) {
      return this.failed_image ? "http://ondaji.kro.kr" + imageUrl : imageUrl
    },
    onImgError: function(event) {
      console.log(event)

      this.failed_image = true
    },
  },
  videoInfo: {
    autoplayByscroll() {
      let videos = document.querySelectorAll("video")
      let fraction = 0.9999
      for (var i = 0; i < videos.length; i++) {
        let video = videos[i]

        let x = video.offsetLeft,
          y = video.offsetTop,
          w = video.offsetWidth,
          h = video.offsetHeight,
          r = x + w, //right
          b = y + h, //bottom
          visibleX,
          visibleY,
          visible

        visibleX = Math.max(
          0,
          Math.min(
            w,
            window.pageXOffset + window.innerWidth - x,
            r - window.pageXOffset
          )
        )
        visibleY = Math.max(
          0,
          Math.min(
            h,
            window.pageYOffset + window.innerHeight - y,
            b - window.pageYOffset
          )
        )
        visible = (visibleX * visibleY) / (w * h)

        console.log(
          "window.innerHeight",
          i,
          visibleY,
          window.innerHeight,
          video.getBoundingClientRect().top
        )
        let videolocation = video.getBoundingClientRect().top
        if (
          visible > fraction &&
          videolocation >= 0 &&
          videolocation < video.offsetHeight
        ) {
          video.play()
        } else {
          video.pause()
        }
      }
    },
  },
  dateInfo: {
    getDateDiffString(val) {
      if (val == null) return "0"
      if (val < 60) return String(val) + "분전"
      if (val < 60 * 24) return String(Math.round(val / 60.0)) + "시간전"
      if (val < 60 * 24 * 30)
        return String(Math.round(val / (60.0 * 24.0))) + "일전"
      if (val < 60 * 24 * 365)
        return String(Math.round(val / (60.0 * 24.0 * 30))) + "달전"
      else return String(Math.round(val / (60.0 * 24.0 * 365))) + "년전"
    },
  },
  numberInfo: {
    getNumberToKorean(val) {
      if (val === null) return "0"
      else if (val >= 100000000 && val < 1000000000)
        return this.$moment.this.getNumer(val, 1000000) + "백만"
      else if (val >= 10000000 && val < 100000000)
        return this.getNumer(val, 100000) + "십만"
      else if (val >= 1000000 && val < 10000000)
        return this.getNumer(val, 100000) + "십만"
      else if (val >= 100000 && val < 1000000)
        return this.getNumer(val, 10000) + "만"
      else if (val >= 10000 && val < 100000)
        return this.getNumer(val, 10000) + "천"
      else return String(val)
    },
    getNumer(val, nCnt) {
      return Math.round(val / (nCnt / 10)) / 10
    },
    getFileSize(val) {
      if (val >= 1000000000) {
        return String(val / 1000000000.0) + "Gb"
      } else if (val >= 1000000) {
        return String(val / 1000000.0) + "Mb"
      } else if (val >= 1000) {
        return String(val / 1000.0) + "Kb"
      } else {
        return String(val) + "byte"
      }
    },
  },
}

/*********** 확장메소드 적용*********/
/** 전화번호 */
String.prototype.phone = function phone() {
  return this.replace(
    /(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,
    "$1-$2-$3"
  )
}
/** 문자열 디폴트값 */
String.prototype.defaultVal = function defaultVal(val) {
  return this === undefined || this === null ? val : this
}
