<template>
  <v-container>
    <v-row align="start" justify="start">
      <v-col cols="12">
        <Vuetify-menutitle />
      </v-col>
      <v-col cols="12" class="mt-0 pt-0">
        <v-card outlined>
          <v-container class="pt-0 pb-0" fluid>
            <v-row>
              <v-col cols="12" sm="2">
                <v-select
                  v-model="selVal"
                  :items="selList"
                  item-text="text"
                  item-value="value"
                  label="조회선택"
                  outlined
                  dense
                  hide-details
                  @change="selChange"
                ></v-select>
              </v-col>
              <v-col cols="12" sm="4">
                <v-text-field
                  v-model="searchText"
                  class="pt-0"
                  placeholder="검색어를 입력하세요"
                  clear-icon="mdi-close-circle"
                  clearable
                  append-outer-icon="fas fa-search"
                  @click:append-outer="doQuery"
                  hide-details
                  @keydown.enter="doQuery"
                ></v-text-field>
              </v-col>
              <v-col sm="6" class="text-right">
                <Buttons-default
                  v-if="$BaseCommon.memberInfo.getMember().isLogin"
                  btnType="primary"
                  :size="40"
                  btnText="신규"
                  @click="btnNew"
                />
              </v-col>
            </v-row>
          </v-container>
        </v-card>
      </v-col>
      <v-col cols="12" class="mt-0 pt-0">
        <v-card outlined>
          <v-data-table
            :headers="headers"
            :items="dataList"
            :loading="isLoading"
            :server-items-length="totalRows"
            :options.sync="options"
            loading-text="Loading... Please wait"
            :items-per-page="itemPerPage"
            :footer-props="{
              'items-per-page-text': '행수',
              'page-text': String('$vuetify.dataFooter.pageText').replaceAll(
                'of',
                ''
              ),

              prevIcon: 'fas fa-angle-left',
              nextIcon: 'fas fa-angle-right',
            }"
          >
            <template v-slot:top>
              <v-toolbar flat color="white">
                <v-text-field
                  v-model="searchText"
                  class="pt-0"
                  label="검색어를 입력하세요"
                  clear-icon="mdi-close-circle"
                  clearable
                  append-outer-icon="fas fa-search"
                  @click:append-outer="doQuery"
                  hide-details
                  @keydown.enter="doQuery"
                ></v-text-field>
                <v-spacer></v-spacer>
                <v-btn color="teal" dark class="mb-2" @click="btnNew"
                  >신규</v-btn
                >
              </v-toolbar>
            </template>
            <template v-slot:[`item.isRead`]="{ item }">
              <v-icon
                small
                v-if="item.isRead"
                class="yellow--text text--darken-3"
                >far fa-envelope-open</v-icon
              >
              <v-icon small v-else class="gray--text"
                >far fa-envelope-open</v-icon
              >
            </template>
            <template v-slot:[`item.title`]="{ item }">
              <a
                href="javascript:void(0)"
                style="cursor: pointer"
                @click="boardRegMove(item.boardCode)"
                >{{ item.title }}</a
              >
            </template>
          </v-data-table>
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
    this.doQuery()
  },
  data() {
    return {
      selVal: { value: "title", text: "제목" },
      selList: [
        { value: "title", text: "제목" },
        { value: "content", text: "내용" },
        { value: "updateName", text: "최종수정자" },
      ],

      searchText: "",
      isLoading: false,
      itemPerPage: 5 /**페이자당 로우수 */,
      totalRows: 0,
      boardType: 1,
      options: {},
      headers: [
        { text: "", value: "isRead", width: 40 },
        { text: "제목", value: "title" },
        { text: "조회수", value: "searchCnt" },
        { text: "등록자", value: "insertName" },
        { text: "등록일", value: "insertDate" },
      ],
      dataList: [],
    }
  },
  watch: {
    options: {
      handler() {
        this.doQuery()
      },
      deep: true,
    },
  },
  methods: {
    doQuery() {
      new Promise((resolve, reject) => {
        console.log(resolve, reject)
        const { sortBy, sortDesc, page, itemsPerPage } = this.options
        console.log("new Promise >>", sortBy, sortDesc, page, itemsPerPage)
        let orderSort = ""
        if (sortBy !== undefined && sortBy.length > 0) {
          orderSort = sortBy[0] + " " + (sortDesc[0] === true ? "desc" : "asc")
        }

        let self = this
        this.isLoading = true
        this.$BaseCommon.restful
          .call2("/board/getlist", {
            boardType: this.boardType,
            startRow: (page - 1) * this.itemPerPage,
            rowSize: this.itemPerPage,
            sortBy: orderSort,
          })
          .then((res) => {
            self.dataList = res
            if (self.dataList.length > 0)
              this.totalRows = self.dataList[0].totRows
            console.log("/board/getlist", res)
            this.isLoading = false
          })
          .catch((err) => {
            this.isLoading = false
            console.log("에러호출 getAnnounceList", err)
          })
      })
    },
    boardRegMove(boardCode) {
      this.$router.push({
        name: "boardregister",
        params: { boardType: this.boardType, boardCode: boardCode },
      })
      //this.$BaseCommon.hostInfo.redirect("/board/register",{boardType:this.boardType, boardCode : boardCode})
    },
    btnNew() {
      this.$BaseCommon.hostInfo.redirect("/board/register")
    },
    selChange(item) {
      this.selVal = this.selList.filter((w) => w.value === item)[0]
    },
  },
}
</script>
