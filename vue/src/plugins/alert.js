import Alert from "@/components/vuetify/alert.vue"

import Vuetify from "@/plugins/vuetify"

function Install(Vue, options = {}) {
  const property = options.property || "$alert"
  delete options.property
  options.vuetify = Vuetify
  const vuetify = options.vuetify
  //delete options.vuetify~
  if (!vuetify) {
    console.warn(
      "Module vuetify-alert needs vuetify instance. Use Vue.use(VuetifyAlert, { vuetify })"
    )
  }

  const Ctor = Vue.extend(Object.assign({ vuetify }, Alert))
  function createDialogCmp(options) {
    const container = document.querySelector("[data-app=true]") || document.body
    try {
      return new Promise((resolve) => {
        const cmp = new Ctor(
          Object.assign(
            {},
            {
              propsData: Object.assign(
                {},
                Vue.prototype.$alert.options,
                options
              ),
              destroyed: () => {
                try {
                  container.removeChild(cmp.$el)
                  resolve(cmp.value)
                } catch (e) {
                  console.log(e)
                }
              },
            }
          )
        )

        container.appendChild(cmp.$mount().$el)
      })
    } catch (e) {
      console.log(e)
    }
  }

  function show(message, options = {}) {
    options.message = message
    return createDialogCmp(options)
  }

  Vue.prototype[property] = show
  Vue.prototype[property].options = options || {}
}

if (typeof window !== "undefined" && window.Vue) {
  window.Vue.use(Install)
}

export default Install
