<template>
  <v-container ref="mainWrap" class="wrap">
    <div class="search-wrap d-sm-flex">
      <div class="search-filter-wrap">
        <Vuetify-menuFilter :info="filterInfo" />
      </div>
      <div class="search-bar-wrap">
        <Table-searchBar
          :info="{ placeholder: '키워드를 입력하여 검색하세요.' }"
          @search="search"
        />
      </div>
    </div>
  </v-container>
</template>

<script>
import { commonMediaShop } from '@/views/mediaShop/mixins/commonMixins'

export default {
  mixins: [commonMediaShop],
  data() {
    return {
      filterInfo: {},
    }
  },
  created() {
    this.init()
  },
  methods: {
    async init() {
      this.filterInfo.items = [
        {
          name: '로컬러명',
          check: true,
          type: this.FILTER_TYPE.LOCAL_NAME,
        },
        {
          name: '업종명',
          check: false,
          type: this.FILTER_TYPE.COMPANY_TYPE,
        },
      ]
      this.filterInfo.width = 150

      if (this.getScreenSize() === 'xs') {
        await this.$nextTick()
        this.filterInfo.width = this.$refs.mainWrap.offsetWidth
        this.filterInfo = { ...this.filterInfo }
      }
    },
    search(searchText) {
      const filterType = this.filterInfo.items.find((item) => item.check)

      const searchInfo = {
        filterType,
        searchText,
      }

      this.$emit('search', searchInfo)
    },
  },
}
</script>

<style lang="scss" scoped>
.wrap {
  padding: 0;
}

.search-wrap {
  margin-top: 48px;

  .search-filter-wrap {
    display: flex;
    align-items: center;
  }

  .search-bar-wrap {
    margin-left: 6px;
    width: 100%;
  }
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .search-wrap {
    margin-top: 20px;

    .search-bar-wrap {
      margin-top: 10px;
      margin-left: 0;
    }
  }
}
</style>
