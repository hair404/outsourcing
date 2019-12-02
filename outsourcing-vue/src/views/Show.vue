<template>
  <div class="wrapper">
    <v-card class="mx-auto my-2">
      <v-img
        class="white--text align-end"
        height="200"
        :src="utils.baseURL + displayinfo.img"
      >
        <v-icon
          color="grey"
          class="ml-4 my-4"
          style="position: absolute;top: 0px;"
          @click="$router.back()"
        >mdi-arrow-left</v-icon>
        <v-card-title>
          <v-avatar>
            <img :src="utils.baseURL + displayinfo.avatar" />
          </v-avatar>
          {{displayinfo.username}}
        </v-card-title>
      </v-img>
      <v-tabs v-model="tab">
        <v-tab>他的信息</v-tab>
        <v-tab>已完成项目</v-tab>
        <v-tab v-if="displayinfo.bid">正在竞标的项目</v-tab>
        <v-tab v-if="!displayinfo.bid">工作室成员</v-tab>
      </v-tabs>
    </v-card>
    <v-tabs-items v-model="tab">

      <v-tab-item>
        <v-card
          class="mx-auto"
          max-height="900"
        >
          <v-card-title class="headline mb-1">公司信息</v-card-title>
          <v-divider></v-divider>
          <v-list>
            <v-list-item>
              <v-list-item-content>联系电话</v-list-item-content>
              <v-list-item-icon>{{displayinfo.tel}}</v-list-item-icon>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>他的邮箱</v-list-item-content>
              <v-list-item-icon>{{displayinfo.email}}</v-list-item-icon>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>他的简介</v-list-item-content>
              <v-list-item-action>{{displayinfo.info}}</v-list-item-action>
            </v-list-item>
          </v-list>
        </v-card>
      </v-tab-item>

      <v-tab-item>
        <LoadCard
          v-if="isloaded"
          :isLoaded="isloaded"
          :cardsProp="complete"
          number="12"
          type="1"
          :totalProps="completeNo"
        ></LoadCard>
      </v-tab-item>

      <v-tab-item>
        <LoadCard
          :isLoaded="isloaded"
          :cardsProp="bid"
          number="12"
          type="1"
          :totalProps="bidNo"
        ></LoadCard>
      </v-tab-item>

      <v-tab-item>
        <Table
          ref="table"
          :data="member"
          :headers="headers"
          itemkey="id"
          issort
          isCompany
        ></Table>
      </v-tab-item>

    </v-tabs-items>
  </div>
</template>

<script>
import axios from 'axios'
import LoadCard from '../components/LoadCard'

export default {
  name: 'show',
  components: { LoadCard },
  data () {
    return {
      displayinfo: {},
      complete: [],
      completeNo: 0,
      bid: [],
      bidNo: 0,
      isloaded: false,
      tab: 0,
      headers: [{ text: '名字', value: 'name' }, { text: '介绍', value: 'info' }, { text: '邮箱', value: 'email' }, { text: '电话', value: 'tel' }],
      member: []
    }
  },
  methods: {
    loaddisplayinfo () {
      axios
        .post(this.utils.baseURL + '/display_info', 'id=' + this.$route.params.id + '&first=1')
        .then(response => {
          this.displayinfo = response.data
          this.complete = response.data.complete ? response.data.complete.slice(1) : []
          this.completeNo = response.data.complete ? response.data.complete[0] : 0
          this.bid = response.data.bid ? response.data.bid.slice(1) : []
          this.bidNo = response.data.bid ? response.data.bid[0] : 0
          this.isloaded = true
        })
        .catch(error => { console.log(error) })

      axios.post(this.utils.baseURL + '/member', 'id=' + this.$route.params.id).then(response => {
        this.member = response.data
      }).catch(error => { console.log(error) })
    }
  },
  created () {
    this.loaddisplayinfo()
  }
}
</script>

<style>
a {
  color: black !important;
}

ul {
  padding: 0px !important;
}
</style>
