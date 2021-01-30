<template lang="pug">
  v-container
    v-row
      v-col()
        v-radio-group(v-model="itemGroup" :label="label"  class="d-flex mt-0")
          v-container(class="py-0 px-1" :fluid="false")
            v-row
              v-col(v-for="item in items" :key="item.subCode" cols="6" class="py-0 px-2 buttons")
                v-radio(:label="item[params.disColumnName || 'name']" :value="item.subCode" cols="6" )
            
</template>
<style lang="scss" scoped>
.v-input--radio-group.v-input--radio-group--column {
  width: fit-content;
  .buttons {
    white-space: nowrap;
    min-width: 5rem;
  }
}
</style>
<script>
export default {
  props: {
    value: {
      type: Number,
      default: 1,
    },
    params: {
      type: Object,
      default: function () {
        return {
          mainCode: "A001",
          subCode: -1,
          disColumnName: "name",
        }
      },
    },
    label: {
      default: "유형",
    },
  },
  created() {
    this.itemGroup = this.value
    this.doQuery()
  },
  data() {
    return {
      itemGroup: 1,
      items: [],
    }
  },
  methods: {
    doQuery() {
      this.$BaseCommon.restful
        .call("/common/getCommonList", this.params)
        .then((res) => {
          this.items = res
          console.log(res)
        })
    },
  },
}
</script>
