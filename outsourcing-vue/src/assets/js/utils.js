function $(id) {
    return document.getElementById(id);
}

 function $name(name) {
    return document.getElementsByName(name);
}

 function serialize(form) {
    var data = "";
    for (const input of form.getElementsByTagName("input")) {
        if (input.type == "text" || input.type == "date" || input.type == "email")
            data += "&" + input.name + "=" + input.value;
    }
    for (const textarea of form.getElementsByTagName("textarea")) {
        data += "&" + textarea.name + "=" + textarea.value;
    }
    return data.substring(1, data.length);
}

FormData.prototype.add = (name, value) => {
    this.append(name, value);
    return this;
}

function translate(a) {
    switch (a) {
        case 0:
            return "企业";
        case 1:
            return "工作室";
        case 2:
            return "管理员";
    }
    return "游客";
}
function translate_ctg(a) {
    switch (a) {
        case 0:
            return "全部";
        case 1:
            return "网站开发";
        case 2:
            return "移动应用开发";
        case 3:
            return "H5开发";
        case 4:
            return "UI设计";
        case 5:
            return "测试运维";
        case 6:
            return "云服务";
        case 7:
            return "IT综合服务";
    }
}

 function translate_subctg(a, b) {
    switch (a) {
        case 1:
            switch (b) {
                case 1:
                    return "前端开发";
                case 2:
                    return "网站维护";
            }
        case 2:
            switch (b) {
                case 1:
                    return "安卓APP";
                case 2:
                    return "苹果APP";
            }
        case 3:
            switch (b) {
                case 1:
                    return "H5模板";
                case 2:
                    return "H5定制";
            }
        case 4:
            switch (b) {
                case 1:
                    return "网站UI";
                case 2:
                    return "移动UI";
            }
    }
    return "全部";
}
function translate_state(a) {
    switch (a) {
        case 0:
            return "匹配工作室";
        case 1:
            return "工作室工作";
        case 2:
            return "验收成果";
    }
}

function translate_passed(a) {
    switch (a) {
        case 0:
            return "未审核";
        case 1:
            return "已通过";
        case 2:
            return "未通过";
    }
}
 function translate_manager(type, state) {
    switch (type) {
        case 0:
            switch (state) {
                case 0:
                    return "正常";
                case 1:
                    return "已撤回";
            }
        case 1:
            switch (state) {
                case 0:
                    return "正常";
                case 1:
                    return "已撤回";
            }

        case 2:
            switch (state) {
                case 0:
                    return "已处理";
                case 1:
                    return "待处理";
            }
    }
}

export{
    $,
    $name,
    serialize,
    translate,
    translate_ctg,
    translate_manager,
    translate_passed,
    translate_state,
    translate_subctg
}