<template>
  <div>
    <router-view />
    <div class="wrapper">
      <div id="process" style="text-align: center;margin-bottom: 2%;">
        <div class="select-card process">匹配工作室</div>
        <div class="select-card process">工作室工作</div>
        <div class="select-card process">验收成果</div>
      </div>
      <div
        class="card"
        style="width: 98%;min-height: 500px;position: relative;margin: 0 auto;height: fit-content;"
      >
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
        <!--<input type='button' @click="check()" value='测试'>      测试按钮-->
        <div
          id="enroll"
          class="select-card select-card-hover"
          style="width: 100px;bottom: 3%;position: absolute;margin-left: 5%;display: none;"
        >我要投标</div>

        <div id="modal" style="display: none">
          <div
            class="modal_backgrd"
            @click="$('modal').children[0].setAttribute('style','background: rgba(0, 0, 0, 0);');$('modal').children[1].setAttribute('style','top: 150%');$('modal').setAttribute('style','display: none;')"
          ></div>
          <div class="card modal">
            <div
              style="position: absolute;top: 25px;width: 100%;text-align: center;font-size: 16px;"
            >相关信息</div>
            <form
              id="change"
              class="content"
              style="margin-top: 70px;padding-left: 10%;"
              onsubmit="return false;"
            >
              <textarea
                name="info"
                type="text"
                placeholder="告诉我们原因"
                style="width: 100%;height: 300px"
                required
              ></textarea>
              <input
                name='submit'
                type="submit"
                class="select-card select-card-hover"
                style="width: 100px;height: 42px;"
                value="确认"
              />
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
          <input type="file" style="display: none;" id="fileInput" />
          <div style="width: 90%;margin: 0 auto;">
            <div class="filename">文件名</div>
            <div class="date">日期</div>
            <div class="passed">当前状态</div>
            <div style="line-height: 42px;display: inline-block;">操作</div>
          </div>
        </div>
        <table id="table" class="table"></table>
      </div>
    </div>
  </div>
</template>
<script>
import Nav from "../components/Nav";
import Ctg from "../components/Ctg";

