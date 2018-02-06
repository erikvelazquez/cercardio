(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('Medic', Medic);

    Medic.$inject = ['$resource', 'DateUtils'];

    function Medic ($resource, DateUtils) {
        var resourceUrl =  'api/medics/:id';

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
