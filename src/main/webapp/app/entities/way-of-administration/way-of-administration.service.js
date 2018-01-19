(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('Way_of_Administration', Way_of_Administration);

    Way_of_Administration.$inject = ['$resource'];

    function Way_of_Administration ($resource) {
        var resourceUrl =  'api/way-of-administrations/:id';

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
