(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('Medical_Procedures', Medical_Procedures);

    Medical_Procedures.$inject = ['$resource'];

    function Medical_Procedures ($resource) {
        var resourceUrl =  'api/medical-procedures/:id';

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
