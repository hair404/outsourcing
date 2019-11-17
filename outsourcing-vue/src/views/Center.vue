<template>
  <div
    class="wrapper"
    v-resize="onResize"
  >

    <v-dialog
      max-width="400"
      v-model="dialog.open"
    >
      <template v-if="dialog.state === 0">
        <v-card>
          <v-card-title>{{info.type === 0 ? '上传身份证':'上传营业执照'}}</v-card-title>
          <v-card-text>
            <v-file-input
              :label="info.type === 0 ? '身份证正面':'上传营业执照'"
              v-model="file0"
              accept="image/*"
              filled
            ></v-file-input>
            <v-file-input
              v-if="info.type === 1"
              label="身份证反面"
              v-model="file1"
              accept="image/*"
              filled
            ></v-file-input>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="primary"
              text
              @click="upload(0)"
            >上传</v-btn>
          </v-card-actions>
        </v-card>
      </template>

      <template v-if="dialog.state === 1">
        <v-card>
          <v-card-title>添加成员</v-card-title>
          <v-card-text>
            <v-form
              ref="form"
              lazy-validation
            >
              <v-text-field
                v-model="mem.name"
                label="成员姓名"
                :rules="[v => !!v || '必需项',]"
                required
              ></v-text-field>
              <v-text-field
                v-model="mem.info"
                label="成员介绍"
                :rules="[v => !!v || '必需项',]"
                required
              ></v-text-field>
              <v-text-field
                v-model="mem.tel"
                label="成员电话"
                type="number"
                :rules="[v => !!v || '必需项',]"
                required
              ></v-text-field>
              <v-text-field
                v-model="mem.email"
                label="成员邮箱"
                type="number"
                :rules="[v => !!v || '必需项',]"
                required
              ></v-text-field>
            </v-form>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="primary"
                text
                @click="add()"
              >添加</v-btn>
            </v-card-actions>
          </v-card-text>
        </v-card>
      </template>

      <template v-if="dialog.state === 2">
        <v-card>
          <v-card-title>文件上传</v-card-title>
          <v-card-text>
            <v-file-input
              label="点击上传"
              v-model="file"
              accept="image/*"
              filled
            ></v-file-input>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="primary"
              text
              @click="upload(0)"
            >上传</v-btn>
          </v-card-actions>
        </v-card>
      </template>

      <template v-if="dialog.state === 3">
        <v-card>
          <v-card-title>文件上传</v-card-title>
          <v-card-text>
            <v-file-input
              label="点击上传"
              v-model="avatar"
              accept="image/*"
              filled
            ></v-file-input>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="primary"
              text
              @click="upload(1)"
            >上传</v-btn>
          </v-card-actions>
        </v-card>
      </template>

      <template v-if="dialog.state === 4">
        <v-card>
          <v-card-title>新建项目</v-card-title>
          <v-card-text>
            <v-form
              ref="prj"
              lazy-validation
            >
              <v-text-field
                v-model="prj.prjname"
                label="项目名称"
                :rules="[v => !!v || '必需项']"
                required
              ></v-text-field>
              <v-text-field
                v-model="prj.info"
                label="具体信息"
                :rules="[v => !!v || '必需项']"
                required
              ></v-text-field>
              <v-select
                v-model="prj.tag"
                :items="ctgValue"
                color="primary"
                label="类别"
                outlined
              ></v-select>
              <v-select
                v-model="prj.subtag"
                :items="utils.ctg[prj.tag+1].subctg?utils.getTextValue(utils.ctg[prj.tag+1].subctg):[]"
                color="primary"
                label="子类别"
                outlined
              ></v-select>
              <v-file-input
                label="上传头图"
                v-model="prj.img"
                accept="image/*"
                filled
              ></v-file-input>
              <v-menu offset-y>
                <template v-slot:activator="{ on }">
                  <div class="title d-inline-block ml-4">{{prj.deadline}}</div>
                  <v-btn
                    color="primary"
                    outlined
                    v-on="on"
                  >
                    选择截止日期
                  </v-btn>
                </template>
                <v-date-picker
                  landscape
                  v-model="prj.deadline"
                ></v-date-picker>
              </v-menu>
              <v-text-field
                v-model="prj.price"
                label="总价"
                type="Number"
                :rules="[v => (v>0) || '必须大于0']"
                required
              ></v-text-field>
              <v-text-field
                v-model="prj.pia"
                label="首付款"
                type="Number"
                :rules="[v => (v>0 && v<parseInt(prj.price)) || '大于0且小于总价']"
                required
              ></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="primary"
              text
              @click="$refs.prj.validate()?upload(2):''"
            >提交</v-btn>
          </v-card-actions>
        </v-card>
      </template>

      <template v-if="dialog.state === 5">
        <v-card>
          <v-card-title>上首页</v-card-title>
          <v-card-text>
            <div class="title mb-2">你在首页时间以及权重将会由你付的金额决定</div>
            <v-select
              v-model="price"
              :items="[{text:'￥100',value:100},{text:'￥300',value:300},{text:'￥1000',value:1000}]"
              color="primary"
              label="快速选择"
              outlined
            ></v-select>
            <v-text-field
              v-model="price"
              label="金额"
              type="Number"
              :rules="[v => (v>0) || '必须大于0']"
              required
            ></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="primary"
              text
              @click="pay()"
            >支付</v-btn>
          </v-card-actions>
        </v-card>
      </template>
    </v-dialog>

    <div :class="cardClass === true?'v-card v-sheet theme--light':''">
      <v-row>
        <v-col
          cols="3"
          class="d-none d-md-flex"
        >
          <v-list
            width="100%"
            shaped
          >
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

        <v-col
          :cols="state === 1?9:''"
          style="height: 800px"
          :class="state === 0? 'py-0':''"
        >
          <template v-if="state === 0">
            <v-card
              height="800"
              class="mx-auto"
            >
              <v-parallax
                height="200"
                :src="'Platform'+myinfo.img"
              >

                <v-icon
                  style="display: block;width: fit-content;position: absolute;right: 10px;bottom: 10px;"
                  color="white"
                  @click="(dialog.open = true) && (dialog.state = 2)"
                >
                  mdi-pencil
                </v-icon>
              </v-parallax>

              <v-avatar
                size="100"
                style="top: 150px;left: 20px;position: absolute"
              >
                <v-img
                  @mouseenter="overlay = true"
                  @mouseleave="overlay = false"
                  :src="'/Platform' + myinfo.avatar"
                >
                  <v-overlay
                    :value="overlay"
                    absolute
                  >
                    <v-icon
                      color="white"
                      @click="(dialog.open = true) && (dialog.state = 3)"
                    >mdi-pencil</v-icon>
                  </v-overlay>
                </v-img>
              </v-avatar>

              <v-card-title class="ml-1 mt-8">{{myinfo.username}}</v-card-title>

              <v-card-text>
                <v-row
                  align="center"
                  class="mx-0"
                >
                  <v-rating
                    :value="myinfo.credit"
                    color="orange"
                    background-color="orange"
                    dense
                    half-increments
                    readonly
                  ></v-rating>

                  <div class="grey--text ml-4">{{myinfo.credit}}</div>
                </v-row>

                <v-icon>mdi-email</v-icon>

                <div class="my-4 mr-4 subtitle-1 black--text d-inline-block">
                  {{myinfo.email}}
                </div>

                <v-icon>mdi-phone</v-icon>

                <div class="my-4 subtitle-1 black--text d-inline-block">
                  {{myinfo.tel}}
                </div>

                <div>{{myinfo.info}}</div>
              </v-card-text>

              <v-divider class="mx-4"></v-divider>

              <v-card-title>我的成员</v-card-title>

              <v-card-text>
                <v-list>
                  <v-list-item-group
                    color="primary"
                    style="height: 220px;overflow-y: auto"
                  >
                    <v-list-item
                      v-for="(item, i) in member"
                      :key="i"
                      @click="this.$router.push(item.url)"
                    >
                      <v-list-item-content>
                        <v-list-item-title>{{ item.name }}</v-list-item-title>
                        <v-list-item-subtitle>{{ item.info }}</v-list-item-subtitle>
                      </v-list-item-content>
                    </v-list-item>
                  </v-list-item-group>
                </v-list>
              </v-card-text>

              <v-card-actions>
                <v-btn
                  color="deep-purple accent-4"
                  text
                  @click="state = 3"
                >
                  编辑
                </v-btn>
                <v-btn
                  v-if="info.type === 1"
                  color="green accent-4"
                  text
                  @click="(dialog.open = true) && (dialog.state = 5)"
                >
                  我要上首页
                </v-btn>
                <v-btn
                  color="error accent-4"
                  text
                  @click="logoff"
                >
                  注销
                </v-btn>
              </v-card-actions>
            </v-card>
          </template>

          <template v-if="state === 1">
            <v-row>
              <v-select
                v-model="proSelect"
                :items="proStateValue"
                class="mr-6 ml-8 d-inline-block"
                width="100%"
                color="primary"
                label="类型"
                outlined
              ></v-select>
              <v-btn
                v-if="info.type === 0"
                class="mr-8 ml-8 d-inline-block mt-2"
                color="primary"
                @click="(dialog.open = true) && (dialog.state = 4)"
                outlined
              >发布项目</v-btn>
            </v-row>
            <LoadCard
              style="height: 700px;overflow-y: auto"
              :type="1"
              address="my_prj"
              :extraParam="utils.toFormData({'state':proSelect})"
              :number="16"
              :btnText="info.type === 0?'上首页':undefined"
              :callback="()=>{dialog.open = true; dialog.state = 5}"
            ></LoadCard>
          </template>

          <template v-if="state === 2">
            <v-btn
              color="primary"
              text
            >清除通知</v-btn>
            <v-list-item-group
              style="height: 700px;overflow-y: auto"
              color="primary"
            >
              <v-list-item
                v-for="(item, i) in message"
                :key="i"
                @click="this.router.push(item.url)"
              >
                <v-list-item-content>
                  <v-list-item-title>{{ item.title }}</v-list-item-title>
                  <v-list-item-subtitle>{{ item.body }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </template>

          <template v-if="state === 3">
            <Info
              :info="info"
              :myinfo="myinfo"
              :snackbar="snackbar"
              class="mt-12"
            />
          </template>

          <template v-if="state === 4">
            <v-btn
              outlined
              color="error"
              style="margin-right: 10px"
              @click="deleteItem()"
            >删除所选</v-btn>
            <v-btn
              outlined
              color="primary"
              @click.stop="(dialog.state = 1) && (dialog.open = true)"
            >添加</v-btn>
            <Table
              ref="table"
              :data="member"
              :headers="headers"
              itemkey="id"
              issort
            ></Table>
          </template>
        </v-col>
      </v-row>
    </div>

    <div style="height:50px"></div>

    <v-bottom-navigation
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
import utils from '../js/utils'
import Table from '../components/Table'
import LoadCard from '../components/LoadCard'
import Info from '../components/Info'

export default {
  components: {
    Table,
    LoadCard,
    Info
  },
  props: {
    info: Object,
    infoLoaded: Boolean,
    snackbar: Object
  },
  data () {
    return {
      utils: utils,
      file0: null,
      file1: null,
      dialog: {
        open: false,
        state: 0
      },
      state: 0,
      cardClass: true,
      myinfo: {},
      tabs: [{ name: '个人信息', icon: 'mdi-account' }, { name: '我的项目', icon: 'mdi-progress-wrench' }, { name: '我的消息', icon: 'mdi-bell' }, { name: '编辑信息', icon: 'mdi-account-edit' }],
      file: null,
      avatar: null,
      overlay: false,
      proState: ['全部', '竞标中', '完善进度表', '付押金', '进行中', '待评分', '已结束', '已取消', '正在申诉'],
      proStateValue: [],
      proSelect: 0,
      message: [],
      member: [],
      mem: { name: '', info: '', tel: '', email: '' },
      prj: { prjname: '', tag: 0, subtag: 0, img: null, info: '', deadline: new Date().toISOString().substr(0, 10), price: '', pia: '' },
      ctgValue: [],
      headers: [{ text: '名字', value: 'name' }, { text: '介绍', value: 'info' }, { text: '邮箱', value: 'email' }, { text: '电话', value: 'tel' }],
      price: 0
    }
  },
  methods: {
    onResize () {
      if (window.innerWidth < 950) {
        this.cardClass = false
      } else {
        this.cardClass = true
      }
    },
    add () {
      if (this.$refs.form.validate()) {
        var mem = {}
        mem.name = this.mem.name
        mem.info = this.mem.info
        mem.tel = this.mem.tel
        mem.email = this.email
        Axios.post('/Platform/addMember', 'mem=' + JSON.stringify(mem))
          .then(response => {
            if (response.data === 'success')
              this.table.push(mem)
          })
          .catch(error => {
            console.log(error)
            this.snackbar.color = 'error'
            this.snackbar.text = '服务器错误'
            this.snackbar.open = true
          })
        this.dialog = false
        this.mem.name = ''
        this.mem.info = ''
        this.mem.tel = ''
        this.mem.email = ''
        this.$refs.form.resetValidation()
      }
    },
    deleteItem () {
      var selected = this.$refs.table.selected
      var id = []
      selected.forEach(item => {
        id.push(item.id)
      })
      Axios.post('delMember', 'id=' + JSON.stringify(id))
        .then(response => {
          this.table = this.table.filter(i => !selected.includes(i))
        })
        .catch(error => {
          console.log(error)
          this.snackbar.color = 'error'
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
    },
    upload (a) {
      if (a === 2) {
        let fd = new FormData()
        this.prj.forEach((item, i) => {
          fd.append(Object.keys(this.prj)[i], item)
        })
        Axios.post('register_prj', fd)
          .then(response => {
            if (response.data === 'success') {
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
        return
      }
      let fd = new FormData()
      fd.append('img', a === 0 ? this.file : this.avatar)
      Axios.post(a === 0 ? '/Platform/uploadimg' : '/Platform/upload_avatar', fd)
        .then(response => {
          if (response.data) {
            this.myinfo.img = response.data
          }
        })
        .catch(error => {
          console.log(error)
          this.snackbar.color = 'error'
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
    },
    pay () {
      Axios.post(this.info.type === 0 ? 'ad_register_prj' : 'ad_register_stu', 'ad_price=' + this.price)
        .then(response => {
          if (response.data) {
            var id = window.open('', '_blank', 'height=800,width=1000')
            id.onload = function () {
              id.document.body.innerHTML = response.data
              id.document.forms[0].submit()
            }
          }
        })
        .catch(error => {
          console.log(error)
          this.snackbar.color = 'error'
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
    },
    logoff () {
      Axios.post('/logoff').then(response => {
        if (response.data === 'success') this.$router.push({ path: '/Login' })
      }).catch(error => { console.log(error) })
    }
  },
  created () {
    utils.getTextValue(this.proState, this.proStateValue)

    utils.getTextValue(utils.ctg.slice(1), this.ctgValue, (item, i) => { return { text: item.name, value: i } })

    Axios.post('/Platform/center').then(response => {
      this.myinfo = response.data
      if (response.data.isVaild === 1) {
        this.dialog.open = true
        this.dialog.state = 0
      }
    }).catch(error => { console.log(error) })

    Axios.post('/Platform/member', 'id=' + this.info.id).then(response => {
      this.member = response.data
    }).catch(error => { console.log(error) })

    Axios.post('/Platform/notify').then(response => {
      this.message = response.data
    }).catch(error => { console.log(error) })

    if ((this.info.type === 1) && (this.tabs.length === 4)) {
      this.tabs.push({ name: '我的成员', icon: 'mdi-account-group' })
    }
  },
  watch: {
    state: function (newS, oldS) {
      if (newS === undefined)
        this.state = oldS
    },
    infoLoaded: function (loaded) {
      if (loaded === true)
        if (this.info.type === 1) {
          this.tabs.push({ name: '我的成员', icon: 'mdi-account-group' })
        }
    }
  }
}
</script>
