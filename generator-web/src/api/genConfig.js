import request from '@/utils/request'

export function get () {
  return request({
    url: 'api/generator/getConfig',
    method: 'get'
  })
}

export function update (data) {
  return request({
    url: 'api/generator/update',
    data,
    method: 'put'
  })
}

export function getColums (tableName) {
  return request({
    url: 'api/generator/getColums?tableName=' + tableName,
    method: 'get'
  })
}

export function generator (data, tableName) {
  return request({
    url: 'api/generator/generator?tableName=' + tableName,
    data,
    method: 'post'
  })
}
