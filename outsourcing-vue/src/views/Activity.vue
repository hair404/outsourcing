<template>
  <v-card style="margin:5% auto" width="1000" fixed>
    <v-row>
      <v-dialog v-model="dialog" max-width="600px">
        <template v-slot:activator="{ on }">
          <v-btn
            color="light-blue"
            icon
            text
            v-on="on"
            @click="reSet"
            class="ml-5 mt-2"
            ><v-icon>mdi-plus</v-icon></v-btn
          >
        </template>
        <v-card>
          <v-btn color="grey" text @click="dialog = false" style="float:right;">
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-card-title>
            <span class="headline" style="float:left;">添加活动</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-form ref="form">
                <v-text-field
                  label="活动名称*"
                  v-model="addItem.name"
                  :rules="rule"
                  color="light-blue"
                  :error="error.name"
                ></v-text-field>
                <v-text-field
                  label="活动地址*"
                  v-model="addItem.address"
                  color="light-blue"
                  :rules="rule"
                  :error="error.address"
                ></v-text-field>
                <v-text-field
                  label="操作*"
                  v-model="addItem.opera"
                  color="light-blue"
                  :rules="rule"
                  :error="error.opera"
                ></v-text-field>
              </v-form>
            </v-container>
            <small>*表示必填字段</small>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="light-blue" outlined @click="save">提交</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-spacer></v-spacer>
      <v-btn class="mr-5 mt-2" @click="refresh" color="light-blue" text icon
        ><v-icon>mdi-cached</v-icon>
      </v-btn>
    </v-row>
    <v-data-table
      :headers="headers"
      :items="activity"
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
      dialog: false,
      length: 0,
      activity: [],
      loading: false,
      options: {},
      addItem: { name: '', address: '', opera: '' },
      error: { name: false, address: false, opera: false },
      rule: [v => !!v || 'This is required'],
      headers: [
        {
          text: '序号',
          align: 'center',
          sortable: true,
          value: 'id'
        },
        {
          text: '活动名',
          align: 'center',
          value: 'name',
          sortable: false
        },
        {
          text: '地址',
          align: 'center',
          value: 'address',
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
    this.activity = [
      {
        id: 1,
        name: 'xxxxx',
        address: '浙江省杭州市江干区',
        opera: 'xxxxx'
      }
    ]
    this.length = 1
  },
  methods: {
    refresh() {
      this.axios
        .post('http://localhost:8080/#/Manage/Activity')
        .then(response => {
          if (response.data) {
            this.activity = response.data
          }
        })
        .catch(error => {
          console.log(error)
          this.snackbar.color = 'error'
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
    },
    save() {
      let params = {
        name: this.addItem.name,
        address: this.addItem.address,
        opera: this.addItem.opera
      }
      if (
        (params.name !== '') &
        (params.address !== '') &
        (params.opera !== '')
      ) {
        this.axios
          .post(
            'http://localhost:8080/#/Manage/Activity',
            this.qs.stringify(params)
          )
          .then(response => {
            if (response.data) {
              this.activity = response.data
            }
          })
          .catch(error => {
            console.log(error)
            this.snackbar.color = 'error'
            this.snackbar.text = '服务器错误'
            this.snackbar.open = true
          })
        this.dialog = false
      } else {
        if (params.name === '') {
          this.snackbar.color = 'error'
          this.snackbar.text = '活动名称不能为空'
          this.snackbar.open = true
          this.error.name = true
        } else if (params.address === '') {
          this.snackbar.color = 'error'
          this.snackbar.text = '活动地址不能为空'
          this.snackbar.open = true
          this.error.address = true
        } else if (params.opera === '') {
          this.snackbar.color = 'error'
          this.snackbar.text = '操作不能为空'
          this.snackbar.open = true
          this.error.opera = true
        }
      }
    },
    reSet() {
      this.$refs.form.resetFields()
    }
  }
}
</script>
