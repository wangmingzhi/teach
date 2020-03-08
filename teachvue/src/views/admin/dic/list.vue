<template>
<div class="app-container">
  <el-form :model="queryParam" ref="queryForm" :inline="true">
      <el-form-item label="类型编码：">
        <el-input v-model="queryParam.typeCode"></el-input>
      </el-form-item>
      <el-form-item label="类型名称：">
        <el-input v-model="queryParam.typeName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm" icon="el-icon-search">查询</el-button>
      </el-form-item>
    </el-form>
  <el-row :gutter="20">
      <el-col :span="12">
        <div>
          <el-button type="primary" size="small" icon="el-icon-document-add" @click="createType">新增</el-button>
          <el-button type="primary" size="small" icon="el-icon-edit" @click="editType">修改</el-button>
          <el-popconfirm title="确定删除吗？" iconColor="red" @onConfirm="deleteType">
            <el-button slot="reference" type="primary" size="small" icon="el-icon-delete">删除</el-button>
          </el-popconfirm>
          </div>
        <el-table v-loading="listLoading" ref="typeTable"  :data="typeData" @row-click="typeRowClick" border fit highlight-current-row style="width: 100%;margin-top:10px">
      <el-table-column prop="typeName" label="类型名称" width='100px'/>
      <el-table-column prop="typeCode" label="类型编码" />
      <el-table-column prop="orderNo" label="排序" width='50px'/>
      <el-table-column prop="remarks" label="备注" />
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParam.pageIndex" :limit.sync="queryParam.pageSize"
                @pagination="search"/>
        </el-col>
      <el-col :span="12">
        <div>
          <el-button type="primary" size="small" icon="el-icon-document-add" @click="createDic">新增</el-button>
          <el-button type="primary" size="small" icon="el-icon-edit" @click="editDic">修改</el-button>
          <el-popconfirm title="确定删除吗？" iconColor="red" @onConfirm="deleteDic">
            <el-button slot="reference" type="primary" size="small" icon="el-icon-delete">删除</el-button>
          </el-popconfirm>
          </div>
<el-table v-loading="dicLoading" ref="dicTable" :data="dicData" @row-click="dicRowClick" border fit highlight-current-row style="width: 100%;margin-top:10px">
      <el-table-column prop="dictionaryName" label="字典名称" width='100px'/>
      <el-table-column prop="dictionaryCode" label="字典编码"/>
      <el-table-column prop="orderNo" label="排序" width='50px'/>
      <el-table-column prop="remarks" label="备注" />
    </el-table>
    <pagination v-show="total>0" :total="dicTotal" :page.sync="dicParam.pageIndex" :limit.sync="dicParam.pageSize"
                @pagination="searchDic"/>
      </el-col>
    </el-row>

    <el-dialog title="类型编辑" :visible.sync="dialogTypeVisible">
  <el-form :model="typeForm" ref="typeForm" :rules="rules">
    <el-form-item label="类型名称：" :label-width="formLabelWidth" prop="typeName" required>
      <el-input v-model="typeForm.typeName" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="类型编码" :label-width="formLabelWidth" prop="typeCode" required>
      <el-input v-model="typeForm.typeCode" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="排序：" :label-width="formLabelWidth" prop="orderNo" required>
      <el-input v-model="typeForm.orderNo" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="备注" :label-width="formLabelWidth">
      <el-input v-model="typeForm.remarks" autocomplete="off"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogTypeVisible = false">取 消</el-button>
    <el-button type="primary" @click="submitTypeForm">确 定</el-button>
  </div>
</el-dialog>

<el-dialog title="字典编辑" :visible.sync="dialogDicVisible">
  <el-form :model="dicForm" ref="dicForm" :rules="rules">
    <el-form-item label="字典名称：" :label-width="formLabelWidth" prop="dictionaryName" required>
      <el-input v-model="dicForm.dictionaryName" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="字典编码：" :label-width="formLabelWidth" prop="dictionaryCode" required>
      <el-input v-model="dicForm.dictionaryCode" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="排序：" :label-width="formLabelWidth" prop="orderNo" required>
      <el-input v-model="dicForm.orderNo" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="备注" :label-width="formLabelWidth">
      <el-input v-model="dicForm.remarks" autocomplete="off"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogDicVisible = false">取 消</el-button>
    <el-button type="primary" @click="submitDicForm">确 定</el-button>
  </div>
</el-dialog>
</div>
</template>

<script>
import Pagination from '@/components/Pagination'
import userApi from '@/api/user'

