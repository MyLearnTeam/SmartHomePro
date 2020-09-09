var ServerResults={
    Success:200,//请求成功
    SuccessWithoutBody:"201",//请求成功无响应体
    ServerError:400,//服务器内部错误
    ReqParameterError:300//请求参数错误
};


//搜索条件操作
var SearchOperators = {
    GREATER_THAN: "GREATER_THAN",//大于
    LESS_THAN: "LESS_THAN",//小于
    EQUAL: "EQUAL",//等于
    NOT_EQUAL:"NOT_EQUAL",//不等于
    GREATER_EQUAL:"GREATER_EQUAL",//大于等于
    LESS_EQUAL:"LESS_EQUAL",//小于等于
    FUZZY_MATCH:"FUZZY_MATCH",//模糊匹配
    IN:"IN"//当使用此比较符时，则对应的value值列表请以“,”分隔，表示查询在此范围内的数据。
};

var SearchConnectors={
    and:"and",
    or:"or"
}

//排序方式
var OrderMethods={
    asc:"asc",
    desc:"desc"
}


//树等级
var TreeLevels={
    Parent:0,//父级（上一级）
    Parents:1,//所有父级
    ParentAndSelf:2,//父级（上一级）包括自己
    ParentsAndSelf:3,//所有父级包括自己
    Leaf:4,//子级（下一级）
    Leafs:5,//所有子级
    LeafAndSelf:6,//下一级包括自己
    LeafsAndSelf:7//所有下一级包括自己
}


JigouInvaild={
    No:0,//已失效
    Yes:1//未失效
}



OrderStatus={
    BackPay:-5,//退回支付
    Invalidation:-4,//作废
    ContractBack:-3,//合同退回
    Cancel:-2,//取消
    Return:-1,//退回
    NotSubmitted:0,//未提交
    WaitConfirmed:1,//待确认
    WaitContract:2,//已确认（待上传合同）
    WaitEvaluate:3,//待评价
    WaitPayment:4,//待支付
    Finished:5//已完成
}

OrderPaymentStatus={
    WaitPayment:0,//未支付
    Paied:1,//已支付
    ConfirmedPaied:2//机构已确认支付
}

function getOrderStatusName(orderStatus,paymentStatus) {
    if(orderStatus==OrderStatus.BackPay){
        return "退回支付";
    }

    if(orderStatus==OrderStatus.Invalidation){
        return "作废";
    }

    if(orderStatus==OrderStatus.ContractBack){
        return "合同退回";
    }

    if(orderStatus==OrderStatus.Cancel){
        return "企业取消";
    }

    if(orderStatus==OrderStatus.Return){
        return "退回";
    }

    if(orderStatus==OrderStatus.NotSubmitted){
        return "未提交";
    }

    if(orderStatus==OrderStatus.WaitConfirmed){
        return "待服务机构确认";
    }

    if(orderStatus==OrderStatus.WaitContract){
        return "已确认（待上传合同）";
    }

    if(orderStatus==OrderStatus.WaitEvaluate){
        return "待评价";
    }

    if(orderStatus==OrderStatus.WaitPayment){
        return "待支付";
    }

    if(orderStatus==OrderStatus.Finished){

        if(paymentStatus==OrderPaymentStatus.WaitPayment){
            return "待支付";
        }

        if(paymentStatus==OrderPaymentStatus.Paied){
            return "已支付，待结算";
        }

        if(paymentStatus==OrderPaymentStatus.ConfirmedPaied){
            return "已完成";
        }

    }
   
    

    return "未知";

}
