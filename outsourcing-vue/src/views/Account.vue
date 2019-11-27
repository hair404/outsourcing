
<template>
  <v-app>
    <v-snackbar
      color="error"
      v-model="snackbar.open"
    >
      {{ snackbar.text }}
      <v-btn
        text
        @click="snackbar.open = false"
      >
        关闭
      </v-btn>
    </v-snackbar>

    <v-app-bar
      color="primary"
      fixed
      dark
    >
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title class="title">人力和项目服务平台</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn
        v-if="!searchBar"
        icon
        @click="searchBar = true"
      >
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
      <v-form @submit.prevent="search()">
        <v-text-field
          v-if="searchBar"
          type="text"
          v-model="keywords"
          label="你想搜索"
          filled
          dense
          hide-details
        ></v-text-field>
      </v-form>
      <v-btn
        v-if="searchBar"
        icon
        @click="searchBar = false"
      >
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </v-app-bar>

    <v-content>
      <router-view :snackbar="snackbar"></router-view>
    </v-content>

    <v-navigation-drawer
      v-model="drawer"
      fixed
      temporary
    >
      <v-img src="https://img.xjh.me/random_img.php?type=bg&ctype=nature&return=302"></v-img>
      <v-list shaped>
        <v-list-item-group
          v-model="menuSelected"
          color="primary"
        >
          <v-list-item
            v-for="(item,i) in menu"
            :key="i"
          >
            <v-list-item-icon>
              <v-icon color="primary">{{item.icon}}</v-icon>
            </v-list-item-icon>
            <v-list-item-title @click="$router.push({path:item.path})">{{item.name}}</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
  </v-app>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      menuSelected: 0,
      type: 0,
      snackbar: {
        open: false,
        text: ''
      },
      drawer: null,
      searchBar: false,
      menu: [{ name: '首页', path: 'home', icon: 'mdi-home' }, { name: '全部招标', path: 'search', icon: 'mdi-magnify' }, { name: '全部工作室', path: 'search', icon: 'mdi-magnify' }],
      keywords: ''
    }
  },
  methods: {
    search () {
      this.$router.push({ name: 'search', params: { keyword: this.keywords } })
    }
  }
}
</script>
