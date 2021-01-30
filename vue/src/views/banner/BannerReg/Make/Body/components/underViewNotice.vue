<template>
  <v-container class="under-view-background pa-0 text-start">
    <v-row class="pa-2 pa-sm-5" no-gutters>
      <v-col cols="12">
        <v-row class="pa-0" no-gutters>
          <v-col cols="12">
            <div class="notice-title">{{ text.title }}</div>
          </v-col>

          <v-row class="pa-0" no-gutters>
            <v-col class="pt-3" cols="12">
              <v-row class="pa-0" no-gutters>
                <v-col cols="12">
                  <span class="notice-sub-title">
                    ·
                  </span>
                  <span class="notice-sub-title">
                    {{ text.localBox }}
                  </span>
                </v-col>
              </v-row>
              <v-row class="pb-1 mt-1" no-gutters style="margin-left: 0.39rem;">
                <v-col cols="12">
                  <span class="notice-content">
                    {{ text.localBoxGuide }}
                  </span>
                </v-col>
              </v-row>
            </v-col>
          </v-row>

          <v-row class="pa-0" no-gutters>
            <v-col class="pt-3" cols="12">
              <v-row class="pa-0" no-gutters>
                <v-col cols="12">
                  <span class="notice-sub-title">
                    ·
                  </span>
                  <span class="notice-sub-title">
                    {{ text.localBanner }}
                  </span>
                </v-col>
              </v-row>
              <v-row class="mt-1 pa-0" no-gutters style="margin-left: 0.39rem;">
                <v-col cols="12">
                  <span class="notice-content">
                    {{ text.localBannerGuide }}
                  </span>
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      text: {
        title: `모바일 배너  상세 페이지 하단에 구성될 배너를 선택해주세요.`,

        localBox: `로컬박스`,
        localBoxGuide: `로컬박스에 게시한 ‘당신의’ 배너들로 구성됩니다.`,

        localBanner: `지역광고`,
        localBannerGuide: `‘다른 로컬사업자’의 ‘광고배너’로 구성됩니다.`,
      },
    }
  },
}
</script>

<style lang="scss" scoped>
div {
  font-weight: normal;
  font-size: 0.714rem;
  color: white;
}

.under-view-background {
  background-color: #757575;
  max-width: 26rem;
  border-radius: 0.3rem;
}

.notice-title {
  font-weight: bold;
  font-size: 0.750rem;
}

.notice-sub-title {
  font-weight: bold;
  padding-top: 0.750rem;
}

.notice-content {
  font-weight: normal;
  font-size: 0.750rem;
}
</style>
