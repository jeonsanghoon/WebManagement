<template>
  <div id="app">
  <v-app id="inspire">
    <v-data-table
        
        :headers="headers"
        :items="items"
        item-key="name"
        class="elevation-1" 
        :footer-props="footerProps"
        ref="sortableTable"
      >
      <template v-slot:body="{ items }">
        <tbody>
          <tr class="sortableRow"  style="cursor: move" v-for="item in items" :key="item.name">
               
          <td class="text-xs-left">{{ item.name }}</td>
          <td class="text-xs-right">{{ item.calories }}</td>
          <td class="text-xs-right">{{ item.fat }}</td>
          <td class="text-xs-right">{{ item.carbs }}</td>
          <td class="text-xs-right">{{ item.protein }}</td>
          <td class="text-xs-right">{{ item.sodium }}</td>
          <td class="text-xs-right">{{ item.calcium }}</td>
          <td class="text-xs-right">{{ item.iron }}</td> 
          </tr>
        </tbody>
      </template>
    </v-data-table>

    <Vuetify-button class="ma-2" :name="'btnConfirm'"   :btntype="this.$Config.vuetify.btn.type.default" :text="'확인'" @btnConfirm_Click="btnClick()"></Vuetify-button>
      <v-textarea
                    label="결과값"
                    auto-grow
                    outlined
                    rows="5"
                    row-height="50"
                    style="width:500px"
                    shaped
                    :value="rtnData"
                ></v-textarea>
  </v-app>
</div>
</template>

<script >
import Sortable from "sortablejs"

export default {
  
  mounted () {
    /* eslint-disable no-new */
    new Sortable(
      this.$refs.sortableTable.$el.getElementsByTagName('tbody')[0],
      {
        draggable: '.sortableRow',
        handle: '.sortableRow',
        onEnd: this.dragReorder
      }
    )
  },
  methods: {
    dragReorder ({oldIndex, newIndex}) {
      const movedItem = this.items.splice(oldIndex, 1)[0]
      this.items.splice(newIndex, 0, movedItem)
    },
    itemKey (item) {
      if (!this.itemKeys.has(item)) this.itemKeys.set(item, ++this.currentItemKey)
      return this.itemKeys.get(item)
    },btnClick(){
      
      this.rtnData = JSON.stringify(this.items)
    }
  },
  data () {
    return {
      itemKeys: new WeakMap(),
      rtnData:"",
      footerProps: {'items-per-page-options': [5, 10, 15, 30, 50, 100]},
      pagination: {
        sortBy: 'name'
      },
      currentItemKey: 0,
      headers: [
      
        {
          text:     'Dessert (100g serving)',
          align:    'center',
          sortable: true,
          value:    'name'
        },
        { text: 'Calories', value: 'calories', sortable: false },
        { text: 'Fat (g)', value: 'fat', sortable: false },
        { text: 'Carbs (g)', value: 'carbs', sortable: false },
        { text: 'Protein (g)', value: 'protein', sortable: false },
        { text: 'Sodium (mg)', value: 'sodium', sortable: false },
        { text: 'Calcium (%)', value: 'calcium', sortable: false },
        { text: 'Iron (%)', value: 'iron', sortable: false }
      ],
      items: [
        {
          value: false,
          name: 'Frozen Yogurt',
          calories: 159,
          fat: 6.0,
          carbs: 24,
          protein: 4.0,
          sodium: 87,
          calcium: '14%',
          iron: '1%'
        },
        {
          value: false,
          name: 'Ice cream sandwich',
          calories: 237,
          fat: 9.0,
          carbs: 37,
          protein: 4.3,
          sodium: 129,
          calcium: '8%',
          iron: '1%'
        },
        {
          value: false,
          name: 'Eclair',
          calories: 262,
          fat: 16.0,
          carbs: 23,
          protein: 6.0,
          sodium: 337,
          calcium: '6%',
          iron: '7%'
        },
        {
          value: false,
          name: 'Cupcake',
          calories: 305,
          fat: 3.7,
          carbs: 67,
          protein: 4.3,
          sodium: 413,
          calcium: '3%',
          iron: '8%'
        },
        {
          value: false,
          name: 'Gingerbread',
          calories: 356,
          fat: 16.0,
          carbs: 49,
          protein: 3.9,
          sodium: 327,
          calcium: '7%',
          iron: '16%'
        },
        {
          value: false,
          name: 'Jelly bean',
          calories: 375,
          fat: 0.0,
          carbs: 94,
          protein: 0.0,
          sodium: 50,
          calcium: '0%',
          iron: '0%'
        },
        {
          value: false,
          name: 'Lollipop',
          calories: 392,
          fat: 0.2,
          carbs: 98,
          protein: 0,
          sodium: 38,
          calcium: '0%',
          iron: '2%'
        },
        {
          value: false,
          name: 'Honeycomb',
          calories: 408,
          fat: 3.2,
          carbs: 87,
          protein: 6.5,
          sodium: 562,
          calcium: '0%',
          iron: '45%'
        },
        {
          value: false,
          name: 'Donut',
          calories: 452,
          fat: 25.0,
          carbs: 51,
          protein: 4.9,
          sodium: 326,
          calcium: '2%',
          iron: '22%'
        },
        {
          value: false,
          name: 'KitKat',
          calories: 518,
          fat: 26.0,
          carbs: 65,
          protein: 7,
          sodium: 54,
          calcium: '12%',
          iron: '6%'
        }
      ]
    }
  }
}
</script>
<style scoped>
.buttons {
  margin-top: 35px;
}
</style>