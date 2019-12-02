<template>
  <div class="mt-8">
    <v-card
      class="wrapper"
      tile
    >
      <v-row no-gutters>
        <v-col cols="3">
          <v-list shaped>
            <v-subheader>设置</v-subheader>
            <v-list-item-group
              v-model="item"
              color="primary"
            >
              <v-list-item
                v-for="(item, i) in items"
                :key="i"
              >
                <v-list-item-content>
                  <v-list-item-title v-text="item"/>
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-col>
        <v-divider vertical />
        <v-col>
          <Info />
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
import Info from '../components/Info'

export default {
  components: {
    Info
  },
  data () {
    return {
      item: 0,
      items: ['个人信息', '设置'],
      views: 0
    }
  }
}
</script>
