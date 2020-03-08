<template>
<div class="app-container">
  <el-row :gutter="20">
      <el-col :span="24">
      <el-form :model="classForm" ref="classForm" :inline="true">
        <el-form-item label="地区：">
          <el-select v-model="classParam.areaType" clearable>
            <el-option v-for="item in areaTypeOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称：">
          <el-input v-model="classParam.className"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchClass" icon="el-icon-search">查询</el-button>
          <el-button type="primary" icon="el-icon-document-add" @click="createClass">新增</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="listLoading" ref="classTable"  :data="classData" @row-click="typeRowClick" border fit highlight-current-row style="width: 100%;">
        <el-table-column prop="areaType" label="地区" :formatter="areaTypeFormatter"/>/>
        <el-table-column prop="className" label="名称" />
        <el-table-column prop="createUser" label="录入人" />
        <el-table-column prop="createTime" label="录入时间" />
        <el-table-column width="290px" label="操作" align="center">
          <template slot-scope="{row}">
            <el-button size="mini" @click="editClass(row)" class="link-left" type="primary" plain icon="el-icon-edit">修改</el-button>
            <el-popconfirm title="确定删除吗？" iconColor="red" @onConfirm="deleteClass(row)">
              <el-button slot="reference" size="mini" type="danger" class="link-left" icon="el-icon-delete">删除</el-button>
            </el-popconfirm>
          </template>
      </el-table-column>
      </el-table>
      <pagination v-show="total>0" :total="total" :page.sync="classParam.pageIndex" :limit.sync="classParam.pageSize"
                @pagination="searchClass"/>
  </el-col></el-row>
  <el-row :gutter="20">
      <el-col :span="24">
    <!-- <el-divider content-position="left">学员管理</el-divider> -->
      <el-form :model="studentParam" ref="studentForm" :inline="true">
        <el-form-item label="地区：">
          <el-select v-model="studentParam.areaType" clearable @change="areaTypeChange">
            <el-option v-for="item in areaTypeOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级：">
          <el-select v-model="studentParam.classId" clearable @change="classChange">
            <el-option v-for="item in classOptions" :key="item.classId" :label="item.className" :value="item.classId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="姓名：">
          <el-input v-model="studentParam.studentName"></el-input>
        </el-form-item>
        <el-form-item label="手机号：">
          <el-input v-model="studentParam.phoneNo"></el-input>
        </el-form-item>
        <el-form-item label="微信号：">
          <el-input v-model="studentParam.wechat"></el-input>
        </el-form-item>
        <el-form-item label="有效期：">
          <el-date-picker v-model="studentParam.expiryDate" type="date" placeholder="选择日期" value-format="yyyy-MM-dd"></el-date-picker>
        </el-form-item>
        <el-form-item label="授权：">
          <el-select v-model="studentParam.isAuthorize" clearable>
            <el-option v-for="item in isOptions" :key="item.key" :label="item.value" :value="item.key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="邮箱：">
          <el-input v-model="studentParam.email"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchDic" icon="el-icon-search">查询</el-button>
          <el-button type="primary" icon="el-icon-document-add" @click="createStudent">新增</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="listLoading" ref="typeTable"  :data="dicData" border fit highlight-current-row style="width: 100%;">
        <el-table-column prop="studentName" label="姓名"/>
        <el-table-column prop="sex" label="性别" :formatter="sexFormatter" width='50px'/>
        <el-table-column prop="phoneNo" label="手机号" />
        <el-table-column prop="email" label="邮箱" :show-overflow-tooltip="true"/>
        <el-table-column prop="wechat" label="微信号" />
        <el-table-column prop="company" label="企业" :show-overflow-tooltip="true"/>
        <el-table-column prop="expiryDate" label="有效期" width='100px' />
        <el-table-column prop="isAuthorize" label="授权"  :formatter="isAuthorizeFormatter" width='50px'/>
        <el-table-column prop="createUser" label="录入人" />
        <el-table-column width="290px" label="操作" align="center">
          <template slot-scope="{row}">
            <el-button size="mini" @click="changeStatus(row)" class="link-left" :type="row.isAuthorize == '1' ? 'info' : 'success'" plain :icon="row.isAuthorize == '1' ? 'el-icon-lock' : 'el-icon-key'">
              {{ row.isAuthorize == '1' ? '取消' : '授权' }}</el-button>
            <el-button size="mini" @click="editDic(row)" class="link-left" type="primary" plain icon="el-icon-edit">修改</el-button>
            <el-popconfirm title="确定删除吗？" iconColor="red" @onConfirm="deleteDic(row)">
              <el-button slot="reference" size="mini" type="danger" class="link-left" icon="el-icon-delete">删除</el-button>
            </el-popconfirm>
          </template>
      </el-table-column>
      </el-table>
      <pagination v-show="dicTotal>0" :total="dicTotal" :page.sync="studentParam.pageIndex" :limit.sync="studentParam.pageSize"
                @pagination="searchDic"/>
  </el-col></el-row>

  <el-dialog title="班级编辑" :visible.sync="dialogClassVisible">
    <el-form :model="classForm" ref="classForm" :rules="rules">
      <el-form-item label="地区：" :label-width="formLabelWidth" prop="areaType" required>
        <el-select v-model="classForm.areaType" clearable>
            <el-option v-for="item in areaTypeOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="名称:" :label-width="formLabelWidth" prop="className" required>
        <el-input v-model="classForm.className" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogClassVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitclassForm">确 定</el-button>
    </div>
