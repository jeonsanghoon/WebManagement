<template>
  <v-btn
    :class="info.class"
    :rounded="info.rounded"
    :width="info.width"
    :height="info.height"
    :color="info.color"
    text
    @click="button"
    :to="info.to"
  >
    <slot>버튼내용</slot>
  </v-btn>
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
  methods: {
    button() {
      this.$emit('click:button')
    },
  },
}
</script>

<style lang="scss" scoped>
@mixin button-in-white-style {
  font: {
    weight: bold;
  }
  color: #ffffff;
}

@mixin button-in-brown-style {
  font: {
    weight: bold;
  }
  color: #42322e;
}

@mixin button-in-grey-style {
  font: {
    weight: bold;
  }
  color: #757575;
}

.transparent-btn {
  @include button-in-brown-style;
  &:hover {
    background-color: #d7d7d7;
  }
}

.darken-grey-btn {
  @include button-in-grey-style;
  background-color: #e0e0e0;
  &:hover {
    border: 2px solid #3e2723;
    background-color: rgb(62, 39, 35, 0.2);
  }
}

.select-darken-grey-btn {
  @include button-in-grey-style;
  border: 2px solid #3e2723;
  background-color: rgb(62, 39, 35, 0.2);
}

.brown-btn {
  @include button-in-white-style;
  background-color: #958985;
  &:hover {
    background-color: #3e2723;
  }
}

.lighten-brown-btn {
  @include button-in-brown-style;
  background-color: #d7ccc8;
}

.darken-brown-btn {
  @include button-in-white-style;
  background-color: #42322e;
}

.white-btn {
  font-weight: normal;
  color: #7d7d7d;
  background-color: #ffffff;
  border: 1px solid #f5eee8;
  border-radius: 10%;
  &:hover {
    background-color: #f5eee8;
    border: 2px solid #ffffff;
  }
}

.select-white-btn {
  font-weight: normal;
  color: #7d7d7d;
  background-color: #f5eee8;
  border: 2px solid #ffffff;
  border-radius: 10%;
}
</style>