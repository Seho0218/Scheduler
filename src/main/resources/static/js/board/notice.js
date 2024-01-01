$(document).ready(function() {
    $('#save-button').click(function() {
        var noticeId = $("#noticeId").text();
        var commentAuthor = $("#username").val();
        var password = $("#password").val();
        var comment = $("#comment").val();


        $.ajax({
            url: '/comment/comment_submit',
            method: 'POST',
            data: {
                noticeId : noticeId,
                commentAuthor: commentAuthor,
                password: password,
                comment: comment
            },
            success: function () {
                location.reload();
                },
            error: function (xhr) {
                alert(xhr.responseText);
            }
        });
    });

    $('.delete-button').click(function() {
        const teacherId = $(this).attr('value');

        if (confirmation) {
            $.ajax({
                url: '/comment/comment_delete',
                method: 'POST',
                data:{

                },
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