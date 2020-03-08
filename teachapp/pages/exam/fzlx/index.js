// pages/exam/fzlx/index.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list:[],
    titile:'',
    trainType: '',
    exerciseType:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      trainType: options.trainType,
      exerciseType: options.exerciseType,
      titile: options.titile
    });
  },

  getBank(trainType, exerciseType){
    let _this = this;
    // _this.setData({
    //   spinShow: true
    // });
    app.formPost('/api/wx/student/exam/getBank', {
      "trainType": trainType, "exerciseType": exerciseType
    })
      .then(res => {
        this.setData({
          spinShow: false
        });
        if (res.code == 1) {
          _this.setData({
            list: res.response,
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

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    wx.setNavigationBarTitle({
      title: this.data.trainType + '-' + this.data.titile
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.getBank(this.data.trainType, this.data.exerciseType)
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