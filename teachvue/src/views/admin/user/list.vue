<template>
  <div class="app-container">
    <el-form :model="queryParam" ref="queryForm" :inline="true">
      <!-- <el-form-item label="地区：">
        <el-select v-model="queryParam.areaType" clearable>
          <el-option v-for="item in areaTypeOptions" :key="item.dictionaryCode" :label="item.dictionaryName" :value="item.dictionaryCode"></el-option>
        </el-select>
      </el-form-item> -->
      <el-form-item label="姓名：">
        <el-input v-model="queryParam.userName"></el-input>
      </el-form-item>
      <el-form-item label="手机号：">
        <el-input v-model="queryParam.phoneNo"></el-input>
      </el-form-item>
      <el-form-item label="邮箱：">
        <el-input v-model="queryParam.email"></el-input>
      </el-form-item>
      <el-form-item label="有效期：">
         <el-date-picker v-model="queryParam.expiryDate" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search" icon="el-icon-search">查询</el-button>
        <el-button type="primary" @click="create" icon="el-icon-document-add">新增</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <!-- <el-table-column prop="areaType" label="地区" width='80px'/> -->
      <el-table-column prop="userName" label="姓名" />
      <el-table-column prop="account" label="账号" />
      <el-table-column prop="sex" label="性别" :formatter="sexFormatter" width='50px'/>
      <el-table-column prop="phoneNo" label="手机号"/>
      <el-table-column prop="email" label="邮箱" :show-overflow-tooltip="true"/>
      <!-- <el-table-column label="是否禁用" prop="isEnable" width="80px;">
      </el-table-column> -->
      <el-table-column prop="expiryDate" label="有效期" width='100px'/>
      <el-table-column prop="createUser" label="录入人"/>
      <el-table-column prop="createTime" label="录入时间" width='100px'/>
      <el-table-column width="340px" label="操作" align="center">
        <template slot-scope="{row}">
          <el-button size="mini"   @click="changeStatus(row)" class="link-left" plain :type="row.isEnable == '1' ? 'info' : 'success'">
            {{ row.isEnable == '1' ? '禁用' : '启用' }}
          </el-button>
          <el-button size="mini" @click="resetPwd(row)" class="link-left" type="warning" plain>重置</el-button>
          <el-button size="mini" @click="grantRole(row)" class="link-left" type="success" plain>授权</el-button>
          <!-- <router-link :to="{path:'/admin/user/edit', query:{userId:row.userId}}" class="link-left">
            <el-button size="mini" icon="el-icon-edit" type="primary" plain></el-button>
          </router-link> -->
          <el-button size="mini" @click="edit(row)" icon="el-icon-edit" type="primary" plain></el-button>
          <el-popconfirm title="确定删除吗？" iconColor="red" @onConfirm="deleteUser(row)">
             <el-button slot="reference" size="mini" type="danger" class="link-left" icon="el-icon-delete"></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParam.pageIndex" :limit.sync="queryParam.pageSize"
                @pagination="search"/>

  <el-dialog title="用户编辑" :visible.sync="dialogDicVisible">
  <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
    <el-row :gutter="10">
      <el-col :span="12">
        <el-form-item label="姓名："  prop="userName" required :label-width="formLabelWidth">
          <el-input v-model="form.userName"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="性别：" :label-width="formLabelWidth">
          <el-select v-model="form.sex" placeholder="性别" clearable >
            <el-option v-for="item in sexEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row :gutter="10">
      <el-col :span="12">
        <el-form-item label="账号：" prop="account" required :label-width="formLabelWidth">
          <el-input v-model="form.account"></el-input>
        </el-form-item>
     </el-col>
      <el-col :span="12">
        <el-form-item label="手机号：" :label-width="formLabelWidth" prop="phoneNo">
          <el-input v-model="form.phoneNo"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
      <el-row :gutter="10">
      <el-col :span="12">
      <el-form-item label="启用：" :label-width="formLabelWidth">
        <el-select v-model="form.isEnable">
          <el-option v-for="item in isOptions" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      </el-col>
      <el-col :span="12">
          <el-form-item label="有效期：" :label-width="formLabelWidth">
            <el-date-picker v-model="form.expiryDate" type="date" placeholder="选择日期" value-format="yyyy-MM-dd"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10">
     <!-- <el-col :span="12">
        <el-form-item label="地区："  prop="areaType" required :label-width="formLabelWidth">
          <el-select v-model="form.areaType" clearable>
            <el-option v-for="item in areaTypeOptions" :key="item.key" :value="item.key" :label="item.value"></el-option>
          </el-select>
        </el-form-item>
      </el-col> -->
      <el-col :span="12">
      <el-form-item label="邮箱：" :label-width="formLabelWidth" prop="email">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      </el-col>
    </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogDicVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitForm">确 定</el-button>
    </div>
</el-dialog>

