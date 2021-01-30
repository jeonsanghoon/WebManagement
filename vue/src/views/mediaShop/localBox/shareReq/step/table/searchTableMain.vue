<template>
  <v-dialog v-model="dialog" persistent>
    <modalHeader class="header-wrap" @close="close"> 광고 요청 </modalHeader>

    <div class="content-wrap">
      <!-- 헤더 타이틀 -->
      <div class="title-wrap">
        <p class="ondaji_font_14_bold_brown_100">로컬러 찾기</p>
        <p class="ondaji_font_14_medium_brown_100">
          배너 공유를 요청할 로컬러를 찾아보세요.
        </p>
      </div>

      <div class="main-wrap">
        <!-- 검색창 -->
        <searchHeader @search="reqSearchLocalers" />
        <!-- 테이블 메인 -->
        <tableBody
          class="table-wrap"
          :localers="localers"
          :totalNum="totalNum"
          @headerCheck="headerCheck"
          @localerCheck="setSelectLocalers"
          @getPageInfo="reqPageInLocaler"
          @selectAddr="reqAddrInLocaler"
        />
      </div>
    </div>

    <div class="footer-wrap">
      <!-- 선택한 로컬러 슬라이더 -->
      <div class="slide-wrap">
        <selectLocalerSlide :localers="selectLocalers" @close="closeChip" />
      </div>
      <!-- 버튼 -->
      <div class="footer-btn-wrap">
        <v-btn class="back-btn" text depressed @click="close">
          <span class="ondaji_font_14_bold_brown_80"> 뒤로가기 </span>
        </v-btn>
        <v-btn :class="activeBtn" height="30" depressed @click="select">
          <span>선택하기</span>
        </v-btn>
      </div>
    </div>
  </v-dialog>
</template>

<script>
import { commonMediaShop } from "@/views/mediaShop/mixins/commonMixins"

import modalHeader from "@/views/mediaShop/components/modalHeader"
import searchHeader from "./header/searchTableHeader"
import tableBody from "./body/searchTableBodyMain"
import selectLocalerSlide from "./footer/searchTableFooterMain"

export default {
  mixins: [commonMediaShop],
  components: { modalHeader, searchHeader, tableBody, selectLocalerSlide },
  props: {
    dialog: {
      type: Boolean,
      required: true,
      default: false,
    },
    shareBanner: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      localers: [],
      selectLocalers: [],
      totalNum: 0,
      pageStartNo: 0,

      formData: {},
    }
  },
  created() {
    this.formData = this.initFormData()

    this.reqLocalers()
  },
  computed: {
    activeBtn() {
      return this.selectLocalers.length === 0
        ? "select-btn"
        : "select-btn active"
    },
  },
  methods: {
    select() {
      let selectLocaler = ""
      const len = this.selectLocalers.length

      if (len > 0) {
        selectLocaler =
          len > 1
            ? `${this.selectLocalers[0].name} 외 ${len - 1}곳`
            : this.selectLocalers[0].name
      }

      const selectLocalersInfo = {
        selectLocalers: this.selectLocalers,
        selectLocaler,
      }
      this.$emit("selectLocalers", selectLocalersInfo)
      this.close()
    },
    close() {
      this.$emit("update:dialog", false)
    },
    reqSearchLocalers(searchInfo) {
      if (searchInfo.filterType.type === this.FILTER_TYPE.LOCAL_NAME) {
        this.formData.localerName = searchInfo.searchText
      } else {
        this.formData.companyType = searchInfo.searchText
      }

      this.reqLocalers()
    },
    reqPageInLocaler(pageInfo) {
      this.reqLocalers(pageInfo.startNo)
    },
    reqAddrInLocaler(addr) {
      this.formData.areaCode = addr.code

      this.reqLocalers()
    },
    reqLocalers(startNo = 0) {
      this.formData.pageStartNo = startNo
      this.formData.bannerType = this.shareBanner.bannerType

      this.$BaseCommon.restful
        .call2("/mediashop/getlocalerlist", this.formData)
        .then((res) => {
          this.setLocaler(res.localers)
          this.totalNum = res.totalNum
        })
        .catch((res) => {
          console.log("/mediashop/getlocalerlist >> ", res)
        })
    },
    setLocaler(res) {
      this.localers = []

      for (const data of res) {
        const localer = {
          check: false,
          no: data.rowNum,
          localboxCode: data.localboxCode,
          memberCode: data.memberCode,
          memberId: data.memberId,
          profileImg: data.profileUrl,
          name:
            data.nickname.defaultVal("") === ""
              ? data.companyName.defaultVal("") === ""
                ? data.memberName
                : data.companyName
              : data.nickname,

          company: data.companyType,
          addr: data.address,
        }

        this.localers = this.localers.concat(localer)
      }

      for (let localer of this.localers) {
        for (const selectLocaler of this.selectLocalers) {
          if (selectLocaler.localboxCode === localer.localboxCode) {
            localer.check = true
            break
          }
        }
      }
    },
    headerCheck(check) {
      this.localers = this.localers.map((localer) => {
        localer.check = check

        return localer
      })

      this.setAllselectLocalers(check)
    },
    closeChip(chip) {
      this.localers = this.localers.map((localer) =>
        localer.localboxCode === chip.localboxCode
          ? { ...localer, check: !localer.check }
          : localer
      )

      this.removeSelectLocalers(chip)
    },
    setAllselectLocalers(check) {
      if (check) {
        for (const localer of this.localers) {
          if (!this.selectLocalers.includes(localer)) {
            this.selectLocalers = this.selectLocalers.concat(localer)
          }
        }
      } else {
        for (const localer of this.localers) {
          this.removeSelectLocalers(localer)
        }
      }
    },
    setSelectLocalers(localer) {
      if (!localer.check) {
        this.selectLocalers = this.selectLocalers.concat(localer)
      } else {
        this.removeSelectLocalers(localer)
      }
    },
    removeSelectLocalers(localer) {
      this.selectLocalers = this.selectLocalers.filter(
        (selectLocaler) => selectLocaler.localboxCode !== localer.localboxCode
      )
    },
  },
}
</script>

