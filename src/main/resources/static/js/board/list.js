$(document).ready(function() {
    $('.delete').click(function() {
        const id = $(this).parent('td').val();

        const confirmation = confirm('정말 공지를 삭제하시겠습니까?');
        if (confirmation) {
            $.ajax({
                url: '/board/delete/'+id,
                method: 'POST',
                success: function(data) {
                    alert(data);
                    location.reload();
                },
                error: function(xhr, status, error) {
                    console.log(error);
                }
            });
        }
    });
});