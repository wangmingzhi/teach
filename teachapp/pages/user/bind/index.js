const app = getApp()
const utils = require('../../../utils/util.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    student: {},
    title:'用户修改',
  }, 
  formSubmit: function (e) {
    let _this = this;
    let form = e.detail.value
    if (!form.studentName || !form.phoneNo || !form.email || !form.company) {
      app.message('请输入完整信息', 'error');
      return;
    }
    if (!utils.checkPhoneNum(form.phoneNo)){
      app.message('手机号有误', 'error')
      return;
    }
    if (!utils.checkEmai(form.email)) {
      app.message('邮箱格式有误', 'error')
      return;
    }
    // _this.setData({
    //   spinShow: true
    // });
    if (this.data.student.studentId){
      form.studentId = this.data.student.studentId
    }
    if (app.globalData.studentInfo.openid){
      form.openid = app.globalData.studentInfo.openid
    }
    form.sex = app.globalData.userInfo.gender
    form.wechat = app.globalData.userInfo.nickName
    app.formPost('/api/wx/student/auth/bind', form)
      .then(res => {
        this.setData({
          spinShow: false
        });
        if (res.code == 1) {
          app.globalData.studentInfo = res.response
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
  },


  inputPhoneNum: function (e) {
    let phoneNumber = e.detail.detail.value
    if (!utils.checkPhoneNum(phoneNumber)) {
      app.message('手机号有误', 'error')
    }

  },
  inputemail: function (e) {
    let email = e.detail.detail.value
    if (!utils.checkEmai(email)){
      app.message('邮箱格式有误', 'error')
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options.title){
      this.data.title = options.title
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    wx.setNavigationBarTitle({
      title: this.data.title
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    debugger
    if (app.globalData.studentInfo.studentId) {
      this.setData({
        student: app.globalData.studentInfo
      })
    }
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

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