<template>
  <v-card style="margin:5% auto" width="1000" fixed>
    <v-row>
      <v-select
        v-model="select"
        :items="types"
        class="mt-3 ml-6"
        color="light-blue"
        label="类型"
        @change="change"
        outlined
      ></v-select>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="关键字"
        class="mt-4 mr-6"
        color="light-blue"
      ></v-text-field>
      <v-btn color="light-blue" class="mt-6 mr-6" @click="lookup" outlined
        >查询</v-btn
      >
    </v-row>
    <v-data-table
      :headers="headers"
      :items="acount"
      :options.sync="options"
      :server-items-length="length"
      :loading="loading"
      show-select
      class="elevation-1"
    ></v-data-table>
  </v-card>
</template>

<script>
export default {
  props: {
    snackbar: Object
  },
  data() {
    return {
      search: '',
      select: 0,
      length: 0,
      acount: [],
      loading: false,
      options: {},
      types: this.utils.getTextValue([
        '全部',
        '首付款',
        '押金',
        '阶段款',
        '广告投放退款',
        '延误罚款'
      ]),
      headers: [
        {
          text: '序号',
          align: 'center',
          sortable: true,
          value: 'id'
        },
        {
          text: '金额',
          align: 'center',
          value: 'money',
          sortable: false
        },
        {
          text: '用途',
          align: 'center',
          value: 'usage',
          sortable: false
        },
        {
          text: '付款方',
          align: 'center',
          value: 'payer',
          sortable: false
        },
        {
          text: '收款方',
          align: 'center',
          value: 'beneficiary',
          sortable: false
        },
        {
          text: '操作',
          align: 'center',
          value: 'opera',
          sortable: false
        }
      ]
    }
  },
  created() {
    this.acount = [
      {
        id: 1,
        money: '$12345',
        usage: 'xxxxx',
        payer: 'xxxxx',
        beneficiary: 'xxxxx',
        opera: 'xxxxx'
      }
    ]
    this.length = 1
  },
  methods: {
    change() {
      let params = {
        type: this.types[this.select]
      }
      this.axios
        .post(
          'http://localhost:8080/#/Manage/Advertise',
          this.qs.stringify(params)
        )
        .then(response => {
          if (response.data) {
            this.users = response.data
          }
        })
        .catch(error => {
          console.log(error)
          this.snackbar.color = 'error'
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
    },
    lookup() {
      let params = {
        info: this.search
      }
      this.axios
        .post('http://localhost:8080/#/Manage/Users', this.qs.stringify(params))
        .then(response => {
          if (response.data) {
            this.users = response.data
          }
        })
        .catch(error => {
          console.log(error)
          this.snackbar.color = 'error'
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
    }
  }
}
</script>
