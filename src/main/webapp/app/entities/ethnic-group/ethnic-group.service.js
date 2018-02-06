(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('EthnicGroup', EthnicGroup);

    EthnicGroup.$inject = ['$resource'];

    function EthnicGroup ($resource) {
        var resourceUrl =  'api/ethnic-groups/:id';

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
