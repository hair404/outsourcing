<template>
  <div class="wrapper">
    <div class="d-flex flex-wrap">
      <v-card
        height="400"
        class="mx-auto activity"
      >
        <v-carousel height="100%">
          <v-carousel-item
            v-for="(item,i) in activities"
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
                <v-parallax
                  style="width:100%"
                  :src="'/Platform' + item.img"
                />
              </v-row>
            </v-sheet>
          </v-carousel-item>
        </v-carousel>
      </v-card>
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
        class="mx-auto d-none d-md-block"
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
    <div style="height: 10px;" />
    <v-tabs>
      <v-tab>推荐工作室</v-tab>
      <v-tab>推荐项目</v-tab>
      <v-tab-item>
        <LoadCard
          width="100%"
          :type="0"
        ></LoadCard>
      </v-tab-item>
      <v-tab-item>
        <LoadCard
          width="100%"
          :type="1"
        ></LoadCard>
      </v-tab-item>
    </v-tabs>
  </div>
</template>

<script>
import Axios from 'axios'
import LoadCard from '../components/LoadCard'

export default {
  components: {
    LoadCard
  },
  props: {
    info: {},
    infoLoaded: Boolean
  },
  data () {
    return {
      activities: []
    }
  },
  methods: {
    loadActivities () {
      Axios
        .post('/Platform/activity', 'type=basic')
        .then(response => {
          this.activities = response.data
        })
        .catch(error => { console.log(error) })
    }
  },
  created () {
    this.loadActivities()
  }
}
</script>

<style scoped>
.activity {
  width: 69%;
}

@media screen and (max-width: 960px) {
  .activity {
    width: 100%;
  }
}
</style>
