<template>
  <div class="bannerComponent">
    <v-card outlined class="mx-auto text-left" @click="bannerClick">
      <v-img
        v-if="data.thumbImageUrl !== ''"
        :src="cPicture(data.thumbImageUrl)"
        @error="onImgError"
        color="#e7e7e7"
        class="card-image"
        contain
      >
        <div
          v-if="data.searchCnt > 0"
          class="imgIcon ondaji_font_12_medium_grey_70 search"
        >
          <div>
            <v-img
              class="viewIcon"
              src="@/assets/images/user/views.svg"
            ></v-img>
          </div>
          <div style="margin-left:2px;">{{ data.searchCnt }}</div>
        </div>
        <div class="imgIcon ondaji_color_tealgreen_40 bookmark">
          <v-icon color="white">fas fa-bookmark</v-icon>
        </div>

        <div class="imgIcon ondaji_font_14_bold_brown_80 ad">
          AD
        </div>
      </v-img>

      <v-container class="pt-2">
        <v-row class="card-title ondaji_font_24_bold_grey_100">
          <v-col cols="12" class="pa-0 textWrap ">
            {{ data.title }}
          </v-col>
        </v-row>
        <v-row class="card-subtitle ondaji_font_16_thin_grey_80 ">
          <v-col cols="12" class="px-0 py-0 mt-1 mb-1   textWrap_sub">
            {{ data.subTitle }}
          </v-col>
        </v-row>
        <v-row>
          <v-col
            cols="12"
            class="px-0 pt-0 mt-0 pb-1 d-flex ondaji_font_11_medium_tealgreen_50 textWrap"
          >
            <div
              class="spArea"
              v-for="(data, index) in this.data.areaDatas"
              :key="index"
            >
              <span v-if="index > 0" class="pl-1"> &middot; </span
              >{{ data.name }}
            </div>
          </v-col>
          <v-col cols="12" class="pa-0 pt-4 etc ondaji_font_14_thin_grey_60">
            {{ data.memberName }} <span class="px-1">|</span>
            {{ data.diffMinName }}
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </div>
</template>

<style lang="scss" scoped>
.imgIcon {
  height: 20px;
  position: absolute;
  z-index: 1;

  background: #ffffff;
  padding-top: 1.5px;
  padding-left: 4px;
  padding-right: 6px;

  border-radius: 1px;

  margin: 0px;
  opacity: 0.8;

  &.search {
    left: 10px;
    top: 10px;
    .v-image {
      vertical-align: middle;
    }
    .viewIcon {
      height: 13px;
    }
    div {
      display: inline-block;
    }
  }
  &.ad {
    right: 10px;
    top: 10px;
    width: 30px;
    height: 20px;
    padding: 0px;
    text-align: center;
    background-color: #ffffff;
    opacity: 0.8;
  }
  &.bookmark {
    right: 10px;
    bottom: 10px;
    width: 52px;
    height: 52px;
    border-radius: 3px;

    border-radius: 3px;
    color: #ffffff !important;
    .v-icon {
      margin: auto;
      margin-left: 30%;
      margin-top: 22%;
      font-size: 26px;
    }
  }
}

