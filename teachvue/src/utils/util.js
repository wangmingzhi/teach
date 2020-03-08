import userApi from '@/api/user'
const dicFormat = function (array, key) {
  for (let item of array) {
    if (item.dictionaryCode === key) {
      return item.dictionaryName
    }
  }
  return null
}

const format = function (array, key) {
  for (let item of array) {
    if (item.key === key) {
      return item.value
    }
  }
  return null
}

const initSelect = function (typeCode, callback) {
  const dicParam = {
    typeCode: typeCode,
    pageIndex: 1,
    pageSize: 100
  }
  userApi.getDic(dicParam).then(data => {
    const re = data.response
    callback(re.list)
  })
}

const isOptions = [{ key: '1', value: '是' }, { key: '0', value: '否' }]
const sexEnum = [{ key: '1', value: '男' }, { key: '2', value: '女' }]

export {
  dicFormat,
  format,
  isOptions,
  initSelect,
  sexEnum
}

export function download (url) {
  let objectUrl = new URL(process.env.VUE_APP_URL + url)
  let link = document.createElement('a')
  link.style.display = 'none'
  link.href = objectUrl
  // link.setAttribute('download', `${name}.xlsx`)
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
  window.URL.revokeObjectURL(objectUrl)
}

export function dateFormat (date) {
  console.log(date)
  var year = date.getFullYear()
  var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
  var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
  return year + '-' + month + '-' + day
}
