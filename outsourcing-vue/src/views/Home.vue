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
      <MyInfo
        :infoLoaded="infoLoaded"
        :info="info"
        :height="400"
        style="width:29%;"
      />
    </div>
    <div style="height: 10px;" />
    <v-tabs>
      <v-tab>推荐工作室</v-tab>
      <v-tab>推荐项目</v-tab>
      <v-tab-item>
        <LoadCard
          width="100%"
          :type="0"
          address="recommend_studio"
          isActiveLoad
        ></LoadCard>
      </v-tab-item>
      <v-tab-item>
        <LoadCard
          width="100%"
          :type="1"
          address="recommend_project"
          isActiveLoad
        ></LoadCard>
      </v-tab-item>
    </v-tabs>
  </div>
</template>

<script>
import Axios from 'axios'
import LoadCard from '../components/LoadCard'
import MyInfo from '../components/MyInfo'
import utils from '../js/utils'

export default {
  components: {
    LoadCard,
    MyInfo
  },
  props: {
    info: {},
    infoLoaded: Boolean
  },
  data () {
    return {
      utils: utils,
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
