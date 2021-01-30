<template lang="pug">
  div
    v-text-field(v-if="isTextBox" dense filled flat rounded hide-details
                 v-model="searchText" @click="searchShow" @click:append="searchShow" :clearable="true"
                 item-color="#3e2723" color="#3e2723" append-icon="far fa-search" class="text-left searchbar")
    v-btn(v-else icon @click="searchShow" )
      v-icon(color="#3e2723" size="20") fas fa-search
    v-dialog(v-model="dialog" fullscreen hide-overlay transition="dialog-top-transition" scrollable)
      v-card(outlined class="overflow-hidden searchDialog elevation-0")
        v-card-title(class="pa-0" color="white")
          v-app-bar(class="searchBar elevation-0" color="white")
            v-btn(small icon @click="searchclose")
              v-icon(fas fa-chevron-left)
            v-text-field(ref="txtSearch" class="pl-2 pr-2 pt-0 txtsearch ondaji_font_16_medium_grey_60" placeholder="검색어를 입력해주세요"
                          hide-details clearable flat rounded outlined height="30px" dense width="100%" append-icon="fas fa-search" 
                          v-model="searchText" :loading="isLoading" @keyup="keyup" 
                          @click:append="search" @keydown.enter="search" @focus="keywordSearch"
                          )
        v-card-text(class="pt-2")
          v-list
            v-list-item(v-for="(item, i) in keywordItems" @click="itemSelect(item)" :key="i")
              v-list-item-content
                v-list-item-title(v-html="changeKeywordCss(item.keywordName)")
        v-card-actions(class="elevation-0")
          v-spacer
          v-btn(text @click="searchclose") 닫기
</template>
<style lang="scss">
.searchDialog {
  .v-toolbar__content {
    margin-top: 1rem;
  }
}
.searchBar {
  .v-icon {
    font-size: 1.125rem !important;
  }
  .v-input__icon--append {
    border-left: solid 2px #d1c8c6;
    padding-left: 1.4em;
    padding-right: 0.4em !important;
  }
  .v-icon.primary--text {
    color: #3e2723 !important;
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
      border-color: #d1c8c6 !important;
    }
    .v-input__icon--clear {
      button {
        color: #aaaaaa !important;
        padding-right: 0.75em;
      }
    }
  }
  margin-top: 2rem;
  text-align: left !important;

  input {
    text-align: left;
  }
  .v-input__icon--clear {
    .v-icon.v-icon.v-icon--link {
      color: #aaaaaa !important;
      padding-right: 0.75em;
    }
  }
}
</style>
<script>
export default {
  props: {
    isTextBox: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      isLoading: false,
      searchText: "",
      dialog: false,
      bAuto: false,
      searchTimer: null,
      selectItem: null,
      keywordItems: [],
      searchParam: null,
    }
  },
  watch: {
    dialog(val) {
      setTimeout(() => {
        if (val) this.$refs.txtSearch.$el.querySelector("input").focus()
      }, 100)
    },
  },
  mounted() {},
  methods: {
    keyup(e) {
      if (e.keyCode >= 37 && e.keyCode <= 40) {
        return
      } else if (e.keyCode === 13) {
        this.itemSelect({ keywordName: this.searchText })
        return
      }
      this.isLoading = true
      console.log("검색중...", this.searchText)
      this.keywordSearch()
    },
    changeKeywordCss(val) {
      return this.$BaseCommon.stringInfo.changeKeywordCss(val, this.searchText)
    },

    keywordSearch() {
      let cond = {
        keywordCode: this.searchText === "" ? -1 : null,
        keywordName: this.searchText,
      }
      if (this.keywordSearch != null) {
        clearTimeout(this.searchTimer)
      }
      this.searchTimer = setTimeout(() => {
        this.bAuto = false
        console.log("search Change >>", this.searchText)

        this.getAutoKeyword(cond)
        console.log(this.searchText, 1)
      }, 200)
    },
    getAutoKeyword(cond) {
      this.searchParam = cond
      this.isLoading = true
      this.keywordItems = []
      console.log("서치증>>>>", cond)
      this.$BaseCommon.restful
        .call2("/keyword/getautolist", cond)
        .then((res) => {
          this.keywordItems = [...res]
          console.log(this.keywordItems)
          this.isLoading = false
        })
        .catch((err) => {
          this.isLoading = false
          console.log(err)
        })
    },
    searchShow() {
      if (!this.dialog) this.dialog = true
    },
    search() {
      this.itemSelect({ keywordName: this.searchText })
    },
    searchclose() {
      this.dialog = false
    },
    itemSelect(item) {
      console.log("선택됨", item)
      this.selectItem = item
      this.searchText = item.keywordName
      /*if (this.$BaseCommon.hostInfo.getCurrentName() === "usersearch") {
        this.$EventBus.$emit("keywordSearch", item)
      } else this.$router.push({ name: "usersearch", params: item })
*/
      this.$emit("callback", item)
      this.dialog = false
    },
  },
}
</script>
