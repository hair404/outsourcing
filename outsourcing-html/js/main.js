var index = 1;
var todo = function todo(response) {
    var json = JSON.parse(response);
    for (let i = index - 1; i < json.length + index - 2; i++) {
        const jsonObject = json[i - (index - 1) + 1];
        var project;
        if (jsonObject.hasOwnProperty("info")) {
            if (projects.length < 5 || (index > 4 || i > 4))
                $("container").innerHTML += "<div name=\"project\" onclick=\"window.open('./display.html?id=" + jsonObject.id + "');\" class=\"card net-cell\" style=\"position: relative;cursor: pointer;\"><div class=\"account\" style=\"height: 10%;width: 80%;padding: 10%;\">" +
                    "<img src=\"./img/icon.png\" style=\"float: left\">" +
                    "<div style=\"line-height: initial;max-width: 100px;\"></div></div><div style=\"padding-left: 20%;height: fit-content;\"></div>" +
                    "<div style=\"margin-left: 5%;position: absolute;width: 90%;bottom: 2%;\"><div style=\"text-align: center;\"></div><div style=\"color: black;text-align: center;\"></div></div></div>";
            else {
                var height;
                if (i == 0)
                    height = 50;
                else
                    height = 40;
                projects[i].innerHTML = "<div class=\"account\" style=\"height: 10%; width: 80%; padding: 5%; \"><img src=\"./img/icon.png\" style=\"float: left\">" +
                    "<div style=\"line-height: initial;\"></div></div>" +
                    "<div style=\"padding-left: 10%;height: " + height + "%;padding-top: 5%;\"></div>";
                if (i == 0)
                    projects[i].innerHTML += "<div style=\"height: 20%\"><div class=\"pro-name\"></div><div class=\"pro-price\" style=\"color: black\"></div></div>";
                else
                    projects[i].innerHTML += "<div><div style=\"text-align: center;\"></div><div style=\"color: black;text-align: center;\"></div></div></div>";
            }
            project = projects[i];
            project.children[0].children[0].setAttribute("src", jsonObject.img);
            var username = jsonObject.username || jsonObject.prjname;
            project.children[0].children[1].innerHTML = username + "<br>";
            var tags = "";
            for (const tag of jsonObject.tag)
                tags += translate_ctg(tag) + " ";
            project.children[0].children[1].innerHTML += tags.replace(" ", "&nbsp;");
            project.children[1].innerText = jsonObject.info;
            project.children[2].children[0].innerText = "电话：" + jsonObject.tel;
            project.children[2].children[1].innerText = "邮箱：" + jsonObject.email;
            projects[i].onclick = function () {
                window.open("./display.html?id=" + jsonObject.id);
            }
        }
        else {
            if (projects.length < 5 || (index > 4 || i > 4))
                $("container").innerHTML += "<div name='project' style=\"cursor: pointer;\" onclick=\"window.open('./detail.html?id=" + jsonObject.id + "');\" class='card net-cell'><div class='pic'><img class='autoimg'></div><div style='height: 20%'><div class='pro-name adjust'></div > <div class='pro-price'></div></div ></div > ";
            project = projects[i];
            var img = new Image();
            img.onload = function () {
                projects[i].children[0].children[0].setAttribute("src", jsonObject.img);
            };
            img.src = jsonObject.img;
            var tags = translate_ctg(jsonObject.tag) + "-" + translate_subctg(jsonObject.tag, jsonObject.subtag);
            if ((projects_length < 5 || index > 4) || i >= 5 || i == 0)
                project.children[1].children[0].innerHTML = "<div>" + jsonObject.prjname + "<br><span style='color:grey;'>"
                    + tags + "</span>" + "</div>";
            else
                project.children[1].children[0].innerHTML = jsonObject.prjname;
            project.children[1].children[1].innerText = jsonObject.price;
            projects[i].onclick = function () {
                window.open("./detail.html?id=" + jsonObject.id);
            }
        }
    }
    if (json[0] > 0) {
        $("pages").innerHTML = "<div id=\"pageup\" class=\"card button selection-blue\"><a>上一页</a></div>";
        var number = json[0];
        for (let i = 0; i < number / (sizeofRow * row); i++)
            $("pages").innerHTML += "<div onclick=\"pageSwitch(" + (i + 1) + ");\"><a>" + (i + 1) + "</a></div>";
        $("pages").innerHTML += "<div id=\"pagedown\" class=\"card button selection-blue\"><a>下一页</a></div>";
        $("pageup").onclick = function () {
            if (currentpage != 1) {
                currentpage -= 1;
                pageSwitch(currentpage);
            }
        }
        $("pagedown").onclick = function () {
            if (currentpage < $("pages").children.length - 2) {
                currentpage += 1;
                pageSwitch(currentpage);
            }
        }
        for (const page of $("pages").children) {
            page.setAttribute("class", "card button selection-blue");
        }
        $("pages").children[currentpage].setAttribute("class", "card button selection-blue selection-blue-selected");
    }
    index += json.length - 1;
};

