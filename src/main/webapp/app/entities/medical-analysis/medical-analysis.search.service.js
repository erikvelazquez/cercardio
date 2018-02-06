(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('MedicalAnalysisSearch', MedicalAnalysisSearch);

    MedicalAnalysisSearch.$inject = ['$resource'];

    function MedicalAnalysisSearch($resource) {
        var resourceUrl =  'api/_search/medical-analyses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
