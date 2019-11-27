<template>
  <div class="wrapper">
    <v-dialog
      max-width="400"
      v-model="dialog.open"
      :persistent="dialog.state===0"
    >
      <v-card v-if="dialog.state===0">
        <v-card-title>管理员登录</v-card-title>
        <v-card-text>
          <v-text-field
            v-model="man.name"
            filled
            label="工号"
            :rules="[v => !!v || '必需项',]"
            required
          ></v-text-field>
          <v-text-field
            v-model="man.password"
            filled
            label="密码"
            type="password"
            :rules="[v => !!v || '必需项',]"
            required
          ></v-text-field>
          <v-select
            :items="[{ text: '管理员', value: 2 },{ text: '专家', value: 3 }]"
            v-model="man.type"
            label="点击以选择"
            filled
          />
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="login()"
          >登录</v-btn>
        </v-card-actions>
      </v-card>

      <v-card v-if="dialog.state===1">
        <v-card-title>注册活动</v-card-title>
        <v-card-text>
          <v-text-field
            v-model="activity.name"
            filled
            label="活动名"
            :rules="[v => !!v || '必需项',]"
            required
          ></v-text-field>
          <v-file-input
            label="图片"
            v-model="activity.img"
            accept="image/*"
            filled
          ></v-file-input>
          <v-text-field
            v-model="activity.url"
            filled
            label="url"
            type="text"
            :rules="[v => !!v || '必需项',]"
            required
          ></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="atvt_register()"
          >注册</v-btn>
        </v-card-actions>
      </v-card>

      <v-card v-if="dialog.state===2">
        <v-card-title>注册管理员</v-card-title>
        <v-card-text>
          <v-form ref="form">
            <v-text-field
              v-model="register.name"
              type="text"
              label="名字"
              :rules="[v => !!v || '必需项']"
              required
              filled
            />
            <v-text-field
              v-model="register.password"
              type="text"
              label="密码"
              :rules="[v => !!v || '必需项']"
              required
              filled
            />
            <v-text-field
              v-model="register.repassword"
              type="text"
              label="重复密码"
              :rules="[v => v===register.password || '密码不一致']"
              required
              filled
            />
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            outlined
            color="warning"
            class="mr-4"
          >注册</v-btn>
        </v-card-actions>
      </v-card>

      <v-card v-if="dialog.state===3">
        <v-card-title>添加分类</v-card-title>
        <v-card-text>
          <v-text-field
            v-model="ctgName"
            filled
            label="名称"
            :rules="[v => !!v || '必需项',]"
            required
          ></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="(addsubctg===true && ctg[selectCtg]?ctg[selectCtg].subctg.push(ctgName):ctg.push({name:ctgName})) && (dialog.open=false)"
          >添加</v-btn>
        </v-card-actions>
      </v-card>

      <v-card v-if="dialog.state===4">
        <v-card-title>验证图片</v-card-title>
        <v-card-text>
          <v-img :src="utils.baseURL+clickedrow.img"></v-img>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="action(1,[clickedrow,1])"
          >通过</v-btn>
          <v-btn
            color="primary"
            text
            @click="action(0,[clickedrow,1])"
          >不通过</v-btn>
          <v-btn
            color="primary"
            text
            @click="$router.push({name:'display',params:{id:clickedrow.id}})"
          >查看信息</v-btn>
        </v-card-actions>
      </v-card>

    </v-dialog>

    <ConfirmDialog :CDialog="CDialog"></ConfirmDialog>

    <v-card height="fit-content">
      <v-row>
        <v-col
          cols="3"
          class="d-none d-md-flex"
        >
          <v-list
            width="100%"
            shaped
          >
            <v-list-item>
              <v-list-item-avatar>
                <v-avatar color="primary">
                  <v-icon dark>mdi-account-circle</v-icon>
                </v-avatar>
              </v-list-item-avatar>
              <v-list-item-title>{{info.id}}</v-list-item-title>
            </v-list-item>

            <v-subheader>管理中心</v-subheader>
            <v-list-item-group
              v-model="state"
              color="primary"
            >
              <v-list-item
                v-for="(item, i) in tabs"
                :key="i"
              >
                <v-list-item-icon>
                  <v-icon v-text="item.icon"></v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title v-text="item.name"></v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-col>
        <v-col>
          <template v-if="info.type === 2 && state === 0">
            <v-row class="ml-2">
              <v-col>
                <v-select
                  v-model="select"
                  :items="utils.getTextValue(types)"
                  width="100%"
                  color="primary"
                  label="类型"
                  outlined
                ></v-select>
              </v-col>
              <v-col>
                <v-select
                  v-model="selectVerified"
                  :items="utils.getTextValue(['全部','等待认证为用户','等待认证为学生'])"
                  width="100%"
                  color="primary"
                  label="类型"
                  outlined
                ></v-select>
              </v-col>
              <v-col>
                <v-text-field
                  v-model="text"
                  filled
                  label="关键字"
                  required
                ></v-text-field>
              </v-col>
              <v-col>
                <v-btn
                  color="primary"
                  class="mt-4"
                  @click="lookup(state,dataUser)"
                  outlined
                >查询</v-btn>
              </v-col>
              <v-spacer class="d-none d-md-inline-block"></v-spacer>
              <v-col class="d-none d-md-inline-block">
                <v-btn
                  class="mt-4"
                  outlined
                  color="primary"
                  @click="(dialog.open = true)&&(dialog.state = 2)"
                >注册管理员</v-btn>
              </v-col>
            </v-row>
            <Table
              ref="table"
              :data="dataUser"
              :headers="headers"
              :callback="(item)=>{(CDialog.callback = (()=>{action(0,item)})) && (CDialog.open = true)}"
              itemkey="id"
              issort
              callbackIcon="mdi-trash-can-outline"
              :itemperPage="10"
              :rowCallback="(item)=>{$router.push({name:'display',params:{id:item.id}})}"
            ></Table>
            <v-fab-transition>
              <v-btn
                class="d-block d-md-none"
                style="bottom: 100px"
                color="pink"
                dark
                fixed
                bottom
                right
                fab
                @click="(dialog.open = true)&&(dialog.state = 2)"
              >
                <v-icon>mdi-plus</v-icon>
              </v-btn>
            </v-fab-transition>
          </template>

          <template v-if="info.type === 3 && state === 0">
            <Table
              :data="dataCom"
              :headers="headersComplain"
              :callback="[(item)=>{(CDialog.callback = (()=>{action(5,[item,1])})) && (CDialog.open = true)},(item)=>{(CDialog.callback = (()=>{action(5,[item,0])})) && (CDialog.open = true)}]"
              itemkey="id"
              issort
              :callbackIcon="['mdi-check','mdi-close']"
              :itemperPage="10"
              :rowCallback="(item)=>{item.img?clickedrow = item&&(dialog.open = true)&&(dialog.state = 4):$router.push({name:'detail',params:{id:item.prjid}})}"
              :isCallback="item=>{return item.state === '未通过'}"
            ></Table>
          </template>

          <template v-if="state === 1">
            <v-row class="ml-2">
              <v-col>
                <v-select
                  v-model="select"
                  :items="utils.getTextValue(['项目','工作室'])"
                  width="100%"
                  color="primary"
                  label="类型"
                  outlined
                ></v-select>
              </v-col>
              <v-col>
                <v-text-field
                  v-model="text"
                  filled
                  label="关键字"
                  required
                ></v-text-field>
              </v-col>
              <v-col>
                <v-btn
                  color="primary"
                  class="mt-4"
                  @click="lookup(state,dataAD)"
                  outlined
                >查询</v-btn>
              </v-col>
            </v-row>
            <Table
              :data="dataAD"
              :headers="headersAD"
              :callback="[(item)=>{(CDialog.callback = (()=>{action(1,[item,1])})) && (CDialog.open = true)},(item)=>{(CDialog.callback = (()=>{action(1,[item,0])})) && (CDialog.open = true)}]"
              itemkey="id"
              issort
              :callbackIcon="['mdi-check','mdi-close']"
              :rowCallback="(item)=>{$router.push(item.type === 0?{name:'display',params:{id:item.solr_id}}:{name:'detail',params:{id:item.solr_id}})}"
              :itemperPage="10"
              :isCallback="item=>{return item.state === '未通过'}"
            ></Table>
          </template>

          <template v-if="state === 2">
            <v-row class="ml-2">
              <v-col>
                <v-select
                  v-model="select"
                  :items="utils.getTextValue(moneyType)"
                  width="100%"
                  color="primary"
                  label="类型"
                  outlined
                ></v-select>
              </v-col>
              <v-col>
                <v-text-field
                  v-model="text"
                  filled
                  label="关键字"
                  required
                ></v-text-field>
              </v-col>
              <v-col>
                <v-btn
                  color="primary"
                  class="mt-4"
                  @click="lookup(state,dataMoney)"
                  outlined
                >查询</v-btn>
              </v-col>
            </v-row>
            <Table
              :data="dataMoney"
              :headers="headersMoney"
              :callback="(item)=>{(CDialog.callback = (()=>{action(2,item)})) && (CDialog.open = true)}"
              itemkey="id"
              issort
              callbackIcon="mdi-currency-usd-off"
              :itemperPage="10"
            ></Table>
          </template>

          <template v-if="state === 3">
            <v-btn
              color="primary"
              class="mt-4 ml-2"
              @click="(dialog.open = true) && (dialog.state = 1)"
              outlined
            >添加活动</v-btn>
            <v-btn
              color="primary"
              class="mt-4 ml-2"
              @click="activityLookup()"
              outlined
            >刷新</v-btn>
            <Table
              :data="dataActivity"
              :headers="headersActivity"
              :callback="(item=>{action(3,item)})"
              itemkey="id"
              issort
              callbackIcon="mdi-trash-can-outline"
              :rowCallback="(item)=>{window.open(item.url)}"
              :itemperPage="10"
            ></Table>
          </template>

          <template v-if="state === 4">
            <div class="mx-auto wrapper">
              <v-row>
                <v-col>
                  <v-card style="overflow: auto;max-height: 500px">
                    <v-list>
                      <v-list-item-group
                        v-model="selectCtg"
                        color="primary"
                      >
                        <v-list-item
                          v-for="(item,i) in ctg"
                          :key="i"
                        >
                          <v-list-item-content>
                            <v-list-item-title>{{item.name}}</v-list-item-title>
                          </v-list-item-content>
                          <v-list-item-icon @click="ctg.splice(i,1)">
                            <v-icon>mdi-trash-can-outline</v-icon>
                          </v-list-item-icon>
                        </v-list-item>
                        <v-list-item @click="(addsubctg = false) && (dialog.open = true) && (dialog.state = 3)">
                          <v-icon style="position: relative;left: calc(50% - 12px)">mdi-plus</v-icon>
                        </v-list-item>
                      </v-list-item-group>
                    </v-list>
                  </v-card>
                </v-col>
                <v-col>
                  <v-card v-if="selectCtg !== undefined">
                    <v-list>
                      <v-list-item-group color="primary">
                        <v-list-item
                          v-for="(item,i) in ctg[selectCtg].subctg"
                          :key="i"
                        >
                          <v-list-item-content>
                            <v-list-item-title>{{item}}</v-list-item-title>
                          </v-list-item-content>
                          <v-list-item-icon @click="ctg[selectCtg].subctg.splice(i,1)">
                            <v-icon>mdi-trash-can-outline</v-icon>
                          </v-list-item-icon>
                        </v-list-item>
                        <v-list-item @click="(addsubctg = true) && (dialog.open = true) && (dialog.state = 3)">
                          <v-icon style="position: relative;left: calc(50% - 12px)">mdi-plus</v-icon>
                        </v-list-item>
                      </v-list-item-group>
                    </v-list>
                  </v-card>
                </v-col>
              </v-row>
              <v-btn
                outlined
                color="primary"
                @click="action(4)"
              >提交</v-btn>
            </div>
          </template>
        </v-col>
      </v-row>
    </v-card>

    <div style="height:50px"></div>

    <v-bottom-navigation
      v-if="!(info.type === 3 && state === 0)"
      class="d-flex d-md-none"
      color="primary"
      v-model="state"
      shift
      absolute
      style="position: fixed;"
    >
      <v-btn
        v-for="(item,i) in tabs"
        :key="i"
      >
        <span>{{item.name}}</span>
        <v-icon>{{item.icon}}</v-icon>
      </v-btn>
    </v-bottom-navigation>
  </div>
