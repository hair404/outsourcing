package com.common.result;

public class ResultBean {

    private int code;

    private String description;

    private Object content;

    public static ResultBean success(Object content) {
        ResultBean resultBean = new ResultBean();
        resultBean.code = ResultCode.OK.getId();
        resultBean.description = ResultCode.OK.getDescription();
        resultBean.content = content;
        return resultBean;
    }

    public static ResultBean success() {
        return success(null);
    }

    public static ResultBean fromCode(ResultCode resultCode) {
        ResultBean resultBean = new ResultBean();
        resultBean.code = resultCode.getId();
        resultBean.description = resultCode.getDescription();
        return resultBean;
    }
}
