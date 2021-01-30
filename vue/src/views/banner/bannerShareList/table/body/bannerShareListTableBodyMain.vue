<template>
  <v-container class="pa-0">
    <v-row justify="center" no-gutters>
      <v-col cols="12" lg="8">
        <tableMenu @searchType="searchType" />
      </v-col>

      <template v-if="getScreenSize() !== 'xs'">
        <v-col cols="12" lg="8">
          <tableCategory />
        </v-col>

        <v-col cols="12" lg="8">
          <div v-for="(banner, i) in bannerList" :key="i">
            {{ banner.rowNum }}
            {{ banner.title }}
            {{ banner.subTitle }}
            {{ banner.reqStatus }}
            {{ $moment(banner.reqDate).format("YYYY-MM-DD") }}
          </div>

          <!-- <tableBody :bannerList="bannerList" @bannerCheck="bannerCheck" /> -->
        </v-col>
      </template>
      <template v-else>
        <template v-if="bannerList.length !== 0">
          <v-col cols="2">
            <tableCategory :len="bannerList.length" />
          </v-col>

          <v-col cols="10">
            <div v-for="(banner, i) in bannerList" :key="i">
              {{ banner.rowNum }}
              {{ banner.title }}
              {{ banner.subTitle }}
              {{ banner.reqStatus }}
              {{ banner.reqDate }}
            </div>
            <!-- <tableBody :bannerList="bannerList" @bannerCheck="bannerCheck" /> -->
          </v-col>
        </template>
        <template v-else>
          <v-col cols="12">
            <div v-for="(banner, i) in bannerList" :key="i">
              {{ banner.rowNum }}
              {{ banner.title }}
              {{ banner.subTitle }}
              {{ banner.reqStatus }}
              {{ banner.reqDate }}
            </div>
            <!-- <tableBody :bannerList="bannerList" @bannerCheck="bannerCheck" /> -->
          </v-col>
        </template>
      </template>
    </v-row>
  </v-container>
</template>

<script>
import { commonBanner } from "@/views/banner/mixins/commonMixins"

import tableMenu from "./bannerShareListTableBodyMenu"
import tableCategory from "./bannerShareListTableBodyCategory"

export default {
  mixins: [commonBanner],
  components: {
    tableMenu,
    tableCategory,
  },
  props: {
    searchInfo: {
      type: Object,
      required: false,
    },
    bannerList: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      headerInfo: {},
      totalNum: 0,
      change: false,
      checkUpload: false,
    }
  },
  methods: {
    searchType(typeInfoArr, searchText) {
      this.$emit("searchType", typeInfoArr, searchText)
    },
  },
}
</script>

<style lang="scss" scoped>
@media screen and (min-width: $grid-breakpoints_lg) {
  .help-btn-wrap {
    position: absolute;
    bottom: 30px;
    right: 30px;
  }
}
@media screen and (min-width: $grid-breakpoints_sm) {
  .footer-wrap {
    margin-bottom: 50px;
  }
  .help-btn-wrap {
    position: absolute;
    bottom: 15px;
    right: 15px;
  }
}
@media screen and (max-width: $grid-breakpoints_sm) {
  .table-wrap {
    border-top: 1px solid #e2d9d2;
  }
  .footer-wrap {
    margin-bottom: 30px;
  }
  .help-btn-wrap {
    position: absolute;
    bottom: 4px;
    right: 10px;
  }
}
</style>
