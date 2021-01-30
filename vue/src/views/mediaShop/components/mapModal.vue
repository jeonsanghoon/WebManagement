<template>
  <v-dialog v-model="dialog" persistent max-width="1200">
    <div class="map-wrap">
      <Kakaomap-normal
        ref="map"
        v-model="mapInfo"
        :dialog="mapDialog"
        :height="mapHeight"
      />
      <div class="shrink-wrap" @click="close">
        <v-img
          class="shrink-icon"
          contain
          height="17"
          src="@/assets/images/icons/shrink.svg"
        />
      </div>
    </div>
  </v-dialog>
</template>

<script>
import { commonMediaShop } from '@/views/mediaShop/mixins/commonMixins'

export default {
  mixins: [commonMediaShop],
  props: {
    dialog: {
      type: Boolean,
      required: true,
      default: false,
    },
    posInfo: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      mapInfo: { lat: 37.512091, longi: 127.044348, radius: 500 },
      mapDialog: false,
    }
  },
  watch: {
    dialog() {
      if (this.dialog === true) {
        this.mapInfo = this.posInfo

        if (this.$refs.map != undefined) {
          this.$refs.map.callMap(this.mapInfo)
        }
      }
    },
  },
  computed: {
    mapHeight() {
      return `${window.innerHeight - 200}px`
    },
  },
  methods: {
    close() {
      this.mapInfo.lat = this.mapInfo.latitude
      this.mapInfo.longi = this.mapInfo.longitude
      this.mapInfo.radius = this.mapInfo.radius

      this.$emit('close', this.mapInfo)
      this.$emit('update:dialog', false)
    },
  },
}
</script>

<style lang="scss" scoped>
.map-wrap {
  position: relative;
}

.close-icon {
  position: absolute;
  top: 15px;
  right: 15px;
  z-index: 2;
  cursor: pointer;
}

.shrink-wrap {
  background-color: white;
  border: 1px solid #aaaaaa;
  border-radius: 2px;
  width: 25px;
  height: 25px;
  position: absolute;
  bottom: 15px;
  right: 15px;
  z-index: 2;
  cursor: pointer;
}

.shrink-icon {
  margin-top: 3px;
}
</style>
