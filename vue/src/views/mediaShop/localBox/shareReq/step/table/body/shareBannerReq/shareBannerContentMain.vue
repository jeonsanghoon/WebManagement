<template>
  <v-container class="wrap">
    <oneFrame v-if="shareBanner.deviceType2 === 1" :bannerInfo="bannerInfo" />
    <sixFrame v-else :bannerInfo="bannerInfo" />
  </v-container>
</template>

<script>
import { commonMediaShop } from "@/views/mediaShop/mixins/commonMixins"

import oneFrame from "./oneFrame"
import sixFrame from "./sixFrame"

export default {
  mixins: [commonMediaShop],
  components: {
    oneFrame,
    sixFrame,
  },
  props: {
    shareBanner: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      bannerInfo: {
        img: "",
        title: "",
        subTitle: "",
        content: "",
        name: "",
      },

      SIGNAGE_TYPE: {
        ONE_FRAME: 1,
        SIX_FRAME: 2,
      },
    }
  },
  created() {
    this.reqShareBanner()
  },
  watch: {
    touch() {
      this.touchClass = [
        "ondaji_font_12_bold_white",
        "ondaji_font_12_medium_grey_50",
      ]

      if (this.touch === 1) {
        this.touchClass = [
          "ondaji_font_12_medium_grey_50",
          "ondaji_font_12_bold_white",
        ]
      }
    },
  },
  methods: {
    reqShareBanner() {
      this.$BaseCommon.restful
        .call2("/banner/getdata", {
          bannerCode: this.shareBanner.bannerCode,
        })
        .then((res) => {
          const member = this.$BaseCommon.memberInfo.getMember().member

          this.bannerInfo = {
            img: res.playType === 1 ? res.bannerImageUrl : res.thumbImageUrl,
            video: res.bannerMovieUrl,
            title: res.title,
            subTitle: res.subTitle,
            content: res.content,
            name: !member.companyName ? member.memberName : member.companyName,
            vertical: res.isVertical,
            profileImg: member.profileUrl,
          }

          console.log(this.bannerInfo)
        })
        .catch((res) => {
          console.log("/banner/getdata >> ", res)
        })
    },
  },
}
</script>

<style lang="scss" scoped>
p {
  padding: 0;
  margin: 0;
}

.wrap {
  background-color: white;
  width: 312px;
  height: 484px;
  border-radius: 10px;
  box-shadow: 0 0.05rem 0.3rem 0 #00000050;
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .wrap {
    width: 264px;
  }
}
</style>
