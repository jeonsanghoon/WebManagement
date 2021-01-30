<template>
  <v-container class="pa-0" fluid>
    <v-row no-gutters>
      <v-col cols="12">
        <v-row class="notice-info px-2 pl-lg-2 pr-lg-2" no-gutters>
          <!-- 주의 사항 -->
          <v-menu open-on-hover bottom offset-y>
            <template v-slot:activator="{ on }">
              <v-col
                class="d-flex align-end pb-4"
                cols="3"
                sm="2"
                lg="3"
                v-on="on"
                style="max-width: 6rem;"
              >
                <span class="span-info">주의사항</span>
                <v-img
                  class="ml-2"
                  src="@/assets/images/banner_icons/banner_info.svg"
                  max-width="15"
                  max-height="15"
                  style="margin-bottom: 0.15rem;"
                ></v-img>
              </v-col>
            </template>

            <!-- 주의 사항 툴팁 -->
            <v-col class="pa-0" cols="12">
              <bannerMakeNotice :deviceType="deviceType"></bannerMakeNotice>
            </v-col>
          </v-menu>

          <!-- 모바일 미리보기 버튼 -->
          <bannerBtn :info="btnPreview.info" @click:button="openPreviewModal">
            {{ btnPreview.content }}
          </bannerBtn>
        </v-row>

        <!-- 모바일 업로드 -->
        <v-row no-gutters>
          <v-col cols="12">
            <bannerMakeUpload
              ref="upload"
              :deviceType="deviceType"
              :selectBanner="selectBanner"
            ></bannerMakeUpload>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
    <!-- 모바일 미리보기 모달 -->
    <v-row justify="center" no-gutters>
      <bannerPreviewModal
        :deviceType="deviceType"
        :dialog.sync="dialog"
      ></bannerPreviewModal>
    </v-row>
  </v-container>
</template>

<script>
import { componentsBanner } from '../../../mixins/componentsMixins'

import bannerMakeNotice from './components/notice'
import bannerBtn from '../../../components/bannerBtn'
import bannerPreviewModal from './components/previewModal'
import bannerMakeUpload from './upload'

export default {
  mixins: [componentsBanner],
  components: {
    bannerMakeNotice,
    bannerBtn,
    bannerPreviewModal,
    bannerMakeUpload,
  },
  props: {
    deviceType: {
      type: Number,
      required: true,
      default: 0,
    },
    selectBanner: {
      type: Object,
      required: false,
      default: null,
    },
  },
  data() {
    return {
      TypeInfo() {
        this.info = {
          class: ``,
        }
      },

      btnPreview: {},

      dialog: false,
    }
  },
  created() {
    let type = new this.TypeInfo()

    type.info.class = `brown-btn ml-auto`
    type.info.width = `9.0rem`
    type.info.height = `2.0rem`

    type.content = `모바일 미리보기`

    this.btnPreview = type
  },
  methods: {
    getInfo() {
      return this.$refs.upload.getUploadModel()
    },
    openPreviewModal() {
      this.dialog = !this.dialog

      // 모바일 미리보기 버튼을 누르면
      // 메인 배너 정보와 배너 업로드 쪽 정보 2가지를 로드
      this.$EventBus.$emit(`getBannerInfo`)
      this.$EventBus.$emit(
        `getSubBannerInfo`,
        this.$refs.upload.getUploadModel()
      )
    },
  },
}
</script>

<style lang="scss" scoped>
.notice-info {
  position: relative;
  top: 2.7rem;
  z-index: 1;
}

.span-info {
  font-weight: bold;
  font-size: 0.714rem;
  color: #887d7a;
  padding-bottom: 0.15rem;
}
</style>
