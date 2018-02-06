(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('Appreciation', Appreciation);

    Appreciation.$inject = ['$resource', 'DateUtils'];

    function Appreciation ($resource, DateUtils) {
        var resourceUrl =  'api/appreciations/:id';

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
