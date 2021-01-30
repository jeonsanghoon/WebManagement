<template>
  <v-container class="grey lighten-5">
    <v-row justify="center" no-gutters>
      <v-col cols="12" class="pa-4">
        <Kakaomap-normalPop
          :dialog="dialog"
          v-model="mapInfo"
          @callback="kakaomap_nomal_Callback"
        />
        <v-btn class="mb-5" @click="modal">영역지정</v-btn>
        <Kakaomap-normalPop
          :dialog="dialog2"
          v-model="mapInfo2"
          @callback="kakaomap_nomal_Callback2"
        />
        <v-btn class="mb-5" @click="modal2">영역미지정</v-btn>

        <Kakaomap-multiMap
          v-model="mapInfoMulti"
          :dialog="dialog3"
          @callback="kakaomap_multi_Callback3"
        />
        <v-btn class="mb-5" @click="modal3">광고영역 다중등록</v-btn>

        <v-textarea
          label="결과값"
          auto-grow
          outlined
          rows="5"
          row-height="50"
          style="width:100%"
          shaped
          :value="rtnData"
        ></v-textarea>
        <hr />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12"> </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  components: {},
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({
      el: this.$el,
    })
  },
  data() {
    return {
      dialog: false,
      dialog2: false,
      dialog3: false,
      mapInfo: {
        lat: 35.512091,
        longi: 127.044348,
        radius: 5000,
      },
      mapInfo2: null,
      mapInfoMulti: {
        count: 3 /** 맵의 광고영역 최대 갯수 */,
        list: [
          /**  중심 마커  */
          {
            latitude: 35.155218755551616,
            longitude: 126.87778145840258,
            radius: 5000,
          },
          {
            latitude: 35.06593304126589,
            longitude: 126.78849574411687,
            radius: 5000,
          },
          {
            latitude: 35.06593304126591,
            longitude: 126.96706717268829,
            radius: 5000,
          },
        ],
      },
      rtnData: "",
    }
  },
  methods: {
    modal: function() {
      console.log("modal")
      this.dialog = true
    },
    modal2: function() {
      console.log("modal2")
      this.dialog2 = true
    },
    modal3: function() {
      console.log("modal3")
      this.dialog3 = true
    },
    kakaomap_nomal_Callback: function(res) {
      this.dialog = false

      if (res != null) {
        this.rtnData =
          "광고영역설정=>>" +
          JSON.stringify(res) +
          ">>>>>>>>>" +
          JSON.stringify(this.mapInfo2)
      } else {
        this.rtnData = "광고영역설정 취소 "
      }
    },
    kakaomap_nomal_Callback2: function(res) {
      this.dialog2 = false
      if (res != null) {
        this.rtnData =
          "광고영역설정2=>>" +
          JSON.stringify(res) +
          ">>>>>>>>>" +
          JSON.stringify(this.mapInfo2)
      } else {
        this.rtnData = "광고영역설정2 취소 "
      }
    },

    kakaomap_multi_Callback3: function(res) {
      console.log(res)
      console.log("모델확인", this.mapInfoMulti)
      this.dialog3 = false
      let resModel = res
      this.rtnData = "광고멀티영역설정=>>"
      if (!(resModel == null || resModel.list == null)) {
        for (let i = 0; i < resModel.list.length; i++)
          this.rtnData = this.rtnData + JSON.stringify(resModel.list[i])
      } else {
        this.rtnData = "광고멀티영역설정 취소 "
      }
    },
  },
}
</script>
