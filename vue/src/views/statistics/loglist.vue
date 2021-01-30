<template>
  <v-container>
    <v-row align="start" justify="start">
      <v-col cols="12">
        <Vuetify-menutitle />
      </v-col>
      <v-col cols="12" class="mt-0 pt-0">
        <v-card outlined>
          <v-card-title>
            <v-text-field
              v-model="search"
              append-icon="search"
              label="검색어를 입력하세요"
              single-line
              hide-details
            ></v-text-field>
            <v-spacer></v-spacer>
            <Vuetify-button
              class="ma-2"
              :name="'btnQuery'"
              :btntype="this.$Config.vuetify.btn.type.default"
              :text="'갱신'"
              @click="doQuery(1)"
            ></Vuetify-button>
          </v-card-title>
          <v-data-table
            dense
            fixed-header
            height="600"
            class="elevation-1"
            :headers="headers"
            :items="dataList"
            :search="search"
            :items-per-page="pageSize"
            hide-default-footer
          >
          </v-data-table>
          <div class="text-center pa-2">
            <v-pagination
              v-model="page"
              @input="pageChange"
              @next="pageNext"
              @previous="pagePrevious"
              circle
              :length="pageCount"
              :total-visible="pageVisible"
            ></v-pagination>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
export default {
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({ el: this.$el, isTop: true })
  },
  created() {
    this.doQuery(1)
  },
  data() {
    return {
      search: "",

      headers: [
        { text: "코드", value: "id" },
        { text: "회원코드", value: "memberCode" },
        { text: "회원명", value: "memberName" },
        { text: "페이지명", value: "pageName" },
        { text: "url", value: "pageUrl" },
        { text: "기기유형", value: "deviceType" },
        { text: "등록일", value: "regDate" },
      ],
      dataList: [],
      pageCount: 0,
      page: 1,
      pageSize: 100,
      pageVisible: 7,
    }
  },
  methods: {
    doQuery(page) {
      this.page = page === undefined ? this.page : page
      this.$BaseCommon.restful
        .call("/mongodb/logpagelist", {
          memberCode: null,
          page: this.page,
          pageSize: this.pageSize,
          searchText: "",
        })
        .then((res) => {
          this.dataList = res.list
          this.pageCount =
            Math.floor(res.rowCount / (this.pageSize * 1.0)) +
            (res.rowCount % this.pageSize > 0 ? 1 : 0)
          console.log(res)
        })
    },
    pageChange(page) {
      console.log("페이지>>", page)
      this.page = page
      this.doQuery()
    },
    pagePrevious() {
      //this.page = this.page - this.pageSize < 0 ? 0 : this.page - this.pageSize;
      console.log("페이지Previous>>", this.page)
      this.doQuery()
    },
    pageNext() {
      //this.page = this.page + this.pageSize > this.pageCount ? this.pageCount : this.page + this.pageSize;
      console.log("페이지Next>>", this.page)
      this.doQuery()
    },
  },
}
</script>
