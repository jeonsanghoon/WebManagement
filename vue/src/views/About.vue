<template>
  <div class="about mt-10">
    <h1>This is an about page</h1>
  </div>
</template>
<script>
export default {
  name: 'about'
  ,	mounted() {
			this.$store.watch(()=>{
        this.$store.dispatch('baseStore/setIsShowBar',true);
			})
		}
}
</script>