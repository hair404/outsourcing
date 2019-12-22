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
      :items="users"
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
      users: [],
      loading: false,
      options: {},
      types: this.utils.getTextValue([
        '全部',
        '等待认证为用户',
        '等待学生认证'
      ]),
      headers: [
        {
          text: '序号',
          align: 'center',
          sortable: true,
          value: 'id'
        },
        {
          text: '用户名',
          align: 'center',
          value: 'username',
          sortable: false
        },
        {
          text: '类型',
          align: 'center',
          value: 'type',
          sortable: false
        },
        {
          text: '状态',
          align: 'center',
          value: 'state',
          sortable: false
        }
      ]
    }
  },
  created() {
    this.users = [
      {
        id: 1,
        username: 'abc',
        type: '等待认证为用户',
        state: '等待'
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
