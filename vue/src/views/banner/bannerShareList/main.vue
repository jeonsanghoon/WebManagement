<template>
  <v-container class="align-start justify-start" fluid>
    <v-row class="px-2 px-lg-0" no-gutters>
      <v-col class="pt-3 pb-9 pr-0" cols="12">
        <Vuetify-menutitle />
      </v-col>
      <v-col class="pa-0" cols="12">
        <!-- 공유배너 타입 선택 -->
        <shareListSection @selectSection="selectSection" />
      </v-col>
      <v-col class="pa-0" cols="12">
        <!-- 테이블 -->
        <tableMain :info="info" />
      </v-col>
    </v-row>

    <Vuetify-progressModal :dialog.sync="progressDialog">
      검색중...
    </Vuetify-progressModal>
  </v-container>
</template>

<script>
import { commonBannerShareList } from '@/views/banner/mixins/commonBannerShareListMixins'

import shareListSection from './bannerShareListSection'
import tableMain from './table/bannerShareListTableMain'

export default {
  mixins: [commonBannerShareList],
  components: {
    shareListSection,
    tableMain,
  },
  data() {
    return {
      info: {},
      progressDialog: false,
    }
  },
  created() {
    this.info.section = this.SECTION_TYPE.SECTION_REQ
  },
  methods: {
    searchLoding(load) {
      this.progressDialog = load
    },
    search(info) {
      this.searchInfo = info
    },
    selectSection(section) {
      this.info = { ...this.info, section }
    },
  },
}
</script>

<style lang="scss" scoped></style>
