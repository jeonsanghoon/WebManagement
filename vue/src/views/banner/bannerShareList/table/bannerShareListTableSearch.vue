<template>
  <v-container ref="mainWrap" class="ma-0 pa-0" fluid>
    <v-row justify="center" no-gutters>
      <v-col class="d-sm-flex align-center pt-sm-5" cols="12" sm="8" lg="6">
        <div class="search-type">
          <Vuetify-menuFilter :info="filterInfo">배너명</Vuetify-menuFilter>
        </div>
        <v-col class="ml-sm-2 pt-1 pt-sm-3 px-0">
          <Table-searchBar
            :info="{ placeholder: '배너 조회를 해보세요.' }"
            @search="search"
          />
        </v-col>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { commonBanner } from '@/views/banner/mixins/commonMixins'
import { commonBannerShareList } from '@/views/banner/mixins/commonBannerShareListMixins'

export default {
  mixins: [commonBanner, commonBannerShareList],

  data() {
    return {
      filterInfo: {},
    }
  },
  created() {
    this.filterInfo.items = [
      { name: '배너명', check: true, type: this.FILTER_TYPE.BANNER_TITLE },
      {
        name: '로컬러',
        check: false,
        type: this.FILTER_TYPE.LOCAL_NAME,
      },
    ]
    this.filterInfo.width = 150

    if (this.getScreenSize() === 'xs') {
      this.mobileFilterStyle()
    }
  },
  methods: {
    async mobileFilterStyle() {
      await this.$nextTick()
      this.filterInfo = {
        ...this.filterInfo,
        width: this.$refs.mainWrap.offsetWidth,
      }
    },
    search(searchText) {
      const items = this.filterInfo.items

      const filterType = items.find((item) => item.check === true)

      const searchInfo = {
        filterType,
        searchText,
      }

      this.$emit('search', searchInfo)
    },
  },
}
</script>
