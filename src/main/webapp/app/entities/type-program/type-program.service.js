(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('Type_Program', Type_Program);

    Type_Program.$inject = ['$resource'];

    function Type_Program ($resource) {
        var resourceUrl =  'api/type-programs/:id';

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
