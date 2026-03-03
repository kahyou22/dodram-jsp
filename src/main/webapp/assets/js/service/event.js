
$(document).ready(function(){
  $('.tab-btn').click(function(){
    const tab = $(this).data('tab');

    // 버튼 active 처리
    $('.tab-btn').removeClass('active');
    $(this).addClass('active');

    // 이벤트 카드 표시
    if(tab === 'all'){
      $('.event-card').show();
    } else {
      $('.event-card').hide();
      $('.event-card[data-tab="'+tab+'"]').show();
    }
      // ✅ 모든 카드가 숨겨졌는지 확인
    if($('.event-card:visible').length === 0){
      // 기존 문구 없으면 추가
      if($('#eventList .empty').length === 0){
        $('#eventList').append('<li class="empty">진행중인 이벤트가 없습니다.</li>');
      }
    } else {
      // 카드가 보이면 문구 제거
      $('#eventList .empty').remove();
    }
  });
  
});
