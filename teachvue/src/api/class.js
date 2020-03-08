import { post } from '@/utils/request'

export default {
  pageList: query => post('/api/admin/class/page/list', query),
  edit: query => post('/api/admin/class/edit', query),
  select: id => post('/api/admin/class/select/' + id),
  delete: id => post('/api/admin/class/delete/' + id),

  pageListStudent: query => post('/api/admin/student/page/list', query),
  editStudent: query => post('/api/admin/student/edit', query),
  selectStudent: id => post('/api/admin/student/select/' + id),
  deleteStudent: id => post('/api/admin/student/delete/' + id),
  changeStatus: id => post('/api/admin/student/changeStatus/' + id)
}
