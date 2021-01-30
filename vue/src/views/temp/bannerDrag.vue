<template>
  <div class="bannerDrag" style="width:300px;height:900px">
    <dashboard :id="'dashExample'">
      <dash-layout
        v-for="layout in dlayouts"
        v-bind="layout"
        :debug="true"
        :key="layout.breakpoint"
      >
        <dash-item v-for="item in layout.items" :key="item.id">
          <div class="content"></div>
        </dash-item>
      </dash-layout>
    </dashboard>
  </div>
</template>

<script>
import { Dashboard, DashLayout, DashItem } from "vue-responsive-dash"

export default {
  name: "App",
  components: {
    Dashboard,
    DashLayout,
    DashItem,
  },
  data() {
    return {
      dlayouts: [
        {
          breakpoint: "xl",
          numberOfCols: 2,
          resizable: false,
          items: [
            { id: "1", x: 0, y: 0, width: 1, height: 1 },
            { id: "2", x: 1, y: 0, width: 1, height: 2 },
            { id: "3", x: 1, y: 1, width: 1, height: 2 },
          ],
        },
      ],
    }
  },
}
</script>

<style>
.bannerDrag .content {
  height: 100%;
  width: 100%;
  border: 2px solid #42b983;
  border-radius: 5px;
}
</style>
