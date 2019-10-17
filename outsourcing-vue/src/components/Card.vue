<template>
  <div
    name="project"
    v-on:click="open('www.baidu.com?id='+id)"
    class="card net-cell"
    style="position: relative;cursor: pointer;"
  >
    <div
      class="account"
      style="height: 10%;width: 80%;padding: 10%;"
    >
      <img
        :src=img
        style="float: left"
      >
      <div style="line-height: initial;max-width: 100px;"></div>
    </div>
    <div style="padding-left: 20%;height: fit-content;"></div>
    <div style="margin-left: 5%;position: absolute;width: 90%;bottom: 2%;">
      <div style="text-align: center;"></div>
      <div style="color: black;text-align: center;"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Card',
  props: ['id', 'img'],
  methods: {
    open (url) {
      window.open(url)
    }
  }
}
</script>
