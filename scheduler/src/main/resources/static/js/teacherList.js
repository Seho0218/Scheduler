$(document).ready(function() {
    $('.approve-button').click(function() {
        const teacherId = $(this).attr('value');
        const confirmation = confirm(teacherId + '을(를) 승인하시겠습니까?');

        if (confirmation) {
            $.ajax({
                url: '/admin/grant',
                method: 'POST',
                data: { username : teacherId },
                success: function() {
                    alert('승인되었습니다');
                    location.reload();
                },
                error: function() {
                    alert('승인에 실패했습니다. 다시 시도해주세요.');
                }
            });
        }
    });

    $('.cancel-button').click(function() {
        const teacherId = $(this).attr('value');
        const confirmation = confirm(teacherId + '을(를) 승인을 취소하시겠습니까?');

        if (confirmation) {
            $.ajax({
                url: '/admin/revoke',
                method: 'POST',
                data: { username : teacherId },
                success: function() {
                    alert('승인 취소 되었습니다');
                    location.reload();
                },
                error: function() {
                    alert('승인 취소에 실패했습니다. 다시 시도해주세요.');
                }
            });
        }
    });

    $('.delete-button').click(function() {
        const teacherId = $(this).attr('value');
        const confirmation = confirm(teacherId + '을(를) 삭제하시겠습니까?');

        if (confirmation) {
            $.ajax({
                url: '/admin/delete',
                method: 'POST',
                data: { username : teacherId },
                success: function() {
                    alert('삭제 되었습니다');
                    location.reload();
                },
                error: function() {
                    alert('삭제에 실패했습니다. 다시 시도해주세요.');
                }
            });
        }
    });
});