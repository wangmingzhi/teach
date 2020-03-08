const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    paperType: '',
    trainType: [],
    exerciseType: [],
    notice: null,
    errorCount: 0,
    pageIndex: 1,
    total: 0,
    tableData: [],
    loadMoreTip: '暂无数据',
    loadMoreLoad: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.getType();
  },
  getType: function() {
    let _this = this;
    // _this.setData({
    //   spinShow: true
    // });
    app.formPost('/api/wx/student/index/getType', {})
      .then(res => {
        _this.setData({
          spinShow: false
        });
        if (res.code == 1) {
          _this.setData({
            trainType: res.response,
            paperType: res.response[0].id,
            exerciseType: res.response[0].children,
            notice: res.response[0].name
          });
          _this.getErrorCount()
          _this.getScoreSort()
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
  getScoreSort: function(rest) {
    let _this = this;
    // _this.setData({
    //   spinShow: true
    // });
    app.formPost('/api/wx/student/index/getScoreSort', {
        'trainType': _this.data.paperType,
        'pageIndex': _this.data.pageIndex
      })
      .then(res => {
        _this.setData({
          spinShow: false
        });
        if (res.code == 1) {
          const re = res.response
          _this.setData({
            pageIndex: re.pageNum,
            tableData: rest ? re.list : _this.data.tableData.concat(re.list),
            total: re.pages
          });
          if (re.pageNum >= re.pages) {
            _this.setData({
              loadMoreLoad: false,
              loadMoreTip: '暂无数据'
            });
          }
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
  getErrorCount: function() {
    if (!app.globalData.studentInfo.studentId) {
      return
    }
    let _this = this;
    // _this.setData({
    //   spinShow: true
    // });
    app.formPost('/api/wx/student/exam/getErrorCount', {
        'trainType': _this.data.paperType
      })
      .then(res => {
        _this.setData({
          spinShow: false
        });
        if (res.code == 1) {
          const re = res.response
          _this.setData({
            errorCount: re,
          });
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
  tabChange({
    detail
  }) {
    this.setData({
      paperType: detail.key
    });

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    if (this.data.paperType){
      this.getErrorCount()
      this.getScoreSort(true)
    }
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if (!this.loading && this.data.pageIndex < this.data.total) {
      this.setData({
        loadMoreLoad: true,
        loadMoreTip: '正在加载'
      });
      this.setData({
        pageIndex: this.data.pageIndex + 1
      });
      this.getScoreSort()
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})