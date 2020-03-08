const app = getApp()
// pages/exam/do/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    bankId: null,
    exercise: null,
    nextExercise: null,
    qIndex: 1,
    exerciseNum: 0,
    title: "",
    click: "",
    right: "",
    error: "",
    userSelect: null,
    isRight: "0",
    isErrorBook: false,
    isNow: true,//是否及时判题
    trainType: '',
    time: 0,
    doTime:0,
    modalTile:"",
    modalActions: [
      {
        name: '确定',
        color: '#2d8cf0!important'
      }],
    showModal: false,
    finish:false,
    interval:null,
    answers: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      isErrorBook: options.isErrorBook === 'true',
      isNow: options.markType === 'IMMED' || options.isErrorBook === 'true',
      trainType: options.trainType,
      bankId: options.bankId,
      exerciseNum: Number(options.exerciseNum),
      title: options.title,
      qIndex: Number(options.qIndex),
      time: options.time
    });
    if (this.data.time > 0){
      this.data.interval = setInterval(this.countDown, 1000*60);
    }

    //重新开始
    if (!this.data.isErrorBook && this.data.qIndex > this.data.exerciseNum) {
      this.setData({
        qIndex: 1
      })
      this.restExercise(this.data.bankId, false)
    }else{
      this.getExercise(this.data.bankId, this.data.qIndex);
    }
    
  },
  countDown(){
    if(this.data.finish){
      if (this.data.interval) {
        clearInterval(this.data.interval)
      }
      return
    }
    if (this.data.time > 0){
      this.setData({
        time: this.data.time - 1,
        doTime: this.data.doTime +1
      })
    }
    if(this.data.time == 0){
      if (this.data.interval) {
        clearInterval(this.data.interval)
      }
      app.message('答题超时', 'error')
      this.getAswers()
    }
  },
  bindClick(e) {
    if (this.data.click) {
      return
    }
    var value = e.currentTarget.dataset.id
    this.setSelect(this.data.exercise.selectionCorrect, value)
    this.doExercise()
  },
  setSelect(selectionCorrect, value) {
    if (selectionCorrect && selectionCorrect.indexOf(value) > -1) {
      this.setData({
        click: value,
        right: value,
        isRight: "1"
      })
    } else {
      this.setData({
        click: value,
        error: value,
        right: this.data.exercise.selectionCorrect,
        isRight: "0"
      })
    }
  },
  bindClickPre(e) {
    if (this.data.qIndex == 1) {
      return
    }
    this.getExercise(this.data.bankId, Number(this.data.qIndex) - 1);
  },
  bindClickNext(e) {
    if (this.data.qIndex == this.data.exerciseNum) {
      return
    }
    // this.getExercise(this.data.bankId, Number(this.data.qIndex) + 1);
      // this.doExercise();
      if (this.data.nextExercise){
        this.setData({
          click: "",
          right: "",
          error: "",
          exercise: this.data.nextExercise,
          qIndex: this.data.nextExercise.orderNo
        });
      }else{
        this.getExercise(this.data.bankId, Number(this.data.qIndex) + 1);
      }
  
  },
  getExercise(bankId, orderNo) {
    let _this = this;
    // _this.setData({
    //   spinShow: true
    // });
    app.formPost('/api/wx/student/exam/getExercise', {
      'isErrorBook': _this.data.isErrorBook,
      'trainType':  _this.data.trainType,
      "bankId": bankId,
      "orderNo": orderNo
    }).then(res => {
      this.setData({
        spinShow: false
      });
      if (res.code == 1) {
        _this.setData({
          click: "",
          right: "",
          error: "",
          userSelect: res.response.userSelect,
          exercise: res.response,
          nextExercise:null,
          qIndex: res.response.orderNo
        });
        if (res.response.userSelect) {
          this.setSelect(this.data.exercise.selectionCorrect, res.response.userSelect)
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
  doExercise() {
    let _this = this;
    // _this.setData({
    //   spinShow: true
    // });
    app.formPost('/api/wx/student/exam/doExercise', {
      'isErrorBook': _this.data.isErrorBook,
      'trainType': _this.data.trainType,
      "isRight": this.data.isRight,
      "bankId": this.data.exercise.bankId,
      "orderNo": this.data.qIndex,
      "excerId": this.data.exercise.excerId,
      "selectionCorrect": this.data.exercise.selectionCorrect,
      "excerId": this.data.exercise.excerId,
      "selectionCorrect": this.data.exercise.selectionCorrect,
      "selectionAnswer": this.data.click
    }).then(res => {
      this.setData({
        spinShow: false
      });
      if (res.code == 1) {
        this.data.nextExercise = res.response
        if (this.data.isErrorBook){
          this.data.exerciseNum = res.response.exerciseNum
        }
      } else if (res.code == 0){
        this.setData({
          isNow:true,
          finish:true
        })
        if(!this.data.isErrorBook){
          this.getAswers()
        }
        app.message(res.message, 'error')
      }else {
        app.message(res.message, 'error')
      }
    }).catch(e => {
      _this.setData({
        spinShow: false
      });
      app.message(e.message, 'error')
    })
  },
  restExercise(bankId, timeout, callback) {
    let _this = this;
    // _this.setData({
    //   spinShow: true
    // });
    app.formPost('/api/wx/student/exam/restExercise', {
      "bankId": bankId,
      "timeout": timeout
    }).then(res => {
      if (callback){
        callback()
      }
      this.setData({
        spinShow: false
      });
      if (res.code == 1) {
        _this.setData({
          click: "",
          right: "",
          error: "",
          exercise: res.response,
          qIndex: res.response.orderNo
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
  deleteError() {
    let _this = this;
    // _this.setData({
    //   spinShow: true
    // });
    app.formPost('/api/wx/student/exam/deleteError', {
      "excerId": this.data.exercise.excerId
    }).then(res => {
      this.setData({
        spinShow: false
      });
      if (res.code == 1) {
        _this.setData({
          qIndex: this.data.qIndex
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
  modalClick({ detail }){
    let _this = this;
    const index = detail.index;
    if (index === 0) {
      _this.setData({
        showModal: false,
      });
      if (this.data.time == 0 && !this.data.finish) {
        this.restExercise(this.data.bankId, true, function(){
          wx.navigateBack({
            delta: 1
          })
        })
      }
    }
  },
  gotoIndex(e){
    if (this.data.time == 0 && !this.data.finish) {
      app.message('答题超时', 'error')
      return
    }
    let _this = this;
    _this.setData({
      nextExercise: null,
      showModal: false,
    });
    var value = e.currentTarget.dataset.id
    _this.getExercise(this.data.bankId, value)
  },
  getAswers() {
    let _this = this;
    // _this.setData({
    //   spinShow: true
    // });
    app.formPost('/api/wx/student/exam/getAswers', {
      "bankId": this.data.exercise.bankId,
      "exerciseNum": this.data.exerciseNum
    }).then(res => {
      this.setData({
        spinShow: false
      });
      if (res.code == 1) {
        _this.setData({
          answers: res.response.info,
          modalTile: res.response.title,
          showModal: true
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
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {
    if(this.data.isErrorBook){
      wx.setNavigationBarTitle({
        title: '错题本'
      })
    }else{
      wx.setNavigationBarTitle({
        title: this.data.title
      })
    }
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

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
    debugger
    if (this.data.interval){
      clearInterval(this.data.interval)
    }
    if (this.data.time > 0 && !this.data.finish){
      this.restExercise(this.data.bankId, true)
    }
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

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})