<template>
  <v-container class="table-background">
    <v-row no-gutters>
      <v-col cols="12" v-if="bannerList.length > 0">
        <v-row no-gutters v-for="(banner, i) in bannerList" :key="i">
          <v-col cols="12">
            <bannerTableCard
              :banner="banner"
              :info="contentInfo"
            ></bannerTableCard>
            <div v-if="bannerList.length - 1 != i">
              <div :style="bottomLine"></div>
            </div>
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="12" v-else>
        <bannerSelectTypeEmpty v-if="searchTitleCheck === false" />
        <bannerSearchTextEmpty v-else />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { commonBanner } from '../../../mixins/commonMixins'
import { commonBannerStore } from '../../../mixins/commonBannerStoreMixins'

import bannerTableCard from './components/card'
import bannerSelectTypeEmpty from '@/components/table/searchTypeEmpty'
import bannerSearchTextEmpty from '@/components/table/searchTextEmpty'

export default {
  mixins: [commonBanner, commonBannerStore],

  components: {
    bannerTableCard,
    bannerSelectTypeEmpty,
    bannerSearchTextEmpty,
  },
  props: {
    bannerList: {
      type: Array,
      required: false,
    },
    searchTitleCheck: {
      type: Boolean,
      required: false,
    },
  },
  data() {
    return {
      contentInfo: [],
    }
  },
  created() {
    this.initContentInfo()
  },
  computed: {
    bottomLine() {
      if (this.getScreenSize() != 'xs') {
        return `margin-bottom: 0.438rem;`
      } else {
        return `border-bottom: 0.05rem solid #F2F2F2;`
      }
    },
  },
  methods: {
    initContentInfo() {
      let content = new Object()

      content.class = `d-flex py-2`
      content.cols = 12

      if (this.getScreenSize() === 'xs') {
        content.class = ``
      }

      this.contentInfo.push(content)

      // 1: NO, 2: 배너명, 3: 제작일, 4: 서비스
      for (let i = 1; i < this.MENU_NUM + 1; i++) {
        switch (i) {
          case 1:
            if (this.getScreenSize() != 'xs') {
              this.setContentInfo(`d-flex align-center justify-end`, 2)
            } else {
              this.setContentInfo(`py-4 px-3 text-start`, 12)
            }
            break
          case 2:
            if (this.getScreenSize() != 'xs') {
              this.setContentInfo(`d-flex pa-2 py-lg-2 pl-lg-7 pr-lg-2`, 6)
            } else {
              this.setContentInfo(`text-start`, 12, `padding: 0.313rem;`)
            }
            break
          case 3:
            if (this.getScreenSize() != 'xs') {
              this.setContentInfo(`d-flex align-center justify-center pa-0`, 2)
            } else {
              this.setContentInfo(`py-4 px-3 text-start`, 12)
            }
            break
          case 4:
            if (this.getScreenSize() != 'xs') {
              this.setContentInfo(`d-flex align-center pa-0`, 2)
            } else {
              this.setContentInfo(`px-0 text-start`, 12, `padding:0.585rem;`)
            }
            break
        }
      }
    },

    setContentInfo(className, cols, style) {
      let content = new Object()

      if (this.checkNull(className) === false) {
        content.class = className
      }
      if (this.checkNull(cols) === false) {
        content.cols = cols
      }
      if (this.checkNull(style) === false) {
        content.style = style
      }

      this.contentInfo.push(content)
    },
  },
}
</script>

<style lang="scss" scoped>
.table-background {
  height: 24.875rem;
  padding: 0rem;
  background-color: #f2f2f2;
}
</style>
