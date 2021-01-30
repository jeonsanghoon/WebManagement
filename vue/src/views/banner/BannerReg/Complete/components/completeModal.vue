<template>
  <v-dialog v-model="dialog" persistent max-width="720">
    <v-card>
      <header>
        <bannerHeader :info="info" @close="close"></bannerHeader>
      </header>

      <content>
        <div class="py-3 py-sm-2 px-2 px-sm-12">
          <v-row class="mb-5" no-gutters>
            <v-col class="list-number" cols="1">
              <span>1.</span>
            </v-col>
            <v-col cols="11">
              <span>{{ content1 }}</span>
            </v-col>
          </v-row>

          <v-row class="mb-1" no-gutters>
            <v-col class="list-number" cols="1">
              <span>2.</span>
            </v-col>
            <v-col cols="11">
              <span>{{ content2 }}</span>
            </v-col>
          </v-row>
          <v-row class="mb-5" no-gutters>
            <v-col class="list-number" cols="1"></v-col>
            <v-col cols="11">
              <span>{{ content2_2 }}</span>
            </v-col>
          </v-row>

          <v-row class="mt-4 mb-7" no-gutters>
            <v-col cols="12" sm="7">
              <v-img
                src="@/assets/images/banner_icons/ref_img.svg"
                contain
              ></v-img>
            </v-col>
            <v-col class="d-flex align-center mt-4 mt-sm-0" cols="7" sm="5">
              <v-img
                src="@/assets/images/banner_icons/toggle_explain.svg"
                contain
                max-height="80"
              ></v-img>
            </v-col>
          </v-row>

          <v-row class="mb-1" no-gutters>
            <v-col class="list-number" cols="1">
              <span>3.</span>
            </v-col>
            <v-col cols="11">
              <span>{{ content3 }}</span>
            </v-col>
          </v-row>
          <v-row class="mb-5" no-gutters>
            <v-col class="list-number" cols="1"></v-col>
            <v-col cols="11">
              <span>{{ content3_2 }}</span>
            </v-col>
          </v-row>
        </div>
      </content>
    </v-card>
  </v-dialog>
</template>

<script>
import bannerHeader from '../../../components/bannerHeader'

export default {
  components: {
    bannerHeader,
  },
  props: {
    dialog: {
      type: Boolean,
      required: true,
      default: false,
    },
  },
  data() {
    return {
      info: {
        class: `py-4`,
        classTitle: `pl-sm-4`,
        title: `배너 광고 서비스 이용 안내`,
        styleTitle: `font-size: 0.929rem; font-weight: 700;`,
        closeIcon: 'pr-sm-4 fas fa-times',
        classCloseIcon: `ml-auto`,
        divider: true,
      },

      content1: `[배너 보관함]으로 이동해주세요.`,
      content2: `[배너보관함]에 보관되어 있는 배너는 서비스 ‘비활성화’된 상태가 기본 설정값입니다.`,
      content2_2: `외부에 게시가 필요한 경우 토글 버튼을 클릭하여 서비스를 ‘활성화’상태로 전환해주세요.`,
      content3: `서비스가 활성화되어 있는 배너는 ‘미디어 작업실’ 로 이동합니다.`,
      content3_2: `미디어 작업실에서 배너를 게시하셔야 광고효과가 발생합니다.`,
    }
  },
  methods: {
    close() {
      this.$emit('update:dialog', false)
    },
  },
}
</script>

<style lang="scss" scoped>
span {
  font-weight: normal;
  font-size: 0.875rem;
}

.list-number {
  max-width: 1.3rem;
}
</style>
