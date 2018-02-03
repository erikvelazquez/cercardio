(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('Diagnosis', Diagnosis);

    Diagnosis.$inject = ['$resource'];

    function Diagnosis ($resource) {
        var resourceUrl =  'api/diagnoses/:id';

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
