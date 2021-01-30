<template>
  <div class="switch-background" @click.capture="changeSwitch">
    <v-switch
      ref="switch"
      v-model="info.switchBtn"
      :color="info.color"
      :readonly="info.readonly"
      inset
      hide-details
    ></v-switch>
  </div>
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
      switchBtn: false,
    }
  },
  watch: {
    'info.switchBtn'() {
      this.setDisabledColor()
    },
  },
  methods: {
    changeSwitch() {
      if (this.info.readonly === true) {
        return
      }

      this.info.switchBtn = !this.info.switchBtn

      this.$emit('click:switch')
    },
    setDisabledColor() {
      if (this.info.switchBtn === false) {
        this.$nextTick().then(() => {
          this.$refs.switch.$el.querySelector(
            '.v-input--switch__track'
          ).style.color = '#0000001a'
        })
      }
    },
  },
}
</script>

<style lang="scss" scoped>
@mixin switch-common-background {
  width: 4.3rem;
  height: 2.12rem;
  cursor: pointer;
}

@media only screen and (max-width: $grid-breakpoints_sm) {
  .switch-background {
    @include switch-common-background;
    padding-top: 0.2rem;
    padding-left: 1rem;
  }
}

.switch-background {
  @include switch-common-background;
  padding-top: 0.4rem;
  padding-left: 0.8rem;
}

::v-deep {
  .v-input {
    margin: 0px !important;
  }

  .v-input--switch__track.theme--light {
    width: 2.5rem;
    height: 1.25rem;
    opacity: 0.8;
  }
  .v-input--switch__thumb {
    width: 0.75rem;
    height: 0.75rem;
  }
  .v-input--selection-controls__ripple {
    width: 1.688rem;
    height: 1.688rem;
  }
  .v-input--switch__thumb.theme--light {
    color: white !important;
    box-shadow: 0 0.125rem 0.25rem 0 #00000050;
  }
}
</style>
