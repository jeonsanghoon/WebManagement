<template>
  <v-container class="pa-0 text-start" fluid style="background-color: #757575;">
    <v-row class="pb-0" no-gutters>
      <v-col cols="12">
        <template v-if="deviceType != 2">
          <v-row no-gutters style="background-color: #616161;">
            <v-col cols="12">
              <div class="notice-title pt-4 px-4">{{ text.title }}</div>
            </v-col>
          </v-row>

          <div class="pt-4 px-4">
            <v-row no-gutters>
              <v-col cols="12">
                <span class="device-type">{{ text.signage }}</span>
              </v-col>
            </v-row>

            <v-row no-gutters>
              <v-col class="mr-lg-2" cols="3" sm="2" lg="1">
                <span class="file-type">{{ text.image }}</span>
              </v-col>

              <v-col
                class="file-content ml-sm-n4 ml-lg-0"
                cols="9"
                sm="4"
                lg="5"
              >
                <v-row class="pb-1" no-gutters>
                  {{ text.imageInfo }}
                </v-row>
                <v-row class="pb-1" no-gutters>
                  <template v-if="deviceType != 1">
                    <span v-if="signageType === 0">{{
                      text.sizeInfoWide
                    }}</span>
                    <span v-else>{{ text.sizeInfoLong }}</span>
                  </template>
                  <template v-else>
                    <span>{{ text.sizeInfoSquare }}</span>
                  </template>
                </v-row>
              </v-col>

              <template v-if="deviceType === 0">
                <v-col class="mr-lg-2" cols="3" sm="2" lg="1">
                  <span class="file-type">{{ text.video }}</span>
                </v-col>

                <v-col
                  class="file-content ml-sm-n4 ml-lg-0"
                  cols="9"
                  sm="4"
                >
                  <v-row class="pb-1" no-gutters>
                    {{ text.videoInfo }}
                  </v-row>
                  <v-row class="pb-1" no-gutters>
                    <span v-if="signageType === 0">{{
                      text.sizeInfoWide
                    }}</span>
                    <span v-else>{{ text.sizeInfoLong }}</span>
                  </v-row>
                  <v-row class="pb-1" no-gutters>
                    {{ text.signageVideoLen }}
                  </v-row>
                </v-col>

                <v-row class="pb-1" no-gutters>
                  <v-col
                    class="pl-sm-5 pl-lg-2 pt-1"
                    cols="12"
                    offset="3"
                    offset-sm="5"
                    offset-lg="6"
                  >
                    <span class="video-notice">{{ text.videoNotice }}</span>
                  </v-col>
                </v-row>
              </template>
            </v-row>
          </div>
        </template>

        <v-divider class="mx-4" color="white"></v-divider>

        <div class="pt-4 px-4">
          <template>
            <v-row no-gutters>
              <v-col cols="12">
                <span class="device-type">{{ text.mobile }}</span>
              </v-col>
            </v-row>

            <v-row no-gutters>
              <v-col class="mr-lg-2" cols="3" sm="2" lg="1">
                <span class="file-type">{{ text.image }}</span>
              </v-col>

              <v-col
                class="file-content ml-sm-n4 ml-lg-0"
                cols="9"
                sm="4"
                lg="5"
              >
                <v-row class="pb-1" no-gutters>
                  {{ text.imageInfo }}
                </v-row>
                <v-row class="pb-1" no-gutters>
                  {{ text.sizeInfoSquare }}
                </v-row>
              </v-col>

              <template>
                <v-col class="mr-lg-2" cols="3" sm="2" lg="1">
                  <span class="file-type">{{ text.video }}</span>
                </v-col>

                <v-col
                  class="file-content ml-sm-n4 ml-lg-0"
                  cols="9"
                  sm="4"
                >
                  <v-row class="pb-1" no-gutters>
                    {{ text.videoInfo }}
                  </v-row>
                  <v-row class="pb-1" no-gutters>
                    {{ text.sizeInfoSquare }}
                  </v-row>
                  <v-row class="pb-1" no-gutters>
                    {{ text.mobileVideoLen }}
                  </v-row>
                </v-col>

                <v-row class="pb-1" no-gutters>
                  <v-col
                    class="pl-sm-5 pl-lg-2 pt-1"
                    cols="12"
                    offset="3"
                    offset-sm="5"
                    offset-lg="6"
                  >
                    <span class="video-notice">{{ text.videoNotice }}</span>
                  </v-col>
                </v-row>
              </template>
            </v-row>
          </template>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { componentsBanner } from '../../../../mixins/componentsMixins.js'

export default {
  mixins: [componentsBanner],
  props: {
    deviceType: {
      type: Number,
      required: true,
      default: 0,
    },
  },
  data() {
    return {
      text: {
        title: `*모바일 배너로 활용가능하오니 고려하여 제작하시는 것을 권장합니다.`,

        signage: `사이니지 배너`,
        mobile: `모바일 배너`,

        image: `이미지`,
        video: `동영상`,

        imageInfo: `파일 유형 - jpg 또는 png`,
        videoInfo: `파일 유형 - mp4 또는 avi`,

        sizeInfoWide: `비율 - 16:9`,
        sizeInfoLong: `비율 - 9:16`,
        sizeInfoSquare: `비율 - 1:1`,

        signageVideoLen: `최대 길이 - 15초`,
        mobileVideoLen: `최대 길이 - 1분`,

        videoNotice: `* 동영상은 1개만 업로드 가능`,
      },
      signageType: 0,
    }
  },
  created() {
    this.$EventBus.$on('selectSignageType', (select) => {
      this.signageType = select
    })
  },
  beforeDestroy() {
    this.$EventBus.$off('selectSignageType')
  },
}
</script>

<style lang="scss" scoped>
div {
  font-weight: normal;
  font-size: 0.714rem;
  color: white;
}

.footer-background {
  background-color: #F1F0F0;
}

.notice-title {
  font-weight: normal;
  font-size: 0.714rem;
}

.device-type {
  font-weight: bold;
  font-size: 0.714rem;
}

.file-type {
  font-weight: normal;
  font-size: 0.714rem;
}

.file-content {
  font-weight: normal;
  padding-top: 0.2rem;
}

.video-notice {
  font-weight: normal;
  color: #bccfff;
  font-size: 0.643rem;
}

.row {
  padding-bottom: 1rem;
}
</style>
