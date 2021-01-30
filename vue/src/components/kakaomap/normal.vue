<template>
  <v-card :width="width" :height="height">
    <v-card-text
      class="pa-0"
      :style="`height:${this.height};width:${this.width};`"
    >
      <div id="mapwrap" :style="`height:${this.height};width:${this.width};`">
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
          :style="`width:100%;height:${height};`"
        />
        <!-- 지도 위에 표시될 마커 카테고리 -->

        <div class="category">
          <table>
            <tr>
              <td class="pa-0" cols="8">
                <Kakaomap-category
                  v-model="areaCode"
                  @change="categoryChange"
                />
              </td>
              <td class="pl-1" cols="2">
                <v-avatar color="white" size="30px" class="dvmarker avatar">
                  <img src="@/assets/images/map/add_nav.svg" alt="John" />
                </v-avatar>
              </td>
            </tr>
          </table>
        </div>

        <div :class="'dvmarker'">
          <v-btn
            v-if="false"
            small
            depressed
            style="text-transform: none"
            @click="setRadius(1000)"
          >
            <v-img
              src="@/assets/images/map/add_nav.svg"
              class="mobileRadius"
              max-width="1.1rem"
              max-height="2rem"
            />
          </v-btn>
          <v-btn
            v-if="false"
            class="ml-2"
            small
            :color="
              btnRadiusColors.depth2isClick ? 'lime darken-1' : 'lime lighten-5'
            "
            style="text-transform: none"
            @click="setRadius(10000)"
          >
            <v-icon
              dark
              :color="btnRadiusColors.depth2isClick ? 'lime lighten-5' : 'lime'"
              >location_on</v-icon
            >10km
          </v-btn>
          <v-btn
            v-if="false"
            class="ml-2"
            small
            :color="
              btnRadiusColors.depth3isClick ? 'red darken-1' : 'red lighten-5'
            "
            style="text-transform: none"
            @click="setRadius(100000)"
          >
            <v-icon
              dark
              :color="btnRadiusColors.depth3isClick ? 'red lighten-5' : 'red'"
              >location_on</v-icon
            >100km
          </v-btn>
        </div>
      </div>
    </v-card-text>
  </v-card>
</template>

<style lang="scss" scoped>
#mapwrap {
  .info {
    background-color: rgba($color: #fff, $alpha: 1) !important;
    .title {
      .overlayclose {
        float: right;
        margin: 0.2em;
        &:hover {
          cursor: pointer;
        }
      }
    }
  }
}

