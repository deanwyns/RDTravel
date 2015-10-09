/**
 * Created by DWSAX40 on 8/10/2015.
 */
(function() {
    $(document).ready(function() {
        $.getJSON(continentsJsonUrl, {}, jsonReceived);

        function jsonReceived(json) {
            $.fn.vectorMap('addMap', 'continents_mill', json);
            $('#world-map').vectorMap({
                map: 'continents_mill',
                zoomButtons: false,
                onRegionClick: onRegionClick
            });

            var map = $("#world-map").vectorMap('get', 'mapObject');
        }

        function onRegionClick(e, code) {
            console.log(e);
            console.log(code);
            window.selectContinent({code: code});
        }
    });
})();

function handleContinent(xhr, code, args) {
    console.log(arguments);
}