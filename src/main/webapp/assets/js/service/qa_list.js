

$(document).ready(function(){

    $(".date-btn").click(function(){

        let days = $(this).data("day");

        let today = new Date();
        let endDate = today.toISOString().substring(0,10);

        let start = new Date();
        start.setDate(start.getDate() - days);

        let startDate = start.toISOString().substring(0,10);

        $("#startDate").val(startDate);
        $("#endDate").val(endDate);

    });

});


$('#pwdForm').submit(function(e){
    e.preventDefault();
    $.post('${ctx}/qa/checkPassword', $(this).serialize(), function(res){
        if(res === 'ok'){
            window.location.href = '${ctx}/qa/view?id=${param.qaNum}&auth=ok';
        } else {
            alert('비밀번호가 틀렸습니다.');
        }
    });
});




