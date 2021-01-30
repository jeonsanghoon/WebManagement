<template lang="pug">
    v-switch(v-model="model" inset hide-details color="#00C853" dense class="my-0" @change="change")
</template>
<script>
export default {
  props: {
    value: {},
    item: {},
    revert: { type: Boolean, default: false },
  },
  created() {
    this.model = this.revert ? !this.value : this.value
  },
  watch: {
    model(val) {
      this.$emit("input", val)
    },
    item() {},
  },
  data() {
    return {
      model: null,
    }
  },
  methods: {
    change() {
      this.$emit("change", this.item, this.model)
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
