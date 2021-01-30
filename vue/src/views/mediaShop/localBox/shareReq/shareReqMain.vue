<template>
  <v-dialog class="modal-wrap" v-model="dialog" persistent>
    <!-- 헤더 -->
    <modalHeader class="header-wrap" @close="close">
      광고 요청
    </modalHeader>

    <!-- 메인 -->
    <typeSelect
      v-if="page === 0"
      ref="refTypeSelect"
      :info="selects"
      @moveConfirm="moveConfirm"
    />
    <confirm
      v-else-if="page === 1"
      :info="selects"
      :shareBanner="shareBanner"
      @moveSelctType="moveSelctType"
      @moveComplete="moveComplete"
    />
    <complete v-else @close="close" />
  </v-dialog>
</template>

<script>
import { commonMediaShop } from "@/views/mediaShop/mixins/commonMixins"

import modalHeader from "@/views/mediaShop/components/modalHeader"
import typeSelect from "./step/shareTypeSelect"
import confirm from "./step/shareConfirm"
import complete from "./step/shareComplete"

export default {
  mixins: [commonMediaShop],
  components: {
    modalHeader,
    typeSelect,
    confirm,
    complete,
  },
  props: {
    dialog: {
      type: Boolean,
      required: true,
      default: true,
    },
    shareBanner: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      page: 0,
      selects: [],
      refGuides: [],
      guides: [],
    }
  },
  watch: {
    async shareBanner() {
      await this.$nextTick(
        () => (this.refGuides = this.$refs.refTypeSelect.guides)
      )
    },
    dialog() {
      if (!this.dialog) return

      const formData = {
        bannerCode: this.shareBanner.bannerCode,
      }

      this.$BaseCommon.restful
        .call2("/mediashop/getbannershare", formData)
        .then((res) => {
          this.setShareBanner(res)
        })
        .catch((res) => {
          console.log("/mediashop/getbannershare >> ", res)
        })
    },
  },
  methods: {
    setShareBanner(res) {
      for (const banner of res) {
        if (banner.isLocal) {
          this.setRefGuides(0, banner)
        } else if (banner.isShare) {
          this.setRefGuides(1, banner)
        } else {
          this.setRefGuides(2, banner)
        }
      }

      this.refGuides = [...this.refGuides]
    },
    setRefGuides(index, banner) {
      let guide = this.refGuides[index]

      const endDate = new Date(
        banner.toDate.toString().replace(/(\d{4})(\d{2})(\d{2})/, "$1.$2.$3")
      )
      const nowDate = new Date()

      if (nowDate < endDate) {
        guide.expiration = true
        if (index !== 1) {
          guide.startDate = banner.frDate
            .toString()
            .replace(/(\d{4})(\d{2})(\d{2})/, "$1.$2.$3")
          guide.endDate = banner.toDate
            .toString()
            .replace(/(\d{4})(\d{2})(\d{2})/, "$1.$2.$3")
          guide.viewDate = `${guide.startDate.slice(
            2,
            10
          )}~${guide.endDate.slice(2, 10)}`
        }
      }
    },
    moveConfirm(selects) {
      this.selects = [...selects]
      this.page++
    },
    moveSelctType() {
      this.page--
    },
    moveComplete(shareInfo) {
      if (!this.checkNull(shareInfo)) {
        this.selects[1].startDate = shareInfo.startDate
        this.selects[1].endDate = shareInfo.endDate
        this.selects[1].reqReason = shareInfo.reqReason
        this.selects[1].localers = shareInfo.selectLocalersInfo
      }
      this.reqSaveShareBanner()
    },
    reqSaveShareBanner() {
      let formData = []

      for (const [i, select] of this.selects.entries()) {
        if (!select.select) continue

        const data = {
          bannerCode: this.shareBanner.bannerCode,
          requestMemberCode: this.$BaseCommon.memberInfo.getMember().member
            .memberCode,
          frDate: select.startDate.replaceAll(".", ""),
          toDate: select.endDate.replaceAll(".", ""),
          requestDesc: select.reqReason,
          localers: select.localers,
        }
        data.insertCode = data.requestMemberCode

        if (i === 0) {
          data.isLocal = true
        } else if (i === 1) {
          data.isShare = true
        } else {
          data.isMobile = true
        }

        formData.push(data)
      }

      this.$BaseCommon.restful
        .call2("/mediashop/savebannershare", formData)
        .then(() => {
          this.page++
        })
        .catch((res) => {
          console.log("/mediashop/savebannershare >> ", res)
        })
    },
    close() {
      for (let guide of this.refGuides) {
        guide.style = ""
        guide.select = false
        guide.dateViewClick = false
        guide.expiration = false
        guide.startDate = ""
        guide.endDate = ""
        guide.viewDate = ""

        if (guide.img.charAt(guide.img.length - 1) === "2") {
          guide.img = guide.img.slice(0, -1)
        }
      }

      this.page = 0
      this.selects = []

      this.$emit("update:dialog", false)
    },
  },
}
</script>

<style lang="scss" scoped>
::v-deep {
  .v-dialog {
    margin: 100px auto;
    padding: 0;
    width: 740px;
    background-color: white !important;
  }

  @media (min-width: $grid-breakpoints_sm) and (max-width: $grid-breakpoints_lg) {
    .v-dialog {
      margin: 100px auto;
      width: 600px;
    }
  }

  @media screen and (max-width: $grid-breakpoints_sm) {
    .v-dialog {
      margin: 100px auto;
      padding: 0 !important;
      width: 320px;
    }
  }
}
</style>
