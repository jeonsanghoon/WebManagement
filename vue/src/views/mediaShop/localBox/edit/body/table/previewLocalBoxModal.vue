<template>
  <v-dialog v-model="dialog" persistent max-width="428">
    <div v-scroll:#scroll-target="onScroll">
      <div id="scroll-target" ref="modal">
        <!-- 헤더 -->
        <modalHeader class="header-wrap" @close="close">
          로컬박스 미리보기
        </modalHeader>
        <!-- 컨텐츠 -->
        <div class="modal-wrap">
          <div
            v-for="(banner, index) in bannerList"
            :key="index"
            class="card-wrap"
          >
            <previewCard :info="banner" />
          </div>
        </div>
      </div>
    </div>
  </v-dialog>
</template>

<script>
import { commonMediaShop } from "@/views/mediaShop/mixins/commonMixins"

import modalHeader from "@/views/mediaShop/components/modalHeader"
import previewCard from "./previewLocalBoxCard"

export default {
  mixins: [commonMediaShop],
  components: {
    modalHeader,
    previewCard,
  },
  props: {
    dialog: {
      type: Boolean,
      required: true,
      default: false,
    },
  },
  data() {
    return {
      PREVIEW_PAGE_TOTAL_NUM: 6,

      bannerList: [],

      pageInfo: {
        startNo: 0,
        curPage: 0,
      },

      loading: false,
    }
  },
  watch: {
    dialog() {
      if (this.dialog === true) {
        this.reqBannerList(this.initFormData())
      }
    },
  },
  methods: {
    onScroll() {
      if (this.loading === true) {
        return
      }

      const list = this.$refs.modal

      if (
        list.scrollTop + list.clientHeight >= list.scrollHeight &&
        list.scrollTop != 0
      ) {
        if (
          (this.pageInfo.curPage + 1) * this.PREVIEW_PAGE_TOTAL_NUM >=
          this.totalNum
        ) {
          return
        }

        this.pageInfo.startNo =
          ++this.pageInfo.curPage * this.PREVIEW_PAGE_TOTAL_NUM

        this.reqBannerList(this.initFormData(this.pageInfo.startNo))
      }
    },
    reqBannerList(formData) {
      formData.pageInDataNum = this.PREVIEW_PAGE_TOTAL_NUM

      this.loading = true

      this.$BaseCommon.restful
        .call2("/mediashop/getlocalboxpreviewbannerlist", formData)
        .then((res) => {
          if (this.checkNull(res) === false) {
            let resBannerList = res.bannerList.map((banner, i) => {
              banner.places = res.places[i].address.split(",")

              if (banner.serviceDate != null) {
                banner.serviceDate = `${this.$moment().diff(
                  banner.serviceDate,
                  "days"
                )}일전`
              }
              return banner
            })

            this.setAddress(resBannerList)
            this.setTotalBannerList(resBannerList)
            this.totalNum = res.totalNum
            this.loading = false
          }
        })
        .catch((res) => {
          console.log("/mediashop/getlocalboxpreviewbannerlist >> ", res)
        })
    },
    setAddress(bannerList) {
      bannerList.forEach((banner) => {
        const places = ["동", "리"]

        banner.address = []

        banner.places.forEach((place) => {
          const address = place
            .split(" ")
            .filter((place) => {
              const endStr = place.slice(-1)

              if (places.includes(endStr) === true) {
                return place
              }
            })
            .toString()

          banner.address.push(address)
        })
      })
    },
    setTotalBannerList(bannerList) {
      const start =
        (this.bannerList.length + bannerList.length) %
        this.PREVIEW_PAGE_TOTAL_NUM
      let num = this.bannerList.length + bannerList.length + 1

      if (start != 0 || this.bannerList.length + bannerList.length === 0) {
        for (let i = start; i < this.PREVIEW_PAGE_TOTAL_NUM; i++) {
          let bannerInfo = {}

          bannerInfo.title = `${num++}번 배너`
          bannerInfo.address = [`OO동`, `OO동`, `OO동`]
          bannerInfo.subTitle = `부제목`
          bannerInfo.serviceDate = `O일전`

          bannerList.push(bannerInfo)
        }
      }

      this.bannerList = [...this.bannerList, ...bannerList]
    },
    close() {
      this.bannerList.length = 0
      this.pageInfo.startNo = 0
      this.pageInfo.curPage = 0
      this.loading = false

      this.$emit("update:dialog", false)
    },
  },
}
</script>

<style lang="scss" scoped>
#scroll-target {
  overflow: scroll;
  overflow-x: hidden;
  max-height: 800px;
}

.header-wrap {
  width: 411px;
  position: fixed;
  z-index: 1;
}

.modal-wrap {
  padding: 67px 22px 0px 22px;
  background: white;

  &::after {
    content: "";
    display: block;
    clear: both;
  }
}

.modal-wrap div:nth-child(2n) {
  margin-left: 16px;
}

.card-wrap {
  width: 174px;
  height: 274px;
  text-align: start;
  float: left;
  margin-bottom: 20px;
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .header-wrap {
    width: 310px;
  }

  .modal-wrap {
    padding: 61px 4px 0px 4px;
  }

  .modal-wrap div:nth-child(2n) {
    margin-left: 8px;
  }

  .card-wrap {
    width: 146px;
  }
}

::v-deep {
  .v-dialog {
    margin: 5px !important;
  }
  @media screen and (max-width: $grid-breakpoints_sm) {
    .v-dialog {
      width: 310px;
    }
  }
}
</style>
