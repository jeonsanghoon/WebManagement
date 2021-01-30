/* store.js */
import Vue from "vue"
import Vuex from "vuex"

Vue.use(Vuex)

// 기본 저장소
const baseStore = {
  namespaced: true,
  state: {
    isShowBar: false,
    isSideBar: false,
    isLoading: false,
    memberInfo: null,
    layoutMode: "local",
  },
  getters: {
    getIsShowBar: (state) => state.isShowBar,
    getIsSideBar: (state) => state.isSideBar,
    getIsLoading: (state) => state.isLoading,
    getMemberInfo: (state) => state.memberInfo,
    getLayoutMode: (state) => state.layoutMode,
  },
  mutations: {
    setIsShowBar: (state, bTrue) => (state.isShowBar = bTrue),
    setIsSideBar: (state, bTrue) => (state.isSideBar = bTrue),
    setIsLoading: (state, bTrue) => (state.isLoading = bTrue),
    setMemberInfo: (state, member) => (state.memberInfo = member),
    setLayoutMode: (state, mode) => (state.layoutMode = mode),
  },
  actions: {
    setIsShowBar: ({ commit }, bTrue) => commit("setIsShowBar", bTrue),
    setIsSideBar: ({ commit }, bTrue) => commit("setIsSideBar", bTrue),
    setIsLoading: ({ commit }, bTrue) => commit("setIsLoading", bTrue),
    setMemberInfo: ({ commit }, member) => commit("setMemberInfo", member),
    setLayoutMode: ({ commit }, mode) => commit("setLayoutMode", mode),
  },
}

export default new Vuex.Store({
  modules: {
    baseStore: baseStore,
  },
})
