<template>
  <div
    class="wrapper"
    style="height: 42px;"
  >
    <div class="categories nav">
      <ul id="ctg">
        <li><a>首页</a></li>
        <li><a>网站开发</a>
          <ul
            class="selection card"
            style="width: 100px;position: absolute;"
          >
            <li><a>前端开发</a></li>
            <li><a>网站维护</a></li>
          </ul>
        </li>
        <li><a>移动应用开发</a>
          <ul
            class="selection card"
            style="width: 100px;position: absolute;"
          >
            <li><a>安卓APP</a></li>
            <li><a>苹果APP</a></li>
          </ul>
        </li>
        <li><a>H5开发</a>
          <ul
            class="selection card"
            style="width: 100px;position: absolute;"
          >
            <li><a>H5模板</a></li>
            <li><a>H5定制</a></li>
          </ul>
        </li>
        <li><a>UI设计</a>
          <ul
            class="selection card"
            style="width: 100px;position: absolute;"
          >
            <li><a>网站UI</a></li>
            <li><a>移动UI</a></li>
          </ul>
        </li>
        <li><a>测试运维</a></li>
        <li><a>云服务</a></li>
        <li><a>IT综合服务</a></li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Ctg'
}
</script>

<style lang="scss">
.categories ul li {
  padding-left: 20px;
  padding-right: 20px;
}

.categories ul li a {
  padding: 0;
  display: inline;
  transition: 300ms;
}

.categories > ul > li:hover > a {
  border-bottom: 3px solid #00a1d6;
}

.categories ul li ul {
  display: none;
  z-index: 20;
}

.categories ul li:hover ul {
  display: block;
}
.nav {
  float: left;
}

.nav ul {
  list-style: none;
}

.nav > ul > li,
.nav > ul > div > li {
  float: left;
  text-align: center;
  line-height: 42px;
  transition: 300ms;
}

.nav > ul > li > a,
.nav > ul > div > li > a {
  display: block;
  padding-left: 10px;
  padding-right: 10px;
}

.nav > ul > li:hover,
.message:hover,
.nav > ul > div > li:hover {
  cursor: pointer;
  background-color: rgba(255, 255, 255, 0.2);
}
</style>