var projects = $name("project");
var projects_length = projects.length;

var sizeofRow;
var length;
if (window.innerWidth >= 1400)
    sizeofRow = 4;
else if (window.innerWidth >= 1000)
    sizeofRow = 3;
else
    sizeofRow = 2;
if (window.innerHeight >= 800)
    length = 5 + sizeofRow * 4;
else
    length = 5 + sizeofRow;
$("search").onsubmit = function () {
    window.location.href = "./search.html?type=0&ctg=0&subctg=0&keyword=" + $("search").children[0].value;
    return false;
}

function sync_info(person, info, option, callback) {
    sendAjax("post", "info", "type=basic", function todo(response) {
        if (response != "NotLogin") {
            var json = JSON.parse(response);
            myid = json.id;
            mytype = json.type;
            generate_link();
            $("info").children[0].innerText = "您是：" + translate(json.type) + "用户";
            $("info").children[1].innerText = json.email;
            person.children[0].innerText = json.username;
            person.children[1].setAttribute("src", json.img);
            function enter_center() {
                window.location.href = './center.html';
            }
            person.onclick = enter_center;
            info.onclick = enter_center;
            option.children[0].onclick = enter_center;
            option.children[1].onclick = enter_center;
            option.children[2].innerText = "注销登录";
            option.children[2].onclick = function () {
                sendAjax("post", "logoff", "", function () {
                    window.location.href = "./login.html";
                })
            }
            if (callback != null)
                callback();
            return;
        }
    });
}

function generate_link() {
    if ($("ctg") == null)
        return;
    var ctgs = $("ctg").children;
    for (let i = 0; i < ctgs.length; i++) {
        ctgs[i].children[0].href = "./search.html?type=" + mytype + "&ctg=" + i;
        var subctgs = ctgs[i].getElementsByTagName("li");
        for (let j = 0; j < subctgs.length; j++) {
            subctgs[j].children[0].href = "./search.html?type=" + mytype + "&ctg=" + i + "&subctg=" + (j + 1);
        }
    }
}

function load(length) {
    sendAjax("post", "recommend", "first=" + index + "&end=" + (index + length - 1), todo);
}

function display(a) {
    if (a == 0)
        person.parentNode.children[1].setAttribute("style", "display:block");
    else
        person.parentNode.children[1].setAttribute("style", "display:none");
}

function transition() {
    $('modal').setAttribute('style', 'display: block;');
    setTimeout(function () { $('modal').children[0].setAttribute('style', 'background: rgba(0, 0, 0, 0.4);'); $('modal').children[1].setAttribute('style', 'top: 50%;'); }, 50);
}

function initFirebase() {
    messaging.onMessage((payload) => {
        notify(payload.notification.body, payload.notification.click_action);
    });
}

function notify(body, link) {
    $("notify").setAttribute("style", "");
    setTimeout(() => { $("notify").setAttribute("style", "bottom: 10px;"); }, 50);
    setTimeout(() => { $("notify").setAttribute("style", "display: none"); }, 3000);
    $("notify").innerText = body;
    if (link != null)
        $("notify").onclick = () => {
            window.open(link);
        }
}