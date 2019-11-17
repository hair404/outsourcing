<template>
  <v-card
    class="mx-auto"
    height="300"
    width="100%"
  >
    <v-list-item three-line>
      <v-list-item-content v-if="info.tag">
        <v-list-item-title class="headline mb-1">
          <v-icon
            color="grey"
            class="mr-4 my-4"
            @click="$router.back()"
          >mdi-arrow-left</v-icon>项目名称:{{info.prjname}}
        </v-list-item-title>
        <v-list-item-title>发布时间:{{info.releaseTime}}</v-list-item-title>
        <v-list-item-title>截止时间:{{info.deadline}}</v-list-item-title>
        <v-list-item-title>整个项目截止时间:{{info.deadline}}</v-list-item-title>
        <v-list-item-title>薪金:{{info.price}}</v-list-item-title>
        <v-list-item-title>首付款:{{info.payinadvance}}</v-list-item-title>
        <v-list-item-title>类别:{{utils.ctg[info.tag].name}}{{info.subtag == 0 ? '': '-'+utils.ctg[info.tag].subctg[info.subtag - 1]}}</v-list-item-title>
        <v-list-item-title>其他信息:{{info.info}}</v-list-item-title>
      </v-list-item-content>

      <v-img
        max-height="100"
        class="img"
        max-width="100"
        :src="'/Platform'+info.img"
      ></v-img>
    </v-list-item>
  </v-card>
</template>

<script>
import utils from '../js/utils'

export default {
  props: {
    info: Object
  },
  data () {
    return {
      utils: utils
    }
  }
}
</script>

<style scoped>
.v-list-item__content .v-list-item__subtitle {
  line-height: 24px;
}
</style>
