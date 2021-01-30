import { BaseCommon } from "@/assets/scripts/Common"
import { Config } from "@/assets/scripts/Config"
import { EventBus } from "@/assets/scripts/EventBus"
import { msgResource } from "@/assets/scripts/message/msgResource"

export default {
  install(Vue) {
    Vue.prototype.$BaseCommon = BaseCommon
    Vue.prototype.$Config = Config
    Vue.prototype.$EventBus = EventBus
    Vue.prototype.$moment = require("moment")
    Vue.prototype.$msg = msgResource
  },
}
