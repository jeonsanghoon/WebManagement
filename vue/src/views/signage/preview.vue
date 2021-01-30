<template lang="pug">
    v-dialog( v-model="isShow" )
        v-card( class="mx-auto my-12"  )
            carousel(class="carousel" :per-page="1" :navigationEnabled="true" 
                  :navigationNextLabel="`<div class='circle'><i class='material-icons btnCarouselArrow'>arrow_forward_ios</i></div>`"
                  :navigationPrevLabel="`<div class='circle prev'><i class='material-icons btnCarouselArrow'>arrow_back_ios</i></div>`"
                  @page-change="caroselChange")
                slide(v-for="(slide, i) in bannerList" :key="(i,slide.bannerCode)" v-if="frameType===1")
                    v-img( src=`/files/${slide.bannerImageUrl}` )
                slide(v-for="(slide, i) in bannerList" :key="(i,slide.bannerCode)" v-if="frameType===6")
                    v-container()
                        v-row
                            v-col(cols="6" class="pa-0" v-for="(item,pageIdx) in bannerList.filter(w=>w.page===slide.page)" :key="(seq,item.bannerCode)")
                                v-img(src=`/files/${item.bannerImageUrl}` )
                    
</template>
<script>
export default {
  props: {
    value: {
      type: Array,
      default: new Array(),
    },
    frameType: {
      type: Number,
      default: 1,
    },
  },
  watch: {
    value(val) {
      this.bannerList = [...val]
    },
    bannerList(val) {
      this.bannerPages = [...new Set(val.map((x) => x.page))]
    },
  },
  created() {
    this.bannerList = [...this.value]
  },
  data() {
    return {
      isShow: false,
      bannerList: [],
      bannerPages: [],
    }
  },
  methods: {
    caroselChange(page) {
      console.log(page)
    },
  },
}
</script>
