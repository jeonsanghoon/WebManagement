<template>
  <v-container ref="banner">
    <v-row>
      <v-col class="title text-left">
        배너로드 예제 (사이니지 6프레임)
      </v-col>
    </v-row>
    <v-row class="ma-0">
      <v-col class="pa-0">
        <Vuetify-bannerUpload
          v-model="uploadModel"
          @deleteBanner="deleteBanner"
          @infoShow="infoShow"
          @mobilePreview="mobilePreview"
        />
        <div class="dvInfomation">설명을 넣어주세요 설명을요</div>
      </v-col>
    </v-row>
    <v-row class="ma-0">
      <v-col>
        <Vuetify-button
          class="ma-2"
          :name="'btnSave'"
          :btntype="this.$Config.vuetify.btn.type.save"
          :text="'확인하기'"
          @click="btnConfirm()"
        ></Vuetify-button>
      </v-col>
    </v-row>
    <v-row>
      <v-col class="pa-0">
        <v-textarea
          label="결과값"
          auto-grow
          outlined
          rows="5"
          row-height="3"
          style="width:100%"
          shaped
          v-model="rtnData"
        ></v-textarea>
      </v-col>
    </v-row>
    <v-row>
      <v-col class="title text-left">
        모바일배너
      </v-col>
    </v-row>
    <v-row class="ma-0">
      <v-col class="pa-0">
        <Vuetify-bannerUpload
          v-model="uploadModel2"
          @deleteBanner="deleteBanner2"
        />
      </v-col>
    </v-row>
    <v-row class="ma-0">
      <v-col>
        <Vuetify-button
          class="ma-2"
          :name="'btnSave'"
          :btntype="this.$Config.vuetify.btn.type.save"
          :text="'확인하기'"
          @click="btnConfirm2()"
        ></Vuetify-button>
      </v-col>
    </v-row>
    <v-row class="ma-0">
      <v-col class="pa-0">
        <v-textarea
          label="결과값"
          auto-grow
          outlined
          rows="5"
          row-height="3"
          style="width:100%"
          shaped
          v-model="rtnData2"
        ></v-textarea>
      </v-col>
    </v-row>
    <v-row>
      <v-col class="title text-left">
        사이니지 9:16
      </v-col>
    </v-row>
    <v-row class="ma-0">
      <v-col class="pa-0">
        <Vuetify-bannerUpload
          v-model="uploadModel3"
          @deleteBanner="deleteBanner3"
        />
      </v-col>
    </v-row>
    <v-row class="ma-0">
      <v-col>
        <Vuetify-button
          class="ma-2"
          :name="'btnSave'"
          :btntype="this.$Config.vuetify.btn.type.save"
          :text="'확인하기'"
          @click="btnConfirm3()"
        ></Vuetify-button>

        <Vuetify-button
          class="ma-2"
          :name="'btnSave'"
          :btntype="this.$Config.vuetify.btn.type.save"
          :text="'16:9로변경'"
          @click="btnChange169()"
        ></Vuetify-button>
      </v-col>
    </v-row>
    <v-row class="ma-0">
      <v-col class="pa-0">
        <v-textarea
          label="결과값"
          auto-grow
          outlined
          rows="5"
          row-height="3"
          style="width:100%"
          shaped
          v-model="rtnData3"
        ></v-textarea>
      </v-col>
    </v-row>
    <v-row>
      <v-col class="title text-left">
        사이니지 16:9
      </v-col>
    </v-row>
    <v-row class="ma-0">
      <v-col class="pa-0">
        <Vuetify-bannerUpload
          v-model="uploadModel4"
          @deleteBanner="deleteBanner4"
        />
      </v-col>
    </v-row>
    <v-row class="ma-0">
      <v-col>
        <Vuetify-button
          class="ma-2"
          :name="'btnSave'"
          :btntype="this.$Config.vuetify.btn.type.save"
          :text="'확인하기'"
          @click="btnConfirm4()"
        ></Vuetify-button>
      </v-col>
    </v-row>
    <v-row class="ma-0">
      <v-col class="pa-0">
        <v-textarea
          label="결과값"
          auto-grow
          outlined
          rows="5"
          row-height="3"
          style="width:100%"
          shaped
          v-model="rtnData4"
        ></v-textarea>
      </v-col>
    </v-row>
    <v-row>
      <v-col class="title text-left">
        사이니지 6Frame
      </v-col>
    </v-row>
    <v-row class="ma-0">
      <v-col class="pa-0">
        <Vuetify-bannerUpload
          v-model="uploadModel5"
          @deleteBanner="deleteBanner5"
        />
      </v-col>
    </v-row>
    <v-row class="ma-0">
      <v-col>
        <Vuetify-button
          class="ma-2"
          :name="'btnSave'"
          :btntype="this.$Config.vuetify.btn.type.save"
          :text="'확인하기'"
          @click="btnConfirm5()"
        ></Vuetify-button>
      </v-col>
    </v-row>
    <v-row class="ma-0">
      <v-col class="pa-0">
        <v-textarea
          label="결과값"
          auto-grow
          outlined
          rows="5"
          row-height="3"
          style="width:100%"
          shaped
          v-model="rtnData5"
        ></v-textarea>
      </v-col>
    </v-row>
  </v-container>
