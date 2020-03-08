<template>
<div class="app-container">
  <el-row :gutter="20">
      <el-col :span="24">
      <el-form :model="typeForm" ref="typeForm" :inline="true">
        <el-form-item label="编号：">
          <el-input v-model="queryParam.bankNo"></el-input>
        </el-form-item>
        <el-form-item label="名称：">
          <el-input v-model="queryParam.bankName"></el-input>
        </el-form-item>
        <el-form-item label="科目：">
          <el-select v-model="queryParam.trainType" clearable>
            <el-option v-for="item in tranOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="类别：">
          <el-select v-model="queryParam.exerciseType" clearable>
            <el-option v-for="item in exerOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="计时：">
          <el-select v-model="queryParam.isTime" clearable>
            <el-option v-for="item in isTimeOptions" :key="item.key" :label="item.value" :value="item.key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="判题方式：">
          <el-select v-model="queryParam.markType" clearable>
            <el-option v-for="item in judgeOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="search" icon="el-icon-search">查询</el-button>
          <el-button type="primary" icon="el-icon-document-add" @click="createType">新增</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="listLoading" ref="typeTable"  :data="typeData" @row-click="typeRowClick" border fit highlight-current-row  style="width: 100%;">
        <el-table-column prop="orderNo" label="序号" width="50px"/>
        <el-table-column prop="bankNo" label="题库编号" :show-overflow-tooltip="true"/>
        <el-table-column prop="trainType" label="培训科目" :formatter="tranFormatter"/>
        <el-table-column prop="exerciseType" label="习题类别" :formatter="exerFormatter"/>
        <el-table-column prop="bankName" label="题库名称" :show-overflow-tooltip="true"/>
        <el-table-column prop="exerciseNum" label="习题数量" />
        <el-table-column prop="isTime" label="是否计时" :formatter="isTimeFormatter"/>
        <el-table-column prop="time" label="计时时间" />
        <el-table-column prop="markType" label="判题方式" :formatter="judgeFormatter"/>
        <!-- <el-table-column prop="remarks" label="是否启用" :formatter="domainFormatter"/> -->
        <el-table-column width="200px" label="操作" align="center">
          <template slot-scope="{row}">
            <el-button size="mini" @click="editType(row)" class="link-left" icon="el-icon-edit" type="primary" plain>修改</el-button>
            <el-popconfirm title="确定删除吗？" iconColor="red" @onConfirm="deleteType(row)">
              <el-button slot="reference" size="mini" type="danger" class="link-left" icon="el-icon-delete">删除</el-button>
            </el-popconfirm>
          </template>
      </el-table-column>
      </el-table>
      <pagination v-show="total>0" :total="total" :page.sync="queryParam.pageIndex" :limit.sync="queryParam.pageSize"
                @pagination="search"/>
  </el-col></el-row>
  <el-row :gutter="20">
    <el-col :span="24">
      <el-form :model="dicParam" ref="qForm" :inline="true">
        <el-form-item label="序号：">
          <el-input v-model="dicParam.orderNo"></el-input>
        </el-form-item>
        <el-form-item label="内容：">
          <el-input v-model="dicParam.content"></el-input>
        </el-form-item>
        <el-form-item label="领域：">
          <el-select v-model="dicParam.ownDomain" clearable>
            <el-option v-for="item in domainOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="过程组：">
          <el-select v-model="dicParam.ownProcess" clearable>
            <el-option v-for="item in processOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchDic(null)" icon="el-icon-search">查询</el-button>
          <el-button type="primary" icon="el-icon-document-add" @click="createDic">新增</el-button>
          <el-upload class="upload" action="aa" accept="application/vnd.ms-excel"
                :multiple = "false" :show-file-list = "false"
                :http-request="uploadFile">
                <el-button type="primary" icon="el-icon-upload2">导入</el-button>
          </el-upload>
          <el-button type="primary" @click="exportQ" icon="el-icon-download">导出</el-button>
          <el-button type="primary" @click="download" icon="el-icon-download">下载模板</el-button>
          <el-popconfirm title="确定删除吗？" iconColor="red" @onConfirm="multiDelete()" style="padding-left:10px">
            <el-button type="primary" slot="reference" icon="el-icon-delete">删除</el-button>
          </el-popconfirm>
        </el-form-item>
      </el-form>
      <el-table v-loading="dicLoading" ref="dicTable" row-key="excerId"  @selection-change="handleSelectionChange" :data="dicData" border fit highlight-current-row style="width: 100%;">
        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column prop="orderNo" label="序号" width="50px"/>
        <el-table-column prop="content" label="习题内容" :show-overflow-tooltip="true" />
        <el-table-column prop="selectionA" label="A选项" :show-overflow-tooltip="true"/>
        <el-table-column prop="selectionB" label="B选项" :show-overflow-tooltip="true"/>
        <el-table-column prop="selectionC" label="C选项" :show-overflow-tooltip="true"/>
        <el-table-column prop="selectionD" label="D选项" :show-overflow-tooltip="true"/>
        <el-table-column prop="selectionCorrect" label="正确选项" />ic
        <el-table-column prop="answer" label="解析" :show-overflow-tooltip="true"/>
        <el-table-column prop="ownDomain" label="所属领域" :formatter="domainFormatter"/>
        <el-table-column prop="ownProcess" label="所属过程组" :formatter="processFormatter"/>
        <el-table-column width="200px" label="操作" align="center">
          <template slot-scope="{row}">
            <el-button size="mini" @click="editDic(row)" class="link-left" icon="el-icon-edit" type="primary" plain>修改</el-button>
            <el-popconfirm title="确定删除吗？" iconColor="red" @onConfirm="deleteDic(row)">
              <el-button slot="reference" size="mini" type="danger" class="link-left" icon="el-icon-delete">删除</el-button>
            </el-popconfirm>
          </template>
      </el-table-column>
      </el-table>
      <pagination v-show="dicTotal>0" :total="dicTotal" :page.sync="dicParam.pageIndex" :limit.sync="dicParam.pageSize"
                @pagination="searchDic"/>
  </el-col></el-row>

  <el-dialog title="题库编辑" :visible.sync="dialogTypeVisible">
    <el-form :model="typeForm" ref="typeForm" :rules="rulesType">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="培训科目：" :label-width="formLabelWidth" prop="trainType">
            <el-select v-model="typeForm.trainType" clearable>
              <el-option v-for="item in tranOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="习题类别：" :label-width="formLabelWidth" prop="exerciseType">
            <el-select v-model="typeForm.exerciseType" clearable>
              <el-option v-for="item in exerOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
            </el-select>
        </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="题库名称：" :label-width="formLabelWidth" prop="bankName">
            <el-input v-model="typeForm.bankName" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="题库序号：" :label-width="formLabelWidth" prop="orderNo">
            <el-input v-model="typeForm.orderNo" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="习题数量：" :label-width="formLabelWidth" prop="exerciseNum">
            <el-input v-model.number="typeForm.exerciseNum" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="判题方式：" :label-width="formLabelWidth" prop="markType">
            <el-select v-model="typeForm.markType" clearable>
              <el-option v-for="item in judgeOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="是否计时：" :label-width="formLabelWidth">
            <el-switch v-model="typeForm.isTime" active-value="1" inactive-value="0" @change="isTimeChange"></el-switch>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="计时时间：" :label-width="formLabelWidth" prop="time">
            <el-input v-model.number="typeForm.time" autocomplete="off" :disabled="typeForm.isTime != '1' "></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <!-- <el-col :span="12">
          <el-form-item label="是否启用：" :label-width="formLabelWidth">
            <el-input v-model="typeForm.exerciseNum" autocomplete="off"></el-input>
          </el-form-item>
        </el-col> -->
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogTypeVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitTypeForm">确 定</el-button>
    </div>
  </el-dialog>

