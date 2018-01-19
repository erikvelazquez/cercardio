(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('UserBD', UserBD);

    UserBD.$inject = ['$resource', 'DateUtils'];

    function UserBD ($resource, DateUtils) {
        var resourceUrl =  'api/user-bds/:id';

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
