<template>
  <div>
    <v-dialog
      v-model="dialog"
      max-width="400"
    >
      <v-card v-if="dialogState === 0">
        <v-card-title>文件上传</v-card-title>
        <v-card-text>
          <v-file-input
            label="点击上传"
            v-model="file"
            filled
          ></v-file-input>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="upload()"
          >上传</v-btn>
        </v-card-actions>
      </v-card>

      <v-card v-if="dialogState === 1">
        <v-card-title>延迟原因</v-card-title>
        <v-card-text>
          <v-text-field
            v-model="reason"
            filled
            label="原因"
          />
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="$emit('submit',7,{reason:reason,stepid:stepid},()=>{data[stepid].state = 3;dialog = false})"
          >提交</v-btn>
        </v-card-actions>
      </v-card>

      <v-card v-if="dialogState === 2">
        <v-card-title>要求对方</v-card-title>
        <v-card-text>
          <v-form
            ref="form"
            v-model="valid"
          >
            <v-select
              :items="type===0?punishCompany:punishStudio"
              v-model="punishSelect"
              filled
              label="点击以选择"
            />
            <template v-if="punishSelect === 0">
              <v-text-field
                v-model="money"
                :rules="[v => v < 100 && v > 0 || '必须小于100大于0']"
                type="Number"
                label="金额百分比"
                required
                style="display: inline-block;width :90%"
              />
              <div style="display: inline-block;width: 10%;text-align: center">%</div>
            </template>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="$emit('submit',9,{measure:punishSelect,stepid:stepid,money:money},()=>{dialog = false})"
          >提交</v-btn>
        </v-card-actions>
      </v-card>

      <v-card v-if="dialogState === 3">
        <v-card-title>我要申诉</v-card-title>
        <v-card-text>
          <v-text-field
            filled
            label="原因"
            v-model="reason"
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
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="$emit('submit',type === 0 ? 7 : 8,{reason:reason,money:money},()=>{dialog = false})"
          >提交</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-progress-linear :value="progress"></v-progress-linear>
    <v-expansion-panels
      v-model="expand"
      accordion
    >

      <v-expansion-panel
        v-for="(item,i) in data"
        :key="i"
      >
        <v-expansion-panel-header disable-icon-rotate>
          {{getName(item,i)}}
          <template v-slot:actions>
            <div class="text--secondary mr-4">截止时间：{{new Date(new Date() - item.time * 24* 60*60*1000).toLocaleDateString().replace(/\//g, "-") }}</div>
            <v-icon :color="state[item.state].color">{{state[item.state].icon}}</v-icon>
          </template>
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          {{item.info}}
          <div v-if="item.state === 3 && type === 0">对方已经延误，可以进行处罚</div>
          <div v-if="(item.state === 4 && type === 0)||(item.state === 7 && type === 1)">对方可能在<Countdown :deadline="item.deadline"></Countdown>内发起申诉</div>
          <div v-if="item.state === 4 && type === 1">你被扣{{item.money}}%的进度款，你可以选择在<Countdown :deadline="item.deadline"></Countdown>内发起申诉</div>
          <div v-if="item.state === 7 && type === 0">你被扣{{item.money}}%的押金由于延误支付进度款，你可以选择在<Countdown :deadline="item.deadline"></Countdown>内发起申诉</div>
          <div v-if="item.state === 8">现在处于处理申诉状态，请耐心等候</div>

          <div style="float: right">
            <v-btn
              v-if="item.state === 1 && type === 1"
              outlined
              color="primary"
              class="mr-4"
              @click.stop="(dialogState = 0) || (stepid = item.part) || (dialog = true)"
            >上传文件</v-btn>
            <v-btn
              v-if="item.state === 1 && type === 1"
              text
              color="primary"
              class="mr-4"
              @click.stop="(dialogState = 1) && (dialog = true) || (stepid = item.part)"
            >我无法准时完成！</v-btn>
            <v-btn
              v-if="item.state === 2 && type === 0"
              text
              color="primary"
              class="mr-4"
              @click="window.open('/Platform/'+prjinfo.path) || (stepid = item.part)"
            >下载文件</v-btn>
            <v-btn
              v-if="item.state === 2 && type === 0"
              outlined
              color="primary"
              class="mr-4"
              @click="$emit('submit',8,{stepid:item.part},()=>{dialog = false}) || (stepid = item.part)"
            >通过</v-btn>
            <v-btn
              v-if="item.state === 2 && type === 0"
              outlined
              color="error"
              class="mr-4"
              @click="(dialogState = 2) && (dialog = true) || (stepid = item.part)"
            >不通过</v-btn>

            <div
              v-if="item.state === 3 && type === 1"
              class="text--secondary mr-4"
            >等待对方进行处罚</div>
            <v-btn
              v-if="item.state === 3 && type === 0"
              outlined
              color="error"
              class="mr-4"
              @click="(dialogState = 2) && (dialog = true) && (stepid = item.part)"
            >进行处罚</v-btn>

            <v-btn
              v-if="item.state === 4 && type === 1"
              outlined
              color="error"
              class="mr-4"
              @click="(dialogState = 3) && (dialog = true) || (stepid = item.part)"
            >不合理，申诉</v-btn>

            <v-btn
              v-if="item.state === (5 || 6) && type === 0"
              outlined
              color="green"
              class="mr-4"
              @click="$emit('submit',6,{stepid:item.part}) || (stepid = item.part)"
            >付款({{item.price}})</v-btn>
            <div
              v-if="item.state === 5 && type === 1"
              class="text--secondary mr-4"
            >等待对方付款</div>

            <v-btn
              v-if="item.state === 6 && type === 1"
              outlined
              color="error"
              class="mr-4"
              @click="(dialogState = 2) && (dialog = true) || (stepid = item.part)"
            >要求进行处罚</v-btn>
            <v-btn
              v-if="item.state === 6 && type === 1"
              outlined
              color="primary"
              class="mr-4"
              @click="$emit('submit',10) || (stepid = item.part)"
            >催促公司付款</v-btn>

            <v-btn
              v-if="item.state === 7 && type === 0"
              outlined
              color="error"
              class="mr-4"
              @click="(dialogState = 3) && (dialog = true) || (stepid = item.part)"
            >不合理，申诉</v-btn>
          </div>
        </v-expansion-panel-content>
      </v-expansion-panel>

    </v-expansion-panels>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  props: {
    data: Array,
    type: Number,
    id: Number,
    snackbar: Object,
    prjinfo: Object
  },
  data () {
    return {
      state: [
        { icon: '$expand', color: 'primary' },
        { icon: 'mdi-progress-wrench', color: 'orange' },
        { icon: 'mdi-timer', color: 'purple' },
        { icon: 'mdi-alert-circle-outline', color: 'red' },
        { icon: 'mdi-alert-circle-outline', color: 'red' },
        { icon: 'mdi-cash-usd-outline', color: 'green' },
        { icon: 'mdi-alert-circle-outline', color: 'red' },
        { icon: 'mdi-alert-circle-outline', color: 'red' },
        { icon: 'mdi-alert', color: 'red' },
        { icon: 'mdi-check', color: 'teal' }
      ],
      expand: 0,
      dialog: false,
      dialogState: 0,
      stepid: 0,
      file: null,
      window: window,
      reason: '',
      punishCompany: [{ text: '减少进度款', value: 0 }, { text: '延长时间', value: 1 }, { text: '取消', value: 2 }],
      punishStudio: ['扣押金', '延长时间', '取消'],
      punishSelect: '减少进度款',
      progress: 0
    }
  },
  methods: {
    upload () {
      let fd = new FormData()
      fd.append('id', this.id)
      fd.append('action', 6)
      fd.append('file', this.file)
      fd.append('stepid', this.stepid)
      axios.post('/Platform/studio_action', fd)
        .then(response => {
          if (response.data === 'success') {
            this.data[this.stepid].state += 1
            this.dialog = false
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
    },
    getName (item, i) {
      if (item.state === 1) {
        this.expand = i
        this.progress = i / this.data.length * 100
      }
      return item.name
    }
  }
}
</script>
