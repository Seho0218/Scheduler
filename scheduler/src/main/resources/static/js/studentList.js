$(document).ready(function() {
    $('.delete-button').click(function() {
        const studentId = $(this).attr('value');
        const confirmation = confirm('정말 학생 정보를 삭제하시겠습니까?');
        if (confirmation) {
            $.ajax({
                url: '/manage/deleteStudentList',
                method: 'POST',
                data: { id : studentID },
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
        const row = $(this).closest('tr');

        const studentId = row.find('.studentID').val();
        const teacherId = row.find('.teacherID').val();

        console.log(teacherId);
        console.log(studentId);
        const confirmation = confirm('담당교사를 변경하시겠습니까?');
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