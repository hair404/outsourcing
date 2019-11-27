<template>
  <div class="wrapper d-flex flex-wrap">
    <v-dialog
      v-model="dialog"
      max-width="400"
    >
      <v-card v-if="state === 1">
        <v-card-title>确认？</v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="info.type === 0?action(0, { studioid: id }, () => { state = 2 }):action(0);dialog = false"
          >确认</v-btn>
          <v-btn
            color="primary"
            text
            @click="dialog = false"
          >取消</v-btn>
        </v-card-actions>
      </v-card>

      <v-card v-if="state === 2 && (info.type == 1 && projectInfo.isform === 0)">
        <v-card-title>添加进度</v-card-title>
        <v-card-text>
          <v-form
            ref="form"
            v-model="valid"
            lazy-validation
          >
            <v-text-field
              v-model="step.name"
              label="进度名"
              :rules="[v => !!v || '必需项',]"
              required
            ></v-text-field>
            <v-text-field
              v-model="step.info"
              label="进度详情(可选)"
            ></v-text-field>
            <v-text-field
              v-model="step.time"
              label="所需时间(天数)"
              type="number"
              :rules="[v => v > 0 || '天数必须大于0']"
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

      <v-card v-if="state == 2 && (info.type == 0) && (projectInfo.isform === 0) && (projectInfo.issetprice === 0)">
        <v-card-title>确认？</v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="action(3);dialog = false"
          >确认</v-btn>
          <v-btn
            color="primary"
            text
            @click="dialog = false"
          >取消</v-btn>
        </v-card-actions>
      </v-card>

      <v-card v-if="state === 7">
        <v-card-title>我要申诉</v-card-title>
        <v-card-text>
          <v-form ref="form">
            <v-text-field
              v-model="reason"
              filled
              label="原因"
            />
            <v-text-field
              v-model="money"
              :rules="[v => (v <= 100 && v > 0) || '必须小于100大于0']"
              type="Number"
              label="我要求退回金额（需要赔偿金额的百分比）"
              required
              style="display: inline-block;width :90%"
            />
            <div style="display: inline-block;width: 10%;text-align: center">%</div>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="$refs.form.validate()?action(info.type === 0?11:12,{reason:reason,money:money},()=>{state += 1}):''"
          >提交</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog
      max-width="400"
      v-model="infoDialog"
    >
      <v-card>
        <v-card-title>{{projectInfo.prjname}}</v-card-title>
        <v-card-text>{{projectInfo.info}}</v-card-text>
        <v-divider></v-divider>
        <v-card-title>具体信息</v-card-title>
        <v-list>
          <v-list-item
            v-for="(item,i) in displayinfo"
            :key="i"
          >
            <v-list-item-content>{{item.key}}</v-list-item-content>
            <v-list-item-icon>
              {{projectInfo[item.value]}}
            </v-list-item-icon>
          </v-list-item>
        </v-list>
      </v-card>
    </v-dialog>

    <v-container>
      <!-- <v-row v-resize="onResize">
        <v-col :cols="cols">
          <Prjinfo :info="projectInfo"></Prjinfo>
        </v-col>
        <v-col cols="4">
          <MyInfo
            :height="300"
            :info="displayInfo"
            :infoLoaded="infoLoaded"
            isOthers
          >
          </MyInfo>
        </v-col>
      </v-row> -->

      <v-row
        align="center"
        v-resize="onResize"
      >
        <v-col cols="3">
          <img
            style="border-radius: 14px;width: 100%"
            :src="utils.baseURL + ''+projectInfo.img"
          />
        </v-col>
        <v-col cols="9">
          <v-card-title>{{projectInfo.prjname}}</v-card-title>
          <v-card-subtitle>
            <v-btn
              class="primary--text text--darken-2 pa-0"
              text
              @click="$router.push({name:'display',params:{id:projectInfo.id}})"
            >{{projectInfo.companyName}}</v-btn>
          </v-card-subtitle>
        </v-col>
      </v-row>

      <v-row>
        <v-btn
          color="primary"
          width="95%"
          class="mx-auto"
          v-if="state === 1 && info.type === 1 && !isEnter"
          @click="dialog.state = 1 && (dialog.open = 1)"
        >我要投标</v-btn>
      </v-row>

      <v-row>
        <v-col
          class="pb-0"
          cols="12"
        >
          <v-card-title
            v-ripple
            @click="infoDialog = true"
            class="px-0"
          >关于此项目<v-spacer></v-spacer>
            <v-icon>mdi-arrow-right</v-icon>
          </v-card-title>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12">
          <p>{{projectInfo.info}}</p>
          <v-chip
            outlined
            class="mr-4"
          >{{infoLoaded===true?utils.ctg[projectInfo.tag].name:''}}</v-chip>
          <v-chip outlined>{{infoLoaded===true? ( projectInfo.subtag === 0 ? '': utils.ctg[projectInfo.tag].subctg[projectInfo.subtag - 1]):''}}</v-chip>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12">
          <v-stepper
            v-if="state < 6"
            v-model="state"
            style="width: 100%"
            :vertical="vertical"
          >
            <v-stepper-header v-if="!vertical">
              <template v-for="(item,i) in steps">
                <v-stepper-step
                  :key="`${i}-step`"
                  :complete="state > (i + 1)"
                  :step="i + 1"
                >
                  {{item}}
                </v-stepper-step>

                <v-divider
                  v-if="(i + 1) !== 5"
                  :key="i"
                ></v-divider>
              </template>
            </v-stepper-header>

            <template v-for="(item,i) in steps">
              <v-stepper-step
                v-if="vertical"
                :key="i"
                :complete="state > (i + 1)"
                :step="i + 1"
              >{{item}}
                <div
                  v-if="state === (i+1)"
                  style="position: absolute;right: 20px"
                >
                  <!-- <v-btn
                    v-if="state === 1 && info.type === 1 && !isEnter"
                    outlined
                    color="red"
                    style="margin-right: 10px"
                    @click="dialog = true"
                  >我要竞标</v-btn> -->

                  <template v-if="state === 2 && info.type === 1 && projectInfo.isform === 0">
                    <div class="d-inline mr-4">{{'剩余可以分配的时间：' + getLeftTime(projectInfo.deadline)}}</div>
                    <v-btn
                      outlined
                      color="error"
                      style="margin-right: 10px"
                      @click="deleteItem()"
                    >删除所选</v-btn>
                    <v-btn
                      outlined
                      color="primary"
                      @click.stop="dialog = true"
                    >添加</v-btn>
                  </template>
                </div>
              </v-stepper-step>
              <v-stepper-content
                :step="i + 1"
                :key="(i+1) * 10"
                :style="!vertical &&state !== i + 1 ? 'display:none':''"
              >
                <LoadCard
                  v-if="(i+1) === 1 && state === 1 && info.type === 0 && projectInfo.companyID === 'self'"
                  isActiveLoad
                  :isLoaded="infoLoaded"
                  :cardsProp="enroll"
                  :type="0"
                  :extraText="(item)=>{return '报价：' + item.quote}"
                  :btnText="projectInfo.companyID === 'self'?'中标':null"
                  :callback="win"
                ></LoadCard>

                <div
                  v-if="projectInfo.enroll?projectInfo.enroll.length === 0:false"
                  class="headline"
                >没有工作室参与竞标</div>

                <div
                  v-if="(i+1) === 1 && state === 1 && isEnter"
                  class="headline"
                >您已经进入投标，请耐心等待结果</div>

                <template v-if="(i+1) === 2 && state === 2">
                  <div v-if="(info.type == 1 && projectInfo.isform === 0) || (info.type === 0 && projectInfo.issetprice === 0 && projectInfo.isform === 1) || (info.type === 1 && projectInfo.isform === 1 && projectInfo.issetprice === 1)">
                    <Table
                      ref="table"
                      :data="table"
                      :isCompany="info.type === 0?true : false"
                      :isPriceCol="(info.type == 1 && projectInfo.isform === 0)"
                      :headers="headers"
                      itemkey="name"
                    ></Table>
                    <v-btn
                      v-if="info.type === 1 && projectInfo.isform === 1 && projectInfo.issetprice === 1"
                      color="primary"
                      class="my-4"
                      outlined
                      @click="action(4,{},()=>{state = 3})"
                    >确认</v-btn>
                    <v-btn
                      v-else
                      color="primary"
                      class="my-4"
                      outlined
                      @click="action(1,{table:JSON.stringify(table)},()=>{info.type === 1?projectInfo.isform = 1:projectInfo.issetprice = 1})"
                    >提交</v-btn>

                  </div>
                  <v-card
                    v-else
                    class="mx-auto"
                    width="100%"
                    height="200"
                    outlined
                  >
                    <div
                      v-if="info.type == 0 && projectInfo.isform === 1"
                      style="padding:40px 0 5px 16px"
                    >等待工作室确认进度款</div>
                    <div
                      v-else-if="info.type == 0"
                      style="padding:40px 0 5px 16px"
                    >等待工作室制定进度表</div>
                    <div
                      v-if="info.type == 1"
                      style="padding:40px 0 5px 16px"
                    >等待公司制定进度款</div>
                    <v-card-actions style="padding-top: 80px">
                      <v-btn
                        color="primary"
                        text
                        outlined
                        @click="action(2)"
                      >催促对方尽快完成</v-btn>
                      <v-btn
                        color="error"
                        text
                        outlined
                        @click="dialog = true"
                      >要求取消项目</v-btn>
                    </v-card-actions>
                  </v-card>
                </template>

                <Paid
                  v-if="(i+1) === 3 && state === 3"
                  :info="info"
                  :prjinfo="projectInfo"
                  @submit="action"
                ></Paid>

                <Processing
                  v-if="(i+1) === 4 && state === 4"
                  :data="table"
                  :type="info.type"
                  :id="projectInfo.id"
                  :snackbar="snackbar"
                  class="mx-auto my-1"
                  :style="processWidth"
                  @submit="action"
                  :prjinfo="projectInfo"
                ></Processing>

                <Comment
                  v-if="(i+1) === 5 && state === 5"
                  :type="info.type"
                  :prjinfo="projectInfo"
                  @submit="action"
                ></Comment>
              </v-stepper-content>
            </template>
          </v-stepper>

          <v-card
            v-if="state === 6"
            width="100%"
            height="400px"
          >
            <div
              class="display-1"
              style="text-align: center;padding-top: 80px"
            >已经完成此项目</div>
            <v-card-subtitle
              class="headline"
              style="text-align: center"
            >对方的评分:</v-card-subtitle>
            <v-rating
              style="text-align: center"
              v-model="projectInfo.othersRate"
              background-color="orange"
              color="orange"
              readonly
            ></v-rating>
            <v-card-subtitle
              class="headline"
              style="text-align: center"
            >我的评分:</v-card-subtitle>
            <v-rating
              style="text-align: center"
              v-model="projectInfo.myRate"
              background-color="orange"
              color="orange"
              readonly
            ></v-rating>
          </v-card>

          <v-card
            v-if="state === 7"
            width="100%"
            height="400px"
          >
            <div
              class="display-1"
              style="text-align: center;padding-top: 80px"
            >处于被取消状态</div>
            <v-card-subtitle
              class="headline"
              style="text-align: center"
            >在<Countdown
                class="headline d-inline-block"
                :deadline="projectInfo.countdown"
              ></Countdown>之前可以申诉</v-card-subtitle>
            <v-btn
              outlined
              class="mx-auto"
              color="error"
              style="display: block"
              @click="dialog = true"
            >申诉</v-btn>
          </v-card>

          <v-card
            v-if="state === 8"
            width="100%"
            height="400px"
          >
            <div
              class="display-1"
              style="text-align: center;padding-top: 80px"
            >平台正在处理申诉，请耐心等候</div>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios'
