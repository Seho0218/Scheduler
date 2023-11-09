$(document).ready(function() {
    $('.delete-button').click(function() {
        var studentId = $(this).attr('value');
        var confirmation = confirm('정말 학생 정보를 삭제하시겠습니까?');
        if (confirmation) {
            $.ajax({
                url: '/manage/deleteStudentList',
                method: 'POST',
                data: { id : studentId },
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

    $('.save-button').click(function() {
        var teacherId = $('#teacherID').val();
        var studentId = $('#studentID').val();
        var confirmation = confirm('담당교사를 변경하시겠습니까?');
        if (confirmation) {
            $.ajax({
                url: '/admin/changeTeacher',
                method: 'POST',
                data: {
                    teacherId : teacherId,
                    studentId : studentId
                },
                success: function() {
                    alert('변경되었습니다.');
                    location.reload();
                },
                error: function(xhr, status, error) {
                    console.log(error);
                }
            });
        }
    });
});