<template>
  <div class="text-center multimarkerMap">
    <v-dialog v-model="bDialog" stent persistent min-width="300px" width="100%">
      <v-card>
        <v-card-title class="popTitle"
          >광고영역 <v-spacer></v-spacer>
          <v-btn small icon @click="btnConfirm(false)">
            <v-icon>fas fa-times</v-icon>
          </v-btn>
        </v-card-title>
        <v-card-text class="pa-0">
          <div id="mapwrap">
            <vue-daum-map
              id="daummap"
              :appKey="Config.kakaoApiKey"
              :level.sync="kakaoMapInfo.level"
              :center.sync="kakaoMapInfo.center"
              :mapTypeId="kakaoMapInfo.mapTypeId"
              :libraries="kakaoMapInfo.libraries"
              @load="onLoad"
              @center_changed="center_changed"
              @zoom_start="zoom_start"
              @zoom_changed="zoom_changed"
              @bounds_changed="bounds_changed"
              @click="click"
              @dblclick="dblclick"
              @rightclick="rightclick"
              @mousemove="mousemove"
              @dragstart="dragstart"
              @drag="drag"
              @dragend="dragend"
              @idle="idle"
              @tilesloaded="tilesloaded"
              @maptypeid_changed="maptypeid_changed"
              style="width:100%;height:100%;"
            />
            <!-- 지도 위에 표시될 마커 카테고리 -->

            <div
              class="category"
              @click="
                () => {
                  isCategoryShow = false
                }
              "
            >
              <table>
                <tr>
                  <td>
                    <Kakaomap-category
                      v-model="areaCode"
                      :isShow="isCategoryShow"
                      @change="categoryChange"
                    />
                  </td>
                  <td>
                    <v-card
                      style="height:28px;"
                      class="transparent rounded pl-0 pr-0 pt-0"
                    >
                      <div :class="'dvmarker'">
                        <v-btn-toggle v-model="toggleNone" class="pl-0 pr-0">
                          <v-btn
                            small
                            style="text-transform: none;opacity:0.8"
                            v-for="(data, index) in radiusList"
                            v-bind:key="data"
                            :color="
                              radius === data
                                ? 'grey lighten-1'
                                : 'white lighten-5'
                            "
                            @click="setRadius(data, index)"
                          >
                            <v-img
                              src="@/assets/images/map/add_nav.svg"
                              class="mr-2 mobileRadius"
                            />
                            <strong>
                              {{ (data * 2.0) / 1000.0
                              }}<span class="mobileRadius">km</span></strong
                            >
                          </v-btn>
                        </v-btn-toggle>
                      </div>
                    </v-card>
                  </td>
                  <td>
                    <v-btn
                      small
                      :color="`brown darken-4`"
                      class="limitbutton ml-0 mt-0 pr-2  white--text"
                    >
                      제한범위 :
                      <span
                        style="text-transform: none;"
                        class="ml-1 pr-0 white--text font-weight-bold"
                        >{{ limitRadius / 1000.0 }} km
                      </span>
                    </v-btn>
                  </td>
                </tr>
              </table>
            </div>

            <v-card
              style="height:28px;"
              class="transparent selDistance pl-0 pr-0 pt-0"
            >
              <div :class="'dvmarker'">
                <v-btn-toggle v-model="toggleNone" class="pl-0 pr-0">
                  <v-btn
                    small
                    style="text-transform: none;opacity:0.8"
                    v-for="(data, index) in radiusList"
                    v-bind:key="data"
                    :color="
                      radius === data ? 'grey lighten-1' : 'white lighten-5'
                    "
                    @click="setRadius(data, index)"
                  >
                    <v-img
                      src="@/assets/images/map/add_nav.svg"
                      class="mr-2 mobileRadius"
                    />
                    <strong>
                      {{ (data * 2.0) / 1000.0
                      }}<span class="mobileRadius">km</span></strong
                    >
                  </v-btn>
                </v-btn-toggle>
              </div>
            </v-card>

            <div class="history text-center">
              <v-menu
                v-model="isHistory"
                class="historymenu white"
                style="margin-left:-10em;"
              >
                <template v-slot:activator="{ on, attrs }">
                  <div
                    :class="
                      (!isHistory
                        ? `brown--text text--darken-4 white lighten-4`
                        : `brown darken-4 white--text text--white`) +
                        ' historyButton elevation-4'
                    "
                    v-bind="attrs"
                    v-on="on"
                    small
                  >
                    <v-tooltip left>
                      <template v-slot:activator="{ on, attrs }">
                        <v-icon
                          :class="
                            !isHistory
                              ? `brown--text text--darken-4 white lighten-4`
                              : `brown darken-4 white--text text--white`
                          "
                          v-bind="attrs"
                          v-on="on"
                          width="12px"
                          height="18px"
                          >mdi-history</v-icon
                        >
                      </template>
                      <span>history</span>
                    </v-tooltip>
                  </div>
                </template>
                <v-card class="mx-auto pa-0 ma-0 historyTable" width="250" tile>
                  <v-card-text class="pa-0">
                    <v-card outlined class="pa-0 ma-0 ">
                      <div
                        class="table"
                        style="border-bottom: solid 1px lightgray;"
                      >
                        <v-simple-table fixed-header height="60vh">
                          <template v-slot:default>
                            <thead>
                              <tr>
                                <th>
                                  위치
                                </th>
                                <th>
                                  영역
                                </th>
                              </tr>
                            </thead>
                            <tbody>
                              <tr
                                v-for="(data, index) in historyList.filter(
                                  (w) => w.idx_sub === 0
                                )"
                                :key="index"
                                @click="historyClick(data, index)"
                              >
                                <td class="text-left">
                                  {{
                                    data.address.split(" ")[0] +
                                      " " +
                                      data.address.split(" ")[1] +
                                      (data.address.split(" ").length >= 2
                                        ? " " + data.address.split(" ")[2]
                                        : "")
                                  }}
                                </td>
                                <td class="text-right" style="min-width:80px">
                                  {{ (data.radius * 2.0) / 1000.0 }} km
                                </td>
                              </tr>
                            </tbody>
                          </template>
                        </v-simple-table>
                      </div>
                    </v-card>
                  </v-card-text>
                </v-card>
              </v-menu>
            </div>
          </div>
        </v-card-text>

        <v-divider></v-divider>

        <div class="confirmButton">
          <Vuetify-button
            class="ma-2"
            :rounded="false"
            :name="'btnSave'"
            :btntype="this.$Config.vuetify.btn.type.save"
            :text="'저장하기'"
            height="30px"
            @click="btnConfirm(true)"
          ></Vuetify-button>
        </div>
        <v-card-actions style="display:none">
          <v-spacer></v-spacer>

          <v-snackbar
            v-model="snackbar.snackbar"
            :multi-line="snackbar.multiLine"
          >
            {{ this.snackbar.text }}
            <v-btn color="red" snackbar.text @click="snackbar.snackbar = false">
              Close
            </v-btn>
          </v-snackbar>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<style lang="scss" scoped>
