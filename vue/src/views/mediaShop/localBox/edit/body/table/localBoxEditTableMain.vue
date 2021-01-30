<template>
  <v-container class="pa-0">
    <v-row justify="center" no-gutters>
      <v-col cols="12" lg="8">
        <tableHeaderBtn
          :bannerList="bannerList"
          @orderComplete="orderComplete"
          @upload="reqUpload"
        />
      </v-col>

      <template v-if="getScreenSize() != `xs`">
        <v-col cols="12" lg="8">
          <tableHeaderMenu />
        </v-col>

        <v-col cols="12" lg="8">
          <tableBody :bannerList="bannerList" @bannerCheck="bannerCheck" />
        </v-col>
      </template>
      <template v-else>
        <template v-if="bannerList.length != 0">
          <v-col cols="2">
            <tableHeaderMenu :len="bannerList.length" />
          </v-col>

          <v-col cols="10">
            <tableBody :bannerList="bannerList" @bannerCheck="bannerCheck" />
          </v-col>
        </template>
        <template v-else>
          <v-col cols="12">
            <tableBody :bannerList="bannerList" @bannerCheck="bannerCheck" />
          </v-col>
        </template>
      </template>

      <v-col class="footer-wrap" cols="12" lg="8">
        <tableFooter
          :info="{ totalNum: totalNum, PAGE_TOTAL_NUM: PAGE_TOTAL_NUM }"
          @getPageInfo="reqPageInBanner"
        />
      </v-col>

      <div class="help-btn-wrap">
        <helpModal />
      </div>
    </v-row>
  </v-container>
</template>

<script>
import { commonMediaShop } from "@/views/mediaShop/mixins/commonMixins"

import tableHeaderBtn from "./header/localBoxEditTableHeaderBtn"
import tableHeaderMenu from "./header/localBoxEditTableHeaderMenu"
import tableBody from "./body/localBoxEditTableBodyMain"
import tableFooter from "@/components/table/footer"
import helpModal from "./footer/helpModal"

export default {
  mixins: [commonMediaShop],

  components: {
    tableHeaderBtn,
    tableHeaderMenu,
    tableBody,
    tableFooter,
    helpModal,
  },
  props: {
    searchInfo: {
      type: Object,
      required: false,
    },
  },
  data() {
    return {
      headerInfo: {},

      bannerList: [],

      totalNum: 0,

      change: false,
      checkUpload: false,
    }
  },
  created() {
    this.reqBannerList(this.initFormData())
  },
  watch: {
    searchInfo() {
      let formData = this.initFormData()

      formData.title = this.searchInfo.searchText

      this.reqBannerList(formData)
    },
  },
  methods: {
    initFormData() {
      let formData = {}

      formData.memberCode = this.$BaseCommon.memberInfo.getMember().member.memberCode

      // if(searchInfo.filterInfo === this.FILTER_TYPE.BANNER_TITEL) {
      //   packet.formData.title = searchInfo.searchText
      // } else {
      //   packet.formData.name = searchInfo.searchText
      // }
      // packet.formData.title = searchInfo.searchText
      formData.pageStartNo = 0
      formData.pageInDataNum = this.PAGE_TOTAL_NUM

      return formData
    },
    reqUpload() {
      if (this.checkUpload === true) {
        return
      }

      if (this.change === false) {
        this.$alert("변경사항이 없습니다.")
        return
      }

      this.bannerList.forEach((banner) => {
        banner.memberCode = this.$BaseCommon.memberInfo.getMember().member.memberCode
      })

      this.checkUpload = true

      this.$BaseCommon.restful
        .call2("/mediashop/uploadlocalboxbanner", this.bannerList)
        .then((res) => {
          this.checkUpload = false
          this.change = false

          if (this.checkRes(res) === true) {
            this.$alert("업로드되었습니다.")
          }
        })
        .catch((res) => {
          console.log("/mediashop/uploadlocalboxbanner >> ", res)
        })
    },
    reqBannerList(formData) {
      this.$emit("searchLoding", true)

      this.$BaseCommon.restful
        .call2("/mediashop/getlocalboxbannerlist", formData)
        .then((res) => {
          if (this.checkNull(res) === false) {
            this.bannerList = res.bannerList.map((banner, i) => {
              banner.check = false
              banner.index = i

              return banner
            })
            this.totalNum = res.totalNum
          }

          this.$emit("searchLoding", false)
        })
        .catch((res) => {
          console.log("/mediashop/getlocalboxbannerlist >> ", res)
        })
    },
    reqPageInBanner(pageInfo) {
      let formData = this.initFormData()

      formData.pageStartNo = pageInfo.startNo

      this.reqBannerList(formData)
    },
    orderComplete(retList) {
      this.change = true

      this.bannerList = retList
    },
    bannerCheck(retList) {
      this.bannerList = retList
    },
  },
}
</script>

<style lang="scss" scoped>
@media screen and (min-width: $grid-breakpoints_lg) {
  .help-btn-wrap {
    position: absolute;
    bottom: 30px;
    right: 30px;
  }
}

@media screen and (min-width: $grid-breakpoints_sm) {
  .footer-wrap {
    margin-bottom: 50px;
  }

  .help-btn-wrap {
    position: absolute;
    bottom: 15px;
    right: 15px;
  }
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .table-wrap {
    border-top: 1px solid #e2d9d2;
  }

  .footer-wrap {
    margin-bottom: 30px;
  }

  .help-btn-wrap {
    position: absolute;
    bottom: 4px;
    right: 10px;
  }
}
</style>
