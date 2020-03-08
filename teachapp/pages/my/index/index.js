// pages/my/index/index.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    student: {
      statusName:'未绑定'
    },
    editUrl: '',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    bindButton: '绑定'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (app.globalData.studentInfo.studentId){
      this.setData({
        bindButton: '解除绑定',
        editUrl: '/pages/user/bind/index'
      })
    }
    this.loadUser()
  },
  bindClick(){
    let _this = this
    if (!app.globalData.studentInfo.openid) {
      app.message('未获取到微信OpenId，无法绑定', 'error')
      return
    }
    if (this.data.student.studentId) {
      // _this.setData({
      //   spinShow: true
      // });
      app.formPost('/api/wx/student/auth/unBind', { "studentId" : this.data.student.studentId})
        .then(res => {
          _this.setData({
            spinShow: false
          });
          if (res.code == 1) {
            const openID = app.globalData.studentInfo.openid
            app.globalData.studentInfo = { openid: openID}
            wx.reLaunch({
              url: '/pages/my/index/index',
            });
          } else {
            app.message(res.message, 'error')
          }
        }).catch(e => {
          _this.setData({
            spinShow: false
          });
          app.message(e, 'error')
        })
    }else{
      wx.navigateTo({
        url: '/pages/user/bind/index?title=绑定',
      });
    }
  },
  loadUser(){
    if (app.globalData.userInfo.nickName) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function (e) {
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  getStudentInfo: function () {
    let _this = this;
    // _this.setData({
    //   spinShow: true
    // });
    app.formPost('/api/wx/student/auth/getStudentInfo', 
      { 'studentId': app.globalData.studentInfo.studentId})
      .then(res => {
        _this.setData({
          spinShow: false
        });
        if (res.code == 1) {
          app.globalData.studentInfo = res.response
          this.setData({
            student: app.globalData.studentInfo
          })
        } else {
          app.message(res.message, 'error')
        }
      }).catch(e => {
        _this.setData({
          spinShow: false
        });
        app.message(e.message, 'error')
      })
  },
  

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    if (app.globalData.studentInfo.studentId) {
      this.getStudentInfo();
    }
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})