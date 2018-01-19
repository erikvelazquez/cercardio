(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('MedicalAnalysis', MedicalAnalysis);

    MedicalAnalysis.$inject = ['$resource', 'DateUtils'];

    function MedicalAnalysis ($resource, DateUtils) {
        var resourceUrl =  'api/medical-analyses/:id';

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
