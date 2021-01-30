<template>
  <v-chip class="ma-1 pa-1" :close="info.close" close-icon="mdi-close" :style="info.style">
    <slot>내용</slot>
  </v-chip>
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
}
</script>

<style lang="scss" scoped>
::v-deep {
.v-icon {
    margin-left: 0px !important;
}
}
</style>