#mapwrap {
  position: relative;
  overflow: hidden;
}
.category,
.category * {
  margin: 0;
  padding: 0;
}
.category {
  position: absolute;
  overflow: hidden;
  top: 10px;
  left: 10px;

  z-index: 10;
}
.dvmarker {
  &.avatar {
    padding: 0.55rem;
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
    height: {
      type: String,
      default: "100%",
    },
    width: {
      type: String,
      default: "100%",
    },
    value: {
      type: Object,
    },
    level: {
      type: Number,
    },
  },
  components: {
    VueDaumMap,
  },
  watch: {
    value: {
      handler(n) {
        console.log(n)
        //this.dataInit(n)
        this.modelData = Object.assign({}, n)
      },
    },
    resData: {
      handler(n) {
        console.log(n)
        //this.dataInit(n)

        this.$emit("input", n)
      },
    },
  },
  data: () => {
    return {
      areaCode: "",
      areaName: "",
      mapInfo: {},
      resData: {
        address: "",
        roadAddress: "",
        zipCode: "",
        latitude: null,
        longitude: null,
        radius: 500,
      },
      kakaoMapInfo: {
        map: null,
        marker: null,
        radius: 500,
        circle: null,
        overlay: null,
        mapTypeId: VueDaumMap.MapTypeId.NORMAL,
        center: { lat: 37.450701, lng: 127.070667 },
        level: 6,
        libraries: ["services", "clusterer", "drawing"],
      },
      VueDaumMap: VueDaumMap,
      Config: Config,
      btnRadiusColors: {
        depth1isClick: true,
        depth2isClick: false,
        depth3isClick: false,
      },
    }
  },
  methods: {
    onLoad: function(map) {
      console.log(map)
      this.kakaoMapInfo.map = map

      this.mapInfo =
        this.value == undefined ? null : Object.assign({}, this.value)
      if (this.mapInfo != null) {
        this.kakaoMapInfo.radius =
          this.mapInfo.radius === undefined || this.mapInfo.radius === null
            ? 500
            : this.mapInfo.radius
        this.addMarker(
          new kakao.maps.LatLng(this.mapInfo.lat, this.mapInfo.longi)
        )

        this.addCircle()
        this.setMarkerCenter(this.mapInfo.lat, this.mapInfo.longi)
        this.setAddressArea(this.mapInfo.lat, this.mapInfo.longi)
      } else {
        this.setAddressArea()
      }
    },
    setAddressArea(lat, langi) {
      let self = this
      this.$BaseCommon.mapInfo.getCurrentAddress(lat, langi).then((res) => {
        /** 검색위치 */
        self.areaName = `${res.sidoName} ${res.sigunguName} ${res.dong2Name}`

        self.areaCode = res.code
      })
    },
    center_changed: function() {},
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
      this.setInit()
      this.addMarker(mouseEvent[0].latLng)
    },
    callMap(posInfo) {
      this.mapInfo = posInfo

      this.setInit()
      this.outSetMarker()
      this.setMarkerCenter(this.mapInfo.latitude, this.mapInfo.longitude)
      this.mapCenterChange()
    },
    dblclick: function(mouseEvent) {
      console.log("dblclick", mouseEvent)
    },
    rightclick: function(mouseEvent) {
      console.log("rightclick", mouseEvent)
    },
    mousemove: function() {
      // console.log("mousemove", mouseEvent)
    },
    dragstart: function() {},
    drag: function() {
      //   console.log("drag",mouseEvent)
    },
    dragend: function() {
      console.log(
        "dragend",
        this.kakaoMapInfo.map.getCenter().getLat(),
        this.kakaoMapInfo.map.getCenter().getLng()
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

    addMarker: function(position) {
      if (this.kakaoMapInfo.marker === null) {
        let imgUrl = require("@/assets/images/map/nav_1km.svg")
        let markerImage = new kakao.maps.MarkerImage(
          imgUrl,
          new kakao.maps.Size(32, 43),
          null
        )

        this.kakaoMapInfo.marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(position.getLat(), position.getLng()),
          draggable: true,
          image: markerImage,
        })
        this.kakaoMapInfo.marker.setMap(this.kakaoMapInfo.map)
        this.addCircle()
        this.setMakerEvent(this.kakaoMapInfo.marker)
      }
    },
    outSetMarker: function() {
      if (this.kakaoMapInfo.marker === null) {
        let imgUrl = require("@/assets/images/map/nav_1km.svg")
        let markerImage = new kakao.maps.MarkerImage(
          imgUrl,
          new kakao.maps.Size(32, 43),
          null
        )

        this.kakaoMapInfo.marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(this.mapInfo.lat, this.mapInfo.longi),
          draggable: true,
          image: markerImage,
        })
        this.kakaoMapInfo.marker.setMap(this.kakaoMapInfo.map)
        this.addCircle()
        this.setMakerEvent(this.kakaoMapInfo.marker)
      }
    },
    addCircle: function(position) {
      if (this.kakaoMapInfo.circle === null) {
        this.kakaoMapInfo.circle = new kakao.maps.Circle({
          map: this.kakaoMapInfo.map,
          center:
            position != null
              ? position
              : new kakao.maps.LatLng(
                  this.kakaoMapInfo.marker.getPosition().getLat(),
                  this.kakaoMapInfo.marker.getPosition().getLng()
                ),
          radius: this.kakaoMapInfo.radius,
          strokeColor: "#00BA5F",
          strokeOpacity: 1,
          strokeStyle: "line",
          strokeWeight: 2,
          fillColor: "#00E676",
          fillOpacity: 0.4,
        })

        // 지도에 원을 표시합니다
        this.kakaoMapInfo.circle.setMap(this.kakaoMapInfo.map)
      }
      this.setContent()
    },
    setContent: function() {
      let self = this
      let position = this.kakaoMapInfo.marker.getPosition()
      if (this.kakaoMapInfo.overlay != null) {
        this.kakaoMapInfo.overlay.setMap(null)
        this.kakaoMapInfo.overlay = null
      }

      this.resData.latitude = position.getLat()
      this.resData.longitude = position.getLng()
      this.resData.radius = this.kakaoMapInfo.radius
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
          self.resData.address =
            res.documents[0].address === null
              ? ""
              : res.documents[0].address.address_name
          self.resData.roadAddress =
            res.documents[0].road_address === null
              ? ""
              : res.documents[0].road_address.address_name
          self.resData.zipCode =
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

          if (self.kakaoMapInfo.overlay === null) {
            self.kakaoMapInfo.overlay = new kakao.maps.CustomOverlay({
              content: content,
              map: self.kakaoMapInfo.map,
              position: self.kakaoMapInfo.marker.getPosition(),
              clickable: true,
              zIndex: 4,
            })
          } else {
            self.kakaoMapInfo.overlay.setContent(content)
            self.kakaoMapInfo.overlay.position = self.kakaoMapInfo.marker.getPosition()
          }
          self.kakaoMapInfo.overlay.setMap(self.kakaoMapInfo.map)
        })
      this.$emit("input", this.resData)
    },

    setMakerEvent: function(marker) {
      let self = this
      kakao.maps.event.addListener(marker, "click", function() {})

      kakao.maps.event.addListener(marker, "mouseover", function() {})

      kakao.maps.event.addListener(marker, "mouseout", function() {})

      kakao.maps.event.addListener(marker, "rightclick", function() {
        self.kakaoMapInfo.circle.setMap(null)
        self.kakaoMapInfo.circle = null
        self.kakaoMapInfo.marker.setMap(null)
        self.kakaoMapInfo.marker = null
        self.kakaoMapInfo.overlay.setMap(null)
        self.kakaoMapInfo.overlay = null
        self.setInit()
      })

      kakao.maps.event.addListener(marker, "dragstart", function() {
        self.setCircleInit()
      })

      kakao.maps.event.addListener(marker, "dragend", function() {
        console.log("dragend")
        self.addCircle(marker.getPosition())
      })
    },
    setInit: function() {
      this.setCircleInit()
      this.setMarkerInit()
    },
    setCircleInit() {
      if (this.kakaoMapInfo.circle !== null) {
        this.kakaoMapInfo.circle.setMap(null)
        this.kakaoMapInfo.circle = null
      }
    },
    setMarkerInit() {
      if (this.kakaoMapInfo.marker !== null) {
        this.kakaoMapInfo.marker.setMap(null)
        this.kakaoMapInfo.marker = null
      }
    },
    setRadius: function(radius) {
      console.log("setRadius", radius)
      this.btnRadiusColors.depth1isClick = false
      this.btnRadiusColors.depth2isClick = false
      this.btnRadiusColors.depth3isClick = false
      if (!(this.level === undefined || this.level === null)) {
        this.kakaoMapInfo.map.setLevel(this.level)
      }
      if (radius === 1000) {
        this.kakaoMapInfo.map.setLevel(7)
        this.btnRadiusColors.depth1isClick = true
      } else if (radius === 10000) {
        this.kakaoMapInfo.map.setLevel(9)
        this.btnRadiusColors.depth2isClick = true
      } else if (radius === 100000) {
        this.kakaoMapInfo.map.setLevel(12)
        this.btnRadiusColors.depth3isClick = true
      }
      this.kakaoMapInfo.radius = radius

      if (this.kakaoMapInfo.marker != null) {
        this.setCircleInit()
        this.addCircle()
      }
      this.setMarkerCenter(
        this.kakaoMapInfo.marker.getPosition().getLat(),
        this.kakaoMapInfo.marker.getPosition().getLng()
      )
    },
    setMarkerCenter: function(lat, longi) {
      // 지도 중심을 이동 시킵니다

      this.kakaoMapInfo.map.setCenter(new kakao.maps.LatLng(lat, longi))
    },
    mapCenterChange: function() {
      this.$BaseCommon.restful
        .call2("/kakaoapi/coord2regioncode", {
          lat: this.kakaoMapInfo.map.getCenter().getLat(),
          longi: this.kakaoMapInfo.map.getCenter().getLng(),
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
                  if (this.areaCode != res2[0].code)
                    this.areaCode = res2[0].code
                }
              })
          }
        })
    },
    categoryChange(areaCode, areaName) {
      console.log("categoryChange", areaCode, areaName)
      this.areaName = areaName
      //this.areaCode = areaCode
      this.setAddressToCenter(areaName)
    },
    btnConfirm: function(bSave) {
      this.$emit("input", bSave ? this.resData : null)
      this.$emit("callback", bSave ? this.resData : null)
    },
  },
}
</script>
