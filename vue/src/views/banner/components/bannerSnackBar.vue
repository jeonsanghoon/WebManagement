<template>
  <v-snackbar v-model="info.model" color="#3E2723" :timeout="4000">
    <span>{{ info.text }}</span>
  </v-snackbar>
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
