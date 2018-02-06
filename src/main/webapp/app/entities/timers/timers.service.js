(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('Timers', Timers);

    Timers.$inject = ['$resource'];

    function Timers ($resource) {
        var resourceUrl =  'api/timers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
