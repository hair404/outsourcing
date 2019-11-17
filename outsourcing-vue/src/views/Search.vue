<template>
  <div class="wrapper">
    <v-card>
      <div class="label">类型</div>
      <v-btn-toggle
        v-model="type"
        tile
        color="primary"
        group
        mandatory
      >
        <v-btn
          v-for="(item,i) in types"
          :key="i"
          :value="i"
        >{{item}}</v-btn>
      </v-btn-toggle>
      <br />
      <div
        class="label"
        style="float: left"
      >分类</div>
      <div style="overflow-x: auto">
        <v-btn-toggle
          v-model="ctg"
          tile
          color="primary"
          group
          mandatory
        >
          <v-btn
            v-for="(item,i) in utils.ctg"
            :key="i"
            :value="i"
          >{{item.name}}</v-btn>
        </v-btn-toggle>
      </div>
      <div class="label">子类</div>
      <v-btn-toggle
        v-model="subctg"
        tile
        color="primary"
        group
        mandatory
      >
        <v-btn
          key="0"
          :value="0"
        >全部</v-btn>
        <v-btn
          v-for="(item,i) in utils.getReal(utils.ctg[ctg ].subctg)"
          :key="i + 1"
          :value="i + 1"
        >{{item}}</v-btn>
      </v-btn-toggle><br />
      <div class="label">排序</div>
      <v-btn-toggle
        v-model="sort"
        tile
        color="primary"
        group
        mandatory
      >
        <v-btn
          v-for="(item,i) in type === 0 ? sorts:sortsStudio"
          :key="i"
          :value="i"
          @click="()=>{if(sort === i) sortrule = 1 - sortrule}"
        >{{item}}
          <v-icon
            v-if="sort === i && sortrule === 0"
            color="primary"
          >mdi-chevron-down</v-icon>
          <v-icon
            v-if="sort === i && sortrule === 1"
            color="primary"
          >mdi-chevron-up</v-icon>
        </v-btn>
      </v-btn-toggle>
    </v-card>
    <LoadCard
      :type="1 - type"
      address="search"
      :extraParam="utils.toFormData({'type':(1 - type), ctg, subctg, keyword, sort, sortrule})"
      :number="20"
      ref="LoadCard"
    />
  </div>
</template>

<script>
import utils from '../js/utils'
import LoadCard from '../components/LoadCard'

export default {
  name: 'search',
  components: {
    LoadCard
  },
  props: {
    keyword: String
  },
  data () {
    return {
      utils: utils,
      types: ['招标', '工作室'],
      type: utils.getReal(this.$route.params.type, 0),
      ctg: utils.getReal(this.$route.params.ctg, 0),
      subctg: utils.getReal(this.$route.params.subctg, 0),
      sorts: ['默认', '酬金', '首付款'],
      sortsStudio: ['默认', '评分'],
      sort: 0,
      sortrule: 0
    }
  },
  watch: {
    ctg: function () {
      this.subctg = 0
    }
  }
}
</script>

<style>
.label {
  display: inline-block;
  width: 42px;
  text-align: center;
  margin-left: 6px;
  line-height: 56px;
}
</style>
