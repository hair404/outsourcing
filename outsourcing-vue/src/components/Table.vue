<template>
  <div>
    <v-data-table
      v-model="selected"
      :headers="headers"
      :items="data"
      item-key="name"
      class="elevation-1"
      :show-select="!isCompany"
      disable-sort
      hide-default-footer
    >
      <template
        v-if="isCompany"
        v-slot:item.price="props"
      >
        <v-edit-dialog
          :return-value.sync="props.item.price"
          large
          persistent
        >
          <v-btn
            v-if="!props.item.price"
            outlined
            color="primary"
          >设定进度款</v-btn>
          <div v-else>{{props.item.price}}</div>
          <template v-slot:input>
            <v-text-field
              v-model="props.item.price"
              :rules="[v => typeof v =='number' && v > 0 || '必须超过0']"
              label="Edit"
              single-line
              counter
              autofocus
            ></v-text-field>
          </template>
        </v-edit-dialog>
      </template>
    </v-data-table>
  </div>
</template>

<script>
export default {
  props: {
    data: Array,
    isCompany: {
      type: Boolean,
      default: false
    }
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
        text: '所需时间(天)', value: 'time'
      }],
      selected: []
    }
  },
  created () {
    if (this.isCompany)
      this.headers.push({ text: '价格', value: 'price' })
  }
}
</script>
