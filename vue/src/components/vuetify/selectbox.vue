<template lang="pug">
     v-menu(offset-y v-model="menu"  )
        template(v-slot:activator="{ on, attrs }")
            v-btn(color="brown lighten-1" small text dark v-bind="attrs" v-on="on") {{selectedItem.text}}
                v-icon(class="pl-2 pt-1" small v-if="!menu") fas fa-angle-down
                v-icon(class="pl-2 pt-1" small v-else) fas fa-angle-up
        v-list
            v-list-item(v-for="(item, index) in selItems" :key="index" @click="click(item)")
                v-list-item-title {{ item.text }}
</template>
<script>
export default {
  props: {
    value: {
      type: Object,
      required: true,
    },
    options: {
      type: Object,
      required: true,
    },
  },
  watch: {
    menu: {},
  },
  created() {
    // eslint-disable-next-line no-debugger
    debugger
    this.selItems = [...this.options]
    this.selectedItem = { ...this.value }
  },
  data() {
    return {
      menu: false,
      selectedItem: {},
      selItems: [],
    }
  },
  methods: {
    click(item) {
      this.selectedItem = { ...item }
      this.$emit("change", this.selectedItem)
      this.$emit("input", this.selectedItem)
    },
  },
}
</script>
