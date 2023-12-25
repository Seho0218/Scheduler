$(document).ready(function() {
    $('.delete-button').click(function() {
        const teacherId = $(this).attr('value');
        const confirmation = confirm(teacherId + '을(를) 삭제하시겠습니까?');
        if (confirmation) {
            $.ajax({
                url: '/search/api/delete/'+id,
                method: 'POST',
                success: function(data) {
                    alert(data);
                    location.reload();
                },
                error: function() {
                    alert('삭제에 실패했습니다. 다시 시도해주세요.');
                }
            });
        }
    });
});