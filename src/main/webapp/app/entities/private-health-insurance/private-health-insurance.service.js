(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('PrivateHealthInsurance', PrivateHealthInsurance);

    PrivateHealthInsurance.$inject = ['$resource'];

    function PrivateHealthInsurance ($resource) {
        var resourceUrl =  'api/private-health-insurances/:id';

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
