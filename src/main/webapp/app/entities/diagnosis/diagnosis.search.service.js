(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('DiagnosisSearch', DiagnosisSearch);

    DiagnosisSearch.$inject = ['$resource'];

    function DiagnosisSearch($resource) {
        var resourceUrl =  'api/_search/diagnoses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
