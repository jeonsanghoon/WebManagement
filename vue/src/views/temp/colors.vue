<template>
  <v-container class="fluid align-start justify-start">
    <v-row>
      <v-col cols="12">
        <Vuetify-menutitle title="컬러정의" />
      </v-col>

      <v-col
        cols="12"
        sm="6"
        md="4"
        lg="3"
        v-for="(colorData, colorIdx) in colors"
        :key="colorIdx"
        class="mt-0 pt-0 cssData"
      >
        <v-card
          v-if="
            (colorData.key.indexOf('white') < 0 &&
              colorData.key.indexOf('grey') < 0 &&
              colorData.key.indexOf('green1') < 0) ||
              colorData.key.indexOf('grey1') >= 0
          "
          :class="'ondaji_color_' + colorData.key"
        >
          <span
            :class="
              colorData.key === 'black' ||
              colorData.key === 'grey1' ||
              colorData.key === 'brown' ||
              colorData.key === 'brown1'
                ? 'white--text'
                : ''
            "
          >
            {{ "ondaji_color_" + colorData.key }}
          </span>
        </v-card>
        <v-card v-else :class="'ondaji_color_' + colorData.key">
          <span
            :class="
              colorData.key === 'black' ||
              colorData.key === 'grey1' ||
              colorData.key === 'brown' ||
              colorData.key === 'brown1'
                ? 'white--text'
                : ''
            "
          >
            {{ "ondaji_color_" + colorData.key }}
          </span>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<style lang="scss">
.cssData .v-card {
  margin-bottom: 0.875rem;
  padding: 1rem;
  text-align: left;
}
</style>
<script>
export default {
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({ el: this.$el, isTop: true })
  },
  data() {
    return {
      colors: [
        { key: "grey_100", val: "#000000" },
        { key: "grey_90", val: "#242424" },
        { key: "grey_80", val: "#424242" },
        { key: "grey_70", val: "#616161" },
        { key: "grey_60", val: "#757575" },
        { key: "grey_50", val: "#aaaaaa" },
        { key: "grey_40", val: "#bdbdbd" },
        { key: "grey_30", val: "#c4c4c4" },
        { key: "grey_20", val: "#e0e0e0" },
        { key: "grey_10", val: "#f2f2f2" },
        { key: "grey_5", val: "#fafafa" },
        { key: "white", val: "#ffffff" },
        { key: "brown_100", val: "#231815" },
        { key: "brown_90", val: "#403532" },
        { key: "brown_80", val: "#3e2723" },
        { key: "brown_70", val: "#938783" },
        { key: "brown_60", val: "#c0afaa" },
        { key: "brown_50", val: "#c6b8b4" },
        { key: "brown_40", val: "#c2b7b3" },
        { key: "brown_30", val: "#d1c8c6" },
        { key: "brown_20", val: "#e2d9d2" },
        { key: "brown_10", val: "#f5eee8" },
        { key: "tealgreen_100", val: "#005d3b" },
        { key: "tealgreen_90", val: "#007a56" },
        { key: "tealgreen_80", val: "#028a64" },
        { key: "tealgreen_70", val: "#069b73" },
        { key: "tealgreen_60", val: "#0ba880" },
        { key: "tealgreen_50", val: "#24b791" },
        { key: "tealgreen_40", val: "#50c5a4" },
        { key: "tealgreen_30", val: "#84d6be" },
        { key: "tealgreen_20", val: "#b4e6d7" },
        { key: "tealgreen_10", val: "#e1f5f0" },
        { key: "green_100", val: "#2e7d32" },
        { key: "green_40", val: "#00c853" },
        { key: "green_10", val: "#e0f8e2" },
        { key: "blue_90", val: "#1565c0" },
        { key: "blue_70", val: "#2979ff" },
        { key: "blue_30", val: "#64b5f6" },
        { key: "blue_20", val: "#bbdefb" },
        { key: "yellow_80", val: "#f57f17" },
        { key: "yellow_10", val: "#ffecb3" },
        { key: "red_60", val: "#e53935" },
      ],
    }
  },
}
</script>
