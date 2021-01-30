<template>
  <v-container ref="searchBackground" class="pa-0">
    <!-- header -->
    <v-row class="search-bar" justify="center" no-gutters>
      <v-col class="px-2 px-sm-0" cols="12" sm="6" lg="4">
        <Table-searchBar
          :info="{ placeholder: '배너 조회를 해보세요.' }"
          @search="searchType"
        ></Table-searchBar>
      </v-col>
    </v-row>

    <!-- content -->
    <v-row justify="center" no-gutters>
      <v-col class="pa-0" cols="12" lg="10">
        <div class="search-div-bar"></div>

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
              디바이스 유형
            </bannerSelectMenu>
          </v-col>

          <v-col cols="12" sm="3" lg="2">
            <bannerSelectMenu
              :info="typeInfoArr[TYPE.SERVICE].info"
              @click:button="closeShowBtn(TYPE.SERVICE)"
              @searchType="searchType"
            >
              서비스 상태
            </bannerSelectMenu>
          </v-col>
        </v-row>

        <div class="search-div-bar"></div>
      </v-col>
    </v-row>

    <v-row
      v-if="progressInfo.lodingSearch === true"
      justify="center"
      no-gutters
    >
      <bannerProgressModal :dialog.sync="progressInfo.dialog">
        검색중...
      </bannerProgressModal>
    </v-row>
  </v-container>
</template>

<script>
import { commonBanner } from '@/views/banner/mixins/commonMixins'
import { commonBannerStore } from '@/views/banner/mixins/commonBannerStoreMixins'

import bannerSelectMenu from '@/views/banner/components/bannerSelectMenu'
import bannerProgressModal from '@/components/vuetify/progressModal'

export default {
  mixins: [commonBanner, commonBannerStore],
  components: {
    bannerSelectMenu,
    bannerProgressModal,
  },
  props: {
    progressInfo: {
      type: Object,
      required: true,
      default: null,
    },
    outsideClickCheck: {
      type: Boolean,
      required: true,
      default: false,
    },
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
          style: ``,
        }
      },
      searchText: ``,

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
      for (let i = 0; i < this.BTN_NUM; i++) {
        let typeInfo = new this.TypeInfo()

        typeInfo.info.checkView = true

        switch (i) {
          case this.TYPE.DATE_ORDER:
            typeInfo.info.type = this.TYPE.DATE_ORDER
            typeInfo.info.items = [
              { name: `최근 제작일순`, check: true },
              { name: `오래된 제작일순`, check: false },
            ]
            typeInfo.info.checkOnly = true
            typeInfo.info.checkView = false
            typeInfo.info.width = 150
            break
          case this.TYPE.BANNER:
            typeInfo.info.type = this.TYPE.BANNER
            typeInfo.info.items = [
              { name: `정보배너`, check: false },
              { name: `광고배너`, check: false },
            ]
            typeInfo.info.width = 120
            break
          case this.TYPE.FRAME:
            typeInfo.info.type = this.TYPE.FRAME
            typeInfo.info.items = [
              { name: `사이니지 1프레임`, check: false },
              { name: `사이니지 6프레임`, check: false },
              { name: `모바일`, check: false },
            ]
            typeInfo.info.width = 140
            break
          case this.TYPE.SERVICE:
            typeInfo.info.type = this.TYPE.SERVICE
            typeInfo.info.items = [
              { name: `ON`, check: false },
              { name: `OFF`, check: false },
            ]
            typeInfo.info.width = 130
            break
        }

        if (i != this.TYPE.SERVICE && this.getScreenSize() != 'xs') {
          typeInfo.info.style = `border-right: 0.15rem solid #E2D9D2;`
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
      if (searchText != undefined) {
        this.searchText = searchText
      }

      this.$emit('searchType', this.typeInfoArr, this.searchText)
    },
    closeShowBtn(type) {
      this.typeInfoArr.forEach((e, i) => {
        if (type === -1 || i != type) {
          e.info = Object.assign({}, e.info, { show: false })
        }
      })

      if (type != -1) {
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
