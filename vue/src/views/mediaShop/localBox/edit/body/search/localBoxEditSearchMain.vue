<template>
  <v-container ref="mainWrap" class="pa-0">
    <v-row justify="center" no-gutters>
      <v-col
        class="d-sm-flex align-center"
        cols="12"
        sm="10"
        lg="5"
        offset-lg="3"
      >
        <div class="search-type">
          <searchFilter :info="filterInfo">배너명</searchFilter>
        </div>
        <v-col class="ml-sm-2 px-0">
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
import { commonMediaShop } from '@/views/mediaShop/mixins/commonMixins'

import searchFilter from '@/components/vuetify/menuFilter'

export default {
  mixins: [commonMediaShop],
  components: {
    searchFilter,
  },
  data() {
    return {
      filterInfo: {},
    }
  },
  created() {
    this.filterInfo.items = [
      { name: `배너명`, check: true, type: this.FILTER_TYPE.BANNER_TITLE },
      {
        name: `로컬러`,
        check: false,
        type: this.FILTER_TYPE.LOCAL_NAME,
      },
    ]
    this.filterInfo.width = 150

    if (this.getScreenSize() === 'xs') {
      this.$nextTick().then(() => {
        this.filterInfo.width = this.$refs.mainWrap.offsetWidth
        this.filterInfo = Object.assign({}, this.filterInfo)
      })
    }
  },
  methods: {
    search(searchText) {
      const items = this.filterInfo.items

      const filterType = items.find((item) => {
        return item.check === true
      })

      const searchInfo = {
        filterType,
        searchText,
      }

      this.$emit('search', searchInfo)
    },
  },
}
</script>

<style lang="scss" scoped></style>
