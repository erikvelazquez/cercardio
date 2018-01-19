(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('Occupation', Occupation);

    Occupation.$inject = ['$resource'];

    function Occupation ($resource) {
        var resourceUrl =  'api/occupations/:id';

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
