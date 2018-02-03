(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('Disabilities', Disabilities);

    Disabilities.$inject = ['$resource'];

    function Disabilities ($resource) {
        var resourceUrl =  'api/disabilities/:id';

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
