
var pattern = [];
pattern['num'] = /^[0-9]+$/;
pattern['numAndColon'] = /^[0-9:]+$/;
pattern['float'] = /^\d{1,4}(\.{1}[\d]?[\d])?$/;
pattern['engnum'] = /^[a-zA-Z0-9]^/;
pattern['email'] = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

// validation patterns
pattern['eng'] = /^[a-zA-Z]^/;
pattern['eng_f'] = /^[ａ-ｚＡ-Ｚ]^/;
pattern['passwd'] = /[^0-9a-zA-Z0-9!#$%&’*+/=?^_`{|}~.-]+/;
pattern['sp'] = /[^\u3041-\u309e\u309b-\u309c\u30a1-\u30fe\uff61-\uff9f\u4E00-\u9FA5\uF900-\uFA2Da-zA-Zａ-ｚＡ-Ｚ0-9０-９!#$%&’’'*+/=?^_`{|}~.｡。､、！＃＄％＆‘＇＊＋－／＝？＾＿｀｛｜}～．\s \r\n-]+/;


var pieOptions = {
    cutoutPercentage: 30,
    rotation: -0.5 * Math.PI,
    circumference: 2 * Math.PI,
    animation: {
        animateRotate: true,
        animateScale: false
    }
};
var barChartOptions = {
    barPercentage: 0.8,
    categoryPercentage: 0.8,
    responsive: true,
    legend: {
        position: 'top',
    },
    scales: {
        yAxes:[
            {
                ticks:{
                    beginAtZero:true
                }
            }
        ]
    }
};

function checkError(error) {
    console.log(error);
    if(!error.response){

    }else{
        if(error.response.status === 403){
            // console.log('expire');
            $('#modal-expire').show();
            // alert('권한이 없습니다.\n로그인 해주세요.');
            // location.href = '/login';
        }else{
            console.log(error.response);
            showAlert('경고','처리 중 오류가 발생하였습니다. 잠시후 다시 시도해주세요.');
        }
    }
}

function errorMsg(){
    showAlert('카테고리','처리중 오류가 발생하였습니다.');
}

function extractTel(tel) {
    var telNum = [];
    if (tel === "" || tel === null) {
        return telNum;
    } else {
        telNum.push(tel.substr(0,3));
        if (tel.length === 11) {
            telNum.push(tel.substr(3,4));
            telNum.push(tel.substr(7,4));
        } else {
            telNum.push(tel.substr(3,3));
            telNum.push(tel.substr(6,4));
        }
    }
    return telNum;
}


function formatNumber(v1,v2){
    var str = new Array();
    if(v1 == null){
        v1 = 0;
    }
    v1 = String(v1);
    for(var i=1;i<=v1.length;i++){
        if(i % v2) str[v1.length-i] = v1.charAt(v1.length-i);
        else str[v1.length-i] = ','+v1.charAt(v1.length-i);
    }
    return str.join('').replace(/^,/,'');
}

function dateFormat(t, type) {
    var d = new Date(t);
    var returnDate = '';

    switch (type) {
        case 'date':
            var month = '' + (d.getMonth() + 1);
            var day = '' + d.getDate();
            var year = d.getFullYear();

            if (month.length < 2) month = '0' + month;
            if (day.length < 2) day = '0' + day;

            returnDate = [year, month, day].join('-');
            break;
        case 'hour':
            var hours = d.getHours().toString();
            if (hours.length < 2) hours = '0' + hours.toString();
            returnDate = hours;
            break;
        case 'min':
            var mins = d.getMinutes().toString();
            if (mins.length < 2) mins = '0' + mins.toString();
            returnDate = mins;
            break;
        case 'time':
            var hours = d.getHours().toString();
            if (hours.length < 2) hours = '0' + hours.toString();
            var mins = d.getMinutes().toString();
            if (mins.length < 2) mins = '0' + mins.toString();

            var time = hours + ':' + mins;
            returnDate = time;
            break;
    }

    return returnDate;
}

function searchObj(searchObj, searchKey, searchVal){
    var obj = $.grep(searchObj, function(obj){return eval('obj.' + searchKey) == searchVal;})[0];
    return obj;
}

function checkLength(inputObj, maxLength){
    var inputString = inputObj.val();
    if(inputString.length > maxLength){
        showAlert("경고","입력가능한 최대 글자수를 초과하였습니다");
        // inputObj.val(inputString.substring(0,maxLength));
    }

    return inputString.substring(0,maxLength);
}

function checkBizID(bizID)  //사업자등록번호 체크
{
    // bizID는 숫자만 10자리로 해서 문자열로 넘긴다.
    var checkID = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5, 1);
    var tmpBizID, i, chkSum=0, c2, remander;
    bizID = bizID.replace(/-/gi,'');

    for (i=0; i<=7; i++) chkSum += checkID[i] * bizID.charAt(i);
    c2 = "0" + (checkID[8] * bizID.charAt(8));
    c2 = c2.substring(c2.length - 2, c2.length);
    chkSum += Math.floor(c2.charAt(0)) + Math.floor(c2.charAt(1));
    remander = (10 - (chkSum % 10)) % 10 ;

    if (Math.floor(bizID.charAt(9)) == remander) return true ; // OK!
    return false;
}

function showAlert(title,msg){
    $('#warning-title').html(title);
    $('#warning-msg').html(msg);

    $('#modal-warning').show();

    $('#warning_bt').focus();
}

function showConfirm(title, msg, goFunc){
    $('#confirm-title').html(title);
    $('#confirm-msg').html(msg);

    $('#modal-confirm').show();

    $("#confirm_bt").off('click').click(function(){
        $("#modal-confirm").hide();
        if(!goFunc){
            return false;
        }
        goFunc();
    });
}


function replaceValiString(type, val){
    var returnVal = val;
    switch (type){
        case 'number':
            returnVal = val.replace(/[^0-9]/g,"");
            break;
        case 'alphaNum':
            returnVal = val.replace(/[^A-Za-z0-9]/g,"");
            break;
        case 'alphaNumHy':
            returnVal = val.replace(/[^A-Za-z0-9-]/g,"");
            break;
    }

    return returnVal;
}

$(document).ready(function(){
    $('.datepicker').datepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
        clearBtn: true,
        showOn: 'both',
    });
/*
    $('.timepicker').flatpickr({
        enableTime: true,
        noCalendar: true,
        dateFormat: "H:i",
        time_24hr: true,
        // wrap: true
    });

    $('.timepicker-second').flatpickr({
        enableTime: true,
        noCalendar: true,
        dateFormat: "H:i:S",
        time_24hr: true,
        enableSeconds: true,
        // wrap: true
    });*/

    //Timepicker
    $('.timepicker').timepicker({
        showInputs: false,
        maxHours:24,
        showMeridian:false,
        minuteStep: 5,
        defaultTime: '12:00'
    });


    $(':button').click(function(evnet){
        if($(this).data('dismiss')){
            $('.' + $(this).data('dismiss')).hide();
        }else if($(this).data('show')){
            $('#' + $(this).data('show')).show();
        }
    });

    $('#expire_login_bt').click(function(){
        location.href = '/login';
    });

    $('.number').keyup(function(event){
        $(this).val($(this).val().replace(/[^0-9]/g,""));
    });
    $('.number').focusout(function(event){
        $(this).val($(this).val().replace(/[^0-9]/g,""));
    });

    $('.alphabetAndNumber').keyup(function(event){
        $(this).val($(this).val().replace(/[^A-Za-z0-9]/g,""));
    });

    $('.numberAndPoint').keyup(function(event){
        $(this).val($(this).val().replace(/[^0-9.]/g,""));
    });
    $('.alphabetAndNUmberAndHyphen').keyup(function(){
        $(this).val($(this).val().replace(/[^A-Za-z0-9-]/g,""));
    });

});

