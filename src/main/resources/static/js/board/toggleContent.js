function toggleContent(button) {
    var contentDiv = button.parentElement.nextElementSibling;
    if (contentDiv.style.display === 'none') {
        contentDiv.style.display = 'block';
    } else {
        contentDiv.style.display = 'none';
    }
}