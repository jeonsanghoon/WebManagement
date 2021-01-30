<template>
  <div class="text-center">
    <v-dialog
      v-model="bDialog"
      stent
      persistent
      min-width="300px"
      max-width="500px"
      width="100%"
    >
      <v-card>
        <v-card-title
          class="ondaji_color_brown_80 white--text justify-center subtitle-1"
        >
          주소검색
        </v-card-title>
        <v-card-text class="pa-0">
          <div class="mapwrap">
            <vue-daum-postcode
              @complete="complete"
              :theme="this.themeObj"
              :max-suggest-items="this.max_suggest_items"
              :q="this.query"
            />
          </div>
        </v-card-text>

        <v-divider></v-divider>
        <v-card-actions class="ondaji_color_brown_80">
          <v-spacer></v-spacer>
          <v-btn
            color="gray  lighten-1"
            @click="btnConfirm"
            small
            class=" mr-3"
          >
            닫기
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>
<style scoped lang="scss">
.mapwrap {
  overflow-y: auto;
  max-height: 70vh;
}
.v-dialog > .v-card > .v-card__title {
  padding-top: 6px;
  padding-bottom: 6px;
  font-weight: 400 !important;
}
</style>
<script>
export default {
  props: {
    dialog: {
      type: Boolean,
      required: true,
    },
    name: {
      type: String,
    },
    q: {
      type: String,
    },
  },
  watch: {
    dialog: {
      handler(n, o) {
        console.log("watch >> ", n, o)
        this.bDialog = n
      },
    },
    q: {
      handler(n, o) {
        console.log("watch >> ", n, o)
        this.query = n
      },
    },
  },
  data() {
    return {
      kakaoMap_name: "kakaoMap",
      selectData: new Object(),
      bDialog: false,
      max_suggest_items: 5,
      query: "",
      themeObj: {
        bgColor: "#ECECEC", //바탕 배경색
        searchBgColor: "#FFFFFF", //검색창 배경색
        contentBgColor: "#FFFFFF", //본문 배경색(검색결과,결과없음,첫화면,검색서제스트)
        pageBgColor: "#FAFAFA", //페이지 배경색
        textColor: "#333333", //기본 글자색
        queryTextColor: "#222222", //검색창 글자색
        postcodeTextColor: "#FA4256", //우편번호 글자색
        emphTextColor: "#008BD3", //강조 글자색
        outlineColor: "#E0E0E0", //테두리
      },
    }
  },
  created() {
    this.kakaoMap_name = this.name
    this.bDialog = this.dialog
  },
  methods: {
    complete: function(res) {
      console.log("handleAddress", res, true)

      this.bDialog = false
      this.selectData = res
      // eslint-disable-next-line no-debugger
      debugger
      res.address = res.jibunAddress || res.autoJibunAddress
      res.zipCode = res.zonecode
      res.region = `${res.sido} ${res.sigungu} ${res.hname || res.bname}`
      /*let extraAddress = ""
      if (res.addressType === "R") {
        if (res.bname !== "") {
          extraAddress += res.bname
        }
        if (res.buildingName !== "") {
          extraAddress +=
            extraAddress !== "" ? `, ${res.buildingName}` : res.buildingName
        }
        fullAddress += extraAddress !== "" ? ` (${extraAddress})` : ""
      }
      console.log(fullAddress) // e.g. '서울 성동구 왕십리로2길 20 (성수동1가)'*/
      this.$emit("kakaoPostCodeCallback", res, true)
      this.bDialog = false
    },
    btnConfirm: function() {
      this.$emit("kakaoPostCodeCallback", this.selectData, false)

      this.bDialog = false
    },
  },
}
</script>
