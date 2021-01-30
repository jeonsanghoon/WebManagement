<template>
  <v-snackbar
    class="snackbar "
    :timeout="timeout"
    rounded="pill"
    color="ondaji_color_brown_80"
    v-model="isView"
  >
    <v-icon size="20" class="mr-1">{{ model.icon }}</v-icon>
    <span class="pr-1" v-if="model.title != null" v-html="model.title"></span>

    <span class="snackbar_text" v-html="model.text"></span>
    <span style="width:auto" />
  </v-snackbar>
</template>
<style lang="scss">
.snackbar {
  .v-icon {
    color: #fff;
    opacity: 0.5;
  }
  &_title {
    padding-right: 6px;
  }
  .v-icon {
    padding-right: 6px;
  }
  padding-left: 4px;
  padding-right: 30px;
}
.v-snack[rounded="pill"] {
  .v-snack__content {
    width: auto;
  }
  .v-snack__wrapper {
    border-radius: 28px;
  }
}
</style>

<script>
export default {
  props: {
    value: {
      type: Object,
      /* default: {
        icon: "far fa-check-circle",
        isView: false,
        title: "궁금하당",
        text: "안녕하세요",
      },*/
    },
    icon: {
      type: String,
      default: "far fa-check-circle",
    },
  },
  watch: {
    value(val) {
      this.init(val)

      console.log(val)
    },
  },
  created() {
    this.init(this.value)
  },
  data() {
    return {
      timeout: 3000,
      isView: true,
      model: {
        text: "안녕하세요",
        icon: "far fa-check-circle",
      },
    }
  },
  methods: {
    init(val) {
      try {
        this.isView = val.isView
        this.model = {}
        val.icon = val.icon === undefined ? "" : val.icon
        val.title = val.title === undefined ? "" : val.title
        this.model = Object.assign({}, val)
        console.log(val)
      } catch (e) {
        console.log(e)
      }
    },
  },
}
</script>