<style lang="scss" scoped>
p {
  padding: 0;
  margin: 0;
}

.content-wrap {
  padding: 0 70px;

  .title-wrap {
    margin-top: 48px;
    text-align: start;
  }

  .main-wrap {
    padding: 0 8px;

    .table-wrap {
      margin-top: 38px;
    }
  }
}

.footer-wrap {
  margin-top: 46px;

  .slide-wrap {
    background-color: #fafafa;
    padding-bottom: 8px;
  }

  .footer-btn-wrap {
    display: flex;
    justify-content: space-between;

    margin-top: 44px;
    padding: 15px 0;

    background-image: linear-gradient(to right, #e2d9d2 33%, #ffffff00 0%);
    background-position: top;
    background-size: 35px 2px;
    background-repeat: repeat-x;

    .back-btn {
      padding: 0;
      margin-left: 20px;
      min-width: 0 !important;
    }

    .select-btn {
      margin-right: 20px;
      background-color: #e0e0e0 !important;

      span {
        font: {
          size: 14px;
          weight: 600;
        }
        color: #aaaaaa;
      }
      &.active {
        background-color: #3e2723 !important;

        span {
          color: white;
        }
      }
    }
  }
}

@media (min-width: $grid-breakpoints_sm) and (max-width: $grid-breakpoints_lg) {
  .content-wrap {
    padding: 0 30px;

    .main-wrap {
      padding: 0;
    }
  }
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .content-wrap {
    padding: 0;

    .main-wrap {
      padding: 0;

      .table-wrap {
        margin-top: 30px;
      }
    }
  }
}

::v-deep {
  .v-dialog {
    padding: 0;
    margin: 0;
    background: white !important;
    width: 740px;
  }

  @media (min-width: $grid-breakpoints_sm) and (max-width: $grid-breakpoints_lg) {
    .v-dialog {
      width: 600px;
    }
  }

  @media screen and (max-width: $grid-breakpoints_sm) {
    .v-dialog {
      width: 320px;
    }

    .content-wrap {
      padding-left: 10px;
      padding-right: 10px;
    }
  }
}
</style>
