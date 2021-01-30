<template>
  <v-container class="table-wrap">
    <div v-if="bannerList.length > 0">
      <v-row
        justify="center"
        no-gutters
        v-for="(banner, i) in bannerList"
        :key="i"
      >
        <v-col cols="12">
          <bannerCard
            :banner="banner"
            :info="contentInfo"
            @bannerCheck="bannerCheck"
            @clickShare="clickShare"
          ></bannerCard>
          <div v-if="bannerList.length - 1 != i">
            <div :style="bottomLine"></div>
          </div>
        </v-col>
      </v-row>
    </div>
    <div v-else>
      <bannerSearchTextEmpty />
    </div>

    <shareReqMain :dialog.sync="shareDialog" :shareBanner="shareBanner" />
  </v-container>
</template>

<script>
import { commonMediaShop } from '@/views/mediaShop/mixins/commonMixins'
import { checkCard } from '@/components/table/mixinsCheckCard'

import bannerCard from './localBoxEditTableBodyCard'
import bannerSearchTextEmpty from '@/components/table/searchTextEmpty'
import shareReqMain from '@/views/mediaShop/localBox/shareReq/shareReqMain'

export default {
  mixins: [commonMediaShop, checkCard],

  components: {
    bannerCard,
    bannerSearchTextEmpty,
    shareReqMain,
  },
  props: {
    bannerList: {
      type: Array,
      required: false,
    },
  },
  data() {
    return {
      contentInfo: [],
      shareDialog: false,
      shareBanner: {},
    }
  },
  created() {
    this.initContentInfo()
  },
  mounted() {
    this.$EventBus.$on(`search`, (searchInfo) => {
      this.reqSearchBannerList(searchInfo)
    })
  },
  computed: {
    bottomLine() {
      if (this.getScreenSize() != 'xs') {
        return `margin-bottom: 0.438rem;`
      }
      return `border-top: 0.1rem solid #F2F2F2;`
    },
  },
  methods: {
    bannerCheck(checkInfo) {
      this.$emit('bannerCheck', this.checkItem(this.bannerList, checkInfo))
    },
    initContentInfo() {
      let content = new Object()

      content.class = `d-flex py-2`
      content.cols = 12

      if (this.getScreenSize() === 'xs') {
        content.class = ``
      }

      this.contentInfo.push(content)

      // 1: 체크, 2: NO, 3: 배너명, 4: 광고현황 5: 공유 6: 광고요청
      for (let i = 1; i < this.MENU_NUM + 1; i++) {
        switch (i) {
          case 1:
            if (this.getScreenSize() != 'xs') {
              this.setContentInfo(`d-flex align-center justify-end`, 1)
            } else {
              this.setContentInfo(
                `py-4 px-3 d-flex align-center text-start`,
                12
              )
            }
            break
          case 2:
            if (this.getScreenSize() != 'xs') {
              this.setContentInfo(`d-flex align-center justify-center`, 1)
            } else {
              this.setContentInfo(
                `py-4 px-3 d-flex align-center text-start`,
                12
              )
            }
            break
          case 3:
            if (this.getScreenSize() != 'xs') {
              this.setContentInfo(`d-flex pa-2 py-lg-2 px-lg-2`, 5)
            } else {
              this.setContentInfo(
                `text-start d-flex align-center`,
                12,
                `padding: 0.313rem;`
              )
            }
            break
          case 4:
            if (this.getScreenSize() != 'xs') {
              this.setContentInfo(`d-flex align-center justify-center pa-0`, 2)
            } else {
              this.setContentInfo(
                `py-4 px-3 d-flex align-center text-center`,
                12
              )
            }
            break
          case 5:
            if (this.getScreenSize() != 'xs') {
              this.setContentInfo(`d-flex align-center justify-center`, 1)
            } else {
              this.setContentInfo(
                `py-4 px-3 d-flex align-center text-start`,
                12
              )
            }
            break
          case 6:
            if (this.getScreenSize() != 'xs') {
              this.setContentInfo(`d-flex align-center justify-center pa-0`, 2)
            } else {
              this.setContentInfo(
                `py-4 px-3 d-flex align-center text-center`,
                12
              )
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
    clickShare(shareBanner) {
      this.shareBanner = { ...shareBanner }
      this.shareDialog = true
    },
  },
  beforeDestroy() {
    this.$EventBus.$off('search')
  },
}
</script>

<style lang="scss" scoped>
.table-wrap {
  height: 24.875rem;
  padding: 0rem;
  background-color: #f2f2f2;
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .table-wrap {
    border-top: 1px solid #e2d9d2;
  }
}
</style>
