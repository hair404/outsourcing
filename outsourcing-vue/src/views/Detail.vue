<template>
  <div class="wrapper d-flex flex-wrap">
    <!-- <div class="wrapper">
      <div
        id="process"
        style="text-align: center;margin-bottom: 2%;"
      >
        <div class="select-card process">匹配工作室</div>
        <div class="select-card process">工作室工作</div>
        <div class="select-card process">验收成果</div>
      </div>
      <div
        class="card"
        style="width: 98%;min-height: 500px;position: relative;margin: 0 auto;height: fit-content;"
      >
        <div
          id="prj_info"
          class="select-card-content"
        >
          <div style="font-size: 20px;">需求信息</div>
          <div>需求标题：</div>
          <div>需求类别：</div>
          <div>发布公司：</div>
          <div>发布日期：</div>
          <div>截止日期：</div>
          <div>预算金额：</div>
          <div>详细介绍：</div>
        </div>
        <div
          id="enroll"
          class="select-card select-card-hover"
          style="width: 100px;bottom: 3%;position: absolute;margin-left: 5%;display: none;"
        >我要投标</div>

        <div
          id="modal"
          style="display: none"
        >
          <div
            class="modal_backgrd"
            $click="$('modal').children[0].setAttribute('style','background: rgba(0, 0, 0, 0);');$('modal').children[1].setAttribute('style','top: 150%');$('modal').setAttribute('style','display: none;')"
          ></div>
          <div class="card modal">
            <div style="position: absolute;top: 25px;width: 100%;text-align: center;font-size: 16px;">相关信息</div>
            <form
              id="change"
              class="content"
              style="margin-top: 70px;padding-left: 10%;"
              onsubmit="return false;"
            >
              <textarea
                name="info"
                type="text"
                placeholder="告诉我们原因"
                style="width: 100%;height: 300px"
                required
              ></textarea>
              <input
                type="submit"
                class="select-card select-card-hover"
                style="width: 100px;height: 42px;"
                value="确认"
              >
            </form>
          </div>
        </div>

      </div>
    </div>
    <div class="wrapper">
      <div
        id="container"
        class="container"
        style="top: 20px;"
      ></div>

      <div
        class="card"
        id="state1"
        style="display: none;"
      >
        <div
          class="card"
          style="height: 106px;"
        >
          <div style="margin-left: 5%;font-size: 20px;line-height: 64px;display: inline-block;">审核进度</div>
          <div
            id="submit"
            class="select-card select-card-hover"
            style="display: none;"
          >提交文件</div>
          <div
            id="pay"
            class="select-card select-card-hover"
            style="display: none;"
          >支付违约金</div>
          <input
            type="file"
            style="display: none;"
            id="fileInput"
          >
          <div style="width: 90%;margin: 0 auto;">
            <div class="filename">文件名</div>
            <div class="date">日期</div>
            <div class="passed">当前状态</div>
            <div style="line-height: 42px;display: inline-block;">操作</div>
          </div>
        </div>
        <table
          id="table"
          class="table"
        >
        </table>
      </div>
    </div> -->
    <v-dialog
      v-model="dialog"
      max-width="400"
    >
      <v-card>
        <v-card-title>添加进度</v-card-title>
        <v-card-text>
          <v-form
            ref="form"
            v-model="vaild"
            lazy-validation
          >
            <v-text-field
              v-model="step.name"
              label="进度名"
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
            v-model="state"
            style="width: 100%"
            vertical
          >
            <v-stepper-step
              :complete="state > 1"
              step="1"
            >竞标</v-stepper-step>
            <v-stepper-content step="1">
              <LoadCard
                v-if="state === 1"
                isActiveLoad
                isLoaded
                :cards="enroll"
                :type="0"
              ></LoadCard>
            </v-stepper-content>

            <v-stepper-step
              :complete="state > 2"
              step="2"
            >完善进度表
              <div
                v-if="state === 2 && info.type === 1"
                style="position: absolute;right: 20px"
              >
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
              </div>
            </v-stepper-step>
            <v-stepper-content step="2">
              <Table
                ref="table"
                v-if="state === 2 && table"
                :data="table"
              ></Table>
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
                >等待工作室标定进度款</div>
                <v-card-actions style="padding-top: 80px">
                  <v-btn
                    color="primary"
                    text
                    outlined
                  >催促对方尽快完成</v-btn>
                  <v-btn
                    color="error"
                    text
                    outlined
                  >要求取消项目</v-btn>
                </v-card-actions>
              </v-card>
            </v-stepper-content>

            <v-stepper-step
              :complete="state > 3"
              step="3"
            >付押金</v-stepper-step>
            <v-stepper-content step="3">
              <Paid
                v-if="state === 3"
                :info="info"
                :prjinfo="projectInfo"
              ></Paid>
            </v-stepper-content>

            <v-stepper-step
              :complete="state > 4"
              step="4"
            >进行中</v-stepper-step>
            <v-stepper-content step="4">
            </v-stepper-content>

            <v-stepper-step
              :complete="state > 5"
              step="5"
            >完成并评分</v-stepper-step>
            <v-stepper-content step="5">
            </v-stepper-content>
          </v-stepper>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>
<script>
import Axios from 'axios'
import utils from '../js/utils'
import Prjinfo from '../components/Prjinfo'
import MyInfo from '../components/MyInfo'
import LoadCard from '../components/LoadCard'
import Paid from '../components/Paid'
import Table from '../components/Table'

export default {
  components: {
    Prjinfo,
    MyInfo,
    LoadCard,
    Paid,
    Table
  },
  props: {
    info: Object
  },
  data () {
    return {
      projectInfo: {},
      displayInfo: this.info, // 右上角展示信息
      infoLoaded: false,
      cols: 8, // 控制自适应
      state: 1, // 控制进度条
      dialog: false,
      enroll: [],
      table: Array,
      step: {
        name: '',
        info: '',
        time: 0
      },
      vaild: false
    }
  },
  methods: {
    onResize () {
      if (window.innerWidth < 950)
        this.cols = 12
      else
        this.cols = 8
    },
    add () {
      if (!this.$refs.form.validate()) {
        var step = {}
        step.name = this.step.name
        step.info = this.step.info
        this.table.push(step)
        this.step.name = ''
        this.step.info = ''
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