.bannerComponent {
  max-width: 100%;
  max-height: 100%;
  cursor: pointer;
  .textWrap {
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    word-break: break-all;
    word-wrap: break-word;

    padding-right: 0px;

    &_sub {
      @extend .textWrap;
    }
  }
  .card-image {
    width: inherit;
    background-color: #e7e7e7;
  }
  .v-card {
    border: 0px;
    background-color: transparent;
    .imageData {
      text-align: right;
      background-color: rgba($color: #000000, $alpha: 0.4);
      position: absolute;

      bottom: 0px;
      width: 100%;
      padding-right: 5px;
    }
    .card {
      &-image {
        border: solid 1px #c4c4c4;
        position: relative;
      }
    }
    .spArea {
      margin-right: 0rem;
      overflow: hidden;
      text-overflow: ellipsis;
      display: block;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      font-size: 1.2vh;
      max-width: 6rem;
    }
    .etc {
      font-size: 1.2vh;
    }
  }
}

$mobile_image: 10.125rem;
.bannerComponent {
  width: $mobile_image;
  .card-image {
    height: $mobile_image;
    width: $mobile_image;
  }
  .textWrap {
    font-size: 1rem;
    &_sub {
      font-size: 0.75rem;
    }
  }
  .spArea,
  .etc {
    font-size: 0.625rem !important;
  }

  .imgIcon {
    border-radius: 1px;
    &.search {
      left: 6px;
      top: 6px;
    }
    &.ad {
      font-size: 12px !important;
      color: #757575 !important;
      font-weight: 500 !important;
      right: 6px;
      top: 6px;
      height: 18px;
      width: 24px;
    }
    &.bookmark {
      right: 6px;
      bottom: 6px;
      border-radius: 3px !important;
      width: 40px;
      height: 40px;

      font-size: 10px !important;
      .v-icon {
        font-size: 20px !important;
      }
    }
  }
}
</style>
<script>
export default {
  props: {
    value: {
      type: Object,
    },
  },
  created() {
    if (!(this.value === null || this.value === undefined))
      this.data = Object.assign({}, this.value)

    this.setArea(this.value.areaData)
    this.data.diffMinName = this.$BaseCommon.dateInfo.getDateDiffString(
      this.value.diffMin
    )
    // console.log("테스트요 >> ", this.data, this.value.areaData)
  },
  data() {
    return {
      data: {
        bannerCode: 68,
        title:
          "겔럭시 S20  할인 판매 겔럭시 S20  할인 판매겔럭시 S20  할인 판매겔럭시 S20  할인 판매",
        subTitle:
          "오직 빌리브폰에서  갤럭시 S20 93프로 할인 시작 오직 빌리브폰에서  갤럭시 S20 93프로 할인 시작",
        // ,content:"온라인 최대 역대 할인 찬스  "
        bannerType: 2,
        thumbImageUrl: "https://cdn.vuetifyjs.com/images/cards/docks.jpg",
        searchCnt: 0,
        diffMin: 5779,
        areaData:
          "01|01|22-역삼1동-90,01|01|10-대치4동-82,01|01|16-삼성2동-68,01|01|06-논현2동-24,01|01|16-삼성2동-9,01|01|23-역삼2동-8,01|01|22-역삼1동-6,01|01|23-역삼2동-3,01|01|09-대치2동-3,01|01|22-역삼1동-0,01|01|06-논현2동-0,01|01|05-논현1동-0",
        areaDatas: [],
        areaDisplay: "",
        maxGate: 37,
        localboxName: "로컬박스명",
        profileUrl: "",
      },
      failedImage: false,
    }
  },
  methods: {
    setArea(areaData) {
      if (areaData === undefined || areaData === null) return
      this.data.areaDatas = []
      let arrArea = areaData.split(",")
      arrArea.some((element, index) => {
        let arrData = element.split("-")
        let data = {
          code: arrData[0],
          name: arrData[1],
          gage: arrData[2],
        }
        this.data.areaDatas.push(data)
        if (index === 2) return true
      })
    },
    cPicture: function(imageUrl) {
      return this.failedImage ? "http://ondaji.kro.kr" + imageUrl : imageUrl
    },

    onImgError: function(event) {
      console.log(event)
      this.failedImage = true
      //this.$forceUpdate();
    },
    bannerClick() {
      this.$router
        .push({ path: "/user/bannerdetail/" + this.data.bannerCode })
        .catch((err) => {
          console.log(err)
          this.$route.params.bannerCode = this.data.bannerCode
          this.$EventBus.$emit("bannerDetailInit", this.data.bannerCode)
        })
    },
  },
}
</script>
