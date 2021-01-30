<template>
  <v-container class="ma-0 pa-0" fluid>
    <v-row justify="center" no-gutters>
      <tableSearch />
      <tableBody :bannerList="bannerList" @searchType="reqSearchTypeBanner" />
      <tableFooter :totalNum="totalNum" @reqPageInBanner="reqPageInBanner" />
    </v-row>
  </v-container>
</template>

<script>
import { commonBanner } from "@/views/banner/mixins/commonMixins"
import { commonBannerShareList } from "@/views/banner/mixins/commonBannerShareListMixins"

import tableSearch from "./bannerShareListTableSearch"
import tableBody from "./body/bannerShareListTableBodyMain"
import tableFooter from "./bannerShareListTableFooter"

export default {
  mixins: [commonBanner, commonBannerShareList],
  components: {
    tableSearch,
    tableBody,
    tableFooter,
  },
  props: {
    info: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      Packet() {
        this.formData = {
          memberCode: 0,
          title: "",
          dateSeq: "DESC",
          bannerType: [],
          deviceType2: [],
          statuses: [],
          pageStartNo: 0,
          pageInDataNum: this.PAGE_TOTAL_NUM,
        }
      },

      bannerList: [],
      totalNum: 0,
    }
  },
  created() {
    this.reqShareList(this.initFormData())
  },
  watch: {
    "info.section"() {
      this.reqShareList(this.initFormData())
    },
  },
  methods: {
    initFormData() {
      let packet = new this.Packet()
      const member = this.$BaseCommon.memberInfo.getMember().member

      packet.formData.memberCode = member.memberCode
      packet.formData.memberId = member.memberId
      packet.formData.pageStartNo = 0
      packet.formData.pageInDataNum = this.PAGE_TOTAL_NUM

      return packet.formData
    },
    reqShareList(formData) {
      const url =
        this.info.section === this.SECTION_TYPE.SECTION_REQ
          ? "getreqlist"
          : "getreslist"

      this.$BaseCommon.restful
        .call2(`/bannersharelist/${url}`, formData)
        .then((res) => {
          console.log("shareList: ", res)
          this.bannerList = res.shares
          this.totalNum = res.total_num
        })
        .catch((res) => {
          console.log(`/bannersharelist/${url} >> `, res)
        })
    },
    reqPageInBanner(pageInfo) {
      let formData = this.initFormData()
      formData.pageStartNo = pageInfo.pageStartNo

      this.reqShareList(formData)
    },
    reqSearchTypeBanner(searchTypeArr, bannerTitle) {
      // 배너명 입력을 통한 검색인지 체크
      if (this.checkNull(bannerTitle) === true) {
        this.searchTitleCheck = false
      } else {
        this.searchTitleCheck = true
      }
      this.searchTypeArr = searchTypeArr
      this.bannerTitle = bannerTitle

      const formData = this.getFormData(this.initFormData())
      console.log("formData: ", formData)
      this.reqShareList(formData)
    },
    getFormData(formData) {
      for (let out = 0; out < this.searchTypeArr.length; out++) {
        // 배너 검색 버튼 대분류
        const type = this.searchTypeArr[out].info.type
        // 배너 검색 버튼 소분류
        const selectArr = this.searchTypeArr[out].info.items

        for (let i = 0; i < selectArr.length; i++) {
          const check = selectArr[i].check

          // 배너 검색 버튼을 선택했다면
          if (check === true) {
            if (type === this.TYPE.DATE_ORDER) {
              if (i === 1) {
                formData.dateSeq = "ASC"
              } else {
                formData.dateSeq = "DESC"
              }
            } else if (type === this.TYPE.BANNER) {
              if (i === 0) {
                formData.bannerType.push(1)
              } else {
                formData.bannerType.push(2)
              }
            } else if (type === this.TYPE.FRAME) {
              if (i === 0) {
                formData.deviceType2.push(1)
              } else {
                formData.deviceType2.push(2)
              }
            } else {
              if (i === 0) {
                formData.statuses.push(1)
              } else if (i === 1) {
                formData.statuses.push(4)
              } else {
                formData.statuses.push(9)
              }
            }
          }
        }
      }
      return formData
    },
  },
}
</script>
