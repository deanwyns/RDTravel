(function() {
    $(document).ready(function() {
        var $splash = $('.splash');
        var url = 'https://unsplash.it/' + $splash.width() + '/300/?random';
        $splash.css('background-image', 'url(\'' + url + '\')');
    });
})();