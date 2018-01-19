(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('PacientMedicalData', PacientMedicalData);

    PacientMedicalData.$inject = ['$resource'];

    function PacientMedicalData ($resource) {
        var resourceUrl =  'api/pacient-medical-data/:id';

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
