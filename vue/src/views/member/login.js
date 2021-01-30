import Vue from 'vue'
import Login from '@/views/member/login.vue'
import vuetify from '@/plugins/vuetify'

import router from '@/router'
//store.js를 불러와
import store from '@/store/store'

import '@fortawesome/fontawesome-free/css/all.css' // Ensure you are using css-loader

import BaseCommonPlugin from '@/plugins/BaseCommonPlugin.js';
Vue.use(BaseCommonPlugin);
import config from'@/plugins/ConfigPlugin.js';
Vue.use(config);
import moment from "moment"
import VueMomentJS from "vue-momentjs"
Vue.use(VueMomentJS, moment)

import VueDaumPostcode from "vue-daum-postcode"
Vue.use(VueDaumPostcode)
// Components
import '@/components'

Vue.config.productionTip = false
Vue.config.devtools = true
new Vue({
    vuetify,
    router,
    store,
   
    render: h => h(Login)

}).$mount('#app')