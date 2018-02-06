(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('PacientMedicalDataSearch', PacientMedicalDataSearch);

    PacientMedicalDataSearch.$inject = ['$resource'];

    function PacientMedicalDataSearch($resource) {
        var resourceUrl =  'api/_search/pacient-medical-data/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
