<template lang="pug">
  div(ref="searchBar" class="searchbar")
    v-combobox( v-model="keyword"          :items="keywordItems"  :loading="isLoading"   :search-input.sync="keywordSearch"
                @change="searchChange"     @click:append="search" @keydown.enter="search" return-object
                item-text="keywordName"    item-value="keywordCode" :menu-props="{ auto: true, overflowY: true }"
                flat rounded outlined      no-filter :auto-select-first="true" hide-no-data dense hide-details
                item-color="#3e2723"       height="30px"  width="100%" append-icon="fas fa-search"
                :placeholder="placeholder" :clearable="true" @focus="fitrstListHidden")   
      template(v-slot:item="{ item }")
        span(v-html="changeKeywordCss(item.keywordName)")
</template>
<style lang="scss">
.searchbar {
  width: 100%;
  max-width: 500px;
  .notranslate {
    transform: none !important;
  }
  .v-input__icon--append {
    border-left: solid 2px #d1c8c6;
    padding-left: 1.5em;
    padding-right: 0.4em !important;
  }
  .v-icon.v-icon {
    font-size: 1.125rem;
  }

  .v-input__icon--append {
    border-color: #d1c8c6 !important;
    button {
      color: #3e2723 !important;
    }
  }
  .v-input--is-focused.primary--text {
    color: #64b5f6 !important;
    .v-input__icon--append {
      @extend .v-input__icon--append;
    }
  }
  .v-input__icon--clear {
    button {
      color: #aaaaaa !important;
      padding-right: 0.75em;
    }
  }
}
.v-select-list .v-list-item.firstAutoItem {
  display: none;
}
</style>

<script>
export default {
  props: {
    value: {
      type: Object,
    },
    placeholder: {
      type: String,
      default: "소소하고 유익한 지역정보를 검색해보세요:)",
    },
  },
  data() {
    return {
      isLoading: false,
      keywordItems: [],
      keyword: null,
      keywordSearch: null,
      bAuto: false,
      searchTimer: null,
      searchData: null,
    }
  },
  watch: {
    keywordSearch(val) {
      this.automenuShow()

      this.searchData = val === null ? "" : String(val)

      let cond = { keywordName: val }
      if (this.keywordSearch != null) {
        clearTimeout(this.searchTimer)
      }
      this.searchTimer = setTimeout(() => {
        this.bAuto = false
        console.log("search Change >>", val)

        this.getAutoKeyword(cond)
        console.log(val, 1)
      }, 200)
    },
  },
  mounted() {
    this.automenuShow(false)
    this.fitrstListHidden()
  },
  methods: {
    automenuShow(bTrue) {
      bTrue = bTrue == undefined ? true : bTrue
      if (bTrue) {
        try {
          if (document.querySelector(".v-menu__content--auto") !== null)
            document.querySelector(".v-menu__content--auto").style.display =
              "block"
        } catch (e) {
          console.log(e)
        }
      } else {
        try {
          if (document.querySelector(".v-menu__content--auto") !== null)
            document.querySelector(".v-menu__content--auto").style.display =
              "none"
        } catch (e) {
          console.log(e)
        }
      }
    },
    fitrstListHidden() {
      this.automenuShow()
      try {
        if (document.querySelector(".v-select-list") !== null)
          document
            .querySelector(".v-select-list")
            .classList.add("firstAutoItem")
      } catch (e) {
        console.log(e)
      }
    },
    changeKeywordCss(val) {
      return this.$BaseCommon.stringInfo.changeKeywordCss(val, this.searchData)
    },
    getAutoKeyword(cond) {
      this.isLoading = true
      this.$BaseCommon.restful
        .call2("/keyword/getautolist", cond)
        .then((res) => {
          let first = [{ keywordCode: null, keywordName: cond.keywordName }]

          if (
            res.filter((w) => w.keywordName === first.keywordName).length === 1
          )
            this.keywordItems = res
          else this.keywordItems = [...first, ...res]
          document
            .querySelector(".v-select-list .v-list-item")
            .classList.add("firstAutoItem")
          if (res.length === 0) {
            document.querySelector(".v-select-list").style.display = "none"
          } else {
            document.querySelector(".v-select-list").style.display = "block"
          }
          this.isLoading = false
          this.fitrstListHidden()
        })
        .catch((err) => {
          this.isLoading = false
          console.log(err)
        })
    },
    search() {
      this.automenuShow(false)

      if (typeof this.keyword === "string") {
        this.searchData = this.keyword
        this.keyword = null
      }
      let selCode = this.keyword
      if (this.keyword != null) {
        if (this.keyword.keywordName !== String(this.searchData)) {
          selCode = null
          this.keyword.keywordName = this.searchData
          this.keyword = { keywordCode: null, keywordName: this.searchData }
        }
      }

      this.$emit(
        "callback",
        selCode === null
          ? {
              keywordCode: null,
              keywordName: this.searchData,
            }
          : selCode
      )
    },
    searchChange(e) {
      this.searchData = e.keywordName
      this.search()
      console.log(e)
    },
  },
}
</script>
