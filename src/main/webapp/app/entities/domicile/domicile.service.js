(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('Domicile', Domicile);

    Domicile.$inject = ['$resource'];

    function Domicile ($resource) {
        var resourceUrl =  'api/domiciles/:id';

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
