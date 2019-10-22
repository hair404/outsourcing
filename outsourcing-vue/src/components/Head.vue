<template>
  <div style="width: 60%;height: 42px;margin: 0 auto;">
    <div class="nav">
      <ul>
        <li><a href="./index.html">首页</a></li>
        <li><a href="./search.html?type=0">全部招标</a></li>
        <li><a href="./search.html?type=1">全部工作室</a></li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Head'
}
</script>

<style scoped lang="scss">
a {
  text-decoration: none;
  outline: none;
  color: #000000;
}

.nav {
  float: left;
}

.nav ul {
  list-style: none;
}

.nav > ul > li {
  float: left;
  text-align: center;
  line-height: 42px;
  padding-left: 10px;
  padding-right: 10px;
  transition: 200ms;
}

.nav > ul > li:hover {
  cursor: pointer;
  background-color: rgba(255, 255, 255, 0.2);
}
</style>
