(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('PacientLaboratoy', PacientLaboratoy);

    PacientLaboratoy.$inject = ['$resource', 'DateUtils'];

    function PacientLaboratoy ($resource, DateUtils) {
        var resourceUrl =  'api/pacient-laboratoys/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateofelaboration = DateUtils.convertDateTimeFromServer(data.dateofelaboration);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
