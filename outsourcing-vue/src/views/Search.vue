<template>
  <div>
    <Nav></Nav>
    <Ctg></Ctg>
    <router-view />
  </div>
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
