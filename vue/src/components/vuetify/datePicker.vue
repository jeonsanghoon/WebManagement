<template>
<div>
  <v-menu
    ref="menu"
    v-model="menu"
    :close-on-content-click="false"
    transition="scale-transition"
    offset-y
    min-width="290px"
  >
    <template v-slot:activator="{ on }">
      <v-text-field
        v-model="date"
        :label="options.label"
        prepend-icon="event"
        readonly
        v-on="on"
      ></v-text-field>
    </template>
    <v-date-picker
      ref="picker"
      v-model="date"
      :max="options.max"
      :min="options.min"
      @input="menu = false"
      @change="dateChange"
    ></v-date-picker>
  </v-menu>
</div>
</template>
<script>
let moment = require('moment')
export default {
  props:['options','value'],
  data: () => ({
    date: new Date().toISOString().substr(0, 10),
    moment:moment,
    menu: false,
  
  }),watch: {
    value:function(newVal){
     this.date = moment(newVal).format("YYYY-MM-DD")
    }
  },
  mounted(){
    
       console.log(`selectsCommon_mounted => ${moment(this.options.value).format("YYYY-MM-DD")}`)

      
      this.date = moment(this.options.value).format("YYYY-MM-DD")
  },
  methods: {
    dateChange: function(val) {

      this.$emit('dateChange', val, this.options.tagName)
    }
  }
}
</script>
