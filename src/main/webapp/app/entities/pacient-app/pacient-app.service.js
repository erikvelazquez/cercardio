(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('PacientApp', PacientApp);

    PacientApp.$inject = ['$resource', 'DateUtils'];

    function PacientApp ($resource, DateUtils) {
        var resourceUrl =  'api/pacient-apps/:id';

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
