(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('PacientMedicalAnalysis', PacientMedicalAnalysis);

    PacientMedicalAnalysis.$inject = ['$resource', 'DateUtils'];

    function PacientMedicalAnalysis ($resource, DateUtils) {
        var resourceUrl =  'api/pacient-medical-analyses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.daytime = DateUtils.convertDateTimeFromServer(data.daytime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
