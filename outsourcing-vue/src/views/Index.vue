<template>
  <v-app style="background: rgb(246, 246, 246)">

    <v-snackbar
      :color="snackbar.color"
      v-model="snackbar.open"
    >
      {{ snackbar.text }}
      <v-btn
        text
        @click="snackbar.open = false"
      >
        关闭
      </v-btn>
    </v-snackbar>

    <Nav
      :isLoged="infoLoaded"
      :nick="info.username"
      :img="info.avatar"
      :ctg="ctg"
      @keyword="search"
      :isBack="isBack"
    />
    <div class="d-block d-sm-none">
      <div style="height: 15px;width: 100%" />
    </div>
    <Ctg
      class="d-none d-sm-flex"
      :ctg="ctg"
    />
    <v-content>
      <keep-alive include="search,detail,show">
        <router-view
          :info="info"
          :infoLoaded="infoLoaded"
          :keyword="keyword"
          :type_="typeEmit"
          :ctg_="ctgEmit"
          :subctg_="subctgEmit"
          :snackbar="snackbar"
        />
      </keep-alive>
    </v-content>
  </v-app>
</template>

<script>
import axios from 'axios'
import Nav from '../components/Nav'
import Ctg from '../components/Ctg'
import utils from '../js/utils'

export default {
  components: {
    Nav,
    Ctg
  },
  data () {
    return {
      info: {},
      infoLoaded: false,
      isBack: false,
      ctg: utils.ctg,
      typeEmit: 0,
      ctgEmit: 0,
      subctgEmit: 0,
      keyword: '',
      snackbar: {
        open: false,
        color: 'primary',
        text: '错误'
      }
    }
  },
  methods: {
    loadinfo () {
      axios
        .post(this.utils.baseURL + '/info', 'type=basic')
        .then(response => {
          if (!(response.data === 'NotLogin')) {
            this.infoLoaded = true
            this.info = response.data
            this.$goEasy.subscribe({
              channel: String(response.data.id), // 替换为您自己的channel
              onMessage: function (message) {
                var notification = new Notification(JSON.parse(message.content).title, {
                  body: JSON.parse(message.content).content
                })
                notification.onclick = function () {
                  this.$router.push(message.content.url)
                }
              }
            })
            Notification.requestPermission(function (permission) {
              if (permission === 'granted') {
                console.log(11)
              }
            })
          }
        })
        .catch(error => { console.log(error) })
    },
    search (a) {
      if (typeof a === 'number')
        this.typeEmit = a
      else {
        this.keyword = a[0]
        this.typeEmit = 0
        this.ctgEmit = a[1]
        this.subctgEmit = a[2]
      }
    }
  },
  created () {
    utils.refreshCtg()
    this.loadinfo()
    if (this.$route.params.keyword)
      this.keyword = this.$route.params.keyword
  },
  watch: {
    $route: function (newV) {
      if (newV.path.includes('/detail'))
        this.isBack = true
      else
        this.isBack = false
    }
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
