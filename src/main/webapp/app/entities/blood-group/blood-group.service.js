(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('BloodGroup', BloodGroup);

    BloodGroup.$inject = ['$resource'];

    function BloodGroup ($resource) {
        var resourceUrl =  'api/blood-groups/:id';

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
