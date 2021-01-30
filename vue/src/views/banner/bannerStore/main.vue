<template>
  <v-container class="fluid align-start justify-start" @click="outsideClick">
    <v-row no-gutters>
      <v-col class="pt-3" cols="12">
        <!-- header -->
        <Vuetify-menutitle />

        <!-- content -->
        <bannerStoreSearchMain
          :progressInfo="progressInfo"
          :outsideClickCheck="outsideClickCheck"
          @searchType="reqSearchTypeBanner"
        ></bannerStoreSearchMain>

        <bannerStoreTableMain
          :bannerList="bannerList"
          :totalNum="totalNum"
          :searchTitleCheck="searchTitleCheck"
          @getPageInfo="reqPageInBanner"
        ></bannerStoreTableMain>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { commonBanner } from "../mixins/commonMixins"
import { commonBannerStore } from "../mixins/commonBannerStoreMixins"

import bannerStoreSearchMain from "./search/main"
import bannerStoreTableMain from "./table/main"

export default {
  mixins: [commonBanner, commonBannerStore],
  components: {
    bannerStoreSearchMain,
    bannerStoreTableMain,
  },
  data() {
    return {
      Packet() {
        this.formData = {
          memberCode: 0,
          title: ``,
          dateSeq: `DESC`,
          bannerType: [],
          deviceType2: [],
          service: [],
          pageStartNo: 0,
          pageInDataNum: this.PAGE_TOTAL_NUM,
        }
      },

      // searchTypeArr: 검색 타입
      searchTypeArr: [],
      bannerTitle: ``,

      bannerList: [],
      totalNum: 0,
      // 배너명을 통한 검색이 없을 때 보여줄 화면을 위한 체크
      searchTitleCheck: false,

      progressInfo: {
        lodingSearch: false,
        dialog: true,
      },

      outsideClickCheck: false,
    }
  },
  created() {
    this.reqBannerList(this.initFormData())
  },
  methods: {
    initFormData() {
      let packet = new this.Packet()

      packet.formData.memberCode = this.$BaseCommon.memberInfo.getMember().member.memberCode
      packet.formData.title = this.bannerTitle
      packet.formData.dateSeq = `DESC`
      packet.formData.pageInDataNum = this.PAGE_TOTAL_NUM

      return packet.formData
    },
    reqBannerList(formData) {
      this.progressInfo.lodingSearch = true
      this.$BaseCommon.restful
        .call2("/banner/getbannerstorelist", formData)
        .then((res) => {
          if (this.checkNull(res) === false) {
            this.bannerList = res
            this.totalNum =
              this.bannerList.length > 0 ? this.bannerList[0].totalRows : 0
            this.totalNum = res.totalBannerNum
            this.progressInfo.lodingSearch = false
          }
        })
        .catch((res) => {
          console.log("/banner/getbannerstorelist >> ", res)
        })
    },
    reqPageInBanner(pageInfo) {
      let formData = this.getFormData(this.initFormData())

      formData.pageStartNo = pageInfo.startNo

      this.reqBannerList(formData)
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

      this.reqBannerList(formData)
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
                formData.dateSeq = `ASC`
              } else {
                formData.dateSeq = `DESC`
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
              } else if (i === 1) {
                formData.deviceType2.push(2)
              } else {
                formData.deviceType2.push(3)
              }
            } else {
              if (i === 0) {
                formData.service.push(99)
              } else {
                formData.service.push(9)
              }
            }
          }
        }
      }
      return formData
    },
    outsideClick() {
      this.outsideClickCheck = !this.outsideClickCheck
    },
    mounted() {
      this.$store.watch(() => {
        this.$store.dispatch("baseStore/setIsShowBar", true)
      })
    },
  },
}
</script>

<style lang="scss" scoped></style>
