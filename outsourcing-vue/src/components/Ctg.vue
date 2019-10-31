<template>
  <div
    class="wrapper"
    style="height: 42px;"
  >
    <div class="categories nav">
      <ul id="ctg">
        <li
          v-for="(item,i) in ctg"
          :key="i"
        ><a>{{item.name}}</a>
          <ul
            class="selection card"
            style="width: 100px;position: absolute"
          >
            <li
              v-for="subctg in item.subctg"
              :key="subctg"
            ><a>{{subctg}}</a></li>
          </ul>
        </li>

      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Ctg',
  data () {
    return {
      ctg: [{
        name: '首页'
      },
      {
        name: '网站开发',
        subctg: [
          '前端开发', '网站维护'
        ]
      },
      {
        name: '移动应用开发',
        subctg: ['安卓APP', '苹果APP']
      },
      {
        name: 'H5开发',
        subctg: ['H5模板', 'H5定制']
      },
      {
        name: 'UI设计',
        subctg: ['网站UI', '移动UI']
      },
      { name: '测试运维' },
      { name: '云服务' },
      { name: 'IT综合服务' }]
    }
  }
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
  line-height: 39px;
}

.categories > ul > li:hover > a {
  color: #00a1d6 !important;
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
  height: 42px;
  transition: 300ms;
}

.nav > ul > li > a,
.nav > ul > div > li > a {
  display: block;
  padding-left: 10px;
  padding-right: 10px;
}

.nav > ul > li:hover,
.nav > ul > div > li:hover {
  cursor: pointer;
  background-color: rgba(255, 255, 255, 0.2);
}
</style>
