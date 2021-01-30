<template>
  <v-container ref="searchBackground" class="pa-0">
    <!-- content -->
    <v-row justify="center" no-gutters>
      <v-col class="pa-0" cols="12" lg="12">
        <div class="search-div-bar" />

        <v-row no-gutters>
          <v-col cols="12" sm="3" lg="2">
            <bannerSelectMenu
              :info="typeInfoArr[TYPE.DATE_ORDER].info"
              @click:button="closeShowBtn(TYPE.DATE_ORDER)"
              @searchType="searchType"
            >
              제작일 순
            </bannerSelectMenu>
          </v-col>

          <v-col class="ml-auto" cols="12" sm="3" lg="2">
            <bannerSelectMenu
              :info="typeInfoArr[TYPE.BANNER].info"
              @click:button="closeShowBtn(TYPE.BANNER)"
              @searchType="searchType"
            >
              정보/광고
            </bannerSelectMenu>
          </v-col>

          <v-col cols="12" sm="3" lg="2">
            <bannerSelectMenu
              :info="typeInfoArr[TYPE.FRAME].info"
              @click:button="closeShowBtn(TYPE.FRAME)"
              @searchType="searchType"
            >
              프레임 유형
            </bannerSelectMenu>
          </v-col>

          <v-col cols="12" sm="3" lg="2">
            <bannerSelectMenu
              :info="typeInfoArr[TYPE.REQUIRED].info"
              @click:button="closeShowBtn(TYPE.REQUIRED)"
              @searchType="searchType"
            >
              요청상태
            </bannerSelectMenu>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { commonBanner } from '@/views/banner/mixins/commonMixins'
import { commonBannerShareList } from '@/views/banner/mixins/commonBannerShareListMixins'

import bannerSelectMenu from '@/views/banner/components/bannerSelectMenu'

export default {
  mixins: [commonBanner, commonBannerShareList],
  components: {
    bannerSelectMenu,
  },
  data() {
    return {
      TypeInfo() {
        this.info = {
          type: 0,
          // 버튼  정보
          items: [],
          // 리스트 노출 여부
          show: false,
          // 단일 체크 가능 여부
          checkOnly: false,
          width: 0,
          style: '',
        }
      },
      searchText: '',

      // 선택 버튼들 정보
      typeInfoArr: [],

      type: -1,
    }
  },
  created() {
    this.initBannerTypeInfo()
  },
  watch: {
    outsideClickCheck() {
      this.closeShowBtn(this.type)
      this.type = -1
    },
  },
  methods: {
    initBannerTypeInfo() {
      const type = this.TYPE

      for (let i = 0; i < this.BTN_NUM; i++) {
        let typeInfo = new this.TypeInfo()

        typeInfo.info.checkView = true

        switch (i) {
          case type.DATE_ORDER:
            typeInfo.info.type = type.DATE_ORDER
            typeInfo.info.items = [
              { name: '최근 요청일순', check: true },
              { name: '오래된 요청일순', check: false },
            ]
            typeInfo.info.checkOnly = true
            typeInfo.info.checkView = false
            typeInfo.info.width = 150
            break
          case type.BANNER:
            typeInfo.info.type = type.BANNER
            typeInfo.info.items = [
              { name: '정보배너', check: false },
              { name: '광고배너', check: false },
            ]
            typeInfo.info.width = 120
            break
          case type.FRAME:
            typeInfo.info.type = type.FRAME
            typeInfo.info.items = [
              { name: '1프레임', check: false },
              { name: '6프레임', check: false },
            ]
            typeInfo.info.width = 140
            break
          case type.REQUIRED:
            typeInfo.info.type = type.REQUIRED
            typeInfo.info.items = [
              { name: '요청', check: false },
              { name: '승인', check: false },
              { name: '거절', check: false },
            ]
            typeInfo.info.width = 130
            break
        }

        if (i !== type.REQUIRED && this.getScreenSize() !== 'xs') {
          typeInfo.info.style = 'border-right: 0.15rem solid #E2D9D2;'
        }

        if (this.getScreenSize() === 'xs') {
          this.$nextTick().then(() => {
            typeInfo.info.width = this.$refs.searchBackground.offsetWidth
          })
        }

        this.typeInfoArr.push(typeInfo)
      }
    },
    searchType(searchText) {
      if (searchText !== undefined) {
        this.searchText = searchText
      }

      this.$emit('searchType', this.typeInfoArr, this.searchText)
    },
    closeShowBtn(type) {
      this.typeInfoArr.forEach((e, i) => {
        if (type === -1 || i !== type) {
          e.info = Object.assign({}, e.info, { show: false })
        }
      })

      if (type !== -1) {
        this.type = type
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.search-bar {
  margin: 3.125rem 0;
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .search-bar {
    margin: 1.875rem 0;
  }
}

.search-div-bar {
  border: 0.031rem solid #e2d9d2;
}
</style>
