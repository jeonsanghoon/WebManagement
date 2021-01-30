<template>
  <v-container ref="categoryRef" class="pa-0">
    <ul class="category-wrap">
      <div class="set-local-text ondaji_font_14_medium_brown_70">
        지역설정
      </div>
      <li v-for="(category, i) in categoryList" :key="i">
        <localCategory :info="category" @selectLocal="selectLocal" />
      </li>
    </ul>
  </v-container>
</template>

<script>
import { commonMediaShop } from '@/views/mediaShop/mixins/commonMixins'

import localCategory from '@/views/mediaShop/localBox/shareReq/components/localCategory'

export default {
  mixins: [commonMediaShop],
  components: {
    localCategory,
  },
  data() {
    return {
      categoryList: [],
    }
  },
  created() {
    this.init()
    this.reqGetAddressAreaList(1)
  },

  methods: {
    init() {
      for (let i = 0; i < 3; i++) {
        let category = {}

        switch (i) {
          case 0:
            category.name = '시·도'
            break
          case 1:
            category.name = '구·군'
            break
          case 2:
            category.name = '동·읍·면'
            break
        }

        category.type = i
        category.items = []
        category.width = this.getScreenSize() != 'xs' ? 120 : 300
        category.show = false

        this.categoryList.push(category)
      }
    },
    selectLocal(local) {
      this.$emit('selectAddr', local)

      if (local.level === 3) return

      if (local.level === 1) {
        this.categoryList[local.level].items = []
        this.categoryList[local.level + 1].items = []
      } else if (local.level === 2) {
        this.categoryList[local.level].items = []
      }

      this.reqGetAddressAreaList(local.level + 1, local.code)
    },
    reqGetAddressAreaList(level, code) {
      this.$BaseCommon.restful
        .call2('/common/getAddressAreaList', { level, code })
        .then((res) => {
          res = res.map((local) => {
            local.check = false
            return local
          })

          let category = this.categoryList[level - 1]

          category.items = [...res]
        })
        .catch((res) => {
          console.log('/common/getAddressAreaList >> ', res)
        })
    },
  },
}
</script>

<style lang="scss" scoped>
ul {
  padding: 0;
  list-style: none;
}

li:not(:last-child) {
  border-right: 2px solid #e2d9d2;
}

.category-wrap {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 8px 0;
  border-top: 1px solid #e2d9d2;
  border-bottom: 1px solid #e2d9d2;
}

.set-local-text {
  margin-left: 20px;
  margin-right: auto;
}

@media screen and (max-width: $grid-breakpoints_sm) {
  li:not(:last-child) {
    border: 0;
  }

  .category-wrap {
    display: block;
    border-top: 2px solid #e2d9d2;
  }

  .set-local-text {
    text-align: center;
  }
}
</style>
