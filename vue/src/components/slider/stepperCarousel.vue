<template>
          <v-container class="signageSetting" fill-height fluid>
            <v-row>
              <v-col cols="1">
                <Vuetify-stepper v-model="stepperOption" @change="stepperChange" />
              </v-col>
              <v-col cols="11">
                <hooper
                  ref="carousel"
                  vertical
                  @slide="updateCarousel"
                  :touchDrag="false"
                  :mouseDrag="false"
                >
                  <slide v-for="(item,index) in list" :key="index">
                     <slot></slot>
                  </slide>
           
                  <hooper-navigation slot="hooper-addons"></hooper-navigation>
                </hooper>
              </v-col>
            </v-row>
          </v-container>
</template>
<script>
export default {
    props:{

    }
    ,
     render: function (createElement) {
        return createElement(
        this.$slots.default // array of children
        )
    },
    data(){
        return {
            stepperOption: {
                total: 4,
                value: 1,
                lineHeight: 160,
                disabled: false,
            },
            list:[]
        }
    },
  methods: {
    stepperChange(val) {
      console.log("stepperChange", val)
      this.stepper.value = val
      this.$refs.carousel.slideTo(val - 1)
    },
    slidePrev() {
      this.$refs.carousel.slidePrev()
    },
    slideNext() {
      this.$refs.carousel.slideNext()
    },
    updateCarousel(payload) {
      this.sliderIdx = payload.currentSlide + 1
      let tmp = Object.assign(this.stepperOption, { value: this.sliderIdx })

      this.stepperOption = Object.assign({}, tmp)
    }
  }
}
</script>