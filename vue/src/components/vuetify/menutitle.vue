<template>
  <v-container fluid :class="'pb-0 pt-' + String(topHeight)">
    <v-row>
      <v-col class="text-left px-0 pb-0">
        <div class="d-inline-block icon">
          <v-icon v-if="imgUrl === ''" color="">{{ menuIcon }}</v-icon>
          <v-img v-else :src="imgUrl" />
        </div>
        <div class="ml-2 d-inline-block menuName subtitle-1 font-weight-bold">
          {{ menuName }}
        </div>
        <v-divider class="mt-1" v-if="isDevider"></v-divider>
      </v-col>
    </v-row>
  </v-container>
</template>
<style scoped lang="scss">
.theme--light.v-icon {
  color: black;
}
.icon {
  padding-top: 0.1rem;
}
.title {
  width: 100% !important;
}
.menuName {
  vertical-align: top;
}
</style>

<script>
export default {
  props: {
    title: {
      type: String,
    },
    icon: {
      type: String,
    },
    isDevider: {
      type: Boolean,
      default: true,
    },
    topHeight: {
      type: Number,
      default: 2,
    },
    imgUrl: {
      type: String,
      default: "",
    },
  },
  watch: {
    title: function (val) {
      this.menuName = val
    },
    icon: function (val) {
      this.menuIcon = val
    },
  },
  data() {
    return {
      menuIcon: "",
      menuIconStyle: "",
      menuName: "",
      menuUrl: "",
    }
  },
  created() {
    this.menuName = this.title
    this.menuIcon = this.icon
  },
  mounted() {
    if (this.title == undefined || this.icon == undefined) {
      let self = this
      this.$store.watch(() => {
        let menuInfo = self.$BaseCommon.hostInfo.getCurrentMenuInfo()
        if (menuInfo != null) {
          self.menuIcon = menuInfo.menuIcon
          self.menuIconStyle = menuInfo.menuIconStyle
          self.menuName = menuInfo.menuName
          self.menuUrl = menuInfo.menuUrl
          let arr = menuInfo.menuIconStyle.split("|")
          if (arr.length >= 1) self.menuIconStyle = arr[0]
        }
      })
    }
  },
}
</script>
