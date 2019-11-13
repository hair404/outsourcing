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
            filled
            label="原因"
          />
        </v-card-text>
        <v-card-actions>
          <v-btn
            color="primary"
            text
            @click="$emit('submit',7,{reason:reason},()=>{dialog = false})"
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
            <v-text-field
              v-if="punishSelect === 0"
              v-model="money"
              :rules="v => v < 100 && v > 0 || 必须小于100大于0"
              type="Number"
              label="金额百分比"
              required
            />%
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-btn
            color="primary"
            text
            @click="$emit('submit',9,{measure:punishSelect,money:money},()=>{dialog = false})"
          >提交</v-btn>
        </v-card-actions>
      </v-card>

      <v-card v-if="dialogState === 3">
        <v-card-title>我要申诉</v-card-title>
        <v-card-text>
          <v-text-field
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
            @click="$emit('submit',type === 0 ? 7 : 8,{reason:reason,money:money},()=>{dialog = false})"
          >提交</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-expansion-panels
      v-model="expand"
      accordion
    >

      <v-expansion-panel
        v-for="(item,i) in data"
        :key="i"
      >
        <v-expansion-panel-header disable-icon-rotate>
          {{(item.state === 1?expand = i:true)&&item.name}}
          <template v-slot:actions>
            <div class="text--secondary mr-4">截止时间：{{item.time}}</div>
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
              @click.stop="(dialog = true) && (stepid = item.part)"
            >上传文件</v-btn>
            <v-btn
              v-if="item.state === 1 && type === 1"
              text
              color="primary"
              class="mr-4"
              @click.stop="(dialogState = 1) && (dialog = true)"
            >我无法准时完成！</v-btn>
            <v-btn
              v-if="item.state === 2 && type === 0"
              outlined
              color="primary"
              class="mr-4"
              @click="$emit('submit',8,{stepid:item.part},()=>{dialog = false})"
            >通过</v-btn>
            <v-btn
              v-if="item.state === 2 && type === 0"
              outlined
              color="error"
              class="mr-4"
              @click="(dialogState = 2) && (dialog = true)"
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
              @click="(dialogState = 2) && (dialog = true)"
            >进行处罚</v-btn>

            <v-btn
              v-if="item.state === 4 && type === 1"
              outlined
              color="error"
              class="mr-4"
              @click="(dialogState = 3) && (dialog = true)"
            >不合理，申诉</v-btn>

            <v-btn
              v-if="item.state === (5 || 6) && type === 0"
              outlined
              color="green"
              class="mr-4"
              @click="$emit('submit',6,{stepid:item.part})"
            >付款</v-btn>
            <div
              v-if="item.state === 5 && type === 1"
              class="text--secondary mr-4"
            >等待对方付款</div>

            <v-btn
              v-if="item.state === 6 && type === 1"
              outlined
              color="error"
              class="mr-4"
              @click="(dialogState = 2) && (dialog = true)"
            >要求进行处罚</v-btn>
            <v-btn
              v-if="item.state === 6 && type === 1"
              outlined
              color="primary"
              class="mr-4"
              @click="$emit('submit',10)"
            >催促公司付款</v-btn>

            <v-btn
              v-if="item.state === 7 && type === 0"
              outlined
              color="error"
              class="mr-4"
              @click="(dialogState = 3) && (dialog = true)"
            >不合理，申诉</v-btn>
          </div>
        </v-expansion-panel-content>
      </v-expansion-panel>

    </v-expansion-panels>
  </div>
</template>

<script>
import Axios from 'axios'

export default {
  props: {
    data: Array,
    type: Number,
    id: Number,
    snackbar: Boolean
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
      reason: '',
      punishCompany: ['减少进度款', '延长时间', '取消'],
      punishStudio: ['扣押金', '延长时间', '取消'],
      punishSelect: 0,
      vaild: true
    }
  },
  methods: {
    upload () {
      let fd = new FormData()
      fd.append('id', this.id)
      fd.append('action', 6)
      fd.append('file', this.file)
      Axios.post('studio_action', fd)
        .then(response => {
          if (this.response.data === 'success')
            ;
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
  }
}
</script>
