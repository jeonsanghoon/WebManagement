import Vue from "vue"
import App from "@/App.vue"
import vuetify from "@/plugins/vuetify.js"
import "babel-polyfill"
import "whatwg-fetch"
import router from "@/router"
//store.js를 불러와
import store from "@/store/store"

/*
import BaseCommonPlugin from './plugins/BaseCommonPlugin.js';
Vue.use(BaseCommonPlugin);

import EventbusPlugin from './plugins/EventBusPlugin.js';
Vue.use(EventbusPlugin);

import config from'./plugins/ConfigPlugin.js';
Vue.use(config);*/

// Components
import "@/components"

import plugins from "@/plugins/plugins.js"

import moment from "moment"
import VueMomentJS from "vue-momentjs"
Vue.use(plugins, VueMomentJS, moment)
import VueDaumPostcode from "vue-daum-postcode"
Vue.use(VueDaumPostcode)

import VueCarousel from "vue-carousel"

import VCalendar from "v-calendar"
Vue.use(VCalendar, {
  componentPrefix: "vc",
})

// As a plugin
import VueMask from "v-mask"
Vue.use(VueMask, VueCarousel)

import VuetifyAlert from "@/plugins/alert.js"

Vue.use({ vuetify })
Vue.use(VuetifyAlert, { vuetify })

Vue.config.productionTip = false
Vue.config.devtools = true
new Vue({
  vuetify,
  router,
  store,
  render: (h) => h(App),
}).$mount("#app")

import VueAnalytics from "vue-analytics"

Vue.use(VueAnalytics, {
  id: "UA-175322105-1", // 콘솔에서 본인의 키를 확인하세요!
  router, // 원활한 트래킹을 위해 router를 꼭 바인드 해 주어야 합니다!
})
