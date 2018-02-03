(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('CivilStatus', CivilStatus);

    CivilStatus.$inject = ['$resource'];

    function CivilStatus ($resource) {
        var resourceUrl =  'api/civil-statuses/:id';

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
