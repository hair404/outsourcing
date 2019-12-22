<template>
  <v-row style="margin:5% 10%" fixed>
    <v-col>
      <v-card class="mx-auto" max-width="240">
        <v-list>
          <v-list-item-group v-model="select" mandatory color="light-blue">
            <v-list-item v-for="(item, i) in items" :key="i">
              <v-list-item-content>
                <v-list-item-title v-text="item.name"></v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-item-group>
        </v-list>
        <v-dialog v-model="dialog_1" max-width="600px">
          <template v-slot:activator="{ on }">
            <v-btn
              width="100%"
              color="light-blue"
              v-on="on"
              text
              class="title"
              @click="reSet"
              >+</v-btn
            >
          </template>
          <v-card>
            <v-btn
              color="grey"
              text
              @click="dialog_1 = false"
              style="float:right;"
              ><v-icon>mdi-close</v-icon>
            </v-btn>
            <v-card-title>
              <span class="headline">添加项目</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-form ref="form">
                  <v-text-field
                    label="父项目*"
                    v-model="addItem.name"
                    color="light-blue"
                    :rules="rule"
                    :error="error"
                  ></v-text-field>
                  <v-text-field
                    label="子项目"
                    v-model="addItem.subctg"
                    color="light-blue"
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
      </v-card>
    </v-col>
    <v-col>
      <v-card class="mx-1" max-width="240">
        <v-list v-if="items[select]">
          <v-list-item-group mandatory color="light-blue">
            <v-list-item v-for="(sub, i) in items[select].subctg" :key="i">
              <v-list-item-content>{{ sub }}</v-list-item-content>
            </v-list-item>
          </v-list-item-group>
        </v-list>
        <v-dialog v-model="dialog_2" max-width="600px">
          <template v-slot:activator="{ on }">
            <v-btn width="100%" color="light-blue" text class="title" v-on="on"
              >+</v-btn
            >
          </template>
          <v-card>
            <v-btn
              color="grey"
              text
              @click="dialog_2 = false"
              style="float:right;"
              ><v-icon>mdi-close</v-icon>
            </v-btn>
            <v-card-title>
              <span class="headline">添加子项目</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-form ref="form">
                  <v-text-field
                    label="父项目"
                    v-model="items[select].name"
                    disabled
                  ></v-text-field>
                  <v-text-field
                    label="子项目"
                    v-model="addItem.subctg"
                    color="light-blue"
                    :rules="rule"
                  ></v-text-field>
                </v-form>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="light-blue" outlined @click="saveSub">提交</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-card>
    </v-col>
  </v-row>
</template>
<script>
export default {
  props: {
    snackbar: Object
  },
  data() {
    return {
      dialog_1: false,
      dialog_2: false,
      select: 0,
      error: false,
      rule: [v => !!v || 'This is required'],
      addItem: { name: '', subctg: '' },
      items: [
        {
          name: '网站开发',
          subctg: ['前端开发', '网站维护']
        },
        {
          name: '移动应用开发',
          subctg: ['安卓APP', '苹果APP']
        },
        {
          name: 'H5开发',
          subctg: ['H5模板', 'H5定制']
        },
        {
          name: 'UI设计',
          subctg: ['网站UI', '移动UI']
        },
        { name: '测试运维' },
        { name: '云服务' },
        { name: 'IT综合服务' }
      ]
    }
  },
  methods: {
    save() {
      let params = {
        itemName: this.addItem.name,
        subctg: this.addItem.subctg
      }
      if (params.itemName !== '') {
        this.axios
          .post(
            'http://localhost:8080/#/Manage/Classification',
            this.qs.stringify(params)
          )
          .then(response => {
            if (response.data) {
              this.items = response.data
            }
          })
          .catch(error => {
            console.log(error)
            this.snackbar.color = 'error'
            this.snackbar.text = '服务器错误'
            this.snackbar.open = true
          })
        this.dialog_1 = false
      } else {
        this.snackbar.color = 'error'
        this.snackbar.text = '父项目不能为空'
        this.snackbar.open = true
        this.error = true
      }
    },
    saveSub() {
      let params = {
        itemName: this.items[this.select].name,
        subctg: this.addItem.subctg
      }
      this.axios
        .post(
          'http://localhost:8080/#/Manage/Classification',
          this.qs.stringify(params)
        )
        .then(response => {
          if (response.data) {
            this.items = response.data
          }
        })
        .catch(error => {
          console.log(error)
          this.snackbar.color = 'error'
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
      this.dialog_2 = false
      this.addItem.subctg = ''
    },
    reSet() {
      this.$refs.form.resetFields()
    }
  }
}
</script>
