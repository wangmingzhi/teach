const {
  $Message
} = require('/component/iView/base/index');
// const mtjwxsdk = require('./utils/mtj-wx-sdk.js');

//app.js
App({
  globalData: {
    baseAPI: "https://www.1bagtrace.com",
    // baseAPI: "https://127.0.0.1",
    pageSize: 10,
    userInfo: {},
    studentInfo: {}
  },
  onLaunch: function() {
    let _this = this
    // 登录
    wx.login({
      success: wxres => {
        if (wxres.code) {
          _this.formPost('/api/wx/student/auth/checkBind', {
            "code": wxres.code
          }).then(res => {
            if (res.code == 1) {
              this.globalData.studentInfo = res.response
              // wx.setStorageSync('token', res.response)
              wx.reLaunch({
                url: '/pages/exam/index/index',
              });
            } else if (res.code == 2) {
              this.globalData.studentInfo = res.response
              wx.reLaunch({
                url: '/pages/my/index/index',
              });
            } else {
              wx.showToast({
                title: res.message,
                icon: 'error',
                duration: 2000
              })
            }
          }).catch(e => {
            wx.showToast({
              title: '服务异常',
              icon: 'error',
              duration: 2000
            })
          })
        } else {
          wx.showToast({
            title: wxres.errMsg,
            icon: 'error',
            duration: 2000
          })
        }
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })
  },

  message: function(content, type) {
    $Message({
      content: content,
      type: type
    });
  },
  formPost: function(url, data) {
    let _this = this
    return new Promise(function(resolve, reject) {
      wx.showNavigationBarLoading();
      wx.request({
        url: _this.globalData.baseAPI + url,
        header: {
          'content-type': 'application/x-www-form-urlencoded',
          // 'token': wx.getStorageSync('token')
          'token': _this.globalData.studentInfo.studentId
        },
        method: 'POST',
        data,
        success(res) {
          if (res.statusCode !== 200 || typeof res.data !== 'object') {
            reject('网络出错')
            return false;
          }
          if (res.data.code === 500) {
            reject(res.data.message)
            return false;
          } else if (res.data.code === 501) {
            reject(res.data.message)
            return false;
          } else {
            resolve(res.data);
            return true;
          }
        },
        fail(res) {
          reject(res.errMsg)
          return false;
        },
        complete(res) {
          wx.hideNavigationBarLoading();
        }
      })
    })
  }

})