<template>
  <v-container class="wrap">
    <Table-header
      class="menu-wrap"
      @check="headerCheck"
      :info="headerInfo"
      :len="len"
    />
  </v-container>
</template>

<script>
import { commonMediaShop } from '@/views/mediaShop/mixins/commonMixins'

export default {
  mixins: [commonMediaShop],
  props: {
    len: {
      type: Number,
      required: false,
    },
  },
  data() {
    return {
      headerInfo: [],

      MENU_MAX_LEN: 5,
    }
  },
  created() {
    this.initHeaderInfo()
  },
  methods: {
    initHeaderInfo() {
      for (let i = 0; i < this.MENU_MAX_LEN; i++) {
        let header = new Object()

        header.class = 'd-flex justify-center'
        header.checkBox = false

        switch (i) {
          case 0:
            header.cols = 2
            header.checkBox = true
            break
          case 1:
            header.cols = 1
            header.title = 'NO'
            break
          case 2:
            header.cols = 3
            header.title = '로컬러'
            break
          case 3:
            header.cols = 2
            header.title = '업종'
            break
          case 4:
            header.cols = 4
            header.title = '위치'
            break
        }

        if (this.getScreenSize() === 'xs') {
          header.class = 'py-4 d-flex justify-center align-center'
          header.cols = 12
        }

        this.headerInfo.push(header)
      }
    },
    headerCheck(check) {
      this.$emit('headerCheck', check)
    },
  },
}
</script>

<style lang="scss" scoped>
.wrap {
  padding: 0;
}

.menu-wrap {
  border-bottom: 1px solid #e2d9d2;
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .wrap {
    border-top: 1px solid #e2d9d2;
  }

  .menu-wrap {
    border-top: 0;
    border-bottom: 0;
  }
}
</style>
