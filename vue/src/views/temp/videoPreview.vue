<template>
  <v-container>
    <v-row>
      <v-col xs="12" sm="12" md="12">
        <h3 class="mt-5">동영상파일 업로드</h3>
        <Vuetify-videoUpload
          :index="1"
          :isThumbnail="true"
          @callBack="callBack_video"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col xs="12" sm="12" md="12">
        <Vuetify-videoUpload :index="2" @callBack="callBack_video" />
      </v-col>
    </v-row>
    <v-row>
      <v-col xs="12" sm="12" md="12">
        <v-textarea
          label="결과값"
          auto-grow
          outlined
          rows="1"
          row-height="15"
          :value="result"
        ></v-textarea>
      </v-col>
    </v-row>
    <v-row>
      <v-col xs="12" sm="12" md="12" align="center" justify="center">
        <v-img
          :src="imgSrc"
          contain
          width="150px"
          height="150px"
          aspect-ratio="1.7"
        ></v-img>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      result: "",
      imgSrc: "",
    }
  },

  created() {},
  methods: {
    callBack_video(uploadData, index) {
      console.log("callback >> ", uploadData, index)
      this.isShow = false

      this.result = JSON.stringify(uploadData)
      this.imgSrc = uploadData.pickThumb
    },
  },
}
</script>
