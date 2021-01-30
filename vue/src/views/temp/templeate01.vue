<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <div class="d-flex align-center">
        <v-img
          alt="Vuetify Logo"
          class="shrink mr-2"
          contain
          src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
          transition="scale-transition"
          width="40"
        />

        <v-img
          alt="Vuetify Name"
          class="shrink mt-1 hidden-sm-and-down"
          contain
          min-width="100"
          src="https://cdn.vuetifyjs.com/images/logos/vuetify-name-dark.png"
          width="100"
        />
      </div>

      <v-spacer></v-spacer>

      <v-btn
        href="https://github.com/vuetifyjs/vuetify/releases/latest"
        target="_blank"
        text
      >
        <span class="mr-2">Latest Release</span>
        <v-icon>mdi-open-in-new</v-icon>
      </v-btn>
    </v-app-bar>

    <v-content>
      <v-row align="center" id="app">
        <v-col class="text-center" cols="12" sm="4">
          <div class="my-4">
            <h1>{{ msg }}</h1>

            <img src="@/assets/images/samsung.png" />
            <v-btn small color="primary" v-on:click="btnClick">조회하기</v-btn>
            <template>
              <v-data-table
                v-model="selected"
                :headers="headers"
                :items="memberlist"
                show-select
                item-key="name"
                class="elevation-1"
                :footer-props="footerProps"
                @update:items-per-page="getItemPerPage"
              >
                <template slot="headers" slot-scope="props">
                  <tr>
                    <th>
                      <v-checkbox
                        :input-value="props.all"
                        :indeterminate="props.indeterminate"
                        primary
                        hide-details
                        @click.native="toggleAll"
                      ></v-checkbox>
                    </th>
                    <th
                      v-for="header in props.headers"
                      :key="header.text"
                      :class="[
                        'column sortable',
                        pagination.descending ? 'desc' : 'asc',
                        header.value === pagination.sortBy ? 'active' : '',
                      ]"
                      @click="changeSort(header.value)"
                    >
                      <v-icon small>arrow_upward</v-icon>
                      {{ header.text }}
                    </th>
                  </tr>
                </template>

                <template slot="items" slot-scope="props">
                  <tr
                    :active="props.selected"
                    @click="props.selected = !props.selected"
                  >
                    <td class="text-xs-right">{{ props.item.memberCode }}</td>
                    <td class="text-xs-right">
                      {{ props.item.memberName }} dd
                    </td>
                    <td class="text-xs-right">{{ props.item.insertDate }}</td>
                    <td class="text-xs-center">{{ props.item.udpate_date }}</td>
                  </tr>
                </template>
              </v-data-table>
            </template>
            <table>
              <thead>
                <tr>
                  <th>아이디</th>
                  <th>이름</th>
                  <th>등록일</th>
                  <th>수정일</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="p in memberlist" :key="p.memberCode">
                  <td>{{ p.memberId }}</td>
                  <td>{{ p.memberName }}</td>
                  <td>{{ p.insertDate }}</td>
                  <td>{{ p.updateDate }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </v-col>
      </v-row>
    </v-content>
  </v-app>
</template>

<script>
import ax from "axios"
export const axios = ax
import { BaseCommon } from "@/assets/scripts/Common"

export default {
  data() {
    return {
      isNav: true,
      footerProps: { "items-per-page-options": [5, 10, 15, 30, 50, 100] },
      pagination: {
        sortBy: "memberName",
      },
      selected: [],
      headers: [
        {
          text: "아이디",
          align: "left",
          value: "memberId",
        },
        {
          text: "이름",
          align: "center",
          value: "memberName",
        },
        {
          text: "등록일",
          align: "center",
          value: "insertDate",
        },
        {
          text: "수정일",
          align: "center",
          value: "updateDate",
        },
      ],
      msg: "안녕하세요 로그인이에요",
      memberlist: [],
    }
  },
  methods: {
    btnClick: function(event) {
      BaseCommon.restful
        .call("/member/memberlist", null, "get")
        .then((response) => {
          // JSON responses are automatically parsed.
          console.log(response)

          this.memberlist = response
        })
        .catch((e) => {
          this.errors.push(e)
        })

      if (event) {
        this.$alert(event.target.tagName)
      }
    },
    toggleAll() {
      if (this.selected.length) this.selected = []
      else this.selected = this.memberlist.slice()
    },
    changeSort(column) {
      if (this.pagination.sortBy === column) {
        this.pagination.descending = !this.pagination.descending
      } else {
        this.pagination.sortBy = column
        this.pagination.descending = false
      }
    },
    getItemPerPage(val) {
      console.log(val)
    },
  },
}
</script>