</el-dialog>
<el-dialog title="学员编辑" :visible.sync="dialogDicVisible" width="50%">
  <el-form :model="dicForm" ref="dicForm" :rules="rules">
      <el-row :gutter="10">
        <el-col :span="12">
          <el-form-item label="地区："  :label-width="formLabelWidth">
            <el-select v-model="dicForm.areaType" :disabled="newForm"  clearable @change="areaTypeChange2">
              <el-option v-for="item in areaTypeOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="班级：" :label-width="formLabelWidth">
            <el-select v-model="dicForm.classId" :disabled="newForm" clearable >
              <el-option v-for="item in classOptions2" :key="item.classId" :label="item.className" :value="item.classId"></el-option>
            </el-select>
        </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="12">
          <el-form-item label="姓名：" :label-width="formLabelWidth" prop="studentName" required>
            <el-input v-model="dicForm.studentName" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="性别：" :label-width="formLabelWidth">
            <el-select v-model="dicForm.sex" clearable>
              <el-option v-for="item in sexEnum" :key="item.key" :label="item.value" :value="item.key"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="12">
          <el-form-item label="微信号：" :label-width="formLabelWidth"  prop="wechat" required>
            <el-input v-model="dicForm.wechat" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机号：" :label-width="formLabelWidth" prop="phoneNo" required>
            <el-input v-model="dicForm.phoneNo" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="邮箱：" :label-width="formLabelWidth" prop="email">
            <el-input v-model="dicForm.email" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="企业：" :label-width="formLabelWidth">
          <el-input v-model="dicForm.company" autocomplete="off"></el-input>
        </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="12">
          <el-form-item label="授权：" :label-width="formLabelWidth">
          <el-select v-model="dicForm.isAuthorize" clearable>
            <el-option v-for="item in isOptions" :key="item.key" :label="item.value" :value="item.key"></el-option>
          </el-select>
        </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="有效期：" :label-width="formLabelWidth">
            <el-date-picker v-model="dicForm.expiryDate" type="date" placeholder="选择日期" value-format="yyyy-MM-dd"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogDicVisible = false">取 消</el-button>
    <el-button type="primary" @click="submitDicForm">确 定</el-button>
  </div>
</el-dialog>
</div>
</template>

<script>
import { dicFormat, format, isOptions, sexEnum, initSelect, dateFormat } from '@/utils/util'
import {validPhone, validateEmail} from '@/utils/validate'
import Pagination from '@/components/Pagination'
import classApi from '@/api/class'

