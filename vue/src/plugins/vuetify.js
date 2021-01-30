import '@mdi/font/css/materialdesignicons.css' // Ensure you are using css-loader
import Vue from 'vue';
import Vuetify from 'vuetify/lib';
import '@fortawesome/fontawesome-free/css/all.css' // Ensure you are using css-loader

Vue.use(Vuetify);

export default new Vuetify({
  
    icons: {
        iconfont: 'fa' || 'mdi'// || 'mdiSvg' || 'md' || 'fa' || 'fa4'
      }
});
