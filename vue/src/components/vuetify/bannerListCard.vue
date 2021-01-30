<template lang="pug">
  v-list
    v-list-item
      div(class="d-inline")  
        //v-checkbox( @click="change(!item.bChk)" color="brown lighten-1")
        v-btn( v-if="bChk" icon small @click="change(false)" )
          v-icon far fa-check-square  
        v-btn( v-else icon small @click="change(true)" )
          v-icon far fa-square  
      div(class="d-inline px-1") {{item[seqName]}}
      div(class="d-inline text-center px-1")  
        v-img(height="50" width="50" :src="cPicture(`/files/${item.thumbImageUrl}`)" @error="onImgError" class="thumbnail-zoom" contain) 
      div(class="d-inline text-content text-left px-1" )
        div( class="text-truncate itemTitle ") {{item.title}}  
          v-icon(small v-if="item.bannerType === 2") fas fa-ad
        div(v-text="item.subTitle"  class="text-truncate caption ")
      div(class="d-inline text-right px-1 lock" v-if="isPrivateShow" )  
        v-btn(icon v-if="item.isPrivate" @click="changedPrivate(false)")
          v-icon(small) fas fa-lock {{item.isPrivate}}
        v-btn(icon v-else @click="changedPrivate(true)")
          v-icon(small) fas fa-unlock
    
</template>

<script>
export default {
  props: {
    value: {},
    seqName: {
      type: String,
      default: "rowNum",
    },
    isPrivateShow: {
      type: Boolean,
      default: false,
    },
    index: {},
  },
  watch: {
    value(val) {
      console.log("watch", val)
    },
    item() {},
    index() {},
  },
  created() {
    this.item = { ...this.value }
    this.bChk = this.item.bChk || false
    this.item.editMode = this.item.editMode || ""
  },
  data() {
    return {
      item: {},
      bChk: false,
      isPrivateButton: false,
      failedImage: false,
    }
  },
  methods: {
    change(val) {
      this.bChk = val
      this.item = Object.assign(this.item, { bChk: this.bChk })
      this.callback()
    },
    cPicture: function(imageUrl) {
      return this.failedImage ? "http://ondaji.kro.kr" + imageUrl : imageUrl
    },

    onImgError: function(event) {
      console.log(event)
      this.failedImage = true
      //this.$forceUpdate();
    },
    changedPrivate(isPrivate) {
      this.item.isPrivate = isPrivate
      this.callback()
    },
    callback() {
      this.item.editMode = "U"
      this.$emit("input", this.item)
    },
  },
}
</script>
<style lang="scss">
input[type="checkbox"] {
  width: 10px;
  height: 10px;
}
</style>
<style lang="scss" scoped>
.dvItem .dvCol {
  float: left;
}
.td-inline {
  height: 200px;
}
.thumbnail-zoom {
  width: 3rem;
  height: 3rem;
  transition: transform 0.2s;
  margin: 0 auto;
  &:hover {
    transform: scale(5) !important;
    z-index: 1;
  }
}
.lock {
  float: right;
  width: 100%;
}
.text-content {
  max-width: 58%;
}
</style>
