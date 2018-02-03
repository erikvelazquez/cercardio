(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('MedicPacientSearch', MedicPacientSearch);

    MedicPacientSearch.$inject = ['$resource'];

    function MedicPacientSearch($resource) {
        var resourceUrl =  'api/_search/medic-pacients/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
