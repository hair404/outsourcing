<template>
  <div>
    <v-dialog v-model="dialog">
      <v-card height="100px">
        <v-card-title style="display: inline-block">消息</v-card-title>
        <v-card-title style="display: inline-block;float:right">
          <v-btn text>清除通知</v-btn>
        </v-card-title>
        <div
          v-if="message.length === 0"
          style="margin-left: 40%"
          class="subtitle-1"
        >无通知</div>
        <v-list v-else>
          <v-list-item-group color="primary">
            <v-list-item
              v-for="(item, i) in message"
              :key="i"
              @click="this.$router.push(item.url)"
            >
              <v-list-item-content>
                <v-list-item-title>{{ item.title }}</v-list-item-title>
                <v-list-item-subtitle>{{ item.body }}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list-item-group>
        </v-list>
      </v-card>
    </v-dialog>

    <v-app-bar
      class="d-block d-md-none"
      color="primary"
      fixed
      dark
      shrink-on-scroll
      prominent
      src="https://picsum.photos/1920/1080?random"
      fade-img-on-scroll
    >
      <template v-slot:img="{ props }">
        <v-img
          v-bind="props"
          gradient="to top right, rgba(100,115,201,.7), rgba(25,32,72,.7)"
        ></v-img>
      </template>

      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title class="title">外包服务平台</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn
        v-if="!searchBar"
        icon
        @click="searchBar = true"
      >
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
      <v-form @submit.prevent="search">
        <v-text-field
          v-if="searchBar"
          height="30px"
          type="text"
          v-model="keywords"
          label="你想搜索"
          filled
        ></v-text-field>
      </v-form>
      <v-btn
        v-if="searchBar"
        icon
        @click="searchBar = false"
      >
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-menu
        open-on-hover
        offset-y
        nudge-left="50%"
      >
        <template v-slot:activator="{ on }">
          <v-btn
            v-on="on"
            icon
            @click="if(window.innerWidth < 600) dialog = true"
          >
            <v-icon>mdi-bell</v-icon>
          </v-btn>
        </template>
        <v-list class="d-none d-sm-flex">
          <v-subheader style="float:left">消息</v-subheader>
          <v-subheader style="float:right">
            <v-btn text>清除通知</v-btn>
          </v-subheader>
          <v-list-item-group color="primary">
            <v-list-item
              v-for="(item, i) in message"
              :key="i"
              @click="this.router.push(item.url)"
            >
              <v-list-item-content>
                <v-list-item-title>{{ item.title }}</v-list-item-title>
                <v-list-item-subtitle>{{ item.body }}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list-item-group>
        </v-list>
      </v-menu>
    </v-app-bar>

    <div
      class="d-block d-md-none"
      style="width: 100%;height: 128px"
    ></div>

    <div class="head d-none d-md-block">
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
        <div style="float: right;height: 42px;">
          <div
            class="nav d-none d-sm-flex"
            style="float: right;height: 42px;"
          >
            <div
              @click="!isLoged?$router.push({path:'/Login'}):$router.push({path:'/Center'})"
              class="account"
            >
              <div>{{isLoged?nick:'游客'}}</div>
              <img
                v-if="isLoged"
                :src="'/Platform'+img"
              />
            </div>
          </div>

          <v-menu
            open-on-hover
            offset-y
            nudge-left="50%"
          >
            <template v-slot:activator="{ on }">
              <div
                class="message"
                v-on="on"
                @click="if(window.innerWidth < 600) dialog = true"
              >消息</div>
            </template>
            <v-list class="d-none d-sm-flex">
              <v-subheader style="float:left">消息</v-subheader>
              <v-subheader style="float:right">
                <v-btn text>清除通知</v-btn>
              </v-subheader>
              <v-list-item-group color="primary">
                <v-list-item
                  v-for="(item, i) in message"
                  :key="i"
                  @click="this.router.push(item.url)"
                >
                  <v-list-item-content>
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                    <v-list-item-subtitle>{{ item.body }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-menu>

        </div>
      </div>
    </div>

    <v-parallax
      class="d-none d-md-block"
      height="170"
      src="../assets/head_bkgrd.jpg"
      style="margin-top: -50px"
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
      fixed
      temporary
    >
      <v-list-item v-if="!isLoged">
        <v-list-item-content>
          <v-list-item-title @click="$router.push({path:'/Login'})">游客</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item
        @click="$router.push({path:'/Center'})"
        v-else
      >
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
              <router-link
                @click.native="menuSelected = 1"
                :to="{name:'search',params:{ctg: i + 1}}"
              >{{item.name}}</router-link>
            </v-list-item-title>
          </v-list-item>
          <v-list-group
            :key="i"
            v-else
          >
            <template v-slot:activator>
              <v-list-item-title>
                <router-link
                  @click.native="menuSelected = 1"
                  :to="{name:'search',params:{ctg: i + 1}}"
                >{{item.name}}</router-link>
              </v-list-item-title>
            </template>
            <v-list-item
              v-for="(subctg,j) in item.subctg"
              :key="j"
            >
              <v-list-item-title>
                <router-link
                  @click.native="menuSelected = 1"
                  :to="{name:'search',params:{ctg: i + 1,subctg: j + 1}}"
                >{{subctg}}</router-link>
              </v-list-item-title>
            </v-list-item>
          </v-list-group>
        </template>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
import Axios from 'axios'
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
      searchBar: false,
      menu: [{ name: '首页', path: 'home' }, { name: '全部招标', path: 'search' }, { name: '全部工作室', path: 'search' }],
      menuSelected: 0,
      keywords: '',
      dialog: false,
      message: [],
      window: window
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
  },
  created () {
    Axios.post('/Platform/notify').then(response => { this.message = response.data }).catch(error => { console.log(error) })
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
  transition: 300ms;
}

.message:hover {
  cursor: pointer;
  background-color: rgba(255, 255, 255, 0.2);
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
