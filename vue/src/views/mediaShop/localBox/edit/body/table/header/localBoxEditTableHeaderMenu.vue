<template>
  <v-container class="pa-0">
    <selectMenu class="menu-wrap" :info="headerInfo" :len="len" />
  </v-container>
</template>

<script>
import { commonMediaShop } from '@/views/mediaShop/mixins/commonMixins'

import selectMenu from '@/components/table/header'

export default {
  mixins: [commonMediaShop],
  components: {
    selectMenu,
  },
  props: {
    len: {
      type: Number,
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
            header.class = `d-flex justify-center`
            header.cols = 1
            header.title = ``
            break
          case 1:
            header.class = `d-flex justify-center`
            header.cols = 1
            header.title = `NO`
            break
          case 2:
            header.class = ``
            header.cols = 5
            header.title = `배너명`
            break
          case 3:
            header.class = `d-flex justify-center`
            header.cols = 2
            header.title = `광고현황`
            break
          case 4:
            header.class = `d-flex justify-center`
            header.cols = 1
            header.title = `공유`
            break
          case 5:
            header.class = `d-flex justify-center`
            header.cols = 2
            header.title = `광고요청`
            break
        }

        if (this.getScreenSize() === 'xs') {
          header.class = `py-4 d-flex align-center justify-center`
          header.cols = 12
          header.style = `height: 60px;`
        }

        this.headerInfo.push(header)
      }
    },
    orderComplete(retList) {
      this.$emit('orderComplete', retList)
    },
  },
}
</script>

<style lang="scss" scoped>
.menu-wrap {
  border-top: 2px solid #e2d9d2;
  border-bottom: 1px solid #e2d9d2;
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .container {
    border-top: 1px solid #e2d9d2;
  }

  .menu-wrap {
    border-top: 0;
    border-bottom: 0;
  }
}
</style>