#mapwrap .info .title .overlayclose {
  float: right;
  margin: 0.2em;
}

#mapwrap .info .title .overlayclose:hover {
  cursor: pointer;
}

.v-menu__content {
  margin-left: -4.5rem;
}

.historyTable {
  z-index: 205;

  th,
  td {
    color: #3e2723 !important;
    font-size: 0.75rem !important;
    height: 2rem;
  }
  th {
    font-weight: bold !important;
    text-align: center;
    border-bottom: #d1c8c6 0.05rem solid !important;
  }
}
.historyButton {
  width: 34px;
  height: 34px;
  padding: 3px;

  border: 0.5px stroke #aaa !important;
}
.confirmButton {
  position: absolute;
  z-index: 99;
  right: 0rem;
  bottom: 0rem;
  .v-btn {
    margin-right: 1.25rem !important;
    margin-bottom: 1.25rem !important;
  }
}

.v-btn:not(.v-btn--text):not(.v-btn--outlined):hover:before {
  opacity: 0;
}
.historyTable td,
.historyTable th {
  padding: 0 0.8em;
}

.dvmarker {
  padding: 0.4em;
  padding-left: 0.2em;
}

.v-tooltip__content {
  margin-left: -0.2rem !important;
}
#mapwrap {
  position: relative;
  overflow: hidden;
  height: 83vh;
}

