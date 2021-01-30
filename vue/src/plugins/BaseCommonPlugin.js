import { BaseCommon } from "@/assets/scripts/Common"

export default {
  install(Vue) {
    //다른 컴포넌트나 main.js에서 Vue.use(ChartPlugin) 을 명시할 경우 ChartPlugin.install(Vue)이 실행된다.
    Vue.prototype.$BaseCommon = BaseCommon
  },
}
