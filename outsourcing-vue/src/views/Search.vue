<template>
  <div>
    <div class="wrapper class">
      <div
        class="nav"
        style="width: 100%;border-top-left-radius: 3px;border-top-right-radius: 3px;"
      >
        <div style="float: left;line-height: 42px;padding-left: 3%;padding-right: 3%">类型</div>
        <ul
          class="selection-blue"
          id="type"
        >
          <li><a>招标</a></li>
          <li><a>工作室</a></li>
        </ul>
      </div>
      <div
        class="nav"
        style="width: 100%;border-radius: 0px"
      >
        <div style="float: left;line-height: 42px;padding-left: 3%;padding-right: 3%">分类</div>
        <ul
          class="selection-blue"
          id="categories"
        >
          <li><a class="selection-blue-selected">全部</a></li>
          <li><a>网站开发</a></li>
          <li><a>移动应用开发</a></li>
          <li><a>H5开发</a></li>
          <li><a>UI设计</a></li>
          <li><a>测试运维</a></li>
          <li><a>云服务</a></li>
          <li><a>IT综合服务</a></li>
        </ul>
      </div>
      <div
        class="nav"
        style="width: 100%;border-bottom-left-radius: 3px;border-bottom-right-radius: 3px;"
      >
        <div style="float: left;line-height: 42px;padding-left: 3%;padding-right: 3%">子类</div>
        <ul
          class="selection-blue"
          id="subcategories"
        >
          <li><a class="selection-blue-selected">全部</a></li>
          <div style="display: none">
            <li><a>前端开发</a></li>
            <li><a>网站维护</a></li>
          </div>
          <div style="display: none">
            <li><a>安卓APP</a></li>
            <li><a>苹果APP</a></li>
          </div>
          <div style="display: none">
            <li><a>H5模板</a></li>
            <li><a>H5定制</a></li>
          </div>
          <div style="display: none">
            <li><a>网站UI</a></li>
            <li><a>移动UI</a></li>
          </div>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
// import Card from '../components/Card'

export default {
  components: {

  }
}
</script>

<style>
.class {
  height: 126px;
  border: 1px lightgray solid;
  margin-top: 10px;
  background-color: white;
  transition: 300ms;
  border-radius: 3px;
}

.class > div {
  height: 42px;
}

.class:hover {
  box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
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
