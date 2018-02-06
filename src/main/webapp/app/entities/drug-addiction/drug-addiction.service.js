(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('DrugAddiction', DrugAddiction);

    DrugAddiction.$inject = ['$resource'];

    function DrugAddiction ($resource) {
        var resourceUrl =  'api/drug-addictions/:id';

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
