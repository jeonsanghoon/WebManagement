<template>
  <v-container class="card-background">
    <v-row no-gutters>
      <v-col :class="info[0].class" :cols="info[0].cols">
        <!-- 체크 -->
        <v-col
          :class="`col-wrap ${info[1].class}`"
          :cols="info[1].cols"
          :style="info[1].style"
        >
          <div @click.capture="clickCheckBox">
            <v-checkbox
              v-model="banner.check"
              class="ma-0"
              color="#3E2723"
              hide-details
              dense
            />
          </div>
        </v-col>

        <!-- No -->
        <v-col
          :class="`col-wrap ${info[2].class}`"
          :cols="info[2].cols"
          :style="info[2].style"
        >
          <span class="card-no">{{ banner.rowNum }}</span>
        </v-col>

        <!-- 배너명 -->
        <v-col
          :class="`col-wrap ${info[3].class}`"
          :cols="info[3].cols"
          :style="info[3].style"
        >
          <v-row no-gutters>
            <v-col
              class="d-flex align-center justify-start pl-2 pl-sm-0 mr-lg-n2"
              cols="2"
            >
              <div class="card-img-box">
                <v-img
                  class="card-banner-thumbnail-zoom"
                  v-if="checkNull(banner.thumbImageUrl) === false"
                  contain
                  width="38"
                  height="38"
                  :src="`/files/${banner.thumbImageUrl}`"
                >
                </v-img>
              </div>
            </v-col>

            <v-col
              ref="refTitle"
              class="card-title-box text-start pl-3 pl-sm-2"
              cols="10"
            >
              <v-row no-gutters>
                <v-col class="d-flex" cols="12">
                  <p class="text-truncate ondaji_font_14_bold_grey_100">
                    {{ title }}
                  </p>

                  <div class="card-device-type-icon d-flex">
                    <v-img
                      contain
                      width="17"
                      height="17"
                      :src="require(`@/assets/images/icons/${bannerIconImg}`)"
                    >
                    </v-img>
                  </div>
                  <div
                    v-if="banner.playType === 2"
                    class="card-banner-play-type d-flex"
                  >
                    <v-img
                      contain
                      height="17"
                      src="@/assets/images/icons/movie_table.svg"
                    >
                    </v-img>
                  </div>
                  <div
                    v-if="banner.bannerType === 2"
                    class="card-banner-type-icon d-flex"
                  >
                    <v-img
                      contain
                      width="17"
                      height="17"
                      src="@/assets/images/icons/paid.svg"
                    >
                    </v-img>
                  </div>
                </v-col>
              </v-row>
              <v-row no-gutters>
                <v-col class="ondaji_font_12_medium_grey_60" cols="12">
                  <p class="text-truncate card-sub">{{ subTitle }}</p>
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-col>

        <!-- 광고현황 -->
        <v-col
          :class="`col-wrap ${info[4].class}`"
          :cols="info[4].cols"
          :style="info[4].style"
        >
          <!-- <div class="service-wrap ondaji_font_10_medium_grey_60">지역광고</div> -->
        </v-col>

        <!-- 공유 -->
        <v-col
          :class="`col-wrap ${info[5].class}`"
          :cols="info[5].cols"
          :style="info[5].style"
        >
          <span class="card-date">0</span>
        </v-col>

        <!-- 광고요청 -->
        <v-col
          :class="`col-wrap ${info[6].class}`"
          :cols="info[6].cols"
          :style="info[6].style"
        >
          <div
            class="service-req-wrap ondaji_font_12_bold_white"
            @click="clickShare"
          >
            요청하기
          </div>
        </v-col>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { commonMediaShop } from "@/views/mediaShop/mixins/commonMixins"

export default {
  mixins: [commonMediaShop],
  components: {},

  props: {
    info: {
      type: Array,
      required: true,
      default: null,
    },
    banner: {
      type: Object,
      required: false,
    },
  },
  data() {
    return {
      title: ``,
      subTitle: ``,
    }
  },
  mounted() {
    this.initInfo()
  },
  computed: {
    bannerIconImg() {
      const type = this.banner.deviceType2

      if (type === 1) {
        return `s1.svg`
      } else if (type === 2) {
        return `s6.svg`
      } else {
        return `m.svg`
      }
    },
    insertDate() {
      return this.$moment(this.banner.insertDate).format("YYYY-MM-DD")
    },
  },
  watch: {
    banner() {
      this.initInfo()
    },
  },
  methods: {
    clickCheckBox() {
      const checkInfo = {
        index: this.banner.index,
        check: this.banner.check,
      }

      this.$emit("bannerCheck", checkInfo)
    },
    initInfo() {
      const titleLen = (this.$refs.refTitle.clientWidth / 100) * 5
      const subTitleLen = (this.$refs.refTitle.clientWidth / 100) * 9

      // 텍스트 뒤 아이콘때문에 따로 계산
      this.title = this.initText(this.banner.title, titleLen)
      this.subTitle = this.initText(this.banner.subTitle, subTitleLen)
    },
    initText(val, len) {
      let text = val

      if (this.getScreenSize() != "lg") {
        if (this.checkNull(text) === false && text.length > len) {
          text = text.substring(0, len)
          text = `${text}...`
        }
      }

      return text
    },
    clickShare() {
      this.$emit("clickShare", this.banner)
    },
  },
}
</script>

<style lang="scss" scoped>
p {
  padding: 0;
  margin: 0;
}
.card-background {
  padding: 0rem;
  background-color: white;
}

.card-img-box {
  height: 38px;
  width: 38px;
  border-radius: 2px;
  background-color: #e0e0e0;
}

.card-banner-thumbnail-zoom {
  transition: transform 0.2s;
  margin: 0 auto;
  &:hover {
    transform: scale(5) !important;
    z-index: 1;
  }
}

.card-banner-play-type {
  top: -2.45rem;
  right: 0.7rem;
}

.card-no {
  font-weight: bold;
  font-size: 0.75rem;
}

.card-title-box {
  height: 2.688rem;
  min-width: 70px;
}

.card-title {
  font-size: 0.875rem;
  color: black;
}

.card-device-type-icon {
  padding-top: 0.1rem;
  padding-left: 0.5rem;
}

.card-banner-type-icon {
  padding-top: 0.05rem;
  padding-left: 0.2rem;
}

.card-sub-box {
  padding-top: 0.1rem;
}

.card-sub {
  font-weight: 400;
  font-size: 0.75rem;
}

.card-date {
  font-weight: 500;
  font-size: 0.75rem;
}

.service-wrap {
  background: #e0e0e0;
  width: 50px;
  height: 18px;
  border-radius: 3px;
  line-height: 20px;
}

.service-req-wrap {
  background: #00c853;
  width: 58px;
  height: 20px;
  border-radius: 3px;
  line-height: 21px;
  cursor: pointer;
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .col-wrap {
    height: 59.95px;
  }
}
</style>
