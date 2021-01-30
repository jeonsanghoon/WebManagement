<template>
  <v-btn
    type="button"
    :width="width"
    :rounded="rounded"
    :tile="tile"
    :depressed="depressed"
    :large="large"
    :color="this.color"
    :class="this.color + ' ' + this.btnTextColor + '--text'"
    @click="btnClick"
    onsubmit="return false;"
    >{{ this.btnText }}
  </v-btn>
</template>
<style scoped lang="scss">
.v-btn {
  font-weight: bold;

  &:hover {
    cursor: pointer;
  }
}
</style>
<script>
export default {
  props: {
    name: {
      type: String,
      default: "btnDefault",
    },
    btntype: {
      type: String,
    },
    text: {
      type: String,
    },
    rounded: {
      type: Boolean,
      default: false,
    },
    tile: {
      type: Boolean,
      default: true,
    },
    depressed: {
      type: Boolean,
      default: true,
    },
    width: {
      type: String,
    },
    large: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      color: "white",
      btnTextColor: "",
      btnText: "버튼",
    }
  },
  created() {
    console.log(this.$router.currentRoute)
    console.log(this.btntype)
    console.log(this.$Config.vuetify.btn[this.btntype])
    this.color = this.$Config.vuetify.btn[this.btntype].color
    this.btnText = this.text == null ? "버튼" : this.text
    this.btnTextColor = this.$Config.vuetify.btn[this.btntype].textColor
  },
  methods: {
    btnClick: function() {
      this.$Config.isFormChange = false
      this.$Config.isFormChangeCheck = false
      this.$emit(this.name + "_Click")

      this.$emit("click")
    },
  },
}
</script>
