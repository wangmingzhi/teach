// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import { router, initMenu } from './router'
import store from './store'
import Element from 'element-ui'
import './styles/element-variables.scss'

import '@/styles/index.scss' // global css
import './icons'

import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style

Vue.use(Element)

Vue.config.productionTip = false
NProgress.configure({ showSpinner: false }) // NProgress Configuration

router.beforeEach(async (to, from, next) => {
  if (to.path === '/login') {
    next(0)
    next()
    return
  }
  if (!store.state.user.userName) {
    next('/login')
  } else {
    if (to.path === '/') {
      next('/home')
      return
    }
    if (store.state.router.routes.length === 0) {
      store.dispatch('router/initMenu').then(res => {
        initMenu()
        next()
      })
      // store.commit('router/initRoutes', menus)
      // console.log(store.state.router.routes.length)
    } else {
      next()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
Vue.prototype.$$router = router

new Vue({
  router: router,
  store: store,
  render: h => h(App)
}).$mount('#app')