<el-dialog title="习题编辑" :visible.sync="dialogDicVisible" width="60%">
  <el-form :model="dicForm" ref="dicForm" :rules="rulesDic">
      <el-row :gutter="10">
        <el-col :span="8">
          <el-form-item label="培训科目：" :label-width="formLabelWidth"  prop="trainType">
            <el-select v-model="dicForm.trainType" clearable>
              <el-option v-for="item in tranOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="习题类别：" :label-width="formLabelWidth" prop="exerciseType">
            <el-select v-model="dicForm.exerciseType" clearable @change="exerciseTypeChange">
              <el-option v-for="item in exerOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
            </el-select>
        </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="题库名称：" :label-width="formLabelWidth" prop="bankId">
            <el-select v-model="dicForm.bankId" clearable>
              <el-option v-for="item in bankOptions" :key="item.bankId" :label="item.bankName" :value="item.bankId"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="8">
          <el-form-item label="所属领域：" :label-width="formLabelWidth">
            <el-select v-model="dicForm.ownDomain" clearable>
              <el-option v-for="item in domainOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="所属过程组：" :label-width="formLabelWidth">
            <el-select v-model="dicForm.ownProcess" clearable>
              <el-option v-for="item in processOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="习题序号：" :label-width="formLabelWidth" prop="orderNo">
            <el-input v-model="dicForm.orderNo" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="24">
          <el-form-item label="习题内容：" :label-width="formLabelWidth" prop="content">
            <el-input type='textarea' v-model="dicForm.content" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="12">
          <el-form-item label="A选项：" :label-width="formLabelWidth" prop="selectionA">
            <el-input v-model="dicForm.selectionA" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="B选项：" :label-width="formLabelWidth" prop="selectionB">
            <el-input v-model="dicForm.selectionB" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="12">
          <el-form-item label="C选项：" :label-width="formLabelWidth">
            <el-input v-model="dicForm.selectionC" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="D选项：" :label-width="formLabelWidth">
            <el-input v-model="dicForm.selectionD" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="12">
          <el-form-item label="正确答案：" :label-width="formLabelWidth" prop="checkList">
          <el-checkbox-group v-model="checkList" @change="checkListChange">
            <el-checkbox label="A"></el-checkbox>
            <el-checkbox label="B"></el-checkbox>
            <el-checkbox label="C"></el-checkbox>
            <el-checkbox label="D"></el-checkbox>
          </el-checkbox-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="解析：" :label-width="formLabelWidth" prop="answer">
            <el-input type='textarea' v-model="dicForm.answer" autocomplete="off"></el-input>
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
import { dicFormat, format, isOptions, initSelect } from '@/utils/util'
import Pagination from '@/components/Pagination'
import questionApi from '@/api/question'