</template>

<script>
import Axios from 'axios'
import qs from 'qs'
import utils from '../js/utils'
import Table from '../components/Table'
import ConfirmDialog from '../components/ConfirmDialog'

export default {
  props: {
    info: Object,
    infoLoaded: Boolean,
    snackbar: Object
  },
  components: {
    Table,
    ConfirmDialog
  },
  data () {
    return {
      utils: utils,
      window: window,
      man: {
        name: '',
        password: '',
        type: 2
      },
      dialog: {
        open: false,
        state: 0
      },
      CDialog: {
        open: false,
        callback: null
      },
      state: 0,
      tabs: [{ name: '用户管理', icon: 'mdi-account-badge-outline' },
        { name: '广告管理', icon: 'mdi-post-outline' },
        { name: '资金流', icon: 'mdi-cash-usd-outline' },
        { name: '活动', icon: 'mdi-ticket' },
        { name: '分类管理', icon: 'mdi-shape-outline' }],
      select: 0,
      selectVerified: 0,
      clickedrow: 0,
      text: '',
      types: ['全部', '企业', '工作室'],
      moneyType: ['全部', '首付款', '押金', '阶段款', '广告投放退款', '延误罚款', '赔款'],
      headers: [{ text: '用户id', value: 'id' }, { text: '用户名', value: 'username' }, { text: '类型', value: 'type' }],
      headersAD: [{ text: '广告id', value: 'id' }, { text: '名字', value: 'name' }, { text: '所属', value: 'belong' }, { text: '金额', value: 'money' }, { text: '类型', value: 'type' }, { text: '状态', value: 'state' }],
      ADstate: ['未通过', '通过'],
      headersMoney: [{ text: 'id', value: 'id' }, { text: '金额', value: 'money' }, { text: '用途', value: 'usage' }, { text: '付款方', value: 'from' }, { text: '收钱方', value: 'to' }],
      headersActivity: [{ text: 'id', value: 'id' }, { text: '活动名', value: 'name' }, { text: '地址', value: 'url' }],
      activity: {
        name: '',
        img: null,
        url: ''
      },
      register: {
        name: '',
        password: '',
        repassword: ''
      },
      headersComplain: [{ text: 'id', value: 'id' }, { text: '项目名称', value: 'name' }, { text: '发起方', value: 'from' }, { text: '理由', value: 'from' }, { text: '金额(百分比)', value: 'money' }, { text: '状态', value: 'state' }],
      dataUser: [],
      dataAD: [],
      dataMoney: [],
      dataActivity: [],
      dataCom: [],
      ctg: [],
      selectCtg: 0,
      addsubctg: false,
      ctgName: ''
    }
  },
  methods: {
    login () {
      Axios.post(this.utils.baseURL + '/login', qs.stringify(this.man))
        .then(response => {
          if (response.data === 'success') {
            this.dialog.open = false
            Axios
              .post(this.utils.baseURL + '/info', 'type=basic')
              .then(response => {
                if (!(response.data === 'NotLogin')) {
                  this.info.id = response.data.id
                  this.info.name = response.data.name
                  this.info.type = response.data.type
                  if (this.info.type === 3) {
                    this.tabs = [{ name: '申诉管理', icon: 'mdi-shape-outline' }]
                    Axios.post(this.utils.baseURL + '/manager', 'state=5')
                      .then(response => {
                        this.data[5] = response.data
                      }).catch(error => {
                        console.log(error)
                        this.snackbar.color = 'error'
                        this.snackbar.text = '服务器错误'
                        this.snackbar.open = true
                      })
                  }
                }
              })
              .catch(error => {
                console.log(error)
                this.snackbar.color = 'error'
                this.snackbar.text = '服务器错误'
                this.snackbar.open = true
              })
          }
        })
        .catch(error => {
          console.log(error)
          this.snackbar.color = 'error'
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
    },
    lookup (a, b) {
      var extra = ''
      switch (a) {
        case 0:
          extra += 'isVerified=' + this.selectVerified + '&'
        // eslint-disable-next-line
        case 1:
        case 2:
          extra += 'type=' + this.select + '&text=' + this.text
          break
        default:
          break
      }
      Axios.post(this.utils.baseURL + '/manager', 'state=' + this.state + '&' + extra)
        .then(response => {
          if (response.data) {
            response.data.forEach((item, i) => {
              this.$set(b, i, item)
              b[i].type = utils.type[item.type]
              b[i].state = this.ADstate[item.state]
            })
          }
        })
        .catch(error => {
          console.log(error)
          this.snackbar.color = 'error'
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
    },
    activityLookup () {
      Axios.post(this.utils.baseURL + '/activity')
        .then(response => {
          if (response.data) {
            this.dataActivity = response.data
          }
        })
        .catch(error => {
          console.log(error)
          this.snackbar.color = 'error'
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
    },
    action (a, e) {
      var extra = '&'
      switch (a) {
        case 0:
          extra += 'id=' + e.id
          break
        case 1:
          extra += 'solr_id=' + e[0].id + '&do=' + e[1]
          break
        case 5:
          extra += 'id=' + e[0].id + '&do=' + e[1]
          break
        case 2:
          extra += 'id=' + e.id
          break
        case 3:
          extra += 'id=' + e.id
          break
        case 4:
          extra += 'ctg=' + JSON.stringify(['全部'].concat(this.ctg))
          break
        default:
          break
      }
      Axios.post(this.utils.baseURL + '/manager_action', 'state=' + a + extra)
        .then(response => {
          if (response.data === 'success') {
            this.dialog.open = false
            this.CDialog.open = false
            this.snackbar.color = 'green'
            this.snackbar.text = '成功'
            this.snackbar.open = true
          }
        })
        .catch(error => {
          console.log(error)
          this.snackbar.color = 'error'
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
    },
    atvt_register () {
      let fd = new FormData()
      fd.append('name', this.activity.name)
      fd.append('img', this.activity.img)
      fd.append('url', this.activity.url)
      Axios.post(this.utils.baseURL + '/activity_register', fd)
        .then(response => {
          if (response.data === 'success') {
            this.snackbar.color = 'green'
            this.snackbar.text = '成功'
            this.snackbar.open = true
            this.dialog.open = false
          }
        })
        .catch(error => {
          console.log(error)
          this.snackbar.color = 'error'
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
    }
  },
  created () {
    this.ctg = utils.ctg.slice(1)

    if (this.info && this.info.type)
      if (this.info.type === 2 || this.info.type === 3) {
        this.dialog.open = true
      } else
        this.$router.push('/home')
    else
      this.dialog.open = true
  },
  watch: {
    infoLoaded: function () {
      if (this.info.type === 0 || this.info.type === 1) {
        this.$router.push('/home')
      } else
        this.dialog.open = false
    }
  }
}
</script>