import qs from 'qs'
import utils from '../js/utils'
import LoadCard from '../components/LoadCard'
import Paid from '../components/Paid'
import Table from '../components/Table'
import Processing from '../components/Processing'
import Comment from '../components/Comment'
import Countdown from '../components/Countdown'

export default {
  name: 'detail',
  components: {
    LoadCard,
    Paid,
    Table,
    Processing,
    Comment,
    Countdown
  },
  props: {
    info: Object,
    snackbar: Object
  },
  data () {
    return {
      utils: utils,
      projectInfo: {},
      displayinfo: [{ key: '发布时间', value: 'releaseTime' },
        { key: '整个项目截止时间', value: 'deadline' },
        { key: '薪金', value: 'price' },
        { key: '首付款', value: 'payinadvance' }],
      infoLoaded: false,
      vertical: true,
      processWidth: 'width: 95%',
      steps: ['竞标', '完善进度表', '付押金', '进行中', '完成并评分'],
      cols: 8, // 控制自适应
      state: 1, // 控制进度条
      dialog: false,
      infoDialog: false,
      enroll: Array,
      isEnter: false,
      id: 0,
      headers: [{
        text: '进度名', value: 'name'
      },
      {
        text: '进度详情', value: 'info'
      },
      {
        text: '所需时间(天)', value: 'time'
      }],
      table: [],
      step: {
        name: '',
        info: '',
        time: 0
      },
      valid: true,
      reason: '',
      money: 0
    }
  },
  methods: {
    getinfo () {
      axios.post(this.utils.baseURL + '/project_info', 'id=' + this.$route.params.id)
        .then(response => {
          this.projectInfo = response.data
          this.state = response.data.state
          this.enroll = utils.getReal(response.data.enroll, [])
          if (this.info.type === 1)
            this.enroll.forEach(item => {
              if (item.id === this.info.id)
                this.isEnter = true
            })
          this.table = utils.getReal(response.data.table, [])
          this.infoLoaded = true
        })
        .catch(error => { console.log(error) })
    },
    onResize () {
      if (window.innerWidth < 950) {
        this.cols = 12
        this.vertical = false
        this.processWidth = 'width: 100%'
      } else {
        this.cols = 8
        this.vertical = true
        this.processWidth = 'width: 95%'
      }
    },
    win (id) { this.id = id; this.dialog = true },
    add () {
      if (this.$refs.form.validate() && this.step.time > 0) {
        var step = {}
        step.name = this.step.name
        step.info = this.step.info
        step.time = this.step.time
        this.table.push(step)
        this.dialog = false
        this.step.name = ''
        this.step.info = ''
        this.step.time = 0
        this.$refs.form.resetValidation()
      }
    },
    deleteItem () {
      var selected = this.$refs.table[0].selected
      this.table = this.table.filter(i => !selected.includes(i))
    },
    getLeftTime (deadline) {
      if (deadline === undefined)
        return 0
      var day = (Date.parse(deadline + ' 24:00:00') - Date.parse(new Date())) / 1000 / 60 / 60 / 24
      this.table.forEach(item => {
        day -= item.time
      })
      day = parseInt(day)
      if (day === 0)
        return '已全部分配'
      return day + '天'
    },
    action (a, extra, callback) {
      axios.post(this.info.type === 0 ? this.utils.baseURL + '/company_action' : this.utils.baseURL + '/studio_action', qs.stringify(Object.assign({ id: this.projectInfo.id, action: a }, extra)))
        .then(response => {
          if (response.data === 'success')
            if (callback) {
              this.snackbar.color = 'green'
              this.snackbar.text = '成功'
              this.snackbar.open = true
              callback()
            } else;
          else if (response.data[0] === '<') {
            var id = window.open('', '_blank', 'height=800,width=1000')
            id.onload = function () {
              id.document.body.innerHTML = response.data
              id.document.forms[0].submit()
            }
          } else {
            this.snackbar.color = 'error'
            this.snackbar.text = '服务器错误'
            this.snackbar.open = true
          }
        }).catch(error => {
          console.log(error)
          this.snackbar.color = 'error'
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
    }
  },
  mounted () {
    this.getinfo()
  },
  activated () {
    this.getinfo()
  }
}
</script>