export default {
  components: { Pagination },
  data () {
    let _this = this
    var validSelectionCorrect = (rule, value, callback) => {
      if (!_this.checkList || _this.checkList.length === 0) {
        callback(new Error('请设置正确答案'))
      } else {
        _this.$refs.dicForm.rules.checkList[0].required = false
        callback()
      }
    }
    var validTime = (rule, value, callback) => {
      if (_this.typeForm.isTime === '1') {
        if (/^[0-9]+$/.test(value)) {
          callback()
        } else {
          callback(new Error('计时时间必须为数字值'))
        }
      } else {
        callback()
      }
    }
    return {
      queryParam: {
        pageIndex: 1,
        pageSize: 5
      },
      dicParam: {
        pageIndex: 1,
        pageSize: 5
      },
      query: {},
      isTimeOptions: isOptions,
      judgeOptions: [],
      tranOptions: [],
      exerOptions: [],
      domainOptions: [],
      processOptions: [],
      bankOptions: [],
      classOptions: [],
      listLoading: true,
      dicLoading: false,
      typeData: [],
      dicData: [],
      total: 0,
      dicTotal: 0,
      dialogTypeVisible: false,
      typeForm: {
        bankId: null
      },
      dialogDicVisible: false,
      dicForm: {
        excerId: null
      },
      selectRow: null,
      multipleSelection: [],
      checkList: [],
      formLabelWidth: '100px',
      rulesType: {
        trainType: [
          { required: true, message: '请选择培训科目', trigger: ['change', 'blur'] }
        ],
        exerciseType: [
          { required: true, message: '请选习题类别', trigger: ['change', 'blur'] }
        ],
        bankName: [
          { required: true, message: '请输入题库名称', trigger: 'blur' }
        ],
        markType: [
          { required: true, message: '请选择判题方式', trigger: 'blur' }
        ],
        exerciseNum: [
          { required: true, type: 'number', message: '习题数量必须为数字值', trigger: 'blur', transform: (value) => Number(value) }
        ],
        orderNo: [
          { required: true, type: 'number', message: '序号必须为数字值', trigger: 'blur', transform: (value) => Number(value) }
        ],
        time: [
          { required: false, type: 'number', validator: validTime, trigger: 'blur' }
        ]
      },
      rulesDic: {
        trainType: [
          { required: true, message: '请选择培训科目', trigger: ['change', 'blur'] }
        ],
        exerciseType: [
          { required: true, message: '请选习题类别', trigger: ['change', 'blur'] }
        ],
        bankId: [
          { required: true, message: '请选择题库', trigger: ['change', 'blur'] }
        ],
        content: [
          { required: true, message: '请输入习题内容', trigger: 'blur' }
        ],
        answer: [
          { required: true, message: '请输入习题解析', trigger: 'blur' }
        ],
        selectionA: [
          { required: true, message: '请输入习题选项', trigger: 'blur' }
        ],
        selectionB: [
          { required: true, message: '请输入习题选项', trigger: 'blur' }
        ],
        orderNo: [
          { required: true, type: 'number', message: '序号必须为数字值', trigger: 'blur', transform: (value) => Number(value) }
        ],
        checkList: [
          { required: true, validator: validSelectionCorrect, trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.init()
    this.search()
  },
  methods: {
    init () {
      initSelect('JUDGE_TYPE', data => {
        this.judgeOptions = data
      })
      initSelect('TRAN_TYPE_CODE', data => {
        this.tranOptions = data
      })
      initSelect('EXER_TYPE_CODE', data => {
        this.exerOptions = data
      })
      initSelect('DOMAIN', data => {
        this.domainOptions = data
      })
      initSelect('PROCESS', data => {
        this.processOptions = data
      })
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    exerciseTypeChange (val) {
      if (this.dicForm.trainType != null) {
        this.loadBank(this.dicForm.trainType, val, function () {})
      }
    },
    loadBank (trainType, exerciseType, callback) {
      this.query.trainType = trainType
      this.query.exerciseType = exerciseType
      this.query.pageIndex = 1
      this.query.pageSize = 50
      questionApi.pageListBank(this.query).then(data => {
        const re = data.response
        this.bankOptions = re.list
        callback()
      })
    },
    search () {
      let _this = this
      _this.listLoading = true
      questionApi.pageListBank(this.queryParam).then(data => {
        const re = data.response
        _this.typeData = re.list
        _this.total = re.total
        _this.queryParam.pageIndex = re.pageNum
        _this.listLoading = false
        if (_this.typeData.length > 0) {
          _this.$refs.typeTable.setCurrentRow(_this.typeData[0])
          this.typeRowClick(_this.typeData[0])
          // _this.dicParam.bankId = _this.typeData[0].bankId
          // _this.searchDic()
        }
      })
    },
    searchDic (callback) {
      let _this = this
      questionApi.pageList(this.dicParam).then(data => {
        const re = data.response
        _this.dicData = re.list
        _this.dicTotal = re.total
        _this.dicParam.pageIndex = re.pageNum
        _this.dicLoading = false
        if (callback) {
          callback(re.total)
        }
      })
    },
    typeRowClick (row, column, event) {
      // 取消选中
      if (this.dicParam.bankId === row.bankId) {
        // this.dicParam.bankId = null
        // this.selectRow = null
        // this.$refs.typeTable.setCurrentRow()
      } else {
        this.dicParam.bankId = row.bankId
        this.selectRow = row
      }
      this.searchDic()
    },
    uploadFile (params) {
      let _this = this
      if (!this.selectRow) {
        _this.$message({
          message: '请选择题库',
          type: 'warning'
        })
        return
      }
      const file = params.file
      // const fileType = file.type
      // const isImage = fileType.indexOf('image') > -1
      // const isLt2M = file.size / 1024 / 1024 < 2
      const form = new FormData()
      form.append('file', file)
      form.append('bankId', this.selectRow.bankId)
      questionApi.import(form).then(data => {
        if (data.code === 1) {
          this.$message({
            duration: 3000,
            message: data.message,
            type: 'success'
          })
          _this.searchDic(null)
        } else {
          _this.$message.error(data.message)
          _this.formLoading = false
        }
      })
    },
    download () {
      questionApi.download()
    },
    exportQ () {
      let _this = this
      if (!this.selectRow) {
        _this.$message({
          message: '请选择题库',
          type: 'warning'
        })
        return
      }
      _this.searchDic(total => {
        if (total === 0) {
          _this.$message.warning('无数据')
        } else {
          questionApi.export(_this.dicParam)
        }
      })
    },
    checkListChange (val) {
      this.$refs.dicForm.validateField('checkList')
    },
    isTimeChange (val) {
      if (val === '0') {
        this.$refs.typeForm.validateField('time')
      }
    },
    createType () {
      this.typeForm = {
        bankId: null,
        markType: 'IMMED',
        isTime: '1'
      }
      this.dialogTypeVisible = true
      this.$refs['typeForm'].resetFields()
    },
    createDic () {
      if (!this.selectRow) {
        this.$message({
          message: '请选择题库',
          type: 'warning'
        })
        return
      }
      let _this = this
      this.checkList = []
      this.dicForm = {
        excerId: null,
        trainType: this.selectRow.trainType,
        exerciseType: this.selectRow.exerciseType,
        bankId: _this.selectRow.bankId
      }
      this.loadBank(this.dicForm.trainType, this.dicForm.exerciseType, function () {
        _this.dicForm.bankId = _this.selectRow.bankId
      })
      this.dialogDicVisible = true
      this.$refs['dicForm'].resetFields()
    },
    editType (row) {
      this.formLoading = true
      questionApi.selectBank(row.bankId).then(re => {
        this.typeForm = re.response
        this.formLoading = false
        this.dialogTypeVisible = true
        this.$refs['typeForm'].resetFields()
      })
    },
    editDic (row) {
      this.formLoading = true
      questionApi.select(row.excerId).then(re => {
        this.dicForm = re.response
        this.formLoading = false
        this.dialogDicVisible = true
        this.$refs['dicForm'].resetFields()
        this.checkList = this.dicForm.selectionCorrect.split(',')
        this.exerciseTypeChange(this.dicForm.exerciseType)
      })
    },
    deleteType (row) {
      let _this = this
      if (_this.dicTotal > 0) {
        _this.$message.error('题库有习题，无法删除')
        return
      }
      questionApi.deleteBank(row.bankId).then(re => {
        if (re.code === 1) {
          _this.search()
          _this.$message.success(re.message)
        } else {
          _this.$message.error(re.message)
        }
      })
    },
    deleteDic (row) {
      let _this = this
      questionApi.delete(row.bankId, row.excerId).then(re => {
        if (re.code === 1) {
          _this.searchDic()
          _this.$message.success(re.message)
        } else {
          _this.$message.error(re.message)
        }
      })
    },
    multiDelete () {
      let _this = this
      if (_this.multipleSelection.length === 0) {
        this.$message({
          message: '请选择习题',
          type: 'warning'
        })
        return
      }
      var bankId = ''
      var ids = []
      _this.multipleSelection.forEach((item, i) => {
        bankId = item.bankId
        ids.push(item.excerId)
      })
      questionApi.multiDelete(bankId, ids).then(re => {
        if (re.code === 1) {
          _this.searchDic()
          _this.$message.success(re.message)
        } else {
          _this.$message.error(re.message)
        }
      })
    },
    submitTypeForm () {
      let _this = this
      this.$refs.typeForm.validate((valid) => {
        if (valid) {
          this.formLoading = true
          questionApi.editBank(this.typeForm).then(data => {
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
      let _this = this
      this.dicForm.selectionCorrect = this.checkList.join(',')
      this.$refs.dicForm.validate((valid) => {
        if (valid) {
          this.formLoading = true
          questionApi.edit(this.dicForm).then(data => {
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
    domainFormatter  (row, column, cellValue, index) {
      return dicFormat(this.domainOptions, cellValue)
    },
    processFormatter  (row, column, cellValue, index) {
      return dicFormat(this.processOptions, cellValue)
    },
    tranFormatter  (row, column, cellValue, index) {
      return dicFormat(this.tranOptions, cellValue)
    },
    exerFormatter  (row, column, cellValue, index) {
      return dicFormat(this.exerOptions, cellValue)
    },
    judgeFormatter  (row, column, cellValue, index) {
      return dicFormat(this.judgeOptions, cellValue)
    },
    isTimeFormatter (row, column, cellValue, index) {
      return format(isOptions, cellValue)
    }
  }
}
</script>
