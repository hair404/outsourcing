<template>
  <div>
    <div class="head">
      <div style="overflow: hidden;width: 100%;height: 42px;position: absolute;z-index: -1">
        <img
          src="../assets/head.jpg"
          class="blur"
        ></div>
      <div class="cover"></div>
      <div
        class="wrapper"
        style="overflow:hidden"
      >
        <div class="nav d-none d-sm-flex">
          <ul>
            <li
              v-for="(item,i) in menu"
              :key="i"
              v-ripple
            >
              <router-link :to="{name:item.path,params:{type:i - 1}}">{{item.name}}</router-link>
            </li>
          </ul>
        </div>
        <v-icon
          class="nav d-flex d-sm-none"
          style="line-height:42px;"
          color="black"
          @click="drawer = !drawer"
        >mdi-menu</v-icon>
        <div
          v-if="!isLoged"
          style="float: right;line-height: 42px;"
          @click="$router.push({path:'/Login'})"
        >游客</div>
        <div
          v-else
          style="float: right;height: 42px;"
        >
          <div
            class="nav d-none d-sm-flex"
            style="float: right;height: 42px;"
          >
            <div class="account">
              <div>{{nick}}</div>
              <img :src="'/Platform'+img" />
            </div>
          </div>
          <div class="message">收藏</div>
          <div class="message">消息</div>
        </div>
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
        @submit.prevent="search"
      >
        <input
          type="text"
          v-model="keywords"
          placeholder="你想搜索"
        >
        <button
          type="submit"
          style="background: transparent;border: transparent;"
        >
          <v-icon
            color="primary"
            style="line-height: 30px;cursor: pointer;"
          >mdi-magnify</v-icon>
        </button>
      </form>
    </v-parallax>
    <v-navigation-drawer
      v-model="drawer"
      absolute
      temporary
    >
      <v-list-item>
        <v-list-item-avatar>
          <v-img :src="'/Platform'+img"></v-img>
        </v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title>{{nick}}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-divider />
      <v-list shaped>
        <v-list-item-group
          v-model="menuSelected"
          color="primary"
        >
          <v-list-item
            v-for="(item,i) in menu"
            :key="i"
          >
            <v-list-item-title @click="$router.push({path:item.path})">{{item.name}}</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
      <v-divider />
      <v-list rounded>
        <v-subheader>分类</v-subheader>
        <template v-for="(item,i) in ctg.slice(1)">
          <v-list-item
            :key="i"
            v-if="item.subctg == null"
          >
            <v-list-item-title>
              <router-link :to="{name:'search',params:{ctg: i + 1}}">{{item.name}}</router-link>
            </v-list-item-title>
          </v-list-item>
          <v-list-group
            :key="i"
            v-else
          >
            <template v-slot:activator>
              <v-list-item-title>
                <router-link :to="{name:'search',params:{ctg: i + 1}}">{{item.name}}</router-link>
              </v-list-item-title>
            </template>
            <v-list-item
              v-for="(subctg,j) in item.subctg"
              :key="j"
            >
              <v-list-item-title>
                <router-link :to="{name:'search',params:{ctg: i + 1,subctg: j + 1}}">{{subctg}}</router-link>
              </v-list-item-title>
            </v-list-item>
          </v-list-group>
        </template>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
export default {
  name: 'Nav',
  props: {
    isLoged: Boolean,
    nick: String,
    img: String,
    ctg: Array
  },
  data () {
    return {
      drawer: null,
      menu: [{ name: '首页', path: 'home' }, { name: '全部招标', path: 'search' }, { name: '全部工作室', path: 'search' }],
      menuSelected: 0,
      keywords: ''
    }
  },
  methods: {
    search () {
      if (this.$route.path === '/search') {
        this.$emit('keyword', this.keywords)
      } else {
        this.$emit('keyword', this.keywords)
        this.$router.push({ path: '/search' })
      }
    }
  }
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
  z-index: 6;
  background-color: transparent;
}
.blur {
  position: absolute;
  opacity: 1;
  left: 50%;
  transform: translate(-50%, 0);
  z-index: -2;
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
  width: 30px;
  height: 30px;
  margin: 5px;
  border-radius: 50px;
  object-fit: cover;
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
  color: black;
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

.nav {
  line-height: 42px;
}
</style>
