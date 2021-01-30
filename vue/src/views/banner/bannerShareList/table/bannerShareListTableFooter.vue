<template>
  <v-col class="footer-wrap" cols="12" lg="8">
    <tableFooter
      :info="{ totalNum: totalNum, PAGE_TOTAL_NUM: PAGE_TOTAL_NUM }"
      @getPageInfo="reqPageInBanner"
    />
  </v-col>
</template>

<script>
import { commonBanner } from '@/views/banner/mixins/commonMixins'
import { commonBannerShareList } from '@/views/banner/mixins/commonBannerShareListMixins'

export default {
  mixins: [commonBanner, commonBannerShareList],
  props: {
    totalNum: {
      type: Number,
      required: true,
    },
  },
  methods: {
    reqPageInBanner(pageInfo) {
      this.$emit('reqPageInBanner', pageInfo)
    },
  },
}
</script>

<style lang="scss" scoped>
@include tablet {
  .footer-wrap {
    margin-bottom: 50px;
  }
}

@include mobile {
  .footer-wrap {
    margin-bottom: 30px;
  }
}
</style>
