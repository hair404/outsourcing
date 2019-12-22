<template>
  <v-content>
    <v-navigation-drawer permanent fixed app>
      <v-list-item>
        <v-list-item-avatar>
          <v-avatar color="primary">
            <v-icon dark>mdi-account-circle</v-icon>
          </v-avatar>
        </v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title>
            管理员
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-subheader>管理中心</v-subheader>
      <v-divider></v-divider>

      <v-list dense nav>
        <v-list-item-group>
          <v-list-item
            v-for="(item, i) in items"
            :key="i"
            :to="`/Manage${item.link}`"
            class="mt-3"
            color="light-blue"
          >
            <v-list-item-icon>
              <v-icon v-text="item.icon"></v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title
                v-text="item.title"
                class="subtitle-2"
              ></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>

    <router-view :snackbar="snackbar"></router-view>

    <v-snackbar :color="snackbar.color" v-model="snackbar.open">
      {{ snackbar.text }}
      <v-btn text @click="snackbar.open = false">
        关闭
      </v-btn>
    </v-snackbar>
    <v-fab-transition>
      <v-btn
        color="light-blue"
        to="/Manage/ManageRegister"
        style="bottom: 30px"
        icon
        outlined
        buttom
        right
        absolute
        @click="submit"
        ><v-icon>mdi-plus</v-icon>
      </v-btn>
    </v-fab-transition>
  </v-content>
</template>

<script>
export default {
  data() {
    return {
      snackbar: {
        open: false,
        color: 'primary',
        text: '错误'
      },
      items: [
        {
          title: '用户管理',
          icon: 'mdi-account-box-multiple',
          link: '/Users'
        },
        {
          title: '广告管理',
          icon: 'mdi-post-outline',
          link: '/Advertise'
        },
        {
          title: '资金流',
          icon: 'mdi-cash-usd-outline',
          link: '/Acount'
        },
        {
          title: '活动',
          icon: 'mdi-ticket',
          link: '/Activity'
        },
        {
          title: '分类管理',
          icon: 'mdi-book-variant',
          link: '/Classification'
        }
      ]
    }
  }
}
</script>
