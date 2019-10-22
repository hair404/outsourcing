<template>
  <v-app>
    <Nav></Nav>
    <Ctg></Ctg>
    <router-view />
  </v-app>
</template>

<script>
import Nav from '../components/Nav'
import Ctg from '../components/Ctg'

export default {
  components: {
    Nav,
    Ctg
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