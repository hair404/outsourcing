<template>
  <div class="wrapper">
    <div class="d-flex flex-wrap">
      <v-card
        width="69%"
        height="400"
        class="mx-auto"
      >
        <v-carousel height="100%">
          <v-carousel-item
            v-for="(item,i) in carousels"
            :key="i"
          >
            <v-sheet
              height="100%"
              tile
            >
              <v-row
                class="fill-height"
                align="center"
                justify="center"
              >
                <v-img :src="'Platform'+item.img" />
              </v-row>
            </v-sheet>
          </v-carousel-item>
        </v-carousel>
      </v-card>
      <v-card
        class="mx-auto"
        width="29%"
        min-width="200px"
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
          <div>{{info.type}}</div>
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
    <LoadCard width="100%"></LoadCard>
  </div>
</template>

<script>
import Axios from 'axios'
import LoadCard from '../components/LoadCard'

export default {
  components: {
    LoadCard
  },
  data () {
    return {
      info: {},
      carousels: []
    }
  },
  methods: {
    loadinfo () {
      Axios
        .post('/Platform/info', 'type=basic')
        .then(response => {
          this.info = response.data
        })
        .catch(error => { console.log(error) })
    }
  },
  created () {
    this.loadinfo()
  }
}
</script>
