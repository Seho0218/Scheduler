$(document).ready(function() {
    $('.delete-button').click(function() {
        var studentName = $(this).attr('value');
        var confirmation = confirm('정말로 ' + studentName + '을(를) 삭제하시겠습니까?');
        if (confirmation) {
            $.ajax({
                url: '/manage/delete',
                method: 'POST',
                data: { studentName : studentName },
                success: function() {
                    alert('삭제되었습니다.');
                    location.reload();
                },
                error: function(xhr, status, error) {
                    console.log(error);
                }
            });
        }
    });
});