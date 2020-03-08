webpackJsonp([5],{pDOH:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var o=a("1onU"),l=a("vMJZ"),r={components:{Pagination:o.a},data:function(){return{queryParam:{userName:"",role:3,pageIndex:1,pageSize:10},form:{},listLoading:!0,tableData:[],areaTypeOptions:[],total:0,formLoading:!1,dialogDicVisible:!1,roleParam:{pageIndex:1,pageSize:5},roleData:[],roleSelection:[],roleTotal:0,roleId:null,roleLoading:!1,dialogRoleVisible:!1,menuData:[],checkedMenu:[],defaultProps:{children:"children",label:"label"},dialogMenuVisible:!1,formLabelWidth:"80px",rules:{roleName:[{required:!0,message:"请输入角色名称",trigger:"blur"}],roleCode:[{required:!0,message:"请输入角色编码",trigger:"blur"}]}}},created:function(){this.search()},methods:{search:function(){var e=this;this.listLoading=!0,l.a.getRolePageList(this.queryParam).then(function(t){var a=t.response;e.tableData=a.list,e.total=a.total,e.queryParam.pageIndex=a.pageNum,e.listLoading=!1})},create:function(){this.form={roleId:null},this.dialogDicVisible=!0,this.$refs.form.resetFields()},edit:function(e){var t=this;this.formLoading=!0,l.a.selectRole(e.roleId).then(function(e){t.form=e.response,t.formLoading=!1,t.dialogDicVisible=!0})},deleteRole:function(e){var t=this;l.a.deleteRole(e.roleId).then(function(e){1===e.code?(t.search(),t.$message.success(e.message)):t.$message.error(e.message)})},searchRole:function(){var e=this;this.roleParam.roleId=this.roleId,this.roleParam.isEnable="1",l.a.getUserPageList(this.roleParam).then(function(t){var a=t.response;e.roleData=a.list,e.roleTotal=a.total,e.roleParam.pageIndex=a.pageNum,e.roleLoading=!1,e.$nextTick(function(){var e=this;this.roleData.forEach(function(t,a){console.log(t.roleId),t.roleId&&e.$refs.roleTable.toggleRowSelection(t,!0)})})})},roleSelectionChange:function(e){this.roleSelection=e},grantRole:function(e){this.roleParam={pageIndex:1,pageSize:5},this.dialogRoleVisible=!0,this.roleId=e.roleId,this.roleSelection=[],this.searchRole()},roleClose:function(){this.$refs.roleTable.clearSelection()},grant:function(){var e=this,t=this,a=[];this.roleSelection.forEach(function(e,t){a.push(e.userId)}),l.a.grantUser(this.roleId,a).then(function(a){1===a.code?(t.$message.success(a.message),e.dialogRoleVisible=!1):t.$message.error(a.message)})},grantMenu:function(e){var t=this;this.roleId=e.roleId,l.a.menuTree(this.roleId).then(function(e){if(1===e.code){var a=e.response;t.menuData=a.menuData,t.checkedMenu=a.checkedMenu,t.dialogMenuVisible=!0}else t.$message.error(e.message)})},submitMenu:function(){var e=this,t=this.$refs.menuTree.getCheckedKeys(!1);l.a.grantMenu(this.roleId,t).then(function(t){1===t.code?(e.$message.success(t.message),e.dialogMenuVisible=!1):e.$message.error(t.message)})},submitForm:function(){var e=this,t=this;this.$refs.form.validate(function(a){if(!a)return!1;e.formLoading=!0,l.a.createRole(e.form).then(function(a){1===a.code?(t.$message.success(a.message),e.dialogDicVisible=!1,t.formLoading=!1,e.search()):(t.$message.error(a.message),t.formLoading=!1)}).catch(function(e){t.formLoading=!1})})}}},i={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container"},[a("el-form",{ref:"queryForm",attrs:{model:e.queryParam,inline:!0}},[a("el-form-item",{attrs:{label:"名称："}},[a("el-input",{model:{value:e.queryParam.roleName,callback:function(t){e.$set(e.queryParam,"roleName",t)},expression:"queryParam.roleName"}})],1),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:e.search}},[e._v("查询")]),e._v(" "),a("el-button",{attrs:{type:"primary",icon:"el-icon-document-add"},on:{click:e.create}},[e._v("新增")])],1)],1),e._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],staticStyle:{width:"100%"},attrs:{data:e.tableData,border:"",fit:"","highlight-current-row":""}},[a("el-table-column",{attrs:{prop:"roleCode",label:"角色编码"}}),e._v(" "),a("el-table-column",{attrs:{prop:"roleName",label:"角色名称"}}),e._v(" "),a("el-table-column",{attrs:{prop:"roleRemarks",label:"备注"}}),e._v(" "),a("el-table-column",{attrs:{prop:"createUser",label:"录入人"}}),e._v(" "),a("el-table-column",{attrs:{prop:"createTime",label:"录入时间",width:"100px"}}),e._v(" "),a("el-table-column",{attrs:{width:"380px",label:"操作",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var o=t.row;return[a("el-button",{staticClass:"link-left",attrs:{size:"mini",type:"primary",plain:""},on:{click:function(t){return e.grantMenu(o)}}},[e._v("分配资源")]),e._v(" "),a("el-button",{staticClass:"link-left",attrs:{size:"mini",type:"success",plain:""},on:{click:function(t){return e.grantRole(o)}}},[e._v("分配用户")]),e._v(" "),a("el-button",{attrs:{size:"mini",icon:"el-icon-edit",type:"primary",plain:""},on:{click:function(t){return e.edit(o)}}},[e._v("编辑")]),e._v(" "),a("el-popconfirm",{attrs:{title:"确定删除吗？",iconColor:"red"},on:{onConfirm:function(t){return e.deleteRole(o)}}},[a("el-button",{staticClass:"link-left",attrs:{slot:"reference",size:"mini",type:"danger",icon:"el-icon-delete"},slot:"reference"},[e._v("删除")])],1)]}}])})],1),e._v(" "),a("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.queryParam.pageIndex,limit:e.queryParam.pageSize},on:{"update:page":function(t){return e.$set(e.queryParam,"pageIndex",t)},"update:limit":function(t){return e.$set(e.queryParam,"pageSize",t)},pagination:e.search}}),e._v(" "),a("el-dialog",{attrs:{title:"角色编辑",visible:e.dialogDicVisible},on:{"update:visible":function(t){e.dialogDicVisible=t}}},[a("el-form",{directives:[{name:"loading",rawName:"v-loading",value:e.formLoading,expression:"formLoading"}],ref:"form",attrs:{model:e.form,"label-width":"100px",rules:e.rules}},[a("el-form-item",{attrs:{label:"角色编码：",prop:"roleCode",required:""}},[a("el-input",{model:{value:e.form.roleCode,callback:function(t){e.$set(e.form,"roleCode",t)},expression:"form.roleCode"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"角色名称：",prop:"roleName",required:""}},[a("el-input",{model:{value:e.form.roleName,callback:function(t){e.$set(e.form,"roleName",t)},expression:"form.roleName"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"备注："}},[a("el-input",{model:{value:e.form.roleRemarks,callback:function(t){e.$set(e.form,"roleRemarks",t)},expression:"form.roleRemarks"}})],1)],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.dialogDicVisible=!1}}},[e._v("取 消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:e.submitForm}},[e._v("确 定")])],1)],1),e._v(" "),a("el-dialog",{attrs:{title:"分配用户",visible:e.dialogRoleVisible},on:{"update:visible":function(t){e.dialogRoleVisible=t},close:e.roleClose}},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:24}},[a("el-form",{ref:"queryForm",attrs:{model:e.roleParam,inline:!0}},[a("el-form-item",{attrs:{label:"姓名："}},[a("el-input",{model:{value:e.roleParam.userName,callback:function(t){e.$set(e.roleParam,"userName",t)},expression:"roleParam.userName"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"手机号："}},[a("el-input",{model:{value:e.roleParam.phoneNo,callback:function(t){e.$set(e.roleParam,"phoneNo",t)},expression:"roleParam.phoneNo"}})],1),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:e.searchRole}},[e._v("查询")])],1)],1),e._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.roleLoading,expression:"roleLoading"}],ref:"roleTable",staticStyle:{width:"100%"},attrs:{data:e.roleData,border:"",fit:"","highlight-current-row":"","row-key":"userId"},on:{"selection-change":e.roleSelectionChange}},[a("el-table-column",{attrs:{type:"selection",width:"55","reserve-selection":!0}}),e._v(" "),a("el-table-column",{attrs:{prop:"userName",label:"姓名"}}),e._v(" "),a("el-table-column",{attrs:{prop:"account",label:"账号"}}),e._v(" "),a("el-table-column",{attrs:{prop:"phoneNo",label:"手机号"}}),e._v(" "),a("el-table-column",{attrs:{prop:"email",label:"邮箱","show-overflow-tooltip":!0}}),e._v(" "),a("el-table-column",{attrs:{prop:"expiryDate",label:"有效期",width:"100px"}})],1),e._v(" "),a("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.roleTotal,page:e.roleParam.pageIndex,limit:e.roleParam.pageSize},on:{"update:page":function(t){return e.$set(e.roleParam,"pageIndex",t)},"update:limit":function(t){return e.$set(e.roleParam,"pageSize",t)},pagination:e.searchRole}})],1)],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.dialogRoleVisible=!1}}},[e._v("取 消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:e.grant}},[e._v("确 定")])],1)],1),e._v(" "),a("el-dialog",{attrs:{title:"分配资源",visible:e.dialogMenuVisible,width:"30%"},on:{"update:visible":function(t){e.dialogMenuVisible=t}}},[a("el-tree",{ref:"menuTree",attrs:{data:e.menuData,"show-checkbox":"","node-key":"id","default-expand-all":!0,"default-checked-keys":e.checkedMenu,props:e.defaultProps}}),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.dialogMenuVisible=!1}}},[e._v("取 消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:e.submitMenu}},[e._v("确 定")])],1)],1)],1)},staticRenderFns:[]},n=a("VU/8")(r,i,!1,null,null,null);t.default=n.exports}});
//# sourceMappingURL=5.33f77750024a586cf533.js.map