<template>
  <v-container class="pa-0" fluid>
    <v-row class="px-2 px-lg-0" no-gutters>
      <v-col cols="12" lg="5" offset-lg="2">
        <!-- 광고 영역 -->
        <div class="range-box" no-gutters>
          <div class="range-title ondaji_font_16_bold_brown_80">
            광고영역 설정
          </div>
        </div>

        <div class="map-wrap" no-gutters>
          <Kakaomap-normal ref="map" v-model="posInfo" height="23.45rem" />
          <div class="expand-wrap" @click="mapExpand">
            <v-icon small color="#3E2723">
              fas fa-expand
            </v-icon>
          </div>
        </div>

        <v-row justify="center" no-gutters>
          <mapModal
            :posInfo="posInfo"
            :dialog.sync="dialog"
            @close="modalClose"
          ></mapModal>
        </v-row>

        <!-- 버튼 -->
        <v-row class="btn-box" no-gutters>
          <div class="mr-auto">
            <v-btn class="prev-btn" rounded depressed @click="prevPage()">
              <span class="ondaji_font_14_bold_brown_80"
                ><v-icon class="btn-left-icon">fas fa-chevron-left</v-icon
                >이전</span
              >
            </v-btn>
          </div>

          <div class="ml-auto">
            <v-btn class="complete-btn" depressed @click="complete()">
              <span class="ondaji_font_12_bold_white">{{
                completeBtnText
              }}</span>
            </v-btn>
          </div>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import mapModal from "@/views/mediaShop/components/mapModal"

export default {
  components: {
    mapModal,
  },
  props: {
    info: {
      type: Object,
      required: false,
    },
    call: {
      type: Boolean,
      required: false,
    },
  },
  data() {
    return {
      posInfo: {
        lat: 37.512091,
        longi: 127.044348,
        radius: 500,
      },

      dialog: false,

      completeBtnText: `완료하기`,
    }
  },
  created() {
    if ((Object.keys(this.info).length === 0) === false) {
      this.initRange()
    }
  },
  methods: {
    initRange() {
      this.posInfo = {}

      this.posInfo.lat = this.info.latitude
      this.posInfo.longi = this.info.longitude
      this.posInfo.radius = this.info.radius

      if (this.call === true) {
        this.completeBtnText = `저장하기`
      }
    },
    prevPage() {
      this.$emit("prevPage", this.getInfo())
    },
    complete() {
      this.$emit("complete", this.getInfo())
    },
    getInfo() {
      let posInfo = {}

      posInfo.latitude = this.posInfo.latitude
      posInfo.longitude = this.posInfo.longitude
      posInfo.radius = this.posInfo.radius
      posInfo.address = this.posInfo.address
      posInfo.roadAddress = this.posInfo.roadAddress
      posInfo.zipCode = this.posInfo.zipCode
      posInfo.call = this.info.call

      return posInfo
    },
    modalClose(posInfo) {
      this.$refs.map.callMap(posInfo)
    },
    mapExpand() {
      this.dialog = true
      this.setPos(this.posInfo)
    },
    setPos(res) {
      this.posInfo.lat = res.latitude
      this.posInfo.longi = res.longitude
    },
  },
}
</script>

<style lang="scss" scoped>
.range-box {
  text-align: start;
  margin-top: 40px;
}

.range-title {
  margin-bottom: 12px;
}

.map-wrap {
  position: relative;
  border: 5px solid white;
  margin-bottom: 40px;
}

.expand-wrap {
  background-color: white;
  border: 1px solid #aaaaaa;
  border-radius: 2px;
  width: 25px;
  height: 25px;
  position: absolute;
  bottom: 15px;
  right: 15px;
  z-index: 2;
  cursor: pointer;
}

.btn-box {
  margin-bottom: 40px;
}

.prev-btn {
  width: 88px !important;
  height: 30px !important;
  border: 2px solid #3e2723;
  background: transparent !important;
  &:hover {
    background: #e2d9d2 !important;
  }
}

.btn-left-icon {
  margin-right: 28px;
  font-size: 0.875rem;
}

.complete-btn {
  width: 68px !important;
  height: 28px !important;
  background: #3e2723 !important;
}
</style>
