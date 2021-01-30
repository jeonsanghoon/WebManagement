<template>
  <v-container class="pt-0 pb-8">
    <header>
      <v-col class="pt-3 pl-0" cols="12">
        <span
          :class="info.class"
          style="font-weight: bold; font-size: 0.875rem; color: #757575;"
          >{{ text.title }} {{ info.index }}</span
        >
      </v-col>
    </header>

    <content>
      <v-card class="pb-1 mx-auto" :width="imgWidth">
        <!-- image -->
        <v-img :src="info.src" contain :height="imgHeight"></v-img>
        <!-- middle-bar -->
        <v-system-bar>
          <div class="mx-auto">
            <span v-for="(circleIcon, i) in circleIconArr" :key="i">
              <v-icon class="circle-icon">{{ circleIcon }}</v-icon>
            </span>
          </div>
        </v-system-bar>
        <!-- content-header -->
        <v-list-item>
          <v-list-item-avatar></v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title class="title">
              <span class="font-weight-medium">{{ info.nick }}</span>
            </v-list-item-title>
            <v-list-item-subtitle>
              <span class="sub-title">
                {{ info.local }}
              </span>
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
        <!-- content-in -->
        <v-divider class="mx-4" color="#F4F2F0"></v-divider>
        <div class="mt-2 mb-n1 ml-4">
          <span class="font-weight-bold">{{ info.title }}</span>
        </div>
        <v-card-text :style="contentStyle">
          <span style="font-weight: normal;">{{ info.content }} </span>
        </v-card-text>
      </v-card>
    </content>
  </v-container>
</template>

<script>
import { commonBanner } from "../../../../mixins/commonMixins"

export default {
  mixins: [commonBanner],
  props: {
    info: {
      type: Object,
      required: true,
      default: null,
    },
  },
  data() {
    return {
      text: {
        title: `모바일 배너 - `,
      },
      circleIconArr: [],

      imgWidth: 0,
      imgHeight: 0,

      contentStyle: ``,
    }
  },
  created() {
    this.initImgSize()

    let circleName = ""

    for (let i = 0; i < this.info.len; i++) {
      if (this.info.index - 1 === i) {
        circleName = `fas fa-circle`
      } else {
        circleName = `far fa-circle`
      }

      this.circleIconArr.push(circleName)
    }
  },
  methods: {
    initImgSize() {
      const screenSize = this.getScreenSize()

      if (screenSize === "lg") {
        this.imgWidth = 389
        this.imgHeight = 389
        this.contentStyle = `height: 215px;`
      } else if (screenSize === "sm") {
        this.imgWidth = 300
        this.imgHeight = 300
        this.contentStyle = `height: 300px;`
      } else {
        this.imgWidth = 280
        this.imgHeight = 280
        this.contentStyle = `height: 310px;`
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.container {
  padding: 0.405rem;
}

.v-card {
  background-color: white;
}

.v-system-bar {
  background-color: #f0f0f0 !important;
  height: 35px !important;
}

.v-avatar {
  background-color: #f5f5f5;
}

.title {
  font-weight: bold;
  font-size: 0.75rem !important;
}

.sub-title {
  color: black;
  font-weight: bold;
}

.circle-icon {
  font-size: 0.6rem;
  padding-bottom: 0.2rem;
}
</style>
