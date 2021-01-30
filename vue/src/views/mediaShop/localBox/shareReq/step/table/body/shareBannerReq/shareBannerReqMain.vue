<template>
  <v-container class="wrap">
    <v-row class="main-wrap" no-gutters>
      <div>
        <div class="req-title-wrap ondaji_font_14_bold_brown_100">
          배너공유 요청서
        </div>

        <div class="content-wrap">
          <!-- 로컬러 -->
          <div class="title-wrap">
            <p class="content-title ondaji_font_14_bold_brown_70">
              로컬러
            </p>
            <div class="search-wrap">
              <p class="ondaji_font_12_medium_grey_60">
                {{ selectLocalersInfo.selectLocaler }}
              </p>
              <v-btn
                class="search-btn"
                depressed
                rounded
                @click="searchTableDialog = true"
              >
                <span class="ondaji_font_12_bold_brown_70">
                  검색
                </span>
              </v-btn>
            </div>
          </div>

          <!-- 요청 사유 -->
          <div class="req-reason-wrap">
            <p class="content-title ondaji_font_14_bold_brown_70">
              요청 사유
            </p>
            <div class="search-wrap">
              <v-textarea
                v-model="reqReason"
                class="req-input"
                dense
                solo
                auto-grow
                rows="1"
                row-height="32"
                hide-details
                flat
                maxlength="200"
                placeholder="요청 사유를 입력해주세요."
              />
            </div>
          </div>

          <!-- 게시 기간 -->
          <div class="date-wrap">
            <p class="date-title ondaji_font_14_bold_brown_70">
              게시 기간
            </p>
            <datePicker ref="refDatePicker" class="datePicker" />
          </div>

          <!-- 배너 내용 -->
          <div class="banner-content-wrap">
            <p class="banner-content-title ondaji_font_14_bold_brown_70">
              배너 내용
            </p>
            <bannerContent class="banner-content" :shareBanner="info" />
          </div>
        </div>
      </div>
    </v-row>

    <searchTable
      :dialog.sync="searchTableDialog"
      :shareBanner="info"
      @selectLocalers="selectLocalers"
    />
  </v-container>
</template>

<script>
import datePicker from '@/views/mediaShop/localBox/shareReq/components/datePicker'
import bannerContent from './shareBannerContentMain'
import searchTable from '../../searchTableMain'

export default {
  components: {
    datePicker,
    bannerContent,
    searchTable,
  },
  props: {
    info: {
      type: Object,
    },
  },
  data() {
    return {
      reqReason: '',

      selectLocalersInfo: {
        selectLocalers: [],
      },
      searchTableDialog: false,
    }
  },
  watch: {
    info() {},
  },
  methods: {
    selectLocalers(info) {
      this.selectLocalersInfo = info
    },
    getShareInfo() {
      const date = this.$refs.refDatePicker.$el
        .querySelector('input')
        .value.split(' ')

      let startDate = ''
      let endDate = ''

      if (date[0] !== '') {
        startDate = date[0].replaceAll('-', '')
        endDate = date[2].replaceAll('-', '')
      }

      const shareInfo = {
        reqReason: this.reqReason,
        startDate,
        endDate,
        selectLocalersInfo: this.selectLocalersInfo.selectLocalers.map(
          (localer) => localer.memberCode
        ),
      }

      return shareInfo
    },
  },
}
</script>

<style lang="scss" scoped>
$space-top: 24px;
$space-bottom: 12px;

p {
  padding: 0;
  margin: 0;
}

.wrap {
  display: flex;
  justify-content: center;
  margin-top: 32px;
  padding: 0;
}

.main-wrap {
  display: flex;
  justify-content: center;

  background-image: linear-gradient(to right, #e2d9d2 33%, #ffffff00 0%);
  background-position: top;
  background-size: 35px 2px;
  background-repeat: repeat-x;
}

.req-title-wrap {
  margin-top: 32px;
  text-align: start;
}

.content-wrap {
  width: 418px;
  border: 2px solid #d1c8c6;
  border-radius: 3px;
  padding: 24px 32px 20px 32px;
  margin-top: 22px;
  text-align: start;
}

.req-reason-wrap,
.date-wrap,
.banner-content-wrap {
  margin-top: $space-top;
}

.banner-content {
  margin-top: 12px;
}

.date-title,
.content-title {
  margin-bottom: $space-bottom;
}

.search-wrap {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-btn {
  height: 24px !important;
  width: 50px !important;
  min-width: 50px !important;
  padding: 0;
  background-color: white !important;
  border: 2px solid #938783;

  &:hover {
    background-color: #e2d9d2 !important;
  }
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .content-wrap {
    width: 300px;
    padding: 10px;
  }
}

::v-deep {
  .v-input {
    border: 1px solid #bdbdbd !important;
    padding: 0 10px;
    &:focus,
    &:active,
    &:hover {
      outline: none !important;
      border: 1px solid #bbdefb !important;
    }
  }

  .v-input__slot {
    padding: 0 !important;
  }

  .v-text-field__slot {
    padding: 0 !important;

    textarea {
      margin: 0;
      line-height: 28px !important;
    }
  }
}
</style>
