<template>
  <div>
    <v-select
      item-text="name"
      item-value="subCode"
      :items="itemlist"
      v-model="defaultSelected"
      :label="options.label"
      :rules="rules"
      dense
      class="pa-0 ma-0"
      @change="selectChange"
    ></v-select>
  </div>
</template>

<script>
export default {
  props: {
    options: {},
    value: {
      type: Number,
    },
    rules: {},
  },
  data: () => ({
    defaultSelected: {
      subCode: null,
      name: "-선택-",
    },
    itemlist: [{ subCode: null, name: "-선택-", bSelect: true }],
  }),
  watch: {
    value: function(newVal) {
      console.log("selectsCommon watch => ", newVal)
      this.defaultSelected = { subCode: newVal }
    },
  },

  created() {
    if (
      !(
        this.$options.propsData.options.bBlank == undefined ||
        this.$options.propsData.options.bBlank
      )
    ) {
      this.itemlist = []
    }
    this.defaultSelected.subCode = this.value
    this.$BaseCommon.restful
      .call("/common/getSelectItemList", this.$options.propsData.options)
      .then((res) => {
        if (res != null) {
          if (res.length > 0) this.itemlist[0].bSelect = false
          this.itemlist = [...this.itemlist, ...res]
        }
      })
  },
  beforeMount: () => {},
  methods: {
    selectChange: function(val) {
      console.log(this.options.name)

      this.$emit("selectChange", val, this.options.tagName)
      this.$emit(
        "change",
        val,
        this.itemlist.filter((w) => w.subCode === val)[0].name
      )
    },
  },
}
</script>
