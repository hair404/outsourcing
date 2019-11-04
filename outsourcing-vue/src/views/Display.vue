<template>
  <div>
        <Nav></Nav>
    <Ctg></Ctg>
    <div
      id="modal"
      style="display: none"
    >
      <div
        class="modal_backgrd"
        onclick="$('modal').children[0].setAttribute('style','background: rgba(0, 0, 0, 0);');$('modal').children[1].setAttribute('style','top: 150%');$('modal').setAttribute('style','display: none;')"
      ></div>
      <div class="card modal">
        <div style="position: absolute;top: 25px;width: 100%;text-align: center;font-size: 16px;">相关信息</div>
        <form
          id="change"
          class="content"
          style="margin-top: 70px;padding-left: 10%;"
          onsubmit="return false;"
        >
          <input
            type="button"
            style="width: 100%"
            value="支付费用"
            onclick=""
          >
          <input
            name="duetime"
            type="date"
            placeholder="到期时间"
            style="width: 100%"
            required
          >
          <input
            type="submit"
            class="select-card select-card-hover"
            style="width: 100px;height: 42px;"
            value="确认"
          >
        </form>
      </div>
    </div>

    <div class="wrapper">
      <img
        id="pic"
        class="person-pic"
      >
      <div
        id="manage"
        style="width: 100px;float: right;display: none;"
      >
        <div
          class="select-card select-card-hover"
          style="width: 100px;float: right; margin-bottom: 20px;"
          onclick="window.location.href='./center.html'"
        >切换到管理页</div>
        <div
          id="ad"
          class="select-card select-card-hover"
          style="width: 100px;float: right;"
        >我要上推荐</div>
      </div>
      <div
        id="person_info"
        style="display: inline-block;vertical-align: top;margin-top: 1%;line-height: 35px;margin-left: 2%;"
      >
        <div style="font-size: 20px;">名字</div>
        <div>联系方式：</div>
        <div>介绍</div>
      </div>
    </div>
    <div class="wrapper">
      <div style="font-size: 20px;padding-left: 1%;">已完成项目</div>
      <div
        id="container"
        class="container"
        style="top: 20px;"
      ></div>
    </div>
  </div>
</template>

<script>
export default {
  components: {
  }
}
</script>