</template>
<style scoped>
.dvInfomation {
  position: absolute;
  top: 80px;
  left: 40px;
  z-index: 5;
  background-color: red;
  display: none;
}
</style>
<script>
export default {
  mounted() {
    this.$store.watch(() => {
      this.$store.dispatch("baseStore/setIsShowBar", true)
    })
  },
  created() {
    /** t_banner 정보 */
    this.uploadModel = {
      bannerCode: 1,
      title: "겔럭시 20 울트라",
      subTitle: "5G 걸맞은 몬스터 스펙 1억800만 화소 초고화질 무장",
      /** 0: 모바일 1: 1Frame 사이니지(9:16) 2: 1Frame 사이니지(16:9) 6: 6Frame 사이니지 */
      regType: 6,
      /** 재생유형(t_common : P002 1:이미지 2:동영상) 값이 0이면 이미지/동영상 업로드 */
      playType: 1,
      /**  이미지베너 URL */
      bannerImageUrl: "202006/11e04299-4381-4b2a-87b0-6448955d9196.jpg",
      //bannerImageUrl: "202006/1f25706c-b83e-4e4b-a569-8fb65231ee9d.jpg",
      /** 썸네일 URL */
      thumbImageUrl: "202006/thumb_11e04299-4381-4b2a-87b0-6448955d9196.jpg",
      //thumbImageUrl: "202006/thumb_1f25706c-b83e-4e4b-a569-8fb65231ee9d.jpg",
      /** 동영상 URL */
      bannerMovieUrl: "",

      /**
       * t_banner_sub 테이블 정보
       */
      content: "상세설명입니다. 상세설명입니다.상세설명입니다.상세설명입니다.",
      bannerList: [
        {
          /** 기본키(자동순번) */
          subIdx: 1,
          /** 배너테이블의 기본키(t_banner_sub) */
          bannerCode: 1,
          /** 정렬순번 */
          seq: 1,
          /** 재생유형(t_common : P002 1:이미지 2:동영상) 값이 0이면 이미지/동영상 업로드*/
          playType: 2,
          /**  이미지베너 URL */
          bannerImageUrl:
            "202006/db40030c-ef28-43f1-b37b-32d52bec4551_takeP.png",
          //bannerImageUrl: "202006/1f25706c-b83e-4e4b-a569-8fb65231ee9d.jpg",
          /** 썸네일 URL */
          thumbImageUrl:
            "202006/db40030c-ef28-43f1-b37b-32d52bec4551_takeP.png",
          //thumbImageUrl:"202006/thumb_1f25706c-b83e-4e4b-a569-8fb65231ee9d.jpg",

          content: "나아키 상세설명입니다.",
        },
        {
          /** 기본키(자동순번) */
          subIdx: 2,
          /** 배너테이블의 기본키(t_banner) */
          bannerCode: 1,
          /** 정렬순번 */
          seq: 2,
          /** 재생유형(t_common : P002 1:이미지 2:동영상) 값이 0이면 이미지/동영상 업로드*/
          playType: 1,
          /**  이미지베너 URL */
          bannerImageUrl: "202006/57662ee0-8c73-4395-913d-d93210bf6c42.jpg",
          //bannerImageUrl: "202005/2e305204-678e-4a52-b3b0-29a5f0888ccd.png",
          /** 썸네일 URL */
          thumbImageUrl:
            "202006/thumb_57662ee0-8c73-4395-913d-d93210bf6c42.jpg",
          //thumbImageUrl: "202005/2e305204-678e-4a52-b3b0-29a5f0888ccd.png",
          content: "상세설명이 필요합니다222.",
        },
        {
          /** 기본키(자동순번) */
          subIdx: 3,
          /** 배너테이블의 기본키(t_banner) */
          bannerCode: 1,
          /** 정렬순번 */
          seq: 3,
          /** 재생유형(t_common : P002 1:이미지 2:동영상) 값이 0이면 이미지/동영상 업로드*/
          playType: 1,
          /**  이미지베너 URL */
          bannerImageUrl: "202006/57662ee0-8c73-4395-913d-d93210bf6c42.jpg",
          //bannerImageUrl: "202005/2e305204-678e-4a52-b3b0-29a5f0888ccd.png",
          /** 썸네일 URL */
          thumbImageUrl:
            "202006/thumb_57662ee0-8c73-4395-913d-d93210bf6c42.jpg",
          //thumbImageUrl: "202005/2e305204-678e-4a52-b3b0-29a5f0888ccd.png",
          content: "상세설명이 필요합니다222.",
        },
      ],
    }
    this.uploadModel2 = {
      /** 0: 모바일 1: 1Frame 사이니지(9:16) 2: 1Frame 사이니지(16:9) 6: 6Frame 사이니지 */
      regType: 0,
      /** 재생유형(t_common : P002 1:이미지 2:동영상) 값이 0이면 이미지/동영상 업로드 */
      bannerList: [{ seq: 1 }],
    }
    this.uploadModel3 = {
      /** 0: 모바일 1: 1Frame 사이니지(9:16) 2: 1Frame 사이니지(16:9) 6: 6Frame 사이니지 */
      regType: 1,
      /** 재생유형(t_common : P002 1:이미지 2:동영상) 값이 0이면 이미지/동영상 업로드 */
      bannerList: [{ seq: 1 }],
    }
    this.uploadModel4 = {
      /** 0: 모바일 1: 1Frame 사이니지(9:16) 2: 1Frame 사이니지(16:9) 6: 6Frame 사이니지 */
      regType: 2,
      /** 재생유형(t_common : P002 1:이미지 2:동영상) 값이 0이면 이미지/동영상 업로드 */
      bannerList: [{ seq: 1 }],
    }
    this.uploadModel5 = {
      /** 0: 모바일 1: 1Frame 사이니지(9:16) 2: 1Frame 사이니지(16:9) 6: 6Frame 사이니지 */
      regType: 6,
      /** 재생유형(t_common : P002 1:이미지 2:동영상) 값이 0이면 이미지/동영상 업로드 */
      bannerList: [{ seq: 1 }],
    }
  },
  data() {
    return {
      uploadModel: null,
      uploadModel2: null,
      uploadModel3: null,
      uploadModel4: null,
      uploadModel5: null,
      rtnData: "",
      rtnData2: "",
      rtnData3: "",
      rtnData4: "",
      rtnData5: "",
    }
  },
  methods: {
    btnConfirm() {
      this.rtnData = JSON.stringify(this.uploadModel)
      console.log(JSON.stringify(this.uploadModel))
    },
    deleteBanner(res) {
      /** 모바일배너 */
      console.log("deleteBanner >>>", res)
    },
    btnConfirm2() {
      this.rtnData2 = JSON.stringify(this.uploadModel2)
      console.log(JSON.stringify(this.uploadModel2))
    },
    deleteBanner2(res) {
      /** 모바일배너 */
      console.log("deleteBanner2 >>>", res)
    },
    btnConfirm3() {
      this.rtnData3 = JSON.stringify(this.uploadModel3)
      console.log(JSON.stringify(this.uploadModel3))
    },
    deleteBanner3(res) {
      /** 모바일배너 */
      console.log("deleteBanner3 >>>", res)
    },
    btnConfirm4() {
      this.rtnData4 = JSON.stringify(this.uploadModel4)
      console.log(JSON.stringify(this.uploadModel4))
    },
    deleteBanner4(res) {
      /** 모바일배너 */
      console.log("deleteBanner4 >>>", res)
    },
    btnConfirm5() {
      this.rtnData5 = JSON.stringify(this.uploadModel5)
      console.log(JSON.stringify(this.uploadModel5))
    },
    deleteBanner5(res) {
      /** 모바일배너 */
      console.log("deleteBanner >>>", res)
    },
    btnChange169() {
      this.uploadModel3.regType = 2

      let tmp = Object.assign({}, this.uploadModel3)

      this.uploadModel3 = Object.assign({}, tmp)
    },

    infoShow(bShow) {
      console.log("infoShow", bShow)
      if (bShow)
        this.$refs.banner.querySelector(".dvInfomation").style.display = "block"
      else
        this.$refs.banner.querySelector(".dvInfomation").style.display = "none"
    },
    mobilePreview() {
      this.$alert("모바일 미리보기")
    },
  },
}
</script>
