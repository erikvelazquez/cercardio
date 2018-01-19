(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('LivingPlace', LivingPlace);

    LivingPlace.$inject = ['$resource'];

    function LivingPlace ($resource) {
        var resourceUrl =  'api/living-places/:id';

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
