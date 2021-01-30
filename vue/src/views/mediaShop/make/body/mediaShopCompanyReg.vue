<template>
  <v-container class="pa-0 text-start" fluid>
    <v-row class="px-2 px-lg-0 py-3" no-gutters>
      <v-col cols="12" lg="5" offset-lg="2">
        <!-- 주소 -->
        <div class="content-box" no-gutters>
          <div class="content-title-box ondaji_font_16_bold_brown_80">주소</div>

          <div class="content-title-box ondaji_font_14_bold_brown_70">
            우편번호
          </div>
          <v-col class="read-only-box" cols="12" sm="2">
            {{ input.zoneCode }}
          </v-col>

          <div class="content-title-box ondaji_font_14_bold_brown_70">주소</div>
          <v-col class="d-inline-flex read-only-box address" cols="12" sm="9">
            {{ input.address }}
          </v-col>
          <v-col class="float-sm-right pa-0 mb-4 mb-sm-0" cols="12" sm="2">
            <v-btn
              class="search-address-btn"
              depressed
              @click="searchAddrDialog = !searchAddrDialog"
            >
              <span class="ondaji_font_14_medium_white">주소검색</span></v-btn
            >
          </v-col>

          <v-col class="d-flex pa-0" cols="12" sm="9">
            <v-text-field
              v-model="input.subAddress"
              label="상세주소를 입력해 주세요"
              background-color="white"
              color="grey lighten-1"
              single-line
              dense
              maxlength="50"
            />
          </v-col>

          <div class="content-title-box ondaji_font_12_medium_brown_70">
            *주소지 중심 1km 직경은 로컬박스 광고 영역으로 활용됩니다.
          </div>

          <v-row justify="center" no-gutters>
            <mapModal
              ref="map"
              :posInfo="posInfo"
              :dialog.sync="dialog"
              @close="modalClose"
            ></mapModal>
          </v-row>

          <v-btn
            class="range-confirm-btn"
            color="tranparent"
            width="110"
            height="34"
            depressed
            @click="dialog = true"
          >
            <div class="ondaji_font_14_bold_brown_70">
              광고영역 확인
            </div></v-btn
          >
        </div>

        <!-- 선택 입력사항 -->
        <v-col class="d-flex pa-0 mt-11">
          <v-col class="reg-divider pa-0" cols="2"></v-col>
          <v-col class="text-center pa-0" cols="4" sm="2"
            ><span class="ondaji_font_14_bold_grey_50">
              선택 입력사항
            </span></v-col
          >
          <v-col class="reg-divider pa-0" cols="6" sm="8"></v-col>
        </v-col>

        <!-- 영업시간 -->
        <v-row class="content-box" no-gutters>
          <v-col cols="12">
            <div class="content-title-box ondaji_font_16_bold_brown_80">
              영업시간
            </div>
            <v-text-field
              v-model="input.openTime"
              label="화~일 10:00~18:00, 월요일 휴무"
              background-color="white"
              color="grey lighten-1"
              single-line
              dense
              maxlength="50"
            ></v-text-field>
          </v-col>
        </v-row>

        <!-- 연락처 -->
        <v-row class="content-box" no-gutters>
          <v-col cols="12">
            <div class="content-title-box ondaji_font_16_bold_brown_80">
              연락처
            </div>
            <Vuetify-phoneInput
              :value="input.phoneNumber"
              :info="phoneInputInfo"
              @phoneInputCallback="resPhone"
            >
            </Vuetify-phoneInput>
          </v-col>
        </v-row>

        <!-- 공지사항 -->
        <v-row class="content-box" no-gutters>
          <v-col cols="12">
            <div class="content-title-box ondaji_font_16_bold_brown_80">
              공지사항
            </div>
            <v-text-field
              v-model="input.notice"
              label="리뉴얼 오픈 공사 예정, 양해 부탁드립니다."
              background-color="white"
              color="grey lighten-1"
              single-line
              dense
              maxlength="50"
            ></v-text-field>
          </v-col>
        </v-row>

        <!-- 버튼 -->
        <v-row class="btn-box" no-gutters>
          <div class="mr-auto">
            <v-btn class="prev-btn" rounded depressed @click="prevPage()">
              <span class="ondaji_font_14_bold_brown_80"
                ><v-icon class="btn-left-icon">fas fa-chevron-left</v-icon
                >이전</span
              >
            </v-btn>
          </div>

          <div class="ml-auto">
            <v-btn class="complete-btn" depressed @click="complete()">
              <span class="ondaji_font_12_bold_white">{{
                completeBtnText
              }}</span>
            </v-btn>
          </div>
        </v-row>

        <!-- 주소검색 모달 -->
        <postCode
          :dialog.sync="searchAddrDialog"
          :radius="posInfo.radius"
          @getPostCode="getPostCode"
          @close="close"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import postCode from '@/views/mediaShop/components/postCode'
