(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('MedicNurse', MedicNurse);

    MedicNurse.$inject = ['$resource'];

    function MedicNurse ($resource) {
        var resourceUrl =  'api/medic-nurses/:id';

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
