<template>
  <div>
    <v-data-table
      v-model="selected"
      :headers="headers"
      :items="data"
      :item-key="itemkey"
      class="elevation-1"
      :show-select="!isCompany"
      :disable-sort="!issort"
      :hide-default-footer="itemperPage === 100?true:false"
      :items-per-page="itemperPage"
      @click:row="(item)=>{rowCallback?rowCallback(item):null}"
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

      <template v-slot:item.action="{ item }">
        <template v-if="callback && isCallback(item)">

          <v-icon
            v-if="typeof callback === 'function'"
            @click.stop="callback(item)"
          >
            {{typeof callback === 'function'?callbackIcon(item):callbackIcon}}
          </v-icon>

          <v-icon
            v-else
            class="mr-2"
            v-for="(call, i) in callback"
            :key="i"
            @click.stop="call(item)"
          >
            {{callbackIcon[i]}}
          </v-icon>
        </template>
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
    },
    isPriceCol: Boolean,
    headers: Array,
    itemkey: String,
    issort: {
      type: Boolean,
      default: false
    },
    isCallback: {
      type: Function,
      default: () => { return true }
    },
    callback: [Function, Array],
    callbackIcon: [String, Array, Function],
    itemperPage: {
      type: Number,
      default: 100
    },
    rowCallback: Function
  },
  data () {
    return {
      selected: []
    }
  },
  methods: {
    update () {
      if (!this.headers.contain({ text: '进度款', value: 'price' }) && this.isPriceCol === true)
        this.headers.push({ text: '进度款', value: 'price' })

      if ((!this.headers.contain({ text: '操作', value: 'action' })) && this.callback)
        this.headers.push({ text: '操作', value: 'action' })
    }
  },
  created () {
    this.update()
  },
  beforeUpdate () {
    this.update()
  }
}
</script>
