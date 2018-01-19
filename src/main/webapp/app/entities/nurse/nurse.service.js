(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('Nurse', Nurse);

    Nurse.$inject = ['$resource', 'DateUtils'];

    function Nurse ($resource, DateUtils) {
        var resourceUrl =  'api/nurses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdat = DateUtils.convertDateTimeFromServer(data.createdat);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
