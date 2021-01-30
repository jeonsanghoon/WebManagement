<template>
  <v-dialog v-model="dialog" persistent max-width="320">
    <v-card>
      <v-card-text class="progress-card pt-3">
        <slot>내용</slot>
        <v-progress-linear
          class="mt-3 mb-0"
          indeterminate
          color="#5c4c47"
        ></v-progress-linear>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  props: {
    dialog: {
      type: Boolean,
      required: true,
      default: false,
    },
  },
}
</script>

<style lang="scss" scoped>
.progress-card {
  font-weight: bold;
  color: #5c4c47;
}
</style>
