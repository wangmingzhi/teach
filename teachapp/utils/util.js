const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}


const formatSeconds = theTime => {
  let theTime1 = 0
  let theTime2 = 0
  if (theTime > 60) {
    theTime1 = parseInt(theTime / 60)
    theTime = parseInt(theTime % 60)
    if (theTime1 > 60) {
      theTime2 = parseInt(theTime1 / 60)
      theTime1 = parseInt(theTime1 % 60)
    }
  }
  let result = '' + parseInt(theTime) + '秒'
  if (theTime1 > 0) {
    result = '' + parseInt(theTime1) + '分' + result
  }
  if (theTime2 > 0) {
    result = '' + parseInt(theTime2) + '小时' + result
  }
  return result
}


const checkPhoneNum = function (phoneNumber) {
  let reg = /^1[3|4|5|7|8][0-9]\d{8}$/
  if (reg.test(phoneNumber)) {
    return true
  } else {
    return false
  }
}

const checkEmai = email => {
  let reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  if (reg.test(email)) {
    return true
  } else {
    return false
  }
}

module.exports = {
  formatSeconds: formatSeconds,
  formatTime: formatTime,
  checkPhoneNum: checkPhoneNum,
  checkEmai: checkEmai
}
