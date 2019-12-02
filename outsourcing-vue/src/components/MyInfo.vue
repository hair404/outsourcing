<template>
  <div
    class="mx-auto d-none d-md-block"
    min-width="200px"
  >
    <v-card
      v-if="!infoLoaded"
      :height="height"
      class="d-flex"
    >
      <div class="mx-auto my-auto">
        <v-btn
          outlined
          rounded
          color="primary"
          class="d-block mb-10"
          width="100"
          @click="$router.push('/login')"
        >登录</v-btn>
        <v-btn
          outlined
          rounded
          color="primary"
          class="d-block"
          width="100"
          @click="$router.push('/register')"
        >注册</v-btn>
      </div>
    </v-card>
    <v-card
      v-else
      :height="height"
    >
      <v-img
        class="white--text align-end"
        :height="height/2"
        :src="utils.baseURL+info.img"
      >
        <v-card-title>{{info.username}}</v-card-title>
      </v-img>
      <v-rating
        class="ml-2"
        v-if="info.type == 1 && !isOthers"
        v-model="info.rating"
        color="orange"
        background-color="orange"
        readonly
        half-increments
      ></v-rating>

      <v-list-item
        class="py-0"
        style="height: 24px"
        v-ripple
      >
        <v-list-item-content>身份</v-list-item-content>
        <v-list-item-icon>
          {{utils.type[info.type]}}
        </v-list-item-icon>
      </v-list-item>
      <v-list-item
        class="py-0"
        style="height: 24px"
        v-ripple
      >
        <v-list-item-content>邮箱</v-list-item-content>
        <v-list-item-icon>
          {{info.email}}
        </v-list-item-icon>
      </v-list-item>
      <v-card-actions
        style="position: absolute;bottom: 5px"
        v-if="!isOthers"
      >
        <v-btn
          color="primary"
          text
          @click="$router.push('/center')"
        >
          个人中心
        </v-btn>
        <v-btn
          color="primary"
          text
        >
          展示页面
        </v-btn>
        <v-btn
          color="red"
          text
          @click="logoff()"
        >
          注销
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
import utils from '../js/utils'
import Axios from 'axios'

export default {
  props: {
    infoLoaded: Boolean,
    info: {},
    height: Number,
    isOthers: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      utils: utils
    }
  },
  methods: {
    logoff () {
      Axios.post(this.utils.baseURL + '/logoff').then(response => {
        if (response.data === 'success') {
          this.info.type = null
          this.$router.push({ path: '/Login' })
        }
      }).catch(error => { console.log(error) })
    }
  }
}
</script>
