<template>
  <v-container class="pa-0">
    <!-- 모바일 -->
    <v-row v-if="$vuetify.breakpoint.name === 'xs'" no-gutters>
      <v-col cols="12">
        <div class="menu-wrap" v-for="index in len" :key="index">
          <v-row v-for="(header, i) in info" :key="i" no-gutters>
            <v-col :class="`menu ${header.class}`" :cols="header.cols">
              <span class="th-title">{{ header.title }}</span>
            </v-col>
          </v-row>
        </div>
      </v-col>
    </v-row>

    <!-- 데스크탑, 태블릿 -->
    <v-row v-else no-gutters>
      <v-col class="menu-wrap" cols="12">
        <v-col
          class="menu"
          v-for="(header, i) in info"
          :key="i"
          :class="header.class"
          :cols="header.cols"
        >
          <div v-if="header.checkBox">
            <div>
              <v-checkbox
                v-model="check"
                class="ma-0"
                color="#3E2723"
                hide-details
                dense
              />
            </div>
          </div>
          <span v-else class="th-title">{{ header.title }}</span>
        </v-col>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  props: {
    info: {
      type: Array,
      required: true,
    },
    len: {
      type: Number,
      required: false,
    },
  },
  data() {
    return {
      check: false,
      pageMove: false,
    }
  },
  created() {
    this.$EventBus.$on('movePage', () => {
      if (this.check) {
        this.check = false
        this.pageMove = true
      }
    })
  },
  watch: {
    check() {
      if (!this.pageMove) {
        this.$emit('check', this.check)
      }
      this.pageMove = false
    },
  },
  beforeDestroy() {
    this.$EventBus.$off('movePage')
  },
}
</script>

<style lang="scss" scoped>
.th-title {
  font-weight: bold;
  font-size: 0.75rem;
}

.menu-wrap {
  display: flex;
  align-items: center;
  border-bottom: 0.1rem solid #e2d9d2;
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .menu-wrap {
    display: block;
    border-bottom: 1px solid #e2d9d2;
  }

  .menu {
    height: 60px;
  }
}

::v-deep {
  .v-input {
    padding: 0;
  }
  .v-input--selection-controls__input {
    margin-right: 0;
  }
}
</style>
