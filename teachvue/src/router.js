import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/layout'
import axios from 'axios'

Vue.use(Router)

export const container = []

export const dynamicRoutes = [
  {
    path: '/class',
    component: Layout,
    name: 'ClassPageList',
    meta: {
      title: '班级学员管理',
      icon: 'peoples'
    },
    children: [
      {
        path: 'list',
        component: () => import('@/views/admin/class/list'),
        name: 'ClassPageList',
        meta: { title: '班级学员管理', icon: 'peoples', noCache: true }
      }]
  },
  {
    path: '/question',
    component: Layout,
    name: 'QuestionPage',
    meta: {
      title: '题库管理',
      icon: 'question'
    },
    children: [
      {
        path: 'list',
        component: () => import('@/views/admin/question/list'),
        name: 'QuestionPage',
        meta: { title: '题库管理', icon: 'documentation', noCache: true }
      }]
  },
  {
    path: '/admin',
    component: Layout,
    name: 'settings',
    meta: {
      title: '系统管理',
      icon: 'settings'
    },
    children: [
      {
        path: 'user/list',
        component: () => import('@/views/admin/user/list'),
        name: 'UserPageList',
        meta: { title: '用户管理', icon: 'users', noCache: true }
      },
      // {
      //   path: 'user/edit',
      //   component: () => import('@/views/admin/user/edit'),
      //   name: 'UserEdit',
      //   meta: { title: '用户编辑', noCache: true, activeMenu: '/admin/user/list' },
      //   hidden: true
      // },
      {
        path: 'role/list',
        component: () => import('@/views/admin/role/list'),
        name: 'RolePageList',
        meta: { title: '角色管理', icon: 'peoples', noCache: true }
      },
      // {
      //   path: 'role/edit',
      //   component: () => import('@/views/admin/role/edit'),
      //   name: 'RoleEdit',
      //   meta: { title: '角色编辑', noCache: true, activeMenu: '/admin/role/list' },
      //   hidden: true
      // },
      {
        path: 'dic/list',
        component: () => import('@/views/admin/dic/list'),
        name: 'dicPageList',
        meta: { title: '数据字典', icon: 'dic', noCache: true }
      }
    ]
  }
]

export const router = new Router({
  mode: 'history',
  routes: [{
    path: '/login',
    name: 'Login',
    hidden: true,
    component: () => import('@/views/login/index'),
    meta: { title: '登录' }
  },
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '',
    component: Layout,
    name: 'home',
    children: [
      {
        path: 'home',
        component: () => import('@/views/dashboard/index'),
        name: 'home',
        meta: { title: '主页', icon: 'home', affix: true }
      }
    ]
  }
  ]
})

export function recursionRouter (userRouter = [], allRoutes = []) {
  var realRoutes = []
  allRoutes.forEach((v, i) => {
    userRouter.forEach((item, index) => {
      if (item.name === v.name) {
        if (item.children && item.children.length > 0) {
          v.children = recursionRouter(item.children, v.children)
        }
        realRoutes.push(v)
      }
    })
  })
  return realRoutes
}

export async function initMenu () {
  const params = {}
  const query = {
    baseURL: process.env.VUE_APP_URL,
    url: '/api/admin/role/getMenu',
    method: 'post',
    withCredentials: true,
    timeout: 30000,
    data: params,
    headers: { 'Content-Type': 'application/json', 'request-ajax': true }
  }
  const res = await axios.request(query)
  const data = res.data
  if (data.code === 1 && data.response && data.response.length > 0) {
    let menu = recursionRouter(res.response, dynamicRoutes)
    // let MainContainer = container.find(v => v.path === '')
    // let children = MainContainer.children
    container.push(...menu)
    router.addRoutes(container)
    return [...router.options.routes, ...container]
  }
  return []
}