import mapModal from '@/views/mediaShop/components/mapModal'

export default {
  components: {
    postCode,
    mapModal,
  },
  props: {
    info: {
      type: Object,
      required: false,
    },
    call: {
      type: Boolean,
      required: false,
    },
  },
  data() {
    return {
      input: {
        zoneCode: ``,
        address: ``,
        subAddress: ``,
        openTime: ``,
        phoneNumber: ``,
        notice: ``,
      },
      posInfo: {
        lat: 37.512091,
        longi: 127.044348,
        radius: 500,
      },
      rangeInfo: {},

      searchAddrDialog: false,
      dialog: false,

      completeBtnText: `완료하기`,

      mapInfo: new Object(),

      phoneInputInfo: {
        class: `background-white`,
        rounded: false,
        filled: true,
        backgroundColor: `white`,
        color: `grey lighten-1`,
      },
    }
  },
  created() {
    if ((Object.keys(this.info).length === 0) === false) {
      this.initInfo()
    }
  },
  methods: {
    initInfo() {
      this.input = this.info
      this.rangeInfo = this.info.rangeInfo

      this.posInfo.lat = this.rangeInfo.latitude
      this.posInfo.longi = this.rangeInfo.longitude
      this.posInfo.latitude = this.rangeInfo.latitude
      this.posInfo.longitude = this.rangeInfo.longitude

      this.posInfo = Object.assign({}, this.posInfo)

      if (this.call === true) {
        this.completeBtnText = `저장하기`
      }
    },
    getPostCode(post) {
      this.input.zoneCode = post.zonecode
      this.input.address = post.address

      this.posInfo = Object.assign({}, post)

      this.rangeInfo = post
      this.rangeInfo.radius = this.posInfo.radius

      this.close()
    },
    close() {
      this.searchAddrDialog = false
    },
    resPhone(phoneNumber) {
      this.input.phoneNumber = phoneNumber
    },
    prevPage() {
      this.$emit('prevPage', this.getInfo())
    },
    complete() {
      this.$emit('complete', this.getInfo())
    },
    getInfo() {
      this.rangeInfo.address = this.input.address
      this.rangeInfo.subAddress = this.input.subAddress
      this.rangeInfo.zonecode = this.input.zoneCode

      if ((Object.keys(this.rangeInfo).length === 0) === false) {
        this.input.rangeInfo = this.rangeInfo
      }

      return this.input
    },
    modalClose(posInfo) {
      console.log(posInfo)
    },
  },
}
</script>

<style lang="scss" scoped>
.read-only-box {
  height: 40px;
  line-height: 32px;
  padding: 4px 12px;
  margin-bottom: 20px;
  background: white;
  &.address {
    margin-bottom: 10px;
  }
  color: #000000de;
}

.search-address-btn {
  background: #938783 !important;
  width: 84px !important;
  height: 32px !important;
  margin-top: 3px;
  margin-left: auto;
}

.range-confirm-btn {
  border: 3px solid #938783 !important;
  &:hover {
    background: #e2d9d2 !important;
  }
}

.reg-divider {
  height: 2px;
  background: #e0e0e0;
  margin-top: 8px;
}

.content-box {
  margin-top: 20px;
}

.content-title-box {
  margin-bottom: 12px;
}

.btn-box {
  margin-top: 28px;
  margin-bottom: 40px;
}

.prev-btn {
  width: 88px !important;
  height: 30px !important;
  border: 2px solid #3e2723;
  background: transparent !important;
  &:hover {
    background: #e2d9d2 !important;
  }
}

.btn-left-icon {
  margin-right: 28px;
  font-size: 0.875rem;
}

.complete-btn {
  width: 68px !important;
  height: 28px !important;
  background: #3e2723 !important;
}

::v-deep {
  .v-input__slot {
    padding: 4px 12px !important;
    border: 0 !important;
  }

  .v-input__slot::before {
    border: 0 !important;
  }

  .background-white .v-label {
    line-height: 13px;
  }

  .v-label {
    font-weight: 400;
    font-size: 0.85rem;
    color: #aaa19f !important;
  }

  .v-text-field.v-input--dense:not(.v-text-field--outlined) input {
    padding: 10px 0 10px;
  }
}
</style>
