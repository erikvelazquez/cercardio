(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('PacientContactSearch', PacientContactSearch);

    PacientContactSearch.$inject = ['$resource'];

    function PacientContactSearch($resource) {
        var resourceUrl =  'api/_search/pacient-contacts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
