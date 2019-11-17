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
      :img="info.img"
      :ctg="ctg"
      @keyword="search"
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
      ctg: utils.ctg,
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
        .post('/Platform/info', 'type=basic')
        .then(response => {
          if (!(response.data === 'NotLogin')) {
            this.infoLoaded = true
            this.info = response.data
          }
        })
        .catch(error => { console.log(error) })
    },
    search (keyword) {
      this.keyword = keyword
    }
  },
  created () {
    this.loadinfo()
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
