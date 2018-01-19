(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('PacientApnpSearch', PacientApnpSearch);

    PacientApnpSearch.$inject = ['$resource'];

    function PacientApnpSearch($resource) {
        var resourceUrl =  'api/_search/pacient-apnps/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
