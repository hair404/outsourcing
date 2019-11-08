<template>
  <v-app style="background: rgb(246, 246, 246)">
    <Nav
      :isLoged="infoLoaded"
      :nick="info.username"
      :img="info.img"
      :ctg="ctg"
      @keyword="search"
    />
    <div class="d-block d-sm-none">
      <div style="height: 21px;width: 100%" />
    </div>
    <Ctg
      class="d-none d-sm-flex"
      :ctg="ctg"
    />
    <v-content>
      <router-view
        :info="info"
        :infoLoaded="infoLoaded"
        :keyword="keyword"
      />
    </v-content>
  </v-app>
</template>

<script>
import Axios from 'axios'
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
      keyword: ''
    }
  },
  methods: {
    loadinfo () {
      Axios
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
