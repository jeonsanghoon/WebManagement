<template>
  <v-container class="wrap">
    <p ref="refTitle" class="title-text ondaji_font_12_bold_brown_100">
      선택한 로컬러
    </p>

    <v-slide-group
      ref="refSlide"
      class="slide-wrap"
      :show-arrows="showArrows"
      multiple
    >
      <v-slide-item
        v-for="(localer, i) in localers"
        :key="i"
        v-slot:default="{ active }"
      >
        <Vuetify-chip :input-value="active" @close="close(localer)">
          {{ localer.name }}
        </Vuetify-chip>
      </v-slide-item>
    </v-slide-group>
  </v-container>
</template>

<script>
import { commonMediaShop } from '@/views/mediaShop/mixins/commonMixins'

export default {
  mixins: [commonMediaShop],
  props: {
    localers: {
      type: Array,
      required: true,
    },
  },
  mounted() {
    this.localerViewStyle()
  },
  computed: {
    showArrows() {
      return this.getScreenSize() === 'lg' ? true : false
    },
  },
  watch: {
    localers() {
      this.localerViewStyle()
    },
  },
  methods: {
    async localerViewStyle() {
      if (this.getScreenSize() !== 'lg') return

      await this.$nextTick()

      setTimeout(() => {
        const refSlide = this.$refs.refSlide.$el
        const width = refSlide.querySelector('.v-slide-group__prev').offsetWidth

        const slide = refSlide.querySelector('.v-slide-group__wrapper')

        slide.style.marginLeft = width > 0 ? '0' : '52px'
      }, 10)
    },
    close(localer) {
      this.$emit('close', localer)
    },
  },
}
</script>

<style lang="scss" scoped>
p {
  padding: 0;
  margin: 0;
}

.wrap {
  padding: 0;
}

.slide-wrap {
  padding: 0 20px 0;
}

.title-text {
  text-align: start;
  padding: 12px 0;
  margin-left: 72px;
}

@media screen and (max-width: $grid-breakpoints_lg) {
  .slide-wrap {
    padding-left: 29px;
  }

  .title-text {
    margin-left: 30px;
  }
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .slide-wrap {
    padding: 0;
  }

  .title-text {
    margin-left: 8px;
  }
}

::v-deep {
  .v-icon {
    font-size: 12px;
  }
  .v-chip {
    max-width: 100px;
  }
}
</style>
