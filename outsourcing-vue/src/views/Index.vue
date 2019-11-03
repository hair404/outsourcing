<template>
  <v-app>
    <Nav
      :isLoged="infoLoaded"
      :nick="info.username"
      :img="info.img"
    />
    <Ctg />
    <router-view
      :info="info"
      :infoLoaded="infoLoaded"
    />
  </v-app>
</template>

<script>
import Axios from 'axios'
import Nav from '../components/Nav'
import Ctg from '../components/Ctg'

export default {
  components: {
    Nav,
    Ctg
  },
  data () {
    return {
      info: {},
      infoLoaded: false
    }
  },
  methods: {
    loadinfo () {
      Axios
        .post('/Platform/info', 'type=basic')
        .then(response => {
          this.infoLoaded = true
          this.info = response.data
        })
        .catch(error => { console.log(error) })
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
