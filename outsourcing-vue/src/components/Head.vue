<template>
  <div style="width: 60%;height: 42px;margin: 0 auto;">
    <div class="nav">
      <ul>
        <li><a @click='$router.push("../views/Index")'>首页</a></li>
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
<style scoped>
.card {
    display: block;
    background-color: white;
    transition: 300ms;
    border-radius: 3px;
    box-shadow: rgba(0, 0, 0, .2) 0 1px 5px 0px;
}
.card:hover {
    box-shadow: rgba(0, 0, 0, .2) 0 1px 3px 2px;
}
.head {
    height: 42px;
    position: relative;
    z-index: 200;
    background-color: transparent;
}

.blur {
    position: absolute;
    overflow: hidden;
    height: 100%;
    width: 100%;
    z-index: -2;
    background-image: url('../assets/head.jpg');
    filter: blur(4px);
}

.cover {
    position: absolute;
    height: 100%;
    width: 100%;
    z-index: -1;
    transition: 300ms;
    background-color: rgba(255, 255, 255, 0.2);
    box-shadow: rgba(0, 0, 0, 0.2) 0px 1px 1px;
}

.cover:hover {
    box-shadow: rgba(0, 0, 0, 0.2) 0px 3px 6px;
}
.nav {
    float: left;
}

.nav ul {
    list-style: none;
}

.nav>ul>li, .nav>ul>div>li {
    float: left;
    text-align: center;
    line-height: 42px;
    transition: 300ms;
}

.nav>ul>li>a, .nav>ul>div>li>a {
    display: block;
    padding-left: 10px;
    padding-right: 10px;
}

.nav>ul>li:hover, .message:hover, .nav>ul>div>li:hover {
    cursor: pointer;
    background-color: rgba(255, 255, 255, 0.2);
}

.account {
    padding-left: 10px;
    padding-right: 10px;
    height: 100%;
    transition: 300ms;
}

.account>div:not(.card) {
    line-height: 42px;
    padding-right: 10px;
    float: left;
    padding-left: 10px;
}

.account:hover {
    cursor: pointer;
    background-color: rgba(255, 255, 255, 0.2);
}

.account img {
    float: right;
    max-width: 35px;
    margin: 3.5px;
    border-radius: 50px;
}

.message {
    float: right;
    line-height: 42px;
    padding-left: 10px;
    padding-right: 10px;
}

.info {
    display: none;
    width: 210px;
    height: 210px;
    position: absolute;
    top: 42px;
    transform: translate(-25%, 0);
}

.logo {
    position: absolute;
    left: 20%;
    top: 40%;
    z-index: 200;
}
._search {
    position: absolute;
    right: 20%;
    top: 70%;
    z-index: 200;
    width: 200px;
    height: 30px;
    border: 0px;
    background: rgba(255, 255, 255, 0.8);
    border-radius: 5px;
    transition: 300ms;
}

._search input {
    float: left;
    height: 100%;
    width: 170px;
    border: 0px;
    background: transparent;
}

._search:hover {
    background: rgba(255, 255, 255, 0.9);
    box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px;
}
</style>
