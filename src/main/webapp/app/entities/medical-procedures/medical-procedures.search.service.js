(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('Medical_ProceduresSearch', Medical_ProceduresSearch);

    Medical_ProceduresSearch.$inject = ['$resource'];

    function Medical_ProceduresSearch($resource) {
        var resourceUrl =  'api/_search/medical-procedures/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
