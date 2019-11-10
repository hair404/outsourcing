<template>
  <div>
    <v-data-table
      v-model="selected"
      :headers="headers"
      :items="data"
      :items-per-page="5"
      item-key="name"
      class="elevation-1"
      show-select
      disable-sort
    ></v-data-table>
  </div>
</template>

<script>
export default {
  props: {
    data: Array
  },
  data () {
    return {
      headers: [{
        text: '进度名', value: 'name'
      },
      {
        text: '进度详情', value: 'info'
      },
      {
        text: '所需时间', value: 'time'
      }],
      selected: []
    }
  }
}
</script>
