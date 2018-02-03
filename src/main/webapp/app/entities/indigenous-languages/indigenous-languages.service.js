(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('Indigenous_Languages', Indigenous_Languages);

    Indigenous_Languages.$inject = ['$resource'];

    function Indigenous_Languages ($resource) {
        var resourceUrl =  'api/indigenous-languages/:id';

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
