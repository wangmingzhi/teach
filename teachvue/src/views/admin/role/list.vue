<template>
  <div class="app-container">
    <el-form :model="queryParam" ref="queryForm" :inline="true">
      <el-form-item label="名称：">
        <el-input v-model="queryParam.roleName"></el-input>
      </el-form-item>
      <el-form-item>
        <!-- <router-link :to="{path:'/admin/role/edit'}" class="link-left">
          <el-button type="primary" icon="el-icon-document-add">新增</el-button>
        </router-link> -->
        <el-button type="primary" @click="search" icon="el-icon-search">查询</el-button>
        <el-button type="primary" @click="create" icon="el-icon-document-add">新增</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="roleCode" label="角色编码"/>
      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column prop="roleRemarks" label="备注" />
      <el-table-column prop="createUser" label="录入人"/>
      <el-table-column prop="createTime" label="录入时间" width='100px'/>
      <el-table-column width="380px" label="操作" align="center">
        <template slot-scope="{row}">
          <!-- <router-link :to="{path:'/admin/role/edit', query:{roleId:row.roleId}}" class="link-left">
            <el-button size="mini" icon="el-icon-edit" type="primary" plain>编辑</el-button>
          </router-link> -->
          <el-button size="mini" @click="grantMenu(row)" class="link-left" type="primary" plain>分配资源</el-button>
          <el-button size="mini" @click="grantRole(row)" class="link-left" type="success" plain>分配用户</el-button>
          <el-button size="mini" @click="edit(row)" icon="el-icon-edit" type="primary" plain>编辑</el-button>
          <el-popconfirm title="确定删除吗？" iconColor="red" @onConfirm="deleteRole(row)">
             <el-button slot="reference" size="mini" type="danger" class="link-left" icon="el-icon-delete">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParam.pageIndex" :limit.sync="queryParam.pageSize"
                @pagination="search"/>
  <el-dialog title="角色编辑" :visible.sync="dialogDicVisible">
  <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="角色编码："  prop="roleCode" required>
       <el-input v-model="form.roleCode"></el-input>
      </el-form-item>
      <el-form-item label="角色名称："  prop="roleName" required>
        <el-input v-model="form.roleName"></el-input>
      </el-form-item>
      <el-form-item label="备注：">
        <el-input v-model="form.roleRemarks"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogDicVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitForm">确 定</el-button>
    </div>
</el-dialog>
<el-dialog title="分配用户" :visible.sync="dialogRoleVisible" @close = "roleClose">
  <el-row :gutter="20">
      <el-col :span="24">
  <el-form :model="roleParam" ref="queryForm" :inline="true">
      <el-form-item label="姓名：">
        <el-input v-model="roleParam.userName"></el-input>
      </el-form-item>
      <el-form-item label="手机号：">
        <el-input v-model="roleParam.phoneNo"></el-input>
      </el-form-item>
      <el-form-item>
        <!-- <router-link :to="{path:'/admin/role/edit'}" class="link-left">
          <el-button type="primary" icon="el-icon-document-add">新增</el-button>
        </router-link> -->
        <el-button type="primary" @click="searchRole" icon="el-icon-search">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="roleLoading" :data="roleData" ref="roleTable" @selection-change="roleSelectionChange"
     border fit highlight-current-row row-key='userId' style="width: 100%">
      <el-table-column type="selection" width="55" :reserve-selection='true'/>
      <el-table-column prop="userName" label="姓名" />
      <el-table-column prop="account" label="账号" />
      <el-table-column prop="phoneNo" label="手机号"/>
      <el-table-column prop="email" label="邮箱" :show-overflow-tooltip="true"/>
      <!-- <el-table-column label="是否禁用" prop="isEnable" width="80px;"/> -->
      <el-table-column prop="expiryDate" label="有效期" width='100px'/>
    </el-table>
    <pagination v-show="total>0" :total="roleTotal" :page.sync="roleParam.pageIndex" :limit.sync="roleParam.pageSize"
                @pagination="searchRole"/>
      </el-col></el-row>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogRoleVisible = false">取 消</el-button>
      <el-button type="primary" @click="grant">确 定</el-button>
    </div>
</el-dialog>

<el-dialog title="分配资源" :visible.sync="dialogMenuVisible" width="30%">
  <el-tree ref="menuTree" :data="menuData" show-checkbox node-key="id" :default-expand-all="true"
    :default-checked-keys="checkedMenu"
    :props="defaultProps">
  </el-tree>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogMenuVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitMenu">确 定</el-button>
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
      roleId: null,
      roleLoading: false,
      dialogRoleVisible: false,
      menuData: [],
      checkedMenu: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      dialogMenuVisible: false,
      formLabelWidth: '80px',
      rules: {
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ],
        roleCode: [
          { required: true, message: '请输入角色编码', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.search()
  },
  methods: {
    search () {
      this.listLoading = true
      userApi.getRolePageList(this.queryParam).then(data => {
        const re = data.response
        this.tableData = re.list
        this.total = re.total
        this.queryParam.pageIndex = re.pageNum
        this.listLoading = false
      })
    },
    create () {
      this.form = {
        roleId: null
      }
      this.dialogDicVisible = true
      this.$refs['form'].resetFields()
    },
    edit (row) {
      this.formLoading = true
      userApi.selectRole(row.roleId).then(re => {
        this.form = re.response
        this.formLoading = false
        this.dialogDicVisible = true
      })
    },
    deleteRole (row) {
      let _this = this
      userApi.deleteRole(row.roleId).then(re => {
        if (re.code === 1) {
          _this.search()
          _this.$message.success(re.message)
        } else {
          _this.$message.error(re.message)
        }
      })
    },
    searchRole () {
      this.roleParam.roleId = this.roleId
      this.roleParam.isEnable = '1'
      userApi.getUserPageList(this.roleParam).then(data => {
        const re = data.response
        this.roleData = re.list
        this.roleTotal = re.total
        this.roleParam.pageIndex = re.pageNum
        this.roleLoading = false
        this.$nextTick(function () {
          this.roleData.forEach((item, i) => {
            console.log(item.roleId)
            if (item.roleId) {
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
      this.roleId = row.roleId
      this.roleSelection = []
      this.searchRole()
    },
    roleClose () {
      this.$refs.roleTable.clearSelection()
    },
    grant () {
      let _this = this
      var userIds = []
      this.roleSelection.forEach((item, i) => {
        userIds.push(item.userId)
      })
      userApi.grantUser(this.roleId, userIds).then(data => {
        if (data.code === 1) {
          _this.$message.success(data.message)
          this.dialogRoleVisible = false
        } else {
          _this.$message.error(data.message)
        }
      })
    },
    grantMenu (row) {
      let _this = this
      this.roleId = row.roleId
      userApi.menuTree(this.roleId).then(data => {
        if (data.code === 1) {
          const re = data.response
          _this.menuData = re.menuData
          _this.checkedMenu = re.checkedMenu
          _this.dialogMenuVisible = true
        } else {
          _this.$message.error(data.message)
        }
      })
    },
    submitMenu () {
      let _this = this
      var menus = this.$refs.menuTree.getCheckedKeys(false)
      userApi.grantMenu(this.roleId, menus).then(data => {
        if (data.code === 1) {
          _this.$message.success(data.message)
          _this.dialogMenuVisible = false
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
          userApi.createRole(this.form).then(data => {
            if (data.code === 1) {
              _this.$message.success(data.message)
              // _this.delCurrentView(_this).then(() => {
              //   _this.$router.push('/admin/role/list')
              // })
              this.dialogDicVisible = false
              _this.formLoading = false
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
    }
  }
}
</script>
