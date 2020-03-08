import axios from 'axios'
import vue from 'vue'

const request = function (loadtip, query) {
  let loading
  if (loadtip) {
    loading = vue.prototype.$loading({
      lock: false,
      text: '正在加载中…',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.5)'
    })
  }
  return axios.request(query)
    .then(res => {
      if (loadtip) {
        loading.close()
      }
      return status(res.data)
    })
    .catch(e => {
      if (loadtip) {
        loading.close()
      }
      vue.prototype.$message.error(e.message)
      return Promise.reject(e.message)
    })
}

function status (data) {
  if (data.code === 401) {
    vue.prototype.$$router.push({ path: '/login' })
    return Promise.reject(data)
  } else if (data.code === 500) {
    return Promise.reject(data)
  } else if (data.code === 501) {
    return Promise.reject(data)
  } else if (data.code === 502) {
    vue.prototype.$$router.push({ path: '/login' })
    return Promise.reject(data)
  } else {
    return Promise.resolve(data)
  }
}

const post = function (url, params) {
  const query = {
    baseURL: process.env.VUE_APP_URL,
    url: url,
    method: 'post',
    withCredentials: true,
    timeout: 30000,
    data: params,
    headers: { 'Content-Type': 'application/json', 'request-ajax': true }
  }
  return request(false, query)
}

const postWithLoadTip = function (url, params) {
  const query = {
    baseURL: process.env.VUE_APP_URL,
    url: url,
    method: 'post',
    withCredentials: true,
    timeout: 30000,
    data: params,
    headers: { 'Content-Type': 'application/json', 'request-ajax': true }
  }
  return request(true, query)
}

const postWithOutLoadTip = function (url, params) {
  const query = {
    baseURL: process.env.VUE_APP_URL,
    url: url,
    method: 'post',
    withCredentials: true,
    timeout: 30000,
    data: params,
    headers: { 'Content-Type': 'application/json', 'request-ajax': true }
  }
  return request(false, query)
}

const get = function (url, params) {
  const query = {
    baseURL: process.env.VUE_APP_URL,
    url: url,
    method: 'get',
    withCredentials: true,
    timeout: 30000,
    params: params,
    headers: { 'request-ajax': true }
  }
  return request(false, query)
}

const form = function (url, params) {
  const query = {
    baseURL: process.env.VUE_APP_URL,
    url: url,
    method: 'post',
    withCredentials: true,
    timeout: 30000,
    data: params,
    headers: { 'Content-Type': 'multipart/form-data', 'request-ajax': true }
  }
  return request(false, query)
}

export {
  post,
  postWithLoadTip,
  postWithOutLoadTip,
  get,
  form
}

export function download (url, params) {
  const loading = vue.prototype.$loading({
    lock: false,
    text: '正在加载中…',
    spinner: 'el-icon-loading',
    background: 'rgba(0, 0, 0, 0.5)'
  })
  const query = {
    baseURL: process.env.VUE_APP_URL,
    url: url,
    method: 'post',
    withCredentials: true,
    timeout: 30000,
    data: params,
    responseType: 'blob'
    // headers: { 'Content-Type': 'application/json', 'request-ajax': true }
  }
  axios.request(query).then(res => {
    if (loading) {
      loading.close()
    }
    // 失败
    if (res.headers['content-type'] === 'application/json;charset=utf-8') {
      let blob = new Blob([res.data], {type: 'application/json;charset=utf-8'})
      var reader = new FileReader()
      reader.readAsText(blob, 'utf-8')
      reader.onload = function () {
        var data = JSON.parse(reader.result)
        return status(data)
      }
    } else {
      let blob = new Blob([res.data], {type: 'application/vnd.ms-excel'})
      let objectUrl = URL.createObjectURL(blob)
      let link = document.createElement('a')
      link.style.display = 'none'
      link.href = objectUrl
      link.setAttribute('download', decodeURI(res.headers['filename']))
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
      window.URL.revokeObjectURL(url)
    }
  }).catch(e => {
    if (loading) {
      loading.close()
    }
    vue.prototype.$message.error(e.message)
    return Promise.reject(e.message)
  })
}
