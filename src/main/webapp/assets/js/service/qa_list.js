const secret = document.querySelectorAll(".table-tit a");

secret.forEach(el => {
    el.addEventListener("click", (e) => {
        // e.preventDefault();
        alert("비밀글은 작성자 본인만 확인가능합니다. ");
    });
});
