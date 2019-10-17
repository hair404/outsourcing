<template>
  <div>
    <div
      id="notify"
      class="card notify"
      style="display: none;"
    ></div>
    <div class="top">
      <div class="head">
        <div class="blur"></div>
        <div class="cover"></div>
        <div
          class="wrapper"
          style="overflow:hidden"
        >
          <img>
          <div class="nav">
            <ul>
              <li><a href="./index.html">首页</a></li>
              <li><a href="./search.html?type=0">全部招标</a></li>
              <li><a href="./search.html?type=1">全部工作室</a></li>
            </ul>
          </div>
          <div style="float: right;height: 42px;padding-right: 10px;">
            <div
              class="account"
              onmouseover="display(0);"
              id="person"
            >
              <div>游客</div>
              <img id="personalPic">
            </div>
            <div
              class="card info"
              onmouseleave="display(1)"
            >
              <div
                class="card"
                style="height: 84px;"
                id="info"
              >
                <div style="line-height: 42px;text-align: center;">您是：游客</div>
                <div style="line-height: 42px;text-align: center;"></div>
              </div>
              <ul
                id="option"
                class="selection"
                style="width: 100%;list-style: none;"
              >
                <li style="line-height: 42px;text-align: center;"><a>我的项目</a></li>
                <li style="line-height: 42px;text-align: center;"><a>我的收藏</a></li>
                <li style="line-height: 42px;text-align: center;"><a>登录</a></li>
              </ul>
            </div>
          </div>
          <div class="message">消息</div>
        </div>
      </div>
      <v-parallax
        height="170"
        src="../assets/head_bkgrd.jpg"
        style="margin-top:-42px"
      >
        <div class="logo"><img src="../assets/logo2.png"></div>
        <form
          id="search"
          class="_search"
        >
          <input
            type="text"
            placeholder="你想搜索"
          >
          <button
            type="submit"
            style="background: transparent;border: transparent;"
          ><i
              class="material-icons search"
              style="line-height: 30px;color: #00a1d6;cursor: pointer;"
            ></i></button>
        </form>
      </v-parallax>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Nav'
}
</script>

<style scoped lang="scss">
.notify {
  position: fixed;
  bottom: -64px;
  right: 10px;
  width: fit-content;
  background-color: #212121;
  color: white;
  line-height: 64px;
  padding-left: 20px;
  padding-right: 20px;
  z-index: 200;
  transition: 300ms;
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
  background-image: url("../assets/head.jpg");
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

.account {
  padding-left: 10px;
  padding-right: 10px;
  height: 100%;
  transition: 300ms;
}

.account > div:not(.card) {
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
.background {
  margin-top: -42px;
  position: relative;
  height: 170px;
  z-index: 9;
}
</style>
