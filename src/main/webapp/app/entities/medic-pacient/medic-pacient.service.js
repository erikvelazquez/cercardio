(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('MedicPacient', MedicPacient);

    MedicPacient.$inject = ['$resource'];

    function MedicPacient ($resource) {
        var resourceUrl =  'api/medic-pacients/:id';

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
