<template>
  <v-container class="px-0 py-1">
    <v-row class="empty-background" no-gutters>
      <v-col cols="12" class="pa-0">
        <v-container
          style="max-width:600px"
          class="mx-auto text-center text-sm-start"
        >
          <v-row align="center" no-gutters>
            <v-col class="d-felx" cols="12" sm="3">
              <v-img
                contain
                height="180"
                class="imgSearch ma-auto"
                src="@/assets/images/banner_icons/emptysearch.svg"
              >
              </v-img>
            </v-col>
            <v-col class="pl-0 pl-sm-8 pt-3 pt-sm-0" cols="12" sm="9">
              <v-row no-gutters>
                <v-col class="pt-2" cols="12">
                  <div class="empty-title">
                    <span v-if="keyword_name !== ''">
                      <span class="spKeyword">검색어</span>
                      <span style="color:#0ba880">
                        '{{ this.keyword_name }}'</span
                      >의</span
                    >
                    검색 결과가 없습니다.
                  </div>
                </v-col>
              </v-row>

              <v-row no-gutters>
                <v-col v-if="keyword_name !== ''" class="pt-3 " cols="12">
                  <span class="empty-sub-title">
                    -
                  </span>
                  <span class="empty-sub-title ml-1">
                    동네를 변경해보세요.
                  </span>
                </v-col>
                <v-col class="pt-1 pt-sm-3" cols="12">
                  <span class="empty-sub-title">
                    -
                  </span>
                  <span class="empty-sub-title ml-1">
                    단어의 철자가 정확한지 확인해 보세요.
                  </span>
                </v-col>

                <v-col class="pt-1 pt-sm-3" cols="12">
                  <span class="empty-sub-title">
                    -
                  </span>
                  <span class="empty-sub-title ml-1">
                    두 단어 이상의 검색어인 경우, 띄어쓰기를 확인해 보세요.
                  </span>
                </v-col>

                <v-col class="pt-1 pt-sm-3" cols="12">
                  <span class="empty-sub-title">
                    -
                  </span>
                  <span class="empty-sub-title ml-1">
                    검색 옵션을 변경해서 다시 검색해 보세요.
                  </span>
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {}
</script>

<style lang="scss" scoped>
.empty-background {
  height: 24.313rem;
  padding-top: 1.875rem;
  background-color: transparent;
}

.empty-title {
  font-weight: bold;
  font-size: 1.125rem;
  color: #838383;
}

.empty-sub-title {
  font-weight: 500;
  font-size: 0.875rem;
  color: #757575;
}

@include mobile {
  .imgSearch {
    height: 130px !important;
    width: 130px !important;
  }
  .empty-background {
    padding-top: 0rem;
  }
  .spKeyword {
    display: none;
  }
  .empty-title {
    font-size: 16px;
  }
  .empty-sub-title {
    font-size: 12px;
  }
}
</style>

<script>
export default {
  props: {
    value: {
      type: String,
      default: "",
    },
  },
  created() {
    this.keyword_name = this.value
  },
  watch: {
    value(val) {
      this.keyword_name = val
    },
  },
  data() {
    return {
      keyword_name: "",
    }
  },
}
</script>
