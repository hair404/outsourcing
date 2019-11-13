<template>
  <div>{{hours}}小时{{minutes}}分{{seconds}}秒</div>
</template>

<script>
export default {
  props: {
    deadline: Object
  },
  data () {
    return {
      time: this.deadline.split(':'),
      hours: 0,
      minutes: 0,
      seconds: 0
    }
  },
  methods: { // 倒计时
    num (n) {
      return n < 10 ? '0' + n : '' + n
    },
    // 倒计时
    timer () {
      var _this = this
      var time = window.setInterval(function () {
        if (_this.seconds === 0 && _this.minutes !== 0) {
          _this.seconds = 59
          _this.minutes -= 1
        } else if (_this.minutes === 0 && _this.seconds === 0 && _this.hours !== 0) {
          _this.hours -= 1
          _this.minutes = 59
          _this.seconds = 59
        } else if (_this.minutes === 0 && _this.seconds === 0 && _this.hours === 0) {
          _this.seconds = 0
          window.clearInterval(time)
        } else {
          _this.seconds -= 1
        }
      }, 1000)
    }
  },
  mounted () {
    // 倒计时
    this.timer()
    this.hours = parseInt(this.time[0])
    this.minutes = parseInt(this.time[1])
    this.seconds = parseInt(this.time[2])
  },
  watch: {
    // 倒计时
    second: {
      handler (newVal) {
        this.num(newVal)
      }
    },
    // 倒计时
    minute: {
      handler (newVal) {
        this.num(newVal)
      }
    },
    hour: {
      handler (newVal) {
        this.num(newVal)
      }
    }
  },
  computed: {
    // 倒计时
    second: function () {
      return this.num(this.time.s)
    },
    minute: function () {
      return this.num(this.time.m)
    },
    hour: function () {
      return this.num(this.time.h)
    }
  }
}
</script>
