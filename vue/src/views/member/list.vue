<template>
  <v-container fluid grid-list-xl>
    <v-layout wrap>
      <v-flex xs12 md12 class="pb-0">
        <v-breadcrumbs class="pl-0" :items="items">
          <template v-slot:divider>
            <v-icon>mdi-chevron-right</v-icon>
          </template>
        </v-breadcrumbs>
      </v-flex>
      <v-flex xs12 md12 class="pt-0">
        <h3 class="text-left pa">회원조회</h3>
      </v-flex>
      <v-flex xs12 md12>
        <v-card class="pl-5 pr-5">
          <v-layout>
            <v-flex xs12 md3>
              <v-text-field
                v-model="formData.memberId"
                prepend-icon="email"
                label="아이디(E-mail)"
                class="purple-input"
              />
            </v-flex>
            <v-flex xs12 md3>
              <v-text-field
                v-model="formData.memberName"
                prepend-icon="account_box"
                label="사용자이름"
                class="purple-input"
              />
            </v-flex>
            <v-flex xs12 md3>
              <Vuetify-selectsCommon
                :options="selectOption"
                class="mt-4"
                @selectChange="selectChange"
              >
              </Vuetify-selectsCommon>
            </v-flex>
            <v-flex xs12 md3 pa-5 text-md-right>
              <Vuetify-button
                :name="'btnQuery'"
                :btntype="Config.vuetify.btn.type.default"
                :text="'조회'"
                @btnQuery_Click="doQuery"
              ></Vuetify-button>
            </v-flex>
          </v-layout>
        </v-card>
      </v-flex>
      <v-flex xs12 md12>
        <v-card>
          <v-data-table
            :headers="dataTable.headers"
            :items="dataTable.list"
            :sort-by="['memberName', 'memberId']"
            :sort-desc="[false, true]"
            multi-sort
            class="elevation-1"
          >
            <template v-slot:[`item.birthDate`]="{ item }">
              {{
                BaseCommon.stringInfo.isNullEmpty(item.birthDate)
                  ? ""
                  : moment(item.birthDate).format("YYYY-MM-DD")
              }}
            </template>
            <template v-slot:[`item.memberId`]="{ item }">
              <a
                href="javascript:void(0)"
                style="cursor:pointer"
                @click="memberRegMove(item.memberCode)"
                >{{ item.memberId }}</a
              >
            </template>
          </v-data-table>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { Config } from "@/assets/scripts/Config"
export default {
  name: "memberlist",
  data: () => {
    return {
      BaseCommon: null,
      moment: null,
      Config: Config,
      items: [
        {
          text: "회원정보",
          disabled: false,
        },
        {
          text: "회원조회",
          disabled: false,
          href: "breadcrumbs_link_1",
        },
      ],
      selectOption: {
        mainCode: "A001",
        hide: false,
        label: "권한",
        value: null,
      },
      formData: {
        memberId: "",
        memberName: "",
        authType: 1,
      },
      dataTable: {
        headers: [
          { text: "코드", value: "memberCode", align: "left" },
          { text: "아이디", value: "memberId", align: "left" },
          { text: "회원명", value: "memberName", align: "left" },
          { text: "권한", value: "authTypeName", align: "center" },
          { text: "생년월일", value: "birthDate", align: "center" },
        ],
        list: [],
      },
    }
  },
  created() {
    this.BaseCommon = this.$BaseCommon
    this.moment = this.$moment
  },
  methods: {
    /** 컴포넌트 콜백 메서드 */
    selectChange: function(selectedData) {
      this.formData.authType = selectedData
    },
    doQuery: function() {
      this.$BaseCommon.restful
        .call("/member/getSearchList", this.formData)
        .then((res) => {
          this.dataTable.list = res
          console.log(res)
        })
    },
    memberRegMove: function(code) {
      console.log(code)
      this.$router.push({ name: "membermod", params: { memberCode: code } })
    },
    btnClick: function() {
      console.log("btnClick")
    },
  },
  mounted() {
    this.$store.watch(() => {
      this.$store.dispatch("baseStore/setIsShowBar", true)
    })
  },
}
</script>
