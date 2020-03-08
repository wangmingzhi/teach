import { post, download } from '@/utils/request'

export default {
  pageList: query => post('/api/admin/exercise/page/list', query),
  edit: query => post('/api/admin/exercise/edit', query),
  select: id => post('/api/admin/exercise/select/' + id),
  delete: (bankId, id) => post('/api/admin/exercise/delete/' + bankId + '/' + id),
  multiDelete: (bankId, ids) => post('/api/admin/exercise/multiDelete/' + bankId, ids),

  download: () => download('/api/admin/exercise/download'),
  export: query => download('/api/admin/exercise/export', query),
  import: query => post('/api/admin/exercise/import', query),

  pageListBank: query => post('/api/admin/bank/page/list', query),
  editBank: query => post('/api/admin/bank/edit', query),
  selectBank: id => post('/api/admin/bank/select/' + id),
  deleteBank: id => post('/api/admin/bank/delete/' + id)

}