export default {
  id: window.location.href.split("?id=")[1],
  components: {
    Nav,
    Ctg
  },
  data() {
    return {
      id: "",
      action: ""
    };
  },
  computed:{
    mark:function(){
      let person=$('person')
      let info=$('info')
      let option=$('option')
      let id = window.location.href.split("?id=")[1];
      let value=document.getElementsByName('info').value;
      return person,info,option,id,value
}
  },
  methods: {
  /* check:function(){
      console.log(person)
      console.log(info)
      console.log(option)
    },                       -->测试函数 */
    examine: function(a, fileid) {
      axios({
        methods: "post",
        url: "examine",
        data: {
          id: fileid,
          action: a
        }
          .then(function(response) {
            if ((response = "succeess")) notify("递交成功");
          })
          .catch(function(error) {
            console.log(error);
          })
      });
    },
    sync_info(person,info,option) {
      axios({
        methods: "post",
        url: "project_info",
        data: {
          id: id,
          name
        }
          .then(function(response) {
            //initFirebase();
            var json = JSON.parse(response);
            $("prj_info").children[1].innerText += json.prjname;
            $("prj_info").children[2].innerText += translate_ctg(json.tag) + "-" + translate_subctg(json.tag, json.subtag);
            $("prj_info").children[3].innerText += json.companyName;
            $("prj_info").children[4].innerText += json.releasedata;
            $("prj_info").children[5].innerText += json.deadline;
            $("prj_info").children[6].innerText += json.price;
            $("prj_info").children[7].innerText += json.info;
            $("process").children[json.state].setAttribute("class", "select-card select-card-selected process");
            switch (json.state) {
                case 0:
                    todo(JSON.stringify(json.enroll));
                    if (json.companyID == "self") {
                        $("enroll").innerText = "我要上推荐";
                        $("enroll").onclick = function () {
                            if ($("change").children[0].tagName == "TEXTAREA") {
                                $("change").children[0].remove();
                                $("change").innerHTML = '<input type="button" style="width: 100%;background: transparent;" value="支付费用" onclick="">' +
                                    '<input name="duetime" type="date" placeholder="到期时间" style="width: 100%" required>' + $("change").innerHTML;
                                $("change").onsubmit = function () {
                                  axios({
                                      methods: "post",
                                      url: "ad_register_prj",
                                      data: {
                                        id: id,
                                        info:value
                                        
                                      }
                                        .then(function(response) {
                                          if ((response = "succeess"))
                                          notify("注册成功！此项目将会上首页");
                                        })
                                  })
                                    return false;
                                };
                            }
                            transition();
                        }
                        $("enroll").setAttribute("style", "width: 100px;bottom: 3%;position: absolute;margin-left: 5%;");
                        for (let i = 0; i < json.enroll.length - 1; i++) {
                            $name("project")[i].children[2].innerHTML += "<div id=\"select\" class=\"select-card select-card-hover\" style=\"width: 90%; margin-left: 5%\">中标</div>";
                            $name("project")[i].children[2].children[2].onclick = function (e) {
                                e.stopPropagation();
                                axios({
                                      methods: "post",
                                      url: "company_action",
                                      data: {
                                        id: id,
                                        action:select,
                                        studioid:json.enroll[i + 1].id,
                                        
                                      }
                                        .then(function(response) {
                                          if ((response = "succeess"))
                                          notify("选择成功！请在后续步骤中缴纳首付款");
                                        })
                                  })
                            }
                        }
                    }
                    if (mytype == 1) {
                        $("enroll").onclick = function () {
                          axios({
                                      methods: "post",
                                      url: "studio_action",
                                      data: {
                                        id: id,
                                        action:tender,
                                      }
                                        .then(function(response) {
                                          if ((response = "succeess"))
                                          notify("投标成功！请留意通知");
                                        })
                                  })
                        }
                        $("enroll").setAttribute("style", "width: 100px;bottom: 3%;position: absolute;margin-left: 5%;");
                    }
                    break;
                case 1:
                    $("state1").setAttribute("style", "width: 98%;height: fit-content;min-height: 500px;position: relative;margin: 0 auto;top: 20px;margin-bottom: 20px;");
                    if (json.companyID == "self") if (json.companyhasPaid == 0) {
                        $("enroll").innerText = "撤销项目";
                        $("enroll").onclick = function () {
                            $("change").onsubmit = function () {
                              axios({
                                      methods: "post",
                                      url: "company_action",
                                      data: {
                                        id: id,
                                        action:cancel,
                                        info:value
                                      }
                                        .then(function(response) {
                                          if ((response = "succeess"))
                                          notify("申请成功！请留意通知");
                                        })
                                  })
                                return false;
                            };
                            transition();
                        }
                        $("enroll").setAttribute("style", "width: 100px;bottom: 3%;position: absolute;margin-left: 5%;");
                    } else {
                        $("pay").innerText = "支付首付款";
                        $("pay").onclick = () => {
                          axios({
                                      methods: "post",
                                      url: "company_action",
                                      data: {
                                        id: id,
                                        action:pay,
                                        fileid:0
                                      }
                                        .then(function(response) {
                                          if ((response = "succeess"))
                                          notify("申请成功！请留意通知");
                                        })
                                  })
                        }
                        $("pay").setAttribute("style", "float: right;width: 100px;margin-top: 20px;");
                    }
                    if (json.studioID == "self") if (json.studiohasPaid == 0) {
                        $("enroll").innerText = "我要申诉";
                        $("enroll").onclick = function () {
                            $("change").onsubmit = function () {
                              axios({
                                      methods: "post",
                                      url: "studio_action",
                                      data: {
                                        id: id,
                                        action:complain,
                                        info:value
                                      }
                                        .then(function(response) {
                                          if ((response = "succeess"))
                                          notify("申请成功！请留意通知");
                                        })
                                  })
                                return false;
                            };
                            transition();
                        }
                        $("enroll").setAttribute("style", "width: 100px;bottom: 3%;position: absolute;margin-left: 5%;");
                    } else {
                        $("submit").setAttribute("style", "float: right;width: 100px;margin-top: 20px;");
                        $("pay").onclick = () => {
                          axios({
                                      methods: "post",
                                      url: "studio_action",
                                      data: {
                                        id: id,
                                        action:pay,
                                      }
                                        .then(function(response) {
                                          if ((response = "succeess"))
                                          notify("申请成功！请留意通知");
                                        })
                                  })
                        }
                        $("pay").setAttribute("style", "float: right;width: 100px;margin-top: 20px;");
                    }
                    $("submit").onclick = function () {
                        $("fileInput").click();
                    }
                    $("fileInput").onchange = function () {
                        var fd = new FormData();
                        fd.append("file", $("fileInput").files[0], "file");
                        fd.append("prjid", id);
                        axios({
                                      methods: "post",
                                      url: "upload_img",
                                      data: {
                                        id: id,
                                        action:complain,
                                        info:value
                                      }
                                        .then(function(response) {
                                          if ((response = "succeess"))
                                          notify("上传成功");
                                        })
                                  })
                    }
                    for (const file of json.file) {
                        var row = document.createElement("tr");
                        row.innerHTML = "<td class=\"filename-cell\"><a href=" + file.url + ">" + file.name + "</a></td>" +
                            "<td style=\"width: fit-content\">" + file.date + "</td>" +
                            "<td style=\"width: fit-content\";>" + translate_passed(file.ispassed) + "</td>";
                        if (json.companyID == "self")
                            row += "<td style=\"width: fit-content\"><a onclick='examine(0," + file.id + ")'>通过</a>&nbsp;<a onclick='examine(0," + file.id + ")'>不通过</a></td>";
                        else if (file.ispassed == 3 && json.studioID == "self") {
                            var td = document.createElement("td");
                            td.setAttribute("style", "width: fit-content");
                            td.innerText = "申诉";
                            td.onclick = function () {
                                $("change").onsubmit = function () {
                                  axios({
                                      methods: "post",
                                      url: "studio_action",
                                      data: {
                                        id: id,
                                        action:complain,
                                        info:value
                                      }
                                        .then(function(response) {
                                          if ((response = "succeess"))
                                          notify("申请成功！请留意通知");
                                        })
                                  })
                                    return false;
                                };
                                transition();
                            }
                            row += td;
                        }
                        $("table").innerHTML += row;
                    }
                    break;
                case 2:
                    $("prj_info").innerHTML += "<div style='color: red;'>此项目已成功完成并处于关闭状态</div>";
                    break;
                case 3:
                    $("prj_info").innerHTML += "<div>此项目中途失败，理由：" + json.reason + "</div>";
                    $("enroll").innerText = "我要申诉";
                    $("enroll").onclick = function () {
                        $("change").onsubmit = function () {
                          axios({
                                      methods: "post",
                                      url: "studio_action",
                                      data: {
                                        id: id,
                                        action:complain,
                                        info:value
                                      }
                                        .then(function(response) {
                                          if ((response = "succeess"))
                                          notify("申请成功！请留意通知");
                                        })
                                  })
                            return false;
                        };
                        transition();
                    }
                    $("enroll").setAttribute("style", "width: 100px;bottom: 3%;position: absolute;margin-left: 5%;");
                    break;
            }
        })
    });

  }
}
}
</script>
<style scoped>
.select-card {
    display: inline-block;
    border-radius: 50px;
    border: 1px solid lightgrey;
    margin-left: 1.4%;
    margin-right: 1.4%;
    text-align: center;
    line-height: 42px;
    transition: 300ms;
    background: white;
}

