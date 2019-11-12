<template>
  <div class="wrapper d-flex flex-wrap">
    <v-dialog
      v-model="dialog"
      max-width="400"
    >
      <v-card v-if="state === 2">
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
            <v-btn
              color="primary"
              text
              @click="add()"
            >添加</v-btn>
          </v-card-actions>
        </v-card-text>
      </v-card>

      <v-card v-if="state === 7">
        <v-card-title>我要申诉</v-card-title>
        <v-card-text>
          <v-text-field
            v-model="reason"
            filled
            label="原因"
          />
          <v-text-field
            v-if="punishSelect === 0"
            v-model="money"
            :rules="v => v <= 100 && v > 0 || 必须小于100大于0"
            type="Number"
            label="我要求退回金额（需要赔偿金额的百分比）"
            required
          />%
        </v-card-text>
        <v-card-actions>
          <v-btn
            color="primary"
            text
            @click="action(info.type === 0?10:12,{reason:reason,money:money},()=>{projectInfo.state += 1})"
          >提交</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-container>
      <v-row v-resize="onResize">
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
                  <v-btn
                    v-if="state === 1 && info.type === 1"
                    outlined
                    color="red"
                    style="margin-right: 10px"
                    @click="action(0)"
                  >我要竞标</v-btn>

                  <template v-if="state === 2 && info.type === 1">
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
                  v-if="state === 1"
                  isActiveLoad
                  :isLoaded="infoLoaded"
                  :cardsProp="enroll"
                  :type="0"
                  btnText="中标"
                  :callback="win"
                ></LoadCard>

                <template v-if="state === 2">
                  <div v-if="table">
                    <Table
                      ref="table"
                      :data="table"
                      :isCompany="info.type === 0?true : false"
                    ></Table>
                    <v-btn
                      color="primary"
                      class="my-4"
                      outlined
                      @click="action(1,{table:JSON.stringify(table)},()=>{projectInfo.wait = 1})"
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
                      v-if="info.type == 0 && projectInfo.wait"
                      style="padding:40px 0 5px 16px"
                    >等待工作室确认进度表</div>
                    <div
                      v-else-if="info.type == 0"
                      style="padding:40px 0 5px 16px"
                    >等待工作室制定进度表</div>
                    <div
                      v-if="info.type == 1"
                      style="padding:40px 0 5px 16px"
                    >等待公司定进度款</div>
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
                        @click="action(3)"
                      >要求取消项目</v-btn>
                    </v-card-actions>
                  </v-card>
                </template>

                <Paid
                  v-if="state === 3"
                  :info="info"
                  :prjinfo="projectInfo"
                  :submit="action"
                ></Paid>

                <Processing
                  v-if="state === 4"
                  :data="table"
                  :type="info.type"
                  :id="projectInfo.id"
                  :snackbar="snackbar"
                  class="mx-auto my-1"
                  :style="processWidth"
                  @submit="action"
                ></Processing>

                <Comment
                  v-if="state === 5"
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
                class="headline"
                :deadline="projectInfo.deadline"
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
import Axios from 'axios'
import qs from 'qs'
import utils from '../js/utils'
import Prjinfo from '../components/Prjinfo'
import MyInfo from '../components/MyInfo'
import LoadCard from '../components/LoadCard'
import Paid from '../components/Paid'
import Table from '../components/Table'
import Processing from '../components/Processing'
import Comment from '../components/Comment'
import Countdown from '../components/Countdown'

export default {
  components: {
    Prjinfo,
    MyInfo,
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
      projectInfo: {},
      displayInfo: this.info, // 右上角展示信息
      infoLoaded: false,
      vertical: true,
      processWidth: 'width: 95%',
      steps: ['竞标', '完善进度表', '付押金', '进行中', '完成并评分'],
      cols: 8, // 控制自适应
      state: 1, // 控制进度条
      dialog: false,
      enroll: [],
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
    win (id) { this.action(0, { studioid: id }, () => { this.projectInfo.state = 2 }) },
    add () {
      console.log(this.$refs.form.validate())
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
      const selected = this.$refs.table.selected
      this.table.forEach((item, i) => {
        selected.forEach(selectItem => {
          if (item === selectItem)
            this.table.splice(i, 1)
        })
      })
    },
    action (a, extra, callback) {
      Axios.post(this.info.type === 0 ? 'company_action' : 'studio_action', qs.stringify(Object.assign({ id: this.projectInfo.id, action: a }, extra)))
        .then(response => {
          if (this.response.data === 'success')
            if (callback) {
              callback()
            } else;
          else {
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
    Axios.post('/Platform/project_info', 'id=' + 1)
      .then(response => {
        this.projectInfo = response.data
        this.state = response.data.state
        this.enroll = response.data.enroll
        this.table = utils.getReal(response.data.table)
        this.infoLoaded = true
        if (response.data.companyID !== 'self')
          Axios.post('display_info', 'id=' + response.data.companyID)
            .then(response => {
              this.displayInfo = response.data
            })
            .catch(error => { console.log(error) })
      })
      .catch(error => { console.log(error) })
  }
}
</script>
