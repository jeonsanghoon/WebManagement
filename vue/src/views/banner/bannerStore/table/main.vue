<template>
  <v-container class="pa-0">
    <!-- 데스크탑, 태블릿 -->
    <div v-if="getScreenSize() != `xs`">
      <v-row justify="center" no-gutters>
        <v-col cols="12" lg="10">
          <Table-header :info="headerInfo" />
        </v-col>
      </v-row>

      <v-row justify="center" no-gutters>
        <v-col cols="12" lg="10">
          <bannerStoreTableBody
            :bannerList="bannerList"
            :searchTitleCheck="searchTitleCheck"
          ></bannerStoreTableBody>
        </v-col>
      </v-row>
    </div>

    <!-- 모바일 -->
    <div v-else>
      <v-row no-gutters>
        <v-col v-if="bannerList.length > 0" class="d-flex" cols="12">
          <v-col class="pa-0" cols="2">
            <Table-header :info="headerInfo" :len="bannerList.length" />
          </v-col>

          <v-col class="pa-0" sm="10">
            <bannerStoreTableBody
              :bannerList="bannerList"
            ></bannerStoreTableBody>
          </v-col>
        </v-col>

        <v-col v-else class="d-flex" cols="12">
          <bannerStoreTableBody
            :bannerList="bannerList"
            :searchTitleCheck="searchTitleCheck"
          ></bannerStoreTableBody>
        </v-col>
      </v-row>
    </div>

    <v-row justify="center" no-gutters>
      <v-col cols="12" lg="10">
        <bannerStoreTableFooter
          :info="{ totalNum: totalNum, PAGE_TOTAL_NUM: PAGE_TOTAL_NUM }"
          @getPageInfo="getPageInfo"
        ></bannerStoreTableFooter>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { commonBanner } from '../../mixins/commonMixins'
import { commonBannerStore } from '../../mixins/commonBannerStoreMixins'

import bannerStoreTableBody from './Body/main'
import bannerStoreTableFooter from '@/components/table/footer'

export default {
  mixins: [commonBanner, commonBannerStore],
  components: {
    bannerStoreTableBody,
    bannerStoreTableFooter,
  },
  props: {
    bannerList: {
      type: Array,
      required: false,
    },
    totalNum: {
      type: Number,
      required: true,
      default: 0,
    },
    searchTitleCheck: {
      type: Boolean,
      required: false,
    },
  },
  data() {
    return {
      headerInfo: [],
    }
  },
  created() {
    this.initHeaderInfo()
  },
  methods: {
    initHeaderInfo() {
      for (let i = 0; i < this.MENU_NUM; i++) {
        let header = new Object()

        switch (i) {
          case 0:
            header.class = `d-flex justify-end`
            header.cols = 2
            header.title = `NO`
            break
          case 1:
            header.class = ``
            header.cols = 6
            header.title = `배너명`
            break
          case 2:
            header.class = ``
            header.cols = 2
            header.title = `제작일`
            break
          case 3:
            header.class = `d-flex justify-start`
            header.cols = 2
            header.title = `서비스`
            break
        }

        if (this.getScreenSize() === 'xs') {
          header.class = `py-4`
          header.cols = 12
        }

        this.headerInfo.push(header)
      }
    },
    getPageInfo(pageInfo) {
      this.$emit(`getPageInfo`, pageInfo)
    },
  },
}
</script>

<style lang="scss" scoped></style>
