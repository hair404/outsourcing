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
<style scoped>
.wrapper {
    width: 60%;
    margin: 0 auto;
    min-width: 400px;
    height: 100%;
}
.categories ul li {
    padding-left: 20px;
    padding-right: 20px;
}

.categories ul li a {
    padding: 0;
    display: inline;
    transition: 300ms;
}

.categories>ul>li:hover>a {
    border-bottom: 3px solid #00a1d6;
}

.categories ul li ul {
    display: none;
    z-index: 20;
}

.categories ul li:hover ul {
    display: block;
}

.selection li:hover {
    transition: 300ms;
    cursor: pointer;
    background-color: rgba(0, 0, 0, 0.1);
}
.selection-blue a:not(.selection-blue-selected):hover {
    color: #00a1d6;
}

.selection-blue-selected {
    background: #00a1d6;
    color: white;
}

.selection-blue-selected>a {
    color: white;
}
@media screen and (min-width: 700px) and (max-width: 1600px) {
    .left-recommend {
        margin: 1%;
        min-width: 0px;
        height: 250px;
    }
    .right-recommend {
        margin: 1%;
        min-width: 0px;
        height: 250px;
    }
    .right-recommend>div {
        height: 250px;
    }
    .right-cell {
        height: 120px;
    }
    .net-cell {
        width: 23%;
        max-width: 300px;
        height: 350px;
        margin: 1%;
    }
    .pro-name {
        max-width: 110px;
    }
}
@media screen and (max-width: 700px) {
    .account>div:not(.card) {
        padding-left: 0;
        width: 50px;
    }
    .account+div {
        margin-top: 5%;
    }
    .left-recommend {
        margin: 1% auto;
    }
    .right-recommend {
        margin: 1% auto;
    }
    .net-cell {
        width: 171px;
        min-width: 0;
        height: 249.3px;
        margin: 1%;
    }
    .container {
        width: 357px;
    }
    .class {
        height: 168px;
    }
    .class>div:nth-child(2) {
        height: 84px;
    }
    .wrapper {
        width: 90%;
    }
    .left-recommend {
        min-width: 400px;
    }
    .right-recommend {
        min-width: 400px;
    }
    .container {
        width: 400px;
    }
    .container>div {
        width: 190px;
        height: 250px;
    }
    .content {
        padding-left: 0px;
    }
    .adjust {
        line-height: 25px;
    }
    .pro-name {
        max-width: 110px;
    }
}
.select-card {
    display: inline-block;
    border-radius: 50px;
    border: 1px solid lightgrey;
    margin-left: 1.4%;
    margin-right: 1.4%;
    text-align: center;
    line-height: 42px;
    transition: 300ms;
    background: white;
}
.nav {
    float: left;
}

.nav ul {
    list-style: none;
}

.nav>ul>li {
    float: left;
    text-align: center;
    line-height: 42px;
    padding-left: 10px;
    padding-right: 10px;
    transition: 200ms;
}

.nav>ul>li:hover {
    cursor: pointer;
    background-color: rgba(255, 255, 255, 0.2);
}

</style>