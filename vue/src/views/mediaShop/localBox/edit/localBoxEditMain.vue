<template>
  <v-container class="align-start justify-start" fluid>
    <v-row class="px-2 px-lg-0" no-gutters>
      <v-col class="pt-3 pb-9 pr-0" cols="12">
        <Vuetify-menutitle />
      </v-col>
      <v-col cols="12">
        <!-- 프로필 -->
        <headerMain />

        <!-- 검색창 -->
        <searchMain @search="search" />

        <!-- 테이블 -->
        <tableMain :searchInfo="searchInfo" @searchLoding="searchLoding" />
      </v-col>
    </v-row>

    <progressModal :dialog.sync="progressDialog">
      검색중...
    </progressModal>
  </v-container>
</template>

<script>
import { commonMediaShop } from "@/views/mediaShop/mixins/commonMixins"

import headerMain from "./header/localBoxEditHeaderMain"
import searchMain from "./body/search/localBoxEditSearchMain"
import tableMain from "./body/table/localBoxEditTableMain"
import progressModal from "@/components/vuetify/progressModal"

export default {
  mixins: [commonMediaShop],
  components: {
    headerMain,
    searchMain,
    tableMain,
    progressModal,
  },
  data() {
    return {
      searchInfo: {},
      progressDialog: true,
    }
  },
  created() {
    const localboxCode = this.$BaseCommon.memberInfo.getMember().member
      .localboxCode

    if (this.checkNull(localboxCode) === true) {
      const params = this.$route.params

      if ((Object.keys(params).length === 0) === true) {
        this.reqLocalBoxCode()
      }
    }
  },
  methods: {
    searchLoding(load) {
      this.progressDialog = load
    },
    search(info) {
      this.searchInfo = info
    },
  },
}
</script>
