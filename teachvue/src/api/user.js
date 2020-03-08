import { post } from '@/utils/request'

export default {
  getUserPageList: query => post('/api/admin/user/page/list', query),
  getUserEventPageList: query => post('/api/admin/user/event/page/list', query),
  createUser: query => post('/api/admin/user/edit', query),
  selectUser: id => post('/api/admin/user/select/' + id),
  getCurrentUser: () => post('/api/admin/user/current'),
  changeStatus: id => post('/api/admin/user/changeStatus/' + id),
  resetPwd: id => post('/api/admin/user/resetPwd/' + id),
  deleteUser: id => post('/api/admin/user/delete/' + id),

  getRolePageList: query => post('/api/admin/role/page/list', query),
  createRole: query => post('/api/admin/role/edit', query),
  selectRole: id => post('/api/admin/role/select/' + id),
  deleteRole: id => post('/api/admin/role/delete/' + id),

  grantRole: (id, query) => post('/api/admin/role/grantRole/' + id, query),
  grantUser: (id, query) => post('/api/admin/role/grantUser/' + id, query),
  grantMenu: (id, query) => post('/api/admin/role/grantMenu/' + id, query),
  menuTree: (id) => post('/api/admin/role/menuTree/' + id),

  getMenu: () => post('/api/admin/role/getMenu'),

  getDicType: query => post('/api/admin/dic/page/type', query),
  getDic: query => post('/api/admin/dic/page', query),
  createType: query => post('/api/admin/dic/editType', query),
  createDic: query => post('/api/admin/dic/edit', query),
  deleteType: type => post('/api/admin/dic/deleteType/' + type),
  deleteDic: id => post('/api/admin/dic/delete/' + id),
  selectDic: id => post('/api/admin/dic/select/' + id)

}
