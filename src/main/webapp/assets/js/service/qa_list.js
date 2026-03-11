const secret = document.querySelectorAll(".table-tit a");

secret.forEach(el => {
    el.addEventListener("click", (e) => {
        // e.preventDefault();
        alert("비밀글은 작성자 본인만 확인가능합니다. ");
    });
});

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