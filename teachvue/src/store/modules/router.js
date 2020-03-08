import userApi from '@/api/user'
import { router, container, recursionRouter, dynamicRoutes } from '../../router'

let state = {
  routes: []
}

const mutations = {
  initRoutes: (state, data) => {
    state.routes = data
  }
}

const actions = {
  async initMenu ({ commit }) {
    await userApi.getMenu().then(re => {
      if (re.code === 1 && re.response && re.response.length > 0) {
        let menu = recursionRouter(re.response, dynamicRoutes)
        // let MainContainer = container.find(v => v.path === '')
        // let children = MainContainer.children
        container.push(...menu)
        // router.addRoutes(container)
        commit('initRoutes', [...router.options.routes, ...container])
      } else {
      }
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
