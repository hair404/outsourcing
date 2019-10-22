<template>
<div>
    <Nav></Nav>
    <Ctg></Ctg>
    <router-view />
    <div class="wrapper">
        <div id="process" style="text-align: center;margin-bottom: 2%;">
            <div class="select-card process">匹配工作室</div>
            <div class="select-card process">工作室工作</div>
            <div class="select-card process">验收成果</div>
        </div>
        <div class="card" style="width: 98%;min-height: 500px;position: relative;margin: 0 auto;height: fit-content;">
            <div id="prj_info" class="select-card-content">
                <div style="font-size: 20px;">需求信息</div>
                <div>需求标题：</div>
                <div>需求类别：</div>
                <div>发布公司：</div>
                <div>发布日期：</div>
                <div>截止日期：</div>
                <div>预算金额：</div>
                <div>详细介绍：</div>
            </div>
            <div id="enroll" class="select-card select-card-hover" style="width: 100px;bottom: 3%;position: absolute;margin-left: 5%;display: none;">我要投标</div>

            <div id="modal" style="display: none">
                <div class="modal_backgrd" $click="$('modal').children[0].setAttribute('style','background: rgba(0, 0, 0, 0);');$('modal').children[1].setAttribute('style','top: 150%');$('modal').setAttribute('style','display: none;')"></div>
                <div class="card modal">
                    <div style="position: absolute;top: 25px;width: 100%;text-align: center;font-size: 16px;">相关信息</div>
                    <form id="change" class="content" style="margin-top: 70px;padding-left: 10%;" onsubmit="return false;">
                        <textarea name="info" type="text" placeholder="告诉我们原因" style="width: 100%;height: 300px" required></textarea>
                        <input type="submit" class="select-card select-card-hover" style="width: 100px;height: 42px;" value="确认">
                    </form>
                </div>
            </div>

        </div>
    </div>
    <div class="wrapper">
        <div id="container" class="container" style="top: 20px;"></div>

        <div class="card" id="state1" style="display: none;">
            <div class="card" style="height: 106px;">
                <div style="margin-left: 5%;font-size: 20px;line-height: 64px;display: inline-block;">审核进度</div>
                <div id="submit" class="select-card select-card-hover" style="display: none;">提交文件</div>
                <div id="pay" class="select-card select-card-hover" style="display: none;">支付违约金</div>
                <input type="file" style="display: none;" id="fileInput">
                <div style="width: 90%;margin: 0 auto;">
                    <div class="filename">文件名</div>
                    <div class="date">日期</div>
                    <div class="passed">当前状态</div>
                    <div style="line-height: 42px;display: inline-block;">操作</div>
                </div>
            </div>
            <table id="table" class="table">
            </table>
        </div>
    </div>
    </div>
</template>
<!--<script>
import top from '../components/'
import categories from '../components/'


Vue.component("top",{
  template:top
});
new Vue({
  el:"#top"
});
Vue.component("categories",{
  template:categories
});
new Vue({
  el:"#categories"
});

export default {
  data() {
    return {

    };
  },
  mounted(){

  },
  methods: {
}
}
</script>
<style scoped>
</style>
-->