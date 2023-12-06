$(document).ready(function() {
    $('.approve-button').click(function() {
        const teacherId = $(this).attr('value');
        const confirmation = confirm(teacherId + '을(를) 승인하시겠습니까?');

        if (confirmation) {
            $.ajax({
                url: '/admin/api/'+username+'/change-email',
                method: 'POST',
                success: function(data) {
                    alert(data);
                    location.reload();
                },
                error: function() {
                    alert('승인에 실패했습니다. 다시 시도해주세요.');
                }
            });
        }
    });
});