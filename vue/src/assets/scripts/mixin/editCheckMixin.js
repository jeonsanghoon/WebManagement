export const editCheckMixin = {
  created(){
    window.addEventListener('beforeunload', this.beforeunload)
  },
  methods: {
    beforeunload(event){
      if (this.$Config.isFormChange === true) {
      {
        console.log("beforeunload",event)
        event.returnValue = ''
      }
    }
  }
  }
}