<template>
  <v-container class="fluid align-start justify-start pt-10">
    <v-row justify="center">
      <v-col cols="12">
        <h1>영역지정</h1>
      </v-col>
      <v-col cols="4" class="pa-4 text-center">
        <Kakaomap-normal v-model="mapInfo1" />
      </v-col>
      <v-col cols="1">
        <Vuetify-button
          rounded
          class="ma-2"
          :btntype="this.$Config.vuetify.btn.type.confirm"
          :text="'결과값 >>'"
          @click="confirm1"
        >
        </Vuetify-button>
      </v-col>
      <v-col>
        <v-textarea
          label="결과값"
          auto-grow
          outlined
          rows="5"
          row-height="50"
          style="width:100%"
          shaped
          :value="rtnData1"
        >
        </v-textarea>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <hr />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <h1>영역미지정</h1>
      </v-col>
      <v-col cols="4" class="pa-4 text-center">
        <Kakaomap-normal v-model="mapInfo2" height="30rem" width="30rem" />
      </v-col>
      <v-col cols="1">
        <Vuetify-button
          rounded
          class="ma-2"
          :btntype="this.$Config.vuetify.btn.type.confirm"
          :text="'결과값 >>'"
          @click="confirm2"
        ></Vuetify-button>
      </v-col>
      <v-col>
        <v-textarea
          label="결과값"
          auto-grow
          outlined
          rows="5"
          row-height="50"
          style="width:100%"
          shaped
          :value="rtnData2"
        ></v-textarea>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({
      el: this.$el,
    })
  },
  data() {
    return {
      mapInfo1: {
        lat: 37.512091,
        longi: 127.044348,
      },
      rtnData1: "",
      mapInfo2: null,
      rtnData2: "",
    }
  },
  methods: {
    confirm1() {
      this.rtnData1 = JSON.stringify(this.mapInfo1)
    },
    confirm2() {
      this.rtnData2 = JSON.stringify(this.mapInfo2)
    },
  },
}
</script>
