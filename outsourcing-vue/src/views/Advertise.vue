<template>
  <v-card style="margin:5% auto" width="1000" fixed>
    <v-row>
      <v-select
        v-model="select"
        class="mt-3 ml-6"
        color="light-blue"
        label="类型"
        outlined
        :items="types"
        @change="change"
      ></v-select>
      <v-spacer></v-spacer>
      <v-text-field
        label="关键字"
        append-icon="magnmdi-magnify"
        v-model="search"
        class="mt-4 mr-6"
        color="light-blue"
      >
      </v-text-field>
      <v-btn
        label="搜索"
        color="light-blue"
        outlined
        @click="lookup"
        class="mt-6 mr-6"
        >查询</v-btn
      >
    </v-row>
    <v-data-table
      :headers="headers"
      :items="advertise"
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
      advertise: [],
      loading: false,
      options: {},
      types: this.utils.getTextValue(['全部', '企业', '工作室']),
      headers: [
        {
          text: '序号',
          value: 'id',
          align: 'center',
          sortable: true
        },
        {
          text: '名字',
          value: 'name',
          align: 'center',
          sortable: false
        },
        {
          text: '金额',
          value: 'money',
          align: 'center',
          sortable: false
        },
        {
          text: '类型',
          value: 'type',
          align: 'center',
          sortable: false
        },
        {
          text: '状态',
          value: 'state',
          align: 'center',
          sortable: false
        },
        {
          text: '操作',
          value: 'opera',
          align: 'center',
          sortable: false
        }
      ]
    }
  },
  created() {
    this.advertise = [
      {
        id: '1',
        name: 'xxxx',
        money: '￥15662',
        type: '工作室',
        state: 0,
        opera: 'xxxx'
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