.v-dialog > .v-card > .v-card__title {
  font-size: 1.125rem;
}

.category,
.category * {
  margin: 0;
  padding: 0;

  font-weight: bold;
}
.category {
  position: absolute;
  overflow: hidden;
  top: 1.25rem;
  left: 1.25rem;

  z-index: 10;

  text-align: center;
  a {
    margin: 0.5em;
    text-decoration-line: none;
  }
  a:hover {
    text-decoration-line: underline;
  }
  table {
    border-style: none;
    border-collapse: collapse;
    td {
      padding-right: 0.875rem !important;
      border-collapse: collapse;
    }
  }
  .v-card {
    padding-top: 0.25em;
  }
}
.v-btn--active {
  background-color: transparent !important;
}
.dvmarker {
  overflow: hidden;
  margin-left: 0rem;
  z-index: 11;
  &2 {
    left: 200px;
  }
}

.history {
  position: absolute;
  top: 1.25rem;
  right: 1.25rem;
  opacity: 1;
  z-index: 11;
  border-radius: $border-radius-root;
}

.v-dialog > .v-card > .v-card__title {
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
  padding-right: 0.5rem;
  padding-left: 0.875rem;
}

.limitbutton {
  vertical-align: top;

  cursor: default;
  color: white;
}
@media only screen and (max-width: 767px) {
  .limitbutton {
    display: none;
  }
  #mapwrap {
    height: 80vh;
  }
  .selDistance {
    position: absolute;
    left: 0px;
    top: 0px;
  }

  .dvmarker {
    position: absolute;
    overflow: hidden;
    top: 1.5rem;
    left: 1.375rem;
    padding: 0rem;
    z-index: 11;
    &2 {
      left: 10px;
    }
  }
  .category {
    top: 3.5rem;
  }
  .history {
    top: 3.5rem;
  }
  [class*="dvDepth"] {
    top: 5.25rem;
  }
}
@media only screen and (max-width: 400px) {
  .v-menu__content {
    margin-left: -2.5em;
  }
}
</style>
<script>
import { Config } from "@/assets/scripts/Config"
import VueDaumMap from "vue-daum-map"

// eslint-disable-next-line no-unused-vars
/* global kakao */
// kakao 네임스페이스를 사용하기 위헤서 위 주석을 사용해야 함.
// 아니면 eslint 옵션을 수정해야 함

