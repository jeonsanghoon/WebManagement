<template>
  <v-chip class="my-2 mr-4" v-if="chip">
    <div class="text-wrap text-truncate">
      <slot> </slot>
    </div>
    <div class="chip-line" />
    <v-img
      src="@/assets/images/icons/close2.svg"
      contain
      width="13"
      @click="close"
    />
  </v-chip>
</template>

<script>
export default {
  data() {
    return {
      chip: true,
    }
  },
  methods: {
    close() {
      this.$emit('close', close)
    },
  },
}
</script>

<style lang="scss" scoped>
.v-chip {
  display: flex;
  height: 25px;
  line-height: 29px;
  margin-left: 0 !important;
  padding: 0 7px;
}

.chip-wrap {
  display: flex;
  align-content: center;
}

.text-wrap {
  font: {
    size: 12px;
    weight: 500;
  }
  color: #3e2723;
}

.theme--light.v-chip:not(.v-chip--active) {
  background: #e2d9d2;
  border: 2px solid #c0afaa;
}

.chip-line {
  height: 12px;
  margin: 0 5px;
  border: 1px solid #938783;
}

.v-image {
  cursor: pointer;
  margin-bottom: 0.05rem;
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .v-chip {
    margin-left: 6px !important;
    margin-right: 4px !important;
  }
}
</style>
