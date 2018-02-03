(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('PacientLaboratoySearch', PacientLaboratoySearch);

    PacientLaboratoySearch.$inject = ['$resource'];

    function PacientLaboratoySearch($resource) {
        var resourceUrl =  'api/_search/pacient-laboratoys/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
