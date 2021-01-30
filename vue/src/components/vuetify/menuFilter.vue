<template>
  <v-container ref="mainWrap" class="pa-0 mt-1 mt-sm-0 relative">
    <div class="menu-wrap">
      <v-btn
        class="filter-btn"
        color="transparent"
        depressed
        rounded
        :width="info.width"
        @click="button"
      >
        <span ref="btnTitle" class="ondaji_font_14_bold_brown_100 mr-auto">
          <span>{{ btnTitle }}</span>
        </span>
        <v-icon v-if="show === false">keyboard_arrow_down</v-icon>
        <v-icon v-else>keyboard_arrow_up</v-icon>
      </v-btn>
    </div>

    <v-row justify="center">
      <v-card v-if="show === true" :width="info.width">
        <div
          class="select-wrap"
          v-for="(item, i) in info.items"
          :key="i"
          @click.capture="checkBox(i)"
        >
          <span
            v-if="item.check === true"
            class="ondaji_font_12_medium_grey_90"
          >
            {{ item.name }}
          </span>
          <span v-else class="ondaji_font_12_medium_grey_70">
            {{ item.name }}
          </span>
        </div>
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
export default {
  props: {
    info: {
      type: Object,
      required: true,
      default: null,
    },
  },
  data() {
    return {
      // @mouseover="item.check = true"
      //   @mouseleave="item.check = false"

      btnTitle: ``,
      show: false,
    }
  },
  created() {
    this.info.items.forEach((e) => {
      if (e.check === true) {
        this.btnTitle = e.name
      }
    })
  },
  watch: {
    info() {
      this.show = this.info.show
    },
  },
  methods: {
    checkBox(index) {
      for (let i = 0; i < this.info.items.length; i++) {
        if (i != index) {
          this.info.items[i].check = false
          break
        }
      }

      this.info.items[index].check = true
      this.btnTitle = this.info.items[index].name

      this.show = !this.show
    },
    button() {
      this.show = !this.show

      this.$emit('click:button')
    },
  },
}
</script>

<style lang="scss" scoped>
.menu-wrap {
  float: right;
}

.filter-btn {
  padding: 0.625rem !important;
  height: 1.75rem !important;
}

.select-wrap {
  text-align: start;
  padding: 0.2rem 0 0.2rem 0.8rem;
  cursor: pointer;
  &:hover {
    background-color: #e0e0e0;
  }
}

.btn-title {
  font-weight: 500;
  font-size: 0.75rem;
  color: #c0afaa;
}

::v-deep {
  .v-card {
    position: absolute;
    border: 0.5px solid #c0afaa;
    z-index: 1;
  }
  .v-icon {
    color: #3e2723 !important;
    font-size: 1.2rem;
    padding-top: 0.1rem;
  }
}
</style>
