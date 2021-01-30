<template>
  <v-dialog v-model="dialog" persistent>
    <div class="header-box">
      <v-icon @click="close">close</v-icon>
    </div>
    <vue-daum-postcode @complete="complete" />
    <vue-daum-map
      :appKey="appKey"
      :libraries="libraries"
      :center.sync="center"
    ></vue-daum-map>
  </v-dialog>
</template>

<script>
import { Config } from '@/assets/scripts/Config'
import VueDaumMap from 'vue-daum-map'

/* eslint-disable */
/* global kakao */

export default {
  components: { VueDaumMap },
  props: {
    dialog: {
      type: Boolean,
      required: true,
      default: false,
    },
    radius: {
      type: Number,
      required: false,
    },
  },
  data() {
    return {
      VueDaumMap: VueDaumMap,
      Config: Config,
      appKey: Config.kakaoApiKey,
      libraries: ['services', 'clusterer', 'drawing'],
      center: { lat:33.450701, lng:126.570667 },
    }
  },
  methods: {
    complete(post) {
      // 도로명 선택
      if (post.userSelectedType === `R`) {
        post.address = `${post.roadAddress} ${post.buildingName}`
      } else {
        post.address = post.jibunAddress
      }

      const geocoder = new kakao.maps.services.Geocoder()

      geocoder.addressSearch(post.address, (result, status) => {
        if (status === kakao.maps.services.Status.OK) {
          const coords = new kakao.maps.LatLng(result[0].y, result[0].x)

          post.lat = coords.getLat()
          post.longi = coords.getLng()
          post.latitude = coords.getLat()
          post.longitude = coords.getLng()
          post.radius = this.radius

          this.$emit('getPostCode', post)
        }
      })
    },
    close() {
      this.$emit('close')
    },
  },
}
</script>

<style lang="scss" scoped>
.header-box {
  background: white;
  border-bottom: 1px solid #363636;
  text-align: end;
  padding: 5px 8px 5px 0;
}

::v-deep {
  .v-dialog {
    width: 320px !important;
    height: auto !important;
    margin: 0px !important;
  }

  .v-icon {
    width: 20px;
    height: 20px;
  }
}
</style>