export default {
  components: { Pagination },
  data () {
    return {
      classParam: {
        areaType: '',
        className: '',
        pageIndex: 1,
        pageSize: 5
      },
      query: {
        areaType: '',
        pageIndex: 1,
        pageSize: 5
      },
      studentParam: {
        areaType: '',
        className: '',
        pageIndex: 1,
        pageSize: 5
      },
      areaTypeOptions: [],
      sexEnum: sexEnum,
      isOptions: isOptions,
      classOptions: [],
      classOptions2: [],
      dialogClassVisible: false,
      classForm: {
        classId: null
      },
      dicForm: {
        studentId: null
      },
      selectType: '',
      selectClass: '',
      listLoading: true,
      dicLoading: false,
      classData: [],
      dicData: [],
      total: 0,
      dicTotal: 0,
      dialogTypeVisible: false,
      dialogDicVisible: false,
      newForm: false,
      formLabelWidth: '80px',
      rules: {
        areaType: [
          { required: true, message: '请选择区域', trigger: 'blur' }
        ],
        className: [
          { required: true, message: '请输入班级名称', trigger: 'blur' }
        ],
        studentName: [
          { required: true, message: '请输入学员姓名', trigger: 'blur' }
        ],
        phoneNo: [
          { required: true, validator: validPhone, trigger: 'blur' }
        ],
        email: [
          { required: false, validator: validateEmail, trigger: 'blur' }
        ],
        wechat: [
          { required: true, message: '请输入微信号', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.init()
    this.searchClass()
    this.searchDic()
  },
  methods: {
    init () {
      initSelect('AREA_TYPE_CODE', data => {
        this.areaTypeOptions = data
      })
    },
    searchClass () {
      let _this = this
      classApi.pageList(this.classParam).then(data => {
        const re = data.response
        _this.classData = re.list
        _this.total = re.total
        _this.classParam.pageIndex = re.pageNum
        _this.listLoading = false
        // if (_this.classData.length > 0) {
        //   _this.$refs.classTable.setCurrentRow(_this.classData[0])
        // }
      })
    },
    searchDic () {
      let _this = this
      classApi.pageListStudent(this.studentParam).then(data => {
        const re = data.response
        _this.dicData = re.list
        _this.dicTotal = re.total
        _this.studentParam.pageIndex = re.pageNum
        _this.dicLoading = false
      })
    },
    typeRowClick (row, column, event) {
      this.selectType = row.areaType
      this.selectClass = row.classId
      // 取消选中
      if (this.studentParam.classId === row.classId) {
        // this.studentParam.classId = null
        // this.studentParam.areaType = null
        // this.$refs.classTable.setCurrentRow()
        // this.searchDic()
      } else {
        this.studentParam.areaType = row.areaType
        let _this = this
        this.initClassOptions(row.areaType, function (re) {
          _this.classOptions = re.list
          _this.studentParam.classId = row.classId
          _this.searchDic()
        })
      }
    },
    initClassOptions (areaType, callbak) {
      this.query.areaType = areaType
      this.query.pageIndex = 1
      this.query.pageSize = 1000
      classApi.pageList(this.query).then(data => {
        const re = data.response
        callbak(re)
      })
    },
    changeStatus (row) {
      let _this = this
      classApi.changeStatus(row.studentId).then(re => {
        if (re.code === 1) {
          row.isAuthorize = re.response
          _this.$message.success(re.message)
        } else {
          _this.$message.error(re.message)
        }
      })
    },
    areaTypeChange (val) {
      let _this = this
      _this.studentParam.classId = null
      if (!val) {
        _this.classOptions = []
        return
      }
      _this.initClassOptions(val, function (re) {
        _this.classOptions = re.list
      })
    },
    areaTypeChange2 (val) {
      let _this = this
      if (!val) {
        _this.classOptions2 = []
        return
      }
      _this.initClassOptions(val, function (re) {
        _this.classOptions2 = re.list
      })
    },
    classChange (val) {
      this.$forceUpdate()
    },
    createClass () {
      this.classForm = {
        classId: null,
        areaType: '',
        className: ''
      }
      this.dialogClassVisible = true
      this.$refs['classForm'].resetFields()
    },
    createStudent () {
      if (!this.selectType) {
        this.$message({
          message: '请选择班级',
          type: 'warning'
        })
        return
      }
      var date = new Date()
      date.setMonth(date.getMonth() + 10)
      var expiryDate = dateFormat(date)
      this.newForm = false
      this.dicForm = {
        studentId: null,
        areaType: this.selectType,
        classId: this.selectClass,
        studentName: '',
        phoneNo: '',
        expiryDate: expiryDate,
        isAuthorize: '0'
      }
      this.dialogDicVisible = true
      this.$refs.dicForm.resetFields()
    },
    editClass (row) {
      this.formLoading = true
      classApi.select(row.classId).then(re => {
        this.classForm = re.response
        this.formLoading = false
        this.dialogClassVisible = true
      })
    },
    editDic (row) {
      this.newForm = false
      this.formLoading = true
      classApi.selectStudent(row.studentId).then(re => {
        this.dicForm = re.response
        this.formLoading = false
        this.dialogDicVisible = true
        this.areaTypeChange2(this.dicForm.areaType)
      })
    },
    deleteClass (row) {
      let _this = this
      if (_this.dicTotal > 0) {
        _this.$message.error('班级有学员，无法删除')
        return
      }
      classApi.delete(row.classId).then(re => {
        if (re.code === 1) {
          _this.searchClass()
          _this.$message.success(re.message)
        } else {
          _this.$message.error(re.message)
        }
      })
    },
    deleteDic (row) {
      let _this = this
      classApi.deleteStudent(row.studentId).then(re => {
        if (re.code === 1) {
          _this.searchDic()
          _this.$message.success(re.message)
        } else {
          _this.$message.error(re.message)
        }
      })
    },
    submitclassForm () {
      let _this = this
      this.$refs.classForm.validate((valid) => {
        if (valid) {
          this.formLoading = true
          classApi.edit(this.classForm).then(data => {
            if (data.code === 1) {
              _this.$message.success(data.message)
              _this.dialogClassVisible = false
              _this.searchClass()
            } else {
              _this.$message.error(data.message)
              _this.formLoading = false
            }
          }).catch(e => {
            _this.formLoading = false
          })
        } else {
          return false
        }
      })
    },
    submitDicForm () {
      let _this = this
      this.$refs.dicForm.validate((valid) => {
        if (valid) {
          this.formLoading = true
          classApi.editStudent(this.dicForm).then(data => {
            if (data.code === 1) {
              _this.$message.success(data.message)
              _this.dialogDicVisible = false
              _this.searchDic()
            } else {
              _this.$message.error(data.message)
              _this.formLoading = false
            }
          }).catch(e => {
            _this.formLoading = false
          })
        } else {
          return false
        }
      })
    },
    areaTypeFormatter  (row, column, cellValue, index) {
      return dicFormat(this.areaTypeOptions, cellValue)
    },
    isAuthorizeFormatter (row, column, cellValue, index) {
      return format(isOptions, cellValue)
    },
    sexFormatter (row, column, cellValue, index) {
      return format(sexEnum, cellValue)
    }
  }
}
</script>
