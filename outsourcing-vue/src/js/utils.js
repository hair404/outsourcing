import Axios from 'axios'

/* eslint-disable */

const type = ['企业', '工作室', '管理员', '游客']

const ctg = [
  {
    name: '全部'
  },
  {
    name: '网站开发',
    subctg: ['前端开发', '网站维护']
  },
  {
    name: '移动应用开发',
    subctg: ['安卓APP', '苹果APP']
  },
  {
    name: 'H5开发',
    subctg: ['H5模板', 'H5定制']
  },
  {
    name: 'UI设计',
    subctg: ['网站UI', '移动UI']
  },
  { name: '测试运维' },
  { name: '云服务' },
  { name: 'IT综合服务' }
]

function refreshCtg() {
  Axios.post('getCtg')
    .then(response => {
      if (response.data) ctg.response.data
    })
    .catch(error => {
      console.log(error)
    })
}

function toFormData(a) {
  var fd = []
  for (var k in a) {
    fd.push(k + '=' + a[k])
  }
  return fd.join('&')
}

function getReal(a, d, callback) {
  if (!a) return d
  if (callback) return callback(a)
  return a
}

function getTextValue(a, b, callback) {
  if (!b) b = []
  a.forEach((item, i) => {
    b.push(callback ? callback(item, i) : { text: item, value: i })
  })
  return b
}

Array.prototype.contain = function(val) {
  for (var i = 0; i < this.length; i++) {
    if (JSON.stringify(this[i]) === JSON.stringify(val)) {
      return true
    }
  }
  return false
}

export default {
  type,
  ctg,
  refreshCtg,
  toFormData,
  getReal,
  getTextValue
}
