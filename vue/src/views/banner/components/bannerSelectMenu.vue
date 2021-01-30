<template>
  <v-container class="pa-0 mt-1 mt-sm-2">
    <div :style="info.style">
      <v-btn
        class="select-btn"
        color="#fafafa"
        depressed
        rounded
        :width="info.width"
        @click="button"
        @mouseover="mouseOver"
        @mouseout="mouseOut"
      >
        <span ref="btnTitle" class="btn-title mr-auto">
          <slot v-if="btnTitle === ''">버튼 내용</slot>
          <span v-else>{{ btnTitle }}</span>
        </span>
        <v-icon ref="btnIcon" v-if="show === false">keyboard_arrow_down</v-icon>
        <v-icon ref="btnIcon" v-else>keyboard_arrow_up</v-icon>
      </v-btn>
    </div>
    <div class="mb-1 mb-sm-2"></div>

    <v-row justify="center">
      <v-card v-if="show === true" class="mx-auto" :width="info.width">
        <div
          class="select-check-box"
          v-for="(item, i) in info.items"
          :key="i"
          @click.capture="checkBox(i)"
        >
          <v-checkbox
            v-if="info.checkView"
            class="check-box"
            v-model="item.check"
            :label="item.name"
            hide-details
            dense
            readonly
          >
          </v-checkbox>
          <span v-else class="select-check-none">{{ item.name }}</span>
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
      btnTitle: '',
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

      this.mouseOut()
    },
  },
  methods: {
    checkBox(index) {
      // 중복 체크 불가능 버튼
      if (this.info.checkOnly === true) {
        for (let i = 0; i < this.info.items.length; i++) {
          if (i != index) {
            this.info.items[i].check = false
            break
          }
        }

        this.info.items[index].check = true
        this.btnTitle = this.info.items[index].name
      } else {
        this.info.items[index].check = !this.info.items[index].check
      }

      this.$emit('searchType')
    },
    button() {
      this.show = !this.show

      this.$emit('click:button')
    },
    mouseOver() {
      this.$refs.btnTitle.style.color = '#938783'
      this.$refs.btnIcon.$el.style.cssText = 'color: #938783 !important'
    },
    mouseOut() {
      if (this.show === false) {
        this.$refs.btnTitle.style.color = '#c0afaa'
        this.$refs.btnIcon.$el.style.cssText = 'color: #c0afaa !important'
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.select-btn {
  padding: 0.625rem !important;
  height: 1.75rem !important;
}

.select-check-box {
  padding: 0.2rem 0 0.2rem 0.4rem;
  cursor: pointer;
  &:hover {
    background-color: #e0e0e0;
  }

  .select-check-none {
    display: flex;
    justify-content: flex-start;
    padding: 0.1rem 0;
    color: #757575;
    font-size: 0.75rem;
    font-weight: 400;
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
    border: 0.031rem solid #c0afaa;
    z-index: 1;
  }
  .v-icon {
    color: #3e2723 !important;
    font-size: 1.2rem;
    padding-top: 0.1rem;
  }
  .v-label {
    font-weight: 500;
    padding-left: 0.2rem;
  }
  .v-input {
    margin: 0;
  }
  .v-input--selection-controls__input {
    margin-right: 0.063rem;
  }
  .v-input--selection-controls__ripple.primary--text {
    color: #3e2723 !important;
  }
  .v-label.theme--light {
    color: #757575 !important;
    padding-top: 0.1rem;
    padding-bottom: 0.06rem;
    font-size: 0.75rem !important;
    font-weight: 400 !important;
  }
}
</style>