export default {
  components: { Pagination },
  data () {
    return {
      queryParam: {
        typeCode: '',
        typeName: '',
        pageIndex: 1,
        pageSize: 5
      },
      dicParam: {
        typeCode: '',
        typeName: '',
        pageIndex: 1,
        pageSize: 5
      },
      typeRow: {},
      dicRow: {},
      listLoading: true,
      dicLoading: false,
      typeData: [],
      dicData: [],
      total: 0,
      dicTotal: 0,
      dialogTypeVisible: false,
      typeForm: {
        dictionaryId: null,
        typeName: '',
        typeCode: '',
        remarks: ''
      },
      dialogDicVisible: false,
      dicForm: {
        dictionaryId: null,
        typeName: '',
        typeCode: '',
        dictionaryName: '',
        dictionaryCode: '',
        remarks: ''
      },
      formLabelWidth: '120px',
      rules: {
        typeName: [
          { required: true, message: '请输入类型名称', trigger: 'blur' }
        ],
        typeCode: [
          { required: true, message: '请输入类型编码', trigger: 'blur' }
        ],
        dictionaryName: [
          { required: true, message: '请输入字典名称', trigger: 'blur' }
        ],
        dictionaryCode: [
          { required: true, message: '请输入字典编码', trigger: 'blur' }
        ],
        orderNo: [
          { required: true, message: '请输入排序', trigger: 'blur' },
          { type: 'number', message: '排序必须为数字值', trigger: 'blur', transform: (value) => Number(value) }
        ]
      }
    }
  },
  created () {
    this.search()
  },
  methods: {
    search () {
      let _this = this
      this.listLoading = true
      userApi.getDicType(this.queryParam).then(data => {
        const re = data.response
        _this.typeData = re.list
        _this.total = re.total
        _this.queryParam.pageIndex = re.pageNum
        _this.listLoading = false
        _this.typeRow = {}
        if (_this.typeData.length > 0) {
          _this.$refs.typeTable.setCurrentRow(_this.typeData[0])
          _this.typeRow = _this.typeData[0]
          _this.searchDic()
        }
      })
    },
    searchDic () {
      let _this = this
      this.dicLoading = true
      this.dicParam.typeCode = this.typeRow.typeCode
      userApi.getDic(this.dicParam).then(data => {
        const re = data.response
        _this.dicData = re.list
        _this.dicTotal = re.total
        _this.dicParam.pageIndex = re.pageNum
        _this.dicLoading = false
        _this.dicRow = {}
        if (_this.dicData.length > 0) {
          _this.$refs.dicTable.setCurrentRow(_this.dicData[0])
          _this.dicRow = _this.dicData[0]
        }
      })
    },
    typeRowClick (row, column, event) {
      this.typeRow = row
      this.searchDic()
    },
    dicRowClick (row, column, event) {
      this.dicRow = row
    },
    createType () {
      this.clearType()
      this.dialogTypeVisible = true
      this.$refs['typeForm'].resetFields()
    },
    createDic () {
      this.clearDic()
      this.dialogDicVisible = true
      this.$refs['dicForm'].resetFields()
    },
    clearType () {
      this.typeForm = {
        dictionaryId: null,
        typeName: '',
        typeCode: '',
        orderNo: '',
        remarks: ''
      }
    },
    clearDic () {
      this.dicForm = {
        dictionaryId: null,
        typeName: '',
        typeCode: '',
        dictionaryName: '',
        dictionaryCode: '',
        orderNo: '',
        remarks: ''
      }
    },
    editType () {
      if (!this.typeRow.typeCode) {
        this.$message({
          message: '请选择一行数据',
          type: 'warning'
        })
        return
      }
      this.formLoading = true
      userApi.selectDic(this.typeRow.dictionaryId).then(re => {
        this.typeForm = re.response
        this.formLoading = false
        this.dialogTypeVisible = true
      })
    },
    editDic () {
      if (!this.dicRow.dictionaryCode) {
        this.$message({
          message: '请选择一行数据',
          type: 'warning'
        })
        return
      }
      this.formLoading = true
      userApi.selectDic(this.dicRow.dictionaryId).then(re => {
        this.dicForm = re.response
        this.formLoading = false
        this.dialogDicVisible = true
      })
    },
    deleteType () {
      if (!this.typeRow.typeCode) {
        this.$message({
          message: '请选择一行数据',
          type: 'warning'
        })
        return
      }
      let _this = this
      userApi.deleteType(this.typeRow.typeCode).then(re => {
        if (re.code === 1) {
          _this.search()
          _this.$message.success(re.message)
        } else {
          _this.$message.error(re.message)
        }
      })
    },
    deleteDic () {
      if (!this.dicRow.dictionaryCode) {
        this.$message({
          message: '请选择一行数据',
          type: 'warning'
        })
        return
      }
      let _this = this
      userApi.deleteDic(this.dicRow.dictionaryId).then(re => {
        if (re.code === 1) {
          _this.searchDic()
          _this.$message.success(re.message)
        } else {
          _this.$message.error(re.message)
        }
      })
    },
    submitForm () {
      this.queryParam.pageIndex = 1
      this.search()
    },
    submitTypeForm () {
      let _this = this
      this.$refs.typeForm.validate((valid) => {
        if (valid) {
          this.formLoading = true
          userApi.createType(this.typeForm).then(data => {
            if (data.code === 1) {
              _this.$message.success(data.message)
              _this.dialogTypeVisible = false
              _this.search()
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
      this.dicForm.typeCode = this.typeRow.typeCode
      this.dicForm.typeName = this.typeRow.typeName
      let _this = this
      this.$refs.dicForm.validate((valid) => {
        if (valid) {
          this.formLoading = true
          userApi.createDic(this.dicForm).then(data => {
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
    }
  }
}
</script>
