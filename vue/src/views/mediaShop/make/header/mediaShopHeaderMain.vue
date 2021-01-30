<template>
  <v-container class="pa-0 text-start" fluid>
    <v-row class="px-2 px-lg-0" no-gutters>
      <v-col cols="12" lg="5" offset-lg="2">
        <div class="header-box ondaji_font_24_bold_grey_70" no-gutters>
          <div>{{ title }}</div>
          <div class="ondaji_font_20_medium_grey_70">{{ subTitle }}</div>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { commonMediaShop } from "@/views/mediaShop/mixins/commonMixins"

export default {
  mixins: [commonMediaShop],
  props: {
    page: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      title: ``,
      subTitle: ``,
    }
  },
  created() {
    this.setTitle()
  },
  watch: {
    page() {
      this.setTitle()
    },
  },
  methods: {
    setTitle() {
      const member = this.$BaseCommon.memberInfo.getMember().member
      let name = ``

      if (this.checkNull(member.companyName) === false) {
        name = `${member.companyName} ${member.branchName}`
      } else {
        name = member.nickname
      }

      if (this.page === 0) {
        this.title = `${name} 로컬러님`
        this.subTitle = `로컬박스 정보를 적어 주세요.`
      } else {
        this.title = `로컬박스의 지역 특색을 높이는 Tip!`
        this.subTitle = `배너 광고 지역과 거리를 가깝게 설정하세요! 광고 효율성이
              높아집니다.`
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.row {
  background: #fafafa;
}

.header-box {
  margin-top: 56px;
  margin-bottom: 36px;
}

@media only screen and (max-width: $grid-breakpoints_lg) {
  .header-box {
    margin-top: 20px;
  }
}
</style>