<el-dialog title="分配角色" :visible.sync="dialogRoleVisible" @close = "roleClose">
  <el-row :gutter="20">
      <el-col :span="24">
  <el-form :model="roleParam" ref="queryForm" :inline="true">
      <el-form-item label="名称：">
        <el-input v-model="roleParam.roleName"></el-input>
      </el-form-item>
      <el-form-item>
        <!-- <router-link :to="{path:'/admin/role/edit'}" class="link-left">
          <el-button type="primary" icon="el-icon-document-add">新增</el-button>
        </router-link> -->
        <el-button type="primary" @click="searchRole" icon="el-icon-search">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="roleLoading" :data="roleData" ref="roleTable" @selection-change="roleSelectionChange"
     border fit highlight-current-row row-key='roleId' style="width: 100%">
      <el-table-column type="selection" width="55" :reserve-selection='true'/>
      <el-table-column prop="roleCode" label="角色编码"/>
      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column prop="roleRemarks" label="备注" />
    </el-table>
    <pagination v-show="total>0" :total="roleTotal" :page.sync="roleParam.pageIndex" :limit.sync="roleParam.pageSize"
                @pagination="searchRole"/>
      </el-col></el-row>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogRoleVisible = false">取 消</el-button>
      <el-button type="primary" @click="grant">确 定</el-button>
    </div>
</el-dialog>
  </div>

</template>

<script>
import { initSelect, format, isOptions, sexEnum } from '@/utils/util'
import {validPhone, validateEmail} from '@/utils/validate'
import Pagination from '@/components/Pagination'
import userApi from '@/api/user'

export default {
  components: { Pagination },
  data () {
    return {
      queryParam: {
        userName: '',
        role: 3,
        pageIndex: 1,
        pageSize: 10
      },
      form: {},
      listLoading: true,
      tableData: [],
      areaTypeOptions: [],
      total: 0,
      formLoading: false,
      dialogDicVisible: false,
      roleParam: {
        pageIndex: 1,
        pageSize: 5
      },
      roleData: [],
      roleSelection: [],
      roleTotal: 0,
      userId: null,
      roleLoading: false,
      dialogRoleVisible: false,
      formLabelWidth: '80px',
      sexEnum: sexEnum,
      isOptions: isOptions,
      rules: {
        userName: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        account: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        phoneNo: [
          { required: false, validator: validPhone, trigger: 'blur' }
        ],
        email: [
          { required: false, validator: validateEmail, trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    initSelect('AREA_TYPE_CODE', data => {
      this.areaTypeOptions = data
    })
    this.search()
  },
  methods: {
    search () {
      this.listLoading = true
      userApi.getUserPageList(this.queryParam).then(data => {
        const re = data.response
        this.tableData = re.list
        this.total = re.total
        this.queryParam.pageIndex = re.pageNum
        this.listLoading = false
      })
    },
    create () {
      this.form = {
        userId: null,
        isEnable: '1'
      }
      this.dialogDicVisible = true
      this.$refs['form'].resetFields()
    },
    edit (row) {
      this.formLoading = true
      userApi.selectUser(row.userId).then(re => {
        this.form = re.response
        this.formLoading = false
        this.dialogDicVisible = true
      })
    },
    changeStatus (row) {
      let _this = this
      userApi.changeStatus(row.userId).then(re => {
        if (re.code === 1) {
          row.isEnable = re.response
          _this.$message.success(re.message)
        } else {
          _this.$message.error(re.message)
        }
      })
    },
    deleteUser (row) {
      let _this = this
      userApi.deleteUser(row.userId).then(re => {
        if (re.code === 1) {
          _this.search()
          _this.$message.success(re.message)
        } else {
          _this.$message.error(re.message)
        }
      })
    },
    resetPwd (row) {
      let _this = this
      userApi.resetPwd(row.userId).then(re => {
        if (re.code === 1) {
          _this.search()
          _this.$message.success(re.message)
        } else {
          _this.$message.error(re.message)
        }
      })
    },
    searchRole () {
      this.roleParam.userId = this.userId
      userApi.getRolePageList(this.roleParam).then(data => {
        const re = data.response
        this.roleData = re.list
        this.roleTotal = re.total
        this.roleParam.pageIndex = re.pageNum
        this.roleLoading = false
        this.$nextTick(function () {
          this.roleData.forEach((item, i) => {
            if (item.userId) {
              this.$refs.roleTable.toggleRowSelection(item, true)
            }
          })
        })
      })
    },
    roleSelectionChange (val) {
      this.roleSelection = val
    },
    grantRole (row) {
      this.roleParam = {
        pageIndex: 1,
        pageSize: 5
      }
      this.dialogRoleVisible = true
      this.userId = row.userId
      this.roleSelection = []
      this.searchRole()
    },
    roleClose () {
      this.$refs.roleTable.clearSelection()
    },
    grant () {
      let _this = this
      var roleIds = []
      this.roleSelection.forEach((item, i) => {
        roleIds.push(item.roleId)
      })
      userApi.grantRole(this.userId, roleIds).then(data => {
        if (data.code === 1) {
          _this.$message.success(data.message)
          this.dialogRoleVisible = false
        } else {
          _this.$message.error(data.message)
        }
      })
    },
    submitForm () {
      let _this = this
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.formLoading = true
          userApi.createUser(this.form).then(data => {
            if (data.code === 1) {
              _this.$message.success(data.message)
              this.formLoading = false
              this.dialogDicVisible = false
              this.search()
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
    sexFormatter  (row, column, cellValue, index) {
      return format(sexEnum, cellValue)
    }
  }
}
</script>
