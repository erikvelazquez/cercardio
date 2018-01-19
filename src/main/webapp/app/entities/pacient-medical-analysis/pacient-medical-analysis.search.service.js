(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('PacientMedicalAnalysisSearch', PacientMedicalAnalysisSearch);

    PacientMedicalAnalysisSearch.$inject = ['$resource'];

    function PacientMedicalAnalysisSearch($resource) {
        var resourceUrl =  'api/_search/pacient-medical-analyses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
