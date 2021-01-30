<template>
  <v-container class="pa-0">
    <v-row no-gutters>
      <tableCategory @selectAddr="selectAddr" />
    </v-row>

    <v-row no-gutters>
      <v-col :class="tableWraps[0]">
        <tableMenu :len="localers.length" @headerCheck="headerCheck" />
      </v-col>
      <v-col :class="tableWraps[1]">
        <tableCard
          :class="tableWraps[2]"
          v-for="(localer, i) in localers"
          :key="i"
          :localer="localer"
          @check="localerCheck"
        />
      </v-col>
    </v-row>

    <Table-footer
      :info="{ totalNum: totalNum, PAGE_TOTAL_NUM: PAGE_TOTAL_NUM }"
      @getPageInfo="getPageInfo"
    />
  </v-container>
</template>

<script>
import { commonMediaShop } from '@/views/mediaShop/mixins/commonMixins'

import tableCategory from './searchTableBodyCategory'
import tableMenu from './searchTableBodyMenu'
import tableCard from './searchTableBodyCard'

export default {
  mixins: [commonMediaShop],

  components: {
    tableCategory,
    tableMenu,
    tableCard,
  },
  props: {
    localers: {
      type: Array,
      required: true,
    },
    totalNum: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      tableWraps: [],
    }
  },
  created() {
    this.tableWraps.length = 3

    if (this.getScreenSize() === 'xs') {
      this.tableWraps[0] = 'table-wrap col-2'
      this.tableWraps[1] = 'table-wrap col-10'
      this.tableWraps[2] = 'card-wrap'
    } else {
      this.tableWraps[0] = 'col-12'
    }
  },
  methods: {
    headerCheck(check) {
      this.$emit('headerCheck', check)
    },
    localerCheck(localer) {
      this.$emit('localerCheck', localer)
    },
    getPageInfo(pageInfo) {
      this.$emit('getPageInfo', pageInfo)
    },
    selectAddr(addr) {
      this.$emit('selectAddr', addr)
    },
  },
}
</script>

<style lang="scss" scoped>
.table-wrap {
  padding: 0;
}

.card-wrap:last-child {
  border-bottom: 0;
}
</style>
