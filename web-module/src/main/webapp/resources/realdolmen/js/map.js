/**
 * Created by DWSAX40 on 8/10/2015.
 */
(function() {
    var continentMap = {
        map: null,
        container: null
    };
    var worldMap = {
        map: null,
        container: null,
        continent: null,
        country: null
    };

    var currentAirportPopup = {
        country: null,
        div: null
    };
    var scalePromise = null;
    var backButton = null;

    $(document).ready(function() {
        window.getAirportsCallback = getAirportsCallback;

        var worldMapEl = $('#world-map');

        continentMap.container = $('<div/>').css({
            width: '100%',
            height: '100%',
            position: 'absolute'
        });

        worldMap.container = $('<div/>').css({
            width: '100%',
            height: '100%',
            position: 'absolute'
        });

        worldMapEl.append(worldMap.container);
        worldMapEl.append(continentMap.container);

        continentMap.map = new jvm.Map({
            container: continentMap.container,
            map: 'continents_mill',
            zoomButtons: false,
            zoomOnScroll: false,
            panOnDrag: false,
            regionsSelectable: false,
            onRegionClick: onContinentClick
        });

        worldMap.map = new jvm.Map({
            container: worldMap.container,
            map: 'world_mill',
            zoomButtons: false,
            zoomOnScroll: false,
            panOnDrag: false,
            regionsSelectableOne: true,
            onRegionClick: onCountryClick,
            onViewportChange: throttle(onViewportChange, 1000)
        });

        worldMap.container.click(onWorldMapClick);
    });

    function onContinentClick(e, code) {
        worldMap.continent = code;
        var zoom = continentMap.map.getScaleByRegion(code);
        worldMap.container.zIndex(1000);
        scalePromise = worldMap.map.setScale(zoom.scale, zoom.anchorX, zoom.anchorY, true, true);
    }

    function onCountryClick(e, code) {
        worldMap.map.clearSelectedRegions();
        worldMap.map.setSelectedRegions(code);
    }

    function onWorldMapClick(e) {
        if(worldMap.map.getSelectedRegions().length === 0) {
            return;
        }

        var selectedCountry = worldMap.map.getSelectedRegions()[0];
        if(currentAirportPopup.country === selectedCountry) {
            return;
        }

        currentAirportPopup.country = null;
        if(currentAirportPopup.div !== null) {
            currentAirportPopup.div.remove();
        }
        createAirportPopup(selectedCountry, e.offsetX, e.offsetY).then(popupCreated);
        function popupCreated() {
            worldMap.container.append(currentAirportPopup.div);
        }
    }

    function onViewportChange(e, scale) {
        if(worldMap.map === null || (scalePromise !== null && scalePromise.state() !== 'resolved')) {
            return;
        }

        if(worldMap.continent === null) {
            return;
        }

        // Then retrieve the scale for the selected region. If the map is not visible, this isn't working.
        var zoom = continentMap.map.getScaleByRegion(worldMap.continent);

        // Set the scale to the world map.
        scalePromise = worldMap.map.setScale(zoom.scale, zoom.anchorX, zoom.anchorY, true, true);
    }

    jvm.Map.prototype.getScaleByRegion = function(region) {
        var bbox,
            itemBbox,
            newBbox;

        itemBbox = this.regions[region].element.shape.getBBox();
        if (itemBbox) {
            if (typeof bbox === 'undefined') {
                bbox = itemBbox;
            } else {
                newBbox = {
                    x: Math.min(bbox.x, itemBbox.x),
                    y: Math.min(bbox.y, itemBbox.y),
                    width: Math.max(bbox.x + bbox.width, itemBbox.x + itemBbox.width) - Math.min(bbox.x, itemBbox.x),
                    height: Math.max(bbox.y + bbox.height, itemBbox.y + itemBbox.height) - Math.min(bbox.y, itemBbox.y)
                };

                bbox = newBbox;
            }
        }

        return {
            scale: Math.min(this.width / bbox.width, this.height / bbox.height),
            anchorX: -(bbox.x + bbox.width / 2),
            anchorY: -(bbox.y + bbox.height / 2)
        };
    };

    function createAirportPopup(country, x, y) {
        var deferred = $.Deferred();
        currentAirportPopup.country = country;
        currentAirportPopup.div = $('<div />').css({
            position: 'absolute',
            left: x,
            top: y,
            background: '#eee',
            overflow: 'auto',
            'max-height': '30%'
        });

        getAirports(country).then(airportsResolved);
        function airportsResolved(airports) {
            var $list = $('<ul />').css({
                height: '90%'
            });
            for(var i = 0, len = airports.length; i < len; i++) {
                (function(i) {
                    var $listItem = $('<li>' + airports[i].name + '</li>');
                    $listItem.click(function() {
                        selectAirport([
                            { name: 'continent', value: worldMap.continent },
                            { name: 'airport', value: airports[i].id }
                        ]);
                    });
                    $listItem.appendTo($list);
                })(i);
            }

            $list.appendTo(currentAirportPopup.div);
            deferred.resolve();
        }

        return deferred.promise();
    }

    var getAirportsDeferred = null;
    function getAirports(country) {
        getAirportsDeferred = $.Deferred();
        window.getAirports([{ name: 'country', 'value': country }]);
        return getAirportsDeferred.promise();
    }

    function getAirportsCallback(xhr, code, args) {
        if(getAirportsDeferred !== null) {
            getAirportsDeferred.resolve($.parseJSON(args.airports));
        }
    }

    /**
     * http://stackoverflow.com/questions/18177174/how-to-limit-handling-of-event-to-once-per-x-seconds-with-jquery-javascript
     * @param func
     * @param interval
     * @returns {Function}
     */
    function debounce(func, interval) {
        var lastCall = -1;
        return function() {
            clearTimeout(lastCall);
            var args = arguments;
            var self = this;
            lastCall = setTimeout(function() {
                func.apply(self, args);
            }, interval);
        };
    }

    /**
     * http://stackoverflow.com/questions/18177174/how-to-limit-handling-of-event-to-once-per-x-seconds-with-jquery-javascript
     * @param func
     * @param interval
     * @returns {Function}
     */
    function throttle(func, interval) {
        var lastCall = 0;
        return function() {
            var now = Date.now();
            if (lastCall + interval < now) {
                lastCall = now;
                return func.apply(this, arguments);
            }
        };
    }
})();