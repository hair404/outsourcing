<template>
  <div
    class="mx-auto d-none d-md-block"
    min-width="200px"
  >
    <v-skeleton-loader
      v-if="!infoLoaded"
      class="mx-auto d-none d-md-block"
      width="29%"
      min-width="200px"
      height="400"
      type="card"
    ></v-skeleton-loader>
    <v-card
      v-else
      height="400"
    >
      <v-img
        class="white--text align-end"
        height="200px"
        src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
      >
        <v-card-title>{{info.username}}</v-card-title>
      </v-img>
      <v-card-subtitle class="pb-0">身份</v-card-subtitle>
      <v-card-text class="text--primary">
        <div>{{utils.type[info.type]}}</div>
      </v-card-text>
      <v-card-subtitle class="pb-0">邮箱</v-card-subtitle>
      <v-card-text class="text--primary">
        <div>{{info.email}}</div>
      </v-card-text>
      <v-card-actions>
        <v-btn
          color="primary"
          text
        >
          个人中心
        </v-btn>
        <v-btn
          color="primary"
          text
        >
          展示页面
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
import utils from '../js/utils'

export default {
  props: {
    infoLoaded: Boolean,
    info: {}
  },
  data () {
    return {
      utils: utils
    }
  }
}
</script>
