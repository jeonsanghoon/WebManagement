<template>
  <v-textarea
    v-model="info.model"
    :label="info.label"
    :flat="info.flat"
    :maxlength="info.maxlength"
    :filled="info.filled"
    :rounded="info.rounded"
    :dense="info.dense"
    :solo="info.solo"
    :auto-grow="info.autoGrow"
    :hide-details="info.hideDetails"
    :style="info.style"
    :outlined="info.outlined"
    :rows="info.rows"
    :color="info.color"
  >
  </v-textarea>
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
  .v-label.theme--light {
    font-weight: normal;
    font-size: 0.9rem;
    color: #AAA19F !important;
  }

  .v-input__slot {
    border: 1px solid #bdbdbd !important;
  }
}
</style>
