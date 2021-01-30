<template>
  <v-text-field
    v-model="info.model"
    :placeholder="info.placeholder"
    :rules="[info.rules]"
    :maxlength="info.maxlength"
    :filled="info.filled"
    :rounded="info.rounded"
    :dense="info.dense"
    :required="info.required"
    :background-color="info.backgroundColor"
    :readonly="info.readonly"
    :style="info.style"
    :color="info.color"
  >
  </v-text-field>
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
  input::placeholder {
    font-weight: normal;
    font-size: 0.9rem;
    color: #AAA19F !important;
  }

  .v-input__slot {
    border: 1px solid #BDBDBD !important;
  }
}
</style>