export default {
  props: {
    value: {
      type: Object,
    },
    dialog: {
      type: Boolean,
      required: true,
    },
  },
  components: {
    VueDaumMap,
  },
  mounted() {
    this.dataInit(this.value, true)
  },
  data: () => {
    return {
      areaCode: "01|01|01",
      areaName: "",
      isCategoryShow: false,
      isHistory: false,
      modelData: null,
      item: 1,
      historyList: [],
      selectHistorylist: [],
      displayLimitData: "",
      isOverlay: false,
      beforeIdx: 0,
      toggleNone: null,
      bLoad: false,
      radiusList: [500, 5000, 50000],
      limitRadiusList: [5000, 20000, 500000],
      levelList: [6, 10, 13],
      snackbar: {
        multiLine: true,
        snackbar: false,
        text: "",
      },
      resList: [],
      bDialog: false,
      count: 1,
      overlay: null,
      map: null,
      radius: 5000,
      limitRadius: 50000,
      mapList: [],
      kakaoMapInfo: {
        mapTypeId: VueDaumMap.MapTypeId.NORMAL,
        center: null,
        level: 10,
        libraries: ["services", "clusterer", "drawing"],
      },
      VueDaumMap: VueDaumMap,
      Config: Config,
    }
  },
  watch: {
    dialog: {
      handler(n, o) {
        console.log("watch >> ", n, o)
        this.bDialog = n
        console.log("mapShow", n)
        if (this.map !== null) this.dataInit(this.modelData)
      },
    },
    value: {
      handler(n) {
        console.log(n)
        //this.dataInit(n)
        this.modelData = Object.assign({}, n)
      },
    },
  },
  methods: {
    dataInit(val, isInit) {
      isInit = isInit === undefined ? false : true

      try {
        this.modelData = Object.assign({}, val)
        let self = this
        if (self.modelData !== null && self.modelData.list.length > 0) {
          setTimeout(() => {
            self.kakaoMapInfo.center = {
              lat: self.modelData.list[0].latitude,
              lng: self.modelData.list[0].longitude,
            }
            if (!isInit) self.loadMarker(self, self.modelData.list)
          }, 400)
        } else {
          self.kakaoMapInfo.center = { lat: 37.511888, lng: 127.044371 }

          if (navigator.geolocation) {
            // GeoLocation을 이용해서 접속 위치를 얻어옵니다
            navigator.geolocation.getCurrentPosition((position) => {
              let lat = position.coords.latitude // 위도
              let longi = position.coords.longitude // 경도
              //window.setTimeout(this.kakaoMapInfo.center(lat, longi), 300)
              try {
                self.kakaoMapInfo.center = { lat: lat, lng: longi }
              } catch (e) {
                console.log(e)
              }
            })
          } else {
            console.log(navigator.geolocation)

            window.setTimeout(
              self.kakaoMapInfo.center(37.5662952, 126.9429262),
              300
            )
          }
        }
      } catch (e) {
        console.log(e)
      }
      //   if (this.map !== null) this.mapInit(this.map)
    },
    setLimitDisplay() {
      this.count = this.modelData.count
      let len = 0
      try {
        len = this.resList.filter((w) => w !== null).length
      } catch (e) {
        console.log(e)
      }
      this.displayLimitData = `${len} / ${this.count}`
    },
    onLoad: function(map) {
      this.mapInit(map)
      setTimeout(() => {
        try {
          if (document.querySelector(".v-menu__content") !== null)
            document.querySelector(".v-menu__content").style.display = "block"
        } catch (e) {
          console.log(e)
        }
      }, 200)
    },
    mapInit(map) {
      let self = this
      this.setLimitDisplay()

      this.$BaseCommon.restful
        .call2("/common/getCommonList", { mainCode: "D003", hide: false })
        .then((res) => {
          self.radiusList = res.map((x) => Number(x.refData1))
          self.limitRadiusList = res.map((x) => Number(x.refData2))
          if (self.modelData != null && self.modelData.list.length > 0) {
            self.radius = self.modelData.list[0].radius
          } else {
            self.radius =
              self.radiusList.length >= 2
                ? self.radiusList[1]
                : self.radiusList[0]
          }
          let nIdx = self.radiusList.findIndex((w) => w === self.radius)
          self.limitRadius =
            self.limitRadiusList[
              self.radiusList.findIndex((k) => k == self.radius)
            ]
          self.levelList = res.map((x) => Number(x.refData3))
          self.map.setLevel(self.levelList[nIdx])
        })
      this.map = map

      if (self.modelData != null && self.modelData.list.length > 0) {
        self.count = this.modelData.count

        this.loadMarker(self, self.modelData.list)
        this.setAddressArea(
          self.modelData.list[0].latitude,
          self.modelData.list[0].longitude
        )
      } else {
        this.setAddressArea()
      }
      this.getHistoryList()
    },
    setAddressArea(lat, langi) {
      this.$BaseCommon.mapInfo
        .getCurrentAddress(
          lat === undefined
            ? undefined
            : { defaultLat: lat, defaultLongi: langi }
        )
        .then((res) => {
          /** 검색위치 */
          self.areaName = `${res.sidoName} ${res.sigunguName} ${res.dong2Name}`

          self.areaCode = res.code
        })
    },
    center_changed: function() {
      this.bLoad = true
    },
    zoom_start: function() {
      //console.log("zoom_start")
    },
    zoom_changed: function() {
      console.log("zoom_changed")
      this.mapCenterChange()
    },
    bounds_changed: function() {
      //console.log("bounds_changed")
    },
    click: function(mouseEvent) {
      //$$$$
      this.setMapMarkerClearAll()
      this.clickAddMarker(this, mouseEvent[0].latLng)
      this.isCategoryShow = false
    },
    clickAddMarker: (self, positoin) => {
      let position = positoin
      let nDv = 70
      let val = (0.00125 * self.radius) / nDv
      if (self.mapList.length === 0) {
        self.addMarker(0, positoin)
        self.addMarker(
          1,
          new kakao.maps.LatLng(
            position.getLat() - val,
            position.getLng() - val
          )
        )
        self.addMarker(
          2,
          new kakao.maps.LatLng(
            position.getLat() - val,
            position.getLng() + val
          )
        )
        self.setContent(0, self.mapList[0].marker, false)
      } else self.addMarker(self.mapList.length, positoin)
    },
    dblclick: function(mouseEvent) {
      console.log("dblclick", mouseEvent)
    },
    rightclick: function(mouseEvent) {
      console.log("rightclick", mouseEvent)
    },
    mousemove: function() {},
    dragstart: function() {},
    drag: function() {},
    dragend: function() {
      console.log(
        "dragend",
        this.map.getCenter().getLat(),
        this.map.getCenter().getLng()
      )

      this.mapCenterChange()
    },
    idle: () => {
      //console.log("idle")
    },
    tilesloaded: () => {
      //console.log("tilesloaded")
    },
    maptypeid_changed: () => {
      //console.log("maptypeid_changed")
    },
    setAddressToCenter(address) {
      this.$BaseCommon.restful
        .call2("/kakaoapi/address", { query: address, page: 1, size: 10 })
        .then((res) => {
          try {
            if (res.documents.length > 0) {
              let rtn = res.documents[0]

              this.setMarkerCenter(rtn.y, rtn.x)
              this.mapCenterChange()
            }
          } catch (e) {
            console.log(e)
          }
        })
    },

    addMarker: function(idx, position) {
      position = position === undefined || position === null ? null : position
      var obj = {}

      let position2 = position

      let imgUrl = require("@/assets/images/map/nav_" +
        String(idx + 1) +
        ".svg")

      let markerImage = new kakao.maps.MarkerImage(
        imgUrl,
        new kakao.maps.Size(32, 43),
        null
      )
      obj.marker = new kakao.maps.Marker({
        position: position2,
        draggable: true,
        image: markerImage,
      })
      obj.marker.setMap(this.map)

      obj.circle = this.addCircle(idx, obj.marker)
      obj.radius = this.radius

      this.mapList.push(obj)
      this.setMarkerEvent(idx)

      this.setContent(idx, obj.marker)

      //this.setBound()
    },
    hideOvery() {
      if (this.overlay !== null) this.overlay.setMap(self.map)
    },
    addCircle: function(idx, marker) {
      let circle

      if (this.mapList.length > idx) {
        this.setCircleInit(idx)
      }
      let radius = this.radius

      circle = new kakao.maps.Circle({
        map: this.map,
        center: new kakao.maps.LatLng(
          marker.getPosition().getLat(),
          marker.getPosition().getLng()
        ),
        radius: radius,
        strokeColor: "#00BA5F",
        strokeOpacity: 1,
        strokeStyle: "line",
        strokeWeight: 2,
        fillColor: "#00E676",
        fillOpacity: 0.4,
      })

      circle.radius = radius

      // 지도에 원을 표시합니다
      circle.setMap(this.map)
      if (idx === 0) circle.limitCircle = this.addLimitCircle(idx, marker)
      return circle
    },
    addLimitCircle(idx, marker) {
      if (idx === 0) {
        let circle

        let nIdx = this.radiusList.findIndex((k) => k == this.radius)

        this.limitRadius = this.limitRadiusList[nIdx]

        circle = new kakao.maps.Circle({
          map: this.map,
          center: new kakao.maps.LatLng(
            marker.getPosition().getLat(),
            marker.getPosition().getLng()
          ),
          radius: this.limitRadius,
          strokeColor: "#00BA5F",
          strokeOpacity: 1,
          strokeStyle: "line",
          strokeWeight: 2,
          fillColor: "#00E676",
          fillOpacity: 0.1,
        })

        circle.radius = this.radiusList

        // 지도에 원을 표시합니다
        circle.setMap(this.map)
        return circle
      }
      return null
    },
    setContent: function(idx, maker, isOverlay) {
      isOverlay = this.isOverlayShow(isOverlay)
      this.isOverlay = isOverlay === undefined ? !this.isOverlay : isOverlay

      let position = maker.getPosition()

      let self = this
      let resData = new Object()
      resData.latitude = position.getLat()
      resData.longitude = position.getLng()
      this.$BaseCommon.restful
        .call2("/kakaoapi/getCoord2address", {
          lat: position.getLat(),
          longi: position.getLng(),
        })
        .then((res) => {
          if (res.documents.length === 0) return
          console.log(res.documents[0].road_address)
          console.log(res.documents[0].address)
          console.log(res)
          resData.address =
            res.documents[0].address === null
              ? ""
              : res.documents[0].address.address_name
          resData.roadAddress =
            res.documents[0].road_address === null
              ? ""
              : res.documents[0].road_address.address_name
          resData.zipCode =
            res.documents[0].road_address === null
              ? ""
              : res.documents[0].road_address.zone_no
          let content = ` <div class="kakaomapInfo"> 
                                    <div class="mapinfo">
                                        <div class="body">
                                            <div class="desc">
                                              <i class="fa fa-times overlayclose ma-0"  onclick="document.querySelector('.kakaomapInfo').style.display='none';"></i>
                                         
                                              <span class="maptitle">현재위치</span>${"<div class='jibun ellipsis'>" +
                                                res.documents[0].address
                                                  .address_name +
                                                (res.documents[0]
                                                  .road_address === null
                                                  ? ""
                                                  : " (우) " +
                                                    res.documents[0]
                                                      .road_address.zone_no) +
                                                "</div>"}
                                                ${
                                                  res.documents[0]
                                                    .road_address !== null
                                                    ? "<div class='road ellipsis'>" +
                                                      res.documents[0]
                                                        .road_address
                                                        .address_name +
                                                      "</div>"
                                                    : ""
                                                }
                                               
                                            </div>
                                        </div>
                                    </div>
                                </div>`

          try {
            resData.radius = self.mapList[idx].radius
          } catch (e) {
            console.log(e)
          }
          self.resList[idx] = resData
          this.setLimitDisplay()
          if (self.overlay !== null) self.overlay.setMap(null)
          if (this.isOverlay) {
            //if (self.overlay === null) {
            self.overlay = new kakao.maps.CustomOverlay({
              content: content,
              map: self.map,
              position: self.mapList[idx].marker.getPosition(),
              clickable: true,
              zIndex: 4,
            })

            self.overlay.setMap(self.map)
            try {
              //document.querySelector('.kakaomapInfo').style.display='block'
            } catch (e) {
              console.log(e)
            }
          }
        })
    },
    isOverlayShow(bShow) {
      let lsShow = bShow
      try {
        if (
          document.querySelector(".kakaomapInfo") !== null &&
          document.querySelector(".kakaomapInfo") !== undefined &&
          document.querySelector(".kakaomapInfo").style !== null &&
          document.querySelector(".kakaomapInfo").style.display === "none"
        ) {
          document.querySelector(".kakaomapInfo").style.display = "block"
          lsShow = true
        }

        if (!lsShow) {
          if (this.overlay !== null) this.overlay.setMap(null)
        }
      } catch (e) {
        console.log(e)
      }
      return lsShow
    },
    setOverlayClear() {
      if (this.overlay != null) {
        this.overlay.setMap(null)
        this.overlay = null
      }
    },
    setMapMarkerClearAll() {
      for (let i = this.mapList.length - 1; i >= 0; i--) {
        this.setMapMarkerClean(this, i)
      }
      this.mapList = []
      this.resList = []
    },
    setMapMarkerClean: (self, idx) => {
      if (self.mapList.length - 1 < idx) return

      self.setOverlayClear()
      self.setInit(idx)

      console.log(self.mapList)
      //self.setLimitDisplay();
    },

    setMarkerEvent: function(idx) {
      let self = this

      kakao.maps.event.addListener(
        self.mapList[idx].marker,
        "click",
        function() {
          self.isOverlay = !self.isOverlay
          if (self.beforeIdx !== idx) self.isOverlay = true

          self.setContent(idx, self.mapList[idx].marker, self.isOverlay)
          self.beforeIdx = idx
        }
      )

      kakao.maps.event.addListener(
        self.mapList[idx].marker,
        "mouseover",
        function() {
          //alert("mouseover")
          //self.setContent(idx, self.mapList[idx].marker, true)
        }
      )

      kakao.maps.event.addListener(
        self.mapList[idx].marker,
        "mouseout",
        function() {
          //alert("mouseout")
          //self.setOverlayClear()
        }
      )

      kakao.maps.event.addListener(
        self.mapList[idx].marker,
        "rightclick",
        function() {
          //self.setMapMarkerClean(idx);
        }
      )
      let beforePosition = null
      kakao.maps.event.addListener(
        self.mapList[idx].marker,
        "dragstart",
        function() {
          beforePosition = self.mapList[idx].marker.getPosition()
        }
      )

      kakao.maps.event.addListener(
        self.mapList[idx].marker,
        "dragend",
        function() {
          console.log("dragend")

          if (idx > 0) {
            console.log(distance)
            let pos = self.mapList[0].marker.getPosition()
            let aftPos = self.mapList[idx].marker.getPosition()
            let distance = self.$BaseCommon.mapInfo.getDistance(
              pos.getLat(),
              pos.getLng(),
              aftPos.getLat(),
              aftPos.getLng()
            )
            if (self.limitRadius <= distance * 1000 + self.radius) {
              self.mapList[idx].marker.setPosition(beforePosition)
              return
            }
            self.setCircleInit(idx)
            self.mapList[idx].circle = self.addCircle(
              idx,
              self.mapList[idx].marker
            )
            self.setContent(idx, self.mapList[idx].marker, true)
          } else {
            let addMarkerPos = self.mapList[idx].marker.getPosition()
            //#####
            self.setMapMarkerClearAll()
            //self.setCircleInit(idx);
            self.clickAddMarker(self, addMarkerPos)
          }
        }
      )
    },
    setInit: function(idx) {
      if (idx === 0) this.mapList[idx].circle.limitCircle.setMap(null)
      this.setCircleInit(idx)
      this.setMarkerInit(idx)

      this.mapList[idx] = null
      this.resList[idx] = null
    },
    setCircleInit(idx) {
      if (this.mapList.length > idx) {
        if (this.mapList[idx].circle !== null) {
          if (idx === 0) this.mapList[idx].circle.limitCircle.setMap(null)
          this.mapList[idx].circle.setMap(null)
          this.mapList[idx].circle = null
        }
      }
    },
    setMarkerInit(idx) {
      if (this.mapList[idx].marker !== null) {
        this.mapList[idx].marker.setMap(null)
        this.mapList[idx].marker = null
      }
    },
    setRadius: function(radius, index) {
      console.log("setRadius", radius)

      this.radius = radius
      this.setRadiusAfter(index)
      this.limitRadius = this.limitRadiusList[index]
      console.log("제한영역>>", this.limitRadius)
      this.setMapMarkerClearAll()
    },
    setRadiusAfter(index) {
      console.log("setRadiusAfter", index)
      this.map.setLevel(this.levelList[index])
    },

    setMarkerCenter: function(lat, longi) {
      // 지도 중심을 이동 시킵니다
      if (this.map !== null)
        this.map.setCenter(new kakao.maps.LatLng(lat, longi))
    },
    mapCenterChange: function(lat, long) {
      this.$BaseCommon.restful
        .call2("/kakaoapi/coord2regioncode", {
          lat: lat === undefined ? this.map.getCenter().getLat() : lat,
          longi: long === undefined ? this.map.getCenter().getLng() : long,
        })
        .then((res) => {
          console.log("mapCenterChange =>", res)
          if (res.documents.length > 0) {
            this.areaName = `${res.documents[0].region_1depth_name} ${res.documents[0].region_2depth_name} ${res.documents[0].region_3depth_name}`

            this.$BaseCommon.mapInfo
              .getAddressArea({
                address: this.areaName,
              })
              .then((res2) => {
                if (res2 != null && res2.length > 0) {
                  this.areaCode = res2[0].code
                }
              })
          }
        })
    },
    categoryChange(areaCode, areaName) {
      console.log("categoryChange", areaCode, areaName)
      this.areaName = areaName
      this.setAddressToCenter(areaName)
    },
    btnConfirm: function(bSave) {
      this.bDialog = false
      let rtn = bSave ? this.resList.filter((w) => w !== null) : null

      if (rtn != null && this.selectHistorylist.length === rtn.length) {
        this.modelData.list = this.selectHistorylist
      } else this.modelData.list = rtn
      let rtn2 = Object.assign({}, this.modelData)
      this.$emit("input", rtn2)
      this.$emit("callback", this.modelData)

      if (rtn !== null) {
        this.saveHistory(this.modelData.list)
        let history = window.localStorage.getItem("adPlaceHistory")

        let arrHistory = new Array()
        if (!(history === undefined || history === null)) {
          arrHistory = JSON.parse(history)
        }
        arrHistory.push(JSON.stringify(rtn))
        window.localStorage.setItem(
          "adPlaceHistory",
          JSON.stringify(arrHistory)
        )
      }
    },
    saveHistory(list) {
      if (list.length > 0) {
        for (let i = 0; i < list.length; i++) {
          list[i].limitRadius = this.limitRadius
        }
      }

      this.$BaseCommon.restful
        .call2("/mongodb/insertLogAdPlace", list)
        .then((res) => {
          console.log(res)
          this.getHistoryList()
        })
    },
    getHistoryList() {
      console.log("getHistoryList >>")
      this.$BaseCommon.restful
        .call2("/mongodb/logAdplacelist", { member_code: null })
        .then((res) => {
          this.historyList = res
        })
    },
    /** 마커위치에 따라 지도 확장 */
    setBound() {
      if (this.mapList.length > 1) {
        let bounds = new kakao.maps.LatLngBounds()
        this.mapList.forEach((data) => {
          bounds.extend(data.marker.getPosition())
        })
        this.map.setBounds(bounds)
      } else if (this.mapList.length === 1) {
        this.setRadiusAfter(this.mapList[0].radius)
        this.map.setCenter(this.mapList[0].marker.getPosition())
        this.mapCenterChange(
          this.mapList[0].marker.getPosition().getLat(),
          this.mapList[0].marker.getPosition().getLng()
        )
      }
    },
    historyClick(data, index) {
      console.log(index)
      let historyData = this.historyList.filter((w) => w.idx === data.idx)
      console.log("select history", historyData)

      this.radius = data.radius
      this.limitRadius = data.limitRadius
      setTimeout(() => {
        this.loadMarker(this, historyData)
        this.selectHistorylist = historyData
      }, 300)
    },
    loadMarker(self, list) {
      self.setMapMarkerClearAll()
      list.forEach((data, index) => {
        try {
          let pos = new kakao.maps.LatLng(data.latitude, data.longitude)
          self.addMarker(index, pos)
        } catch (e) {
          console.log(e)
        }
      })
      if (self.mapList.length > 0) {
        self.setContent(0, self.mapList[0].marker, false)
      }
      let levelIndex = self.radiusList.findIndex((w) => w === this.radius)
      if (self.map !== null && self.levelList.length > 0) {
        self.map.setLevel(
          self.levelList[levelIndex] + (window.screen.width < 768 ? 1 : 0)
        )
      }

      self.setMarkerCenter(list[0].latitude, list[0].longitude)
    },
  },
}
</script>