.process {
    width: 30%;
    height: 42px;
    margin-top: 20px;
}

.select-card:hover {
    box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
}

.select-card-hover:hover {
    border-color: transparent;
    background: #00a1d6;
    box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
    color: white;
    cursor: pointer;
}

.select-card-selected {
    border-color: transparent;
    background: #00a1d6;
    box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
    color: white;
}

.select-card-content {
    position: relative;
    top: 5%;
    margin-left: 5%;
}

.select-card-content>div {
    margin-top: 2%;
    margin-bottom: 2%;
}

.tab>ul>li>a {
    font-size: 16px;
    line-height: 42px;
    border-bottom: initial !important;
}

.tab>ul>li {
    background: initial !important;
}

.name {
    font-size: 20px;
    float: left;
    margin-left: 30px;
    line-height: 130px;
}

.name-right {
    width: 250px;
    top: 20px;
    right: 0px;
    position: absolute;
}

#cursor {
    background-color: #00a1d6;
    height: 3px;
    width: 120px;
    position: absolute;
    left: 5%;
    bottom: 0px;
    transition: 200ms;
}

.table {
    width: 90%;
    margin: 0 auto;
    border-collapse: collapse;
    border-bottom: 1px solid lightgray;
}

.table td {
    height: 64px;
}

.filename {
    width: 60%;
    line-height: 42px;
    display: inline-block;
}

.date {
    width: 15%;
    line-height: 42px;
    display: inline-block
}

.passed {
    width: 10%;
    line-height: 42px;
    display: inline-block;
}

.filename-cell {
    width: 60%;
}

.modal {
    width: 400px;
    height: fit-content;
    z-index: 500;
    position: fixed;
    top: 150%;
    left: 50%;
    transform: translate(-50%, -50%);
    transition: 300ms;
}

.modal_backgrd {
    z-index: 400;
    position: fixed;
    left: 0px;
    top: 0px;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0);
    transition: 300ms;
}

@media screen and (max-width: 1600px) {
    .process {
        width: 28%;
    }
}

@media screen and (max-width: 600px) {
    .name {
        line-height: 65px;
    }
    .name-right {
        width: 230px;
        top: 65px;
        right: 0px;
        position: absolute;
    }
    .date {
        width: 20%;
    }
    .filename-cell, .filename {
        width: 40%
    }
    .passed {
        width: fit-content;
    }
}
</style>