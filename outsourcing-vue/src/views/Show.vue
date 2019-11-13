<template>
  <div class="wrapper">
    <v-card class="mx-auto my-2">
      <v-img
        class="white--text align-end"
        height="200"
        :src="'Platform/img/' + displayinfo.img"
      >
        <v-card-title>
          <v-avatar>
            <img :src="'Platform/img/' + displayinfo.avatar" />
          </v-avatar>
          {{displayinfo.username}}
        </v-card-title>
      </v-img>
      <v-tabs v-model="tab">
        <v-tab>他的信息</v-tab>
        <v-tab>已完成项目</v-tab>
        <v-tab v-if="displayinfo.bid">正在竞标的项目</v-tab>
      </v-tabs>
    </v-card>
    <v-tabs-items v-model="tab">

      <v-tab-item>
        <v-card
          class="mx-auto"
          max-height="900"
        >
          <v-list-item-content>
            <div style="padding:16px">
              <v-list-item-title class="headline mb-1">公司信息</v-list-item-title>
            </div>
            <v-divider></v-divider>
            <div style="padding:16px;">
              <v-list-item-subtitle style="font-size:20px">联系电话:{{displayinfo.tel}}</v-list-item-subtitle>
              <br />
              <v-list-item-subtitle style="font-size:20px">公司邮箱:{{displayinfo.email}}</v-list-item-subtitle>
              <br />
              <v-list-item-subtitle style="font-size:20px">公司简介:{{displayinfo.info}}</v-list-item-subtitle>
            </div>
          </v-list-item-content>
        </v-card>
      </v-tab-item>

      <v-tab-item>
        <LoadCard
          v-if="isloaded"
          :isLoaded="isloaded"
          :cardsProp="complete"
          number="20"
          type="1"
          :totalProps="completeNo"
        ></LoadCard>
      </v-tab-item>

      <v-tab-item>
        <LoadCard
          :isLoaded="isloaded"
          :cardsProp="bid"
          number="20"
          type="1"
          :totalProps="bidNo"
        ></LoadCard>
      </v-tab-item>

    </v-tabs-items>
  </div>
</template>

<script>
import Axios from 'axios'
import LoadCard from '../components/LoadCard'

export default {
  components: { LoadCard },
  data () {
    return {
      displayinfo: {},
      complete: [],
      completeNo: 0,
      bid: [],
      bidNo: 0,
      isloaded: false,
      tab: 0
    }
  },
  methods: {
    loaddisplayinfo () {
      Axios
        .post('/Platform/display_info', 'id=' + this.$route.params.id)
        .then(response => {
          this.displayinfo = response.data
          this.complete = response.data.complete ? response.data.complete.slice(1) : []
          this.completeNo = response.data.complete ? response.data.complete[0] : 0
          this.bid = response.data.bid ? response.data.bid.slice(1) : []
          this.bidNo = response.data.bid ? response.data.bid[0] : 0
          this.isloaded = true
        })
        .catch(error => { console.log(error) })
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
