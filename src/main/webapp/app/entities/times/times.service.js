(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('Times', Times);

    Times.$inject = ['$resource'];

    function Times ($resource) {
        var resourceUrl =  'api/times/:id';

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
