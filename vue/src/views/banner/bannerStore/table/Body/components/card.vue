<template>
  <v-container class="card-background" @click="selectBanner">
    <v-row no-gutters>
      <v-col :class="info[0].class" :cols="info[0].cols">
        <!-- No -->
        <v-col :class="`col-wrap ${info[1].class}`" :cols="info[1].cols">
          <span class="card-no">{{ banner.rowNum }}</span>
        </v-col>

        <!-- Title -->
        <v-col
          :class="`col-wrap ${info[2].class}`"
          :cols="info[2].cols"
          :style="info[2].style"
        >
          <v-row no-gutters>
            <v-col
              class="d-flex align-center justify-start pl-2 pl-sm-0"
              cols="2"
              lg="1"
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

                <v-img
                  v-if="banner.playType === 2"
                  class="card-banner-thumbnail"
                  contain
                  height="17"
                  src="@/assets/images/banner_icons/movie.svg"
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
                <v-col class="d-flex text-truncate" cols="12">
                  <span class="card-title font-weight-bold">{{ title }}</span>
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
                <v-col class="card-sub-box text-truncate" cols="12">
                  <span class="card-sub">{{ subTitle }}</span>
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-col>

        <!-- Date -->
        <v-col :class="`col-wrap ${info[3].class}`" :cols="info[3].cols">
          <span class="card-date">{{ insertDate }}</span>
        </v-col>

        <!-- Service -->
        <v-col
          :class="`col-wrap ${info[4].class}`"
          :cols="info[4].cols"
          :style="info[4].style"
        >
          <bannerSwitch
            :info="switchInfo"
            @click:switch="reqService"
          ></bannerSwitch>
        </v-col>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { commonBanner } from "../../../../mixins/commonMixins"

import bannerSwitch from "../../../../components/bannerSwitch"

export default {
  mixins: [commonBanner],
  components: {
    bannerSwitch,
  },

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

      switchInfo: {
        switchBtn: true,
        color: `#00C853`,
        readonly: false,
      },

      changeSwitch: false,
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
    selectBanner() {
      // 스위치를 클릭한 상태
      if (this.changeSwitch === true) {
        return
      }

      this.$BaseCommon.restful
        .call2("/banner/getbanner", this.banner.bannerCode)
        .then((res) => {
          if (this.checkNull(res) === false) {
            this.$router.push({
              name: "registerbanner",
              params: { mainBannerInfo: this.banner, subBannerInfo: res },
            })
          }
        })
        .catch((res) => {
          console.log("/banner/getbanner >> ", res)
        })
    },
    initInfo() {
      const titleLen = (this.$refs.refTitle.clientWidth / 100) * 5
      const subTitleLen = (this.$refs.refTitle.clientWidth / 100) * 9

      this.title = this.initText(this.banner.title, titleLen)
      this.subTitle = this.initText(this.banner.subTitle, subTitleLen)

      this.switchInfo.switchBtn = this.banner.status != 99 ? false : true
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
    reqService() {
      this.changeSwitch = true

      this.switchInfo.readonly = true

      let formData = {}

      formData.bannerCode = this.banner.bannerCode

      if (this.switchInfo.switchBtn === true) {
        formData.status = 99
      } else {
        formData.status = 9
      }

      this.$BaseCommon.restful
        .call2("/banner/savebannerstatus", formData)
        .then((res) => {
          if (this.checkRes(res) === true) {
            setTimeout(() => {
              this.switchInfo.readonly = false
              this.changeSwitch = false
            }, 500)
          }
        })
        .catch((res) => {
          console.log("/banner/savebannerstatus >> ", res)
        })
    },
  },
}
</script>

<style lang="scss" scoped>
.card-background {
  padding: 0rem;
  background-color: white;
  cursor: pointer;
  &:hover {
    background-color: #e0f8e2;
  }
}

.card-img-box {
  height: 2.375rem;
  width: 2.375rem;
  background-color: #ededed;
}

.card-banner-thumbnail-zoom {
  transition: transform 0.2s;
  margin: 0 auto;
  &:hover {
    transform: scale(5) !important;
    z-index: 1;
  }
}

.card-banner-thumbnail {
  top: -2.45rem;
  right: 0.7rem;
}

.card-no {
  font-weight: bold;
  font-size: 0.75rem;
}

.card-title-box {
  height: 2.688rem;
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
  padding-top: 0.13rem;
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

@media screen and (max-width: $grid-breakpoints_sm) {
  .col-wrap {
    height: 60px;
  }
}
</style>
