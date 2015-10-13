/**
 * Created by DWSAX40 on 8/10/2015.
 */
(function() {
    var countries = {
        AF: ['BF', 'DJ', 'BI', 'BJ', 'ZA', 'BW', 'DZ', 'ET', 'RW', 'TZ', 'GQ', 'NA', 'NE', 'NG', 'TN', 'LR', 'LS', 'ZW', 'TG', 'TD', 'ER', 'LY', 'GW', 'ZM', 'CI', 'EH', 'CM', 'EG', 'SL', 'CG', 'CF', 'AO', 'CD', 'GA', 'GN', 'GM', 'XS', 'CV', 'GH', 'SZ', 'MG', 'MA', 'KE', 'SS', 'ML', 'KM', 'ST', 'MW', 'SO', 'SN', 'MR', 'UG', 'SD', 'MZ'],
        AS: ['BD', 'MN', 'BN', 'BH', 'BT', 'HK', 'JO', 'PS', 'LB', 'LA', 'TW', 'TR', 'LK', 'TL', 'TM', 'TJ', 'TH', 'XC', 'NP', 'PK', 'PH', 'AE', 'CN', 'AF', 'IQ', 'JP', 'IR', 'AM', 'SY', 'VN', 'GE', 'IL', 'IN', 'AZ', 'ID', 'OM', 'KG', 'UZ', 'MM', 'SG', 'KH', 'CY', 'QA', 'KR', 'KP', 'KW', 'KZ', 'SA', 'MY', 'YE'],
        EU: ['BE', 'FR', 'BG', 'DK', 'HR', 'DE', 'BA', 'HU', 'JE', 'FI', 'BY', 'GR', 'RU', 'NL', 'PT', 'NO', 'LI', 'LV', 'LT', 'LU', 'FO', 'PL', 'XK', 'CH', 'AD', 'EE', 'IS', 'AL', 'IT', 'GG', 'CZ', 'IM', 'GB', 'AX', 'IE', 'ES', 'ME', 'MD', 'RO', 'RS', 'MK', 'SK', 'MT', 'SI', 'SM', 'UA', 'SE', 'AT'],
        NA: ['PR', 'DO', 'DM', 'LC', 'NI', 'PA', 'CA', 'SV', 'HT', 'TT', 'JM', 'GT', 'HN', 'BZ', 'BS', 'CR', 'US', 'GL', 'MX', 'CU'],
        OC: ['GU', 'PW', 'KI', 'NC', 'NU', 'NZ', 'AU', 'PG', 'SB', 'PF', 'FJ', 'FM', 'WS', 'VU'],
        SA: ['PY', 'CO', 'VE', 'CL', 'SR', 'BO', 'EC', 'AR', 'GY', 'BR', 'PE', 'UY', 'FK']
    };

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

    var regionStyle = {
        initial: {
            fill: '#1e8adf',
            "fill-opacity": 1,
            stroke: 'none',
            "stroke-width": 0,
            "stroke-opacity": 1
        },
        hover: {
            "fill-opacity": 0.8,
            cursor: 'pointer'
        },
        selected: {
            fill: '#201858'
        },
        selectedHover: {
        }
    };

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
        backButton = $('<div/>').addClass('jvectormap-goback').text('Back');
        backButton.hide();
        worldMap.container.append(backButton);
        backButton.click(function() {
            worldMap.continent = null;
            worldMap.container.css('z-index', '');
            worldMap.map.setSelectedRegions([]);
            worldMap.map.reset();

            currentAirportPopup.country = null;
            if(currentAirportPopup.div !== null) {
                currentAirportPopup.div.remove();
            }

            backButton.hide();
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
            backgroundColor: '#f4f4f4',
            regionStyle: regionStyle,
            onRegionClick: onContinentClick
        });

        worldMap.map = new jvm.Map({
            container: worldMap.container,
            map: 'world_mill',
            zoomButtons: false,
            zoomOnScroll: false,
            panOnDrag: false,
            regionsSelectableOne: true,
            backgroundColor: '#f4f4f4',
            regionStyle: regionStyle,
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

        backButton.show();
    }

    function onCountryClick(e, code) {
        if(countries[worldMap.continent].indexOf(code) === -1) {
            e.preventDefault();
            return;
        }

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
            left: x,
            top: y
        }).addClass('airport-popup');

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
                        currentAirportPopup.div.remove();
                        worldMap.map.setSelectedRegions([]);
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